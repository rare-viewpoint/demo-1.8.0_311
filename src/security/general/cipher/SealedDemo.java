package security.general.cipher;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @see javax.crypto.SealedObject
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/7 6:57
 */
public class SealedDemo {

    final static String ALG = "AES";
    final static String KEY = "4BPl1PhmXPLnYp0bRqr4jw==";

    public static void main(String[] args) {


        try {
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(KEY), ALG);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, spec);

            SealedObject so = new SealedObject("This is a demo", cipher);

            System.out.println(so.getObject(spec));

            cipher.init(Cipher.DECRYPT_MODE, spec);
            System.out.println(so.getObject(cipher));



        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
