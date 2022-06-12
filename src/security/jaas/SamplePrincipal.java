package security.jaas;

import java.security.Principal;

/**
 * @author shm
 * @desc ******
 * @Date 2021/12/13 9:43
 */
public class SamplePrincipal implements Principal, java.io.Serializable {

    /**
     * @serial
     */
    private String name;

    /**
     * Create a SamplePrincipal with a Sample username.
     *
     * <p>
     *
     * @param name the Sample username for this user.
     *
     * @exception NullPointerException if the <code>name</code>
     *                  is <code>null</code>.
     */
    public SamplePrincipal(String name) {
        if (name == null)
            throw new NullPointerException("illegal null input");

        this.name = name;
    }

    /**
     * Return the Sample username for this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return the Sample username for this <code>SamplePrincipal</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Return a string representation of this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a string representation of this <code>SamplePrincipal</code>.
     */
    public String toString() {
        return("SamplePrincipal:  " + name);
    }

    /**
     * Compares the specified Object with this <code>SamplePrincipal</code>
     * for equality.  Returns true if the given io.object is also a
     * <code>SamplePrincipal</code> and the two SamplePrincipals
     * have the same username.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *          <code>SamplePrincipal</code>.
     *
     * @return true if the specified Object is equal equal to this
     *          <code>SamplePrincipal</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof SamplePrincipal))
            return false;
        SamplePrincipal that = (SamplePrincipal)o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>SamplePrincipal</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>SamplePrincipal</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }
}
