package com.ecpss.mp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 打电话后调查结果表
 * Entity implementation class for Entity: CallResult
 * 
 */
@Entity
public class CallResult implements Serializable {

	private int crid;
	private House house;
	private ResultStatus status;
	private static final long serialVersionUID = 1L;

	public CallResult() {
		super();
	}

	@Id
	public int getCrid() {
		return crid;
	}

	public void setCrid(int crid) {
		this.crid = crid;
	}

	@OneToOne
	@PrimaryKeyJoinColumn
	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	@ManyToOne
	public ResultStatus getStatus() {
		return status;
	}

	public void setStatus(ResultStatus status) {
		this.status = status;
	}

}
