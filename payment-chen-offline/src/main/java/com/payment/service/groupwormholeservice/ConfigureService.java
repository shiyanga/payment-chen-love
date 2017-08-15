package com.payment.service.groupwormholeservice;

import com.payment.entity.groupwormhole.Configure;
import com.payment.entity.publicenitty.Entity;
import com.payment.entity.publicenitty.Page;
import com.payment.entity.publicenitty.Result;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shi_y on 2017/1/22.
 */
@Repository
public interface ConfigureService {

    int queryTaskSwitchStatusByEmployeeID(String employee);

    Result switchTaskDistribution(String employee, int newStatus);

    Configure queryByPrimaryKey(long primaryKey);

    List<Configure> queryMultiConditions(String[] group, String[] key, String orderColumn, String orderDir, Page page);

    int update(Entity entity);

    Result updateConfigure(Integer id, String value, String desc);

    int insert(Entity entity);

    int deleteByPrimaryKey(int primaryKey);

}
