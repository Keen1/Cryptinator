package main;

import net.sourceforge.argparse4j.*;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;
import java.util.List;


public class Main {
    public static void main(String[] args){
        init(args);

    }



    private static void init(String[] args){
        String mode, path, dir;
        List<String> paths, dirs;
        ArgumentParser parser = getArgParser();
        Namespace ns = null;
        try{
            ns = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
    private static ArgumentParser getArgParser(){
        ArgumentParser ap = ArgumentParsers.newFor("Encryptinator")

                .build()
                .description("Encrypt or decrypt files and directories.")
                .defaultHelp(true);
        //encrypt decrypt args


        //file args
        ap.addArgument("-f")
                .nargs("*")
                .help("List of files to encrypt.")
                .required(false);
        ap.addArgument("-dirs")
                .nargs("*")
                .help("List of directories to encrypt.")
                .required(false);
        ap.addArgument("--generate-key")
                .type(Integer.class)
                .setDefault(256)
                .required(false);
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
