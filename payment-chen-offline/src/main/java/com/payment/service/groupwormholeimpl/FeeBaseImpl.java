package com.payment.service.groupwormholeimpl;

import com.payment.entity.groupwormhole.FeeBase;
import com.payment.mapper.groupwormholemapper.FeeBaseMapper;
import com.payment.service.groupwormholeservice.FeeBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2016/9/1.
 */
@Service
public class FeeBaseImpl implements FeeBaseService {

    @Autowired
    private FeeBaseMapper feeBaseMapper;

    @Override
    public List<FeeBase> selectList(Integer roleId) {
        try {
            Map<String, Object> param = new HashMap<String,Object>();
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
