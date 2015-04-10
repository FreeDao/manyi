package com.manyi.fyb.callcenter.house.model;

public class LoadAreaByParentIdReq {
	private int parentId;//父区域ID
	private boolean flag;//是否加载包含 全部的list
	
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
