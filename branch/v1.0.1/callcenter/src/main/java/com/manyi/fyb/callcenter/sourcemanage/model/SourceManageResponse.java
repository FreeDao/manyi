package com.manyi.fyb.callcenter.sourcemanage.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * @date 2014年4月17日 下午12:47:51
 * @author Tom
 * @description 房源管理详情页 响应对象
 */
public class SourceManageResponse {
	
	
	// 房源编号：MY98686
	private int houseId;
	// 栋座：
	private String building;
	
	// 房型：2室2厅1卫
	// 2室
	private int bedroomSum;
	// 2厅
	private int livingRoomSum;
	// 1卫
	private int wcSum;
	
	// 室号：
	private String room;
	// 面积：
	private BigDecimal coveredArea;

	
//	子小区
	private String subEstateName;
//	主小区
	private String estateName;
//	板块
	private String townName;
//	区域
	private String cityName;
	
	
//	出租或出售 sell、rent、notsell、notrent、
	private String rentOrSell;
//	价格
	private BigDecimal price;
	
	private List sourceHostList;
	private List<Object[]> auditList;
	private List<SourceManageResponse> rentAndSellList;
	
	
	
	
	
	
	
	
	
 

	public List<SourceManageResponse> getRentAndSellList() {
		return rentAndSellList;
	}

	public void setRentAndSellList(List<SourceManageResponse> rentAndSellList) {
		this.rentAndSellList = rentAndSellList;
	}

	public List<Object[]> getAuditList() {
		return auditList;
	}

	public void setAuditList(List<Object[]> auditList) {
		this.auditList = auditList;
	}

	 
	

	public List getSourceHostList() {
		return sourceHostList;
	}

	public void setSourceHostList(List sourceHostList) {
		this.sourceHostList = sourceHostList;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}


	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
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

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public BigDecimal getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(BigDecimal coveredArea) {
		this.coveredArea = coveredArea;
	}

	public String getSubEstateName() {
		return subEstateName;
	}

	public void setSubEstateName(String subEstateName) {
		this.subEstateName = subEstateName;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getRentOrSell() {
		return rentOrSell;
	}

	public void setRentOrSell(String rentOrSell) {
		this.rentOrSell = rentOrSell;
	}

	 

}
