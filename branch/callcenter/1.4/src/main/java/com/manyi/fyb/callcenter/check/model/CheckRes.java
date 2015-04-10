package com.manyi.fyb.callcenter.check.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

public class CheckRes {
	private int logId;//记录id
	/**
	 * 城市id 上海 北京等
	 */
	@Getter @Setter private int provinceId;
	/**
	 * 城市name 上海 北京等
	 */
	@Getter @Setter private String provinceName;
	
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private int houseId;
	private String builing;//栋座
	private String room;//室号
	private int publishId;//发布人
	private String publishName;
	private Date publishDate;//发布时间
	private String publishDateStr;
	private int operServiceId;//客服id 
	private String operServiceName;//客服名字
	private int operServiceState;//客服state
	private String operServiceStateStr;
	private String checkType;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
	private String checkTypeStr;
	
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
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
	public int getPublishId() {
		return publishId;
	}
	public void setPublishId(int publishId) {
		this.publishId = publishId;
	}
	public String getPublishName() {
		return publishName;
	}
	public void setPublishName(String publishName) {
		this.publishName = publishName;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishDateStr() {
		return publishDateStr;
	}
	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}
	public int getOperServiceId() {
		return operServiceId;
	}
	public void setOperServiceId(int operServiceId) {
		this.operServiceId = operServiceId;
	}
	public String getOperServiceName() {
		return operServiceName;
	}
	public void setOperServiceName(String operServiceName) {
		this.operServiceName = operServiceName;
	}
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
	}
	public String getCheckTypeStr() {
		return checkTypeStr;
	}
	public void setCheckTypeStr(String checkTypeStr) {
		this.checkTypeStr = checkTypeStr;
	}
	
}
