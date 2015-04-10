package com.manyi.hims.actionexcel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserExcelRequest {
	
	private String startDate;
	
	private String endDate;
	
	private int cityId;
	
	private int areaId;
	
	private String recommenderMobile;

}
