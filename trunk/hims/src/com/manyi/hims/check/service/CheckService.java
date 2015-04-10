/**
 * 
 */
package com.manyi.hims.check.service;

import com.manyi.hims.PageResponse;
import com.manyi.hims.check.controller.CheckRestController.CheckRes;
import com.manyi.hims.check.model.CheckReq;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;


/**
 * 
 * @author tiger
 *
 */
public interface CheckService {

	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	public PageResponse<CheckRes> checktaskList(CheckReq req);
	
	
	
	public void addContact(HouseResourceContact oper);
	
	public void submitResult(HouseResource oper) ;
	
	/**
	 * @date 2014年4月22日 下午10:24:39
	 * @author Tom  
	 * @description  
	 * 地推人员审核操作
	 */
	public void audit4operFloor(FloorRequest floorRequest);

	public String getUser4UserId(int userId);
	
	
}
