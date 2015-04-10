package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;
/**
 * 已评价行程详情Response模型
 * @author hubin
 *
 */
public class CommentDetailResponse extends Response {
	
	private int appointmentId; //约会ID
	
	private String date; //约看日期 eg.2014年10月25日
	
	private String meetAddress; //碰头地点
	
	private String memo;//备注
	
	private int attitude; //服务态度 1-较差; 2-一般; 3-挺好

	private int ability; //业务能力 1-较差; 2-一般; 3-挺好
	
	private String commentWord; //评价语

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMeetAddress() {
		return meetAddress;
	}

	public void setMeetAddress(String meetAddress) {
		this.meetAddress = meetAddress;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public int getAttitude() {
		return attitude;
	}

	public void setAttitude(int attitude) {
		this.attitude = attitude;
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
