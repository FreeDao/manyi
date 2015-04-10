package com.manyi.ihouse.map.model;

/**
 * 经纬度坐标
 * @author shenyamin
 *
 */
public class LatLng {
	
	private long longitude;//经度
	private long latitude;//纬度
	
	public long getLongitude() {
		return longitude;
	}
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
	public long getLatitude() {
		return latitude;
	}
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
}
