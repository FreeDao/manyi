package com.manyi.hims.ihouse.model;

public class MapMarkModel {
	
	private int areaId; //区域ID
	
	private String areaName; //区域名称
	
	private int grade; //房源颗粒 1-区域; 2-板块; 3-小区
	
	private int houseNum; //房源数量

	private LatLng markPoint; //标注点坐标，格式（json）：[0,0]

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

	public LatLng getMarkPoint() {
		return markPoint;
	}

	public void setMarkPoint(LatLng markPoint) {
		this.markPoint = markPoint;
	}
	
	
}
