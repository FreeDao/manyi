/**
 * 
 */
package com.manyi.ihouse.subway.service;

import java.util.List;

/**
 * @author berlinluo
 * 2014年6月18日
 */
public interface DataImportService {

	// 地铁信息导入
	public int importSubwayInfoForOldTemplate(List<List<String>> infoList);

	// 新模板地铁线路信息导入
	public int importSubwayLine(List<List<String>> infoList);

	// 地铁站点信息导入
	public int importSubwayStation(List<List<String>> infoList);

	// 地铁拐点信息导入
	public int importSubwayPoint(List<List<String>> infoList, String lineName, int cityCode);

}
