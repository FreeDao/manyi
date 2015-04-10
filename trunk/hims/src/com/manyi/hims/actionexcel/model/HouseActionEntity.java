package com.manyi.hims.actionexcel.model;


import java.text.SimpleDateFormat;
import java.util.Date;

public class HouseActionEntity{
	private int sourceLogId;
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private String room;// 房号（例如：1304室，1004－1008室等）
	private String spaceArea;// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	
	private String estateName;//小区名称
	private String road;//二机小区
	private String areaName;//行政区name
	private String townName;//小区所属片区name
	private Date publishDate;//发布时间
	private String publishStr;//发布时间,格式化后的时间
	
	private String price;//价格
	private int sourceState;//审核状态
	private String sourceStateStr;//审核状态对应的文本
	private int logType ;//发布类型
	private String logTypeStr;//对应的类型的字符串  记录 类型(1.发布出售2.发布出租3.改盘4.举报5.新增小区)
	
	private String userName;//发布人 姓名
	private String userMobile;//发布人电话
	private String hostName;//联系人姓名
	private String hostMobile;//联系人电话
	
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
	public String getRoad() {
		return road;
	}
	public void setRoad(String road) {
		this.road = road;
	}
	public int getSourceLogId() {
		return sourceLogId;
	}
	public void setSourceLogId(int sourceLogId) {
		this.sourceLogId = sourceLogId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMobile() {
		return userMobile;
	}
	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	public int getLogType() {
		return logType;
	}
	public void setLogType(int logType) {
		//(1.发布出售2.发布出租3.改盘4.举报5.新增小区)
		switch (logType) {
		case 1:
			this.logTypeStr="出售";
			break;
		case 2:
			this.logTypeStr="出租";
			break;
		case 3:
			this.logTypeStr="改盘";
			break;
		case 4:
			this.logTypeStr="举报";
			break;
		case 5:
			this.logTypeStr="新增小区";
			break;
		default:
			break;
		}
		this.logType = logType;
	}
	public String getLogTypeStr() {
		return logTypeStr;
	}
	public String getSourceStateStr() {
		return sourceStateStr;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
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
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		
		this.publishDate = publishDate;
		this.publishStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(publishDate);
	}
	public String getPublishStr() {
		return publishStr;
	}
	public int getSourceState() {
		return sourceState;
	}
	public void setSourceState(int sourceState) {
		switch (sourceState) {
		case 1:
			this.sourceStateStr="审核中";
			break;
		case 0:
			this.sourceStateStr="审核成功";
			break;
		case 2:
			this.sourceStateStr="审核失败";
			break;
		}
		this.sourceState = sourceState;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public String getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(String spaceArea) {
		this.spaceArea = spaceArea;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

}
