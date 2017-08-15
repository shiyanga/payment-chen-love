package com.payment.entity.groupwormhole;


import java.util.Date;

public class BnbVendorServiceLog {
    private Long logid;

    private Integer vendorid;

    private String orderids;

    private String referid;

    private String requestcontent;

    private String responsecontent;

    private Date createtime;

    private Date datachangeLasttime;

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public Integer getVendorid() {
        return vendorid;
    }

    public void setVendorid(Integer vendorid) {
        this.vendorid = vendorid;
    }

    public String getOrderids() {
        return orderids;
    }

    public void setOrderids(String orderids) {
        this.orderids = orderids == null ? null : orderids.trim();
    }

    public String getReferid() {
        return referid;
    }

    public void setReferid(String referid) {
        this.referid = referid == null ? null : referid.trim();
    }

    public String getRequestcontent() {
        return requestcontent;
    }

    public void setRequestcontent(String requestcontent) {
        this.requestcontent = requestcontent == null ? null : requestcontent.trim();
    }

    public String getResponsecontent() {
        return responsecontent;
    }

    public void setResponsecontent(String responsecontent) {
        this.responsecontent = responsecontent == null ? null : responsecontent.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getDatachangeLasttime() {
        return datachangeLasttime;
    }

    public void setDatachangeLasttime(Date datachangeLasttime) {
        this.datachangeLasttime = datachangeLasttime;
    }
}