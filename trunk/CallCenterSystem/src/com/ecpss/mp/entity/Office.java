package com.ecpss.mp.entity;

import com.ecpss.mp.entity.House;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Office
 * 
 */
@Entity
public class Office extends House implements Serializable {

	private static final long serialVersionUID = 1L;

	public Office() {
		super();
	}

}
