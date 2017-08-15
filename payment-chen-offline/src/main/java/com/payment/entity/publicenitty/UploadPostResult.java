package com.payment.entity.publicenitty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shi_y on 2016/10/17.
 */
public class UploadPostResult {
    @JsonProperty(value = "IsSuccess")
    private boolean isSuccess;
    @JsonProperty(value = "Msg")
    private String msg;
    @JsonProperty(value = "Path")
    private String path;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
