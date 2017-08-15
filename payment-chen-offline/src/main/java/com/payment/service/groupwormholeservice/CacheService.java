package com.payment.service.groupwormholeservice;

import com.payment.entity.groupwormhole.FeeBase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shi_y on 2017/3/1.
 */
@Repository
public interface CacheService {
    String defaultCache(String name);
    String guavaCache60seconds(String name);
    String guavaCache10minutes(String name);
    String guavaCache1hour(String name);
    List<FeeBase> selectList(Integer roleId);
}
