package payment.chen.service.common.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class CryptHelper {
    public static final String KEY_ALGORITHM = "RSA";
    private static final String SEC_RAN_ALG     = "SHA1PRNG";
    
    public enum CryptMethod{
        AES("AES",128),
        DES("DES",56);
        
        private String algorithm = null;
        private int keySize = 0;
        private CryptMethod(String algorithm, int keySize){
            this.algorithm = algorithm;
            this.keySize = keySize;
        }
        
        public String getAlgorithm(){
            return this.algorithm;
        }
        
        public int getKeySize(){
            return this.keySize;
        }
    }
    
    protected static String base64Encode(byte[] data) throws Exception {
        return new String(Base64.encodeBase64(data));
    }
    
    protected static byte[] base64Decode(String base64String) throws Exception {
        return Base64.decodeBase64(base64String);
    }
    
    /** 
     * AES加密 
     * 
     * @param content: 需要加密的内容 
     * @param password:  加密密码 
     * @return:加密后数据
     */      
    public static byte[] aseEncrypt(byte[] content, String password) throws Exception {
        return encrypt(CryptMethod.AES, content, password);
    } 
    
    /** AES解密 
     * 
     * @param content: 待解密内容 
     * @param password: 解密密钥 
     * @return:解密后数据
     */  
    public static byte[] aseDecrypt(byte[] content, String password) throws Exception {
        return decrypt(CryptMethod.AES, content, password);
    }
    
    /** 
     * DES加密 
     * 
     * @param content: 需要加密的内容 
     * @param password:  加密密码 
     * @return:加密后数据
     */      
    public static byte[] desEncrypt(byte[] content, String password) throws Exception {
        return encrypt(CryptMethod.DES, content, password);
    } 
    
    /** DES解密 
     * 
     * @param content: 待解密内容 
     * @param password: 解密密钥 
     * @return:解密后数据
     */  
    public static byte[] desDecrypt(byte[] content, String password) throws Exception {
        return decrypt(CryptMethod.DES, content, password);
    }
    
    /**
     * RSA加密数据
     * @param publicKey: 公匙(Base64String)
     * @param input: 需要加密的数据
     * @return: 加密后的数据
     * @throws Exception
     */
    public static String rsaEncrypt(String publicKey, byte[] input) throws Exception {
        // 对公钥进行base64解码
        byte[] keyBytes = base64Decode(publicKey);
        
        // 取得公钥  
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPublicKey publicKey2 = (RSAPublicKey)keyFactory
                .generatePublic(x509KeySpec);   
        return rsaEncrypt(publicKey2, input);
    }
    
    /**
     * RSA加密数据
     * @param publicKey: 公匙(RSAPublicKey)
     * @param input: 需要加密的数据
     * @return: 加密后的数据
     * @throws Exception
     */
    public static String rsaEncrypt(RSAPublicKey publicKey, byte[] input)
            throws Exception {
        
        // 对数据解密  
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        
        int offset = 0;
        // 如果数据长度大于模长则要分组加密，获取每组最大字节长度
        int length = publicKey.getModulus().bitLength()/8-11; 
        
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        while(offset < input.length){
            if(offset + length >input.length)
                length = input.length - offset;
            
            byte[] data = cipher.doFinal(input, offset, length); 
            byteBuffer.write(data);
            offset += length;
        }
        
        return base64Encode(byteBuffer.toByteArray());
    }
    
    /**
     * RSA解密数据
     * @param privateKey: 私匙(Base64String)
     * @param input: 加密过的数据
     * @return: 解密后的数据
     * @throws Exception
     */
    public static String rsaDecrypt(String privateKey, String input) throws Exception {
        //  对私钥进行base64解码
        byte[] keyBytes = base64Decode(privateKey);
        
        // 取得私钥  
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        RSAPrivateKey privateKey2 = (RSAPrivateKey)keyFactory
                .generatePrivate(pkcs8KeySpec);
        
        return rsaDecrypt(privateKey2, input);
    }
    
    /**
     * RSA解密数据
     * @param privateKey: 私匙(RSAPrivateKey)
     * @param input: 加密过的数据
     * @return: 解密后的数据
     * @throws Exception
     */   
    public static String rsaDecrypt(RSAPrivateKey privateKey, String input) throws Exception {
        // 对数据解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        
        byte[] inputBytes = base64Decode(input);            
        int offset = 0;
        // 如果密文长度大于模长则要分组解密，获取每组最大字节长度
        int length = privateKey.getModulus().bitLength() / 8;
        
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        while(offset < inputBytes.length){
            if(offset + length >inputBytes.length)
                length = inputBytes.length - offset;
            
            byte[] data = cipher.doFinal(inputBytes, offset, length);
            byteBuffer.write(data);
            offset += length;
        }
        return new String(byteBuffer.toByteArray());
    }
    
    /**
     * 加密
     * 
     * @param method    ：加密方法（AES、DES）
     * @param content   ：要加密的内容
     * @param password  ：密码
     * @return          ：加密结果
     * @throws Exception 加密失败抛出异常
     */
    public final static byte[] encrypt(CryptMethod method, byte[] content, String password)
    {
        try{
            KeyGenerator kgen   = KeyGenerator.getInstance(method.getAlgorithm());
            SecureRandom secure = SecureRandom.getInstance(SEC_RAN_ALG);
            secure.setSeed(password.getBytes());
            kgen.init(method.getKeySize(), secure);

            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key   = new SecretKeySpec(enCodeFormat, method.getAlgorithm());
            Cipher cipher       = Cipher.getInstance(method.getAlgorithm());
            
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(content);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 解密
     * 
     * @param method    ：解密方法（AES、DES）
     * @param content   ：解密的内容
     * @param password  ：密码
     * @return          ：解密结果
     * @throws Exception 解密失败抛出异常
     */    
    public final static byte[] decrypt(CryptMethod method, byte[] content, String password)
    {
        try{            
            KeyGenerator kgen   = KeyGenerator.getInstance(method.getAlgorithm());
            SecureRandom secure = SecureRandom.getInstance(SEC_RAN_ALG);
            secure.setSeed(password.getBytes());
            kgen.init(method.getKeySize(), secure);

            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key   = new SecretKeySpec(enCodeFormat, method.getAlgorithm());
            Cipher cipher       = Cipher.getInstance(method.getAlgorithm());
            
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(content);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
}
