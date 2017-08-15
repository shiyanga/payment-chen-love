package com.payment.service.groupwormholeservice;
import com.payment.entity.groupwormhole.FeeBase;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by shi_y on 2016/9/6.
 */
@Repository
public interface FeeBaseService {
    List<FeeBase> selectList(Integer roleId);
}

