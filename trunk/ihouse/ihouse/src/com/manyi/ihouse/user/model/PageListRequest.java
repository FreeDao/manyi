package com.manyi.ihouse.user.model;

public class PageListRequest {
	
	/**
	 * 页码
	 */
//	private int page = 1;
	
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

//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
