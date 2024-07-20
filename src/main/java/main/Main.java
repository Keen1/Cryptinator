package main;

import cryptinators.Cryptinator;
import cryptinators.Encryptinator;
import net.sourceforge.argparse4j.*;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    public static void main(String[] args){
        Namespace namespace = init(args);
        System.out.println(namespace);
        try{
            validateArgs(namespace);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }




    }

    //validate user arguments and that they are a valid configuration
    private static void validateArgs(Namespace ns) throws RuntimeException {
        //valid key lengths currently supported

        String mode = ns.getString("mode");
        if(mode != null){
            //verify that arguments were provided for either files or directories
            List<String> paths = ns.getList("paths");
            System.out.println(paths);
            if(paths == null){
                String message = """
                        Invalid argument specifications.
                        A mode was specified but no files or directories were provided.""";
                throw new RuntimeException(message);

            }
            //verify the paths or directories provided.
            verifyPaths(paths);

        //if the mode isn't present then at LEAST a utility like --generate-key should be used
        //otherwise the config is invalid.
        }else{

            Integer keyLength = ns.getInt("generate_key");

        }
    }

    //verify any paths passed by the user
    private static void verifyPaths(List<String> paths)throws InvalidPathException{
        for(String pathStr : paths){
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
    }



    private static Namespace init(String[] args){
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
        //encrypt decrypt args


        //file args
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

        ap.addArgument("-m", "--mode")
                .choices("encrypt", "decrypt")
                .help("cipher mode")
                .required(false);


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
