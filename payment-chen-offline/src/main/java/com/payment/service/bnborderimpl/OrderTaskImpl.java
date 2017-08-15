package com.payment.service.bnborderimpl;

import com.payment.entity.bnborder.OrderTaskEntity;
import com.payment.mapper.bnbordermapper.OrderTaskDao;
import com.payment.service.bnborderservice.OrderTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2016/9/8.
 */
@Service
public class OrderTaskImpl implements OrderTaskService {

    private Logger logger = LoggerFactory.getLogger(OrderTaskImpl.class);
    @Autowired
    private OrderTaskDao orderTaskDao;

    /**
     * 获得任务列表
     *
     * @param jobId
     * @param statusId
     * @return
     */
    @Override
    public List<OrderTaskEntity> selectList(String jobId, Integer statusId, List<Integer> jobType, Integer limit) {
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("jobId", jobId);
            param.put("statusId", statusId);
            param.put("jobType", jobType);

            if(limit>0) {
                param.put("limit", limit);
            }
            List<OrderTaskEntity> resources = orderTaskDao.selectList(param);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  selectList exception, detail: ", e);
        }
        return null;
    }

    /**
     * 修改任务列表
     *
     * @param entity
     * @return
     */
    @Override
    public int updateByPrimaryKeySelective(OrderTaskEntity entity) {

        try {
            int resources = orderTaskDao.updateByPrimaryKeySelective(entity);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  updateByPrimaryKeySelective exception, detail: ", e);
        }
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(OrderTaskEntity record) {
        try {
            int resources = orderTaskDao.insert(record);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  updateByPrimaryKeySelective exception, detail: ", e);
        }
        return 0;
    }

    @Override
    public int insertSelective(OrderTaskEntity record) {
        return 0;
    }

    @Override
    public OrderTaskEntity selectByPrimaryKey(Long id) {
        try {

            OrderTaskEntity resources = orderTaskDao.selectByPrimaryKey(id);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  selectList exception, detail: ", e);
        }

        return null;
    }

    @Override
    public int updateByPrimaryKey(OrderTaskEntity record) {
        try {
            int resources = orderTaskDao.updateByPrimaryKey(record);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  updateByPrimaryKeySelective exception, detail: ", e);
        }
        return 0;
    }

    @Override
    public int updateCompensateTask(String time) {
        try {

            Map<String, Object> param = new HashMap<String, Object>();
            param.put("datachangeLasttime", time);


            int resources = orderTaskDao.updateCompensateTask(param);
            return resources;
        } catch (Exception e) {
            logger.error("OrderTaskImpl  updateCompensateTask exception, detail: ", e);
        }
        return 0;
    }


}
