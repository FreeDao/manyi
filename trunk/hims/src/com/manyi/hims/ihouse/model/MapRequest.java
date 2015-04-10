package com.manyi.hims.ihouse.model;

public class MapRequest {
	private int page = 1;//当前第几页
	private int rows = 20;//每页显示多少行
	private int level;
//	private LatLng point;
	//多个houseId用","隔开 （4545，,556,6565,5656,）
	private String houseId;

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

//	public LatLng getPoint() {
//		return point;
//	}
//
//	public void setPoint(LatLng point) {
//		this.point = point;
//	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
