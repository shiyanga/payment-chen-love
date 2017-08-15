package com.payment.entity.dbback;

/**
 * 数据库行更新dto
 * Created by shi_y on 2016/11/14.
 */
public class DBTableRowUpdateDTO {
    private DBQueryDTO query;
    private String updateFieldName;
    private String updateFieldValue;

    public DBQueryDTO getQuery() {
        return query;
    }

    public void setQuery(DBQueryDTO query) {
        this.query = query;
    }

    public String getUpdateFieldName() {
        return updateFieldName;
    }

    public void setUpdateFieldName(String updateFieldName) {
        this.updateFieldName = updateFieldName;
    }

    public String getUpdateFieldValue() {
        return updateFieldValue;
    }

    public void setUpdateFieldValue(String updateFieldValue) {
        this.updateFieldValue = updateFieldValue;
    }
}
