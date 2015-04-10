package com.manyi.hims.sell.service;

import java.util.List;

import com.leo.common.Page;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceResponse;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.common.PublishHouseRequest;

public interface SellService {
	
	/**
	 * 查看出售房源详情
	 */
	public HouseEntity sellDetails(int houseId);
	
	
	/**
	 * 出售首页信息列表
	 * @return
	 */
	public List<HouseEntity> indexList(int cityId);
	
	/**
	 * 分页查询  过滤 数据
	 * @param req
	 * @return
	 */
	public Page<HouseResourceResponse> searchHouseResources(HouseSearchRequest req);
	

	/**
	 * 检查房源是否已经出售
	 * @param estateId 小区id
	 * @param building  楼栋号号
	 * @param room 室号
	 * @param houseType 发布类型  1出租，2出售，3即租又售，
	 * @return
	 */
	public SellInfoResponse chenkHoustIsSell(int estateId,String building,String room,int houseType);
	
	/**
	 * 发布出售信息
	 * @param req
	 * @return
	 */
	public int publishSellInfo(PublishHouseRequest req);
	
	public static class SellInfoResponse{
		private int houseId;
		
		public SellInfoResponse(int houseId) {
			this.houseId = houseId;
		}

		public int getHouseId() {
			return houseId;
		}

		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
	}

	/**
	 * 修改 发布 出售记录 信息
	 * @param req
	 * @return
	 */
	public int updatePublishSellInfo(PublishHouseRequest req);
}
