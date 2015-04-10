package com.manyi.hims.estate.model;

import lombok.Data;

@Data
public class EstateReq {

	private int page=1;//当前第几页
	private int rows=20;//每一页多少
	private String orderby =" ";//排序规则
	private String estateName;//小区名字
	private int estateId;//小区ID
	private int areaId;//区域ID
	private int parentId;//父区域ID
	private int sourceState;//审核状态
	
	/**
	 * cityType城市类型
	 */
	private int cityType;

}
