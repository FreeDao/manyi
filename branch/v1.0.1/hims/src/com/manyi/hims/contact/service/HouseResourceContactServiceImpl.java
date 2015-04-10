package com.manyi.hims.contact.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.entity.HouseResourceContact;

/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom
 * @description 房源信息联系人服务类
 */
@Service(value = "houseResourceContactService")
@Scope(value = "singleton")
@SuppressWarnings("unchecked")
public class HouseResourceContactServiceImpl extends BaseService implements HouseResourceContactService {
	
	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @param enable 是否为历史
	 */
	public List<HouseResourceContact> getContactList(int houseId, Boolean enable) {
		
		Query query = this.getEntityManager().createQuery(" from HouseResourceContact a where a.houseId = ? and a.enable = ? and a.status <> 3");
		return query.setParameter(1, houseId).setParameter(2, enable).getResultList();

	}
	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 */
	public List<HouseResourceContact> getContactList(int houseId) {
		
		Query query = this.getEntityManager().createQuery(" from HouseResourceContact a where a.houseId = ? order by a.enable and a.status <> 3");
		return query.setParameter(1, houseId).getResultList();
	
	}

	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @param enable 是否为历史
	 * @return List<张先生 - 13900000000>
	 */
	public List<String> getContactList4HouseId(int houseId, Boolean enable) {

		List<String> list = new ArrayList<String>();
		List<HouseResourceContact> houseResourceContactliList = (enable == null ? getContactList(houseId) : getContactList(houseId, enable));

		for (HouseResourceContact c : houseResourceContactliList) {
			list.add(c.getHostName()==null?"未知":c.getHostName() + "-" + c.getHostMobile());
		}
		
		return list;
	}
	
	/**
	 * @date 2014年4月26日 下午2:33:30
	 * @author Tom
	 * @description 根据houseId查询联系人
	 * @return List<张先生 - 13900000000>
	 */
	public List<String> getContactList4HouseId(int houseId) {
		return this.getContactList4HouseId(houseId, null);
	}

	 

}
