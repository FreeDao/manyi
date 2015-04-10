package com.manyi.hims.ihouse.model;

import java.util.List;

import com.manyi.hims.Response;

public class SubwayResponse extends Response{
	private int houseCount; //总房源数量
	private List<MapMarkModel> markList;
	//地铁线
//	public SubwayLine subwayLine;
	
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
	
	
	
	
}
