package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;
@Data
public class LookHouseReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private String estateName;//小区名字
	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int houseState;//出租状态
	private String taskState;//审核状态
	
	/**
	 * cityType城市类型
	 */
	private int cityType;
	
	/**
	 * 是否有照片
	 * 0, 全部; 1,无照片; 2,有照片
	 */
	private int picNum;
	/**
	 * 看房负责人
	 */
	private String employeeName;
	/**
	 * 发布经纪人
	 */
	private String userName;
	
	private String houseIds;
	/**
	 * 管理员id
	 */
	private int manageId;
	
	/**
	 * 看房时间
	 */
	private String taskDateStart;
	private String taskDateEnd;
	
}
