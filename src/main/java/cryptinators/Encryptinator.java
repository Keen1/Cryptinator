package cryptinators;


import javax.crypto.SecretKey;

public class Encryptinator extends Cryptinator {


    public Encryptinator(String path, SecretKey key){
        super(path, key);
    }
    @Override
    public void run(){
        SecretKey key = this.getKey();
        System.out.println(key.getAlgorithm());

    }

}
