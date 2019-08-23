package com.flexibleemployment.dao.entity;

import java.util.Date;

public class User {
    private String openId;

    private String mobile;

    private String nickName;

    private String headerImg;

    private Date createdAt;

    private Date updatedAt;

    private Byte isWhiteList;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Byte getIsWhiteList() {
        return isWhiteList;
    }

    public void setIsWhiteList(Byte isWhiteList) {
        this.isWhiteList = isWhiteList;
    }
}