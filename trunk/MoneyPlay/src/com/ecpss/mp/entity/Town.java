package com.ecpss.mp.entity;

import com.ecpss.mp.entity.Area;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 镇,一般其父区域为County
 * Entity implementation class for Entity: Town
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Town extends Area implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public Town() {
		super();
	}
   
}
