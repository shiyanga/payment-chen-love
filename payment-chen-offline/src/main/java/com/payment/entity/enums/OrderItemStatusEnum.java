package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/9/20.
 */
public enum OrderItemStatusEnum {



    NO_PAY_NO_CONFIRM((short)1010, "未支付未确认"),
    PAYING_CONFIRMED((short)1110, "支付中已确认"),
    PAYING_NO_CONFIRM((short)1112, "支付中未确`认"),
    NO_PAY_CONFIRMED((short)1012, "未支付已确认"),
    NO_PAY_CONFIRM_FAILED((short)1013, "未支付确认失败(超时或拒绝)"),
    NO_PAY_NO_ORDER((short)1020, "未支付未下单"),
    PAYED_NO_CONFIRM((short)1210, "已支付未确认"),
    PAYED_CONFIRMED((short)1212, "已支付已确认"),
    PAYED_NO_ORDER((short)1220, "已支付未下单"),
    PAYED_CONFIRM_FAILED((short)1213, "已支付房东拒绝"),

    PAYED_CONFIRM_TimeOut((short)1214, "已支付确认超时"),

    PAYED_ORDER((short)1222, "已支付已下单"),
    REFUND_CANCEL((short)2333, "已退款已退订"),
    REFUND_CANCELLING((short)2331, "已退款退订中"),
    REFUND_CANCEL_PART((short)2332, "已退款已部分退订"),
    REFUND_ORDER((short)2322, "已退款已下单"),
    REFUND_CONFIRM_FAILED((short)2313, "已退款确认失败(超时或拒绝)"),
    REFUND_FAILED_CANCEL_FAILED((short)2434, "退款失败退订失败"),
    NO_REFUND_ORDER((short)2022, "待退款已下单"),
    NO_REFUND_NO_CANCEL((short)2030, "待退款待退订"),
    NO_REFUND_CANCEL_PART((short)2032, "待退款已部分退订"),
    NO_REFUND_CONFIRM_FAILED((short)2013, "待退款确认失败(超时或拒绝)"),
    REFUNDING_CANCELLING((short)2131, "退款中退订中"),
    REFUNDING_NO_CANCEL((short)2130, "退款中待退订"),
    REFUNDING_CONFIRM_FAILED((short)2113, "退款中确认失败(超时或拒绝)"),
    REFUND_PART_ORDER((short)2222, "已部分退款已下单"),
    REFUND_PART_CANCEL_PART((short)2232, "已部分退款已部分退订"),
    REFUND_PART_CANCEL((short)2233, "已部分退款已退订"),
    PAY_FAILED_CONFIRMED((short)1312, "支付失败已确认"),
    COMPLETED((short)9000, "订单完成"),
    CLOSED((short)9999, "订单已关闭"),;


//    NO_PAY_NO_CONFIRM((short) 1010, "未支付未确认"),
//    NO_PAY_CONFIRMED((short) 1012, "未支付已确认"),
//    NO_PAY_CONFIRM_FAILED((short) 1013, "未支付确认失败(超时或拒绝)"),
//    NO_PAY_NO_ORDER((short) 1020, "未支付未下单"),
//    PAYED_NO_CONFIRM((short) 1210, "已支付未确认"),
//    PAYED_NO_ORDER((short) 1220, "已支付未下单"),
//    PAYED_CONFIRM_FAILED((short) 1213, "已支付确认失败(超时或拒绝)"),
//    PAYED_ORDER((short) 1222, "已支付已下单"),
//    REFUND_CANCEL((short) 2333, "已退款已退订"),
//    REFUND_PART_CANCEL_PART((short) 2232, "已部分退款已部分退订"),
//    PAY_FAILED_CONFIRMED((short) 1312, "支付失败已确认"),
//    SYSTEM_CLOSE((short) 9999, "订单已关闭"),
//    NO_REFUND_FALID((short) 2013, "确认失败待退款"),;


    private short code;
    private String desc;

    OrderItemStatusEnum(short code, String desc) {
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
