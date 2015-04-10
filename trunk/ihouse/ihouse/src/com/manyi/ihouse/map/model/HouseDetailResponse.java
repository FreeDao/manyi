package com.manyi.ihouse.map.model;

import java.util.Map;

import com.manyi.ihouse.base.Response;

public class HouseDetailResponse extends Response {
	private Long houseId;
	private String rentPrice;
	// 2室3厅1卫
	private String houseRoom;
	//图片数目
	private int pictures;
	//缩略图片url列表 map<图片url,图片名称>
	private Map<String,String> urls;
	//高清图列表  map<图片url,图片名称>
	private Map<String,String> hdUrls;
	// 面积
	private String houseArea;
	//房屋类型 ，如：老公房
	private String houseType;
	// 楼层:0-6低层，7-12中层， >=13高层
	private String floorType;
	// 竣工时间
	private String finishDate;
	//朝向:东，西，南，北
	private String forward;
	// 装修类型
	private String decorateType;
	//格式:电视|冰箱|洗衣机|空调|热水器|床|沙发|浴缸|暖气|中央空调|衣帽间|车位|院落|露台|阁楼|
	private String houseConfig;// 房屋配置
	// 经度
	private double lon;
	// 纬度
	private double lat;
	// 小区地址
	private String zoneAddress;
	// 小区名称
	private String zoneName;
	// 住户数
	private String owners;
	// 停车位
	private String parkings;
	// 待出租数
	private String houseLet;
	//小区ID
	private int areaId;
	//可入住时间
	private String inHouseDate;
	//房屋距离
	private String distance;
	//发布时间
	private String publishDate;
	//区域板块
	private String block;
	//是否收藏
	private boolean favorite = false;//是否收藏
	
	private int existLookHouse = 0;//是否存在看房单

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public String getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}

	public String getHouseRoom() {
		return houseRoom;
	}

	public void setHouseRoom(String houseRoom) {
		this.houseRoom = houseRoom;
	}

	public int getPictures() {
		return pictures;
	}

	public void setPictures(int pictures) {
		this.pictures = pictures;
	}

	public Map<String, String> getUrls() {
		return urls;
	}

	public void setUrls(Map<String, String> urls) {
		this.urls = urls;
	}

	public Map<String, String> getHdUrls() {
		return hdUrls;
	}

	public void setHdUrls(Map<String, String> hdUrls) {
		this.hdUrls = hdUrls;
	}

	public String getHouseArea() {
		return houseArea;
	}

	public void setHouseArea(String houseArea) {
		this.houseArea = houseArea;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getFloorType() {
		return floorType;
	}

	public void setFloorType(String floorType) {
		this.floorType = floorType;
	}

	public String getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(String finishDate) {
		this.finishDate = finishDate;
	}

	public String getForward() {
		return forward;
	}

	public void setForward(String forward) {
		this.forward = forward;
	}

	public String getDecorateType() {
		return decorateType;
	}

	public void setDecorateType(String decorateType) {
		this.decorateType = decorateType;
	}

	public String getHouseConfig() {
		return houseConfig;
	}

	public void setHouseConfig(String houseConfig) {
		this.houseConfig = houseConfig;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public String getZoneAddress() {
		return zoneAddress;
	}

	public void setZoneAddress(String zoneAddress) {
		this.zoneAddress = zoneAddress;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getOwners() {
		return owners;
	}

	public void setOwners(String owners) {
		this.owners = owners;
	}

	public String getParkings() {
		return parkings;
	}

	public void setParkings(String parkings) {
		this.parkings = parkings;
	}

	public String getHouseLet() {
		return houseLet;
	}

	public void setHouseLet(String houseLet) {
		this.houseLet = houseLet;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getInHouseDate() {
		return inHouseDate;
	}

	public void setInHouseDate(String inHouseDate) {
		this.inHouseDate = inHouseDate;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	public int getExistLookHouse() {
		return existLookHouse;
	}

	public void setExistLookHouse(int existLookHouse) {
		this.existLookHouse = existLookHouse;
	}
}
