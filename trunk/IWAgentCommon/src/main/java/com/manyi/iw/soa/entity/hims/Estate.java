package com.manyi.iw.soa.entity.hims;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼盘或小区 Entity implementation class for Entity: Estate
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Estate extends Area implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal totalAcreage;// 小区占地面积
	private BigDecimal plotRatio;// 容积率
	private BigDecimal landscapingRatio;// 绿化率
	private int totalBuilding;// 楼数
	private int totalTruckSpace;// 车位数
	private String propertyDevelopers;// 开发商
	
	private BigDecimal strataFee;// 物业费用
	private String introduce;// 介绍
	
	
	/*********************************** FK ***********************************/
	private int managerId;// PropertyManager's Id
	/*********************************** FK ***********************************/
	private Date finishDate;//小区竣工时间
	private String road;//小区 多少路,多少弄
	private String nameAcronym;//小区名称首字母缩写
	
	
	
}
