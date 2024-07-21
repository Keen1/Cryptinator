package main;

import controllers.Controller;
import controllers.DecrypterController;
import controllers.EncrypterController;
import keys.KeyCreator;
import net.sourceforge.argparse4j.*;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args){

        getNamespaceAndExec(args);

    }

    //get the namespace, validate the arguments, execute.
    public static void getNamespaceAndExec(String[] args){
        //get the namespace
        Namespace namespace = getNamespace(args);

        try{

            //validate arguments
            String mode = validateArgs(namespace);


            //get the list of paths provided
            List<String> paths = namespace.getList("paths");

            //check for key file
            //note - if no key file is used on encrypt mode, --generate-key is implied.
            String keyPath = namespace.getString("key_file");
            SecretKey key;

            //instantiate the controller
            Controller cryptController;
            if(mode.equals("encrypt")){

                if(keyPath == null){

                    //need to revisit hardcoding this
                    try{
                        //generate a key
                        key = KeyCreator.generateKey(256);
                        //set the controller
                        cryptController = new EncrypterController(key, paths);

                        /*
                        * create a message for the user to display the b64 encoded key so they can save it
                        * */
                        String b64KeyString = Base64.getEncoder().encodeToString(key.getEncoded());
                        Scanner scan = new Scanner(System.in);
                        String keyMessage = """
                            You did not specify a key for encryption. One has been generated for you. Please store this 
                            key securely for reuse upon decryption.
                            AES 256-bit key: \n""" + b64KeyString;
                        System.out.println(keyMessage + "\n");
                        System.out.println("Please press [ENTER] to continue after saving your key. ");
                        scan.nextLine();

                        //execute the controller
                        cryptController.execute();

                    }catch(NoSuchAlgorithmException | InvalidParameterException e){
                        System.out.println(e.getMessage());
                    }
                //key path is valid
                }else{

                    String encKeyStr = KeyCreator.readEncodedKeyFromFile(keyPath);
                    key = KeyCreator.generateSecretKeyFromUser(encKeyStr);
                    cryptController = new EncrypterController(key, paths);

                    cryptController.execute();
                }

            }else if (mode.equals("decrypt")){
                String keyStr = KeyCreator.readEncodedKeyFromFile(keyPath);
                key = KeyCreator.generateSecretKeyFromUser(keyStr);
                cryptController = new DecrypterController(key, paths);

                cryptController.execute();
            }

        }catch(RuntimeException  | IOException e){
            System.out.println(e.getMessage());
        }
    }


    //validate user arguments and that they are a valid configuration
    //returns the cipher mode
    private static String validateArgs(Namespace ns) throws RuntimeException {

        //validate that each file exists if the mode is set
        String mode = ns.getString("mode");

        if(mode != null){
            //verify that arguments were provided for either files or directories
            List<String> paths = ns.getList("paths");

            //if a mode is set the paths shouldn't be null
            if(paths == null){
                String message = """
                        Invalid argument specifications.
                        A mode was specified but no files or directories were provided.""";
                throw new RuntimeException(message);

            }

            //verify the paths or directories provided.
            verifyPaths(paths);
            //if mode decrypt is used make sure a key is provided for decryption
            if(mode.equals("decrypt") && ns.getString("key_file") == null){
                String message = """
                        Invalid argument specifications.
                        Decrypt mode was specified but no key was provided.""";
                throw new RuntimeException(message);
            }

            //validate the key file's existence
            //verifyPath(ns.getString("key_file"));



        }
        return mode;
    }

    //verify any paths passed by the user
    private static void verifyPaths(List<String> paths){
        for(String pathStr : paths){

            verifyPath(pathStr);

        }
    }

    //verify path helper
    private static void verifyPath(String pathStr)throws InvalidPathException{
        Path path = Paths.get(pathStr);
        if(!Files.exists(path)){
            throw new InvalidPathException(pathStr, "Path  does not exist.");
        }
        if(!Files.isReadable(path)){
            throw new InvalidPathException(pathStr,  "Path is not readable.");
        }
        if (!Files.isWritable(path)) {
            throw new InvalidPathException(pathStr,  "Path is not writable.");
        }

    }



    private static Namespace getNamespace(String[] args){
        //get the parser and init a namespace to null
        ArgumentParser parser = getArgParser();
        Namespace ns = null;

        //display help by default
        if(args.length == 0){
            parser.printHelp();
            System.exit(0);
        }
        //try to get the namespace
        try{

            ns = parser.parseArgs(args);

        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }

        return ns;

    }

    private static ArgumentParser getArgParser(){
        ArgumentParser ap = ArgumentParsers.newFor("Cryptinator")

                .build()
                .description("Encrypt or decrypt files and directories.")
                .defaultHelp(true);




        ap.addArgument("-m", "--mode")
                .choices("encrypt", "decrypt")
                .help("cipher mode")
                .required(false);

        ap.addArgument("-p", "--paths")
                .nargs("*")
                .help("List of file(s) or directory(ies) to encrypt.")
                .required(false);

        ap.addArgument("--generate-key")
                .type(Integer.class)
                .setDefault()
                .required(false)
                .choices(128, 192, 256)
                .help("Generate a key. Supports 128, 192, and 256 bit AES keys.");

        ap.addArgument("--key-file")
                .nargs(1)
                .help("Read a private key from a file.");




        /*
        * Need to add args:
        * key args
        * - accept keyfile paths for long keys like rsa
        * -- accept base64 input for smaller keys like RSA or DES?
        * -- allow for user requested keygen - needs to work perfectly.
        * */
        return ap;
    }
}
