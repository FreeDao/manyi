package com.manyi.hims.actionexcel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPublishExcelModel {
	
	
	private String date;
	private int h1;
	private int h2to10;
	private int h11to50;
	private int h51to100;
	private int h101;
	
	public UserPublishExcelModel(String date) {
		this.date = date;
	}
	
	

	
}
