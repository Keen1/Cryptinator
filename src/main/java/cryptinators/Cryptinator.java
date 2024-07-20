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
    //key helper methods
    public String encodeKey(SecretKey key){
        return Base64.getEncoder().encodeToString(key.getEncoded());


    }
    public byte[] decodeKey(String encodedKey){
        return Base64.getDecoder().decode(encodedKey);
    }
    public String readEncodedKeyFromFile(String path) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(path));
        return new String(content);
    }

    public SecretKey generateSecretKeyFromUser(String encodedKey){
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");

    }
    public SecretKey generateKey(int keySize) throws NoSuchAlgorithmException, InvalidParameterException{
        if(keySize != 128 && keySize != 192 && keySize != 256){
            throw new InvalidParameterException("Invalid key size for AES. Key size must be 128, 192, or 256 bits.");

        }
        //this will need to be parameterized if we wish to support diff algorithms
        KeyGenerator keyGen  = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        return keyGen.generateKey();

    }





    //abstract override method for run
    @Override
    public abstract void run();


}
