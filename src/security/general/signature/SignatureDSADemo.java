package security.general.signature;

import util.PrintUtil;
import sun.security.provider.DSAPrivateKey;
import sun.security.provider.DSAPublicKeyImpl;
import sun.security.util.DerValue;

import java.io.IOException;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

/**
 * 数据签名算法
 * @see java.security.Signature
 * NONEwithDSA
 * SHA1withDSA
 * SHA224withDSA
 * SHA256withDSA
 * SHA384withDSA
 * SHA512withDSA
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 5:13
 */
public class SignatureDSADemo {

    private final static String SIGNATURE_ALGORITHM = "SHA1withDSA";
    private final static String KEY_PAIR_ALGORITHM = "DSA";

    static KeyPair getKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_PAIR_ALGORITHM);
        keyPairGenerator.initialize(512);
        return keyPairGenerator.generateKeyPair();
    }

    static PrivateKey getPrivateKey(String priKey) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(priKey);
        DerValue derValue = new DerValue(bytes);
        return DSAPrivateKey.parseKey(derValue);
    }

    static PublicKey getPublicKey(String pubKey) throws IOException {
        byte[] bytes = Base64.getDecoder().decode(pubKey);
        DerValue derValue = new DerValue(bytes);
        return DSAPublicKeyImpl.parse(derValue);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, SignatureException, InvalidKeyException, IOException {

        System.out.println("签名算法: "+ SIGNATURE_ALGORITHM);

        KeyPair pair = getKeyPair();
        //class sun.security.general.provider.DSAPublicKeyImpl
        //class sun.security.general.provider.DSAPrivateKey
        PublicKey publicKey = pair.getPublic();
        String pubKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        PrivateKey privateKey = pair.getPrivate();
        System.out.println("公钥(Base64): "+ pubKey);
        String priKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        System.out.println("私钥(Base64): "+ priKey);
        System.out.println(publicKey.getClass());
        System.out.println(privateKey.getClass());


        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature.initSign(privateKey);
        signature.initSign(getPrivateKey(priKey));

        System.out.println("请输入签名的消息:");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNextLine()){
            String content = scanner.nextLine();
            signature.update(content.getBytes());
        }
//        scanner.close();
        byte[] bytes = signature.sign();
        System.out.println("私钥签名(Base64): "+ Base64.getEncoder().encodeToString(bytes));

        Signature signature2 = Signature.getInstance(SIGNATURE_ALGORITHM);
//        signature2.initVerify(publicKey);
        signature2.initVerify(getPublicKey(pubKey));

        System.out.println("请输入消息验证👇");
        PrintUtil.circleScanner(line ->{
            try {
                signature2.update(line.getBytes());
                boolean result = signature2.verify(bytes);
//                System.out.println("验证结果: "+result);
                if(result){
                    System.out.println("Success !!!");
                    System.exit(1);
                }else {
                    System.out.println("Failure !!!");
                    System.out.println("请输入消息验证👇");
                }
            } catch (SignatureException e) {
                e.printStackTrace();
            }

        });


//        util.PrintUtil.circleScanner( inStr -> {
//
//        });
    }
}
