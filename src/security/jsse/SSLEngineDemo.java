package security.jsse;

import util.ByteUtil;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @see javax.net.ssl.SSLEngine
// * @see sun.security.ssl.SSLEngineImpl
 * @see javax.net.ssl.SSLEngineResult
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/10 4:47
 */
public class SSLEngineDemo {

    static void doHandshake(SocketChannel socketChannel, SSLEngine engine, ByteBuffer myNetData, ByteBuffer peerNetData) throws IOException {

        int appBufferSize = engine.getSession().getApplicationBufferSize();
        ByteBuffer myAppData = ByteBuffer.allocate(appBufferSize);
        ByteBuffer peerAppData = ByteBuffer.allocate(appBufferSize);

        engine.beginHandshake();
        SSLEngineResult.HandshakeStatus hs = engine.getHandshakeStatus();

        SSLEngineResult res;
        while (hs != SSLEngineResult.HandshakeStatus.FINISHED &&
                hs != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING){
            switch (hs){
                case NEED_UNWRAP:{
                    // Receive handshaking data from peer
                    if (socketChannel.read(peerNetData) < 0) {
                        // The channel has reached end-of-stream
                    }
                    // Process incoming handshaking data
                    peerNetData.flip();
                    res = engine.unwrap(peerNetData, peerAppData);
                    peerNetData.compact();
                    hs = res.getHandshakeStatus();

                    // Check status
                    switch (res.getStatus()) {
                        case OK :
                            // Handle OK status
                            break;
                        case BUFFER_OVERFLOW:
                        case BUFFER_UNDERFLOW:
                        case CLOSED:
                    }
                    break;
                }
                case NEED_WRAP:{
                    // Empty the local network packet buffer.
                    myNetData.clear();

                    // Generate handshaking data
                    res = engine.wrap(myAppData, myNetData);
                    hs = res.getHandshakeStatus();

                    // Check status
                    switch (res.getStatus()) {
                        case OK :
                            myNetData.flip();
                            // Send the handshaking data to peer
                            while (myNetData.hasRemaining()) {
                                socketChannel.write(myNetData);
                            }
                            break;
                        case BUFFER_UNDERFLOW:
                        case BUFFER_OVERFLOW:
                        case CLOSED:
                    }
                    break;
                }
                case NEED_TASK:{
                    // Handle blocking tasks
                    break;
                }
                default:
            }
        }
    }


    private static String keyStoreFile = "C:\\Users\\starv\\Desktop\\ssl\\keystore";
    private static String trustStoreFile = "C:\\Users\\starv\\Desktop\\ssl\\truststore";
    private static String passwd = "123456";
    public static void main(String[] args) throws Exception {

        // Create and initialize the SSLContext with key material
        char[] passphrase = passwd.toCharArray();

        KeyStore ksKeys = KeyStore.getInstance("JKS");
        ksKeys.load(new FileInputStream(keyStoreFile), passphrase);
        KeyStore ksTrust = KeyStore.getInstance("JKS");
        ksTrust.load(new FileInputStream(trustStoreFile), passphrase);

        // KeyManagers decide which key material to use
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("PKIX");
        kmf.init(ksKeys, passphrase);

        // TrustManagers decide whether to allow connections
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("PKIX");
        tmf.init(ksTrust);

        // Get an instance of SSLContext for TLS protocols
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);


        // Create the engine
        SSLEngine engine = sslContext.createSSLEngine("www.baidu.com", 80);

        // Use as client
        engine.setUseClientMode(true);









//        System.out.println(engine.getHandshakeStatus());


    }
}
