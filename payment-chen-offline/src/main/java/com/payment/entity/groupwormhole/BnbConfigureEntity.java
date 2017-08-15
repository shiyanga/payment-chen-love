package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

import java.util.Date;

public class BnbConfigureEntity extends Entity {
    private Integer id;

    private String group;

    private String key;

    private String value;

    private String description;

    private Date createTime;

    private Date dataChange_lastTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDataChange_lastTime() {
        return dataChange_lastTime;
    }

    public void setDataChange_lastTime(Date dataChange_lastTime) {
        this.dataChange_lastTime = dataChange_lastTime;
    }
}