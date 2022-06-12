package security.general.keystore;

import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * <li>
 *创建KeyStore对象<br>
 *      KeyStore getInstance()<br>
 * </li>
 *<li>
 * 将特定的密钥库加载到内存中<br>
 *      final void load(InputStream stream, char[] password)<br>
 *      final void load(KeyStore.LoadStoreParameter param)<br>
 *</li>
 *<li>
 *
 * 确定密钥库条目类型<br>
 *      final boolean isKeyEntry(String alias)<br>
 *      final boolean isCertificateEntry(String alias)<br>
 *
 *</li>
 *<li>
 * 添加/设置/删除密钥库条目<br>
 *      final void setCertificateEntry(String alias, Certificate cert)<br>
 *      final void setKeyEntry(String alias,
 *                        Key key,
 *                        char[] password,
 *                        Certificate[] chain)<br>
 *      final void setKeyEntry(String alias,
 *                        byte[] key,
 *                        Certificate[] chain)<br>
 *      final void deleteEntry(String alias)<br>
 *      final void setEntry(String alias, Entry entry,
 *                     ProtectionParameter protParam) -- PKCS #12<br>
 *</li>
 *<li>
 *
 * 从密钥库获取信息<br>
 *      final Key getKey(String alias, char[] password)<br>
 *      final Certificate getCertificate(String alias)<br>
 *      final Certificate[] getCertificateChain(String alias)<br>
 *      final String getCertificateAlias(Certificate cert)<br>
 *      final Entry getEntry(String alias, ProtectionParameter protParam) -- PKCS #12<br>
 *</li>
 *<li>
 *
 * 保存密钥库
 *      <p>final void store(OutputStream stream, char[] password)</p>
 *      <p>final void store(KeyStore.LoadStoreParameter param)</p>
 *</li>
 * <div>
 * 类型:&nbsp;&nbsp;
 * jceks
 * jks
 * dks
 * pkcs11
 * pkcs12
 *</div>
 * <div>
 * 密钥库: {@link java.security.KeyStore}
 * </div>

 * @Desc TODO
 * @Author shm
 * @Date 2021/12/5 2:52
 */
public class KeyStoreDemo {

    final static String[] types = new String[]{
            "jceks",
            "jks",
            "dks",
//            "pkcs11",
            "pkcs12"

    };

    public static void main(String[] args) {
        for (String type : types) {
            try {
                KeyStore keyStore =  KeyStore.getInstance(type);
                System.out.println("======================="+type+"=======================");
                System.out.println(keyStore.getProvider().getService("KeyStore", type));
                System.out.println("======================================================");
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
    }
}
