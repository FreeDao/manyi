package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.TblLogOperate;
import com.ecpss.mp.entity.TblLogOperatorlogin;
import com.leo.common.Page;
@Service(value = "tblLoginLogService")
@Scope(value = "singleton")
public class TblLoginLogServiceImpl extends BaseService implements TblLoginLogService{
	
	@Override
	public List<TblLogOperatorlogin> getLoginLog() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblLogOperatorlogin> cq = cb.createQuery(TblLogOperatorlogin.class);
		cq.from(TblLogOperatorlogin.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	@Override
	public List<TblLogOperate> getLogOperate() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblLogOperate> cq = cb.createQuery(TblLogOperate.class);
		cq.from(TblLogOperate.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
}
