package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

public class FeedbackResponse extends Response {
	
	/**
	 * 意见反馈ID
	 */
	private long feedbackId;
	
	/**
	 * 状态 0：没有绑定到用户  1：已绑定到用户
	 */
	private int state;

	public long getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
}
