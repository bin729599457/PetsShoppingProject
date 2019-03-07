package com.example.springboot.demo.domain;

import java.util.Date;

public class TBUser {

    private Long id;
    private String account;
    private String password;
    private String userName;
    private Integer isRoot;
    private String phone;
    private Date createTime;
    private String address;

    public TBUser(Long id, String account, String password, String userName, Integer isRoot, String phone, Date createTime, String address) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.userName = userName;
        this.isRoot = isRoot;
        this.phone = phone;
        this.createTime = createTime;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
