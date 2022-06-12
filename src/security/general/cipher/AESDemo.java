package security.general.cipher;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Scanner;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/2 22:40
 */
public class AESDemo {

//    private static final String SECRET_KEY = "Kj1EW0MU6rbL5Ua4";
    // "算法/模式/补码方式"
    //AES 默认 ECB 和 PKCS5Padding
//    private static final String ALGORITHM = "AES"; //默认电码本模式
//    private static final String ALGORITHM = "AES/ECB/PKCS5Padding"; //电码本模式
    private static final String ALGORITHM = "AES/CBC/PKCS5Padding"; //密码分组链接模式
//    private static final String ALGORITHM = "AES/CTR/PKCS5Padding"; //计数器模式
//    private static final String ALGORITHM = "AES/CFB/PKCS5Padding"; // 密文反馈模式
//    private static final String ALGORITHM = "AES/OFB/PKCS5Padding"; // 输出反馈模式
    private static final String KEY_SPEC = "AES";
    private static final String IV_PARAM = "Kj1EW0MU6rbL5Ua4";



    public static void main(String[] args) throws Exception {
        String secretKey = randomAesKey();
        System.err.println("算法/模式/补码方式："+ ALGORITHM);
        System.err.println("秘钥: "+ secretKey);
        System.out.println("输入明文👇");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String encrypt = encrypt(line,secretKey);
            System.out.println("密文："+ encrypt);
            System.out.println("解密："+ decrypt(encrypt, secretKey));
            System.out.println("======================================");
            System.out.println("输入明文👇");
        }
    }

    static String randomAesKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[12];
        random.nextBytes(key);
        return new BASE64Encoder().encode(key);
    }

    /**
     * AES 加密
     * @param src 明文
     * @param key 秘钥
     * @return 密文
     */
    static String encrypt(String src, String key){
        if(key.length() != 16){
            System.err.println("key 长度不是 16");
            return null;
        }
        byte[] raw = key.getBytes();
        String ret = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher =  Cipher.getInstance(ALGORITHM);
            String[] als = ALGORITHM.split("/");
            String mode = als[0];
            if(als.length > 1)
                mode = als[1];
            switch (mode){
                case "CBC": //密文分组链接模式
                case "CTR":// 计数器模式
                case "CFB"://密码反馈模式
                case "OFB"://输出反馈模式
                {
                    IvParameterSpec ivSpec = new IvParameterSpec(IV_PARAM.getBytes());
                    cipher.init(Cipher.ENCRYPT_MODE,spec, ivSpec);
                    break;
                }
                case "ECB":// 电码本模式
                default: //默认电码本
                    cipher.init(Cipher.ENCRYPT_MODE, spec);
            }


            byte[] encrypted =  cipher.doFinal(src.getBytes());
            ret =  Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * AES 解密
     * @param src 密文
     * @param key 秘钥
     * @return 明文
     */
    static String decrypt(String src, String key){
        if(key.length() != 16){
            System.err.println("key 长度不是 16");
            return null;
        }
        byte[] raw = key.getBytes();
        String ret = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(raw, KEY_SPEC);
            Cipher cipher =  Cipher.getInstance(ALGORITHM);

            String[] als = ALGORITHM.split("/");
            String mode = als[0];
            if(als.length > 1)
                mode = als[1];
            switch (mode) {
                case "CBC": //密文分组链接模式
                case "CTR":// 计数器模式
                case "CFB"://密码反馈模式
                case "OFB"://输出反馈模式
                {
                    //需要一个初始IV参数
                    IvParameterSpec ivSpec = new IvParameterSpec(IV_PARAM.getBytes());
                    cipher.init(Cipher.DECRYPT_MODE,spec, ivSpec);
                    break;
                }
                case "ECB":// 电码本模式
                default: //默认电码本
                    cipher.init(Cipher.DECRYPT_MODE, spec);
            }

            byte[] decrypted =  cipher.doFinal(Base64.getDecoder().decode(src));
            ret =  new String(decrypted);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;

    }
}
