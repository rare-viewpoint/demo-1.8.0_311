package security.general.permission;

import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @Desc TODO
 * @Author shm
 * @Date 2021/12/8 10:05
 */
public class PermissionDemo
{
    public static void main(String[] args) {

////        System.out.println(System.getProperty("java.security.policy"));
//
//       SecurityManager manager =  System.getSecurityManager();
//        System.out.println(manager);
////        System.out.println(manager.getThreadGroup());
//        manager.checkCreateClassLoader();

        System.out.println(
                AccessController.doPrivileged((PrivilegedAction<String>)
                        () -> System.getProperty("user.name")
                )
        );



    }
}
