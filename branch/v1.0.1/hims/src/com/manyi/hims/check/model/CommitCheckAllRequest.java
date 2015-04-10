package com.manyi.hims.check.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CommitCheckAllRequest {
	
	
	private int status;
	
	private int sourceLogId;
	
	private int sellStatusInt;//0在售,1已售,2不售
	
	private int rentStatusInt;//rent 
	
	private String note;
	
	
	
	
	private int houseId;
	
	private BigDecimal sellPrice = new BigDecimal(0);
	
	private BigDecimal rentPrice = new BigDecimal(0);
	
	private boolean sellStatus;
	
	private boolean rentStatus;
	
	private int bedroomSum;// 几房
	
	private int livingRoomSum;// 几厅
	
	private int wcSum;// 几卫
	
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	
	private int checkLoopSuccessHouseStatus;//1 rent 2sell  4neither //3 在租在售用不到
	

}
