/**
 * 
 */
package com.manyi.hims.test.service;

import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.User;
import com.manyi.hims.uc.controller.UserRestController.RegistRequest;

/**
 * @author lei
 *
 */
public interface TestService {
	/**
	 * 出售审核通过
	 * @param houseId
	 */
	public void sellExamineThrough(String[] houseId);
	/**
	 * 出租审核通过
	 * @param houseId
	 */
	public void rentExamineThrough(String[] houseId);
	
	public void addArea();
}
