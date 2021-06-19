package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wq
 * @since 2019-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Surety implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 借款人uid
     */
    private Integer uid;

    /**
     * 担保人姓名
     */
    private String suretyName;

    /**
     * 担保人电话
     */
    private String suretyMobile;

    /**
     * 担保人手机服务密码
     */
    private String mobileServicePassword;

    /**
     * 关系
     */
    private String relation;

    /**
     * 担保人身份证
     */
    private String suretyPersonId;

    /**
     * 身份证图片
     */
    private String fids;

    /**
     * 担保人户籍
     */
    private String suretyCensus;

    /**
     * 担保人地址
     */
    private String suretyAddr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getSuretyName() {
        return suretyName;
    }

    public void setSuretyName(String suretyName) {
        this.suretyName = suretyName;
    }

    public String getSuretyMobile() {
        return suretyMobile;
    }

    public void setSuretyMobile(String suretyMobile) {
        this.suretyMobile = suretyMobile;
    }

    public String getMobileServicePassword() {
        return mobileServicePassword;
    }

    public void setMobileServicePassword(String mobileServicePassword) {
        this.mobileServicePassword = mobileServicePassword;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getSuretyPersonId() {
        return suretyPersonId;
    }

    public void setSuretyPersonId(String suretyPersonId) {
        this.suretyPersonId = suretyPersonId;
    }

    public String getFids() {
        return fids;
    }

    public void setFids(String fids) {
        this.fids = fids;
    }

    public String getSuretyCensus() {
        return suretyCensus;
    }

    public void setSuretyCensus(String suretyCensus) {
        this.suretyCensus = suretyCensus;
    }

    public String getSuretyAddr() {
        return suretyAddr;
    }

    public void setSuretyAddr(String suretyAddr) {
        this.suretyAddr = suretyAddr;
    }
}
