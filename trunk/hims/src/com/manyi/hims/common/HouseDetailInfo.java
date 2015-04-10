package com.manyi.hims.common;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class HouseDetailInfo{
	private String estateName;//小区名称
	private String townName;//片区名称
	private String cityName;//行政区名称
	private String provinceName;//城市名称
	private List<String> addressList;
	private List<HostList> hostList;//联系人信息
	
	/****************building***********************/
	private String buildingName;//楼栋名称
	
	private String aliasName;//楼栋别名 (多个,需要定义分隔符)
	
	private int totalLayers;//总层高
	/****************子划分***********************/
	
	private String SubEstateName;//子划分的名称
	
	/******************house***************************/
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private int balconySum;// 几阳台
	private BigDecimal sellPrice;//出售价格
	private BigDecimal rentPrice;//出租价格
	private int houseState;//1出租，2出售，3即租又售
	private int houseId;
	
	
	@Data
	public static class HostList{
		private String hostName;//联系人姓名
		private String hostMobile;//联系人电话
	}
}

