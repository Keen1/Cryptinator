package cryptinators;

import javax.crypto.SecretKey;


//abstract cryptinator class
//encryptinator and decryptinator extend this class and only implement the run() method.
public abstract class Cryptinator implements Runnable{
    //vars
    private String path;
    private SecretKey key;

    //constructors
    Cryptinator(String path, SecretKey key){
        this.path = path;
        this.key = key;
    }


    //getters
    public String getPath(){
        return this.path;
    }

    public SecretKey getKey(){
        return this.key;
    }






    //abstract override method for run
    @Override
    public abstract void run();


}
