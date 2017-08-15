package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/10/13.
 */
public enum OrderSpaceStatusEnum {
    UNKNOWN((short) 00,"未知"),

    NO_CONFIRM((short)10, "待确认"),
    CONFIRMED((short)12, "已确认"),
    CONFIRM_DENY_FAILED((short)13, "确认失败(房东拒绝)"),
    CONFIRM_TIMEOUT_FAILED((short)14, "确认失败(超时)"),

    NO_CANCEL((short)30, "待退订"),
    CANCEL_PART((short)32, "部分退订"),
    CANCELLED((short)33, "已退订"),
    CANCEL_FAILED((short)34, "退订失败"),

    //------------- 废弃删除 -------------
    CONFIRMING((short)11, "确认中"),
    NO_ORDER((short)20, "待下单"),
    ORDERING((short)21, "下单中"),
    ORDER((short)22, "已下单"),
    ORDER_FAILED((short)23, "下单失败"),
    CANCELLING((short)31, "退业务中"),
    ;

    private short code;
    private String desc;

    OrderSpaceStatusEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OrderSpaceStatusEnum getDescByCode(int code) {
        for (OrderSpaceStatusEnum statusEnum : OrderSpaceStatusEnum.values()) {
            if (statusEnum.getCode() == code) {
                return statusEnum;
            }
        }
        return null;
    }

    public static OrderSpaceStatusEnum getOrderSpaceStatus(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (OrderSpaceStatusEnum codeEnum : OrderSpaceStatusEnum.values()) {
            if (codeEnum.getCode() == typeCode) {
                return codeEnum;
            }
        }
        return null;
    }
}