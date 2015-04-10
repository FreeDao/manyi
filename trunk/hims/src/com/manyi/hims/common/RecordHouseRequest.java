/**
 * 
 */
package com.manyi.hims.common;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zxc
 * 
 */
public class RecordHouseRequest extends PublishHouseRequest {

	@NotEmpty(message="{ec_100003}")
	private int sourceLogTypeId; // 发布状态
	@NotEmpty(message="{ec_100003}")
	private String sourceLogTypeStr; // 发布状态对应的文本

	public int getSourceLogTypeId() {
		return sourceLogTypeId;
	}

	public void setSourceLogTypeId(int sourceLogTypeId) {
		this.sourceLogTypeId = sourceLogTypeId;
	}

	public String getSourceLogTypeStr() {
		return sourceLogTypeStr;
	}

	public void setSourceLogTypeStr(String sourceLogTypeStr) {
		this.sourceLogTypeStr = sourceLogTypeStr;
	}
}