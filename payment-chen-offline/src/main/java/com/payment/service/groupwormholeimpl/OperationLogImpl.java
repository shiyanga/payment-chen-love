package com.payment.service.groupwormholeimpl;


import com.payment.entity.groupwormhole.OperationLog;
import com.payment.mapper.groupwormholemapper.OperationLogDao;
import com.payment.service.groupwormholeservice.OperationLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2016/9/9.
 */
@Service
public class OperationLogImpl implements OperationLogService {


    private Logger logger = LoggerFactory.getLogger(OperationLogImpl.class);


    @Autowired
    private OperationLogDao operationLogDao;


    @Override
    public List<OperationLog> selectByMultiConditions(Map<String, Object> paramMap) {
        return null;
    }

    @Override
    public List<OperationLog> selectByOrderId(long orderId) {
        return null;
    }

    @Override
    public int insert(OperationLog operationLog) {

        try {
            int resources = operationLogDao.insert(operationLog);
            return resources;
        } catch (Exception e) {
            logger.error("OperationLogImpl  insert exception, detail: ", e);
        }

        return 0;
    }

    @Override
    public int update(OperationLog operationLog) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(long primaryKey) {
        return 0;
    }

    @Override
    public OperationLog selectByPrimaryKey(long primaryKey) {
        return null;
    }

    @Override
    public int deleteSqlCollectionsByAppID(String appID) {
        return 0;
    }
}
