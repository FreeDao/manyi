package com.manyi.ihouse.map.model;

public class MapMarkModel {
	
	private int areaId; //区域ID
	
	private String areaName; //区域名称
	
	private int grade; //房源颗粒 1-区域; 2-板块; 3-小区
	
	private int houseNum; //房源数量

//	private String markPoint; //标注点坐标，格式（json）：(121.254548,31.265895)
	
	//纬度
	private double lat;
	//经度
	private double lon;
	
	private String geoHash;

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

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}
	
	
}
