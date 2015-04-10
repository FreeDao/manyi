package com.manyi.hims.response;

import java.math.BigDecimal;
import java.util.Date;

import com.manyi.hims.Response;
import com.manyi.hims.util.EntityUtils;

public class HouseResourceResponse extends Response {
	private int houseId;//房屋ID
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private String room;// 房号（例如：1304室，1004－1008室等）
	private BigDecimal spaceArea;// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	
	private int estateId;//小区ID
	private String estateName;//小区名称
	private int areaId;//行政区ID
	private String areaName;//行政区name
	private int townId;//小区所属片区ID
	private String townName;//小区所属片区name
	private Date publishDate;//发布时间
	private String publishStr;//发布时间,格式化后的时间
	
	private BigDecimal price;//价格
	private int sourceState;//审核状态
	private String sourceStateStr;//审核状态对应的文本
	
	private int publishCount;//今天剩余查看房源的次数
	
	private Long markTime;//时间戳
	
	private int sourceLogId;
	
	public String getSourceStateStr() {
		return sourceStateStr;
	}
	
	public int getSourceLogId() {
		return sourceLogId;
	}

	public void setSourceLogId(int sourceLogId) {
		this.sourceLogId = sourceLogId;
	}

	public void setSourceStateStr(String sourceStateStr) {
		this.sourceStateStr = sourceStateStr;
	}
	public Long getMarkTime() {
		return markTime;
	}
	public void setMarkTime(Long markTime) {
		this.markTime = markTime;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getPublishStr() {
		return publishStr;
	}
	public void setPublishStr(String publishStr) {
		this.publishStr = publishStr;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
//		this.price= price;
		if(price != null){
			this.price = price.setScale(0,BigDecimal.ROUND_DOWN);//去掉小数点
		}else{
			this.price = BigDecimal.valueOf(0d);
		}
	}
	public int getSourceState() {
		return sourceState;
	}
	public void setSourceState(int sourceState) {
		switch (sourceState) {
		case 1:
			this.sourceStateStr="审核成功";
			break;
		case 2:
			this.sourceStateStr="审核中";
			break;
		case 3:
			this.sourceStateStr="审核失败";
			break;
		case 4:
			this.sourceStateStr="无效";
//			this.sourceState = 1;  tips Tom 
			break;
		}
		this.sourceState = sourceState;
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
	public int getPublishCount() {
		return publishCount;
	}
	public void setPublishCount(int publishCount) {
		this.publishCount = publishCount;
	}
}
