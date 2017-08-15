package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/9/20.
 */
public enum OrderPayStatusEnum {
    UNKNOWN((short)00,"未知"),
    NO_PAY((short) 10, "待支付"),
    PAYING((short) 11, "支付中"),
    PAY_SUCCESS((short) 12, "支付成功"),
    PAY_FAILED((short) 13, "支付失败"),
    PAY_PARTY_FAILED((short) 14, "支付部分失败"),
    NO_REFUND((short) 20, "待退款"),
    REFUNDING((short) 21, "退款中"),
    PART_REFUND((short) 22, "部分退款"),
    REFUNDED((short) 23, "已退款"),
    REFUND_FAILED((short) 24, "退款失败");


    private short code;
    private String desc;

    OrderPayStatusEnum(short code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public static OrderPayStatusEnum getOrderPayStatus(Integer typeCode){
        if(typeCode == null){
            return null;
        }
        for(OrderPayStatusEnum codeEnum : OrderPayStatusEnum.values()){
            if(codeEnum.getCode() == typeCode){
                return codeEnum;
            }
        }
        return null;
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
