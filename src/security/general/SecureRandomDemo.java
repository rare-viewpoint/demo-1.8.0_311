package security.general;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 *
 * @see SecureRandom
 * NativePRNG
 * NativePRNGBlocking
 * NativePRNGNonBlocking
 * PKCS11
 * SHA1PRNG
 * Windows-PRNG
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 4:15
 */
public class SecureRandomDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        // SHA1PRNG, Windows-PRNG
//        int size = 16;
        int size = 64;
        SecureRandom random =  SecureRandom.getInstance("SHA1PRNG");
//        System.out.println(random.getProvider().getServices());
         byte[] send = random.generateSeed(size);
         System.out.println("种子："+ Base64.getEncoder().encodeToString(send));
         random.setSeed(send);
         byte[] content = new byte[size];
        for (int i = 0; i < 10; i++) {
            random.nextBytes(content);
            System.out.println("随机数"+i+"："+Base64.getEncoder().encodeToString(content));
        }



//        random.setSeed(System.currentTimeMillis());
//        byte[] content = new byte[20];
//        random.nextBytes(content);
//        System.out.println(Base64.getEncoder().encodeToString(content));
    }
}
