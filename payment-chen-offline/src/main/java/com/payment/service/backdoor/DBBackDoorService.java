package com.payment.service.backdoor;


import org.apache.commons.collections.KeyValue;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;

/**
 * 数据库后门服务
 * Created by lilinjun on 2016/11/10.
 */
@Repository
public interface DBBackDoorService {
    void setDB(DBEnum dbEnum);

    String queryRecordsAsJson(String tableName, KeyValue queryField) throws SQLException;

    int updateRecordField(final String tableName, final KeyValue queryField, final KeyValue updateField);

}
