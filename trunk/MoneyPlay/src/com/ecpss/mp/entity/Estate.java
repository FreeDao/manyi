package com.ecpss.mp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 * 楼盘或小区
 * Entity implementation class for Entity: Estate
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Estate extends Area implements Serializable {

	private List<Address> addresses;
	private static final long serialVersionUID = 1L;

	public Estate() {
		super();
	}

	@OneToMany(mappedBy = "estate")
	public List<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
