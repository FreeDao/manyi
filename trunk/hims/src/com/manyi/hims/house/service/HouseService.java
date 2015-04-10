/**
 * 
 */
package com.manyi.hims.house.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.house.model.HouseDetailRes;
import com.manyi.hims.house.model.HouseReq;
import com.manyi.hims.house.model.HouseRes;
import com.manyi.hims.entity.Residence;



/**
 * 
 * @author tiger
 *
 */
public interface HouseService {

	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	public PageResponse<HouseRes> houseList(HouseReq req);

	/**
	 * @date 2014年4月28日 下午5:30:12
	 * @author Tom  
	 * @description  
	 * 将House复制到History表中
	 */
	public void copyHouse2History(House house);
	
	/**
	 * @date 2014年4月23日 下午12:25:07
	 * @author Tom  
	 * @description  
	 * 加载房屋信息（例如： 中远两湾城 - 22号 - 1102室）
	 */
	public String getHouseMsg(int houseId);
	
	/**
	 * @date 2014年5月19日 下午8:12:16
	 * @author Tom
	 * @description  
	 * 更新房型
	 */
	public Response checkUpdateResidence(Residence residence);

	
    public List<List<String>> houseModifications(int houseId);
    
	/**
	 * @date 2014年5月22日 上午1:22:15
	 * @author Tom
	 * @description  
	 * 根据houseId获得小区serialCode  
	 */
	public String getSerialCodeByHouseId(int houseId);
	
	
	/**
	 * @date 2014年5月22日 上午1:22:15
	 * @author Tom
	 * @description  
	 * 根据houseId获得DecorateType 	1 毛坯，2 装修  
	 */
	public int getDecorateTypeByHouseId(int houseId) ;
	
	/**
	 * @date 2014年5月26日 下午1:53:40
	 * @author Tom
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @description  
	 * 查看装修是否是1基本配套，2高级配套
	 */
	public String getHouseSuppoert(int houseId, String isGaoji) throws Exception;
    
	
	/**
	 * @date 2014年5月26日 下午2:41:37
	 * @author Tom
	 * @description  
	 * 获得有效房屋图片列表
	 */
	public List<HouseImageFile> getHouseEnableImageList(int houseId);
	
	/**
	 * @date 2014年5月26日 下午2:41:37
	 * @author Tom
	 * @description  
	 * 获得usertask 拍照详情页面图片
	 */
	public List<HouseImageFile> getHouseImageList(int houseId, int userTaskId);
	
	/**
	 * @date 2014年5月27日 下午1:54:14
	 * @author Tom
	 * @description  
	 * 获得任务的完成时间和位置信息
	 */
	public BdTask getBDtaskFinishAndLBS(int houseId);
	
	/**
	 * @date 2014年6月6日 下午3:16:06
	 * @author Tom
	 * @description  
	 * 查询房子
	 */
	public House findHouse(int subEstateId, String building, String room);

	/**
	 * 通过usertaskid 获取 装修情况
	 * @param taskId
	 * @param isGaoji
	 * @return
	 * @throws Exception
	 */
	String getHouseSuppoertByUserTaskId(int taskId, String isGaoji) throws Exception;

	public int getDecorateTypeByUserTaskId(int houseId);

	/**
	 * 编辑 房屋
	 * @param req
	 * @return
	 */
	public Response editHouse(HouseDetailRes req);

	/**
	 * 编辑 时 , 加载 房屋 页面
	 * @param houseId
	 * @return
	 */
	public HouseDetailRes findHouseById(int houseId);
	
}
