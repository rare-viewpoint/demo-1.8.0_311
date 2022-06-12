package security.general.keygenerator;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @see javax.crypto.KeyGenerator
 * AES
 * ARCFOUR
 * Blowfish
 * DES
 * DESede
 * HmacMD5
 * HmacSHA1
 * HmacSHA224
 * HmacSHA256
 * HmacSHA384
 * HmacSHA512
 * RC2
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 1:43
 */
public class KeyGeneratorDemo {

    static String[] algorithms = new String[]{
            "AES"
            ,"ARCFOUR"
            ,"Blowfish"
            ,"DES"
            ,"DESede"
            ,"HmacMD5"
            ,"HmacSHA1"
            ,"HmacSHA224"
            ,"HmacSHA256"
            ,"HmacSHA384"
            ,"HmacSHA512"
            ,"RC2"

    };

    static String generateKey(String algorithm){
        KeyGenerator generator = null;
        byte[] contents = null;
        try {
            generator = KeyGenerator.getInstance(algorithm);
            SecretKey key = generator.generateKey();
            contents = key.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(contents);
    }

    public static void main(String[] args) {

        System.out.println("秘钥生成器👇");
        for (String algorithm : algorithms) {
            System.out.println(String.format("%s：%s",algorithm,generateKey(algorithm)));
        }
    }
}
