package cryptinators;

import javax.crypto.SecretKey;

public class Decryptinator extends Cryptinator {


    public Decryptinator(String path, SecretKey key){
        super(path, key);
    }
    public void run(){
        System.out.println("Here.");
    }

}
