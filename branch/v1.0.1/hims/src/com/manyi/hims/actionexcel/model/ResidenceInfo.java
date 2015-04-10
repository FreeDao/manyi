package com.manyi.hims.actionexcel.model;

import java.math.BigDecimal;

import com.manyi.hims.Response;

public class ResidenceInfo extends Response{
	
	private int hid; // 对象id
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	private BigDecimal coveredArea;// 建筑面积
	private BigDecimal spaceArea;// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private int balconySum;// 几阳台
	
	private int directionId ;  //房屋朝向id
	private int  estateId ;  //小区id
	private int  mainOwnerId ;  //业务id
	private int  rightId ;  //产权id
	private int  typeId ; //房屋类型id
	private int areaId; //区域id
	private int statusId;//状态id
	private int useageId;//用途id
	private String areaType; // 地址类型(area_city, area_province, area_town, area_zone)
	
	private String directionTitle ;  //房屋朝向 title
	private String  estateTitle ; //小区 名称
	private String  mainOwnerTitle ;  //业务真实名称
	private String  rightTitle ;  //产权 名称
	private String  typeTitle ; //房屋类型
	private String areaTitle;//区域名称
	private String statusTitle; //状态 名称
	private int useageTitle;//用途
	
	
	private String remark;
	private String ownerName;
	private String ownerTel;
	private String ownerMobile;
	private String id;
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getHid() {
		return hid;
	}
	public void setHid(int hid) {
		this.hid = hid;
	}
	public String getBuilding() {
		return building;
	}
	public void setBuilding(String building) {
		this.building = building;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getLayers() {
		return layers;
	}
	public void setLayers(int layers) {
		this.layers = layers;
	}
	public BigDecimal getCoveredArea() {
		return coveredArea;
	}
	public void setCoveredArea(BigDecimal coveredArea) {
		this.coveredArea = coveredArea;
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
	public int getBalconySum() {
		return balconySum;
	}
	public void setBalconySum(int balconySum) {
		this.balconySum = balconySum;
	}
	public int getDirectionId() {
		return directionId;
	}
	public void setDirectionId(int directionId) {
		this.directionId = directionId;
	}
	public int getEstateId() {
		return estateId;
	}
	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	public int getMainOwnerId() {
		return mainOwnerId;
	}
	public void setMainOwnerId(int mainOwnerId) {
		this.mainOwnerId = mainOwnerId;
	}
	public int getRightId() {
		return rightId;
	}
	public void setRightId(int rightId) {
		this.rightId = rightId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	public int getUseageId() {
		return useageId;
	}
	public void setUseageId(int useageId) {
		this.useageId = useageId;
	}
	public String getAreaType() {
		return areaType;
	}
	public void setAreaType(String areaType) {
		this.areaType = areaType;
	}
	public String getDirectionTitle() {
		return directionTitle;
	}
	public void setDirectionTitle(String directionTitle) {
		this.directionTitle = directionTitle;
	}
	public String getEstateTitle() {
		return estateTitle;
	}
	public void setEstateTitle(String estateTitle) {
		this.estateTitle = estateTitle;
	}
	public String getMainOwnerTitle() {
		return mainOwnerTitle;
	}
	public void setMainOwnerTitle(String mainOwnerTitle) {
		this.mainOwnerTitle = mainOwnerTitle;
	}
	public String getRightTitle() {
		return rightTitle;
	}
	public void setRightTitle(String rightTitle) {
		this.rightTitle = rightTitle;
	}
	public String getTypeTitle() {
		return typeTitle;
	}
	public void setTypeTitle(String typeTitle) {
		this.typeTitle = typeTitle;
	}
	public String getAreaTitle() {
		return areaTitle;
	}
	public void setAreaTitle(String areaTitle) {
		this.areaTitle = areaTitle;
	}
	public String getStatusTitle() {
		return statusTitle;
	}
	public void setStatusTitle(String statusTitle) {
		this.statusTitle = statusTitle;
	}
	public int getUseageTitle() {
		return useageTitle;
	}
	public void setUseageTitle(int useageTitle) {
		this.useageTitle = useageTitle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getOwnerTel() {
		return ownerTel;
	}
	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}
	public String getOwnerMobile() {
		return ownerMobile;
	}
	public void setOwnerMobile(String ownerMobile) {
		this.ownerMobile = ownerMobile;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
