package com.manyi.fyb.callcenter.house.model;

import java.util.Date;

import lombok.Data;

@Data
public class HouseRes {

	private int houseId;//记录id
	
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
	private Date sellPublishDate;//发布 出售时间
	private String sellPublishDateStr;
	private Date rentPublishDate;//发布 出租时间
	private String rentPublishDateStr;
	private int sellState;//出售状态
	private String sellStateStr;
	private int rentState;//出租状态
	private String rentStateStr;
	private String checkType;//审核 类型(1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房)
	private String checkTypeStr;
	private int operServiceState;//审核状态(1,审核成功;2,审核中;3,审核失败)
	private String operServiceStateStr;
	
	/**
	 * task 任务 状态
	 * 0,未开启看房任务 1任务开启(电话预约中) 2预约失败 3预约成功（看房任务开启）  4看房失败  5看房成功
	 */
	private int taskState;
	private String taskStateStr;
	/**
	 * User task 任务 状态
	 * 0,未领取 1已领取(未完成), 2审核中（提交图片完成）, 3审核成功,4审核失败,5过期
	 */
	
	private int userTaskState;
	private String userTaskStateStr;
	
	/**
	 * 照片情况
	 */
	private int picNum;
}
