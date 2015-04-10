package com.manyi.ihouse.base;

import java.util.List;


public class SearchPageResponse<T> extends PageResponse<T> {
	private List<T> recommendList;

	public List<T> getRecommendList() {
		return recommendList;
	}

	public void setRecommendList(List<T> recommendList) {
		this.recommendList = recommendList;
	}
}
