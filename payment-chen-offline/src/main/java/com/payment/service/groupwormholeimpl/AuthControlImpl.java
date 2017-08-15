package com.payment.service.groupwormholeimpl;

import com.github.pagehelper.PageHelper;
import com.payment.entity.groupwormhole.*;
import com.payment.entity.publicenitty.Page;
import com.payment.entity.publicenitty.Result;
import com.payment.mapper.groupwormholemapper.AuthControlMapper;
import com.payment.service.groupwormholeservice.AuthControlService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import payment.chen.service.common.util.CommonUtil;
import payment.chen.service.common.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/19.
 */
@Service
public class AuthControlImpl implements AuthControlService {
    Logger logger = Logger.getLogger(AuthControlImpl.class);


    @Autowired
    private AuthControlMapper authControlMapper;


    @Override
    public List<AuthResource> queryAuthResource(String blurResourceDesc) {
        try {
            Map<String, Object> param = new HashMap<>();
            if (StringUtils.isNotBlank(blurResourceDesc)) {
                param.put("blurResourceDesc", blurResourceDesc);
            }
            List<AuthResource> resources = authControlMapper.selectAuthResource(param);
            return AuthResource.convert2Tree(resources);
        } catch (Exception e) {
            logger.error("query auth resource exception, detail: ", e);
        }
        return null;
    }
    @Override
    public Result addAuthResource(AuthResource resource) {

        if (resource == null || (resource.getParentId() != null && resource.getParentId() <= 0) || StringUtils.isBlank(resource.getCode())) {
            return Result.FAILURE("参数不合法");
        }
        AuthResource.ReqMethodEnum reqMethodEnum = null;
        if (StringUtils.isNotBlank(resource.getReqMethod())) {
            reqMethodEnum = AuthResource.ReqMethodEnum.getItem(resource.getReqMethod());
            if (reqMethodEnum == null) {
                return Result.FAILURE("无效的请求方法-" + resource.getReqMethod());
            }
        }

        try {
            List<AuthResource> resources;
            //检查父资源是否存在
            Map<String, Object> param = new HashMap<>();
            if (resource.getParentId() != null && resource.getParentId() > 0) {
                param.put("resourceId", resource.getParentId());
                resources = authControlMapper.selectAuthResource(param);
                if (CommonUtil.listIsBlank(resources)) {
                    return Result.FAILURE("无效的父资源(id=" + resource.getParentId() + ")");
                }
            } else {
                resource.setParentId(null);
            }

            //检查是否有重复
            param = new HashMap<>();
            param.put("resourceCode", resource.getCode());
            if (StringUtils.isNotBlank(resource.getUrl())) {
                param.put("resourceUrl", resource.getUrl());
            }
            if (StringUtils.isNotBlank(resource.getReqMethod())) {
                param.put("resourceReqType", resource.getReqMethod());
            }
            resources = authControlMapper.selectAuthResource(param);
            if (CommonUtil.listIsNotBlank(resources)) {
                return Result.FAILURE("已经存在相同的的权限资源定义");
            }

            AuthResource authResource = new AuthResource(resource.getParentId(), resource.getCode(), resource.getDescription(), resource.getUrl(), reqMethodEnum == null ? null : reqMethodEnum.getCode());
            int ret = authControlMapper.insertAuthResource(authResource);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能插入数据到DB");

        } catch (Exception e) {
            logger.error("add auth resource exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result updateAuthResource(Integer resourceId, Map<String, Object> columnsMap) {

        if (resourceId == null || resourceId <= 0 || columnsMap == null || columnsMap.size() == 0) {
            return Result.FAILURE("参数不合法");
        }
        try {
            //检查是否有记录
            Map<String, Object> param = new HashMap<>();
            param.put("resourceId", resourceId);
            List<AuthResource> resources = authControlMapper.selectAuthResource(param);
            if (CommonUtil.listIsBlank(resources)) {
                return Result.FAILURE("找不到需要更新的记录(ResourceID=" + resourceId + ")");
            }

            AuthResource newResource = new AuthResource();
            newResource.setId(resourceId);
            //初始不设置值
            newResource.setParentId(-1);
            for (Map.Entry<String, Object> entry : columnsMap.entrySet()) {
                switch (entry.getKey()) {
                    case "parent":
                        newResource.setParentId((entry.getValue() == null || StringUtils.isBlank(entry.getValue().toString())) ? null : Integer.valueOf(entry.getValue().toString()));
                        break;
                    case "code":
                        newResource.setCode(StringUtils.safeString(entry.getValue()));
                        break;
                    case "description":
                        newResource.setDescription(StringUtils.safeString(entry.getValue()));
                        break;
                    case "url":
                        newResource.setUrl(StringUtils.safeString(entry.getValue()));
                        break;
                    case "reqMethod":
                        newResource.setReqMethod(StringUtils.safeString(entry.getValue()));
                        break;
                    default:
                        break;
                }
            }

            int ret = authControlMapper.updateAuthResource(newResource);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能更新到任何数据");

        } catch (Exception e) {
            logger.error("update auth resource exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public List<AuthUser> queryAuthUser(Page page) {
        List<AuthUser> users = null;
        try {
            Map<String, Object> param = new HashMap<>();
            if(page != null){
                PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
            }
            users = authControlMapper.selectAuthUser(param);
            if(page != null){
                page.setTotalRecord(users);
            }
        } catch (Exception e) {
            logger.error("query auth user exception, detail: {}", e);
        }
        return users;
    }

    @Override
    public Result addAuthUser(AuthUser user) {
        if (user == null || StringUtils.isBlank(user.getEmployeeNumber()) || StringUtils.isBlank(user.getName()) || StringUtils.isBlank(user.getEmail())) {
            return Result.FAILURE("参数不合法");
        }

        AuthUser.DepartmentEnum departmentEnum = AuthUser.DepartmentEnum.getItem(user.getDepartment());
        if (departmentEnum == null) {
            return Result.FAILURE("'部门'赋值不合法");
        }
        AuthUser.StatusEnum statusEnum = AuthUser.StatusEnum.getItem(user.getStatus());
        if (statusEnum == null) {
            return Result.FAILURE("'状态'赋值不合法");
        }
        AuthUser authUser = new AuthUser(
                user.getEmployeeNumber(),
                user.getName(),
                DigestUtils.md5Hex(user.getLoginPassword()),//md5加密，该项目在内网使用，所以没有在传输时加密而只在服务端加密
                user.getEmail(),
                user.getTelephone(),
                user.getDepartment(),
                user.getStatus(),
                user.getRemark()
        );

        try {
            //检查是否有重复
            Map<String, Object> param = new HashMap<>();
            if (StringUtils.isNotBlank(user.getEmployeeNumber())) {
                param.put("employeeNumber", user.getEmployeeNumber());
            }
            if (StringUtils.isNotBlank(user.getEmail())) {
                param.put("email", user.getEmail());
            }
            if (StringUtils.isNotBlank(user.getTelephone())) {
                param.put("telephone", user.getTelephone());
            }
            List<AuthUser> users = authControlMapper.selectAuthUser(param);
            if (CommonUtil.listIsNotBlank(users)) {
                return Result.FAILURE("已经存在相同的用户");
            }
            int ret = authControlMapper.insertAuthUser(authUser);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能插入数据到DB");

        } catch (Exception e) {
            logger.error("add auth user exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result updateAuthUser(Integer userId, Map<String, Object> columnsMap) {
        if (userId == null || userId <= 0 || columnsMap == null || columnsMap.size() == 0) {
            return Result.FAILURE("参数不合法");
        }
        try {
            //检查是否有记录
            Map<String, Object> param = new HashMap<>();
            param.put("userId", userId);
            List<AuthUser> users = authControlMapper.selectAuthUser(param);
            if (CommonUtil.listIsBlank(users)) {
                return Result.FAILURE("找不到需要更新的记录(UserID=" + userId + ")");
            }

            AuthUser newUser = new AuthUser();
            newUser.setId(userId);
            for (Map.Entry<String, Object> entry : columnsMap.entrySet()) {
                switch (entry.getKey()) {
                    case "employeeNumber":
                        newUser.setEmployeeNumber(StringUtils.safeString(entry.getValue()));
                        break;
                    case "loginPassword":
                        String pwd = entry.getValue().toString();
                        if (StringUtils.isBlank(pwd) || pwd.length() < 3 || pwd.length()>10) {
                            throw new Exception("密码长度必须在3-10个字符之间");
                        }
                        newUser.setLoginPassword(DigestUtils.md5Hex(pwd));//储存md5加密后的密码
                        break;
                    case "name":
                        newUser.setName(StringUtils.safeString(entry.getValue()));
                        break;
                    case "email":
                        newUser.setEmail(StringUtils.safeString(entry.getValue()));
                        break;
                    case "telephone":
                        newUser.setTelephone(StringUtils.safeString(entry.getValue()));
                        break;
                    case "department":
                        newUser.setDepartment(StringUtils.safeString(entry.getValue()));
                        break;
                    case "status":
                        newUser.setStatus(Integer.valueOf(entry.getValue().toString()));
                        break;
                    case "remark":
                        newUser.setRemark(StringUtils.safeString(entry.getValue()));
                        break;
                    default:
                        break;
                }
            }

            int ret = authControlMapper.updateAuthUser(newUser);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能更新到任何数据");

        } catch (Exception e) {
            logger.error("update auth user exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    /**
     * 删除用户
     * 1、校验用户是否存在
     * 2、删除 用户表的用户
     * 3、删除 用户角色表的数据，和2一个事物
     *
     * @param userId 用户ID
     * @return Result
     */
    @Override
    @Transactional
    public Result deleteAuthUser(Integer userId) {
        if (userId == null || userId <= 0) {
            return Result.FAILURE("参数不合法");
        }

        try {
            //检查是否有重复
            Map<String, Object> param = new HashMap<>();
            param.put("userId", userId);
            List<AuthUser> users = authControlMapper.selectAuthUser(param);
            if (CommonUtil.listIsBlank(users)) {
                return Result.FAILURE("未找到该用户(ID=" + userId + ")");
            }
            //尝试删除用户
            int ret = authControlMapper.deleteUser(userId);
            if (ret != 1) {
                throw new Exception("删除用户失败：未能成功删除DB记录");
            }

            //尝试删除 用户-角色表数据
            authControlMapper.deleteUserRoleByUserId(userId);

            return Result.SUCCESS();
        } catch (Exception e) {
            logger.error("delete auth user exception, detail: ", e);
            //异常回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    /**
     * 删除指定的资源信息
     * 1、检查资源是否存在
     * 2、从resource表删除资源，包括当前记录 + parent为此记录的记录
     *
     * @param resourceId 资源ID
     * @return result
     */
    @Override @Transactional
    public Result deleteAuthResource(Integer resourceId) {
        if (resourceId == null || resourceId <= 0) {
            return Result.FAILURE("参数不合法");
        }

        try {
            //检查是否有记录
            Map<String, Object> param = new HashMap<>();
            param.put("resourceId", resourceId);
            List<AuthResource> resources = authControlMapper.selectAuthResource(param);
            if (CommonUtil.listIsBlank(resources)) {
                return Result.FAILURE("未找到该资源(ResourceID=" + resourceId + ")");
            }

            //查询出所有的 要删除的资源的子集
            List<AuthResource> authResources = authControlMapper.selectAuthResource(null);

            List<AuthResource> children = AuthResource.queryChildren(authResources, resourceId);
            if (CommonUtil.listIsBlank(children)) {
                return Result.FAILURE("递归查询节点失败(ResourceID=" + resourceId + ")");
            }
            //TODO 循环删除，注意记录非常多的情况下的 DB连接池暴增的情况
            for (AuthResource ar : children) {
                //尝试删除资源表记录
                int ret = authControlMapper.deleteResource(ar.getId());
              //  logger.info("delete resource record(ResourceID={}), affected rows:{}", ar.getId(), ret);
                if (ret != 1) {
                    throw new Exception("删除资源失败：未能成功删除DB记录");
                }
                //尝试删除 角色-资源表数据
                ret = authControlMapper.deleteRoleResourceByResourceId(ar.getId());
             //   logger.info("delete role-resource record(ResourceID={}), affected rows:{}", ar.getId(), ret);
            }

            return Result.SUCCESS();
        } catch (Exception e) {
            logger.error("delete auth resource exception, detail: ", e);
            //异常回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public List<AuthRole> queryAuthRole() {
        try {
            Map<String, Object> param = new HashMap<>();

            return authControlMapper.selectAuthRole(param);
        } catch (Exception e) {
            logger.error("query auth role exception, detail: ", e);
        }
        return null;
    }

    /**
     * 删除角色
     * 1、校验角色是否存在
     * 2、删除 角色表的用户
     * 3、删除 角色表的数据，和2一个事物
     *
     * @param roleId 角色ID
     * @return Result
     */
    @Override
    @Transactional
    public Result deleteAuthRole(Integer roleId) {
        if (roleId == null || roleId <= 0) {
            return Result.FAILURE("参数不合法");
        }

        try {
            //检查是否有重复
            Map<String, Object> param = new HashMap<>();
            param.put("roleId", roleId);
            List<AuthRole> roles = authControlMapper.selectAuthRole(param);
            if (CommonUtil.listIsBlank(roles)) {
                return Result.FAILURE("未找到该角色(ID=" + roleId + ")");
            }
            //尝试删除角色记录
            int ret = authControlMapper.deleteRole(roleId);
          //  logger.info("delete records of role, affected rows: {}", ret);

            if (ret != 1) {
                throw new Exception("删除角色失败：未能成功删除DB记录");
            }

            //尝试删除 用户-角色表数据
            ret = authControlMapper.deleteUserRoleByRoleId(roleId);
       //     logger.info("delete records of user-role, affected rows: {}", ret);

            //尝试删除 角色-资源表记录
            ret = authControlMapper.deleteRoleResourceByRoleId(roleId);
          //  logger.info("delete records of role-resource, affected rows: {}", ret);

            return Result.SUCCESS();
        } catch (Exception e) {
            logger.error("delete auth role exception, detail: ", e);
            //异常回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result updateAuthRole(Integer roleId, Map<String, Object> columnsMap) {
        if (roleId == null || roleId <= 0 || columnsMap == null || columnsMap.size() == 0) {
            return Result.FAILURE("参数不合法");
        }
        try {
            //检查是否有记录
            Map<String, Object> param = new HashMap<>();
            param.put("roleId", roleId);
            List<AuthRole> roles = authControlMapper.selectAuthRole(param);
            if (CommonUtil.listIsBlank(roles)) {
                return Result.FAILURE("找不到需要更新的记录(RoleId=" + roleId + ")");
            }

            AuthRole newRole = new AuthRole();
            newRole.setId(roleId);
            for (Map.Entry<String, Object> entry : columnsMap.entrySet()) {
                switch (entry.getKey()) {
                    case "name":
                        newRole.setName(StringUtils.safeString(entry.getValue()));
                        break;
                    case "description":
                        newRole.setDescription(StringUtils.safeString(entry.getValue()));
                        break;
                    default:
                        break;
                }
            }

            int ret = authControlMapper.updateAuthRole(newRole);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能更新到任何数据");

        } catch (Exception e) {
            logger.error("update auth role exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result addAuthRole(AuthRole role) {
        if (role == null || StringUtils.isBlank(role.getName())) {
            return Result.FAILURE("参数不合法");
        }

        AuthRole authRole = new AuthRole(
                role.getName(),
                role.getDescription(),
                AuthRole.StatusEnum.NORMAL.getCode()
        );

        try {
            //检查是否有重复
            Map<String, Object> param = new HashMap<>();
            param.put("name", role.getName());

            List<AuthRole> roles = authControlMapper.selectAuthRole(param);
            if (CommonUtil.listIsNotBlank(roles)) {
                return Result.FAILURE("已经存在相同的角色定义");
            }
            int ret = authControlMapper.insertAuthRole(authRole);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能插入数据到DB");

        } catch (Exception e) {
            logger.error("add auth user exception, detail: ", e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public List<AuthRoleResource> queryRoleResource(Integer roleId) {
        try {
            Map<String, Object> param = new HashMap<>();
            if (roleId != null && roleId > 0) {
                param.put("roleId", roleId);
            }
            return authControlMapper.selectRoleResource(param);
        } catch (Exception e) {
            logger.error("query auth role exception, detail: ", e);
        }
        return null;
    }

    @Override
    public Result addResourceForRole(Integer roleId, Integer resourceId) {
        if (roleId == null || resourceId == null || roleId <= 0 || resourceId <= 0) {
            return Result.FAILURE("参数不合法，角色ID和资源ID不能为空");
        }
        try {
            //判断roleID是否存在
            Map<String, Object> params = new HashMap<>();
            params.put("roleId", roleId);
            List<AuthRole> roles = authControlMapper.selectAuthRole(params);
            if (CommonUtil.listIsBlank(roles)) {
                return Result.FAILURE("不存在的角色（id=" + roleId + "）");
            }

            params.put("resourceId", resourceId);
            //判断resourceID是否在roleID下,是的话直接返回成功
            List<AuthRoleResource> roleResources = authControlMapper.selectRoleResource(params);
            if (CommonUtil.listIsNotBlank(roleResources)) {
                //已经赋值过了，直接返回成功
                return Result.SUCCESS("已经存在资源(id=" + resourceId + ")的权限");
            }

            //尝试添加
            int ret = authControlMapper.insertRoleResource(roleId, resourceId);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能成功插入数据库");
        } catch (Exception e) {
          //  logger.error("add resource-{} for role-{} exception, detail: {}", resourceId, roleId, e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result removeResourceForRole(Integer roleId, Integer resourceId) {
        if (roleId == null || resourceId == null || roleId <= 0 || resourceId <= 0) {
            return Result.FAILURE("参数不合法，角色ID和资源ID不能为空");
        }
        try {
            //判断roleID+resourceID是否存在
            Map<String, Object> params = new HashMap<>();
            params.put("roleId", roleId);
            params.put("resourceId", resourceId);
            List<AuthRoleResource> roleResources = authControlMapper.selectRoleResource(params);
            if (CommonUtil.listIsBlank(roleResources)) {
                //已经赋值过了，直接返回成功
                return Result.SUCCESS("角色(id=" + roleId + ")不存在对资源(id=" + resourceId + ")的权限");
            }
    //        logger.info("try to delete the RoleResource DB record (id={})", roleResources.get(0).getId());
            //尝试删除
            int ret = authControlMapper.deleteRoleResource(roleResources.get(0).getId());
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能成功从数据库删除数据");
        } catch (Exception e) {
         //   logger.error("remove resource-{} for role-{} exception, detail: {}", resourceId, roleId, e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public List<AuthUserRole> queryUserRole(Integer roleId) {
        try {
            Map<String, Object> param = new HashMap<>();
            if (roleId != null && roleId > 0) {
                param.put("roleId", roleId);
            }
            return authControlMapper.selectUserRole(param);
        } catch (Exception e) {
            logger.error("query user role exception, detail: ", e);
        }
        return null;
    }

    @Override
    public Result addUserForRole(Integer roleId, Integer userId) {
        if (roleId == null || roleId <= 0 || userId == null || userId <= 0) {
            return Result.FAILURE("参数不合法，角色ID和用户ID不能为空");
        }
        try {
            //判断roleID是否存在
            Map<String, Object> params = new HashMap<>();
            params.put("roleId", roleId);
            List<AuthRole> roles = authControlMapper.selectAuthRole(params);
            if (CommonUtil.listIsBlank(roles)) {
                return Result.FAILURE("不存在的角色（id=" + roleId + "）");
            }

            params.put("userId", userId);
            //判断resourceID是否在roleID下,是的话直接返回成功
            List<AuthUserRole> userRoles = authControlMapper.selectUserRole(params);
            if (CommonUtil.listIsNotBlank(userRoles)) {
                //已经赋值过了，直接返回成功
                return Result.SUCCESS("改用户(id=" + userId + ")已经存在此角色(id=" + roleId + ")");
            }

            //尝试添加
            int ret = authControlMapper.insertUserRole(roleId, userId);
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能成功插入数据库");
        } catch (Exception e) {
         //   logger.error("add user-{} for role-{} exception, detail: {}", userId, roleId, e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }

    @Override
    public Result removeUserForRole(Integer roleId, Integer userId) {
        if (roleId == null || roleId <= 0 || userId == null || userId <= 0) {
            return Result.FAILURE("参数不合法，角色ID和用户ID不能为空");
        }
        try {
            //判断roleID+resourceID是否存在
            Map<String, Object> params = new HashMap<>();
            params.put("roleId", roleId);
            params.put("userId", userId);
            List<AuthUserRole> userRoles = authControlMapper.selectUserRole(params);
            if (CommonUtil.listIsBlank(userRoles)) {
                //直接返回成功
                return Result.SUCCESS();
            }
         //   logger.info("try to delete the RoleUser DB record (id={})", userRoles.get(0).getId());
            //尝试删除
            int ret = authControlMapper.deleteUserRole(userRoles.get(0).getId());
            return ret > 0 ? Result.SUCCESS() : Result.FAILURE("未能成功从数据库删除数据");
        } catch (Exception e) {
          //  logger.error("remove user-{} for role-{} exception, detail: {}", userId, roleId, e);
            return Result.FAILURE("内部错误：" + e.getMessage());
        }
    }


}
