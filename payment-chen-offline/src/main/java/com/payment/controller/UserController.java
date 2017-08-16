package com.payment.controller;

import com.payment.common.util.CommonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.groupwormhole.AuthUser;
import com.payment.entity.groupwormhole.TokenModel;
import com.payment.entity.publicenitty.Result;
import com.payment.mapper.groupwormholemapper.AuthControlMapper;
import com.payment.service.groupwormholeservice.TokenManager;
import com.payment.service.redis.RedisAuthorityService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/2/22.
 */
@Controller
public class UserController extends BasicController {

    @Autowired
    private TokenManager tokenManager;

    @Autowired
    private AuthControlMapper authControlMapper;

    @Autowired
    private RedisAuthorityService redisAuthorityService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "login/userlogin", method = RequestMethod.POST)
    public ModelAndView userlogin(@RequestParam String username, @RequestParam String password, HttpServletRequest request,
                                  HttpServletResponse response) {

        AuthUser authUser = new AuthUser();
        authUser.setId(1000);
        authUser.setEmployeeNumber("A00072");
        authUser.setName("石阳");


        Map<String, Object> param = new HashMap<>();
        param.put("employeeNumber",username);
        param.put("loginPassword",DigestUtils.md5Hex(password));
        Map<String, Object> data = new HashMap<>();

        List<AuthUser> users = authControlMapper.selectAuthUser(param);
        if(CommonUtil.listIsNotBlank(users))
        {
            data.put("error","yes");
            return modelAndView("login",data);
        }


        data.put("error","no");

        redisAuthorityService.getRedisUserByEmployeeId(authUser.getEmployeeNumber());
       //生成一个token，保存用户登录状态
        TokenModel model = tokenManager.createToken(authUser.getId());
        Map<String, Object> params = new HashMap<>();
        params.put("UserToken", model);
        Result result = new Result();
        result.setResultCode(0);
        result.setResultMsg("成功");
        addCookie(response, "randomkey", model.getUserId() + "_" + model.getToken(), 72000);
        return modelAndView("welcome",data);
    }


    /**
     * 设置cookie
     *
     * @param response
     * @param name     cookie名字
     * @param value    cookie值
     * @param maxAge   cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

}
