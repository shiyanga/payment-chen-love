package payment.chen.service.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;


/**
 * Created by shi_y on 2016/10/18.
 */
public class CryptUtil {
    /**
     * AES加密二进制数据
     *
     * @param data     - 待加密的二进制数据
     * @param password - 加密秘钥
     * @return - 返回加密后的数据
     * @throws Exception
     */
    public static byte[] aesEncrypt(byte[] data, String password) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128, new SecureRandom(password.getBytes()));

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyGen.generateKey().getEncoded(), "AES"));
        return cipher.doFinal(data);
    }

    /**
     * AES解密二进制数据
     *
     * @param data     - 待解密的二进制数据
     * @param password - 解密密钥
     * @return - 返回解密后的数据
     * @throws Exception
     */
    public static byte[] aesDecrypt(byte[] data, String password) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(128, new SecureRandom(password.getBytes()));
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyGen.generateKey().getEncoded(), "AES"));
        return cipher.doFinal(data);
    }

    public static void main(String[] args) {
        String str = "SDFDE111213212";
        try {
            byte[] data1 = CryptUtil.aesEncrypt(str.getBytes("UTF-8"), "654321");
            byte[] data2 = CryptUtil.aesDecrypt(data1, "654321");
            String str2 = new String(data2);
            System.out.println("result:" + str2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
