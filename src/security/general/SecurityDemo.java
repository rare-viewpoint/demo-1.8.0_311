package security.general;

import java.security.Provider;
import java.security.Security;

/**
 * @see java.security.Security
 *
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/7 1:27
 */
public class SecurityDemo {

    public static void main(String[] args) {

        for (Provider provider : Security.getProviders()) {
            System.out.println(provider);
        }
    }
}
