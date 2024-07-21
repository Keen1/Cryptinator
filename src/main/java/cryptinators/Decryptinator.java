package cryptinators;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Decryptinator extends Cryptinator {


    public Decryptinator(String path, SecretKey key){
        super(path, key);
    }
    public void run(){
        IvParameterSpec ivParam = null;

        try(FileInputStream fileInputStream = new FileInputStream(this.getPath())){

            //read the iv from the beginning of the file
            byte[] iv = new byte[16];

            if(fileInputStream.read(iv) != iv.length){
                throw new IOException("IV not read.");
            }
            //set the ivparam obj
            //init the cipher
            //set the streams and decrypt
            ivParam = new IvParameterSpec(iv);
            Cipher cipher = initCipher(ivParam);


            try(FileOutputStream fileOutputStream = new FileOutputStream(this.getPath().replace(".enc", ""))){
                CipherInputStream cipherInputStream = new CipherInputStream(fileInputStream, cipher);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while((bytesRead = cipherInputStream.read(buffer)) != -1){
                    fileOutputStream.write(buffer,0,bytesRead);
                }

                fileOutputStream.flush();
            }

        }catch(IOException e){
            System.out.println(e.getMessage());
        } catch (InvalidAlgorithmParameterException | NoSuchPaddingException | InvalidKeyException |
                 NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }




    public Cipher initCipher(IvParameterSpec ivParam) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, this.getKey(), ivParam);
        return cipher;
    }

}
