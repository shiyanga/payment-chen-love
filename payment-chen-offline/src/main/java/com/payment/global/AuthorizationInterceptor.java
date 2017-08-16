package com.payment.global;

import com.payment.common.util.CookingUtil;
import com.payment.config.Constants;
import com.payment.entity.enums.ErrorEnum;
import com.payment.entity.groupwormhole.TokenModel;
import com.payment.entity.publicenitty.Result;
import com.payment.global.exception.PayMentOfflineException;
import com.payment.service.groupwormholeservice.TokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Created by shi_y on 2017/2/22.
 * 拦截器 拦截所有请求
 * 权限和登录信息控制
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager manager;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        javax.servlet.http.Cookie[] ck = request.getCookies();

        //判断是否为静态资源
        String reqUrl = request.getRequestURI().substring(request.getContextPath().length());
        if (reqUrl.startsWith("/resources/")) {
            return true;
        }
        if (reqUrl.trim().equalsIgnoreCase("/welcome")) {
            return true;
        }
        if (reqUrl.trim().equalsIgnoreCase("/login")) {
            return true;
        }
        if (reqUrl.trim().equalsIgnoreCase("/login/userlogin")) {
            return true;
        }
        if (reqUrl.trim().equalsIgnoreCase("/account/logout")) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //从header中得到token
        String authorization = CookingUtil.getCookieByName(request, "randomkey").getValue();
        //验证token
        TokenModel model = manager.getToken(authorization);
        if (manager.checkToken(model)) {
            //如果token验证成功，将token对应的用户id存在request中，便于之后注入
//            request.setAttribute(Constants.CURRENT_USER_ID, model.getUserId());
//            return true;

            Result authResult = AuthControlHelper.getInstance().validateAuthority(
                    "A00072",
                    reqUrl,
                    request.getMethod());


        } else {
            return false;
        }


        return true;

//        else {
//            throw new PayMentOfflineException(ErrorEnum.NO_PERMISSION, "");
//        }

        //如果验证token失败，并且方法注明了Authorization，返回401错误
//        if (method.getAnnotation(Authorization.class) != null) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            return false;
//        }
    }

}
