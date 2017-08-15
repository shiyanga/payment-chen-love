package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/12/8.
 */
public enum SOAErrorEnum {

    OTHER_ERROR(100, "其它错误", 10),
    NETWORK_ERROR(101, "网络错误", 10),
    SESSON_OVERDUE(102, "session过期", 10),
    ILLEGAL_ARGUMENT(103, "参数非法", 10),
    NO_PERMISSION(104, "没有权限", 10),
    THIRTY_PARTY_ERROR(105, "与第三方交互错误", 10),
    GET_NOTHING(106, "查无记录", 10);

    private int code;
    private String message;
    private int errorLevel;

    SOAErrorEnum(int code, String message, int errorLevel) {
        this.code = code;
        this.message = message;
        this.errorLevel = errorLevel;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public int getErrorLevel() {
        return errorLevel;
    }

    public void setErrorLevel(int errorLevel) {
        this.errorLevel = errorLevel;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
