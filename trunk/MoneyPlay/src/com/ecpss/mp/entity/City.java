package com.ecpss.mp.entity;

import com.ecpss.mp.entity.Area;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 地级市,一般其父区域为Province
 * Entity implementation class for Entity: City
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class City extends Area implements Serializable {
	private static final long serialVersionUID = 1L;

	private int code;//
	

	public City() {
		super();
	}

	@Column(nullable = false, unique = true)
	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
