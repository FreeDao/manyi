package com.manyi.hims.actionexcel.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserRegisterExcelModel {

	private String date;
	private int total;
	private int success;
	private int fail;
	private int BDRecommendNum;
	private int ZJRecommendNum;
	private int NOTRecommendNum;
	private BigDecimal rate;
	private int userPublishNum;
	private int userReportedNum;
	
	
	public UserRegisterExcelModel(String date) {
		this.date = date;
	}
	
	
}
