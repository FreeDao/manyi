package com.manyi.iw.soa.user.model;

/**
 * Function: 更改房源状态 请求体
 *
 * @author   yufei
 * @Date	 2014年6月25日		上午11:20:18
 *
 * @see 	  
 */
public class UpdateDiscContentRequest {
    private int userId;//用户ID
    private int houseId;//房源ID
    private int sellType;//出售状态(1.已出售;2,不出售)
    private int rentType;//出租状态(1.已出租;2,不出租)
    private String hostName;//联系人姓名
    private String hostMobile;//联系人电话
    private String remark;//理由

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getHouseId() {
        return houseId;
    }

    public void setHouseId(int houseId) {
        this.houseId = houseId;
    }

    public int getSellType() {
        return sellType;
    }

    public void setSellType(int sellType) {
        this.sellType = sellType;
    }

    public int getRentType() {
        return rentType;
    }

    public void setRentType(int rentType) {
        this.rentType = rentType;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getHostMobile() {
        return hostMobile;
    }

    public void setHostMobile(String hostMobile) {
        this.hostMobile = hostMobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
