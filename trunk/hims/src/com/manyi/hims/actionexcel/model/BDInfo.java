/**
 * 
 */
package com.manyi.hims.actionexcel.model;

import org.apache.commons.lang.StringUtils;

/**
 * @author zxc
 * 
 *         select su.bdName,su.createTime,su.uid,su.realName,su.mobile,su.rentSum,su.sellSum,su.modifySum,su.reportSum,
 *         su.rentSec,su.sellSec, su.modifySec,su.reportSec,su.rentSum+su.sellSum+su.modifySum+su.reportSum as allSum,
 *         su.rentSec+su.sellSec+su.modifySec+su.reportSec as allSec
 */
public class BDInfo {

	private String bdName;
	private String createTime;
	private String uid;
	private String realName;
	private String mobile;
	private String rentSum;
	private String sellSum;
	private String modifySum;
	private String reportSum;
	private String rentSec;
	private String sellSec;
	private String modifySec;
	private String reportSec;
	private String allSum;
	private String allSec;

	public String getBdName() {
		return format4Null(bdName);
	}

	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	public String getCreateTime() {
		return format4Null(createTime);
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUid() {
		return format4Null(uid);
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRealName() {
		return format4Null(realName);
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return format4Null(mobile);
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRentSum() {
		return format4Null(rentSum);
	}

	public void setRentSum(String rentSum) {
		this.rentSum = rentSum;
	}

	public String getSellSum() {
		return format4Null(sellSum);
	}

	public void setSellSum(String sellSum) {
		this.sellSum = sellSum;
	}

	public String getModifySum() {
		return format4Null(modifySum);
	}

	public void setModifySum(String modifySum) {
		this.modifySum = modifySum;
	}

	public String getReportSum() {
		return format4Null(reportSum);
	}

	public void setReportSum(String reportSum) {
		this.reportSum = reportSum;
	}

	public String getRentSec() {
		return format4Null(rentSec);
	}

	public void setRentSec(String rentSec) {
		this.rentSec = rentSec;
	}

	public String getSellSec() {
		return format4Null(sellSec);
	}

	public void setSellSec(String sellSec) {
		this.sellSec = sellSec;
	}

	public String getModifySec() {
		return format4Null(modifySec);
	}

	public void setModifySec(String modifySec) {
		this.modifySec = modifySec;
	}

	public String getReportSec() {
		return format4Null(reportSec);
	}

	public void setReportSec(String reportSec) {
		this.reportSec = reportSec;
	}

	public String getAllSum() {
		return format4Null(allSum);
	}

	public void setAllSum(String allSum) {
		this.allSum = allSum;
	}

	public String getAllSec() {
		return format4Null(allSec);
	}

	public void setAllSec(String allSec) {
		this.allSec = allSec;
	}
	
	private String format4Null(Object obj) {
		return obj == null ? StringUtils.EMPTY : obj + StringUtils.EMPTY;
	}
}
