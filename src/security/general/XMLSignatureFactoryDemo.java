package security.general;

import javax.xml.crypto.dsig.XMLSignatureFactory;

/**
 *
 * @see javax.xml.crypto.dsig.XMLSignatureFactory
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/6 5:50
 */
public class XMLSignatureFactoryDemo {

    public static void main(String[] args) {
        XMLSignatureFactory factory =  XMLSignatureFactory.getInstance();
        System.out.println(factory.getProvider().getServices());
    }
}
