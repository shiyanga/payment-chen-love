package com.payment.entity.groupwormhole;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shi_y on 2016/8/4.
 */
@Entity
@Table(name = "bnb_vendor", schema = "groupwormholedb", catalog = "")
public class BnbVendorEntity {
    private int id;
    private Integer vendorId;
    private String vendorName;
    private Short vendorType;
    private Integer serviceFee;
    private Date createTime;
    private Date dataChangeLastTime;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "vendorName", nullable = true, length = 200)
    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Basic
    @Column(name = "vendorType", nullable = true)
    public Short getVendorType() {
        return vendorType;
    }

    public void setVendorType(Short vendorType) {
        this.vendorType = vendorType;
    }

    @Basic
    @Column(name = "serviceFee", nullable = true, precision = 0)
    public Integer getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(Integer serviceFee) {
        this.serviceFee = serviceFee;
    }

    @Basic
    @Column(name = "createTime", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "dataChange_LastTime", nullable = false)
    public Date getDataChangeLastTime() {
        return dataChangeLastTime;
    }

    public void setDataChangeLastTime(Date dataChangeLastTime) {
        this.dataChangeLastTime = dataChangeLastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BnbVendorEntity that = (BnbVendorEntity) o;

        if (id != that.id) return false;
        if (vendorId != null ? !vendorId.equals(that.vendorId) : that.vendorId != null) return false;
        if (vendorName != null ? !vendorName.equals(that.vendorName) : that.vendorName != null) return false;
        if (vendorType != null ? !vendorType.equals(that.vendorType) : that.vendorType != null) return false;
        if (serviceFee != null ? !serviceFee.equals(that.serviceFee) : that.serviceFee != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (dataChangeLastTime != null ? !dataChangeLastTime.equals(that.dataChangeLastTime) : that.dataChangeLastTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (vendorId != null ? vendorId.hashCode() : 0);
        result = 31 * result + (vendorName != null ? vendorName.hashCode() : 0);
        result = 31 * result + (vendorType != null ? vendorType.hashCode() : 0);
        result = 31 * result + (serviceFee != null ? serviceFee.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (dataChangeLastTime != null ? dataChangeLastTime.hashCode() : 0);
        return result;
    }


}
