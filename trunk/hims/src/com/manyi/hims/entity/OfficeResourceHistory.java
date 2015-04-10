package com.manyi.hims.entity;

import com.manyi.hims.entity.HouseResourceHistory;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: OfficeResourceHistory
 *
 */
@Entity

public class OfficeResourceHistory extends HouseResourceHistory implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public OfficeResourceHistory() {
		super();
	}
   
}
