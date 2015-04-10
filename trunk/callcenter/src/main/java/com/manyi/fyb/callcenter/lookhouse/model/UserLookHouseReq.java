package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;
@Data
public class UserLookHouseReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private String estateName;//小区名字
	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int houseState;//出租/出售状态
	private String taskState;//审核状态
	
	/**
	 * cityType城市类型
	 */
	private int cityType;
	/**
	 * 审核人
	 */
	private String employeeName;
	/**
	 * 发布经纪人
	 */
	private String userName;
	private String userMobile;
	
	/**
	 * 任务领取时间
	 */
	private String createTimeStart;
	private String createTimeEnd;
	/**
	 * 任务审核时间
	 */
	private String finishDateStart;
	private String finishDateEnd;
	
}
