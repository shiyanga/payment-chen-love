package payment.chen.service.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2016/7/21 12:48
 */
public class JsonUtil {

    public static JSONObject parseObject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        try{
            return JSON.parseObject(str);
        }catch (Exception e){
            return null;
        }
    }

    public static JSONArray parseJsonArray(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        try{
            return JSON.parseArray(str);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> List<T> parseArray(String text, Class<T> clazz) {
        if(StringUtils.isBlank(text) || clazz==null){
            return null;
        }
        try{
            return JSON.parseArray(text, clazz);
        }catch (Exception e){
            return null;
        }
    }

    public static <T> Object parseObject(String text, Class<T> clazz) {
        if(StringUtils.isBlank(text) || clazz==null){
            return null;
        }
        try{
            return JSON.parseObject(text, clazz);
        }catch (Exception e){
            return null;
        }
    }
}
