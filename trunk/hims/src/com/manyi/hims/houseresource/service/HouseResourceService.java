package com.manyi.hims.houseresource.service;

import java.util.List;

import com.leo.common.Page;
import com.manyi.hims.Response;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceTemp;


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
	 * @date 2014年5月16日 上午10:13:04
	 * @author Tom  
	 * @param cityId 北京/上海
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	public List<HouseEntity> indexList(int cityId, Boolean isSell);
	
	/**
	 * @date 2014年4月29日 下午8:23:58
	 * @author Tom  
	 * @param req 页面传来的参数
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	public Page<HouseEntity> queryHouseResourceByPage(HouseSearchRequest req, Boolean isSell);
	
	/**
	 * @date 2014年6月6日 下午8:00:35
	 * @author Tom
	 * @param lunxunFlag 是否排斥轮询任务  。 
	 * 
	 * 	true 轮询任务进行中，不可以发布其他任务; 
	 * 	false 轮询任务进行中，可以发布其他任务
	 * 
	 * @description  
	 * 
	 * 查询该房源正在进行的任务
	 * 
	 * @return 
	 * 
	 * 	EntityUtils.ActionTypeEnum 枚举类的 value
	 * 	0,未知; 1,发布;2,改盘;3,举报;4,轮询;5,BD拍照;6,中介拍照;7,没有任何任务
	 * 
	 */
	public int getOngoingTask(int houseId, boolean lunxunFlag);
	
	// fuhao
	public int getOngoingTaskAll(int houseId, boolean lunxunFlag);
	
	/**
	 * @date 2014年6月12日 下午6:35:09
	 * @author Tom
	 * @description  
	 * 检测是否可以领取拍照任务
	 */
	public Response checkAllowTakePhoto(int houseId);
	
}
