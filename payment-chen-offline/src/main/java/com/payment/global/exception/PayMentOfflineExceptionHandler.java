package com.payment.global.exception;

import com.alibaba.fastjson.JSON;
import com.payment.entity.enums.ErrorEnum;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 2015/12/2 17:09
 */

public class PayMentOfflineExceptionHandler extends SimpleMappingExceptionResolver {

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

        ex.printStackTrace();
        ModelAndView modelAndView = new ModelAndView();
        Map<String, String> errorMap = new HashMap<>();
        String errorCode = ErrorEnum.OTHER_ERROR.getCode();
        String errorMsg = ex.getMessage();
        errorMap.put("errorCode", errorCode);
        errorMap.put("errorMsg", errorMsg);

        if (handler instanceof HandlerMethod && ((HandlerMethod) handler).getMethodAnnotation(ResponseBody.class) == null) {
            modelAndView = new ModelAndView("error", errorMap);
        } else {
            if (ex instanceof PayMentOfflineException) {
                PayMentOfflineException me = (PayMentOfflineException) ex;
                errorMap.put("errorCode", me.getCode());
            }
            try {
                response.setHeader("Content-type", "text/html;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(errorMap));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return modelAndView;
    }
}
