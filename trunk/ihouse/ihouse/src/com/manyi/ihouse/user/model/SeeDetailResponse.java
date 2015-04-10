package com.manyi.ihouse.user.model;

import java.util.List;

import com.manyi.ihouse.base.Response;

/**
 * 行程详情Response模型
 * @author hubin
 *
 */
public class SeeDetailResponse extends Response{

	private long appointmentId; //约会ID
	
	private String assigneeName; //经纪人姓名
	
	private String assigneeTel; //经纪人电话
	
	private String date; //约看日期 eg.2014年10月25日
	
	private String time; //约看时间 eg.10:30
    
    private String seeHouseId;//看房单号

    private String protocolUrl;//现场看房协议URL
	
	private String meetAddress; //碰头地点
	
	private String memo;//备注
	
	private List<HouseSummaryModel> houseInfo; //房源列表
	
	public String getSeeHouseId() {
        return seeHouseId;
    }

    public void setSeeHouseId(String seeHouseId) {
        this.seeHouseId = seeHouseId;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }

    public void setProtocolUrl(String protocolUrl) {
        this.protocolUrl = protocolUrl;
    }

    public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssigneeTel() {
		return assigneeTel;
	}

	public void setAssigneeTel(String assigneeTel) {
		this.assigneeTel = assigneeTel;
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

	public List<HouseSummaryModel> getHouseInfo() {
		return houseInfo;
	}

	public void setHouseInfo(List<HouseSummaryModel> houseInfo) {
		this.houseInfo = houseInfo;
	}

}
