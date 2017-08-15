package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

/**
 * Created by shi_y on 2017/1/23.
 */
public class AuthRoleResource extends Entity {

    private Integer id;
    private AuthRole role;
    private AuthResource resource;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AuthRole getRole() {
        return role;
    }

    public void setRole(AuthRole role) {
        this.role = role;
    }

    public AuthResource getResource() {
        return resource;
    }

    public void setResource(AuthResource resource) {
        this.resource = resource;
    }
}
