package payment.chen.service.common.redis.model;

import java.lang.reflect.Field;

/**
 * Created by shi_y on 2016/11/30.
 */
public class BeanField {
    String primaryKey;
    String className;
    Field[] fieldList;

    public BeanField(String primaryKey, String className, Field[] fieldList) {
        this.primaryKey = primaryKey;
        this.className = className;
        this.fieldList = fieldList;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Field[] getFieldList() {
        return fieldList;
    }

    public void setFieldList(Field[] fieldList) {
        this.fieldList = fieldList;
    }
}
