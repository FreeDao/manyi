package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * cell 客服 打电话后的 
 * 反馈结果
 * 
 * Entity implementation class for Entity: ResultStatus
 * 
 */
@Entity
public class ResultStatus implements Serializable {

	private int rsid;
	private String type;
	private static final long serialVersionUID = 1L;

	public ResultStatus() {
		super();
	}

	@Id
	public int getRsid() {
		return this.rsid;
	}

	public void setRsid(int rsid) {
		this.rsid = rsid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
