package com.manyi.hims.actionexcel.service;

import org.springframework.web.multipart.MultipartFile;

public interface HouseActionService {

	/**
	 * 导入 子划分
	 * @param excel
	 * @return
	 */
	public String importSubEstate(MultipartFile excel);

	/**
	 * 导入楼栋
	 * @param file
	 * @return
	 */
	public String importBilding(MultipartFile file);

	/**
	 * 导入 房屋 
	 * @param file
	 * @return
	 */
	public String importHouse(MultipartFile file);
	
}
