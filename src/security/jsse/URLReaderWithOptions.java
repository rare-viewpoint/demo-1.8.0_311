package security.jsse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/11 12:31
 */
public class URLReaderWithOptions {
    public static void main(String[] args) throws Exception {

        System.out.println("USAGE: java URLReaderWithOptions " +
                "[-h proxyhost] [-p proxyport] [-k protocolhandlerpkgs] " +
                "[-c ciphersarray]");

        // initialize system properties
        char option = 'd';
        for (int i = 0; i < args.length; i++) {
            System.out.println(option+": "+args[i]);
            switch(option) {
                case 'h':
                    System.setProperty("https.proxyHost", args[i]);
                    option = 'd';
                    break;
                case 'p':
                    System.setProperty("https.proxyPort", args[i]);
                    option = 'd';
                    break;
                case 'k':
                    System.setProperty("java.protocol.handler.pkgs", args[i]);
                    option = 'd';
                    break;
                case 'c':
                    System.setProperty("https.cipherSuites", args[i]);
                    option = 'd';
                    break;
                default:
                    // get the next option
                    if (args[i].startsWith("-")) {
                        option = args[i].charAt(1);
                    }
            }
        }

        URL verisign = new URL("https://www.verisign.com/");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        verisign.openStream()));

        String inputLine;

        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);

        in.close();
    }
}
