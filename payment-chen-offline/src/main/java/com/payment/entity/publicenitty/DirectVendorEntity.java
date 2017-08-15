package com.payment.entity.publicenitty;

import java.math.BigDecimal;

/**
 * Created by shi_y on 2016/10/27.
 */
public class DirectVendorEntity {
    private BigDecimal totalAmount;
    private BigDecimal onlineAmount;
    private BigDecimal offlineAmount;
    private Integer StatusId;
    private Long orderId;
    private String confirmationCode;
    private BigDecimal purchaseAmount;

    private String vendorOrderId;


    private BigDecimal refundAmount;

    private String description;

    private String submitResponse;

    private String createTime;


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(BigDecimal onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    public BigDecimal getOfflineAmount() {
        return offlineAmount;
    }

    public void setOfflineAmount(BigDecimal offlineAmount) {
        this.offlineAmount = offlineAmount;
    }

    public Integer getStatusId() {
        return StatusId;
    }

    public void setStatusId(Integer statusId) {
        StatusId = statusId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getVendorOrderId() {
        return vendorOrderId;
    }

    public void setVendorOrderId(String vendorOrderId) {
        this.vendorOrderId = vendorOrderId;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubmitResponse() {
        return submitResponse;
    }

    public void setSubmitResponse(String submitResponse) {
        this.submitResponse = submitResponse;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
