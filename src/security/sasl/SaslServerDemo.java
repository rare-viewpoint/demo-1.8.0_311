package security.sasl;

import util.ByteUtil;

import javax.security.sasl.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/14 10:25
 */
public class SaslServerDemo {

    public static void main(String[] args) throws SaslException {

            Map<String, String> props = new HashMap<>();
//            props.put(Sasl.QOP, "auth");
            String authorizationId = "shm";
            String serverName = "java.com";
            SaslServer ss = Sasl.createSaslServer("DIGEST-MD5", "IMAP", "java.com", props, new ServerCallbackHandler(authorizationId, serverName));

            byte[] challenge = ss.evaluateResponse(new byte[0]);
//            System.out.println(ByteUtil.bytesToHexString(challenge));
            System.out.println("challenge: " + new String(challenge));


            String[] mechanisms = new String[]{"DIGEST-MD5"};
            SaslClient sc = Sasl.createSaslClient(mechanisms, authorizationId, "IMAP", serverName, null, new DemoCallbackhandler());


            byte[] response = sc.evaluateChallenge(challenge);
//            System.out.println(ByteUtil.bytesToHexString(response));
            System.out.println("response: " + new String(response));

            byte[] result = ss.evaluateResponse(response);
            System.out.println("result: " + new String(result));
            if(ss.isComplete()){
                System.out.println("auth success !!!");
            }

    }

}
