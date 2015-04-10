package com.manyi.ihouse.user.model;

/**
 * 删除看房单中的房源request模型
 * @author hubin
 *
 */
public class DeleteSeekHouseRequest {
	
	private long userId; //用户ID

	private String houseIds; //要删除的房源ID 多个用“，"半角逗号隔开

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getHouseIds() {
		return houseIds;
	}

	public void setHouseIds(String houseIds) {
		this.houseIds = houseIds;
	}

}
