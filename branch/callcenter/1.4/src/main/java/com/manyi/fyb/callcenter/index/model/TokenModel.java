package com.manyi.fyb.callcenter.index.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenModel<T> implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5374484507169721408L;

	private int token;
	
	private int employeeId;
	
	private T tokenObject; 
	
	private Date addTime;

	public TokenModel(int token, int employeeId, T tokenObject) {
		this.token = token;
		this.employeeId = employeeId;
		this.tokenObject = tokenObject;
	}
	
	

	

}
