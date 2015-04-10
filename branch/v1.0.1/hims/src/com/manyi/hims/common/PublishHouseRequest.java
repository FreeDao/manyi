package com.manyi.hims.common;

import java.math.BigDecimal;

public class PublishHouseRequest {
	private int logId;//修改是的时候使用,通过这个id修改操作记录
	private int houseId;//房屋ID
	private int userId;//用户ID
	private BigDecimal spaceArea;// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private BigDecimal price;//价格
	private String hostName;//联系人姓名   多个就用, 隔开
	private String houstMobile;//联系人电话  多个就用, 隔开 ; 这里的联系电话 跟 联系人是一一对应的
	
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
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
	public BigDecimal getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = spaceArea;
	}
	public int getBedroomSum() {
		return bedroomSum;
	}
	public void setBedroomSum(int bedroomSum) {
		this.bedroomSum = bedroomSum;
	}
	public int getLivingRoomSum() {
		return livingRoomSum;
	}
	public void setLivingRoomSum(int livingRoomSum) {
		this.livingRoomSum = livingRoomSum;
	}
	public int getWcSum() {
		return wcSum;
	}
	public void setWcSum(int wcSum) {
		this.wcSum = wcSum;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHoustMobile() {
		return houstMobile;
	}
	public void setHoustMobile(String houstMobile) {
		this.houstMobile = houstMobile;
	}
	
}
