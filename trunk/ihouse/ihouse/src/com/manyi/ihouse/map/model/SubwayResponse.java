package com.manyi.ihouse.map.model;

import java.util.List;

import com.manyi.ihouse.base.Response;

public class SubwayResponse extends Response{
	private int houseCount; //总房源数量
	private List<MapMarkModel> markList;
	//子线
	private List<MapMarkModel> childMarkList;	
	//地铁中心点    格式:(37921739,192739271)
	private double centerLon;
	
	private double centerLat;
	//地铁线
	//public SubwayLine subwayLine;
	
	public int getHouseCount() {
		return houseCount;
	}
	public void setHouseCount(int houseCount) {
		this.houseCount = houseCount;
	}
	public List<MapMarkModel> getMarkList() {
		return markList;
	}
	public void setMarkList(List<MapMarkModel> markList) {
		this.markList = markList;
	}
	public List<MapMarkModel> getChildMarkList() {
		return childMarkList;
	}
	public void setChildMarkList(List<MapMarkModel> childMarkList) {
		this.childMarkList = childMarkList;
	}
	public double getCenterLon() {
		return centerLon;
	}
	public void setCenterLon(double centerLon) {
		this.centerLon = centerLon;
	}
	public double getCenterLat() {
		return centerLat;
	}
	public void setCenterLat(double centerLat) {
		this.centerLat = centerLat;
	}
	
	
	
	
}
