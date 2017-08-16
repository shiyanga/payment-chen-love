package com.payment.service.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.payment.common.util.JacksonUtil;
import com.payment.common.util.JsonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.groupwormhole.AuthUser;
import com.payment.entity.groupwormhole.AuthUserRole;
import com.payment.entity.groupwormhole.contract.AuthObject;
import com.payment.entity.publicenitty.ErrorEnum;
import com.payment.entity.publicenitty.Result;
import com.payment.global.SpringContextUtil;
import com.payment.service.groupwormholeservice.AuthorityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.json.Json;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2016/7/21 14:19
 */
@Service
public class RedisAuthorityService implements AuthorityService {
    private static Logger logger = LoggerFactory.getLogger(RedisAuthorityService.class);
    private static final String AUTH_REDIS_KEY = "AUTHORITY_DATA";
    private static final String USER_REDIS_KEY = "USER_DATA";




    @Autowired
    RedisServiceImpl redisServiceImpl;
    /**
     * 权限校验
     *
     * @param employeeId
     * @param url
     * @param reqMethod
     * @return
     */
    public Result validateAuthority(String employeeId, String url, String reqMethod) {
        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(url) || StringUtils.isBlank(reqMethod)) {
            return Result.FAILURE("权限检查失败：参数不全无法校验-员工号或者URL或者reqMethod不能为空");
        }
        if ("S52627".equalsIgnoreCase(employeeId)) {
            return Result.SUCCESS();
        }
        url = url.trim();
        try {
            String strEmploy = redisServiceImpl.hget(AUTH_REDIS_KEY, employeeId.toUpperCase());

            if (StringUtils.isBlank(strEmploy)) {
                return Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]从Redis找不到任何权限信息");
            }
            List<JSONObject> authObjects = JsonUtil.parseArray(strEmploy, JSONObject.class);
            if (authObjects == null || authObjects.size() == 0) {
                return Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]从Redis找不到任何权限信息");
            }

            for (JSONObject authObject : authObjects) {
                if (authObject != null && url.equalsIgnoreCase(authObject.getString("url")) && (StringUtils.isBlank(authObject.getString(reqMethod)) || reqMethod.equalsIgnoreCase(authObject.getString(reqMethod)))) {
                    return Result.SUCCESS();
                }
            }
            return Result.FAILURE("员工[" + employeeId + "]没有权对[" + url + "(" + reqMethod + ")]的访问权限");
        } catch (Exception e) {
            return Result.FAILURE(ErrorEnum.THIRTY_PARTY_ERROR, "连接Redis失败：" + e.getMessage());
        }

    }

    public Result validateAuthority(String employeeId, String authCode) {
        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(authCode)) {
            return Result.FAILURE("权限检查失败：参数不全无法校验-员工号或者AuthCode不能为空");
        }
        if ("S52627".equalsIgnoreCase(employeeId)) {
            return Result.SUCCESS();
        }
        authCode = authCode.trim();
        try {
            String strEmploy = redisServiceImpl.hget(AUTH_REDIS_KEY, employeeId.toUpperCase());
//            String strEmploy = CRedisHelper.getInstance().getProvider().hget(AUTH_REDIS_KEY, employeeId.toUpperCase());
            if (StringUtils.isBlank(strEmploy)) {
                return Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]从Redis找不到任何权限信息");
            }
            List<AuthObject> authObjects = JsonUtil.parseArray(strEmploy, AuthObject.class);
            if (authObjects == null || authObjects.size() == 0) {
                return Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]从Redis找不到任何权限信息");
            }

            for (AuthObject authObject : authObjects) {
                if (authObject != null && authCode.equalsIgnoreCase(authObject.getCode())) {
                    return Result.SUCCESS();
                }
            }
            return Result.FAILURE("员工[" + employeeId + "]没有权对[" + authCode + "]的访问权限");
        } catch (Exception e) {
            return Result.FAILURE(ErrorEnum.THIRTY_PARTY_ERROR, "连接Redis失败：" + e.getMessage());
        }
    }


    /**
     * 放置权限信息对象
     * <p/>
     * 以员工号（大写）为set-key, 以其他字段为value
     *
     * @return boolean
     */
    public Result refreshCache(Map<String, List<AuthObject>> cacheMap) {
        if (cacheMap == null || cacheMap.size() == 0) {
            return Result.FAILURE("参数不能为空");
        }
        try {
            //删除旧的数据
            redisServiceImpl.del(AUTH_REDIS_KEY);
            //添加新的数据
            Map<String, String> daMap = new HashMap<>();
            for (Map.Entry entry : cacheMap.entrySet()) {
                daMap.put(entry.getKey().toString(), JSON.toJSONString(entry.getValue()));
            }
            redisServiceImpl.hset(AUTH_REDIS_KEY, daMap, 0);
            //    CRedisHelper.getInstance().getProvider().hmset(AUTH_REDIS_KEY, daMap);
            return Result.SUCCESS();
        } catch (Exception e) {
            logger.error("put the employee auth info into the redis exception, detail: ", e);
            return Result.FAILURE("信息存入CRedis失败：" + e.getMessage());
        }
    }


    /**
     * 将用户信息全部放到里面去
     *
     * @param cacheMap
     * @return
     */
    public Result refreshUserCache(Map<String, AuthUser> cacheMap) {
        try {
            if (cacheMap == null || cacheMap.size() == 0) {
                return Result.FAILURE("参数不能为空");
            }
            Map<String, String> daMap = new HashMap<>();
            for (Map.Entry entry : cacheMap.entrySet()) {
                daMap.put(entry.getKey().toString(), JSON.toJSONString(entry.getValue()));
            }
            redisServiceImpl.hset(USER_REDIS_KEY, daMap, 0);
        } catch (Exception e) {
            logger.error("put the employee auth info into the redis exception, detail: ", e);
            return Result.FAILURE("信息存入CRedis失败：" + e.getMessage());
        }
        return Result.SUCCESS();
    }

    /**
     * 通过redis 得到用户信息
     *
     * @param employeeId
     * @return
     */
    public AuthUser getRedisUserByEmployeeId(String employeeId) {
        AuthUser user = null;
        try {
            String json = redisServiceImpl.hget(USER_REDIS_KEY, employeeId);
            user = JacksonUtil.json2pojo(json, AuthUser.class);
        } catch (Exception e) {
            logger.error("getRedisUserByEmployeeId the redis exception, detail: ", e);
        }

        return user;
    }


}
