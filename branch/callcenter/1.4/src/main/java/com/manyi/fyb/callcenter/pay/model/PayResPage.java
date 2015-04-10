package com.manyi.fyb.callcenter.pay.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PayResPage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6001504958917131518L;
	private int errorCode;
	private int currentPage;
	private int pageSize;
	private int total;
	private int totalPage;
	private List<PayRes> rows =new ArrayList<PayRes>();
	
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<PayRes> getRows() {
		return rows;
	}
	public void setRows(List<PayRes> rows) {
		this.rows = rows;
	}
}
