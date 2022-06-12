package security.jaas;

import java.io.File;
import java.security.PrivilegedAction;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/13 9:40
 */
public class SampleAction implements PrivilegedAction {

    @Override
    public Object run() {
        System.out.println("\nYour java.home property value is: "
                + System.getProperty("java.home"));

        System.out.println("\nYour user.home property value is: "
                + System.getProperty("user.home"));

        File f = new File("foo.txt");
        System.out.print("\nfoo.txt does ");
        if (!f.exists())
            System.out.print("not ");
        System.out.println("exist in the current working directory.");
        return null;
    }
}
