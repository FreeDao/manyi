package com.manyi.hims.area.service;

import com.manyi.hims.entity.Area;


public interface AreaService {

	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据serialCode获得小区
	 */
	public Area getArea4SerialCode(String serialCode);
	
	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据获得小区serialCode  
	 */
	public String getSerialCodeByAreaId(int areaId);
	
}
