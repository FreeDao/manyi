package com.manyi.hims.actionexcel.model;

import lombok.Data;

/**
 * @author tom
 *
 */
@Data
public class User2infoRequest {

	private String start;
	private String end;
	private int cityType;
	private int areaId;
	
	private String userTels;
	private String userNames;

}
