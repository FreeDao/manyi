package com.manyi.ihouse.user.model;

/**
 * 评价提交request模型
 * @author hubin
 *
 */
public class CommentRequest {
	
	private long userId; //用户ID
	
	private long appointmentId; //约会ID

	private int ability; //专业知识  分数 1-5分
	
	private int appearance; //仪容仪表  分数 1-5分
	
	private int attitude; //服务态度 分数 1-5分
	
	private String commentWord; //评价语

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getAttitude() {
		return attitude;
	}

	public void setAttitude(int attitude) {
		this.attitude = attitude;
	}

	public int getAppearance() {
		return appearance;
	}

	public void setAppearance(int appearance) {
		this.appearance = appearance;
	}

	public int getAbility() {
		return ability;
	}

	public void setAbility(int ability) {
		this.ability = ability;
	}

	public String getCommentWord() {
		return commentWord;
	}

	public void setCommentWord(String commentWord) {
		this.commentWord = commentWord;
	}
	
}
