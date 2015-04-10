package com.manyi.hims.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 省的类型（例如：直辖市、自治区、特别行政区，普通省） Entity implementation class for Entity: ProvinceType
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class ProvinceType implements Serializable {
	private static final long serialVersionUID = 1L;

	private int provinceTypeId;
	private String provinceType;// 抬头

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	public int getProvinceTypeId() {
		return this.provinceTypeId;
	}

	public void setProvinceTypeId(int provinceTypeId) {
		this.provinceTypeId = provinceTypeId;
	}

	@Column(nullable = false, unique = true, length = 20)
	public String getProvinceType() {
		return this.provinceType;
	}

	public void setProvinceType(String provinceType) {
		this.provinceType = provinceType;
	}

}
