package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.TblOperator;
import com.ecpss.mp.entity.TblOperator_;
@Service(value = "tblOperatorService")
@Scope(value = "singleton")
public class TblOperatorServiceImpl extends BaseService implements TblOperatorService{

	/**
	 * getTblOperatorList
	 * @returnr
	 */
	public List<TblOperator> getTblOperatorByPage(Integer pageNo,Integer pageSize){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblOperator> cq = cb.createQuery(TblOperator.class);
		cq.from(TblOperator.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	/**
	 * 通过id查选对象
	 */
	@Override
	public List<TblOperator> getTblOperatorById(String id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblOperator> cq = cb.createQuery(TblOperator.class);
		Root<TblOperator> _tbloperator = cq.from(TblOperator.class);
		cq.where(cb.and(cb.equal(_tbloperator.get(TblOperator_.did),id))).select(_tbloperator);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
	

	@Override
	public void save(TblOperator tbloperator) {
		getEntityManager().persist(tbloperator);
	}

	@Override
	public void update(TblOperator tbloperator) {
		getEntityManager().merge(tbloperator);//合并
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		String sql="delete from TblOperator _tbloperator where _tbloperator.did = "+id;
		getEntityManager().createNativeQuery(sql).executeUpdate();
	}
	
}
