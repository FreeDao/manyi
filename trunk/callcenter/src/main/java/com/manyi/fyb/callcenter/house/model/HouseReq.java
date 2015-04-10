package com.manyi.fyb.callcenter.house.model;

import lombok.Data;

@Data
public class HouseReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private int houseId;//房屋ID
	private String estateName;//小区名字
	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int sellState;//出租状态
	private int rentState;//出售状态
	private String operServiceState;//审核状态
	/**
	 * 是否有照片
	 * 0, 全部; 1,无照片; 2,有照片
	 */
	private int picNum;
	
	/**
	 * cityType城市类型
	 */
	private int cityType;

}
