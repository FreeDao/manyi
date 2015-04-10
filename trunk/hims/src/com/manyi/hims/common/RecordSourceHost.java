/**
 * 
 */
package com.manyi.hims.common;

import java.io.Serializable;

/**
 * @author zxc
 *
 */
public class RecordSourceHost implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String hostName;//联系人姓名
	private String hostMobile;//联系人电话
	
	public RecordSourceHost(){
		
	}
	
	public RecordSourceHost(String hostName,String hostMobile){
		setHostMobile(hostMobile);
		setHostName(hostName);
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostMobile() {
		return hostMobile;
	}

	public void setHostMobile(String hostMobile) {
		this.hostMobile = hostMobile;
	}
}
