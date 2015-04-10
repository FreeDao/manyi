package com.manyi.ihouse.research.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.ihouse.base.RestController;
import com.manyi.ihouse.base.SearchPageResponse;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.research.model.KeywordResponse;
import com.manyi.ihouse.research.model.SearchRequest;
import com.manyi.ihouse.research.service.SearchService;
import com.manyi.ihouse.user.model.HouseBaseModel;

@Controller
@RequestMapping("/Search")
public class SearchController extends RestController {

	@Autowired
	@Qualifier("searchServiceImpl")
	private SearchService  searchService;

	@RequestMapping(value = "/keywordService.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<KeywordResponse> keywordService(@RequestBody SearchRequest request) {
		final KeywordResponse response = this.searchService.keywordService(request);
		DeferredResult<KeywordResponse> dr = new DeferredResult<KeywordResponse>();
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/supportCityService.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<KeywordResponse> supportCityService(){
		final KeywordResponse response = this.searchService.supportCityService();
		DeferredResult<KeywordResponse> dr = new DeferredResult<KeywordResponse>();
		dr.setResult(response);
		return dr;
	}
	
	@RequestMapping(value = "/searchHouseList.rest", produces = "application/json")
	@ResponseBody
	public DeferredResult<SearchPageResponse> searchHouseList(@RequestBody SearchRequest request) {
		final SearchPageResponse<HouseBaseModel> lists = this.searchService.searchHouseList(request);
		DeferredResult<SearchPageResponse> dr = new DeferredResult<SearchPageResponse>();
		dr.setResult(lists);
		return dr;
	}
	
	@RequestMapping(value = "/searchHouseMapMark.rest", produces = "application/json")
	@ResponseBody
	public  DeferredResult<MapResponse> searchHouseMapMark(@RequestBody SearchRequest request){
		final MapResponse lists = this.searchService.searchHouseMapMark(request);
		DeferredResult<MapResponse> dr = new DeferredResult<MapResponse>();
		dr.setResult(lists);
		return dr;
	}
	
}
