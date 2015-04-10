package com.manyi.hims.rent.service;

import java.util.List;

import com.leo.common.Page;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseRecordEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.common.PublishHouseRequest;

public interface RentService {

	/**
	 * 出租首页信息列表
	 * @return
	 */
	public List<HouseEntity> indexList();
	
	/**
	 * 查看出租房源详情
	 */
	public HouseEntity rentDetails(int houseId);
	/**
	 * 查看出租房记录详情
	 * @param logId
	 * @return
	 */
	public HouseRecordEntity rentRecordDetails(int logId);
	
	/**
	 * 检查房源是否已经出售
	 * @param estateId 小区id
	 * @param building  栋座号
	 * @param room 室号
	 * @return
	 */
	public RantInfoResponse chenkHoustIsRent(int estateId,String building,String room);
	
	/**
	 * 发布出租信息
	 * @param req
	 * @return
	 */
	public int publishRentInfo(PublishHouseRequest req);
	
	/**
	 * 修改发布记录信息
	 * @param req
	 * @return
	 */
	public int updatePublishRentInfo(PublishHouseRequest req);
	
	public static class RantInfoResponse{
		private int houseId;
		
		public RantInfoResponse(int houseId) {
			this.houseId = houseId;
		}

		public int getHouseId() {
			return houseId;
		}

		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
	}

}
