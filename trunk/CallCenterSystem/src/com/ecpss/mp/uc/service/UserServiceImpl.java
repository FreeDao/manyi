package com.ecpss.mp.uc.service;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.User;
import com.ecpss.mp.entity.User_;
import com.ecpss.mp.uc.UcConst;
import com.leo.jaxrs.fault.LeoFault;

@Service(value = "userService")
@Scope(value = "singleton")
public class UserServiceImpl extends BaseService implements UserService {
	public User login(String loginName,String password){
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> user = cq.from(User.class);
	
		cq.where(cb.and(cb.equal(user.get(User_.userName), loginName),cb.equal(user.get(User_.password),password)))
			.select(user);
		
		try{
			return getEntityManager().createQuery(cq).getSingleResult();
		}catch(NoResultException e){
			throw new LeoFault(UcConst.ERROR_10100001);
		}
	}
	
	public User login1(String loginName,String password){
		final String jpql = "from User as me where me.userName=? and me.password=?";
		Query loginQuery = getEntityManager().createQuery(jpql);
		loginQuery.setParameter(1, loginName);
		loginQuery.setParameter(2, password);
		try{
			return (User) loginQuery.getSingleResult();
		}catch(NoResultException e){
			throw new LeoFault(UcConst.ERROR_10100001);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(2+'\002');
		System.out.println(4+2+"3"+4+1+5);
		
		String strA = ":6:7:8:9:10:"; 
		String arrA[] = strA.split(":"); 
		for(int i=0;i<arrA.length;i++) 
		{ 
		System.out.println(arrA[i]); 
		}
	}
}
