package com.manyi.ihouse.house.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.util.EntityUtils;

public class HouseResponse extends Response{

	private int houseId;//记录id
	
	@Getter @Setter private int cityId;
	@Getter @Setter private String cityName;
	
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String builing;//栋座
	private String room;//室号
	private Date sellPublishDate;//发布 出售时间
	private String sellPublishDateStr;
	private Date rentPublishDate;//发布 出租时间
	private String rentPublishDateStr;
	private int sellState;
	private String sellStateStr;
	private int rentState;
	private String rentStateStr;
	private String checkType;//审核 类型(//审核 类型/1发布，2，改盘，3举报，4轮询，5抽查
	private String checkTypeStr;
	private int logId ;
	private Date logCreateTime;
	private String logCreateTimeStr;
	private int operServiceState;//审核状态(1,审核成功;2,审核中;3,审核失败)
	private String operServiceStateStr;
	/**
	 * task 任务 状态
	 * 0,未开启看房任务 1任务开启(电话预约中) 2预约失败 3预约成功（看房任务开启）  4看房失败  5看房成功
	 */
	@Getter @Setter
	private int taskState;
	@Getter @Setter
	private String taskStateStr;
	
	/**
	 * 照片情况
	 */
	@Getter @Setter
	private int picNum;
	
	public int getOperServiceState() {
		return operServiceState;
	}

	public String getOperServiceStateStr() {
		return operServiceStateStr;
	}

	public void setOperServiceState(int operServiceState) {
		this.operServiceState = operServiceState;
		
		/*
		 * 未维护的不受控代码
		 * 转换轮询审核状态
		 * TODO
		 * TOFIX
		 */
		if(EntityUtils.StatusEnum.SUCCESS.getValue() == operServiceState){
			this.operServiceStateStr = ( "4".equals(this.checkType) ? "审核失败" : "审核成功"); 
		}else if(EntityUtils.StatusEnum.FAILD.getValue() == operServiceState){
			this.operServiceStateStr = ( "4".equals(this.checkType) ? "审核成功" : "审核失败"); 
		}else if(EntityUtils.StatusEnum.ING.getValue() == operServiceState){
			this.operServiceStateStr="审核中";
		}
	}
	
	public Date getLogCreateTime() {
		return logCreateTime;
	}
	public void setLogCreateTime(Date logCreateTime) {
		this.logCreateTime = logCreateTime;
		if(logCreateTime != null){
			this.logCreateTimeStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logCreateTime);
		}
	}
	public String getLogCreateTimeStr() {
		return logCreateTimeStr;
	}
	public int getLogId() {
		return logId;
	}
	public void setLogId(int logId) {
		this.logId = logId;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCheckTypeStr() {
		return checkTypeStr;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public int getTownId() {
		return townId;
	}
	public void setTownId(int townId) {
		this.townId = townId;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	public int getEstateId() {
		return estateId;
	}
	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}
	public String getEstateName() {
		return estateName;
	}
	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}
	public String getBuiling() {
		return builing;
	}
	public void setBuiling(String builing) {
		this.builing = builing;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public Date getSellPublishDate() {
		return sellPublishDate;
	}
	public void setSellPublishDate(Date sellPublishDate) {
		this.sellPublishDate = sellPublishDate;
		if(sellPublishDate != null){
			this.sellPublishDateStr= new SimpleDateFormat("yyyy-MM-dd").format(sellPublishDate);
		}else{
			this.sellPublishDateStr="-";
		}
	}
	public Date getRentPublishDate() {
		return rentPublishDate;
	}
	public void setRentPublishDate(Date rentPublishDate) {
		this.rentPublishDate = rentPublishDate;
		if(rentPublishDate != null){
			this.rentPublishDateStr= new SimpleDateFormat("yyyy-MM-dd").format(rentPublishDate);
		}else{
			this.rentPublishDateStr="-";
		}
	}
	public int getSellState() {
		return sellState;
	}
	public void setSellState(int sellState) {
		this.sellState = sellState;
	}
	public int getRentState() {
		return rentState;
	}
	public void setRentState(int rentState) {
		this.rentState = rentState;
	}
	public String getSellPublishDateStr() {
		return sellPublishDateStr;
	}
	public String getRentPublishDateStr() {
		return rentPublishDateStr;
	}
	public String getSellStateStr() {
		return sellStateStr;
	}
	public String getRentStateStr() {
		return rentStateStr;
	}
	public void setSellStateStr(String sellStateStr) {
		this.sellStateStr = sellStateStr;
	}
	public void setCheckTypeStr(String checkTypeStr) {
		this.checkTypeStr = checkTypeStr;
	}
	
	
}

