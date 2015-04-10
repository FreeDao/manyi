package com.manyi.ihouse.entity.hims;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 省 Entity implementation class for Entity: Province
 * 
 */
@Entity
@Table(indexes = { @Index(name = "provinceTypeId", columnList = "provinceTypeId") })
public class Province extends Area implements Serializable {
	private static final long serialVersionUID = 1L;

	private String abbreviation;// 缩写

	/*********************************** FK ***********************************/
	
	private int provinceTypeId;
	/*********************************** FK ***********************************/

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public int getProvinceTypeId() {
		return provinceTypeId;
	}
	
	public void setProvinceTypeId(int provinceTypeId) {
		this.provinceTypeId = provinceTypeId;
	}
}
