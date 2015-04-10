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
	
	/**
	 * @date 2014年4月29日 下午7:33:53
	 * @author Tom  
	 * @description  
	 * 根据id 获得parentArea
	 */
	public Area getParentAreaById(int id);
	
	/**
	 * @date 2014年5月22日 上午1:12:23
	 * @author Tom
	 * @description  
	 * 拼接上传阿里云路径：serialCode 0000100001000070000200106
	 * 
	 * 0000100001/000010000100007/00001000010000700002/0000100001000070000200106/
	 */
	public String getAliyunPath(String serialCode);
	
}
