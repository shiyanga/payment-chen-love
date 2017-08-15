package com.payment.entity.publicenitty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by shi_y on 2016/10/17.
 */
public class FilesSendResult {

    @JsonProperty(value = "IsSuccess")
    private boolean isSuccess;
    @JsonProperty(value = "FileBytes")
    private byte[] fileBytes;
    @JsonProperty(value = "Msg")
    private String msg;


    private byte[] aesfileBytes;



    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public byte[] getAesfileBytes() {
        return aesfileBytes;
    }

    public void setAesfileBytes(byte[] aesfileBytes) {
        this.aesfileBytes = aesfileBytes;
    }
}