package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * 产权
 * Entity implementation class for Entity: HouseRight
 * 
 */
@Entity
public class HouseRight implements Serializable {

	private int hrid;
	private String type;//房屋产权类别，（例如：商品房、房改房、集体房）
	private String introduce;//介绍
	private static final long serialVersionUID = 1L;

	public HouseRight() {
		super();
	}

	@Id
	public int getHrid() {
		return this.hrid;
	}

	public void setHrid(int hrid) {
		this.hrid = hrid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
