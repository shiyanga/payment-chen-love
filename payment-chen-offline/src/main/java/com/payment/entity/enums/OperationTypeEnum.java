package com.payment.entity.enums;


/**
 * 权限分类enum, 不规定权限的层级关系
 * @author <a href="jxdong@Ctrip.com">jeff</a>
 * @version 2015/7/2 14:49
 */
public enum OperationTypeEnum {

    DISTRIBUTE_TASK_AUTO(10, "系统分配任务"),
    UPDATE_TASK_STATUS(20, "更新任务状态"),
    SPACE_AUDIT(21, "房源审核"),
    TASK_STATUS_SWITCH(30, "接单状态切换"),
    INFORM_ORDER_STATUS(40, "订单状态通知"),
    MESSAGE_SEND(50, "短信发送"),
    UPDATE_SPACE_STATUS(60,"产品状态更新"),
    UPDATE_SPACE_IMAGE_STATUS(61,"产品图片状态更新"),
    UPDATE_SPACE_REVIEW_STATUS(62,"产品评论状态更新"),
    UPDATE_SPACE_PRIORITY_LEVEL(63, "产品优先级更新"),
    UPDATE_SPACE_IMAGE_PRIORITY_LEVEL(64, "产品图片优先级更新"),
    UPDATE_SPACE_AVAILABILITY(65,"产品剩余房源数"),
    VENDOR_SUBMIT_ORDER(70,"直连订单提交"),
    VENDOR_CANCEL_ORDER(71,"直连订单取消"),
    VENDOR_GET_ORDER(72,"直连订单查询"),
    ORDER_PROCESS(80, "订单处理"),
    ORDER_CANCEL_VENDOR_ORDER(85, "退供应商订单");

    private int code;
    private String desc;

    OperationTypeEnum(int code, String desc){
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
