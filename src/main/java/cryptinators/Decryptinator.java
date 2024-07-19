package cryptinators;

import javax.crypto.SecretKey;

public class Decryptinator extends Cryptinator {


    public Decryptinator(String path, SecretKey key){
        super(path, key);
    }
    public void run(){
        System.out.println("My class: " + this.getClass());
        System.out.println(this.getPath());
        System.out.println(Thread.currentThread());
        System.out.println(Thread.activeCount());
    }

}
