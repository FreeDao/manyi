package com.manyi.ihouse.map.model;

public class HouseRequest {
	private Long houseId;
	private String lat;
	private String lon;
	private int esateId;
	private long userId;
	
	public long getHouseId() {
		return houseId;
	}
	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public int getEsateId() {
		return esateId;
	}
	public void setEsateId(int esateId) {
		this.esateId = esateId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
