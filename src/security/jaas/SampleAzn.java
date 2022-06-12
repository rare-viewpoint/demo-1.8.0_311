package security.jaas;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Iterator;

/**
 * @see
 * @author shm
 * @desc ******
 * @Date 2021/12/13 9:32
 */
public class SampleAzn {


    /**
     * Attempt to authenticate the user.
     *
     * <p>
     *
     * @param args input arguments for this application.  These are ignored.
     */
    public static void main(String[] args) {

        // Obtain a LoginContext, needed for authentication. Tell it
        // to use the LoginModule implementation specified by the
        // entry named "Sample" in the JAAS login configuration
        // file and to also use the specified CallbackHandler.
        LoginContext lc = null;
        try {
            lc = new LoginContext("Sample", new MyCallbackHandler2());
        } catch (LoginException le) {
            System.err.println("Cannot create LoginContext. "
                    + le.getMessage());
            System.exit(-1);
        } catch (SecurityException se) {
            System.err.println("Cannot create LoginContext. "
                    + se.getMessage());
            System.exit(-1);
        }

        // the user has 3 attempts to authenticate successfully
        int i;
        for (i = 0; i < 3; i++) {
            try {

                // attempt authentication
                lc.login();

                // if we return with no exception, authentication succeeded
                break;

            } catch (LoginException le) {

                System.err.println("Authentication failed:");
                System.err.println("  " + le.getMessage());
                try {
                    Thread.currentThread().sleep(3000);
                } catch (Exception e) {
                    // ignore
                }

            }
        }

        // did they fail three times?
        if (i == 3) {
            System.out.println("Sorry");
            System.exit(-1);
        }

        System.out.println("Authentication succeeded!");

        Subject mySubject = lc.getSubject();

        // let's see what Principals we have
        Iterator principalIterator = mySubject.getPrincipals().iterator();
        System.out.println("Authenticated user has the following Principals:");
        while (principalIterator.hasNext()) {
            Principal p = (Principal)principalIterator.next();
            System.out.println("\t" + p.toString());
        }

        System.out.println("User has " +
                mySubject.getPublicCredentials().size() +
                " Public Credential(s)");

        // now try to execute the SampleAction as the authenticated Subject
        PrivilegedAction action = new SampleAction();
        Subject.doAsPrivileged(mySubject, action, null);

        System.exit(0);
    }
}

/**
 * A CallbackHandler implemented by the application.
 *
 * <p> This application is text-based.  Therefore it displays information
 * to the user using the OutputStreams System.out and System.err,
 * and gathers input from the user using the InputStream System.in.
 */
class MyCallbackHandler2 implements CallbackHandler {

    /**
     * Invoke an array of Callbacks.
     *
     * <p>
     *
     * @param callbacks an array of <code>Callback</code> objects which contain
     *                  the information requested by an underlying security
     *                  service to be retrieved or displayed.
     *
     * @exception java.io.IOException if an input or output error occurs. <p>
     *
     * @exception UnsupportedCallbackException if the implementation of this
     *                  method does not support one or more of the Callbacks
     *                  specified in the <code>callbacks</code> parameter.
     */
    public void handle(Callback[] callbacks)
            throws IOException, UnsupportedCallbackException {

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

    // Reads user password from given input stream.
    private char[] readPassword(InputStream in) throws IOException {

        char[] lineBuffer;
        char[] buf;
        int i;

        buf = lineBuffer = new char[128];

        int room = buf.length;
        int offset = 0;
        int c;

        loop:   while (true) {
            switch (c = in.read()) {
                case -1:
                case '\n':
                    break loop;

                case '\r':
                    int c2 = in.read();
                    if ((c2 != '\n') && (c2 != -1)) {
                        if (!(in instanceof PushbackInputStream)) {
                            in = new PushbackInputStream(in);
                        }
                        ((PushbackInputStream)in).unread(c2);
                    } else
                        break loop;

                default:
                    if (--room < 0) {
                        buf = new char[offset + 128];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0, buf, 0, offset);
                        Arrays.fill(lineBuffer, ' ');
                        lineBuffer = buf;
                    }
                    buf[offset++] = (char) c;
                    break;
            }
        }

        if (offset == 0) {
            return null;
        }

        char[] ret = new char[offset];
        System.arraycopy(buf, 0, ret, 0, offset);
        Arrays.fill(buf, ' ');

        return ret;
    }
}
