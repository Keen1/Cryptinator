package controllers;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class Controller {
    private String mode;
    private SecretKey key;
    private List<String> paths;

    public Controller(SecretKey key, List<String> paths ){
        this.key = key;
        this.paths = paths;
    }

    //mode setters and getters
    public void setMode(String mode){
        this.mode = mode;
    }

    public String getMode(){
        return this.mode;
    }

    //key setter and getter
    public void setKey(SecretKey key){
        this.key = key;
    }

    public SecretKey getKey(){
        return this.key;
    }

    //paths getter and setter
    public List<String> getPaths(){
        return this.paths;
    }
    public void setPaths(List<String> paths){
        this.paths = paths;
    }

    //check if the path is a directory
    public boolean isDirectory(Path path){
        return Files.isDirectory(path);
    }

    //check the list of paths for directories
    public void checkForDirectories(){
        List<String> directoryPaths;
        for(String s : this.getPaths()){
            if(isDirectory(Paths.get(s))){
                try{
                    List<String> newPaths = getPathsFromDirectory(s);
                    this.getPaths().addAll(newPaths);
                }catch(IOException e){
                    System.out.println(e.getMessage());
                }

            }
        }



    }

    public List<String> getPathsFromDirectory(String directory)throws IOException{

        List<String> pathList = Files.list(Paths.get(directory)).map(Path::toString).toList();
        main.Main.verifyPaths(pathList);
        return pathList;


    }

    public abstract void execute();



}
