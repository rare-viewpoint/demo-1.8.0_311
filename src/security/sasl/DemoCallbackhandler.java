package security.sasl;

import javax.security.auth.callback.*;
import javax.security.sasl.RealmCallback;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static sun.security.util.Password.readPassword;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/14 10:59
 */
public class DemoCallbackhandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof NameCallback) {

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


            }else if (callbacks[i] instanceof RealmCallback) {

                // prompt the user for sensitive information
                RealmCallback pc = (RealmCallback)callbacks[i];
                pc.setText("java.com");
//                System.err.print(pc.getPrompt());
//                System.err.flush();
//                pc.setText((new BufferedReader
//                        (new InputStreamReader(System.in))).readLine());


            } else {
                throw new UnsupportedCallbackException
                        (callbacks[i], "Unrecognized Callback");
            }
        }
    }
}
