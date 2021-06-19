package com.example.demo.entity.Req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UserBaseReq {
    private Integer uid;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 密码
     */
    private String pass;

    /**
     * 盐
     */
    private String salt;

    /**
     * 是否可登录（非冻结）1=是;2=否
     */
    private Boolean isEnable;

    /**
     * 到期时间
     */
    private String expireTime;

    /**
     * admin=后台人员 user=前台用户
     */
    private String userCate;

    /**
     * 密码输错次数
     */
    private Integer wrongTimes;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeH;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeL;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getUserCate() {
        return userCate;
    }

    public void setUserCate(String userCate) {
        this.userCate = userCate;
    }

    public Integer getWrongTimes() {
        return wrongTimes;
    }

    public void setWrongTimes(Integer wrongTimes) {
        this.wrongTimes = wrongTimes;
    }

    public LocalDateTime getTimeH() {
        return timeH;
    }

    public void setTimeH(LocalDateTime timeH) {
        this.timeH = timeH;
    }

    public LocalDateTime getTimeL() {
        return timeL;
    }

    public void setTimeL(LocalDateTime timeL) {
        this.timeL = timeL;
    }
    public String getCredentialsSalt() {
        return mobile + salt;
    }
}
