package com.manyi.fyb.callcenter.estate.model;

import java.util.Date;

public class EstateRes {

	private int logId;//记录id
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String road;
	private Date publishDate;//发布 出售时间
	private String publishDateStr;
	private int sourceState;
	private String sourceStateStr;
	private int publishId;//发布人
	private String publishStr;
	private int sellNum;//在售数量
	private int rentNum;//在租数量
	private int houseNum;//房源数量
	
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public int getSellNum() {
		return sellNum;
	}
	public void setSellNum(int sellNum) {
		this.sellNum = sellNum;
	}
	public int getRentNum() {
		return rentNum;
	}
	public void setRentNum(int rentNum) {
		this.rentNum = rentNum;
	}
	public int getHouseNum() {
		return houseNum;
	}
	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getSourceState() {
		return sourceState;
	}
	public void setSourceState(int sourceState) {
		this.sourceState = sourceState;
	}
	public int getPublishId() {
		return publishId;
	}
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	public String getPublishDateStr() {
		return publishDateStr;
	}
	public String getSourceStateStr() {
		return sourceStateStr;
	}
	public String getPublishStr() {
		return publishStr;
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
	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}
	public void setSourceStateStr(String sourceStateStr) {
		this.sourceStateStr = sourceStateStr;
	}
	public void setPublishStr(String publishStr) {
		this.publishStr = publishStr;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
}
