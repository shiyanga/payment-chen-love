package com.payment.service.groupwormholeservice;

import com.payment.entity.groupwormhole.OperationLog;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2016/9/9.
 */
@Repository
public interface OperationLogService {

    List<OperationLog> selectByMultiConditions(Map<String, Object> paramMap);

    List<OperationLog> selectByOrderId(long orderId);

    int insert(OperationLog operationLog);

    int update(OperationLog operationLog);

    int deleteByPrimaryKey(long primaryKey);

    OperationLog selectByPrimaryKey(long primaryKey);

    int deleteSqlCollectionsByAppID(String appID);
}
