package com.manyi.ihouse.user.model;

/**
 * 行程详情request模型
 * @author hubin
 *
 */
public class AppointmentDetailRequest {

	private long userId; //用户ID
	
	private long appointmentId; //约会ID
	
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

}
