package com.manyi.hims.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 楼盘或小区 Entity implementation class for Entity: Estate
 * 
 */
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

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}
	
	private String roadAcronym;//road首字母所喜

	public String getRoadAcronym() {
		return roadAcronym;
	}
	
	public void setRoadAcronym(String roadAcronym) {
		this.roadAcronym = roadAcronym;
	}
	
	private String nameAcronym;//小区名称首字母缩写

	public String getNameAcronym() {
		return nameAcronym;
	}

	public void setNameAcronym(String nameAcronym) {
		this.nameAcronym = nameAcronym;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public BigDecimal getTotalAcreage() {
		return totalAcreage;
	}

	public void setTotalAcreage(BigDecimal totalAcreage) {
		this.totalAcreage = totalAcreage;
	}

	public BigDecimal getPlotRatio() {
		return plotRatio;
	}

	public void setPlotRatio(BigDecimal plotRatio) {
		this.plotRatio = plotRatio;
	}

	public BigDecimal getLandscapingRatio() {
		return landscapingRatio;
	}

	public void setLandscapingRatio(BigDecimal landscapingRatio) {
		this.landscapingRatio = landscapingRatio;
	}

	public int getTotalBuilding() {
		return totalBuilding;
	}

	public void setTotalBuilding(int totalBuilding) {
		this.totalBuilding = totalBuilding;
	}

	public int getTotalTruckSpace() {
		return totalTruckSpace;
	}

	public void setTotalTruckSpace(int totalTruckSpace) {
		this.totalTruckSpace = totalTruckSpace;
	}

	@Column(length = 30)
	public String getPropertyDevelopers() {
		return propertyDevelopers;
	}

	public void setPropertyDevelopers(String propertyDevelopers) {
		this.propertyDevelopers = propertyDevelopers;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public BigDecimal getStrataFee() {
		return strataFee;
	}

	public void setStrataFee(BigDecimal strataFee) {
		this.strataFee = strataFee;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
