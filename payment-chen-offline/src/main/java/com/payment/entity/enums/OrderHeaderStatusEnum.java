package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/9/20.
 */
public enum OrderHeaderStatusEnum {
    REQ((short) 10, "处理中"),
    SUCCESS((short) 20, "成功"),
    FAILED((short) 21, "失败"),
    CANCELLED((short) 30, "已取消"),
    PART_CANCELLED((short) 31, "部分取消"),
    COMPLETED((short) 100, "订单完成"),
    SYSTEM_CLOSE((short) 9999, "系统关闭"),;

    private short code;
    private String desc;

    OrderHeaderStatusEnum(short code, String desc) {
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

}
