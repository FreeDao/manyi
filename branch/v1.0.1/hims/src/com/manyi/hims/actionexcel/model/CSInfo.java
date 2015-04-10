/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc
 * select r.employeeId,r.realName,r.userName,sum(r.success) as success,sum(r.ing) as ing,sum(r.faild) as faild,sum(r.success)+sum(r.ing)+sum(r.faild) as allCount
 */
public class CSInfo {

	private int employeeId;
	private String realName;
	private String userName;
	private String success;
	private String ing;
	private String faild;
	private String allCount;
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getRealName() {
		return format4Null(realName);
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getUserName() {
		return format4Null(userName);
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSuccess() {
		return format4Null(success);
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getIng() {
		return format4Null(ing);
	}
	public void setIng(String ing) {
		this.ing = ing;
	}
	public String getFaild() {
		return format4Null(faild);
	}
	public void setFaild(String faild) {
		this.faild = faild;
	}
	public String getAllCount() {
		return format4Null(allCount);
	}
	public void setAllCount(String allCount) {
		this.allCount = allCount;
	}
	
	private String format4Null(Object obj) {
		return obj == null ? StringUtils.EMPTY : obj + StringUtils.EMPTY;
	}
}
