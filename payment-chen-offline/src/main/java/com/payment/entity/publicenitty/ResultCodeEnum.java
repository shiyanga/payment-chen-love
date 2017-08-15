package com.payment.entity.publicenitty;
public enum ResultCodeEnum {

    SUCCESS(0, "成功", 10),

    //房源相关
    SPACE_UN_AVAILABLE(10, "房源不可订", 10),
    SPACE_NOT_FOUND(11, "房源不存在", 10),
    SPACE_INFO_ILLEGAL(12, "房源信息不合法", 10),
    SPACE_OPERATION_ILLEGAL(13, "房源操作不合法", 10),

    //订单相关
    ORDER_REPEAT(20, "订单重复", 10),
    ORDER_NOT_EXIST(21, "订单不存在", 10),
    ORDER_OPERATION_ILLEGAL(22, "订单操作不合法", 10),
    ORDER_PAY_STATUS_ILLEGAL(23, "订单支付状态不合法", 10),
    ORDER_BUSI_STATUS_ILLEGAL(24, "订单业务状态不合法", 10),
    ORDER_STATUS_ILLEGAL(25, "订单状态不合法", 10),
    ORDER_CANCEL_CHECK_FAILED(26, "订单取消检查失败", 10),

    //支付相关
    PAY_FAILED(30,"支付/退款失败", 10),
    PAY_STATUS_ILLEGAL(31,"支付状态不合法", 10),
    PAY_CALLBACK_RES_ILLEGAL(32, "支付返回报文不合法", 10),
    PAY_CALLBACK_FAILED(33, "支付/退款返回处理失败", 10),
    PAY_CONTINUE_FAILED(34,"继续支付失败", 10),

    //退款相关
    REFUND_FAILED(40,"退款失败", 10),

    ILLEGAL_ARGUMENT(100, "参数无效", 10),
    NO_PERMISSION(101, "未得到授权", 10),
    THIRTY_PARTY_ERROR(102, "与第三方交互错误", 10),
    GET_NOTHING(103, "查无记录", 10),
    ILLEGAL_OPERATION(104,"不合法的操作", 10),

    NETWORK_ERROR(200, "网络错误", 10),

    INNER_ERROR(888, "内部错误",10),
    INNER_ERROR_DB(801, "数据库错误", 10),

    OTHER_ERROR(999, "其它错误", 10),
    ;

    private int code;
    private String message;
    private int errorLevel;

    ResultCodeEnum(int code, String message, int errorLevel){
        this.code = code;
        this.message = message;
        this.errorLevel = errorLevel;
    }

    public static ResultCodeEnum getItem(int typeCode) {
        for (ResultCodeEnum statusEnum : ResultCodeEnum.values()) {
            if (statusEnum.getCode() == typeCode) {
                return statusEnum;
            }
        }
        return null;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

    public int getErrorLevel() {
        return errorLevel;
    }

}
