package security.jsse.alpn;

import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/12 6:58
 */
public class SSLClient {

    public static void main(String[] args) throws IOException {
        // Code for creating a client side SSLSocket
        SSLSocketFactory sslsf = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket sslSocket = (SSLSocket) sslsf.createSocket("www.baidu.com", 443);

        // Get an SSLParameters io.object from the SSLSocket
//        SSLParameters sslp = sslSocket.getSSLParameters();

        // Populate SSLParameters with the ALPN values
        // On the client side the order doesn't matter as
        // when connecting to a JDK server, the server's list takes priority
//        String[] clientAPs = {"TLS1.2", "TLS1.3"};
//        sslp.setApplicationProtocols(clientAPs);

        // Populate the SSLSocket io.object with the SSLParameters io.object
        // containing the ALPN values
//        sslSocket.setSSLParameters(sslp);

//        sslSocket.startHandshake();

        // After the handshake, get the application protocol that has been negotiated
//        String ap = sslSocket.getApplicationProtocol();
//        System.out.println("Application Protocol client side: \"" + ap + "\"");

        // Do simple write/read
        OutputStream sslOS = sslSocket.getOutputStream();
        sslOS.write("GET / HTTP/1.0\r\n\r\n".getBytes());
        sslOS.flush();

        InputStream sslIS = sslSocket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(sslIS));
        String line;
        while ((line = reader.readLine()) != null)
        {
            System.out.println(line);
        }

        sslSocket.close();


    }
}
