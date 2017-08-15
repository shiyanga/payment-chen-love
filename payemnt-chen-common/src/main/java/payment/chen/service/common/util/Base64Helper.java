package payment.chen.service.common.util;

import org.apache.commons.codec.binary.Base64;

public class Base64Helper {
    public static String encode(byte[] data) throws Exception {
        return Base64.encodeBase64String(data);
    }
    
    public static byte[] decode(String base64String) throws Exception {
        return Base64.decodeBase64(base64String);
    }
}
