package security.jaas;

import javax.security.auth.callback.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static sun.security.util.Password.readPassword;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/13 9:35
 */
public class MyCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof TextOutputCallback) {

                // display the message according to the specified type
                TextOutputCallback toc = (TextOutputCallback)callbacks[i];
                switch (toc.getMessageType()) {
                    case TextOutputCallback.INFORMATION:
                        System.out.println(toc.getMessage());
                        break;
                    case TextOutputCallback.ERROR:
                        System.out.println("ERROR: " + toc.getMessage());
                        break;
                    case TextOutputCallback.WARNING:
                        System.out.println("WARNING: " + toc.getMessage());
                        break;
                    default:
                        throw new IOException("Unsupported message type: " +
                                toc.getMessageType());
                }


            } else if (callbacks[i] instanceof NameCallback) {

                // prompt the user for a username
                NameCallback nc = (NameCallback)callbacks[i];

                System.err.print(nc.getPrompt());
                System.err.flush();
                nc.setName((new BufferedReader
                        (new InputStreamReader(System.in))).readLine());


            } else if (callbacks[i] instanceof PasswordCallback) {

                // prompt the user for sensitive information
                PasswordCallback pc = (PasswordCallback)callbacks[i];

                System.err.print(pc.getPrompt());
                System.err.flush();
                pc.setPassword(readPassword(System.in));


            } else {
                throw new UnsupportedCallbackException
                        (callbacks[i], "Unrecognized Callback");
            }
        }
    }
}
