package security.sasl;

import javax.security.auth.callback.*;
import javax.security.sasl.AuthorizeCallback;
import javax.security.sasl.RealmCallback;
import javax.security.sasl.Sasl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static sun.security.util.Password.readPassword;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/14 11:23
 */
public class ServerCallbackHandler implements CallbackHandler  {

    private final  String authorizationId;
    private final String serverName;

    public ServerCallbackHandler(String authorizationId, String serverName) {
        this.authorizationId = authorizationId;
        this.serverName = serverName;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (int i = 0; i < callbacks.length; i++) {
            if (callbacks[i] instanceof RealmCallback) {
                RealmCallback nc = (RealmCallback)callbacks[i];
                nc.setText(serverName);
            } else if (callbacks[i] instanceof NameCallback) {
                // prompt the user for a username
                NameCallback nc = (NameCallback)callbacks[i];
                nc.setName(authorizationId);
            } else if (callbacks[i] instanceof PasswordCallback) {

                // prompt the user for sensitive information
                PasswordCallback pc = (PasswordCallback)callbacks[i];
                pc.setPassword("123456".toCharArray());

            }else if (callbacks[i] instanceof AuthorizeCallback) {

                // prompt the user for sensitive information
                AuthorizeCallback pc = (AuthorizeCallback)callbacks[i];
                pc.setAuthorized(true);
            } else {
                throw new UnsupportedCallbackException
                        (callbacks[i], "Unrecognized Callback");
            }
        }
    }
}
