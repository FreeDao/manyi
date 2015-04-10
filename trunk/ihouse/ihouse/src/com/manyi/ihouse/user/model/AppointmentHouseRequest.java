package com.manyi.ihouse.user.model;

/**
 * 约看请求Request模型
 * @author hubin
 *
 */
public class AppointmentHouseRequest {
	
	private long userId; //用户ID

	private String username; //用户称呼
	
	private int gender;//性别 1：男，2：女 3:保密

	private String appointmentTime; //约看时间
	
	private String houseIds; //约看房屋ID 多个用“,"半角逗号隔开

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(String appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getHouseIds() {
		return houseIds;
	}

	public void setHouseIds(String houseIds) {
		this.houseIds = houseIds;
	}
	
}
