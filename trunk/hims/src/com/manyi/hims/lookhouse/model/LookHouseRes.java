package com.manyi.hims.lookhouse.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

import com.manyi.hims.Response;

public class LookHouseRes extends Response{
	
	@Getter @Setter
	private int id;//task id
	private int houseId;//houseId
	
	@Getter @Setter private int cityId;
	@Getter @Setter private String cityName;
	
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String builing;//楼栋号
	private String room;//室号
	private Date publishDate;//发布 时间
	private String publishDateStr;
	private Date addTaskDate;//看房任务 生成  时间
	private String addTaskDateStr;
	
	private Date taskDate;//看房时间
	private String taskDateStr;
	
	@Getter @Setter
	private int houseState;//house状态
	@Getter @Setter
	private String houseStateStr;
	/**
	 * task 任务 状态
	 * 0,未开启看房任务 1任务开启(电话预约中) 2预约失败 3预约成功（看房任务开启）  4看房失败  5看房成功
	 */
	@Getter @Setter
	private int taskState;
	@Getter @Setter
	private String taskStateStr;
	/**
	 * 是否存在图片, 数量
	 */
	@Getter @Setter
	private int picNum;
	@Getter @Setter
	private int employeeId;
	@Getter @Setter
	private String employeeName;
	@Getter @Setter
	private int userId;
	@Getter @Setter
	private String userName;
	
	
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
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
		if(publishDate != null){
			this.publishDateStr= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(publishDate);
		}else{
			this.publishDateStr="-";
		}
	}
	public String getPublishDateStr() {
		return publishDateStr;
	}
	public void setPublishDateStr(String publishDateStr) {
		this.publishDateStr = publishDateStr;
	}
	public Date getAddTaskDate() {
		return addTaskDate;
	}
	public void setAddTaskDate(Date addTaskDate) {
		this.addTaskDate = addTaskDate;
		if(addTaskDate != null){
			this.addTaskDateStr= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(addTaskDate);
		}else{
			this.addTaskDateStr="-";
		}
	}
	public String getAddTaskDateStr() {
		return addTaskDateStr;
	}
	public void setAddTaskDateStr(String addTaskDateStr) {
		this.addTaskDateStr = addTaskDateStr;
	}
	public Date getTaskDate() {
		return taskDate;
	}
	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
		if(taskDate != null){
			this.taskDateStr= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(taskDate);
		}else{
			this.taskDateStr="-";
		}
	}
	public String getTaskDateStr() {
		return taskDateStr;
	}
	public void setTaskDateStr(String taskDateStr) {
		this.taskDateStr = taskDateStr;
	}
	
}
