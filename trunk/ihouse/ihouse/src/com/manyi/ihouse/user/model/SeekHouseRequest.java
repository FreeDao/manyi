package com.manyi.ihouse.user.model;

/**
 * 查看看房单中房源列表request模型
 * @author hubin
 *
 */
public class SeekHouseRequest {
	/**
	 * 用户ID
	 */
	private long userId; 
	
	/**
	 * 查看类型 1：我要看的，2：经济人推荐的
	 */
	private int type = 1; 
	
	/**
	 * 首次请求时间毫秒数
	 */
	private long firstTime;

    /**
	 * 已显示数据条数
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
    
    public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

}
