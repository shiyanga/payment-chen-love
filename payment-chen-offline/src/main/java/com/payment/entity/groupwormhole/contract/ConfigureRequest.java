package com.payment.entity.groupwormhole.contract;

import com.payment.common.util.CommonUtil;
import com.payment.entity.publicenitty.BootDatatablesRequest;
import com.payment.entity.publicenitty.DatatablesRequest;
import org.apache.commons.lang.StringUtils;

import java.util.List;

public class ConfigureRequest extends DatatablesRequest {

    private String[] group;
    private String[] key;

    //获得排序列信息e.g: order by orderId asc.目前只支持单列排序
    public String getOrderColumn() {
        String column = "id";
        List<Order> orderList = this.getOrder();
        if (CommonUtil.listIsNotBlank(orderList)) {
            String columnIndex = orderList.get(0).getColumn();
            if (StringUtils.isNotBlank(columnIndex)) {
                switch (columnIndex) {
                    case "1":
                        column = "group";
                        break;
                    case "2":
                        column = "key";
                        break;
                    case "3":
                        column = "value";
                        break;
                    case "4":
                        column = "createTime";
                        break;
                    case "5":
                        column = "dataChange_lastTime";
                        break;

                }
            }
        }
        return column;
    }

    public String[] getGroup() {
        return group;
    }

    public void setGroup(String[] group) {
        this.group = group;
    }

    public String[] getKey() {
        return key;
    }

    public void setKey(String[] key) {
        this.key = key;
    }
}
