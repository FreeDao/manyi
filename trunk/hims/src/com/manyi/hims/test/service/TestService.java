/**
 * 
 */
package com.manyi.hims.test.service;


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
	
	public void sendSMS();
}
