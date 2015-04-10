package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * 房屋类型（例如：多层，高层，独栋）
 * Entity implementation class for Entity: HouseType
 * 
 */
@Entity
public class HouseType implements Serializable {

	private int htid;
	private String title;//描述
	private String remark;
	private static final long serialVersionUID = 1L;

	public HouseType() {
		super();
	}

	@Id
	@GeneratedValue
	public int getHtid() {
		return this.htid;
	}

	public void setHtid(int htid) {
		this.htid = htid;
	}

	@Column(nullable = false, unique = true,length=12)
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
