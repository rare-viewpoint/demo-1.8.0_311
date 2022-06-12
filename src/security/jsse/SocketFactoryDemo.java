package security.jsse;

import org.junit.Test;
import util.PrintUtil;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;

/**
 * @see javax.net.SocketFactory
 * @see javax.net.ssl.SSLSocketFactory
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/10 1:48
 */
public class SocketFactoryDemo {

    final static String HOST = "127.0.0.1";
    final static int PORT = 8000;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        new SocketFactoryDemo().socketFactory();
        new SocketFactoryDemo().sslSocket();
    }


    @Test
    public void socketFactory() throws IOException {
      SocketFactory factory = SocketFactory.getDefault();
      Socket socket = factory.createSocket();
      SocketAddress address = new InetSocketAddress(HOST, PORT);
      socket.connect(address);
      try (PrintStream printStream = new PrintStream(socket.getOutputStream())){
          PrintUtil.circleScanner(line -> {
              try {
                  printStream.write((line+"\n").getBytes());
                  printStream.flush();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          });
      }
    }



    @Test
    public void sslSocket() throws NoSuchAlgorithmException, IOException {
        SSLContext context = SSLContext.getDefault();
        SSLSocketFactory sslSocketFactory = context.getSocketFactory();
        Socket socket = sslSocketFactory.createSocket(HOST, 8443);

        SSLEngine sslEngine = context.createSSLEngine();
        sslEngine.setUseClientMode(true);

        try (PrintStream printStream = new PrintStream(socket.getOutputStream())){
            final ByteBuffer buffer = ByteBuffer.allocate(1024);
            PrintUtil.circleScanner(line -> {
                try {
                    if(socket.isConnected()){
                        buffer.clear();
                        byte[] content = (line+"\n").getBytes();
                        sslEngine.wrap(ByteBuffer.wrap(content), buffer);
                        printStream.write(buffer.array());
                        printStream.flush();
                        System.out.println("............");
                    }else {
                        System.exit(-1);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }


    }





}
