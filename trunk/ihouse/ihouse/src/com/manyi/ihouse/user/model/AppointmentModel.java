package com.manyi.ihouse.user.model;

import java.util.List;

import com.manyi.ihouse.house.model.HouseResponse;

/**
 * 约会模型
 * @author hubin
 *
 */
public class AppointmentModel {
	
	private long appointmentId; //约会ID
	
	private String date; //约看日期 eg.2014年10月25日
	
	private String time; //约看时间 eg.10:30
	
	private String weekday; //星期
	
	private int state; //状态  
	
	private String meetAddress; //碰头地点
	
	private String memo;//备注
	
	private int houseNum;//约看房源数量
	
	private List<HouseBaseModel> houses; //房源列表

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeekday() {
		return weekday;
	}

	public void setWeekday(String weekday) {
		this.weekday = weekday;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
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

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public List<HouseBaseModel> getHouses() {
		return houses;
	}

	public void setHouses(List<HouseBaseModel> houses) {
		this.houses = houses;
	}

}
