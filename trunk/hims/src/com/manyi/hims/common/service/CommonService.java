/**
 * 
 */
package com.manyi.hims.common.service;

import java.sql.Date;
import java.util.List;

import com.manyi.hims.common.AutoUpdateResponse;
import com.manyi.hims.common.HouseDetailInfo;
import com.manyi.hims.common.HouseInfoResponse;
import com.manyi.hims.common.RecordResponse;
import com.manyi.hims.common.controller.CommonRestController.EstateResponse;
import com.manyi.hims.common.controller.CommonRestController.SubEstateResponse;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseResourceViewCount;

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
	 * @param citySerialCode
	 *            城市的 serialCode, 匹配到下面的小区serialCode 要加15个 _
	 */
	public List<EstateResponse> findEstateByName(String citySerialCode, String name);

	/**
	 * 每天 清除 每个用户当天 已经使用的查看的数据量
	 * 
	 * @return
	 */
	public int clearUserPublishCount(String jpql);

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
	public HouseResourceViewCount houseCount(int uid/*,int houseId*/);

	/**
	 * 
	 * 检查房源是否已经出售
	 * 
	 * @param estateId
	 *            小区id
	 * @param building
	 *            楼栋号
	 * @param room
	 *            室号
	 * @param houseType
	 *            发布类型 1出租，2出售，3即租又售，
	 * @return
	 */
	public SellInfoResponse chenkHoustIsSell(int estateId, String building, String room, int houseType);
	
	public void updateUserCreateLogCount(int uid);
	
	/**
	 * 同一手机号码当天只能发布两次
	 * @author fangyouhui
	 *
	 */
	public int publishCount(String mobile);
	
	/**
	 * 保存经纪人手机号码到中介库
	 * @author fangyouhui
	 *
	 */
	
	
	public void addIntermediary(String mobile);
	
	public HouseInfoResponse getHouseInfo(int houseId);
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

	/**
	 * 
	 * 检查房源是否开放
	 * 
	 * @param estateSerialCode
	 *            小区id
	 * @param houseType
	 *            发布类型 1出租，2出售，3即租又售，
	 * @return
	 */
	public boolean openStatus(int serialCode,
			int houseType);
	
	/**
	 * @date 2014年5月16日 下午1:13:07
	 * @author Tom
	 * @description  
	 * 因为北京下的小区有单元号，上海的没有
	 * 所以这里检测当前发布的小区是否是北京下的
	 */
	public boolean checkIsBeiJing(int estateId);
	public String checkBuilding(String building);
	public String changeRoomStr(String room);
	/**
	 * 
	 * @author fangyouhui
	 * @param type 1 房源的详细信息  2  房源的简要信息    
	 * @param houseId
	 * @return
	 */
	public List<HouseDetailInfo> getHouseDetailInfo(int type,int[] houseId);

	/**
	 * @date 2014年6月23日 下午4:54:52
	 * @author Tom
	 * @description  
	 * 根据小区id返回子划分List
	 */
	public List<SubEstateResponse> getSubEstateResponseList(int estateId);
	/**
	 * 发送短信
	 * @param mobile
	 * @param content
	 * @return
	 */
	public int sendSMS(String mobile, String content);
	
}
