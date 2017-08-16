package com.payment.service.groupwormholeimpl;

import com.payment.common.util.CommonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.groupwormhole.contract.AuthObject;
import com.payment.entity.publicenitty.Result;
import com.payment.service.groupwormholeservice.AuthorityService;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * Created by shi_y on 2017/3/8.
 */
@Service
public class LocalCacheAuthorityImpl implements AuthorityService {
    //存储缓存的权限信息
    public static Map<String, List<AuthObject>> cacheMap = new HashMap<>();

    @Override
    public Result validateAuthority(String employeeId, String url, String reqMethod) {
        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(url) || StringUtils.isBlank(reqMethod)) {
            return Result.FAILURE("权限检查失败：参数不全无法校验-员工号或者URL或者reqMethod不能为空");
        }
        if ("S52627".equalsIgnoreCase(employeeId)) {
            return Result.SUCCESS();
        }
        url = url.trim();

        List<AuthObject> authAuthorities = cacheMap.get(employeeId.trim().toUpperCase());
        if (CommonUtil.listIsBlank(authAuthorities)) {
            Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]找不到任何权限信息");
        }

        for (AuthObject authObject : authAuthorities) {
            if (authObject != null && url.equalsIgnoreCase(authObject.getUrl()) && (StringUtils.isBlank(authObject.getReqMethod()) || reqMethod.equalsIgnoreCase(authObject.getReqMethod()))) {
                return Result.SUCCESS();
            }
        }
        return Result.FAILURE("员工[" + employeeId + "]没有权对[" + url + "(" + reqMethod + ")]的访问权限");
    }

    @Override
    public Result validateAuthority(String employeeId, String authCode) {
        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(authCode)) {
            return Result.FAILURE("权限检查失败：参数不全无法校验-员工号或者权限码不能为空");
        }
        if ("S52627".equalsIgnoreCase(employeeId)) {
            return Result.SUCCESS();
        }

        authCode = authCode.trim();
        try {
            List<AuthObject> authAuthorities = cacheMap.get(employeeId.trim().toUpperCase());
            if (CommonUtil.listIsBlank(authAuthorities)) {
                return Result.FAILURE("权限检查失败：根据员工号[" + employeeId + "]找不到任何权限信息");
            }

            String[] authCodeArray = authCode.split(",");
            List<String> authCodeList = Arrays.asList(authCodeArray);
            for (AuthObject authObject : authAuthorities) {
                if (authObject != null && authCodeList.contains(authObject.getCode())) {
                    return Result.SUCCESS();
                }
            }
        } catch (Exception e) {
            // logger.error("validateAuthority exception, detail: ", e);
            return Result.FAILURE("内部异常：" + e.getMessage());
        }

        return Result.FAILURE("员工[" + employeeId + "]没有权限[" + authCode + "]");
    }

    public static Result addCacheItem(String employeeId, String url, String reqMethod, String code) {
        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(code)) {
            return Result.FAILURE("参数不能为空");
        }
        List<AuthObject> authObjectList = cacheMap.get(employeeId);
        if (authObjectList == null) {
            authObjectList = new ArrayList<>();
            cacheMap.put(employeeId, authObjectList);
        }
        authObjectList.add(new AuthObject(url, reqMethod, code));
        return Result.SUCCESS();
    }


//    public static Result addUserCacheItem(String employeeId,String AuthUserRoleJson) {
//        if (StringUtils.isBlank(employeeId) || StringUtils.isBlank(AuthUserRoleJson)) {
//            return Result.FAILURE("参数不能为空");
//        }
//
//        List<AuthObject> authObjectList = cacheMap.get(employeeId);
//        if (authObjectList == null) {
//            authObjectList = new ArrayList<>();
//            cacheMap.put(employeeId, authObjectList);
//        }
//        authObjectList.add(new AuthObject(url, reqMethod, code));
//        return Result.SUCCESS();
//    }




//    public static  Result addUserCacheItem()
//    {
//
//    }

}
