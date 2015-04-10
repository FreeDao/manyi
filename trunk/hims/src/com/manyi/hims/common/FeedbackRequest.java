/**
 * 
 */
package com.manyi.hims.common;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zxc
 */
public class FeedbackRequest {

	@NotEmpty(message = "{ec_100003}")
	private int uid;
	@NotEmpty(message = "{ec_100003}")
	private String context;//意见反馈内容
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}
}
