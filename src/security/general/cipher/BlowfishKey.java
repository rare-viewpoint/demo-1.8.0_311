package security.general.cipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/8 6:43
 */
public class BlowfishKey {

    public static void main(String[] args) throws Exception {

        String alg = "Blowfish";
        KeyGenerator kGen = KeyGenerator.getInstance(alg);
        SecretKey secretKey = kGen.generateKey();
        byte[] raw = secretKey.getEncoded();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, alg);

        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] plain = "this is just an example".getBytes();
        byte[] encrypted = cipher.doFinal(plain);
        System.out.println(Base64.getEncoder().encodeToString(plain));
        System.out.println(Base64.getEncoder().encodeToString(encrypted));
    }
}
