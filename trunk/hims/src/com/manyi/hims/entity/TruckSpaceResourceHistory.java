package com.manyi.hims.entity;

import com.manyi.hims.entity.HouseResourceHistory;
import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: TruckSpaceResourceHistory
 *
 */
@Entity

public class TruckSpaceResourceHistory extends HouseResourceHistory implements Serializable {

	
	private static final long serialVersionUID = 1L;

	public TruckSpaceResourceHistory() {
		super();
	}
   
}
