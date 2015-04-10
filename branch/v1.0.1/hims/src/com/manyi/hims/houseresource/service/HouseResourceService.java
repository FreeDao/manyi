package com.manyi.hims.houseresource.service;

import com.leo.common.Page;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;


/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom 
 * @description  
 * 房源信息服务类
 */
public interface HouseResourceService {

	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将houseResource复制到Temp表中
	 */
	public void copyHouseResource2Temp(HouseResource houseResource);
	
	/**
	 * @date 2014年4月30日 下午5:43:06
	 * @author Tom  
	 * @description  
	 * 将HouseResourceTemp复制到History表中
	 */
//	public void copyHouseResourceTemp2History(HouseResourceTemp houseResourceTemp, String note, int operatorId);
	
	/**
	 * @date 2014年4月30日 下午5:48:23
	 * @author Tom  
	 * @description  
	 * 删除houseResourceTemp对象
	 */
	public void removeHouseResourceTemp(int houseId);
	
	/**
	 * @date 2014年4月30日 下午5:49:38
	 * @author Tom  
	 * @description  
	 * 更新houseResource对象
	 */
	public void mergeHouseResource(HouseResource houseResource);
	
	/**
	 * @date 2014年4月27日 下午8:46:36
	 * @author Tom  
	 * @description  
	 * 发布举报
	 */
	public void publicAudit(int houseId);
	
	/**
	 * @date 2014年4月30日 下午4:59:08
	 * @author Tom  
	 * @description  
	 * 返回HouseResource对象
	 */
	public HouseResource getHouseResourceByHouseId(int houseId);
	
	/**
	 * @date 2014年4月23日 下午1:52:23
	 * @author Tom  
	 * @description  
	 * 返回HouseResourceTemp对象
	 */
	public HouseResourceTemp getHouseResourceTemp(int houseId);
		
	/**
	 * @date 2014年4月23日 下午1:52:23
	 * @author Tom  
	 * @description  
	 * 返回updateHouseResourceHistory对象
	 * 当审核成功、失败的时候，
	 * 
	 * @param targetHouseState  本次操作房源的目标租售状态
	 */
	public void updateHouseResourceHistory(FloorRequest floorRequest, int status, int targetHouseState);
	
	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将houseResource复制到History表中
	 * @param state  StatusEnum.SUCCESS.getValue()  StatusEnum.Fail.getValue() 
	 * 
	 * @param targetHouseState  本次操作房源的目标租售状态
	 */
	public void copyHouseResource2History(HouseResource houseResource, String note, int operatorId, int state, int targetHouseState);
	
	/**
	 * @date 2014年4月29日 下午8:23:58
	 * @author Tom  
	 * @param req 页面传来的参数
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	public Page<HouseEntity> queryHouseResourceByPage(HouseSearchRequest req, Boolean isSell);
}
