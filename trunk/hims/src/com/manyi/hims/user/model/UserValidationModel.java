package com.manyi.hims.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserValidationModel {
	
	private int id;
	private String mobile;
	private String name;
	private String verifyCode;
	private int status;
	private String statusStr;

}
 