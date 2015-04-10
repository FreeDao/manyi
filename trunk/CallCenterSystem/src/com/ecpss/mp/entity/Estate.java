package com.ecpss.mp.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 楼盘或小区 Entity implementation class for Entity: Estate
 * 
 */
@Entity
public class Estate extends Area implements Serializable {

	private BigDecimal totalAcreage;// 小区占地面积
	private BigDecimal plotRatio;// 容积率
	private BigDecimal landscapingRatio;// 绿化率
	private int totalBuilding;// 楼数
	private int totalTruckSpace;// 车位数
	private String propertyDevelopers;// 开发商
	private PropertyManager manager;// 物业公司
	private BigDecimal strataFee;// 物业费用
	private String introduce;// 介绍

//	private List<Address> addresses;
	private static final long serialVersionUID = 1L;

	public Estate() {
		super();
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

	@ManyToOne
	public PropertyManager getManager() {
		return manager;
	}

	public void setManager(PropertyManager manager) {
		this.manager = manager;
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

//	@OneToMany(mappedBy = "estate")
//	public List<Address> getAddresses() {
//		return this.addresses;
//	}
//
//	public void setAddresses(List<Address> addresses) {
//		this.addresses = addresses;
//	}

}
