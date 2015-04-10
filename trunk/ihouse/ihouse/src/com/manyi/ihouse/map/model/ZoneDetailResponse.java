package com.manyi.ihouse.map.model;

import java.util.Map;

import com.manyi.ihouse.base.Response;

public class ZoneDetailResponse extends Response{
	//小区名称
	private String zoneName;
	//区域板块
	private String block;
	//小区地址
	private String zoneAddress;
	//距离
	private float distance;
	//建造年代
	private long buildingDate;
	//出租率
	private String rentRate;
	//开发商
	private String company;
	//物业类型
	private String wyType;
	//物业公司
	private String wyCompany;
	//绿化率
	private String greenRate;
	//物业费用
	private String fee;
	//停车位
	private String parks;
	//容积率
	private String rjRate;
	//经度*100000000
	private long lon;
	//纬度*100000000
	private long lat;
	//图片URL
	private Map<String,String> urls;
	//高清图片URL
	private Map<String,String> hdUrls;
	
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getZoneAddress() {
		return zoneAddress;
	}
	public void setZoneAddress(String zoneAddress) {
		this.zoneAddress = zoneAddress;
	}
	public float getDistance() {
		return distance;
	}
	public void setDistance(float distance) {
		this.distance = distance;
	}
	public long getBuildingDate() {
		return buildingDate;
	}
	public void setBuildingDate(long buildingDate) {
		this.buildingDate = buildingDate;
	}
	public String getRentRate() {
		return rentRate;
	}
	public void setRentRate(String rentRate) {
		this.rentRate = rentRate;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getWyType() {
		return wyType;
	}
	public void setWyType(String wyType) {
		this.wyType = wyType;
	}
	public String getWyCompany() {
		return wyCompany;
	}
	public void setWyCompany(String wyCompany) {
		this.wyCompany = wyCompany;
	}
	public String getGreenRate() {
		return greenRate;
	}
	public void setGreenRate(String greenRate) {
		this.greenRate = greenRate;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getParks() {
		return parks;
	}
	public void setParks(String parks) {
		this.parks = parks;
	}
	public String getRjRate() {
		return rjRate;
	}
	public void setRjRate(String rjRate) {
		this.rjRate = rjRate;
	}
	public long getLon() {
		return lon;
	}
	public void setLon(long lon) {
		this.lon = lon;
	}
	public long getLat() {
		return lat;
	}
	public void setLat(long lat) {
		this.lat = lat;
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
	
}
