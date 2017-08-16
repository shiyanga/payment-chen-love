package com.payment.controller;

import com.alibaba.fastjson.JSON;
import com.payment.common.util.JacksonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.dbback.DBQueryDTO;
import com.payment.entity.dbback.DBTableRowUpdateDTO;
import com.payment.entity.groupwormhole.*;
import com.payment.entity.groupwormhole.contract.*;
import com.payment.entity.publicenitty.Response;
import com.payment.entity.publicenitty.Result;
import com.payment.global.AuthControlHelper;
import com.payment.service.backdoor.DBBackDoorService;
import com.payment.service.backdoor.DBEnum;
import com.payment.service.groupwormholeservice.AuthControlService;
import com.payment.service.groupwormholeservice.ConfigureService;
import com.payment.service.redis.RedisServiceImpl;
import org.apache.commons.collections.keyvalue.DefaultKeyValue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/17.
 */
@Controller
public class SystemController extends BasicController {
    Logger logger = Logger.getLogger(SystemController.class);
    @Autowired
    private DBBackDoorService dbBackDoorService;

    @Autowired
    private AuthControlService authControlService;

    @Autowired
    private ConfigureService configureService;

    @Autowired
    private RedisServiceImpl redisService;

    //region   UserController 【用户】管理
    @RequestMapping(value = "/sys/authority/user", method = RequestMethod.GET)
    public String systemUserList() {


//        boolean bl = redisService.exists("tutorial-list");

        try {
            String str = JacksonUtil.obj2json(authControlService.queryAuthRole());
            redisService.set("authAll", str, 60);
            List<AuthRole> authRoleList = JacksonUtil.json2list(str, AuthRole.class);

        } catch (Exception ex) {

        }
        return "system/user";
    }


