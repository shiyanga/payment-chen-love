package com.payment.entity.groupwormhole.contract;

import com.payment.entity.publicenitty.DatatablesRequest;


public class AuthUserRequest extends DatatablesRequest {

    private String queryType;//group
    //组ID
    private String groupCode;


    //获得排序列信息e.g: order by orderId asc.目前只支持单列排序
    public String getOrderColumn(){
        String column = "createTime";
        return column;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}
