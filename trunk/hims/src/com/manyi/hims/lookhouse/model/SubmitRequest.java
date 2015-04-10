package com.manyi.hims.lookhouse.model;

import lombok.Data;

@Data
public class SubmitRequest {
	private int id;
	private int status;
	private int operatorId;
	private String note;
	private int lookhouseEmpId;//看房的人的id
	private String lookTime;
	private String lookDate;
	

}
