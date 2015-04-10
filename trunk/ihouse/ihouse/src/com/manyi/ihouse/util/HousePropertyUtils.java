package com.manyi.ihouse.util;

import java.util.Date;

public class HousePropertyUtils {

	/**
	 * 转换装潢类型
	 * 
	 * @param type
	 * @return
	 */
	public static String convertDecorateType(int type) {
		switch (type) {
		case 1:
			return "毛坯";
		case 2:
			return "精装修";
		case 3:
			return "简装";
		case 4:
			return "豪装";
		default:
			return "";
		}
	}
	
	/**
	 * 房屋楼层类型：返回  低层，中层，高层
	 * @param floor
	 * @return
	 */
	public static String convertFloorType(int floor){
		if(floor < 7){
			return "低层";
		} else if(floor >=7 && floor < 13){
			return "中层";
		} else{
			return "高层";
		}
	}
	
	/**
	 * 返回一个long型数据   *10000后的房租价格
	 * @param obj
	 * @return
	 */
	public static long convertRentPrice(Object obj){
		try {
			if(obj == null){
				return 0;
			}
			return Long.parseLong(obj.toString()) * 10000;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return 0;
		}
	}
	
	
	
}
