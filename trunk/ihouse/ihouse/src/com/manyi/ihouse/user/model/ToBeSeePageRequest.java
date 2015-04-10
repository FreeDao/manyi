package com.manyi.ihouse.user.model;

/**
 * 待看房源约会列表request模型
 * @author hubin
 *
 */
public class ToBeSeePageRequest {
	/**
	 * 用户ID
	 */
	private long userId; 
	
	/**
	 * 已展现的数据条数
	 */
	private int offset = 0;
	
	/**
	 * 每页显示数量
	 */
	private int pageSize = 10;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}


	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
