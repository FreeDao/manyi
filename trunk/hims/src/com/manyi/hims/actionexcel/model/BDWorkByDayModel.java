package com.manyi.hims.actionexcel.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import lombok.Data;

@Data
public class BDWorkByDayModel {
	/**
	 * 日期
	 */
	private String day;
	/**
	 * BD手机
	 */
	private String mobile;
	/**
	 * BD id
	 */
	private String bdId;

	/**
	 * BD 名称
	 */
	private String bdName;
	
	/**
	 * 直接 推广人
	 */
	private String currNum;
	
	/**
	 * 间接推广人
	 */
	private String oldNum;
	
	private Map<String,BDWorkByDayModel> works =new LinkedHashMap<String, BDWorkByDayModel>();
	
}
