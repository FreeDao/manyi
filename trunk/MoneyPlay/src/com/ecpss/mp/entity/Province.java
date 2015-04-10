package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * 省 Entity implementation class for Entity: Province
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Province extends Area implements Serializable {

	private String abbreviation;
	private ProvinceType type;
	private static final long serialVersionUID = 1L;

	public Province() {
		super();
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	@ManyToOne
	public ProvinceType getType() {
		return this.type;
	}

	public void setType(ProvinceType type) {
		this.type = type;
	}

}
