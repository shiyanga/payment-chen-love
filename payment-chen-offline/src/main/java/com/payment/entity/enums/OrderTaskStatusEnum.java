package com.payment.entity.enums;

/**
 * Created by shi_y on 2016/9/9.
 */
public enum OrderTaskStatusEnum {

    Pending_TASK(1, "待处理"),
    Processing_TASK(2, "处理中"),
    Sucess_Task(3, "处理完成"),
    Fail_Task(4, "处理失败");


    private int code;
    private String desc;

    OrderTaskStatusEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
