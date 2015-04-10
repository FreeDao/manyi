package com.manyi.ihouse.user.model;

import com.manyi.ihouse.base.PageResponse;

/**
 * 查看看房单中房源列表Response模型
 * @author hubin
 *
 */
public class SeekHouseResponse extends PageResponse<HouseBaseModel>{

	private int assigneeRecommend; //经纪人推荐房源数
	
	private int wantSee;//我要看的房源数
    
    /**
     * 首次请求时间毫秒数
     */
    private long firstTime;
	
	public long getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public int getAssigneeRecommend() {
		return assigneeRecommend;
	}

	public void setAssigneeRecommend(int assigneeRecommend) {
		this.assigneeRecommend = assigneeRecommend;
	}

	public int getWantSee() {
		return wantSee;
	}

	public void setWantSee(int wantSee) {
		this.wantSee = wantSee;
	}
}
