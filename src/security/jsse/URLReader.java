package security.jsse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/11 12:28
 */
public class URLReader {
    public static void main(String[] args) throws Exception {
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
