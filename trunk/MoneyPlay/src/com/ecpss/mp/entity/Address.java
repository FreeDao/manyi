package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Address
 * 
 */
@Entity
public class Address implements Serializable {

	private long aid;
	private String title;
	private Estate estate;
	private static final long serialVersionUID = 1L;

	public Address() {
		super();
	}

	@Id
	@GeneratedValue
	public long getAid() {
		return this.aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@ManyToOne
	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

}
