package com.manyi.hims.building.model;

import lombok.Data;

@Data
public class BuildingReq {
	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	
	private String id;
	/**
	 * 楼栋名称
	 */
	private String name;
	/**
	 * 子划分id
	 */
	private String subEstateId;
	private String subEstateName;
	/**
	 * 小区id 
	 */
	private String estateId;
	private String estateName;
	/**
	 * 区域ID
	 */
	private int areaId;
	/**
	 * 父区域ID(行政区id)
	 */
	private int parentId;
	
	/**
	 * cityType城市类型
	 */
	private int cityType;
}
