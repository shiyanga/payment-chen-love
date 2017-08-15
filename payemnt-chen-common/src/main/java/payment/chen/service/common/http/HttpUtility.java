package payment.chen.service.common.http;

import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


public class HttpUtility {
    /** 默认字符集（UTF-8） */
    public static final String DEFAULT_ENCODING = "UTF-8";

    private final static String safeCharset(String charset)
    {
        if(StringUtils.isBlank(charset))
            charset = DEFAULT_ENCODING;

        return charset;
    }

    /** URL编码 （使用默认字符集） */
    public final static String urlEncode(String url)
    {
        return urlEncode(url, null);
    }

    /** URL编码 （使用指定字符集） */
    public final static String urlEncode(String url, String charset)
    {
        try
        {
            return URLEncoder.encode(url, safeCharset(charset));
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }

    /** URL解码 （使用默认字符集） */
    public final static String urlDecode(String url)
    {
        return urlDecode(url, null);
    }

    /** URL解码 （使用指定字符集） */
    public final static String urlDecode(String url, String enc)
    {
        try
        {
            return URLDecoder.decode(url, safeCharset(enc));
        }
        catch(UnsupportedEncodingException e)
        {
            throw new RuntimeException(e);
        }
    }
}
