package com.payment.global;
import com.payment.common.util.CommonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.groupwormhole.AuthRoleResource;
import com.payment.entity.groupwormhole.AuthUser;
import com.payment.entity.groupwormhole.AuthUserRole;
import com.payment.entity.publicenitty.ErrorEnum;
import com.payment.entity.publicenitty.Result;
import com.payment.mapper.groupwormholemapper.AuthControlMapper;
import com.payment.service.groupwormholeimpl.LocalCacheAuthorityImpl;
import com.payment.service.groupwormholeservice.AuthorityService;
import com.payment.service.redis.RedisAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/3/7.
 */
public class AuthControlHelper {

   /**
     * 刷新缓存
     *
     * @return
     */
    public Result refreshAuthCache() {
        AuthControlMapper authControlMapper = (AuthControlMapper) SpringContextUtil.getBean("authControlMapper");
        RedisAuthorityService redisAuthorityService = (RedisAuthorityService) SpringContextUtil.getBean("redisAuthorityService");
        Map<String, AuthUser> userCacheMap = new HashMap<>();
        try {
            List<AuthRoleResource> authorityList = authControlMapper.selectRoleResource(null);

            if (CommonUtil.listIsNotBlank(authorityList)) {
                List<AuthUserRole> authUserRoleList = authControlMapper.selectUserRole(null);
                if (CommonUtil.listIsNotBlank(authorityList)) {
                    String employeeNo;
                    for (AuthUserRole aur : authUserRoleList) {
                        if (aur != null && aur.getUser() != null && aur.getRole() != null) {
                            employeeNo = aur.getUser().getEmployeeNumber();
                            //查找用户对应角色的权限信息
                            Integer roleId = aur.getRole().getId();
                            if (roleId == null) {
                                continue;
                            }
                            userCacheMap.put(employeeNo, aur.getUser());
                            for (AuthRoleResource aa : authorityList) {
                                //找到角色的权限信息
                                if (aa != null && aa.getRole() != null && aa.getRole().getId() != null && Integer.compare(roleId, aa.getRole().getId()) == 0) {
                                    LocalCacheAuthorityImpl.addCacheItem(
                                            employeeNo.toUpperCase(),
                                            aa.getResource().getUrl(),
                                            aa.getResource().getReqMethod(),
                                            aa.getResource().getCode());
                                }
                            }
                        }
                    }
                }
            }
            //  logger.info("Refresh cache success, cache size: {}", LocalCacheAuthorityService.cacheMap.size());
        } catch (Exception e) {
            //  logger.error("Refresh cache exception, detail: ", e);
            return Result.FAILURE("内部异常：" + e.getMessage());
        }
        //刷新redis缓存
        Result result = redisAuthorityService.refreshCache(LocalCacheAuthorityImpl.cacheMap);
        Result resultUser = redisAuthorityService.refreshUserCache(userCacheMap);
        return Result.SUCCESS();
    }


    /**
     * 权限校验。根据员工号。校验URL+ReqMethod
     *
     * @param employeeId 员工号
     * @param url        URL
     * @param reqMethod  ReqMethod
     * @return Result
     */
    public Result validateAuthority(String employeeId, String url, String reqMethod) {
        String type = "REDIS";
        //先通过redis校验，如果异常，再通过local校验
        RedisAuthorityService redisAuthorityService = (RedisAuthorityService) SpringContextUtil.getBean("redisAuthorityService");
        Result result = redisAuthorityService.validateAuthority(employeeId, url, reqMethod);
        //Redis异常
        if (!result.isSuccess() && StringUtils.safeString(result.getResultCode()).equalsIgnoreCase(ErrorEnum.THIRTY_PARTY_ERROR.getCode())) {
            type = "LOCAL-CACHE";
            //通过本地缓存校验
            result = getAuthorityService(type).validateAuthority(employeeId, url, reqMethod);
        }
        return result;
    }


    //工厂方法
    public AuthorityService getAuthorityService(String name) {
        AuthorityService service;
//        switch (name.toUpperCase()) {
//            case "REDIS": {
//               // service = redisAuthorityService;
//            }
//            break;
//            //默认为local
//            default: {
//                service = new LocalCacheAuthorityImpl();
//            }
//        }
        service = new LocalCacheAuthorityImpl();
        return service;
    }


    private AuthControlHelper() {
        refreshAuthCache();
    }

    public static AuthControlHelper getInstance() {
        return SingletonHolder.cache;
    }

    private static class SingletonHolder {
        private static final AuthControlHelper cache = new AuthControlHelper();
    }
}
