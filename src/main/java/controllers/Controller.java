package controllers;

import javax.crypto.SecretKey;
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

    public abstract void execute();



}
