package com.manyi.fyb.callcenter.lookhouse.model;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
@EqualsAndHashCode(callSuper=true)
public class LookHouseRes extends Response{
	
	private int id;//task id
	private int houseId;//houseId
	
	private int cityId;
	private String cityName;
	
	private int areaId;//区域
	private String areaName;
	private int townId;//片区
	private String townName;
	private int estateId;//小区
	private String estateName;
	private String builing;//栋座
	private String room;//室号
	private Date publishDate;//发布 时间
	private String publishDateStr;
	private Date addTaskDate;//看房任务 生成  时间
	private String addTaskDateStr;
	private Date taskDate;//看房时间
	private String taskDateStr;
	
	private int houseState;//house状态
	private String houseStateStr;
	/**
	 * task 任务 状态
	 * 0,未开启看房任务 1任务开启(电话预约中) 2预约失败 3预约成功（看房任务开启）  4看房失败  5看房成功
	 */
	
	private int taskState;
	
	private String taskStateStr;
	/**
	 * 是否存在图片, 数量
	 */
	private int picNum;
	
	private int employeeId;
	private String employeeName;
	private int userId;
	private String userName;
	
}
