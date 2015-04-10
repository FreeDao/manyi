package com.manyi.iw.agent.console.entity;

import java.util.Date;

public class User {
    /**  */
    private Long id;

    /**  */
    private String mobile;

    /**  */
    private String mobileSn;

    /**  */
    private String password;

    /**  */
    private String realName;

    /**  */
    private Byte gender;

    /**  */
    private Long agentId;

    /**  */
    private Date lastLoginTime;

    /** 用户状态，（0: 新用户，1 :老 用户） */
    private Byte userType;

    /** 系统状态 0 未激活 1，激活  2：删除 */
    private Byte sysStatus;

    /** 业务状态 （0 找房中，1 已租房） */
    private Byte bizStatus;

    /**  */
    private Date updateTime;

    /**  */
    private Date createTime;

    /**  */
    private String memo;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *                the  id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile
     *                the  mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the mobileSn
     */
    public String getMobileSn() {
        return mobileSn;
    }

    /**
     * @param mobileSn
     *                the  mobileSn to set
     */
    public void setMobileSn(String mobileSn) {
        this.mobileSn = mobileSn;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *                the  password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName
     *                the  realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return the gender
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * @param gender
     *                the  gender to set
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }

    /**
     * @return the agentId
     */
    public Long getAgentId() {
        return agentId;
    }

    /**
     * @param agentId
     *                the  agentId to set
     */
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    /**
     * @return the lastLoginTime
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     *                the  lastLoginTime to set
     */
    public void setLastLoginTimeWithDate(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
    
    public void setLastLoginTime(Long lastLoginTime) {
        if(updateTime!=null)
            this.lastLoginTime = new Date(lastLoginTime);
    }

    /**
     * @return the userType
     */
    public Byte getUserType() {
        return userType;
    }

    /**
     * @param userType
     *                the  userType to set
     */
    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    /**
     * @return the sysStatus
     */
    public Byte getSysStatus() {
        return sysStatus;
    }

    /**
     * @param sysStatus
     *                the  sysStatus to set
     */
    public void setSysStatus(Byte sysStatus) {
        this.sysStatus = sysStatus;
    }

    /**
     * @return the bizStatus
     */
    public Byte getBizStatus() {
        return bizStatus;
    }

    /**
     * @param bizStatus
     *                the  bizStatus to set
     */
    public void setBizStatus(Byte bizStatus) {
        this.bizStatus = bizStatus;
    }

    /**
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *                the  updateTime to set
     */
    public void setUpdateTimeWithDate(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public void setUpdateTime(Long updateTime) {
        if(updateTime != null){
            this.updateTime = new Date(updateTime);
        }
    }

    /**
     * @return the createTime
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     *                the  createTime to set
     */
    public void setCreateTimeWithDate(Date createTime) {
        this.createTime = createTime;
    }
    
    public void setCreateTime(Long createTime) {
        if(createTime!=null)
            this.createTime = new Date(createTime);
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @param memo
     *                the  memo to set
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }
}