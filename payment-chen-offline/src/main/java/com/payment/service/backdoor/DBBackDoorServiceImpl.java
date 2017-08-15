package com.payment.service.backdoor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.payment.mapper.bnbordermapper.OrderDBSqlMapper;
import com.payment.mapper.dbbackmapper.SqlMapper;
import com.payment.mapper.groupwormholemapper.WormDBSqlMapper;
import org.apache.commons.collections.KeyValue;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by shi_y on 2017/1/19.
 */
@Service
public class DBBackDoorServiceImpl implements DBBackDoorService {

    //默认为wormdb数据库
    private DBEnum dbEnum = DBEnum.BNBORDERDB;

    @Autowired
    private WormDBSqlMapper wormDBSqlMapper;
    @Autowired
    private OrderDBSqlMapper orderDBSqlMapper;

    @Override
    public void setDB(DBEnum dbEnum) {
        this.dbEnum = dbEnum;
    }


    @Override
    public String queryRecordsAsJson(final String tableName, final KeyValue queryField) throws SQLException {
        final String queryValue = getFieldValueString(tableName, queryField);



        String sql = new SQL() {{
            SELECT("*");
            FROM(tableName);
            WHERE(queryField.getKey() + " = " + queryValue);
            //OR();
            //WHERE(queryField.getKey() + " = '" + queryField.getValue() + "'");
        }}.toString();

        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("SELECT *  ");
        stringBuffer.append("FROM "+tableName);
        stringBuffer.append((queryField.getKey() + " = " + queryValue));


     //   String sql =stringBuffer.toString();

        List<Map> select = getTargetMapper().select(sql);
        Preconditions.checkState(select.size() > 0, "未找到记录");
        return JSON.toJSONString(select, SerializerFeature.WriteMapNullValue);
    }

    @Override
    public int updateRecordField(final String tableName, final KeyValue queryField, final KeyValue updateField) {
        final String updateValue = getFieldValueString(tableName, updateField);
        final String queryValue = getFieldValueString(tableName, queryField);

        String sql = new SQL() {{
            UPDATE(tableName);
            SET(updateField.getKey() + " = " + updateValue);
            WHERE(queryField.getKey() + " = " + queryValue);
        }}.toString();

        Integer updated = getTargetMapper().update(sql);
        Preconditions.checkNotNull(updated);
        Preconditions.checkArgument(updated > 0, "未更新任何记录");
        return updated;
    }


    //region 实现细节
    private SqlMapper getTargetMapper() {
        if (dbEnum == DBEnum.GROUPWORMHOLEDB) {
            return this.wormDBSqlMapper;
        } else if (dbEnum == DBEnum.BNBORDERDB) {
            return this.orderDBSqlMapper;
        }
        return wormDBSqlMapper;
        //throw new Exception("未指定当前数据库");
    }

    private String getFieldValueString(String tableName, KeyValue field) {
        return isNumberTypeField(tableName, field.getKey().toString()) ? field.getValue().toString() : "'" + field.getValue() + "'";
    }

    private Map<String, Map> tableName_tableRow = Maps.newHashMap();

    //判断给定表中的的给定列是不是一个数值型的列
    private boolean isNumberTypeField(final String tableName, String columnName) {
        //如果读取过表的元信息，则不再查库，直接去缓存的元信息进行判断
        if (tableName_tableRow.containsKey(tableName)) {
            return tableName_tableRow.get(tableName).get(columnName) instanceof Number;
        }
        String sql = new SQL() {{
            SELECT("*");
            FROM(tableName);
        }}.toString() + " limit 0,1";

        List<Map> rows = getTargetMapper().select(sql);
        Preconditions.checkState(rows.size() > 0, tableName + "表中没有记录");

        Map tableRow = rows.get(0);
        tableName_tableRow.put(tableName, tableRow);
        //todo 这段代码需要重点测试
        return tableRow.get(columnName) instanceof Number;
    }
    //endregion

}
