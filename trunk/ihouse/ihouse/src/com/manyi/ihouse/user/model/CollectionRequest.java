package com.manyi.ihouse.user.model;

/**
 * 添加收藏房源request模型
 * @author hubin
 *
 */
public class CollectionRequest {
	
	private long userId;//用户ID
	
	private long houseId;//房源ID

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getHouseId() {
		return houseId;
	}

	public void setHouseId(long houseId) {
		this.houseId = houseId;
	}

}
