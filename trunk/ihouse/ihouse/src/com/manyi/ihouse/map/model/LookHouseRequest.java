package com.manyi.ihouse.map.model;

public class LookHouseRequest {
	//用户号码
	private Long userId;
	//预约状态
	private int state;
	//保存毫秒数
	private Long createDate;
	private Long updateDate;//
	private Long houseId;
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Long getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
	public Long getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}
	public Long getHouseId() {
		return houseId;
	}
	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUseId(Long userId) {
		this.userId = userId;
	}
	
	
}