    @RequestMapping(value = {"/sys/authority/user/query", "/sys/authority/role/all-user/query"}, method = RequestMethod.GET)
    @ResponseBody
    public Response userQuery(String jsonParam) {
        try {
            AuthUserRequest request = JacksonUtil.json2pojo(jsonParam, AuthUserRequest.class);
            List<AuthUser> users = authControlService.queryAuthUser(request == null ? null : request.getPage());
            return new Response(DTOConvert.entity2DTO(users, AuthUserDTO.class), request != null ? request.getPage() : null);
        } catch (Exception e) {
            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }

    @RequestMapping(value = "/sys/authority/user/update", method = RequestMethod.PATCH)
    @ResponseBody
    public Response userUpdate(Integer userId, String columnName, String columnValue) {

        if (userId == null || StringUtils.isBlank(columnName)) {
            return Response.resultResponse(Result.FAILURE("参数不合法"));
        }
        Result result = new Result();
        try {
            Map<String, Object> params = new HashMap<>();
            params.put(columnName, columnValue);
            result = authControlService.updateAuthUser(userId, params);

            //如果更新成功，且改的是状态，则进行缓存刷新
            if (result.isSuccess() && "status".equalsIgnoreCase(columnName)) {
                //       AuthControlHelper.getInstance().refreshAuthCache();
            }
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    @RequestMapping(value = "/sys/authority/user/add", method = RequestMethod.PUT)
    @ResponseBody
    public Response userAdd(AuthUser user) {


        Result result;
        try {
            result = authControlService.addAuthUser(user);
        } catch (Exception e) {

            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    @RequestMapping(value = "/sys/authority/user/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response userDelete(Integer userId) {
        Result result;
        try {
            result = authControlService.deleteAuthUser(userId);
            if (result.isSuccess()) {
                //      AuthControlHelper.getInstance().refreshAuthCache();
            }
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    //endregion

    //region ResourceController 【权限资源】管理
    @RequestMapping(value = "/sys/authority/resource", method = RequestMethod.GET)
    public ModelAndView resourcePageForward() {
        return modelAndView("system/resource");
    }

    @RequestMapping(value = {"/sys/authority/resource/query", "/sys/authority/role/all-resource/query"}, method = RequestMethod.GET)
    @ResponseBody
    public Response resourceQuery(String blurResourceDesc) {
        try {
            List<AuthResource> resources = authControlService.queryAuthResource(blurResourceDesc);
            return new Response(DTOConvert.entity2DTO(resources, AuthResourceDTO.class));
        } catch (Exception e) {
            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }


    @RequestMapping(value = "/sys/authority/resource/add", method = RequestMethod.PUT)
    @ResponseBody
    public Response resourceAdd(AuthResource resource) {
        Result result;
        try {
            result = authControlService.addAuthResource(resource);
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    @ResponseBody
    public Response resourceUpdate(Integer resourceId, String columnName, String columnValue) {
        if (resourceId == null || StringUtils.isBlank(columnName)) {
            return Response.resultResponse(Result.FAILURE("参数不合法"));
        }
        Result result;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put(columnName, columnValue);
            result = authControlService.updateAuthResource(resourceId, params);
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }

    @RequestMapping(value = "/sys/authority/resource/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response resourceDelete(Integer resourceId) {
        Result result;
        try {
            result = authControlService.deleteAuthResource(resourceId);

            if (result.isSuccess()) {
                //    AuthControlHelper.getInstance().refreshAuthCache();
            }
            return Response.resultResponse(result);
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);

    }

    //endregion

    //region  ConfigureController[配置文件] 管理

    /**
     * 页面跳转 - configure
     *
     * @return view
     */
    @RequestMapping("/sys/configure")
    public ModelAndView pageForward() {
        return modelAndView("system/configure");
    }

    /**
     * 多条件查询，支持分页
     * configure?status=1&currentPage=1&pageSize=2
     *
     * @param jsonParam jsonParam
     * @return response
     */
    @RequestMapping(value = "/sys/configure/query", method = RequestMethod.GET)
    @ResponseBody
    public Response queryMultiCondition(String jsonParam) {
        ConfigureRequest request = JacksonUtil.json2pojo(jsonParam, ConfigureRequest.class);
        if (request == null) {
            return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS);
        }

        List<Configure> configureList = new ArrayList<>();
        //如果primary不为空，先根据primaryKey查询
        if (request.getPrimaryKey() > 0) {
            Configure configure = configureService.queryByPrimaryKey(request.getPrimaryKey());
            if (configure != null) {
                configureList.add(configure);
                request.getPage().setTotalRecord(1);
            }
        } else {
            configureList = configureService.queryMultiConditions(request.getGroup(), request.getKey(), request.getOrderColumn(), request.getOrderDir(), request.getPage());
        }

        return new Response(DTOConvert.entity2DTO(configureList, ConfigureDTO.class), request != null ? request.getPage() : null);


    }


    @RequestMapping(value = "/sys/configure/add", method = RequestMethod.PUT)
    @ResponseBody
    public Response insert(Configure configure) {
        if (configure == null ||
                org.apache.commons.lang.StringUtils.isBlank(configure.getGroup()) ||
                org.apache.commons.lang.StringUtils.isBlank(configure.getKey()) ||
                org.apache.commons.lang.StringUtils.isBlank(configure.getValue())) {
            return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS);
        }
        int ret = configureService.insert(configure);
        if (ret > 0) {
            return Response.SUCCESS;
        } else {
            return Response.FAILURE(Response.ResultCodeEnum.FAILURE, ret == -2 ? "Duplicate record" : "");
        }
    }

    @RequestMapping(value = "/sys/configure/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response delete(int primaryKey) {
        if (primaryKey <= 0) {
            return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS);
        }
        int ret = configureService.deleteByPrimaryKey(primaryKey);
        if (ret > 0) {
            return Response.SUCCESS;
        } else {
            return Response.FAILURE(Response.ResultCodeEnum.FAILURE, "Delete record failed!");
        }
    }


    /**
     * configure
     * configure?id=1 ...
     *
     * @return response
     */
    @RequestMapping(value = "/sys/configure/update", method = RequestMethod.PATCH)
    @ResponseBody
    public Response update(@RequestParam(value = "conId", required = true) Integer conid,
                           @RequestParam(value = "conColumn", required = true) String column,
                           @RequestParam(value = "conValue", required = true) String value) {
        //参数校验
        if (conid == null || org.apache.commons.lang.StringUtils.isBlank(column) || org.apache.commons.lang.StringUtils.isBlank(value)) {
            return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS);
        }

        Result updateResult;
        switch (column) {
            case "value": {
                if (value.length() >= 5000) {
                    return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS, "字段长度不能>5000");
                }
                updateResult = configureService.updateConfigure(conid, value, null);
                break;
            }
            case "desc": {
                if (value.length() >= 300) {
                    return Response.FAILURE(Response.ResultCodeEnum.INVALID_ARGUMENTS, "字段长度不能>300");
                }
                updateResult = configureService.updateConfigure(conid, null, value);
                break;
            }
            default:
                updateResult = Result.FAILURE("不支持更新列 - " + column);
                break;
        }

        return Response.resultResponse(updateResult);
    }


    //endregion

    //region  dbupdateController  修改数据库记录
    @RequestMapping(value = "/sys/db-back-door", method = RequestMethod.GET)
    public String dbUpdateList() {
        return "system/dbupdate";
    }

    @RequestMapping(value = "/system/dbupdate/queryOneRecord", method = RequestMethod.GET)
    @ResponseBody
    public Response queryOneRecord(String query) throws Exception {
        DBQueryDTO queryDTO = JSON.parseObject(query, DBQueryDTO.class);

        dbBackDoorService.setDB(DBEnum.getItemByCode(queryDTO.getDbEnumCode()));
        String recordsJson = dbBackDoorService.queryRecordsAsJson(queryDTO.getTableName(),
                new DefaultKeyValue(queryDTO.getQueryFieldName(), queryDTO.getQueryFieldValue()));

        return new Response(recordsJson);
    }


    @RequestMapping(value = "/system/dbupdate/updateOneRecord", method = RequestMethod.POST)
    @ResponseBody
    public Response updateOneRecord(@RequestParam("param") String param) throws Exception {
        DBTableRowUpdateDTO updateDTO = JSON.parseObject(param, DBTableRowUpdateDTO.class);

        DBQueryDTO query = updateDTO.getQuery();
        dbBackDoorService.setDB(DBEnum.getItemByCode(query.getDbEnumCode()));
        int updated = dbBackDoorService.updateRecordField(
                query.getTableName(),
                new DefaultKeyValue(query.getQueryFieldName(), query.getQueryFieldValue()),
                new DefaultKeyValue(updateDTO.getUpdateFieldName(), updateDTO.getUpdateFieldValue())
        );

        return new Response(Result.SUCCESS());
    }


    //endregion

    //region  roleController  角色管理


    @RequestMapping(value = "/sys/authority/role", method = RequestMethod.GET)
    public ModelAndView rolePageForward() {
        return modelAndView("system/role");
    }


    //根据角色查询权限列表
    @RequestMapping(value = "/sys/authority/role/query", method = RequestMethod.GET)
    @ResponseBody
    public Response roleQuery(String jsonParam) {
        //logger.employee(loginedAccount.getEmployee()).info("query role");

        try {
            AuthRoleRequest request = JacksonUtil.json2pojo(jsonParam, AuthRoleRequest.class);
            List<AuthRole> roles = authControlService.queryAuthRole();
            return new Response(DTOConvert.entity2DTO(roles, AuthRoleDTO.class));
        } catch (Exception e) {
            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }


    @RequestMapping(value = "/sys/authority/role/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response roleDelete(Integer roleId) {
        Result result;
        try {
            result = authControlService.deleteAuthRole(roleId);

            if (result.isSuccess()) {
                //   AuthControlHelper.getInstance().refreshAuthCache();
            }
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    //根据角色查询权限列表
    @RequestMapping(value = "/sys/authority/role/update", method = RequestMethod.PATCH)
    @ResponseBody
    public Response roleUpdate(Integer roleId, String columnName, String columnValue) {
        if (roleId == null || StringUtils.isBlank(columnName)) {
            return Response.resultResponse(Result.FAILURE("参数不合法"));
        }
        Result result;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put(columnName, columnValue);
            result = authControlService.updateAuthRole(roleId, params);
        } catch (Exception e) {
            // logger.employee(loginedAccount.getEmployee()).error("update role exception: ", e);
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        //   logger.employee(loginedAccount.getEmployee()).info("update role, result: {}", result);
        return Response.resultResponse(result);
    }

    @RequestMapping(value = "/sys/authority/role/add", method = RequestMethod.PUT)
    @ResponseBody
    public Response roleAdd(AuthRole role) {
        Result result;
        try {
            result = authControlService.addAuthRole(role);
        } catch (Exception e) {
            result = Result.FAILURE("内部异常：" + e.getMessage());
        }
        return Response.resultResponse(result);
    }


    //查询角色下的用户列表
    @RequestMapping(value = "/sys/authority/role/user/query", method = RequestMethod.GET)
    @ResponseBody
    public Response roleUserQuery(Integer roleId) {

        if (roleId == null || roleId <= 0) {
            return Response.resultResponse(Result.FAILURE("参数不合法， 角色ID不能为空"));
        }
        try {
            List<AuthUserRole> userRoles = authControlService.queryUserRole(roleId);
            return new Response(DTOConvert.entity2DTO(userRoles, AuthUserRoleDTO.class));
        } catch (Exception e) {
            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }


    @RequestMapping(value = "/sys/authority/role/user/add", method = RequestMethod.PUT)
    @ResponseBody
    public Response roleUserAdd(Integer roleId, Integer userId) {
        try {
            Result result = authControlService.addUserForRole(roleId, userId);
            if (result.isSuccess()) {
                //  AuthControlHelper.getInstance().refreshAuthCache();
            }
            return Response.resultResponse(result);
        } catch (Exception e) {

            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }


    @RequestMapping(value = "/sys/authority/role/user/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response roleUserDelete(Integer roleId, Integer userId) {

        try {
            Result result = authControlService.removeUserForRole(roleId, userId);
            if (result.isSuccess()) {
                //   AuthControlHelper.getInstance().refreshAuthCache();
            }
            return Response.resultResponse(result);
        } catch (Exception e) {

            return Response.resultResponse(Result.FAILURE("内部异常：" + e.getMessage()));
        }
    }
    //endregion

    //region 权限缓存管理
    /**
     * 刷新权限缓存
     *
     * @return response
     */
    @RequestMapping(value = "/sys/authority/cache/refresh", method = RequestMethod.GET)
    @ResponseBody
    public Response refreshAuthority() {
        try {
            AuthControlHelper.getInstance().refreshAuthCache();
        } catch (Exception e) {
            // logger.error("Refresh Cache exception. ", e);
        }
        return Response.SUCCESS;
    }
    //endregion
}


