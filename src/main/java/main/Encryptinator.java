package main;

import java.util.List;

public class Encryptinator {
    private String mode;
    private String path;
    private List<String> paths;

    //empty constructor
    public Encryptinator(){

    }
    /*overloads
    * any constructor call includes at least a mode and one of the following
    * 1. a single file to encrypt or decrypt
    * 2. a list of files to encrypt or decrypt
    */

    //mode and file
    public Encryptinator(String mode, String path){
        this.mode = mode;
        this.path = path;
    }

    //mode and files
    public Encryptinator(String mode, List<String> paths){
        this.mode = mode;
        this.paths = paths;
    }


    //getters

    protected String getMode(){
        return this.mode;
    }

    protected String getPath(){
        return this.path;
    }

    protected List<String> getPaths(){
        return this.paths;
    }

    //setters
    protected void setMode(String mode){
        this.mode = mode;
    }

    protected void setPath(String path){
        this.path = path;
    }

    protected void setPaths(List<String> paths){
        this.paths = paths;
    }






}
