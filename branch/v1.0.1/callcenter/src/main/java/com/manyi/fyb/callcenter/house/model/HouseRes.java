package com.manyi.fyb.callcenter.house.model;

import java.util.Date;

public class HouseRes {

	private int houseId;//记录id
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String builing;//栋座
	private String room;//室号
	private Date sellPublishDate;//发布 出售时间
	private String sellPublishDateStr;
	private Date rentPublishDate;//发布 出租时间
	private String rentPublishDateStr;
	private int sellState;//出售状态
	private String sellStateStr;
	private int rentState;//出租状态
	private String rentStateStr;
	private String checkType;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
	private String checkTypeStr;
	private int operServiceState;//审核状态(1,审核成功;2,审核中;3,审核失败)
	private String operServiceStateStr;
	
	public int getOperServiceState() {
		return operServiceState;
	}
	public void setOperServiceState(int operServiceState) {
		this.operServiceState = operServiceState;
	}
	public String getOperServiceStateStr() {
		return operServiceStateStr;
	}
	public void setOperServiceStateStr(String operServiceStateStr) {
		this.operServiceStateStr = operServiceStateStr;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
		//(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
		if("SellLog".equals(checkType)){
			this.checkTypeStr="发布出售";
		}else if("RentLog".equals(checkType)){
			this.checkTypeStr="发布出租";
		}else if("UpdateDiscLog".equals(checkType)){
			this.checkTypeStr="改盘";
		}else if("ReportLog".equals(checkType)){
			this.checkTypeStr="举报";
		}else if("LoopLog".equals(checkType)){
			this.checkTypeStr="客服轮询";
		}else if("RandomLog".equals(checkType)){
			this.checkTypeStr="抽查看房";
		}
	}
	public String getCheckTypeStr() {
		return checkTypeStr;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getTownId() {
		return townId;
	}
	public void setTownId(int townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public int getEstateId() {
		return estateId;
	}
	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public String getBuiling() {
		return builing;
	}
	public void setBuiling(String builing) {
		this.builing = builing;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public Date getSellPublishDate() {
		return sellPublishDate;
	}
	public void setSellPublishDate(Date sellPublishDate) {
		this.sellPublishDate = sellPublishDate;
	}
	public Date getRentPublishDate() {
		return rentPublishDate;
	}
	public void setRentPublishDate(Date rentPublishDate) {
		this.rentPublishDate = rentPublishDate;
	}
	public int getSellState() {
		return sellState;
	}
	public void setSellState(int sellState) {
		this.sellState = sellState;
	}
	public int getRentState() {
		return rentState;
	}
	public void setRentState(int rentState) {
		this.rentState = rentState;
	}
	public String getSellPublishDateStr() {
		return sellPublishDateStr;
	}
	public String getRentPublishDateStr() {
		return rentPublishDateStr;
	}
	public String getSellStateStr() {
		return sellStateStr;
	}
	public String getRentStateStr() {
		return rentStateStr;
	}
	
	

}
