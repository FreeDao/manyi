package com.manyi.fyb.callcenter.building.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.manyi.fyb.callcenter.base.model.Response;

@Data
@EqualsAndHashCode(callSuper=false)
public class BuildingRes extends Response{
	private int id;
	/**
	 * 楼栋名称
	 */
	private String name;
	
	/**
	 * 楼栋别名 (多个,需要定义分隔符)
	 */
	private String aliasName;
	
	/**
	 * 总层高
	 */
	private int totalLayers;
	
	/**
	 * 竣工年代 范围1000-2500
	 */
	private int finishDate;
	
	/**
	 * 子划分id
	 */
	private int subEstateId;
	private String subEstateName;
	
	/**
	 * 小区id 
	 */
	private int estateId;
	private String serialCode;
	private String estateName;
		
	/**
	 * 楼栋状态 (为小区,子划分拆分预留 暂时用不到)
	 */
	private int status;
	private String statusStr;
	
	/**
	 * 坐标 经度
	 */
	private String longitude;
	/**
	 * 坐标 纬度
	 */
	private String latitude;
	
	/**
	 * 坐标哈希
	 */
	private String axisHash;
		
	/**
	 * 添加时间
	 */
	private Date addTime;
	private String addTimeStr;
	
	private int sellNum;// 在售数量
	private int rentNum;// 在租数量
	private int houseNum;// 房源数量
	private int areaId;// 行政区域
	private String areaName;
	private int townId;// 片区
	private String townName;
	private int cityId;// 片区
	private String cityName;
	
	private List<BuildingImage> images = new ArrayList<BuildingImage>();
	/**
	 * 上传的阿里云的keys json
	 */
	private String imgkeys;
}
