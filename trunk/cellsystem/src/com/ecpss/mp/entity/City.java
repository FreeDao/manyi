package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

import org.hibernate.annotations.GenericGenerator;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity(name="city")
public class City implements Serializable {

	@Id
	@GenericGenerator(name="ud", strategy = "assigend")
	@Column(name="CityName")
	private String cityName;
	
	@Column(name="CityNo" ,unique = true, length = 30)
	private String cityNo;
	private static final long serialVersionUID = 1L;

	public City() {
		super();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityNo() {
		return cityNo;
	}

	public void setCityNo(String cityNo) {
		this.cityNo = cityNo;
	}

	
}
