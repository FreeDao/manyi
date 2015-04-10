package com.manyi.hims.distribute.model;

import com.manyi.hims.Response;


public class DistributeResponse extends Response{
	
	public DistributeResponse() {
		super();
	}
	public DistributeResponse(int id) {
		super();
		this.id = id;
	}
	private int id;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
