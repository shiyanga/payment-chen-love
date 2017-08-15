package com.payment.entity.enums;

/**
 * @author <a href="dongjianxing@aliyun.com">jeff</a>
 * @version 2016/8/2 9:19
 */
public enum CardTypeEnum {
    //入住人证件类型：1.身份证；2.护照；3.台胞证；4.入台证；5.军官证；6.学生证；

    ID_CARD(1, "身份证"),
    PASSPORT(2, "护照");

    CardTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static CardTypeEnum getCardType(Integer typeCode) {
        if (typeCode == null) {
            return null;
        }
        for (CardTypeEnum codeEnum : CardTypeEnum.values()) {
            if (codeEnum.getCode() == typeCode) {
                return codeEnum;
            }
        }
        return null;
    }
}
