package security.sasl;

import com.sun.jndi.ldap.*;

import javax.naming.directory.DirContext;
import javax.security.sasl.Sasl;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;

import static jdk.nashorn.tools.Shell.SUCCESS;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/14 10:25
 */
public class SaslClientDemo {

    public static void main(String[] args) throws Exception {

        String[] mechanisms = new String[]{"DIGEST-MD5"};
        SaslClient sc = Sasl.createSaslClient(mechanisms, "shm", "xmpp", "java.com", null, new DemoCallbackhandler());

        byte[] token = new byte[0];
        byte[] chanllenge = sc.evaluateChallenge(token);


    }

}
