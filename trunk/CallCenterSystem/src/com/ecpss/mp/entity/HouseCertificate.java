package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: HouseCertificate
 * 
 */
@Entity
public class HouseCertificate implements Serializable {

	private int hcid;
	private String type;// 证件（例如：房地产证，购房合同）
	private static final long serialVersionUID = 1L;

	public HouseCertificate() {
		super();
	}

	@Id
	public int getHcid() {
		return this.hcid;
	}

	public void setHcid(int hcid) {
		this.hcid = hcid;
	}

	@Column(length = 16)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
