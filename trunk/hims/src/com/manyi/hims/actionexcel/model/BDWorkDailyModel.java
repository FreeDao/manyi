package com.manyi.hims.actionexcel.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class BDWorkDailyModel {
	private List<BDWorkDailySingleModel> bdWorkDailys = new ArrayList<BDWorkDailySingleModel>();
	private Map<Integer, Integer> directSum = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> notDirectSum = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> allSum = new HashMap<Integer, Integer>();
	
	
	
	@Data
	public static class BDWorkDailySingleModel {
		private String date;
		private int direct;
		private int notDirect;
		private int bdId;
		private String bdName;
		
	}
}

