package com.manyi.hims.common;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class HouseInfoResponse {
	private String name;//小区名称
	private String subEstateName;//小区名称
	private String address;//地址
	private String building;//楼栋号号
	private String room;//室号
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private BigDecimal sellPrice;//出售价格
	private BigDecimal rentPrice;//出租价格
	private int houseState;//1出租，2出售，3即租又售
	private int houseId;
}
