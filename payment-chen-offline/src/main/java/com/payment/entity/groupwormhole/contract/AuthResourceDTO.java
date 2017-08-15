package com.payment.entity.groupwormhole.contract;

import com.payment.entity.publicenitty.BasicDTO;

import java.util.List;

/**
 * Created by shi_y on 2017/1/22.
 */
public class AuthResourceDTO extends BasicDTO {

    private Integer id;
    private Integer parentId;
    private String code;
    private String description;
    private String url;
    private String reqMethod;

    private List<AuthResourceDTO> children;
    public AuthResourceDTO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<AuthResourceDTO> getChildren() {
        return children;
    }

    public void setChildren(List<AuthResourceDTO> children) {
        this.children = children;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }
}
