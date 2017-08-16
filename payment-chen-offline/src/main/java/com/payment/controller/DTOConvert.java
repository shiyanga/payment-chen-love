package com.payment.controller;

import com.payment.common.util.CommonUtil;
import com.payment.entity.groupwormhole.BnbConfigureEntity;
import com.payment.entity.groupwormhole.contract.ConfigureDTO;
import com.payment.entity.publicenitty.BasicDTO;
import com.payment.entity.publicenitty.Entity;
import com.payment.service.groupwormholeimpl.AuthControlImpl;
import org.apache.log4j.Logger;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shi_y on 2017/1/17.
 */
public class DTOConvert {

    static Logger logger = Logger.getLogger(DTOConvert.class);

    @Deprecated //替换成 entityT2DTO 方法
    public static List<ConfigureDTO> toConfigureDTOList(List<BnbConfigureEntity> configureList) {
        List<ConfigureDTO> configureDTOList = new ArrayList<>();
        if (CommonUtil.listIsNotBlank(configureList)) {
            for (BnbConfigureEntity configure : configureList) {
                if (configure != null) {
                    configureDTOList.add(toConfigureDTO(configure));
                }
            }
        }
        return configureDTOList;
    }


    @Deprecated //替换成 entityT2DTO 方法
    public static ConfigureDTO toConfigureDTO(BnbConfigureEntity configure) {
        if (configure == null) {
            return null;
        }
        ConfigureDTO configureDTO = new ConfigureDTO();
        BeanCopier bc = BeanCopier.create(configure.getClass(), configureDTO.getClass(), false);
        bc.copy(configure, configureDTO, null);
        return configureDTO;
    }


    private static Object objCopy(Object obj, Class<?> targetClass) {
        if (obj == null || targetClass == null) {
            return null;
        }
        Object targetObj = null;
        try {
            targetObj = targetClass.newInstance();
            BeanCopier bc = BeanCopier.create(obj.getClass(), targetClass, false);
            bc.copy(obj, targetObj, null);
            return targetObj;
        } catch (Exception e) {
            logger.error("convert exception. detail: ", e);
        }
        return targetObj;
    }


    public static BasicDTO entity2DTO(Entity entity, Class<? extends BasicDTO> dtoClass) {
        return (BasicDTO) DTOConvert.objCopy(entity, dtoClass);
    }


    public static List<BasicDTO> entity2DTO(List<? extends Entity> entityList, Class<? extends BasicDTO> dtoClass) {
        List<BasicDTO> dtoList = new ArrayList<>();
        if (entityList != null && entityList.size() > 0) {
            for (Entity entity : entityList) {
                if (entity != null) {
                    dtoList.add(entity2DTO(entity, dtoClass));
                }
            }
        }
        return dtoList;
    }


}
