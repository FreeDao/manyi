package com.manyi.hims.residence.service;

import com.manyi.hims.entity.Residence;


/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom 
 * @description  
 * 住宅信息服务类
 */
public interface ResidenceService {

	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将residence复制到History表中
	 */
	public void copyResidence2History(Residence residence);
		
}
