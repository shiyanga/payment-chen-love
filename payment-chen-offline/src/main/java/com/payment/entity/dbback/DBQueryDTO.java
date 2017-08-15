package com.payment.entity.dbback;

/**
 * 数据库查询dto
 * Created by lilinjun on 2016/11/14.
 */
public class DBQueryDTO {
    private int dbEnumCode;
    private String tableName;
    private String queryFieldName;
    private String queryFieldValue;

    public int getDbEnumCode() {
        return dbEnumCode;
    }

    public void setDbEnumCode(int dbEnumCode) {
        this.dbEnumCode = dbEnumCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getQueryFieldName() {
        return queryFieldName;
    }

    public void setQueryFieldName(String queryFieldName) {
        this.queryFieldName = queryFieldName;
    }

    public String getQueryFieldValue() {
        return queryFieldValue;
    }

    public void setQueryFieldValue(String queryFieldValue) {
        this.queryFieldValue = queryFieldValue;
    }
}
