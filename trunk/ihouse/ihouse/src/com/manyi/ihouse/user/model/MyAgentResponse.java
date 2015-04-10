package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;

/**
 * 我的经纪人Response
 * Function: TODO ADD FUNCTION
 *
 * @author   hubin
 * @Date	 2014年6月16日		下午5:43:51
 *
 * @see
 */
public class MyAgentResponse extends Response {
	
	private String assigneeName; //经纪人姓名
	
	private String assigneeTel; //经纪人电话
	
	private String assigneePhotoUrl; //经纪人照片URL

	private float score; //经纪人评分

    public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public String getAssigneeTel() {
		return assigneeTel;
	}

	public void setAssigneeTel(String assigneeTel) {
		this.assigneeTel = assigneeTel;
	}

	public String getAssigneePhotoUrl() {
		return assigneePhotoUrl;
	}

	public void setAssigneePhotoUrl(String assigneePhotoUrl) {
		this.assigneePhotoUrl = assigneePhotoUrl;
	}

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
	
}
