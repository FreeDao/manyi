package com.manyi.ihouse.user.model;

import java.util.List;

/**
 * 已看房源列表模型
 * @author hubin
 *
 */
public class HaveSeeModel {
	
	private long appointmentId; //约会ID
	
	private String date; //约看日期 eg.2014年10月25日
	
	private String time; //约看时间 eg.10:30
	
	private String weekday;//星期几
	
	private String seeHouseId;//看房单号
	
	private int state; //状态 1-已评价 2-未评价
	
	private int houseNum; //房源数量
	
	private List<HouseSummaryModel> houses; //房源列表

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

    public String getSeeHouseId() {
        return seeHouseId;
    }

    public void setSeeHouseId(String seeHouseId) {
        this.seeHouseId = seeHouseId;
    }

    public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getHouseNum() {
		return houseNum;
	}

	public void setHouseNum(int houseNum) {
		this.houseNum = houseNum;
	}

	public List<HouseSummaryModel> getHouses() {
		return houses;
	}

	public void setHouses(List<HouseSummaryModel> houses) {
		this.houses = houses;
	}

}
