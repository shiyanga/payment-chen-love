package com.payment.mapper.groupwormholemapper;

import com.payment.entity.groupwormhole.Configure;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ConfigureMapper {

    List<Configure> selectByMultiConditions(Map<String, Object> paramMap);

    Configure selectByPrimaryKey(@Param("primaryKey") long primaryKey);

    int updateByPrimaryKeySelective(Configure configure);

    int insert(Configure configure);

    int deleteByPrimaryKey(@Param("primaryKey") int primaryKey);
}
