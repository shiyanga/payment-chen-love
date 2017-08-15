package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/10/14.
 * <p>
 * 分销商枚举
 */
public enum SellerEnum {
    ELONG(1007, "艺龙");

    SellerEnum(int code, String desc) {
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


    public static SellerEnum getItemByName(String name) {
        for (SellerEnum item : SellerEnum.values()) {
            if (item.name().equals(name)) {
                return item;
            }
        }
        return null;
    }


    public static SellerEnum getItemByCode(int typeCode) {
        for (SellerEnum statusEnum : SellerEnum.values()) {
            if (statusEnum.getCode() == typeCode) {
                return statusEnum;
            }
        }
        return null;
    }
}
