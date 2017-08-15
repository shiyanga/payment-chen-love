package com.payment.entity.enums;

/**
 * Created by shi_y on 2017/1/22.
 */
public enum ConfigureEnum {

    ORDER_TASK_RECIPIENT(Group.TASK_DISTRIBUTION, "RECIPIENT", "订单任务接受者"),

    SPACE_AUDIT_TASK_RECIPIENT(Group.TASK_DISTRIBUTION, "SPACE_AUDIT_TASK_RECIPIENT", "房源审核任务接受者"),

    REALNAME_AUDIT_TASK_RECIPIENT(Group.TASK_DISTRIBUTION, "REALNAME_AUDIT_TASK_RECIPIENT", "房东实名认证任务接受者"),

    REVIEW_KEYWORD_FILTER(Group.SPACE_REVIEW, "KEYWORD_FILTER", "关键字列表"),

    QUARTZ_JOB_SWITCH(Group.QUARTZ_JOB_SWITCH, "JOB_SWITCH", "JOB开关"),

    SQL_COLLECTION_SWITCH(Group.SQL_COLLECTION_SWITCH, "SQL_COLLECTION_SWITCH", "SQL收集处理开关"),

    CTRIP_APP_FIND(Group.SQL_COLLECTION_SWITCH, "CTRIP_APP_FIND", "APP-发现--找个旅由"),
    CTRIP_BNB_MAIN_PAGE(Group.SQL_COLLECTION_SWITCH, "CTRIP_BNB_MAIN_PAGE", "民宿-首页专题"),
    CTRIP_BNB_FIND(Group.SQL_COLLECTION_SWITCH, "CTRIP_BNB_FIND", "民宿-发现专题封面");



    ConfigureEnum(Group group, String code, String desc){
        this.group = group;
        this.code = code;
        this.desc = desc;
    }

    private String code;
    private String desc;
    private Group group;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public enum Group{
        TASK_DISTRIBUTION("TASK_DISTRIBUTION", "任务分配"),
        USER_ROLE("USER_ROLE", "用户角色"),
        SPACE_REVIEW("SPACE_REVIEW","产品评论相关"),
        PRICE_SERVICE("PRICE_SERVICE","价格服务"),
        QUARTZ_JOB_SWITCH("QUARTZ_JOB_SWITCH","Quartz job开关"),
        SQL_COLLECTION_SWITCH("SQL_COLLECTION_SWITCH","SQL 收集处理开关"),
        MESSAGE_TEMPLATE("MESSAGE_TEMPLATE", "短信模板配置"),
        MARKETING_ARTICLE_CHANNEL("MARKETING_ARTICLE_CHANNEL", "营销文章渠道分类");

        Group(String code, String desc){
            this.code = code;
            this.desc = desc;
        }

        private String code;
        private String desc;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }



}
