package security.general.mac;

import util.PrintUtil;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

/**
 *
 * @see javax.crypto.Mac
 * HmacMD5
 * HmacSHA1
 * HmacSHA224
 * HmacSHA256
 * HmacSHA384
 * HmacSHA512
 * PBEWith<mac> 例如PBEWithHmacSHA1
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 3:25
 */
public class MacDemo {

    final static String ALG = "HmacSHA1";

//    public static void main(String[] args) {
//        try {
//            Mac mac = Mac.getInstance(ALG);
////            KeyGenerator generator = KeyGenerator.getInstance(ALG);
////            generator.init(256);
////            Key key = generator.generateKey();
////            System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
//
//            Key key = new SecretKeySpec("3aq/1XZIVmD0bTHnq4AkwgLBWHJt5F7MrQa0mlQ1sW0=".getBytes(), ALG);
//            mac.init(key);
//            System.out.println("请输入消息👇");
//            PrintUtil.circleScanner(line -> {
//                byte[] result =  mac.doFinal(line.getBytes());
//                System.out.println("生成Mac: "+ Base64.getEncoder().encodeToString(result));
//                System.out.println("请输入消息👇");
//            });
//
//        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
            KeyGenerator generator = KeyGenerator.getInstance(ALG);
            generator.init(256);
            Key key = generator.generateKey();
            System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
    }
}
