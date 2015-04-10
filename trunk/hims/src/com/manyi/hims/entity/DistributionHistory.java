package com.manyi.hims.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: OperateDistrEmployee
 * 操作分配管理的日志
 */
@Entity

public class DistributionHistory implements Serializable {

	   
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	private int managerId;//自动分配的分配管理者为0
	private int employeeId;
	private boolean isCanceled;//是否是取消分配
	private int sourceLogId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;
	
	private int lookStatus;//0默认客服,1为地推
	
	private static final long serialVersionUID = 1L;

	public DistributionHistory() {
		super();
	}

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

	public int getSourceLogId() {
		return sourceLogId;
	}

	public void setSourceLogId(int sourceLogId) {
		this.sourceLogId = sourceLogId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getLookStatus() {
		return lookStatus;
	}

	public void setLookStatus(int lookStatus) {
		this.lookStatus = lookStatus;
	}
	

	
}
