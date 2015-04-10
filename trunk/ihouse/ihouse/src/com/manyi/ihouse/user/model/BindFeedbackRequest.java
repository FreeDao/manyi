package com.manyi.ihouse.user.model;

public class BindFeedbackRequest {
	
	/**
	 * 意见反馈ID
	 * 用于（意见反馈无需注册登录即可使用。不过一旦用户登录，这个反馈信息就应绑定到对应账号，以便我们在后台进行查询）
	 */
	private long feedbackId;

	public long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}


}
