package security.general.provider;

import java.security.Provider;
import java.security.Security;
import java.util.Set;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/3 7:00
 */
public class ProviderDemo {

    /**
     * SUN version 1.8
     * SunRsaSign version 1.8
     * SunEC version 1.8
     * SunJSSE version 1.8
     * SunJCE version 1.8
     * SunJGSS version 1.8
     * SunSASL version 1.8
     * XMLDSig version 1.8
     * SunPCSC version 1.8
     * SunMSCAPI version 1.8
     */
    public static void main(String[] args) {
        Provider[] providers = Security.getProviders();
        System.out.println("java.security.Provider 已有provider列表如下：");
        for (Provider provider : providers) {
            System.out.println("=================="+provider+"=====================");
            System.out.println("该provider提供服务如下：\n");
            Set<Provider.Service> serviceSet = provider.getServices();
            for (Provider.Service service : serviceSet) {
                System.out.println(service);
            }
            System.out.println("==============================================================");
            System.out.println();
        }
    }
}
