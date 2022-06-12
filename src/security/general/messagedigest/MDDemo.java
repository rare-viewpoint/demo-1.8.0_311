package security.general.messagedigest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @see java.security.MessageDigest
 * MD2
 * MD5
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/3 6:56
 */
public class MDDemo {

    public static void main(String[] args) {
        try {
//            MessageDigest digest = MessageDigest.getInstance("MD2");
            MessageDigest digest = MessageDigest.getInstance("MD5");
            System.err.println("消息摘要："+ digest.toString());
            byte[] d = digest.digest("shm123".getBytes());
            System.out.println("加密："+Base64.getEncoder().encodeToString(d));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

}
