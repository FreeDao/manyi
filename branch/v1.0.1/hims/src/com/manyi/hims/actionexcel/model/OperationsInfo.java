/**
 * 
 */
package com.manyi.hims.actionexcel.model;

/**
 * @author DWP
 */
public class OperationsInfo {
	private String realName;
	private String mobile;
	private String beginCreateTime;
	private String endCreateTime;
	private int totalNum;
	private int verifyNum;
	
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBeginCreateTime() {
		return beginCreateTime;
	}
	public void setBeginCreateTime(String beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	public String getEndCreateTime() {
		return endCreateTime;
	}
	public void setEndCreateTime(String endCreateTime) {
		this.endCreateTime = endCreateTime;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getVerifyNum() {
		return verifyNum;
	}
	public void setVerifyNum(int verifyNum) {
		this.verifyNum = verifyNum;
	}
	
	
}
