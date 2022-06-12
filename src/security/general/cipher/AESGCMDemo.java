package security.general.cipher;

import util.PrintUtil;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/7 3:13
 */
public class AESGCMDemo {

    final static String ALG = "AES/GCM/NoPadding";

    final static int BYTE_SIZE = 16;
    //128, 120, 112, 104, 96
    final static int T_LEN =  112;
    static byte[] randomKey(){
        byte[] iv = new byte[BYTE_SIZE];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(secureRandom.generateSeed(BYTE_SIZE));
        secureRandom.nextBytes(iv);
        return iv;
    }

    // 加密/解密模式
    static void demo(){
        byte[] add = randomKey();
        byte[] key = randomKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        byte[] iv = randomKey();
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(T_LEN, iv);
        System.out.println("请输入明文👇");
        PrintUtil.circleScanner(line -> {
            try {
                Cipher cipher = Cipher.getInstance(ALG);
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, gcmParameterSpec);
                cipher.updateAAD(add);//++++++++++++++++++++++++++++
                cipher.update(line.getBytes());
                byte[] cipherText = cipher.doFinal();
                System.out.println("密文："+ Base64.getEncoder().encodeToString(cipherText));
                System.out.println(cipher.getBlockSize());
//                System.out.println(cipher.getOutputSize(16));


                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, gcmParameterSpec);
                cipher.updateAAD(add); //++++++++++++++++++++++++++++
                byte[] plainText = cipher.doFinal(cipherText);

                System.out.println("解密："+ new String(plainText));

                System.out.println("请输入明文👇");

            } catch (Exception e) {
                e.printStackTrace();
            }

        });

    }

    //对Key的包装/解包
    static void demo2() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128);
        SecretKey secretKey = generator.generateKey();
        System.out.println("秘钥："+ Base64.getEncoder().encodeToString(secretKey.getEncoded()));



        byte[] key = randomKey();
        byte[] iv = randomKey();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(T_LEN, iv);

        Cipher cipher = Cipher.getInstance(ALG);

        cipher.init(Cipher.WRAP_MODE, secretKeySpec, gcmParameterSpec);
        byte[] wrapKey = cipher.wrap(secretKey);
        System.out.println("包装Key: "+ Base64.getEncoder().encodeToString(wrapKey));

        cipher.init(Cipher.UNWRAP_MODE, secretKeySpec, gcmParameterSpec);
        //public final Key unwrap(byte[] wrappedKey, String wrappedKeyAlgorithm, int wrappedKeyType));
        SecretKey unWrapKey = (SecretKey) cipher.unwrap(wrapKey, "AES", Cipher.SECRET_KEY);
        System.out.println("解包Key: "+ Base64.getEncoder().encodeToString(unWrapKey.getEncoded()));

    }

    public static void main(String[] args) throws Exception {
        demo();
//        demo2();




    }
}
