package com.manyi.hims.entity;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: HouseDirection
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseDirection implements Serializable {
	private static final long serialVersionUID = 1L;

	private int houseDirectionId;
	private String houseDirection;// 朝向描述
	private String remark;

	@Id
	public int getHouseDirectionId() {
		return houseDirectionId;
	}

	public void setHouseDirectionId(int houseDirectionId) {
		this.houseDirectionId = houseDirectionId;
	}

	public String getHouseDirection() {
		return houseDirection;
	}

	public void setHouseDirection(String houseDirection) {
		this.houseDirection = houseDirection;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
