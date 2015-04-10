package com.ecpss.mp.entity;

import com.ecpss.mp.entity.House;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TruckSpace
 * 
 */
@Entity
public class TruckSpace extends House implements Serializable {

	private static final long serialVersionUID = 1L;

	public TruckSpace() {
		super();
	}

}
