package security.jsse;

import sun.security.ssl.HandshakeOutStream;
import util.ByteUtil;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLSession;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/12 16:00
 */
public class SSLEngineDemo2 {


    public static void main(String[] args) throws Exception {

        SSLContext sslCon = SSLContext.getDefault();

        SSLEngine engine = sslCon.createSSLEngine();
        engine.setUseClientMode(true);

        SSLSession sslSession = engine.getSession();
        int appSize = sslSession.getApplicationBufferSize();
        int packetSize = sslSession.getPacketBufferSize();

//        System.out.println(appSize);
//        System.out.println(packetSize);
        ByteBuffer appBuf = ByteBuffer.allocate(appSize);
        ByteBuffer packetBuf = ByteBuffer.allocate(packetSize);
        appBuf.put("Hello Server".getBytes());
        appBuf.flip();
        SSLEngineResult cRet = engine.wrap(appBuf, packetBuf);
        while (cRet.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK
            && engine.getDelegatedTask() != null){
//            System.out.println("RUN CLIENT TASK");
            engine.getDelegatedTask().run();
        }
        packetBuf.flip();
//        byte[] content = new byte[packetBuf.remaining()];
//        packetBuf.get(content);
//        System.out.println(ByteUtil.bytesToHexString(content));

        SSLEngine serverEngine = sslCon.createSSLEngine();
        serverEngine.setUseClientMode(false);
        serverEngine.setNeedClientAuth(true);

        appBuf.clear();
//        packetBuf.flip();
        SSLEngineResult sRet = serverEngine.unwrap(packetBuf, appBuf);
        Runnable runnable;
        while (sRet.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NEED_TASK
                && (runnable = serverEngine.getDelegatedTask()) != null){
//            System.out.println("RUN SERVER TASK");
            runnable.run();
        }
        System.out.println(appBuf.remaining());
        byte[] ret = new byte[appBuf.remaining()];
        appBuf.get(ret);
        System.out.println(new String(ret));



//        Socket socket = new Socket();
//        socket.connect(new InetSocketAddress("www.baidu.com", 80));
//        socket.connect(new InetSocketAddress("www.baidu.com", 443));
//        OutputStream out = socket.getOutputStream();
//        out.write(content);
//        out.write("GET / HTTP/1.0\r\n\r\n".getBytes());
//        out.flush();

//        InputStream in = socket.getInputStream();
//
//        Scanner scanner = new Scanner(in);
//        while (scanner.hasNextLine()){
//            System.out.println(scanner.nextLine());
//        }

//        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("ASCII7")));
//        String line = null;
//        while ((line = reader.readLine()) != null){
//            System.out.println(line);
//        }


//        packetBuf.clear();
//        System.out.println(0);
//        byte[] r = new byte[1024];
//        while (in.read(r) != -1){
//            packetBuf.put(r);
//        }
//        System.out.println(1);
//        appBuf.clear();
//        engine.unwrap(packetBuf, appBuf);
//
//        byte[] ret = new byte[appBuf.remaining()];
//        System.out.println(ByteUtil.bytesToHexString(ret));




    }




}
