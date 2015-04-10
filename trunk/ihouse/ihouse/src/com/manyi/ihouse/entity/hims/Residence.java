package com.manyi.ihouse.entity.hims;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * 住 宅 Entity implementation class for Entity: Residence
 * 
 */
@Entity
public class Residence extends House implements Serializable {
	private static final long serialVersionUID = 1L;

	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private int balconySum;// 几阳台

	public int getBedroomSum() {
		return this.bedroomSum;
	}

	public void setBedroomSum(int bedroomSum) {
		this.bedroomSum = bedroomSum;
	}

	public int getLivingRoomSum() {
		return this.livingRoomSum;
	}

	public void setLivingRoomSum(int livingRoomSum) {
		this.livingRoomSum = livingRoomSum;
	}

	public int getWcSum() {
		return this.wcSum;
	}

	public void setWcSum(int wcSum) {
		this.wcSum = wcSum;
	}

	public int getBalconySum() {
		return this.balconySum;
	}

	public void setBalconySum(int balconySum) {
		this.balconySum = balconySum;
	}

}
