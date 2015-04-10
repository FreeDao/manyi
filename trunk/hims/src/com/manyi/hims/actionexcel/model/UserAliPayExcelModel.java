package com.manyi.hims.actionexcel.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAliPayExcelModel {

	private int hadBoundNum;
	
	private int notBoundNum;
	
	private String date;
}
