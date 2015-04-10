package com.manyi.ihouse.user.model;

/**
 * 意见反馈提交Request模型
 * @author hubin
 *
 */
public class FeedbackRequest {

	/**
	 * 用户ID
	 */
	private long userId;
	
	/**
	 * 意见
	 */
	private String advice;
	
	/**
	 * 手机设备唯一标识，
	 * 用于（意见反馈无需注册登录即可使用。不过一旦用户登录，这个反馈信息就应绑定到对应账号，以便我们在后台进行查询）
	 */
	private String mobileSn; 

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getMobileSn() {
		return mobileSn;
	}

	public void setMobileSn(String mobileSn) {
		this.mobileSn = mobileSn;
	}
	
}
