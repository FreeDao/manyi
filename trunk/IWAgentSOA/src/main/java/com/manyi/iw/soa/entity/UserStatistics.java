package com.manyi.iw.soa.entity;

import java.util.Date;

public class UserStatistics {
    /**  */
    private Long id;

    /**  */
    private Long userId;

    /** 已申请约看 */
    private Integer applyedNum;

    /** 已看房源数 */
    private Integer sawNum;

    /** 看房单房源数 */
    private Integer inchartNum;

    /** 推荐房源数 */
    private Integer recommendNum;

    /** 待处理申请数 */
    private Integer waitdealNum;

    /** 收藏房源数 */
    private Integer collectionNum;

    /**  */
    private String memo;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;

    /** 搜索关键字，用json表示{' keyword':'num'} */
    private String seachKeyword;

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
     * @return the applyedNum
     */
    public Integer getApplyedNum() {
        return applyedNum;
    }

    /**
     * @param applyedNum
     *                the  applyedNum to set
     */
    public void setApplyedNum(Integer applyedNum) {
        this.applyedNum = applyedNum;
    }

    /**
     * @return the sawNum
     */
    public Integer getSawNum() {
        return sawNum;
    }

    /**
     * @param sawNum
     *                the  sawNum to set
     */
    public void setSawNum(Integer sawNum) {
        this.sawNum = sawNum;
    }

    /**
     * @return the inchartNum
     */
    public Integer getInchartNum() {
        return inchartNum;
    }

    /**
     * @param inchartNum
     *                the  inchartNum to set
     */
    public void setInchartNum(Integer inchartNum) {
        this.inchartNum = inchartNum;
    }

    /**
     * @return the recommendNum
     */
    public Integer getRecommendNum() {
        return recommendNum;
    }

    /**
     * @param recommendNum
     *                the  recommendNum to set
     */
    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
    }

    /**
     * @return the waitdealNum
     */
    public Integer getWaitdealNum() {
        return waitdealNum;
    }

    /**
     * @param waitdealNum
     *                the  waitdealNum to set
     */
    public void setWaitdealNum(Integer waitdealNum) {
        this.waitdealNum = waitdealNum;
    }

    /**
     * @return the collectionNum
     */
    public Integer getCollectionNum() {
        return collectionNum;
    }

    /**
     * @param collectionNum
     *                the  collectionNum to set
     */
    public void setCollectionNum(Integer collectionNum) {
        this.collectionNum = collectionNum;
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
     * @return the seachKeyword
     */
    public String getSeachKeyword() {
        return seachKeyword;
    }

    /**
     * @param seachKeyword
     *                the  seachKeyword to set
     */
    public void setSeachKeyword(String seachKeyword) {
        this.seachKeyword = seachKeyword;
    }
}