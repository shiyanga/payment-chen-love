package com.payment.service.groupwormholeservice;

import com.payment.entity.publicenitty.Result;

/**
 * Created by shi_y on 2017/3/8.
 */
public interface AuthorityService {
    Result validateAuthority(String employeeId, String url, String reqMethod);

    Result validateAuthority(String employeeId, String authCode);
}
