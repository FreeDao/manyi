package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.TblRole;
import com.ecpss.mp.entity.TblRole_;
@Service(value = "tblRoleService")
@Scope(value = "singleton")
public class TblRoleServiceImpl extends BaseService implements TblRoleService{


	/**
	 * getTblRoleList
	 * @returnr
	 */
	public List<TblRole> getTblRoleByPage(Integer pageNo,Integer pageSize){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblRole> cq = cb.createQuery(TblRole.class);
		cq.from(TblRole.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	/**
	 * 通过id查选对象
	 */
	@Override
	public List<TblRole> getTblRoleById(String id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<TblRole> cq = cb.createQuery(TblRole.class);
		Root<TblRole> _tblrole = cq.from(TblRole.class);
		cq.where(cb.and(cb.equal(_tblrole.get(TblRole_.did),id))).select(_tblrole);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
	

	@Override
	public void save(TblRole tblrole) {
		getEntityManager().persist(tblrole);
	}

	@Override
	public void update(TblRole tblrole) {
		getEntityManager().merge(tblrole);//合并
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		String sql="delete from TblRole _tblrole where _tblrole.did = "+id;
		getEntityManager().createNativeQuery(sql).executeUpdate();
	}
	
}
