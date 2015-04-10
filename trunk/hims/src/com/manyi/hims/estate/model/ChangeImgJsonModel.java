package com.manyi.hims.estate.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ChangeImgJsonModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String imgkey;
	private String type;
	private String orderNum;
	
}
