package com.manyi.iw.agent.console.entity;

import java.util.Date;

public class Agent {
    /**  */
    private Long id;

    /**  */
    private String name;

    /**  */
    private String serialNumber;

    /**  */
    private String password;

    /**  */
    private String mobile;

    /**  */
    private String photoUrl;

    /** 更新时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;

    /**  */
    private String memo;

    /**  */
    private Byte status;

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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *                the  name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the serialNumber
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * @param serialNumber
     *                the  serialNumber to set
     */
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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
     * @return the photoUrl
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * @param photoUrl
     *                the  photoUrl to set
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
        if(updateTime!=null)
            this.updateTime = new Date(updateTime);
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

    public void setCreateTime(Long createTime){
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

    /**
     * @return the status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     *                the  status to set
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}