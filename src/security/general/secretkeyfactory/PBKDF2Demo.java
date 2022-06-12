package security.general.secretkeyfactory;


import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

/**
 * @Desc PBKDF2WithHmacSHA1 算法 加密密码和验证密码
 * @see SecretKeyFactory
 * @see KeySpec
 * @Author shm
 * @Date 2021/12/2 4:47
 */
public class PBKDF2Demo {

    private final static String ALGORITHM = "PBKDF2WithHmacSHA1";
    private final static int HASH_BYTE_LEN = 24;
    private final static int SALT_BYTE_LEN = 24;
    private final static int PBKDF2_ITERATION_COUNT = 10;
    private final static String PWD = "Table&123.";


    static String creatSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_LEN];
        random.nextBytes(salt);
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(salt);
    }

    static String createHash(String pwd, String salt){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] hash = pbkdf2(pwd.toCharArray(), decoder.decode(salt));
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(hash);
    }

    static boolean validPassword(String validPwd, String salt, String hash){
        String validHash = createHash(validPwd, salt);
        return validHash.equals(hash);
    }

    static boolean slowEqual(byte[] a, byte[] b){
        int diff = a.length ^ b.length;
        if(diff != 0) return false;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    static byte[] pbkdf2(char[] pwd, byte[] salt){
        KeySpec keySpec = new PBEKeySpec(pwd, salt, PBKDF2_ITERATION_COUNT, HASH_BYTE_LEN * 8);
        SecretKeyFactory var5 = null;
        byte[] var7 = null;
        try {
            var5 = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = var5.generateSecret(keySpec);
            var7 = secretKey.getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return var7;
    }

    static String toHex(byte[] bytes){
        if(bytes == null)
            return null;
        BigInteger b = new BigInteger(1, bytes);
        String hex = b.toString(16);
        int len = ( bytes.length * 2 ) - hex.length();
        if(len > 0)
            return String.format("%0"+len+"d",0) + hex;
        else
            return hex;
    }



    public static void main(String[] args) {

        // 一般数据库不直接存储pwd， 可存储hash和salt。
        String salt = creatSalt();
        System.out.println("salt: "+salt);
        String hash = createHash(PWD, salt);
        System.out.println("hash: "+ hash);

        System.out.println("请输入验证密码🔒");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            boolean ret = validPassword(line, salt, hash);
            if(ret) {
                System.out.println("Success !!!");
                break;
            }
            else
                System.err.println("Error !!!");
        }

    }

}
