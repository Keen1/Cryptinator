package cryptinators;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

//abstract cryptinator class
//encryptinator and decryptinator extend this class and only implement the run() method.
public abstract class Cryptinator implements Runnable{
    //vars
    private String path;
    private SecretKey key;

    //constructors
    Cryptinator(){

    }
    Cryptinator(String path, SecretKey key){
        this.path = path;
        this.key = key;
    }


    //path setters and getters
    public void setPath(String path){
        this.path = path;
    }
    public String getPath(){
        return this.path;
    }

    //key getters and setters
    public void setKey(SecretKey key){
        this.key = key;
    }
    public SecretKey getKey(){
        return this.key;
    }






    //abstract override method for run
    @Override
    public abstract void run();


}
