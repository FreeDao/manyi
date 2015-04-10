package com.manyi.fyb.callcenter.check.model;

public class DistributeRequest {
	private int id;
	private int managerId;//自动分配的分配管理者为0
	private int employeeId;
	private boolean isCanceled;//是否是取消分配
	private int lookStatus; //0客服，1地推
	private int[] houseIds;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public boolean isCanceled() {
		return isCanceled;
	}
	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}
	public int getLookStatus() {
		return lookStatus;
	}
	public void setLookStatus(int lookStatus) {
		this.lookStatus = lookStatus;
	}
	public int[] getHouseIds() {
		return houseIds;
	}
	public void setHouseIds(int[] sourceLogIds) {
		this.houseIds = sourceLogIds;
	}
}
