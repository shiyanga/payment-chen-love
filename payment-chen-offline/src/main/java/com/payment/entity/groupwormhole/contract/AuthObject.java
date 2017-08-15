package com.payment.entity.groupwormhole.contract;

public class AuthObject {

    private String url;
    private String reqMethod;
    private String code;

    public AuthObject() {
    }

    public AuthObject(String url, String reqMethod, String code) {
        this.url = url;
        this.reqMethod = reqMethod;
        this.code = code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}