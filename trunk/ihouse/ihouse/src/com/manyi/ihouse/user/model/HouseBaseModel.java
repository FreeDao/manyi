package com.manyi.ihouse.user.model;

import java.util.Map;

/**
 * 房源信息模型
 * @author hubin
 *
 */
public class HouseBaseModel {

	private long houseId;//房源id
	
	private String rentPrice;//出租价格
	
	private int picNum; //照片数量
	
	private String[] picUrls; //照片URL数组

	private int bedroomSum;// 几房
	
	private int livingRoomSum;// 几厅
	
	private int wcSum;// 几卫

	private int floor;// 楼层
	
	private int layers;// 总层高 
	
	private String decorateType; //毛坯，精装修
	
	private String estateName;// 所属小区或楼盘名称 eg.玉兰香苑2期
	
	private String areaName; //区域名称 eg.花木
	
	private String townName;//片区名称 eg.世纪公园
	
	private String pubDate;//发布日期
	
	private String floorType;//楼层显示:高层，中层，低层
	
	private int collectionState = 0; //收藏状态 0:没收藏 1：已收藏
	
	private int enable = 0; //房源是否可用  0：不可用  1：可用
	
	//robin 新增
	/************************************/
	//小区id
	private int estateId;
	//高清图
	private Map<String,String> hdUrls;
	
	private double lon; //经度
	
	private double lat; //纬度
	
	private double distance;//距GPS定位距离
	
	/************************************/
	public int getCollectionState() {
        return collectionState;
    }

    public void setCollectionState(int collectionState) {
        this.collectionState = collectionState;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getFloorType() {
		return floorType;
	}

	public void setFloorType(String floorType) {
		this.floorType = floorType;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

	public String getRentPrice() {
		return rentPrice;
	}

	public void setRentPrice(String rentPrice) {
		this.rentPrice = rentPrice;
	}

	public int getPicNum() {
		return picNum;
	}

	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}

	public String[] getPicUrls() {
		return picUrls;
	}

	public void setPicUrls(String[] picUrls) {
		this.picUrls = picUrls;
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

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getLayers() {
		return layers;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public String getDecorateType() {
		return decorateType;
	}

	public void setDecorateType(String decorateType) {
		this.decorateType = decorateType;
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

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}


	public Map<String, String> getHdUrls() {
		return hdUrls;
	}

	public void setHdUrls(Map<String, String> hdUrls) {
		this.hdUrls = hdUrls;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	
	
	
}
