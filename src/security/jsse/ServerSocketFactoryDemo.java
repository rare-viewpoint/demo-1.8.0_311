package security.jsse;

import org.junit.Test;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 *
 * @see javax.net.ServerSocketFactory
 * @see javax.net.ssl.SSLServerSocketFactory
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/10 2:25
 */
public class ServerSocketFactoryDemo {

    final static String HOST = "127.0.0.1";
    final static int PORT = 8000;

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
//        new ServerSocketFactoryDemo().serverSocket();
        new ServerSocketFactoryDemo().sslServerSocket();
    }

    @Test
    public void serverSocket() throws IOException {
        ServerSocketFactory serverSocketFactory = ServerSocketFactory.getDefault();
        ServerSocket serverSocket = serverSocketFactory.createServerSocket();
        SocketAddress address = new InetSocketAddress(HOST, PORT);
        serverSocket.bind(address);

        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            new Thread(new WorkRunnable(socket)).start();
        }


    }

    private static class WorkRunnable implements Runnable{
        private final Socket socket;
        WorkRunnable(Socket socket) {
            this.socket = socket;
            System.out.println("已连接");
        }

        @Override
        public void run() {
            try {
                Scanner scanner = new Scanner(socket.getInputStream());
                while (socket.isConnected() && scanner.hasNextLine()){
                    System.out.println(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static class SslWorkRunnable implements Runnable{
        private final Socket socket;
        private final SSLContext sslContext;

        private SslWorkRunnable(Socket socket, SSLContext sslContext) {
            this.socket = socket;
            this.sslContext = sslContext;

        }

        @Override
        public void run() {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ByteBuffer pre = ByteBuffer.allocate(1024);
            try {
                SSLEngine engine = sslContext.createSSLEngine();
                engine.setWantClientAuth(true);
                InputStream is =  socket.getInputStream();
                byte[] r = new byte[8];
                while (is.read(r) != -1){
                    pre.put(r);
                }
                SSLEngineResult result = engine.unwrap(pre, buffer);
                System.out.println(new String(buffer.array()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Test
    public void sslServerSocket() throws NoSuchAlgorithmException, IOException {
        SSLContext context = SSLContext.getDefault();
        SSLServerSocketFactory sslServerSocketFactory = context.getServerSocketFactory();
        ServerSocket serverSocket = sslServerSocketFactory.createServerSocket();
        serverSocket.bind(new InetSocketAddress(HOST, 8443));
        while (!serverSocket.isClosed()) {
            Socket socket = serverSocket.accept();
            new Thread(new SslWorkRunnable(socket, context)).start();
        }

    }


}
