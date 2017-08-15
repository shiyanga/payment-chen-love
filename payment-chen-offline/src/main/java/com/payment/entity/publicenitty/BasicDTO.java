package com.payment.entity.publicenitty;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author <a href="jxdong@Ctrip.com">jeff</a>
 * @version 2015/6/26 9:12
 */
public class BasicDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    private Date version;


    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getVersion() {
        return version;
    }
}
