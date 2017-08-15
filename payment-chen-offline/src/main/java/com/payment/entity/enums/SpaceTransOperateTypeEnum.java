package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/11/18.
 */
public enum SpaceTransOperateTypeEnum {


    CANCEL(1000, "直连取消"),
    ORDER(1001, "直连下单"),
    QUERY(1002, "直连查询"),
    BooingCheck(1003, "直连可订检查");


    SpaceTransOperateTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public static SpaceTransOperateTypeEnum getItemByName(String name) {
        for (SpaceTransOperateTypeEnum item : SpaceTransOperateTypeEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }


    public static SpaceTransOperateTypeEnum getItemByCode(int typeCode) {
        for (SpaceTransOperateTypeEnum statusEnum : SpaceTransOperateTypeEnum.values()) {
            if (statusEnum.getCode() == typeCode) {
                return statusEnum;
            }
        }
        return null;
    }
}
