package security.general;

import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @see javax.net.ssl.TrustManagerFactory
// * @see sun.security.ssl.TrustManagerFactoryImpl
 * @see javax.net.ssl.TrustManager
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/6 4:35
 */
public class TrustManagerFactoryDemo {

    public static void main(String[] args) throws NoSuchAlgorithmException {
//        System.out.println(TrustManagerFactory.getDefaultAlgorithm());

        TrustManagerFactory factory = TrustManagerFactory.getInstance("PKIX");
        System.out.println(factory.getProvider().getServices());
//        System.out.println(factory.getProvider());
//        factory.init(KeyStore);
//        factory.init(ManagerFactoryParameters);
//        for (TrustManager trustManager : factory.getTrustManagers()) {
//            System.out.println(trustManager);
//        }

    }
}
