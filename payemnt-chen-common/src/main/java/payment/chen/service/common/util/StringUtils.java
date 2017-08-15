package payment.chen.service.common.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static boolean isStringEqual(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if (str1 != null && str2 != null) {
            return str1.compareTo(str2) == 0;
        }
        return false;
    }

    /**
     * 判断字符串是否为Null或空白
     *
     * @param str
     * @return
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.length() < 1)
            return true;

        return false;
    }

    public static boolean isNotBlank(String str) {
        return org.apache.commons.lang.StringUtils.isNotBlank(str);
    }

    public static boolean isBlank(String str) {
        return org.apache.commons.lang.StringUtils.isBlank(str);
    }

    /**
     * 判断字符串是否为Null或者全部是空格、Tab的内容
     *
     * @param str
     * @return
     */
    public static boolean isNullOrWhiteSpace(String str) {
        if (str == null || str.length() < 1)
            return true;

        for (int i = 0; i < str.length(); i++) {
            if (!(str.charAt(i) == ' ' && str.charAt(i) == '\t'))
                return false;
        }
        return true;
    }

    /**
     * 把参数 obj 转换为安全字符串：如果 obj = null，则把它转换为空字符串
     **/
    public final static String safeString(Object obj) {
        if (obj == null)
            return "";

        return obj.toString();
    }

    /**
     * 检查字符串是否符合整数格式
     */
    public final static boolean isStrNumeric(String str) {
        if (str == null || str.trim().length() <= 0) {
            return false;
        }
        return Pattern.compile("^0$|^\\-?[1-9]+[0-9]*$").matcher(str).matches();
    }

    public static Date string2Date(String str) {
        Date date = null;
        if (str != null && str.trim().length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static Date string2Date(String str, String formate) {
        Date date = null;
        if (str != null && str.trim().length() > 0 && formate != null && formate.trim().length() > 0) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(formate);
                date = sdf.parse(str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static void main(String[] args) {
        String str = "2015-12-31";
        System.out.println(StringUtils.string2Date(str));
    }

    /**
     * String -> int，如果转换不成功则返回默认值 d
     */
    public static int str2Int(String str, int defaultValue) {
        int returnVal;
        try {
            if (str != null)
                str = str.trim();
            returnVal = Integer.valueOf(str);
        } catch (Exception e) {
            returnVal = defaultValue;
        }
        return returnVal;
    }

    /**
     * 得到混淆的安全字符串，比如要混淆2/3位的字符，传参2,3
     * 举例：
     * 传参： origStr = "12345"; numerator = 1; denominator =3; (混淆12345的三分之一个字符)
     * 预期结果：1*****5
     *
     * @param numerator   分子
     * @param denominator 分母
     * @return
     */
    public static String confuseString(String origStr, int numerator, int denominator) {
        StringBuffer safeStr = new StringBuffer("*****");
        if (org.apache.commons.lang.StringUtils.isNotBlank(origStr) && denominator > numerator && numerator > 0) {
            //转化为BigDicemal便于精确计算
            int strLength = origStr.length();
            if (true) {//strLength > denominator
                int startLength = (int) Math.floor(new BigDecimal(String.valueOf(strLength * (denominator - numerator))).divide(new BigDecimal(String.valueOf(denominator * 2)), 2, BigDecimal.ROUND_HALF_UP).doubleValue());
                int endLength = (int) Math.ceil(
                        new BigDecimal(String.valueOf(strLength * numerator)).divide(new BigDecimal(String.valueOf(denominator)), 2, BigDecimal.ROUND_HALF_UP).doubleValue()
                );
                safeStr.insert(0, origStr.substring(0, startLength));
                safeStr.append(origStr.substring((startLength + endLength), strLength));
            }
        }
        return safeStr.toString();
    }


    /**
     * 去掉头部空格
     *
     * @param stream
     * @param trimstr
     * @return
     */
    public static String TrimStart(String stream, String trimstr) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trimstr == null || trimstr.length() == 0) {
            return stream;
        }
        // 结束位置
        int epos = 0;
        // 正规表达式
        String regpattern = "[" + trimstr + "]*+";
        Pattern pattern = Pattern.compile(regpattern, Pattern.CASE_INSENSITIVE);

        StringBuffer buffer = new StringBuffer(stream).reverse();
        Matcher matcher = pattern.matcher(buffer);

        // 去掉开头的指定字符
        matcher = pattern.matcher(stream);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = stream.substring(epos);
        }
        return stream;

    }


    /**
     * 去掉头部空格
     *
     * @param stream
     * @param trimstr
     * @return
     */
    public static String TrimEnd(String stream, String trimstr) {
        // null或者空字符串的时候不处理
        if (stream == null || stream.length() == 0 || trimstr == null || trimstr.length() == 0) {
            return stream;
        }
        // 结束位置
        int epos = 0;
        // 正规表达式
        String regpattern = "[" + trimstr + "]*+";
        Pattern pattern = Pattern.compile(regpattern, Pattern.CASE_INSENSITIVE);
        // 去掉结尾的指定字符
        StringBuffer buffer = new StringBuffer(stream).reverse();
        Matcher matcher = pattern.matcher(buffer);
        if (matcher.lookingAt()) {
            epos = matcher.end();
            stream = new StringBuffer(buffer.substring(epos)).reverse().toString();
        }
        return stream;

    }


}
