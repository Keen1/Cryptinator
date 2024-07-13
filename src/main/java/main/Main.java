package main;
import net.sourceforge.argparse4j.*;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;



public class Main {
    public static void main(String[] args){
        ArgumentParser parser = getArgParser();
        Encryptinator encryptinator = new Encryptinator();
        try{
            System.out.println(parser.parseArgs(args));

        }catch(ArgumentParserException e){
            //System.out.println(e.getMessage());
            parser.handleError(e);
            System.exit(1);
        }


    }
    public static ArgumentParser getArgParser(){
        ArgumentParser ap = ArgumentParsers.newFor("Encryptinator")

                .build()
                .description("Encrypt or decrypt files and directories.")
                .defaultHelp(true);
        //encrypt decrypt args
        ap.addArgument("-e", "--encrypt");
        ap.addArgument("-d", "--decrypt");

        //file args
        ap.addArgument("-f")
                .nargs("*")
                .help("List of files to encrypt.");
        ap.addArgument("-dirs")
                .nargs("*")
                .help("List of directories to encrypt.");
        return ap;
    }
}
