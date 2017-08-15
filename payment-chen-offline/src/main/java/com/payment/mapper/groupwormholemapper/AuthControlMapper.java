package com.payment.mapper.groupwormholemapper;

import com.payment.entity.groupwormhole.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2016/5/13 13:26
 */
public interface AuthControlMapper {
    List<AuthRoleResource> selectRoleResource(Map<String, Object> params) throws DataAccessException;

    List<AuthResource> selectAuthResource(Map<String, Object> params) throws DataAccessException;

    int insertAuthResource(AuthResource resource) throws DataAccessException;

    int updateAuthResource(AuthResource resource) throws DataAccessException;

    List<AuthUser> selectAuthUser(Map<String, Object> params) throws DataAccessException;

    int insertAuthUser(AuthUser user) throws DataAccessException;

    int updateAuthUser(AuthUser user) throws DataAccessException;

    List<AuthRole> selectAuthRole(Map<String, Object> params) throws DataAccessException;

    int updateAuthRole(AuthRole role) throws DataAccessException;

    int insertAuthRole(AuthRole role) throws DataAccessException;

    int insertRoleResource(@Param("roleId") Integer roleId, @Param("resourceId") Integer resourceId) throws DataAccessException;

    int deleteRoleResource(@Param("id") Integer id) throws DataAccessException;

    int deleteResource(@Param("id") Integer id) throws DataAccessException;

    int deleteRoleResourceByRoleId(@Param("roleId") Integer roleId) throws DataAccessException;

    int deleteRoleResourceByResourceId(@Param("resourceId") Integer resourceId) throws DataAccessException;

    int deleteUserRoleByUserId(@Param("userId") Integer userId) throws DataAccessException;

    int deleteUserRoleByRoleId(@Param("roleId") Integer roleId) throws DataAccessException;

    List<AuthUserRole> selectUserRole(Map<String, Object> params) throws DataAccessException;

    int insertUserRole(@Param("roleId") Integer roleId, @Param("userId") Integer userId) throws DataAccessException;

    int deleteUserRole(@Param("id") Integer id) throws DataAccessException;

    int deleteUser(@Param("id") Integer id) throws DataAccessException;

    int deleteRole(@Param("id") Integer id) throws DataAccessException;
}
