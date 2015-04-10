/**
 * 
 */
package com.manyi.hims.house.service;

import java.util.List;

import com.manyi.hims.PageResponse;
import com.manyi.hims.entity.House;
import com.manyi.hims.house.controller.HouseRestController.HouseReq;
import com.manyi.hims.house.controller.HouseRestController.HouseRes;


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
	
    public List<List<String>> houseModifications(int houseId);
    
}
