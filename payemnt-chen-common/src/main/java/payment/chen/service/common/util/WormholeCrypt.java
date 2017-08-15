package payment.chen.service.common.util;

public class WormholeCrypt {

    
    /** 十六进制字符串 -> byte[] */
    private final static byte[] hexStr2Bytes(String str)
    {
        int length = str.length();
        
        if(length % 2 != 0)
        {
            str = "0" + str;
            length = str.length();
        }
        
        byte[] bytes = new byte[length / 2];
        
        for(int i = 0; i < bytes.length; i++)
            bytes[i] = hex2Byte(str.charAt(2 * i), str.charAt(2 * i + 1));
        
        return bytes;
    }
    
    /** 十六进制双字符 -> byte */
    private final static byte hex2Byte(char ch, char cl)
    {
        byte bh = hex2HalfByte(ch);
        byte bl = hex2HalfByte(cl);
        
        return (byte)((bh << 4) + bl);
    }
    
    /** 十六进制单字符 -> 半 byte */
    private final static byte hex2HalfByte(char c)
    {
        return (byte)(c <= '9' ? c - '0' : (c <= 'F' ? c - 'A' + 0xA : c - 'a' + 0xA));
    }
    

    
    /** byte[] -> 十六进制字符串 (小写) */
    private final static String bytes2HexStr(byte[] bytes)
    {
        return bytes2HexStr(bytes, false);
    }
    /** byte[] -> 十六进制字符串 */
    private final static String bytes2HexStr(byte[] bytes, boolean capital)
    {
        StringBuilder sb = new StringBuilder();
        
        for(byte b : bytes)
            sb.append(byte2Hex(b, capital));
        
        return sb.toString();
    }
    /** byte -> 十六进制双字符 (小写) */
    private final static char[] byte2Hex(byte b)
    {
        return byte2Hex(b, false);
    }
    
    /** byte -> 十六进制双字符 */
    private final static char[] byte2Hex(byte b, boolean capital)
    {
        byte bh = (byte)(b >>> 4 & 0xF);
        byte bl = (byte)(b & 0xF);

        return new char[] {halfByte2Hex(bh, capital), halfByte2Hex(bl, capital)};
    }
    
    /** 半 byte -> 十六进制单字符 (小写) */
    private final static char halfByte2Hex(byte b)
    {
        return halfByte2Hex(b, false);
    }
    
    /** 半 byte -> 十六进制单字符 */
    private final static char halfByte2Hex(byte b, boolean capital)
    {
        return (char)(b <= 9 ? b + '0' : (capital ? b + 'A' - 0xA : b + 'a' - 0xA));
    }
    
    /**
     * 兼容原来虫洞的加密方式
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public final static String encrypt(String content, String password) throws Exception {
        byte[] data = CryptHelper.aseEncrypt(content.getBytes(), password);
        String result = bytes2HexStr(data);
        return result;
    }
    
    /**
     * 兼容虫洞原来的解码方式
     * @param content
     * @param password
     * @return
     * @throws Exception
     */
    public final static String decrypt(String content, String password) throws Exception {
        byte[] data = hexStr2Bytes(content); 
        String result = new String(CryptHelper.aseDecrypt(data, password));
        return result;
    }    
}
