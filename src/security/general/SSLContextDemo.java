package security.general;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @see javax.net.ssl.SSLContext
 * SSL
 * SSLv2
 * SSLv3
 * TLS
 * TLSv1
 * TLSv1.1
 * TLSv1.2
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 6:57
 */
public class SSLContextDemo {

    public static void main(String[] args) {

        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            System.out.println(sslContext.getClass());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
