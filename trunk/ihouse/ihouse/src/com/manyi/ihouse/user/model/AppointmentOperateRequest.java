package com.manyi.ihouse.user.model;

/**
 * 约会操作请求模型 用于“确认看房”、“我要改期”、“取消看房”
 * @author hubin
 *
 */
public class AppointmentOperateRequest {
	
	private long userId; //用户ID
	
	private long appointmentId; //约会ID
	
	private int reason; //取消看房原因：1-我已租到房子；2-我不租了；3-其他
	
	private String memo; //备注信息

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
