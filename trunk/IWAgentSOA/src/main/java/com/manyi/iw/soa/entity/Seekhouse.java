package com.manyi.iw.soa.entity;

import java.util.Date;

public class Seekhouse {
    /**  */
    private Long id;

    /** 约会id */
    private Long appointmentId;

    /** 经纪人id */
    private Long agentId;

    /** 用户ID */
    private Long userId;

    /** 房源id */
    private Long houseId;

    /** 约看状态 */
    private Byte state;

    /**  */
    private String memo;

    /** 更新时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;
    /** 希望约看时间**/
    private String wishTime;

    private Byte recommendSource;

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
     * @return the appointmentId
     */
    public Long getAppointmentId() {
        return appointmentId;
    }

    /**
     * @param appointmentId
     *                the  appointmentId to set
     */
    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
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
     * @return the userId
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     *                the  userId to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return the houseId
     */
    public Long getHouseId() {
        return houseId;
    }

    /**
     * @param houseId
     *                the  houseId to set
     */
    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    /**
     * @return the state
     */
    public Byte getState() {
        return state;
    }

    /**
     * @param state
     *                the  state to set
     */
    public void setState(Byte state) {
        this.state = state;
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
     * @return the updateTime
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     *                the  updateTime to set
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getWishTime() {
        return wishTime;
    }

    public void setWishTime(String wishTime) {
        this.wishTime = wishTime;
    }

    public Byte getRecommendSource() {
        return recommendSource;
    }

    public void setRecommendSource(Byte recommendSource) {
        this.recommendSource = recommendSource;
    }

}