package security.general.cipher;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.*;
import java.util.Base64;
import java.util.Scanner;

/**
 *
 * @see Cipher 密码
 * @see KeyFactory 密钥工厂
 * @see PublicKey 公钥
 * @see PrivateKey 私钥
 * @see PKCS8EncodedKeySpec 私钥描述
 * @see X509EncodedKeySpec 公钥描述
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/3 9:11
 */
public class RSADemo {

    private final static String ALG = "RSA";

    //由密钥对KeyPairGenerator生成
    private final static String PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIPvM2DncrayF/yUzrpVFQcnAsTYh0GKXPj8FD2nA2Rv7WH5EgkWu0Q5p/iRbhuHZ+AJQ/aC4U+sH6XfkklJqbcCAwEAAQ==";
    private final static String PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAg+8zYOdytrIX/JTOulUVBycCxNiHQYpc+PwUPacDZG/tYfkSCRa7RDmn+JFuG4dn4AlD9oLhT6wfpd+SSUmptwIDAQABAkAN8WOOpCcdY6jhSqCmVIOKY/cI8pEovgShU5KYCWR0YrA+Qu1pyrndlXcihxCO8POsLajKaecm9DXPvFKgmnhBAiEA3ajLbyTuSAxkd9M0P6snfX8uFr+zLefsyt+UaSUYBQcCIQCYX9Wau6RvER8DDWe2Yrd+QlmgFv+LwAlgA1mi7zk50QIhAL30YFpgOL+Pty2k1rrVTyNSMUoXgFMP1TJKhiHRQxNTAiBDO33voT3jt+U8Ho0Vf6dBMZuENMGa8yfv0mqZpv5UEQIgINO5dOwdhvVSdjj3iRuRpldmyxYc/ZcLG16CooGesEs=";

//    public static void main(String[] args) {
//        System.out.println("RSA加密解密");
//        System.out.println("请输入明文👇");
//        Scanner scanner = new Scanner(System.in);
//        while (scanner.hasNextLine()){
//            String line = scanner.nextLine();
//            String encryptStr = encryptByPublicKey(line);
//            System.out.println("=============================================================================================");
//            System.out.println("RSA公钥加密："+encryptStr);
//            String decryptStr = decryptByPrivateKey(encryptStr);
//            System.out.println("RSA私钥解密："+ decryptStr);
//            System.out.println("----------------------------------------------------------------------------------------------");
//            String encryptStr2 = encryptByPrivateKey(line);
//            System.out.println("RSA私钥加密："+encryptStr2);
//            String decryptStr2 = decryptByPublicKey(encryptStr2);
//            System.out.println("RSA公钥解密："+ decryptStr2);
//            System.out.println("==============================================================================================");
//        }
//
//    }

    public static void main(String[] args) {
        String pubKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4OExcpl7Hz+elx7dz6ng117jb77Bm0imG+Ty9HstzQdFSFJvxGdL0MhC0PETKn/zENf+RKZHvx1/rw/0uzEkd9CTnheIqcaTFdq+T36zIhhStAtW1fIWZsFp7ifZsRNbL8zVXuavSBzx9cgEhr7/GvRN+Nu1Pdxw3HWTaHSTTHwIDAQAB";

        String priKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALzGcqb7y4Rhm3tLGp2X5F1o4qtd2OdQH7UoFaoa8SElClTD7evxok0QX7TneFbIE8AiijcwOGdsLkzBuB7rSmve9+XozkVGZ6zqhpadParIAPuxmaqTM1g6u/Wo5G8eUi1+QOuG1";
        System.out.println(decryptByPrivateKey("GSAzrvfqDPwOAbGJl7bfRHv0SmwX3Sy6TSMioAvaNJei_ty7aYrfBK1qWhGTSB5AssUUVCVuKnXpT7025R_YMW_owqdUxyUrnB9TWiGaptLEa4Y4EabvVAg3iZbgPouC0S2Om3zjEl15xMcHJjPD83DbEKTYbssHXoPDXyDL5D8",
                pubKey));
    }

    //1-1 公钥加密
    static String encryptByPublicKey(String src){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALG);
            byte[] pKey = Base64.getDecoder().decode(PUBLIC_KEY);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] content = cipher.doFinal(src.getBytes());
            return Base64.getEncoder().encodeToString(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //1-2 私钥解密
    static String decryptByPrivateKey(String src, String privateKeyStr){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALG);
            byte[] pKey = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] content = cipher.doFinal(Base64.getDecoder().decode(src));
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //2-1 私钥加密
    static String encryptByPrivateKey(String src){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALG);
            byte[] pKey = Base64.getDecoder().decode(PRIVATE_KEY);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pKey);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] content = cipher.doFinal(src.getBytes());
            return Base64.getEncoder().encodeToString(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //2-2 公钥解密
    static String decryptByPublicKey(String src, String pubicKeyStr){
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALG);
            byte[] pKey = Base64.getDecoder().decode(pubicKeyStr);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pKey);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(ALG);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] content = cipher.doFinal(Base64.getDecoder().decode(src));
            return new String(content);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
