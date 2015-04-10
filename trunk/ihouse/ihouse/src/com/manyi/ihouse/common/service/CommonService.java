/**
 * 
 */
package com.manyi.ihouse.common.service;

import java.awt.geom.Area;
import java.sql.Date;
import java.util.List;

/**
 * 
 * @author tiger
 *
 */
public interface CommonService {
//	
//	/**
//	 * 
//	 * @param logId
//	 * @return
//	 */
//	public RecordResponce loadInfoLog(int logId);
//	
//	/**
//	 * 
//	 * @param sourceLogId
//	 * @return
//	 */
//	public HouseResourceContact getSourceHost(Integer sourceLogId);
//	
//	/**
//	 * 通过时间查询 到得所以的城市列表
//	 * @author zxc
//	 */
//	public List<City> getCityListByTime(Date formTime,Date toTime);
//	
//	/**
//	 * 全量 到得所以的城市列表
//	 * @author zxc
//	 */
//	public List<City> getAllCityList();
//		
//	/**
//	 * 通过parentid 得到area 列表
//	 * @param parentId
//	 * @param b 
//	 * @return
//	 */
//	public  List<Area> findAreaByParentId(int parentId, boolean b);
//
//	/**
//	 * 通过小区名字查找 小区列表
//	 * @param name
//	 * @return
//	 */
//	List<Estate> findEstateByName(String name);
//	
//	/**
//	 * 通过小区首字母查找 小区列表
//	 * @param name
//	 * @return
//	 */
//	List<Estate> findEstateByInitials(String name);
//	
	/**
	 * 每天 清除 每个用户当天 已经使用的查看的数据量
	 * @return
	 */
	public int clearUserPublishCount();
//	
//	/**
//	 * 自动更新
//	 * @param name
//	 * @return
//	 */
//	public AutoUpdateResponse automaticUpdates();
//	
//	/**
//	 * 查看房源详情  可查看次数
//	 * @author fangyouhui
//	 *
//	 */
//	public int houseCount(int uid);
}
