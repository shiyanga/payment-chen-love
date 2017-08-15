package payment.chen.service.common.util;

import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * @version 2015/07/01 15:45
 */
public class PropertyUtil {
    public static final String CONFIG = "config.properties";
    private static Properties prop;

    static {
        prop = new Properties();
        try {
            prop.load(PropertyUtil.class.getClassLoader().getResourceAsStream(CONFIG));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        String result =prop.getProperty(key.toLowerCase(), "");
        if(StringUtils.isEmpty(result)){
            result =prop.getProperty(key, "");
        }
        return result;
    }

    public static String getKeyToken() {
        return getString("KeyToken");
    }
    
    public static Integer getInteger(String key) {
        String value = PropertyUtil.getString(key);
        Integer result = null;
        if (value != null && value.length() > 0) {
            result = Integer.parseInt(value);
        }
        return result;
    }

    public static Boolean getBoolean(String key) {
        String value = PropertyUtil.getString(key);
        Boolean result = false;
        if (value != null && value.length() > 0) {
            if(value.trim().equals("true")){
                result = true;
            }
        }
        return result;
    }


}
