package com.manyi.ihouse.map.model;

import com.manyi.ihouse.base.Response;


public class SubwayLinePoint{
	
	private int subwayPointId;
	
	private String pointDesc;
	
	private int pointStatus;
	
	private double pointLat;
	
	private double pointLon;
	
	private String pointGeoHash;
	
	private int sequence;

	public int getSubwayPointId() {
		return subwayPointId;
	}

	public void setSubwayPointId(int subwayPointId) {
		this.subwayPointId = subwayPointId;
	}

	public String getPointDesc() {
		return pointDesc;
	}

	public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	}

	public int getPointStatus() {
		return pointStatus;
	}

	public void setPointStatus(int pointStatus) {
		this.pointStatus = pointStatus;
	}

	public double getPointLat() {
		return pointLat;
	}

	public void setPointLat(double pointLat) {
		this.pointLat = pointLat;
	}

	public double getPointLon() {
		return pointLon;
	}

	public void setPointLon(double pointLon) {
		this.pointLon = pointLon;
	}

	public String getPointGeoHash() {
		return pointGeoHash;
	}

	public void setPointGeoHash(String pointGeoHash) {
		this.pointGeoHash = pointGeoHash;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

}
