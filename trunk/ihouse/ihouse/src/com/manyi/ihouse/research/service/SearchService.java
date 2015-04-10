package com.manyi.ihouse.research.service;

import com.manyi.ihouse.base.SearchPageResponse;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.research.model.KeywordResponse;
import com.manyi.ihouse.research.model.SearchRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;

public interface SearchService {
	public KeywordResponse keywordService(SearchRequest request);
	
	public KeywordResponse supportCityService();
	
	public SearchPageResponse<HouseBaseModel> searchHouseList(SearchRequest request);
	
	public MapResponse searchHouseMapMark(SearchRequest request);
}
