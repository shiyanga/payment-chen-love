package com.payment.service.groupwormholeservice;

import com.payment.entity.groupwormhole.*;
import com.payment.entity.publicenitty.Page;
import com.payment.entity.publicenitty.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/20.
 */
@Repository
public interface AuthControlService {
    List<AuthResource> queryAuthResource(String blurMatchResourceDesc);

    Result addAuthResource(AuthResource resource);

    Result deleteAuthResource(Integer resourceId);

    Result updateAuthResource(Integer resourceId, Map<String, Object> columnsMap);

    List<AuthUser> queryAuthUser(Page page);

    Result addAuthUser(AuthUser user);

    Result updateAuthUser(Integer userId, Map<String, Object> columnsMap);

    Result deleteAuthUser(Integer userId);

    Result deleteAuthRole(Integer roleId);

    List<AuthRole> queryAuthRole();

    Result updateAuthRole(Integer roleId, Map<String, Object> columnsMap);

    Result addAuthRole(AuthRole role);

    List<AuthRoleResource> queryRoleResource(Integer roleId);

    Result addResourceForRole(Integer roleId, Integer resourceId);

    Result removeResourceForRole(Integer roleId, Integer resourceId);

    List<AuthUserRole> queryUserRole(Integer roleId);

    Result addUserForRole(Integer roleId, Integer userId);

    Result removeUserForRole(Integer roleId, Integer userId);
}
