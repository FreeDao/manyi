package com.manyi.hims.sourcemanage.model;

import java.math.BigDecimal;

import lombok.Data;

/**
 * @date 2014年4月17日 下午12:13:08
 * @author Tom
 * @description 房源管理详情页面,请求对象
 */
@Data
public class SourceManageRequest {

	private int operatorId;
	private int houseId;
	private String sourceId;
	private String sourceHostEnable;
	
	private int bedroomSum;
	private int livingRoomSum;
	private int wcSum;
	private String room;
	private BigDecimal spaceArea;
	private int houseState;
	private BigDecimal sellPrice;
	private BigDecimal rentPrice;
	
}
