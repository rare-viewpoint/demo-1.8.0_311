package security.general.keyagreement;

import javax.crypto.KeyAgreement;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * @see javax.crypto.KeyAgreement
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/7 9:41
 */
public class KeyAgreementDemo {

//    final static String ALG = "DH";
    final static String ALG = "DH";

    public static void main(String[] args) {
        try {

            KeyPairGenerator aliceKpairGen = KeyPairGenerator.getInstance("DH");
            aliceKpairGen.initialize(512);
            KeyPair aliceKpair = aliceKpairGen.generateKeyPair();
            byte[] alicePublicKey = aliceKpair.getPublic().getEncoded();
            System.out.println("Alice PublicKey: "+Base64.getEncoder().encodeToString(alicePublicKey));


            // KeyPairGenerator BobKpairGen = KeyPairGenerator.getInstance("DH");
//            BobKpairGen.initialize(512);
            KeyPair bobKpair = aliceKpairGen.generateKeyPair();
            byte[] bobPublicKey = bobKpair.getPublic().getEncoded();
            System.out.println("Bob PublicKey: "+Base64.getEncoder().encodeToString(bobPublicKey));

//            KeyPairGenerator tKpairGen = KeyPairGenerator.getInstance("DH");
//            tKpairGen.initialize(512);
//            KeyPair tKpair = tKpairGen.generateKeyPair();

            KeyAgreement keyAgreement = KeyAgreement.getInstance(ALG);
            keyAgreement.init(aliceKpair.getPrivate());
//            keyAgreement.doPhase(bobKpair.getPublic(), true);
            keyAgreement.doPhase(getPublicKey(bobPublicKey), true);
            byte[] secretKey = keyAgreement.generateSecret();

            System.out.println("alice（Alice PrivateKey + Bob PublicKey）共享秘钥: "+ Base64.getEncoder().encodeToString(secretKey));

            KeyAgreement keyAgreement2 = KeyAgreement.getInstance(ALG);
            keyAgreement2.init(bobKpair.getPrivate());
//            keyAgreement2.doPhase(aliceKpair.getPublic(),true);
            keyAgreement2.doPhase(getPublicKey(alicePublicKey),true);
            byte[] secretKey2 = keyAgreement2.generateSecret();

            System.out.println("Bob（Bob privateKey + Alick PublicKey）共享秘钥: "+ Base64.getEncoder().encodeToString(secretKey2));

            System.out.println("共享秘钥是否一样："+ isEqual(secretKey, secretKey2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static PrivateKey getPrivateKey(byte[] privateKeyEnc) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALG);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyEnc);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    static PublicKey getPublicKey(byte[] publicKeyEnc) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(ALG);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeyEnc);
        return keyFactory.generatePublic(x509KeySpec);
    }

    // 如果相同，对应位异或为0，不为0，则不同.
    static boolean isEqual(byte[] a, byte[] b){
        if(a.length != b.length)
            return false;
       int result =  0;
        for (int i = 0; i < a.length; i++) {
            result |= a[i] ^ b[i];
        }

        return result == 0;
       
    }
}
