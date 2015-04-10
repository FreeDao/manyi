package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * 县或区
 * Entity implementation class for Entity: County
 *
 */
@Entity
public class County extends Area implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public County() {
		super();
	}
   
}
