package com.payment.entity.groupwormhole;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by shi_y on 2016/8/4.
 */
@Entity
@Table(name = "bnb_order", schema = "groupwormholedb", catalog = "")
public class BnbOrderEntity {
    private long orderId;
    private String uid;
    private Integer orderSource;
    private Integer vendorId;
    private Long vendorUid;
    private String vendorOrderId;
    private long ctripOrderId;
    private long productId;
    private int quantity;
    private BigDecimal saleAmount;
    private BigDecimal paymentAmount;
    private BigDecimal purchaseAmount;
    private BigDecimal serviceFee;
    private BigDecimal cleaningFee;
    private BigDecimal securityDeposit;
    private Date checkIn;
    private Date checkOut;
    private String contactName;
    private String contactMobile;
    private String contactWechat;
    private String guests;
    private String confirmationCode;
    private short statusId;
    private short paymentStatusId;
    private short deleteStatusId;
    private short processStatusId;
    private short settleStatusId;
    private String settleRemark;
    private Timestamp createTime;
    private Date dataChangeLastTime;
    private long hotelId;
    private short terminalType;
    private short extendStatusId;
    private short orderType;
    private Integer securityDepositType;
    private String ownerId;
    private String remark;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private Double onlinePayRate;
    private BigDecimal onlineAmount;
    private BigDecimal offlineAmount;
    private byte isBnbOrder;
    private short confirmType;
    private Date confirmedTime;
    private int arriveAt;
    private short cancellationPolicyType;
    private String cancellationPolicy;
    private Integer fullRefundAheadDays;
    private int guestNumber;
    private String tradingRules;

    @Id
    @Column(name = "orderId", nullable = false)
    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Basic
    @Column(name = "uid", nullable = false, length = 20)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Basic
    @Column(name = "orderSource", nullable = true)
    public Integer getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(Integer orderSource) {
        this.orderSource = orderSource;
    }

    @Basic
    @Column(name = "vendorId", nullable = true)
    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    @Basic
    @Column(name = "vendorUid", nullable = true)
    public Long getVendorUid() {
        return vendorUid;
    }

    public void setVendorUid(Long vendorUid) {
        this.vendorUid = vendorUid;
    }

    @Basic
    @Column(name = "vendorOrderId", nullable = true, length = 25)
    public String getVendorOrderId() {
        return vendorOrderId;
    }

    public void setVendorOrderId(String vendorOrderId) {
        this.vendorOrderId = vendorOrderId;
    }

    @Basic
    @Column(name = "ctripOrderId", nullable = false)
    public long getCtripOrderId() {
        return ctripOrderId;
    }

    public void setCtripOrderId(long ctripOrderId) {
        this.ctripOrderId = ctripOrderId;
    }

