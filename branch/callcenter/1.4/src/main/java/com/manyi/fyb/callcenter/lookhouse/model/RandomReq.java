package com.manyi.fyb.callcenter.lookhouse.model;

import lombok.Data;
@Data
public class RandomReq {

	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int houseState;//房子状态(1,出租;2,出售)
	
	/**
	 * cityType城市类型
	 */
	private int cityType;
	/**
	 * 管理员id
	 */
	private int manageId;
	
	/**
	 * 随机抽查 看房 数量
	 */
	private int checkNum;
	
	/**
	 * 出售价格
	 */
	private int sellPriceStart;
	private int sellPriceEnd;
	
	/**
	 * 出租价格
	 */
	private int rentPriceStart;
	private int rentPriceEnd;
	
	/**
	 * 面积 区间
	 */
	private int spaceAreaStart;
	private int spaceAreaEnd;
	
	/**
	 * 发布时间
	 */
	private String publishDateStart;
	private String publishDateEnd;
	
}
