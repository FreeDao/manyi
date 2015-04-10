package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

/**
 * Entity implementation class for Entity: HouseDirection
 *
 */
@Entity

public class HouseDirection implements Serializable {

	
	private int hdid;
	private String title;//朝向描述
	private String remark;
	private static final long serialVersionUID = 1L;

	public HouseDirection() {
		super();
	}   
	@Id    
	@GeneratedValue
	public int getHdid() {
		return this.hdid;
	}

	public void setHdid(int hdid) {
		this.hdid = hdid;
	}   
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}   
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
   
}
