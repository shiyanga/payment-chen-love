package com.payment.controller;

import com.payment.entity.publicenitty.Response;
import org.springframework.web.servlet.ModelAndView;
import payment.chen.service.common.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/5.
 */
public abstract class BasicController {
    protected ModelAndView modelAndView(String page, Map data){
        if(StringUtils.isNotBlank(page) && data != null){
           data.put("CtripAccount", "");
            return new ModelAndView(page, data);
        }
        return errorModelAndView(Response.ResultCodeEnum.UNKNOWN_ERROR);
    }


    protected ModelAndView errorModelAndView(Response.ResultCodeEnum errorEnum){
        String resultPage = "error";
        Map<String, String> errorMap = new HashMap<>();
        if(errorEnum != null){
            errorMap.put("errorCode", errorEnum.getCode());
            errorMap.put("errorMsg", errorEnum.getDesc());
        }

        return new ModelAndView(resultPage, errorMap);
    }

    protected ModelAndView modelAndView(String page){
        if(org.apache.commons.lang.StringUtils.isNotBlank(page)){
         return new ModelAndView(page, "CtripAccount", "");
        }
        return errorModelAndView(Response.ResultCodeEnum.UNKNOWN_ERROR);
    }

}
