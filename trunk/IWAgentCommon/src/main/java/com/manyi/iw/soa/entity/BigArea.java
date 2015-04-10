package com.manyi.iw.soa.entity;

import java.util.Date;

public class BigArea {
    /**  */
    private Long id;

    /**  */
    private String bigAreaName;

    /**  包含区域id 以英文逗号分隔 */
    private String includeAreaIds;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

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
     * @return the bigAreaName
     */
    public String getBigAreaName() {
        return bigAreaName;
    }

    /**
     * @param bigAreaName
     *                the  bigAreaName to set
     */
    public void setBigAreaName(String bigAreaName) {
        this.bigAreaName = bigAreaName;
    }

    /**
     * @return the includeAreaIds
     */
    public String getIncludeAreaIds() {
        return includeAreaIds;
    }

    /**
     * @param includeAreaIds
     *                the  includeAreaIds to set
     */
    public void setIncludeAreaIds(String includeAreaIds) {
        this.includeAreaIds = includeAreaIds;
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