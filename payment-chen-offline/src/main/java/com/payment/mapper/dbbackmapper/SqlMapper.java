package com.payment.mapper.dbbackmapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 这个mapper用来直接执行sql
 * Created by lilinjun on 2016/11/11.
 */
@Repository
public interface SqlMapper {
    public Integer update(@Param("sql") String sql);
    public List<Map> select(@Param("sql") String sql);
}
