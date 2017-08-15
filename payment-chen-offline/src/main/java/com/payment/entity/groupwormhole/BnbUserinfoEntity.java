package com.payment.entity.groupwormhole;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shi_y on 2016/8/4.
 */
@Entity
@Table(name = "bnb_userinfo", schema = "groupwormholedb", catalog = "")
public class BnbUserinfoEntity {
    private long userId;
    private String userName;
    private String sourceUserId;
    private Integer userFrom;
    private Integer userType;
    private String userHeadUrl;
    private Date dataCreateLastTime;
    private Date dataChangeLastTime;

    @Id
    @Column(name = "userId", nullable = false)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "userName", nullable = true, length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "sourceUserId", nullable = true, length = 50)
    public String getSourceUserId() {
        return sourceUserId;
    }

    public void setSourceUserId(String sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    @Basic
    @Column(name = "userFrom", nullable = true)
    public Integer getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Integer userFrom) {
        this.userFrom = userFrom;
    }

    @Basic
    @Column(name = "userType", nullable = true)
    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Basic
    @Column(name = "userHeadUrl", nullable = true, length = 300)
    public String getUserHeadUrl() {
        return userHeadUrl;
    }

    public void setUserHeadUrl(String userHeadUrl) {
        this.userHeadUrl = userHeadUrl;
    }

    @Basic
    @Column(name = "dataCreate_LastTime", nullable = true)
    public Date getDataCreateLastTime() {
        return dataCreateLastTime;
    }

    public void setDataCreateLastTime(Date dataCreateLastTime) {
        this.dataCreateLastTime = dataCreateLastTime;
    }

    @Basic
    @Column(name = "dataChange_lastTime", nullable = true)
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

        BnbUserinfoEntity that = (BnbUserinfoEntity) o;

        if (userId != that.userId) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (sourceUserId != null ? !sourceUserId.equals(that.sourceUserId) : that.sourceUserId != null) return false;
        if (userFrom != null ? !userFrom.equals(that.userFrom) : that.userFrom != null) return false;
        if (userType != null ? !userType.equals(that.userType) : that.userType != null) return false;
        if (userHeadUrl != null ? !userHeadUrl.equals(that.userHeadUrl) : that.userHeadUrl != null) return false;
        if (dataCreateLastTime != null ? !dataCreateLastTime.equals(that.dataCreateLastTime) : that.dataCreateLastTime != null)
            return false;
        if (dataChangeLastTime != null ? !dataChangeLastTime.equals(that.dataChangeLastTime) : that.dataChangeLastTime != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (sourceUserId != null ? sourceUserId.hashCode() : 0);
        result = 31 * result + (userFrom != null ? userFrom.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (userHeadUrl != null ? userHeadUrl.hashCode() : 0);
        result = 31 * result + (dataCreateLastTime != null ? dataCreateLastTime.hashCode() : 0);
        result = 31 * result + (dataChangeLastTime != null ? dataChangeLastTime.hashCode() : 0);
        return result;
    }
}
