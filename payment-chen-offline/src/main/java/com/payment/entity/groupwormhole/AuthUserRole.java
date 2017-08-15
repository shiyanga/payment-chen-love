package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

/**
 * Created by shi_y on 2017/1/23.
 */
public class AuthUserRole extends Entity {

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
