package security.general.messagedigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 *
 * @see java.security.MessageDigest
 * MD2
 * MD5
 * SHA-1
 * SHA-224
 * SHA-256
 * SHA-384
 * SHA-512
 * SHA-512/224
 * SHA-512/256
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 3:53
 */
public class MessageDigestDemo {

    public static void main(String[] args) {
        String input = "123456";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512/256");
            messageDigest.update(input.getBytes());
            byte[] content = messageDigest.digest();

            messageDigest.update(input.getBytes());
            byte[] content2 = messageDigest.digest();
//            byte[] content = messageDigest.digest(input.getBytes());
            System.out.println(Base64.getEncoder().encodeToString(content));
            System.out.println(Base64.getEncoder().encodeToString(content2));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
