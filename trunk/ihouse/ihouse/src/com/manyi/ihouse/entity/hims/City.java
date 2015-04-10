package com.manyi.ihouse.entity.hims;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * 地级市,一般其父区域为Province
 * Entity implementation class for Entity: City
 * 
 */
@Entity
public class City extends Area implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;//

//	@Column(nullable = false, unique = true)
	
	
	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
