package com.manyi.iw.soa.entity;

import java.util.Date;

public class Recommend {
    /**  */
    private Long id;

    /**  */
    private Long userId;

    /**  */
    private Long agentId;

    /**  */
    private Long houseId;

    /** 0：未约看，1：约看 */
    private Byte seeStatus;

    /** 0：未删除，1：已删除 */
    private Byte status;

    /** 推荐时间 */
    private Date createTime;

    /**  */
    private Date updateTime;

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
     * @return the seeStatus
     */
    public Byte getSeeStatus() {
        return seeStatus;
    }

    /**
     * @param seeStatus
     *                the  seeStatus to set
     */
    public void setSeeStatus(Byte seeStatus) {
        this.seeStatus = seeStatus;
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
}