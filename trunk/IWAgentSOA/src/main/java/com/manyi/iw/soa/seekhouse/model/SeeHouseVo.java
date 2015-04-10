package com.manyi.iw.soa.seekhouse.model;

import java.util.Date;

/**
 * Function: TODO 待处理 已取消 列表 vo 
 *
 * @author   yufei
 * @Date	 2014年6月18日		上午11:03:34
 *
 * @see 	  
 */
public class SeeHouseVo {

    private Long id;

    private Long houseId;
    private String bankuai;
    private String xiaoqu;
    private String fanghao;
    private String huxing;
    private Integer jiage;
    private String lianxifs;
    private Byte state;
    private String memo;

    private String wishTime;

    private Date seeHouseTime;

    private String path;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSeeHouseTime() {
        return seeHouseTime;
    }

    public void setSeeHouseTime(Date seeHouseTime) {
        this.seeHouseTime = seeHouseTime;
    }

    public String getWishTime() {
        return wishTime;
    }

    public void setWishTime(String wishTime) {
        this.wishTime = wishTime;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public String getBankuai() {
        return bankuai;
    }

    public void setBankuai(String bankuai) {
        this.bankuai = bankuai;
    }

    public String getXiaoqu() {
        return xiaoqu;
    }

    public void setXiaoqu(String xiaoqu) {
        this.xiaoqu = xiaoqu;
    }

    public String getFanghao() {
        return fanghao;
    }

    public void setFanghao(String fanghao) {
        this.fanghao = fanghao;
    }

    public String getHuxing() {
        return huxing;
    }

    public void setHuxing(String huxing) {
        this.huxing = huxing;
    }

    public Integer getJiage() {
        return jiage;
    }

    public void setJiage(Integer jiage) {
        this.jiage = jiage;
    }

    public String getLianxifs() {
        return lianxifs;
    }

    public void setLianxifs(String lianxifs) {
        this.lianxifs = lianxifs;
    }

    public Byte getState() {
        return state;
    }

    public void setState(Byte state) {
        this.state = state;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
