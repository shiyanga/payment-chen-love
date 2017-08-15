package com.payment.entity.groupwormhole;

import com.payment.entity.publicenitty.Entity;

import java.util.Date;
import java.util.List;
public class AuthUser extends Entity {
    private Integer id;
    private String employeeNumber;
    private String name;
    private String loginPassword;
    private String email;
    private String telephone;
    private String department;
    private Integer status;
    private String remark;
    private Date loginTime;
    private Date logoutTime;

    private List<AuthRole> roles;
    public AuthUser(){

    }
    public AuthUser(String employeeNumber, String name, String loginPassword, String email, String telephone, String department, Integer status, String remark) {
        this.employeeNumber = employeeNumber;
        this.name = name;
        this.loginPassword = loginPassword;

        this.email = email;
        this.telephone = telephone;
        this.department = department;
        this.status = status;
        this.remark = remark;
    }

    public enum StatusEnum {
        NORMAL(1, "正常"),
        DISABLE(2, "停用"),
        DELETED(3, "删除");

        private int code;
        private String desc;

        StatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static StatusEnum getItem(int typeCode) {
            if(typeCode<=0){
                return null;
            }
            for (StatusEnum statusEnum : StatusEnum.values()) {
                if (statusEnum.getCode() == typeCode) {
                    return statusEnum;
                }
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

    public enum  DepartmentEnum {
        LAD_HOTEL_DEVELOPMENT("LAD-HOTEL-DEVELOPMENT", "大住宿-酒店研发"),
        LAD_HOTEL_RESERVE("LAD-HOTEL-RESERVE", "大住宿-酒店预订部"),
        LAD_PLATFORM_BUSINESS("LAD-PLATFORM-BUSINESS", "大住宿-平台商务部"),
        LAD_O2O_MANAGEMENT("LAD-O2O-MANAGEMENT", "大住宿-O2O管理部"),
        LAD_CUSTOMER_SERVICE("LAD-CUSTOMER-SERVICE", "大住宿-客服部"),
        TUJIA_CUSTOMER_DEP("TUJIA-CUSTOMER-DEP", "途家-大客服"),
        OTHER("OTHER","其它");

        private String code;
        private String desc;

        DepartmentEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static DepartmentEnum getItem(String typeCode) {
            if(typeCode == null){
                return null;
            }
            for (DepartmentEnum statusEnum : DepartmentEnum.values()) {
                if (statusEnum.getCode().equalsIgnoreCase(typeCode)) {
                    return statusEnum;
                }
            }
            return null;
        }

        public String getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getName() {
        return name;
    }

    public List<AuthRole> getRoles() {
        return roles;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setRoles(List<AuthRole> roles) {
        this.roles = roles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }
}
