/**
 * 
 */
package com.manyi.hims.actionexcel.model;

/**
 * @author zxc
 */
public class HourseHostInfo {

	private int hostCount;
	private String hostName;
	private Long hostMobile;
	private Long houseId;
	private String realName;
	private Long mobile;

	public int getHostCount() {
		return hostCount;
	}

	public void setHostCount(int hostCount) {
		this.hostCount = hostCount;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getHostMobile() {
		return hostMobile;
	}

	public void setHostMobile(Long hostMobile) {
		this.hostMobile = hostMobile;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
	}

	public Long getMobile() {
		return mobile;
	}

	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}

	@Override
	public String toString() {
		return this.getHostName() + "\t" + this.getHostMobile() + "\t" + this.getHostCount() + "\t" + this.getHouseId() + "\t"
				+ this.getRealName() + "\t" + this.getMobile() + "\n\r";
	}
}
