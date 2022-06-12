package security.general.keypair;

import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

/**
 *
 * @see KeyPair
 * @see java.security.KeyPairGenerator
 * "DiffieHellman",
*             "DSA",
*             "RSA",
*             "RSASSA-PSS",
*             "EC"
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/3 9:35
 */
public class KeyPairGeneratorDemo {

    static String[] algorithms = new String[]{
            "DiffieHellman",
            "DSA",
            "RSA",
            "RSASSA-PSS",
            "EC"

    };

    public static void main(String[] args) {
        try {
            for (String algorithm : algorithms) {
                KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
                System.out.println("算法："+ generator.getAlgorithm());
//                System.out.println(generator.getProvider().getServices());
                if("EC".equals(algorithm)){

//                    System.out.println(generator.getProvider().getService("AlgorithmParameters", "EC"));
                    /**
                     * SupportedCurves=[secp112r1,1.3.132.0.6]|[secp112r2,1.3.132.0.7]|[secp128r1,1.3.132.0.28]|
                     * [secp128r2,1.3.132.0.29]|[secp160k1,1.3.132.0.9]|[secp160r1,1.3.132.0.8]|[secp160r2,1.3.132.0.30]
                     * |[secp192k1,1.3.132.0.31]|[secp192r1,NIST P-192,X9.62 prime192v1,1.2.840.10045.3.1.1]
                     * |[secp224k1,1.3.132.0.32]|[secp224r1,NIST P-224,1.3.132.0.33]|[secp256k1,1.3.132.0.10]
                     * |[secp256r1,NIST P-256,X9.62 prime256v1,1.2.840.10045.3.1.7]|[secp384r1,NIST P-384,1.3.132.0.34]
                     * |[secp521r1,NIST P-521,1.3.132.0.35]|[X9.62 prime192v2,1.2.840.10045.3.1.2]|
                     * [X9.62 prime192v3,1.2.840.10045.3.1.3]|[X9.62 prime239v1,1.2.840.10045.3.1.4]
                     * |[X9.62 prime239v2,1.2.840.10045.3.1.5]|[X9.62 prime239v3,1.2.840.10045.3.1.6]
                     * |[sect113r1,1.3.132.0.4]|[sect113r2,1.3.132.0.5]|[sect131r1,1.3.132.0.22]
                     * |[sect131r2,1.3.132.0.23]|[sect163k1,NIST K-163,1.3.132.0.1]|[sect163r1,1.3.132.0.2]
                     * |[sect163r2,NIST B-163,1.3.132.0.15]|[sect193r1,1.3.132.0.24]|[sect193r2,1.3.132.0.25]
                     * |[sect233k1,NIST K-233,1.3.132.0.26]|[sect233r1,NIST B-233,1.3.132.0.27]
                     * |[sect239k1,1.3.132.0.3]|[sect283k1,NIST K-283,1.3.132.0.16]
                     * |[sect283r1,NIST B-283,1.3.132.0.17]|[sect409k1,NIST K-409,1.3.132.0.36]
                     * |[sect409r1,NIST B-409,1.3.132.0.37]|[sect571k1,NIST K-571,1.3.132.0.38]
                     * |[sect571r1,NIST B-571,1.3.132.0.39]|[X9.62 c2tnb191v1,1.2.840.10045.3.0.5]
                     * |[X9.62 c2tnb191v2,1.2.840.10045.3.0.6]|[X9.62 c2tnb191v3,1.2.840.10045.3.0.7]
                     * |[X9.62 c2tnb239v1,1.2.840.10045.3.0.11]|[X9.62 c2tnb239v2,1.2.840.10045.3.0.12]
                     * |[X9.62 c2tnb239v3,1.2.840.10045.3.0.13]|[X9.62 c2tnb359v1,1.2.840.10045.3.0.18]
                     * |[X9.62 c2tnb431r1,1.2.840.10045.3.0.20]|[brainpoolP160r1,1.3.36.3.3.2.8.1.1.1]
                     * |[brainpoolP192r1,1.3.36.3.3.2.8.1.1.3]|[brainpoolP224r1,1.3.36.3.3.2.8.1.1.5]
                     * |[brainpoolP256r1,1.3.36.3.3.2.8.1.1.7]|[brainpoolP320r1,1.3.36.3.3.2.8.1.1.9]
                     * |[brainpoolP384r1,1.3.36.3.3.2.8.1.1.11]|[brainpoolP512r1,1.3.36.3.3.2.8.1.1.13]
                     */
                    AlgorithmParameterSpec spec = new ECGenParameterSpec("sect193r1");
                    generator.initialize(spec);
                }else{
                    generator.initialize(512);
                }

                KeyPair keyPair = generator.generateKeyPair();
                PublicKey publicKey = keyPair.getPublic();
                System.out.println("公钥（"+publicKey.getFormat()+"）：\n"+ Base64.getEncoder().encodeToString(publicKey.getEncoded()));
                PrivateKey privateKey = keyPair.getPrivate();
                System.out.println("私钥（"+privateKey.getFormat()+"）：\n"+ Base64.getEncoder().encodeToString(privateKey.getEncoded()));
                System.out.println();
            }
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }
}