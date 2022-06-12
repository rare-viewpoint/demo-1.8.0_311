package security.jsse;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.Socket;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/11 23:30
 */
public class SSLSocketClientWithTunneling {

    public static void main(String[] args) throws IOException {
        new SSLSocketClientWithTunneling().doInt("www.verisign.com", 443);
    }

    String tunnelHost;
    int tunnelPort;

    public void doInt(String host, int port) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();

        tunnelHost = System.getProperty("https.proxyHost");
        tunnelPort = Integer.getInteger("https.proxyPort");

        Socket tunnel = new Socket(tunnelHost, tunnelPort);
        doTunnelHandshake(tunnel, host, port);



        SSLSocket socket = (SSLSocket) factory.createSocket(tunnel, host, port, true);
        socket.addHandshakeCompletedListener(event -> {
            System.out.println("Handshake finished");
            System.out.println("\t CipherSuite："+ event.getCipherSuite());
            System.out.println("\t SessionId："+ event.getSession());
            System.out.println("\t PeerHost："+ event.getSession().getPeerHost());
        });


        socket.startHandshake();
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
        out.println("GET / HTTP/1.0");
        out.println();
        out.flush();

        if(out.checkError()){
            System.out.println("SSLSocketClient: java.io.PrintWriter error");
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String inLine;
        while ((inLine = in.readLine()) != null){
            System.out.println(inLine);
        }

        in.close();
        out.close();
        socket.close();
        tunnel.close();
    }

    private void doTunnelHandshake(Socket tunnel, String host, int port) throws IOException {
        OutputStream out = tunnel.getOutputStream();
        String msg = "CONNECT "+host+":"+port+ " HTTP/1.0\n"
                +"User-Agent: "+ sun.net.www.protocol.http.HttpURLConnection.userAgent
                +"\r\n\r\n";

        byte b[];
        try {
            b = msg.getBytes("ASCII7");
        }catch (Exception e){
            b = msg.getBytes();
        }
        out.write(b);
        out.flush();

        byte replay[] = new byte[200];
        int replayLen = 0;
        int newlineSeen = 0;
        boolean headerDone = false;

        InputStream in = tunnel.getInputStream();
        boolean error = false;

        while (newlineSeen < 2){
            int i = in.read();
            if(i < 0){
                throw new IOException("Unexpected EOF from proxy");
            }
            if(i == '\n'){
                headerDone = true;
                ++newlineSeen;
            }else if(i != '\r'){
                newlineSeen = 0;
                if(!headerDone && replayLen < replay.length){
                    replay[replayLen++] = (byte) i;
                }
            }
        }

        String replayStr;
        try {
            replayStr = new String(replay, 0, replayLen, "ASCII7");
        }catch (Exception e){
            replayStr = new String(replay, 0, replayLen);
        }

        if(!replayStr.startsWith("HTTP/1.0 200")){
            throw new IOException("Unable to tunnel through"
                +tunnelHost+":"+tunnelPort
                +". Proxy returns \""+replayStr+"\"");
        }

    }
}
