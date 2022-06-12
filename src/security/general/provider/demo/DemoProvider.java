package security.general.provider.demo;


import java.security.*;

/**
 * DP
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/6 9:04
 */
public final class DemoProvider extends Provider {

    private static volatile DemoProvider instance = null;

    public DemoProvider() {
        super("DP", 1.8, "DP provider implement —— AES、RSA algorithm implement");

        put("Cipher.DES", "security.general.provider.demo.DemoDESCipher");

        if(instance == null)
            instance = this;
    }

    static DemoProvider getInstance(){
        if(instance == null){
            return new DemoProvider();
        }
        return instance;
    }

    public static void main(String[] args) {
//        Security.addProvider(DemoProvider.getInstance());
        Provider provider =  Security.getProvider("DP");
        System.out.println(provider.getServices());
//            Security.removeProvider("DP");
//          Cipher cipher =  Cipher.getInstance("DES", "DP");
//          System.out.println(cipher.getProvider());
    }


}
