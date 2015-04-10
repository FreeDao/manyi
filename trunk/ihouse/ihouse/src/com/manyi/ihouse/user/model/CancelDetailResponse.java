package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;
/**
 * 已取消 行程详情Response模型
 * @author hubin
 *
 */
public class CancelDetailResponse extends Response {
	
	private int appointmentId; //约会ID
	
	private String date; //约看日期 eg.2014年10月25日
	
	private String meetAddress; //碰头地点
	
	private String memo;//备注
	
	private String cancelReason;//原因

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMeetAddress() {
		return meetAddress;
	}

	public void setMeetAddress(String meetAddress) {
		this.meetAddress = meetAddress;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

}
