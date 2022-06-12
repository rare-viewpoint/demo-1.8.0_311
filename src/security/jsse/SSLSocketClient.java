package security.jsse;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;
import java.io.*;
import java.security.Security;
import java.security.cert.Certificate;
import java.util.Base64;
import java.util.Enumeration;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/11 23:17
 */
public class SSLSocketClient {

    public static void main(String[] args) {
        //
        System.setProperty("com.sun.net.ssl.checkRevocation", "true");
        Security.setProperty("ocsp.enable", "true");

        //在客户端启用在线证书状态协议 (OCSP) 装订
        System.setProperty("jdk.tls.client.enableStatusRequestExtension", "true");

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
//            SSLSocket socket = (SSLSocket) factory.createSocket("www.verisign.com", 443);
            SSLSocket socket = (SSLSocket) factory.createSocket("www.baidu.com", 443);


            socket.startHandshake();
//            System.out.println(socket.getSession().getPeerPrincipal());




            for (X509Certificate x509Certificate : socket.getSession().getPeerCertificateChain()) {
                System.out.println("IssuerDN: "+x509Certificate.getIssuerDN());
                System.out.println("SubjectDN: "+x509Certificate.getSubjectDN());
                System.out.println();
            }
//            for (Certificate peerCertificate : socket.getSession().getPeerCertificates()) {
//                System.out.println(peerCertificate);
//                System.out.println(peerCertificate.getPublicKey());
//                System.out.println(peerCertificate.getType());
//                System.out.println(Base64.getEncoder().encodeToString(peerCertificate.getEncoded()));
//                System.out.println();
//                System.out.println(Base64.getEncoder().encodeToString(peerCertificate.getPublicKey().getEncoded()));
//                System.out.println("----------------------------------------------------------------------------------------------------");
//            }


//            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
//
//            out.println("GET / HTTP/1.0");
//            out.println();
//            out.flush();
//
//            if(out.checkError()){
//                System.out.println("SSLSocketClient: java.io.PrintWriter error");
//            }
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            String inLine;
//            while ((inLine = in.readLine()) != null){
//                System.out.println(inLine);
//            }
//
//            in.close();
//            out.close();
            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
