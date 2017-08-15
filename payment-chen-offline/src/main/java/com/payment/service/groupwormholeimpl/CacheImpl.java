package com.payment.service.groupwormholeimpl;

import com.payment.entity.groupwormhole.FeeBase;
import com.payment.mapper.groupwormholemapper.FeeBaseMapper;
import com.payment.service.groupwormholeservice.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/3/1.
 */
@Service
public class CacheImpl implements CacheService {
    @Autowired
    private FeeBaseMapper feeBaseMapper;


    @Override
    @Cacheable(value = "default")
    public String defaultCache(String name) {
        System.err.println("db start break defaultCache");
        return "defaultCache";
    }

    @Override
    @Cacheable(value = "guavaCache60seconds")
    public String guavaCache60seconds(String name) {
        System.err.println("db start break guavaCache60seconds");
        return "guavaCache60seconds";


    }


    @Override
    @Cacheable(value = "guavaCache10minutes")
    public String guavaCache10minutes(String name) {
        System.err.println("db start break guavaCache10minutes");
        return "guavaCache10minutes";
    }

    @Override
    @Cacheable(value = "guavaCache1hour")
    public String guavaCache1hour(String name) {
        System.err.println("db start break guavaCache1hour");
        return "guavaCache1hour";
    }


    @Override
    @Cacheable(value = "guavaCache60seconds")
    public List<FeeBase> selectList(Integer roleId) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            if (roleId != null) {
                param.put("roleId", roleId);
            }
            List<FeeBase> resources = feeBaseMapper.selectList(param);
            return resources;
        } catch (Exception e) {
            // logger.error("query auth resource exception, detail: ", e);
        }
        return null;
    }

}
