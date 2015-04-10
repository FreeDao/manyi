package com.manyi.ihouse.map.model;

public class MapRequest {
	//已看多少条
	private int offset = 0;
	//每页显示多少行
	private int pageSize = 20;
	//地图层级
	private int level;
	//经纬度
	private double lat;
	private double lon;
	//经纬度GeoHash
	private String geoHash;//"jfasdujfljasl"
	//城市：上海
	//如果在地铁模块检索数据,参数为城市在地铁表里的代码 上海：21 ；北京10码
	private String city;
	//区域:浦东
	private String area;
	//区域ID
	private int areaId;
	//地铁线No
	private int subwayNo;
	
	//列表排序  1:时间 2:价格低-高3：价格高-低 4：距离
	private int sequence = 1;
	
	private long userId;

	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
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

	public String getGeoHash() {
		return geoHash;
	}

	public void setGeoHash(String geoHash) {
		this.geoHash = geoHash;
	}

	public int getSubwayNo() {
		return subwayNo;
	}

	public void setSubwayNo(int subwayNo) {
		this.subwayNo = subwayNo;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getSequence() {
		return sequence;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



}
