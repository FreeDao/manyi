package com.manyi.hims.actionexcel.model;

import java.util.Date;

public class ExportSourceInfo {
	private String start;
	private String end;
	private int state;
	private int type;
	private int cityType;
	private int areaId;
	private Date t1;
	private Date t2;

	public Date getT1() {
		return t1;
	}

	public void setT1(Date t1) {
		this.t1 = t1;
	}

	public Date getT2() {
		return t2;
	}

	public void setT2(Date t2) {
		this.t2 = t2;
	}

	public int getCityType() {
		return cityType;
	}

	public void setCityType(int cityType) {
		this.cityType = cityType;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}