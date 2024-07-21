package keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class KeyCreator {

    //key helper methods
    public static String readEncodedKeyFromFile(String path) throws IOException {
        byte[] content = Files.readAllBytes(Paths.get(path));
        return new String(content);
    }

    public static SecretKey generateSecretKeyFromUser(String encodedKey){
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, "AES");

    }
    public static SecretKey generateKey(int keySize) throws NoSuchAlgorithmException, InvalidParameterException {
        if(keySize != 128 && keySize != 192 && keySize != 256){
            throw new InvalidParameterException("Invalid key size for AES. Key size must be 128, 192, or 256 bits.");

        }
        //this will need to be parameterized if we wish to support diff algorithms
        KeyGenerator keyGen  = KeyGenerator.getInstance("AES");
        keyGen.init(keySize);
        return keyGen.generateKey();

    }


}
