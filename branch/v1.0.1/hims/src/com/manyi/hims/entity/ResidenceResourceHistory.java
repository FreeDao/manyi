package com.manyi.hims.entity;

import com.manyi.hims.entity.HouseResourceHistory;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: ResidenceResourceHistory
 *
 */
@Entity

public class ResidenceResourceHistory extends HouseResourceHistory implements Serializable {

	
	private static final long serialVersionUID = 1L;

	//If Residence
	@Column(columnDefinition="int default 0",nullable=false)
	private int bedroomSum;// 几房
	@Column(columnDefinition="int default 0",nullable=false)
	private int livingRoomSum;// 几厅
	@Column(columnDefinition="int default 0",nullable=false)
	private int wcSum;// 几卫
	@Column(columnDefinition="int default 0",nullable=false)
	private int balconySum;// 几阳台
	
	public ResidenceResourceHistory() {
		super();
	}

	public int getBedroomSum() {
		return bedroomSum;
	}

	public void setBedroomSum(int bedroomSum) {
		this.bedroomSum = bedroomSum;
	}

	public int getLivingRoomSum() {
		return livingRoomSum;
	}

	public void setLivingRoomSum(int livingRoomSum) {
		this.livingRoomSum = livingRoomSum;
	}

	public int getWcSum() {
		return wcSum;
	}

	public void setWcSum(int wcSum) {
		this.wcSum = wcSum;
	}

	public int getBalconySum() {
		return balconySum;
	}

	public void setBalconySum(int balconySum) {
		this.balconySum = balconySum;
	}


}
