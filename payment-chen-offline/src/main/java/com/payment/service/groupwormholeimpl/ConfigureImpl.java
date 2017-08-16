package com.payment.service.groupwormholeimpl;

import com.github.pagehelper.PageHelper;
import com.payment.common.util.CommonUtil;
import com.payment.common.util.StringUtils;
import com.payment.entity.enums.ConfigureEnum;
import com.payment.entity.groupwormhole.Configure;
import com.payment.entity.publicenitty.Entity;
import com.payment.entity.publicenitty.Page;
import com.payment.entity.publicenitty.Result;
import com.payment.mapper.groupwormholemapper.ConfigureMapper;
import com.payment.service.groupwormholeservice.ConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/22.
 */
@Service
public class ConfigureImpl implements ConfigureService {


    private static Logger logger = LoggerFactory.getLogger(ConfigureImpl.class);
    @Autowired
    private ConfigureMapper configureMapper;



    public int queryTaskSwitchStatusByEmployeeID(String employee) {
        int status = -1;
        if (StringUtils.isNotBlank(employee)) {
            List<Configure> configureList = queryMultiConditions(new String[]{ConfigureEnum.Group.TASK_DISTRIBUTION.getCode()}, new String[]{ConfigureEnum.ORDER_TASK_RECIPIENT.getCode()}, null, null, null);
            if (CommonUtil.listIsNotBlank(configureList)) {
                Configure configure = configureList.get(0);
                String[] configStrArray = configure.getValue().split(";");
                if (configStrArray.length > 0) {
                    Map<String, String> configMap = new HashMap<>();
                    for (String str : configStrArray) {
                        if (str != null && str.contains(":")) {
                            configMap.put(str.split(":")[0], str.split(":")[1]);
                        }
                    }
                    if (configMap.get(employee) != null) {
                        return Integer.valueOf(configMap.get(employee));
                    }
                }
            }
        }
        return status;
    }

    /**
     * 切换某employee的task分发状态(0关闭，1打开)
     * 1、判断此员工是否在目前的列表中
     * 2、更新
     *
     * @param employee  员工号
     * @param newStatus 新状态0或者1
     * @return result
     */
    @Override
    @Transactional
    public Result switchTaskDistribution(String employee, int newStatus) {
        List<Configure> configureList = queryMultiConditions(new String[]{ConfigureEnum.Group.TASK_DISTRIBUTION.getCode()}, new String[]{ConfigureEnum.ORDER_TASK_RECIPIENT.getCode()}, null, null, null);
        if (CommonUtil.listIsNotBlank(configureList)) {
            Configure configure = configureList.get(0);
            String[] configStrArray = configure.getValue().split(";");
            if (configStrArray.length > 0) {
                Map<String, String> configMap = new HashMap<>();
                for (String str : configStrArray) {
                    if (str != null && str.contains(":")) {
                        configMap.put(str.split(":")[0], str.split(":")[1]);
                    }
                }
                if (!configMap.containsKey(employee)) {
                    return Result.FAILURE("The employee (" + employee + ") has no permission to the task.");
                }
                //重新更新目前状态
                if (!String.valueOf(newStatus).equals(configMap.get(employee))) {
                    String newValue = configure.getValue().replace(employee + ":" + configMap.get(employee), employee + ":" + newStatus);
                    configure.setValue(newValue);
                    int ret = configureMapper.updateByPrimaryKeySelective(configure);
                    if (ret <= 0) {
                        return Result.FAILURE("Update the configure failed.");
                    } else {
//                        operationLogMapper.insert(new OperationLog(employee, "接单开关",
//                                OperationTypeEnum.TASK_STATUS_SWITCH.getCode(), String.valueOf(newStatus), "员工(" + employee + ") " + (newStatus == 1 ? "打开" : "关闭") + "了接单开关"));
                    }
                }
            }

        } else {
            return Result.FAILURE("The employee (" + employee + ") has no permission to the task.");
        }
        return Result.SUCCESS();
    }

    @Override
    public Configure queryByPrimaryKey(long primaryKey) {
        Configure configure = null;
        try {
            configure = configureMapper.selectByPrimaryKey(primaryKey);
        } catch (Exception e) {
            logger.error("Query configure by primary key({})  error, detail info: ", primaryKey, e);
        }
        return configure;
    }

    @Override
    public List<Configure> queryMultiConditions(String[] groups, String[] keys, String orderColumn, String orderDir, Page page) {
        List<Configure> configureList = null;
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("groups", groups);
            paramMap.put("keys", keys);
            paramMap.put("orderColumn", orderColumn);
            paramMap.put("orderDir", orderDir);
            if (page != null) {
                paramMap.put("page", page);
                //分页
                PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
            }
            configureList = configureMapper.selectByMultiConditions(paramMap);
            if (page != null) {
                page.setTotalRecord(configureList);
            }
        } catch (Exception e) {
            logger.error("Query configure , detail info: ", e);
        }
        return configureList;
    }

    public Result updateConfigure(Integer id, String value, String desc) {

        try{
            Configure oldConfigure = queryByPrimaryKey(id);
            if(oldConfigure == null){
                return Result.FAILURE("找不到对应记录");
            }
            if(StringUtils.isNotBlank(value) && !value.equalsIgnoreCase(oldConfigure.getValue())){
                oldConfigure.setValue(value);
            }
            if(StringUtils.isNotBlank(desc) && !desc.equalsIgnoreCase(oldConfigure.getDescription())){
                oldConfigure.setDescription(desc);
            }
            int ret = configureMapper.updateByPrimaryKeySelective(oldConfigure);
            return (ret >0)?Result.SUCCESS():Result.FAILURE("未更新到任何记录");
        }catch (Exception e){
            logger.error("Update Configure(id={}) exception, detail info: ", id, e);
            return Result.FAILURE("内部异常："+e.getMessage());
        }

    }

    @Override
    public int update(Entity entity) {
        int ret = -1;
        try {
            Configure configure = (Configure) entity;
            if (StringUtils.isBlank(configure.getValue()) && StringUtils.isBlank(configure.getDescription())) {
                return 1;
            }
            Configure oldConfigure = queryByPrimaryKey(configure.getId());
            oldConfigure.setValue(configure.getValue());
            ret = configureMapper.updateByPrimaryKeySelective(configure);
        } catch (Exception e) {
            logger.error("Update Configure({}) error, detail info: ", entity, e);
        }
        return ret;
    }

    @Override
    public int insert(Entity entity) {
        int ret = -1;
        try {
            Configure configure = (Configure) entity;
            List<Configure> configureList = this.queryMultiConditions(new String[]{configure.getGroup()}, new String[]{configure.getKey()}, null, null, new Page());
            if (CommonUtil.listIsNotBlank(configureList)) {
                ret = -2;
            } else {
                ret = configureMapper.insert(configure);
            }
        } catch (Exception e) {
            logger.error("insert Configure({}) error, detail info: ", entity, e);
        }
        return ret;
    }

    @Override
    public int deleteByPrimaryKey(int primaryKey) {
        int ret = -1;
        try {
            ret = configureMapper.deleteByPrimaryKey(primaryKey);
        } catch (Exception e) {
            logger.error("delete Configure by primaryKey({}) error, detail info: ", primaryKey, e);
        }
        return ret;
    }
}
