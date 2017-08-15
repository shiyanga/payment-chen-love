package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/11/8.
 */
public enum SalesChannelEnum {

    DIRECT_SELL(1, "直销"),
    DISTRIBUTE_SELL(2, "分销");

    SalesChannelEnum(int code, String desc){
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

    public static SalesChannelEnum getChannelType(Integer typeCode){
        if(typeCode == null){
            return null;
        }
        for(SalesChannelEnum cancelTypeEnum : SalesChannelEnum.values()){
            if(cancelTypeEnum.getCode() == typeCode){
                return cancelTypeEnum;
            }
        }
        return null;
    }
}
