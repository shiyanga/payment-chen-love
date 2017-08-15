package com.payment.entity.publicenitty;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

/**
 * Created by shi_y on 2016/9/8.
 */
public class Entity {

    private Date createTime;
    private Date updateTime;
    private Date version;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("createTime", createTime)
                .append("updateTime", updateTime)
                .append("version", version)
                .toString();
    }
}
