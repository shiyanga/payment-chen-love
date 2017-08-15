package com.payment.entity.groupwormhole.contract;


import com.payment.entity.publicenitty.BasicDTO;

import com.payment.entity.groupwormhole.*;


public class AuthUserRoleDTO extends BasicDTO {

    private Integer id;
    private AuthUser user;
    private AuthRole role;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }
}
