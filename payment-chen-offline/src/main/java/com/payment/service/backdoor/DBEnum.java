package com.payment.service.backdoor;

/**
 * 表示数据库的枚举
 * Created by lilinjun on 2016/11/11.
 */
public enum DBEnum {
    GROUPWORMHOLEDB(1), BNBORDERDB(2);
    private final int code;

    DBEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DBEnum getItemByCode(Integer code) {
        if(code == null){
            return null;
        }
        for (DBEnum enu : values()) {
            if (enu.getCode() == code) {
                return enu;
            }
        }
        return null;
    }
}
