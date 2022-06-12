package security.general.cipher.file;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @see javax.crypto.CipherInputStream
 * @see javax.crypto.CipherOutputStream
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/7 5:37
 */
public class StreamDemo {

    final static String plainFile = "F:\\dev\\workspace\\demo-1.8.0_311\\src\\security\\cipher\\file\\plain";
    final static String plainFile2 = "F:\\dev\\workspace\\demo-1.8.0_311\\src\\security\\cipher\\file\\plain2";
    final static String cipherFile = "F:\\dev\\workspace\\demo-1.8.0_311\\src\\security\\cipher\\file\\cipher";
    final static String ALG = "AES";
    final static String KEY = "4BPl1PhmXPLnYp0bRqr4jw==";

    static String randomAesKey(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }

    static void inputDemo(){

        FileInputStream fis = null;
        FileOutputStream fos = null;
        CipherInputStream cis = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(KEY), ALG);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, spec);
            fis = new FileInputStream(plainFile);
            cis = new CipherInputStream(fis, cipher);
            fos = new FileOutputStream(cipherFile);
            byte[] b = new byte[cipher.getBlockSize()];
//            byte[] b = new byte[8];
            int i = cis.read(b);
            while (i != -1){
                fos.write(b, 0, i);
                i = cis.read(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cis != null){
                try {
                    cis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static void outputDemo(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        CipherOutputStream cos = null;
        try {
            SecretKeySpec spec = new SecretKeySpec(Base64.getDecoder().decode(KEY), ALG);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.DECRYPT_MODE, spec);
            fos = new FileOutputStream(plainFile2);

            fis = new FileInputStream(cipherFile);
//            cos = new CipherOutputStream(fos, cipher);
            cos = new CipherOutputStream(System.out, cipher);

            byte[] b = new byte[cipher.getBlockSize()];
//            byte[] b = new byte[8];
            int i = fis.read(b);
            while (i != -1){
                cos.write(b, 0, i);
                i = fis.read(b);

            }
            cos.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (cos != null){
                try {
                    cos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
//        System.out.println(randomAesKey());
//        inputDemo();
        outputDemo();
    }
}
