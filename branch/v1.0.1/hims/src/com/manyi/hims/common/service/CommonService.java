/**
 * 
 */
package com.manyi.hims.common.service;

import java.sql.Date;
import java.util.List;

import com.manyi.hims.common.AutoUpdateResponse;
import com.manyi.hims.common.RecordResponse;
import com.manyi.hims.common.controller.CommonRestController.EstateResponse;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.HouseResourceContact;

/**
 * 
 * @author tiger
 * 
 */
public interface CommonService {

	/**
	 * 
	 * @param historyId
	 * @return
	 */
	public RecordResponse loadEstateInfo(int historyId);

	/**
	 * 
	 * @param parentId
	 * @return
	 */
	public String getSerialCode4Area(Integer parentId);

	/**
	 * 
	 * @param uid
	 * @param context
	 * @return
	 */
	public boolean insertFeedback(int uid, String context);

	/**
	 * 
	 * @param logId
	 * @return
	 */
	public RecordResponse loadInfoLog(int logId, int historyId);

	/**
	 * 
	 * @param sourceLogId
	 * @return
	 */
	public HouseResourceContact getSourceHost(Integer sourceLogId);

	/**
	 * 通过时间查询 到得所以的城市列表
	 * 
	 * @author zxc
	 */
	public List<City> getCityListByTime(Date formTime, Date toTime);

	/**
	 * 全量 到得所以的城市列表
	 * 
	 * @author zxc
	 */
	public List<City> getAllCityList();

	/**
	 * parentid查询area
	 * 
	 * @param parentId
	 * @param b
	 * @return
	 */
	public List<Area> findAreaByParentId(int parentId, boolean b);

	/**
	 * @date 2014年4月29日 下午3:21:59
	 * @author Tom
	 * @description 根据父区域code查询下属区域列表
	 */
	public List<Area> findAreaBySerialCode(String serialCode);

	/**
	 * 通过小区名字查找 小区列表
	 * 
	 * @param name
	 * @return
	 */
	List<EstateResponse> findEstateByName(String name);

	/**
	 * 每天 清除 每个用户当天 已经使用的查看的数据量
	 * 
	 * @return
	 */
	public int clearUserPublishCount();

	/**
	 * 自动更新
	 * 
	 * @param name
	 * @return
	 */
	public AutoUpdateResponse automaticUpdates(String versionStr);

	/**
	 * 查看房源详情 可查看次数
	 * 
	 * @author fangyouhui
	 * 
	 */
	public int houseCount(int uid);

	/**
	 * 
	 * 检查房源是否已经出售
	 * 
	 * @param estateId
	 *            小区id
	 * @param building
	 *            栋座号
	 * @param room
	 *            室号
	 * @param houseType
	 *            发布类型 1出租，2出售，3即租又售，
	 * @return
	 */
	public SellInfoResponse chenkHoustIsSell(int estateId, String building, String room, int houseType);
	
	public void updateUserCreateLogCount(int uid);

	public static class SellInfoResponse {
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
}
