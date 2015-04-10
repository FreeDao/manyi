package com.ecpss.mp.entity;

import com.ecpss.mp.entity.Employee;
import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: CallCenterUser
 *
 */
@Entity
public class CallCenterUser extends Employee implements Serializable {

	
	private String jobNumber;//呼叫员特有的编号
	private static final long serialVersionUID = 1L;

	public CallCenterUser() {
		super();
	}   
	public String getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
   
}
