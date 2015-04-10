package com.manyi.iw.soa.entity;

import java.util.Date;

public class Appointment {
    /**  */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 经纪人id */
    private Long agentId;

    /** 约看时间 */
    private Date appointmentTime;

    /** 约看地点 */
    private String meetAddress;

    /** 约看状态 */
    private Byte appointmentState;

    /** 专业知识 */
    private Byte ability;

    /** 仪容仪表 */
    private Byte appearance;

    /** 服务态度 */
    private Byte attitude;

    /** 评价 */
    private String appraise;

    /** 改期说明 */
    private String changeDateMemo;

    /** 取消说明 */
    private String cancelMemo;

    /** 取消原因
    1.测试
    2.测试2
    3.测试3 */
    private Byte cancelReason;

    /** 备注 */
    private String memo;

    /** 更新时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;
    /**  看房单号 **/
    private String seehouseNumber;

    /***
     * 用户签到时间
     */
    private Date userCheckinTime;
    /***
     * 经纪人签到时间
     */
    private Date agentCheckinTime;

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

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    /**
     * @return the meetAddress
     */
    public String getMeetAddress() {
        return meetAddress;
    }

    /**
     * @param meetAddress
     *                the  meetAddress to set
     */
    public void setMeetAddress(String meetAddress) {
        this.meetAddress = meetAddress;
    }

    /**
     * @return the appointmentState
     */
    public Byte getAppointmentState() {
        return appointmentState;
    }

    /**
     * @param appointmentState
     *                the  appointmentState to set
     */
    public void setAppointmentState(Byte appointmentState) {
        this.appointmentState = appointmentState;
    }

    /**
     * @return the ability
     */
    public Byte getAbility() {
        return ability;
    }

    /**
     * @param ability
     *                the  ability to set
     */
    public void setAbility(Byte ability) {
        this.ability = ability;
    }

    /**
     * @return the appearance
     */
    public Byte getAppearance() {
        return appearance;
    }

    /**
     * @param appearance
     *                the  appearance to set
     */
    public void setAppearance(Byte appearance) {
        this.appearance = appearance;
    }

    /**
     * @return the attitude
     */
    public Byte getAttitude() {
        return attitude;
    }

    /**
     * @param attitude
     *                the  attitude to set
     */
    public void setAttitude(Byte attitude) {
        this.attitude = attitude;
    }

    /**
     * @return the appraise
     */
    public String getAppraise() {
        return appraise;
    }

    /**
     * @param appraise
     *                the  appraise to set
     */
    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    /**
     * @return the changeDateMemo
     */
    public String getChangeDateMemo() {
        return changeDateMemo;
    }

    /**
     * @param changeDateMemo
     *                the  changeDateMemo to set
     */
    public void setChangeDateMemo(String changeDateMemo) {
        this.changeDateMemo = changeDateMemo;
    }

    /**
     * @return the cancelMemo
     */
    public String getCancelMemo() {
        return cancelMemo;
    }

    /**
     * @param cancelMemo
     *                the  cancelMemo to set
     */
    public void setCancelMemo(String cancelMemo) {
        this.cancelMemo = cancelMemo;
    }

    /**
     * @return the cancelReason
     */
    public Byte getCancelReason() {
        return cancelReason;
    }

    /**
     * @param cancelReason
     *                the  cancelReason to set
     */
    public void setCancelReason(Byte cancelReason) {
        this.cancelReason = cancelReason;
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

    public String getSeehouseNumber() {
        return seehouseNumber;
    }

    public void setSeehouseNumber(String seehouseNumber) {
        this.seehouseNumber = seehouseNumber;
    }

    public Date getUserCheckinTime() {
        return userCheckinTime;
    }

    public void setUserCheckinTime(Date userCheckinTime) {
        this.userCheckinTime = userCheckinTime;
    }

    public Date getAgentCheckinTime() {
        return agentCheckinTime;
    }

    public void setAgentCheckinTime(Date agentCheckinTime) {
        this.agentCheckinTime = agentCheckinTime;
    }

}