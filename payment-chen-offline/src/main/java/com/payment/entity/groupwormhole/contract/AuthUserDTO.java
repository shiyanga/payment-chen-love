package com.payment.entity.groupwormhole.contract;


import com.payment.entity.groupwormhole.AuthUser;
import com.payment.entity.publicenitty.BasicDTO;
import java.util.Date;
import java.util.List;

public class AuthUserDTO extends BasicDTO {

    private Integer id;
    private String employeeNumber;
    private String loginPassword;
    private String name;
    private String email;
    private String telephone;
    private String department;
    private Integer status;
    private String statusDesc;
    private Date loginTime;
    private Date logoutTime;
    private String remark;

    private List<AuthRoleDTO> roles;

    public AuthUserDTO(){

    }

    public String getStatusDesc() {
        if(status == null){
            return "";
        }
        AuthUser.StatusEnum statusEnum = AuthUser.StatusEnum.getItem(status);
        return statusEnum==null?"":statusEnum.getDesc();
    }

    public String getDepartmentDesc() {
        if(department == null){
            return "";
        }
        AuthUser.DepartmentEnum depStatus = AuthUser.DepartmentEnum.getItem(department);
        return depStatus==null? department:depStatus.getDesc();
    }

    public List<AuthRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<AuthRoleDTO> roles) {
        this.roles = roles;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
