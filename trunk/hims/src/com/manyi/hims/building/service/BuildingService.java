/**
 * 
 */
package com.manyi.hims.building.service;

import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.building.model.BuildingReq;
import com.manyi.hims.building.model.BuildingRes;



/**
 * 
 * @author tiger
 *
 */
public interface BuildingService {

	/**
	 * 楼栋 信息 列表
	 * @param req
	 * @return
	 */
	PageResponse<BuildingRes> buildingList(BuildingReq req);
	
	/**
	 * 编辑楼栋 时 , 加载 楼栋面
	 * @param areaId 楼栋 ID
	 * @return
	 */

	public BuildingRes findBuildingById(String id);

	/**
	 * 编辑楼栋
	 * @param req
	 * @return
	 */
	public Response editBuilding(BuildingRes req);
	
}
