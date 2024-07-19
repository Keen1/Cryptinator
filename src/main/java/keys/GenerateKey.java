package keys;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

public class GenerateKey {
    protected int length;
    public GenerateKey(){
        this.length = 256;
    }
    public GenerateKey(int length){
        this.length = length;
    }

    public SecretKey getKey(KeyGenerator keyGen){
        keyGen.init(this.length);
        return keyGen.generateKey();
    }



    public String encodeKey(SecretKey key){
        Base64.Encoder b64Encode = Base64.getEncoder();
        return b64Encode.encodeToString(key.getEncoded());
    }
    public byte[] decodeKey(String encodedKey){
        Base64.Decoder b64Decode = Base64.getDecoder();
        return b64Decode.decode(encodedKey);
    }
}
