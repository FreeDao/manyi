package com.manyi.hims.contact.service;

import java.util.List;

import com.manyi.hims.entity.HouseResourceContact;

/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom 
 * @description  
 * 房源信息联系人服务类
 */
public interface HouseResourceContactService {

	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @param enable 是否为历史
	 */
	public List<HouseResourceContact> getContactList(int houseId, Boolean enable);
	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 */
	public List<HouseResourceContact> getContactList(int houseId);

	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @param enable 是否为历史
	 * @return List<张先生 - 13900000000>
	 */
	public List<String> getContactList4HouseId(int houseId, Boolean enable);

	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @return List<张先生 - 13900000000>
	 */
	public List<String> getContactList4HouseId(int houseId);
	

	
}
