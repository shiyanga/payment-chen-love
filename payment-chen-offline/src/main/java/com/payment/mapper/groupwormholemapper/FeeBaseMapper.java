package com.payment.mapper.groupwormholemapper;

import com.payment.entity.groupwormhole.FeeBase;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FeeBaseMapper {
    List<FeeBase> selectList(Map<String, Object> params) throws DataAccessException;
}