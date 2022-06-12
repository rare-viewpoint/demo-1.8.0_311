package security.jsse;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/11 22:41
 */
public class URLPrint {

    private final URL url;

    public URLPrint(URL url) {
        this.url =  Objects.requireNonNull(url);
    }


    public void print(){
        try {
            Scanner stream = new Scanner(url.openStream());
            while (stream.hasNextLine()){
                System.out.println(stream.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws MalformedURLException {
        URL url = new URL("https://v.qq.com/x/cover/hzgtnf6tbvfekfv/b0014u1qmgk.html");
        new URLPrint(url).print();
    }
}
