package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * 房屋状态（例如：出租，出售，自住）
 * Entity implementation class for Entity: HouseStatus
 * 
 */
@Entity
public class HouseStatus implements Serializable {

	private int hsid;
	private String type;
	private static final long serialVersionUID = 1L;

	public HouseStatus() {
		super();
	}

	@Id
	public int getHsid() {
		return this.hsid;
	}

	public void setHsid(int hsid) {
		this.hsid = hsid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
