package com.manyi.hims.common;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class HouseSearchRequest {
	
	private String serialCode;//区域serialCode
	private int areaId;//区域areaId
	
	private int bedroomSum;//房型(几房)
	private BigDecimal startPrice;//起始价格
	private BigDecimal endPrice;//截止价格
	private BigDecimal startSpaceArea;// 起始内空面积
	private BigDecimal endSpaceArea;// 截止内空面积
	
	private int start;//数据起始下标
	private int max;//返回的数据量(返回几条记录)
	
	private Long markTime;//时间戳
	
	
}
