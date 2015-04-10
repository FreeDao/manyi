package com.ecpss.mp.entity;

import com.ecpss.mp.entity.House;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 厂房
 * Entity implementation class for Entity: Plant
 * 
 */
@Entity
public class Plant extends House implements Serializable {

	private static final long serialVersionUID = 1L;

	public Plant() {
		super();
	}

}
