package com.manyi.ihouse.user.model;

/**
 * 房源基本信息模型 用于“行程-已看”模块
 * @author hubin
 *
 */
public class HouseSummaryModel {

	private long houseId;//房源id
	
	private String address;//房源地址
	
	private String price;//价格
	
	private String seeState;//状态
	
	private long estateId;//小区ID

	public long getEstateId() {
        return estateId;
    }

    public void setEstateId(long estateId) {
        this.estateId = estateId;
    }

    public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSeeState() {
		return seeState;
	}

	public void setSeeState(String seeState) {
		this.seeState = seeState;
	}

}
