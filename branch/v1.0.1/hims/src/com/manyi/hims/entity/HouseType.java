package com.manyi.hims.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 房屋类型（例如：多层，高层，独栋） Entity implementation class for Entity: HouseType
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseType implements Serializable {

	private int houseTypeId;
	private String houseType;// 描述
	private String remark;
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	public int getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(int houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	@Column(nullable = false, unique = true, length = 12)
	public String getHouseType() {
		return this.houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	@Column(length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
