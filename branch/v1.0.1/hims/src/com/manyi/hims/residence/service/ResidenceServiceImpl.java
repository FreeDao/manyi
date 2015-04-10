package com.manyi.hims.residence.service;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;

/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom
 * 住宅信息服务类
 */
@Service(value = "residenceService")
@Scope(value = "singleton")
public class ResidenceServiceImpl extends BaseService implements ResidenceService {
	
	
	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将residence复制到History表中
	 */
	public void copyResidence2History(Residence residence) {
		ResidenceHistory residenceHistory = new ResidenceHistory();
		BeanUtils.copyProperties(residence, residenceHistory);
		this.getEntityManager().persist(residenceHistory);

	}
	
}
	

