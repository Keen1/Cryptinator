package main;

import java.util.List;

public class Encryptinator {
    private String mode;
    private String path;
    private List<String> paths;

    public  Encryptinator(){

    }

    public Encryptinator(String mode, String path){
        this.mode = mode;
        this.path = path;
    }
    public Encryptinator(String mode, List<String> paths){
        this.mode = mode;
        this.paths = paths;
    }

    //getters

    public String getMode(){
        return this.mode;
    }

    public String getPath(){
        return this.path;
    }

    public List<String> getPaths(){
        return this.paths;
    }


    //setters

    public void setMode(String mode){
        this.mode = mode;
    }

    public void setPath(String path){
        this.path = path;
    }

    public void setPaths(List<String> paths){
        this.paths = paths;
    }



}
