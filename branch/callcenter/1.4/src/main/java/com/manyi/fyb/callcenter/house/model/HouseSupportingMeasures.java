package com.manyi.fyb.callcenter.house.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class HouseSupportingMeasures implements Serializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int houseId;

	//基础配置  //是否含有  true 1 是； false 0 否
	private boolean hasTV; //  电视 
	private boolean hasRefrigerator;//  冰箱 
	private boolean hasWashingMachine;//  洗衣机 
	private boolean hasAirConditioner;//  空调 
	private boolean hasWaterHeater;//  热水器 
	private boolean hasBed;//  床 
	private boolean hasSofa;//  沙发 
	private boolean hasBathtub;//  浴缸/淋浴 
	
	//高端配置 //是否含有  true 1 是； false 0 否
	private boolean hasCentralHeating;//  暖气/地暖 
	private boolean hasCentralAirConditioning;//  中央空调 
	private boolean hasCloakroom;//  衣帽间 
	private boolean hasReservedParking;//  车位 
	private boolean hasCourtyard;//  院落 
	private boolean hasGazebo;//  露台 
	private boolean hasPenthouse;//  阁楼
	private String supportingMeasuresStr; //配置信息统计字段
	
	private int bdTaskId; //bd人员任务id， 默认0
	private int userTaskId; // 中介(经纪人)看房任务id， 默认0
	private int enable;// 是否可用标识，1可用  0 不可用 ，任务可用，配套信息就可用
	
}