    @Basic
    @Column(name = "productId", nullable = false)
    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "saleAmount", nullable = true, precision = 2)
    public BigDecimal getSaleAmount() {
        return saleAmount;
    }

    public void setSaleAmount(BigDecimal saleAmount) {
        this.saleAmount = saleAmount;
    }

    @Basic
    @Column(name = "paymentAmount", nullable = true, precision = 2)
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Basic
    @Column(name = "purchaseAmount", nullable = true, precision = 2)
    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    @Basic
    @Column(name = "serviceFee", nullable = true, precision = 2)
    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Basic
    @Column(name = "cleaningFee", nullable = true, precision = 2)
    public BigDecimal getCleaningFee() {
        return cleaningFee;
    }

    public void setCleaningFee(BigDecimal cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    @Basic
    @Column(name = "securityDeposit", nullable = true, precision = 2)
    public BigDecimal getSecurityDeposit() {
        return securityDeposit;
    }

    public void setSecurityDeposit(BigDecimal securityDeposit) {
        this.securityDeposit = securityDeposit;
    }

    @Basic
    @Column(name = "checkIn", nullable = false)
    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    @Basic
    @Column(name = "checkOut", nullable = false)
    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    @Basic
    @Column(name = "contactName", nullable = true, length = 50)
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    @Basic
    @Column(name = "contactMobile", nullable = true, length = 20)
    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    @Basic
    @Column(name = "contactWechat", nullable = true, length = 45)
    public String getContactWechat() {
        return contactWechat;
    }

    public void setContactWechat(String contactWechat) {
        this.contactWechat = contactWechat;
    }

    @Basic
    @Column(name = "guests", nullable = true, length = 1000)
    public String getGuests() {
        return guests;
    }

    public void setGuests(String guests) {
        this.guests = guests;
    }

    @Basic
    @Column(name = "confirmationCode", nullable = true, length = 50)
    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    @Basic
    @Column(name = "statusId", nullable = false)
    public short getStatusId() {
        return statusId;
    }

    public void setStatusId(short statusId) {
        this.statusId = statusId;
    }

    @Basic
    @Column(name = "paymentStatusId", nullable = false)
    public short getPaymentStatusId() {
        return paymentStatusId;
    }

    public void setPaymentStatusId(short paymentStatusId) {
        this.paymentStatusId = paymentStatusId;
    }

    @Basic
    @Column(name = "deleteStatusId", nullable = false)
    public short getDeleteStatusId() {
        return deleteStatusId;
    }

    public void setDeleteStatusId(short deleteStatusId) {
        this.deleteStatusId = deleteStatusId;
    }

    @Basic
    @Column(name = "processStatusId", nullable = false)
    public short getProcessStatusId() {
        return processStatusId;
    }

    public void setProcessStatusId(short processStatusId) {
        this.processStatusId = processStatusId;
    }

    @Basic
    @Column(name = "settleStatusId", nullable = false)
    public short getSettleStatusId() {
        return settleStatusId;
    }

    public void setSettleStatusId(short settleStatusId) {
        this.settleStatusId = settleStatusId;
    }

    @Basic
    @Column(name = "settleRemark", nullable = true, length = 300)
    public String getSettleRemark() {
        return settleRemark;
    }

    public void setSettleRemark(String settleRemark) {
        this.settleRemark = settleRemark;
    }

    @Basic
    @Column(name = "createTime", nullable = false)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "dataChange_lastTime", nullable = false)
    public Date getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(Date dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    @Basic
    @Column(name = "hotelId", nullable = false)
    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    @Basic
    @Column(name = "terminalType", nullable = false)
    public short getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(short terminalType) {
        this.terminalType = terminalType;
    }

    @Basic
    @Column(name = "extendStatusId", nullable = false)
    public short getExtendStatusId() {
        return extendStatusId;
    }

    public void setExtendStatusId(short extendStatusId) {
        this.extendStatusId = extendStatusId;
    }

    @Basic
    @Column(name = "orderType", nullable = false)
    public short getOrderType() {
        return orderType;
    }

    public void setOrderType(short orderType) {
        this.orderType = orderType;
    }

    @Basic
    @Column(name = "securityDepositType", nullable = true)
    public Integer getSecurityDepositType() {
        return securityDepositType;
    }

    public void setSecurityDepositType(Integer securityDepositType) {
        this.securityDepositType = securityDepositType;
    }

    @Basic
    @Column(name = "ownerId", nullable = true, length = 50)
    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @Basic
    @Column(name = "remark", nullable = true, length = 1000)
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Basic
    @Column(name = "discountAmount", nullable = true, precision = 2)
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }

    @Basic
    @Column(name = "totalAmount", nullable = true, precision = 2)
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "onlinePayRate", nullable = true, precision = 0)
    public Double getOnlinePayRate() {
        return onlinePayRate;
    }

    public void setOnlinePayRate(Double onlinePayRate) {
        this.onlinePayRate = onlinePayRate;
    }

    @Basic
    @Column(name = "onlineAmount", nullable = true, precision = 2)
    public BigDecimal getOnlineAmount() {
        return onlineAmount;
    }

    public void setOnlineAmount(BigDecimal onlineAmount) {
        this.onlineAmount = onlineAmount;
    }

    @Basic
    @Column(name = "offlineAmount", nullable = true, precision = 2)
    public BigDecimal getOfflineAmount() {
        return offlineAmount;
    }

    public void setOfflineAmount(BigDecimal offlineAmount) {
        this.offlineAmount = offlineAmount;
    }

    @Basic
    @Column(name = "isBnbOrder", nullable = false)
    public byte getIsBnbOrder() {
        return isBnbOrder;
    }

    public void setIsBnbOrder(byte isBnbOrder) {
        this.isBnbOrder = isBnbOrder;
    }

    @Basic
    @Column(name = "confirmType", nullable = false)
    public short getConfirmType() {
        return confirmType;
    }

    public void setConfirmType(short confirmType) {
        this.confirmType = confirmType;
    }

    @Basic
    @Column(name = "confirmedTime", nullable = true)
    public Date getConfirmedTime() {
        return confirmedTime;
    }

    public void setConfirmedTime(Date confirmedTime) {
        this.confirmedTime = confirmedTime;
    }

    @Basic
    @Column(name = "arriveAt", nullable = false)
    public int getArriveAt() {
        return arriveAt;
    }

    public void setArriveAt(int arriveAt) {
        this.arriveAt = arriveAt;
    }

    @Basic
    @Column(name = "cancellationPolicyType", nullable = false)
    public short getCancellationPolicyType() {
        return cancellationPolicyType;
    }

    public void setCancellationPolicyType(short cancellationPolicyType) {
        this.cancellationPolicyType = cancellationPolicyType;
    }

    @Basic
    @Column(name = "cancellationPolicy", nullable = false, length = 1000)
    public String getCancellationPolicy() {
        return cancellationPolicy;
    }

    public void setCancellationPolicy(String cancellationPolicy) {
        this.cancellationPolicy = cancellationPolicy;
    }

    @Basic
    @Column(name = "fullRefundAheadDays", nullable = true)
    public Integer getFullRefundAheadDays() {
        return fullRefundAheadDays;
    }

    public void setFullRefundAheadDays(Integer fullRefundAheadDays) {
        this.fullRefundAheadDays = fullRefundAheadDays;
    }

    @Basic
    @Column(name = "guestNumber", nullable = false)
    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    @Basic
    @Column(name = "tradingRules", nullable = true, length = 500)
    public String getTradingRules() {
        return tradingRules;
    }

    public void setTradingRules(String tradingRules) {
        this.tradingRules = tradingRules;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BnbOrderEntity that = (BnbOrderEntity) o;

        if (orderId != that.orderId) return false;
        if (ctripOrderId != that.ctripOrderId) return false;
        if (productId != that.productId) return false;
        if (quantity != that.quantity) return false;
        if (statusId != that.statusId) return false;
        if (paymentStatusId != that.paymentStatusId) return false;
        if (deleteStatusId != that.deleteStatusId) return false;
        if (processStatusId != that.processStatusId) return false;
        if (settleStatusId != that.settleStatusId) return false;
        if (hotelId != that.hotelId) return false;
        if (terminalType != that.terminalType) return false;
        if (extendStatusId != that.extendStatusId) return false;
        if (orderType != that.orderType) return false;
        if (isBnbOrder != that.isBnbOrder) return false;
        if (confirmType != that.confirmType) return false;
        if (arriveAt != that.arriveAt) return false;
        if (cancellationPolicyType != that.cancellationPolicyType) return false;
        if (guestNumber != that.guestNumber) return false;
        if (uid != null ? !uid.equals(that.uid) : that.uid != null) return false;
        if (orderSource != null ? !orderSource.equals(that.orderSource) : that.orderSource != null) return false;
        if (vendorId != null ? !vendorId.equals(that.vendorId) : that.vendorId != null) return false;
        if (vendorUid != null ? !vendorUid.equals(that.vendorUid) : that.vendorUid != null) return false;
        if (vendorOrderId != null ? !vendorOrderId.equals(that.vendorOrderId) : that.vendorOrderId != null)
            return false;
        if (saleAmount != null ? !saleAmount.equals(that.saleAmount) : that.saleAmount != null) return false;
        if (paymentAmount != null ? !paymentAmount.equals(that.paymentAmount) : that.paymentAmount != null)
            return false;
        if (purchaseAmount != null ? !purchaseAmount.equals(that.purchaseAmount) : that.purchaseAmount != null)
            return false;
        if (serviceFee != null ? !serviceFee.equals(that.serviceFee) : that.serviceFee != null) return false;
        if (cleaningFee != null ? !cleaningFee.equals(that.cleaningFee) : that.cleaningFee != null) return false;
        if (securityDeposit != null ? !securityDeposit.equals(that.securityDeposit) : that.securityDeposit != null)
            return false;
        if (checkIn != null ? !checkIn.equals(that.checkIn) : that.checkIn != null) return false;
        if (checkOut != null ? !checkOut.equals(that.checkOut) : that.checkOut != null) return false;
        if (contactName != null ? !contactName.equals(that.contactName) : that.contactName != null) return false;
        if (contactMobile != null ? !contactMobile.equals(that.contactMobile) : that.contactMobile != null)
            return false;
        if (contactWechat != null ? !contactWechat.equals(that.contactWechat) : that.contactWechat != null)
            return false;
        if (guests != null ? !guests.equals(that.guests) : that.guests != null) return false;
        if (confirmationCode != null ? !confirmationCode.equals(that.confirmationCode) : that.confirmationCode != null)
            return false;
        if (settleRemark != null ? !settleRemark.equals(that.settleRemark) : that.settleRemark != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (dataChangeLastTime != null ? !dataChangeLastTime.equals(that.dataChangeLastTime) : that.dataChangeLastTime != null)
            return false;
        if (securityDepositType != null ? !securityDepositType.equals(that.securityDepositType) : that.securityDepositType != null)
            return false;
        if (ownerId != null ? !ownerId.equals(that.ownerId) : that.ownerId != null) return false;
        if (remark != null ? !remark.equals(that.remark) : that.remark != null) return false;
        if (discountAmount != null ? !discountAmount.equals(that.discountAmount) : that.discountAmount != null)
            return false;
        if (totalAmount != null ? !totalAmount.equals(that.totalAmount) : that.totalAmount != null) return false;
        if (onlinePayRate != null ? !onlinePayRate.equals(that.onlinePayRate) : that.onlinePayRate != null)
            return false;
        if (onlineAmount != null ? !onlineAmount.equals(that.onlineAmount) : that.onlineAmount != null) return false;
        if (offlineAmount != null ? !offlineAmount.equals(that.offlineAmount) : that.offlineAmount != null)
            return false;
        if (confirmedTime != null ? !confirmedTime.equals(that.confirmedTime) : that.confirmedTime != null)
            return false;
        if (cancellationPolicy != null ? !cancellationPolicy.equals(that.cancellationPolicy) : that.cancellationPolicy != null)
            return false;
        if (fullRefundAheadDays != null ? !fullRefundAheadDays.equals(that.fullRefundAheadDays) : that.fullRefundAheadDays != null)
            return false;
        if (tradingRules != null ? !tradingRules.equals(that.tradingRules) : that.tradingRules != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (uid != null ? uid.hashCode() : 0);
        result = 31 * result + (orderSource != null ? orderSource.hashCode() : 0);
        result = 31 * result + (vendorId != null ? vendorId.hashCode() : 0);
        result = 31 * result + (vendorUid != null ? vendorUid.hashCode() : 0);
        result = 31 * result + (vendorOrderId != null ? vendorOrderId.hashCode() : 0);
        result = 31 * result + (int) (ctripOrderId ^ (ctripOrderId >>> 32));
        result = 31 * result + (int) (productId ^ (productId >>> 32));
        result = 31 * result + quantity;
        result = 31 * result + (saleAmount != null ? saleAmount.hashCode() : 0);
        result = 31 * result + (paymentAmount != null ? paymentAmount.hashCode() : 0);
        result = 31 * result + (purchaseAmount != null ? purchaseAmount.hashCode() : 0);
        result = 31 * result + (serviceFee != null ? serviceFee.hashCode() : 0);
        result = 31 * result + (cleaningFee != null ? cleaningFee.hashCode() : 0);
        result = 31 * result + (securityDeposit != null ? securityDeposit.hashCode() : 0);
        result = 31 * result + (checkIn != null ? checkIn.hashCode() : 0);
        result = 31 * result + (checkOut != null ? checkOut.hashCode() : 0);
        result = 31 * result + (contactName != null ? contactName.hashCode() : 0);
        result = 31 * result + (contactMobile != null ? contactMobile.hashCode() : 0);
        result = 31 * result + (contactWechat != null ? contactWechat.hashCode() : 0);
        result = 31 * result + (guests != null ? guests.hashCode() : 0);
        result = 31 * result + (confirmationCode != null ? confirmationCode.hashCode() : 0);
        result = 31 * result + (int) statusId;
        result = 31 * result + (int) paymentStatusId;
        result = 31 * result + (int) deleteStatusId;
        result = 31 * result + (int) processStatusId;
        result = 31 * result + (int) settleStatusId;
        result = 31 * result + (settleRemark != null ? settleRemark.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (dataChangeLastTime != null ? dataChangeLastTime.hashCode() : 0);
        result = 31 * result + (int) (hotelId ^ (hotelId >>> 32));
        result = 31 * result + (int) terminalType;
        result = 31 * result + (int) extendStatusId;
        result = 31 * result + (int) orderType;
        result = 31 * result + (securityDepositType != null ? securityDepositType.hashCode() : 0);
        result = 31 * result + (ownerId != null ? ownerId.hashCode() : 0);
        result = 31 * result + (remark != null ? remark.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        result = 31 * result + (totalAmount != null ? totalAmount.hashCode() : 0);
        result = 31 * result + (onlinePayRate != null ? onlinePayRate.hashCode() : 0);
        result = 31 * result + (onlineAmount != null ? onlineAmount.hashCode() : 0);
        result = 31 * result + (offlineAmount != null ? offlineAmount.hashCode() : 0);
        result = 31 * result + (int) isBnbOrder;
        result = 31 * result + (int) confirmType;
        result = 31 * result + (confirmedTime != null ? confirmedTime.hashCode() : 0);
        result = 31 * result + arriveAt;
        result = 31 * result + (int) cancellationPolicyType;
        result = 31 * result + (cancellationPolicy != null ? cancellationPolicy.hashCode() : 0);
        result = 31 * result + (fullRefundAheadDays != null ? fullRefundAheadDays.hashCode() : 0);
        result = 31 * result + guestNumber;
        result = 31 * result + (tradingRules != null ? tradingRules.hashCode() : 0);
        return result;
    }
}
