package com.ecpss.mp.entity;

import com.ecpss.mp.entity.House;
import java.io.Serializable;
import javax.persistence.*;

/**
 * 商铺
 * Entity implementation class for Entity: BusinessHouse
 * 
 */
@Entity
public class Store extends House implements Serializable {

	private static final long serialVersionUID = 1L;

	public Store() {
		super();
	}

}
