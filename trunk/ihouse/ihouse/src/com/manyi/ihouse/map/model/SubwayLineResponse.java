package com.manyi.ihouse.map.model;

public class SubwayLineResponse{
	
	private int subwayLineId;//主键ID
	
	private Integer lineCityCode; //城市编号,区号（不含0）

	private String lineDesc; //线路描述
	
	private String lineName; //线路名称
	
	private int lineStatus; //线路状态1，2，，，
	
	private double lineLat; //线路纬度
	
	private double lineLon; //线路经度
	
	private String lineGeoHash; //经纬度hash值
	
	private int level;

	public int getSubwayLineId() {
		return subwayLineId;
	}

	public void setSubwayLineId(int subwayLineId) {
		this.subwayLineId = subwayLineId;
	}

	public Integer getLineCityCode() {
		return lineCityCode;
	}

	public void setLineCityCode(Integer lineCityCode) {
		this.lineCityCode = lineCityCode;
	}

	public String getLineDesc() {
		return lineDesc;
	}

	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getLineStatus() {
		return lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public double getLineLat() {
		return lineLat;
	}

	public void setLineLat(double lineLat) {
		this.lineLat = lineLat;
	}

	public double getLineLon() {
		return lineLon;
	}

	public void setLineLon(double lineLon) {
		this.lineLon = lineLon;
	}

	public String getLineGeoHash() {
		return lineGeoHash;
	}

	public void setLineGeoHash(String lineGeoHash) {
		this.lineGeoHash = lineGeoHash;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
