package com.payment.entity.groupwormhole.contract;

import com.payment.entity.publicenitty.BasicDTO;

public class AuthRoleDTO extends BasicDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer status;

    public AuthRoleDTO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
