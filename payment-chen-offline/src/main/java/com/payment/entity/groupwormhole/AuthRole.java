package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

/**
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2016/6/7 17:16
 */
public class AuthRole extends Entity {

    private Integer id;
    private String name;
    private String description;
    private Integer status;

    public AuthRole(){

    }

    public AuthRole(String name, String description, Integer status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public enum StatusEnum {
        NORMAL(1, "正常"),
        DISABLE(2, "停用"),
        DELETED(3, "删除");

        private int code;
        private String desc;

        StatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static StatusEnum getItem(int typeCode) {
            if(typeCode<=0){
                return null;
            }
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.getCode() == typeCode) {
                    return statusEnum;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
