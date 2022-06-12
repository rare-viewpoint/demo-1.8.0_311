package security.general.messagedigest;

import java.security.*;
import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @see java.security.MessageDigest
 * @see java.security.Provider
 * @apiNote
 * SHA
 * SHA-224
 * SHA-256
 * SHA-384
 * SHA-512
 * SHA-512/224
 * SHA-512/256
 * @Author shm
 * @Date 2021/12/3 5:51
 */
public class SHADemo {

    private static final String[] ALGORITHMS = new String[]{
            "SHA",
            "SHA-224",
            "SHA-256",
            "SHA-384",
            "SHA-512",
            "SHA-512/224",
            "SHA-512/256"
    };

    public static void main(String[] args) {

        System.err.println("请输入明文👇");

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            digestDemo(line);
            System.err.println("请输入明文👇");
        }
    }

    static void digestDemo(String msg){
        System.out.println("=============================================");
        System.out.println("明文："+msg);
        try {
            for (String algorithm : ALGORITHMS) {
                MessageDigest digest = MessageDigest.getInstance(algorithm);
                byte[] d = digest.digest(msg.getBytes());
                System.out.println("密文("+algorithm+")："+ Base64.getEncoder().encodeToString(d));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println("===============================================");
    }
}
