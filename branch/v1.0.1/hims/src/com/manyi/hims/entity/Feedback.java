/**
 * 
 */
package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

/**
 * @author zxc
 *
 */
@Entity
public class Feedback implements Serializable{

	private static final long serialVersionUID = 1575134496909939096L;
	
	private int feedbackId;
	private int uid;
	private String context;//意见反馈内容
	private Date createTime;
	
	@Column(nullable = false)
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Column(length = 2047)
	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Feedback")
	@TableGenerator(name = "Feedback", allocationSize = 1)
	public int getFeedbackId() {
		return feedbackId;
	}

	public void setFeedbackId(int feedbackId) {
		this.feedbackId = feedbackId;
	}

	@Column(nullable = false)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
