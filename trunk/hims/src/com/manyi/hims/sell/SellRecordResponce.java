/**
 * 
 */
package com.manyi.hims.sell;

import java.math.BigDecimal;
import java.util.Date;

import com.manyi.hims.Response;

/**
 * @author zxc
 *
 */
public class SellRecordResponce  extends Response{

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
	private Date publishDate;//发布时间
	private int sourceState;//审核状态
	private String sourceStateStr;//审核状态对应的文本
	private int sourceLogTypeId; //发布状态
	private String sourceLogTypeStr; //发布状态对应的文本
	private BigDecimal price;//价格
	
	private String hostName;//联系人姓名   多个就用, 隔开
	private String houstMobile;//联系人电话  多个就用, 隔开 ; 这里的联系电话 跟 联系人是一一对应的

	public int getHouseId() {
		return houseId;
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


	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getSourceState() {
		return sourceState;
	}

	public void setSourceState(int sourceState) {
		this.sourceState = sourceState;
	}

	public String getSourceStateStr() {
		return sourceStateStr;
	}

	public void setSourceStateStr(String sourceStateStr) {
		this.sourceStateStr = sourceStateStr;
	}

	public int getSourceLogTypeId() {
		return sourceLogTypeId;
	}

	public void setSourceLogTypeId(int sourceLogTypeId) {
		this.sourceLogTypeId = sourceLogTypeId;
	}

	public String getSourceLogTypeStr() {
		return sourceLogTypeStr;
	}

	public void setSourceLogTypeStr(String sourceLogTypeStr) {
		this.sourceLogTypeStr = sourceLogTypeStr;
	}
}
