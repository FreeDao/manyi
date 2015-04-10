package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.Response;
/**
 * 进入“我的”页面时获取的数据Response模型
 * @author hubin
 *
 */
public class UserMyResponse extends Response {
	
	private int orderNum; //订单数量
	
	private int collectionNum; //收藏数量
	
	private String serviceTel; //客服电话
	
	private String assigneeName; //经纪人姓名
	
	private String assigneeTel; //经纪人电话
	
	private String assigneePhotoUrl; //经纪人照片URL

    private float score; //经纪人评分

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public int getCollectionNum() {
		return collectionNum;
	}

	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}

	public String getServiceTel() {
		return serviceTel;
	}

	public void setServiceTel(String serviceTel) {
		this.serviceTel = serviceTel;
	}

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
