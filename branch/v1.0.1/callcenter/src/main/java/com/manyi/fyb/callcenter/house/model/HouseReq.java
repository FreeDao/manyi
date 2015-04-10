package com.manyi.fyb.callcenter.house.model;

public class HouseReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private String estateName;//小区名字
	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int sellState;//出租状态
	private int rentState;//出售状态
	private String operServiceState;//审核状态
	
	public String getOperServiceState() {
		return operServiceState;
	}
	public void setOperServiceState(String operServiceState) {
		this.operServiceState = operServiceState;
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
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public int getSellState() {
		return sellState;
	}
	public void setSellState(int sellState) {
		this.sellState = sellState;
	}
	public int getRentState() {
		return rentState;
	}
	public void setRentState(int rentState) {
		this.rentState = rentState;
	}
	

}
