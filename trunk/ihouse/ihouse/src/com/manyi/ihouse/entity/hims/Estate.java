package com.manyi.ihouse.entity.hims;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 楼盘或小区 Entity implementation class for Entity: Estate
 * 
 */
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(indexes = { @Index(name = "managerId", columnList = "managerId") })
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
	 *  小区边界范围图 放在 阿里云存储的key
	 */
	private String estateRangeImgKey;
	
	/**
	 * 小区别名 小区有多个 需要定一个分隔符
	 */
	private String aliasName;
	
	/**
	 * 主门地址 (冗余字段,地址表里新增了地址类型)
	 */
	private String mainGateAddress;
	
	/**
	 * 主门坐标经度
	 */
	private String mainGateLongitude;
	
	/**
	 * 主门坐标纬度
	 */
	private String mainGateLatitude;
	
	/**
	 * 主门坐标哈希
	 */
	private String mainGateAxisHash;
	
	/**
	 * 主门街景link
	 */
	private String mainGateStreetSceneryLink;
	
	/**
	 * 建造年代 (1000-2500)
	 */
	private int constructDate;
	
	/**
	 * 小区类型 1住宅 2别墅 3住宅别墅混合
	 */
	private int type;
	
	
}
