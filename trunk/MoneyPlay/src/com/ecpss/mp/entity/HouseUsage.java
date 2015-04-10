package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * 房屋用途
 * Entity implementation class for Entity: HouseUsage
 * 
 */
@Entity
public class HouseUsage implements Serializable {

	private int huid;
	private String title;//用途说明
	private String remark;//备注
	private static final long serialVersionUID = 1L;

	public HouseUsage() {
		super();
	}

	@Id
	@GeneratedValue
	public int getHuid() {
		return this.huid;
	}

	public void setHuid(int huid) {
		this.huid = huid;
	}

	@Column(nullable = false, unique = true, length = 12)
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
