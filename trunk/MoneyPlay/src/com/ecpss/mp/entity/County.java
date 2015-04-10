package com.ecpss.mp.entity;

import com.ecpss.mp.entity.Area;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 县或区
 * Entity implementation class for Entity: County
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class County extends Area implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public County() {
		super();
	}
   
}
