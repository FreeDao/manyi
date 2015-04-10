package com.manyi.hims.pay.model;

public class PayReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private int payState;//出售状态
	private String exportTime;//导出数据的日期(年-月-日)
	private boolean ifExport =false;//是否到处是数据
	private String start;//起始 时间 
	private String end;//截止时间
	private String userAccount;//经纪人支付宝帐号
	private String userName;//经纪人姓名
	
	public String getUserAccount() {
		return userAccount;
	}
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public boolean isIfExport() {
		return ifExport;
	}
	public void setIfExport(boolean ifExport) {
		this.ifExport = ifExport;
	}
	public int getPayState() {
		return payState;
	}
	public void setPayState(int payState) {
		this.payState = payState;
	}
	public String getExportTime() {
		return exportTime;
	}
	public void setExportTime(String exportTime) {
		this.exportTime = exportTime;
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
	
}
