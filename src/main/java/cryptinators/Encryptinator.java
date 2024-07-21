package cryptinators;


import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Encryptinator extends Cryptinator {


    public Encryptinator(String path, SecretKey key){
        super(path, key);
    }
    @Override
    public void run(){
        IvParameterSpec ivParam = this.getIvParameters();
        //set the input file stream
        try(FileInputStream fileInputStream = new FileInputStream(this.getPath());
            //set the file and cipher output streams
            FileOutputStream fileOutputStream = new FileOutputStream(this.getPath() + ".enc");
            CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, initCipher(ivParam))){

            fileOutputStream.write(ivParam.getIV());
            byte[] buffer = new byte[4096];

            int bytesRead;
            while((bytesRead = fileInputStream.read(buffer)) != -1){
                cipherOutputStream.write(buffer,0,bytesRead);
            }
            //flush output streams and delete original file.
            cipherOutputStream.flush();
            fileOutputStream.flush();
            Files.delete(Paths.get(this.getPath()));




        } catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }


    }
    //get iv param spec object.
    public IvParameterSpec getIvParameters(){
        byte[] iv = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    //init the cipher
    public Cipher initCipher(IvParameterSpec ivSpec) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, this.getKey(), ivSpec);
        return cipher;
    }

}
