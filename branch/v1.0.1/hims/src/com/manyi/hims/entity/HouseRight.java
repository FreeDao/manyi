package com.manyi.hims.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 产权
 * Entity implementation class for Entity: HouseRight
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseRight implements Serializable {

	private int houseRightId;
	private String houseRight;//房屋产权类别，（例如：商品房、房改房、集体房）
	private String introduce;//介绍
	private static final long serialVersionUID = 1L;


	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	public int getHouseRightId() {
		return this.houseRightId;
	}

	public void setHouseRightId(int houseRightId) {
		this.houseRightId = houseRightId;
	}

	public String getHouseRight() {
		return this.houseRight;
	}

	public void setHouseRight(String houseRight) {
		this.houseRight = houseRight;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
