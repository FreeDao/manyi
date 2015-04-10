package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.City;
import com.ecpss.mp.entity.Tblemployee;

@Service(value = "tblemployeeService")
@Scope(value = "singleton")
public class TblemployeeServiceImpl extends BaseService implements TblemployeeService {
	
	
	/**
	 * getList
	 * @return
	 */
	public List<Tblemployee> getList(){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Tblemployee> cq = cb.createQuery(Tblemployee.class);
		cq.from(Tblemployee.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	
}
