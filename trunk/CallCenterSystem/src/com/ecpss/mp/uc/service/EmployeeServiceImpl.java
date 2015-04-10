package com.ecpss.mp.uc.service;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.Employee;
import com.ecpss.mp.uc.UcConst;
import com.leo.jaxrs.fault.LeoFault;

@Service(value = "employeeService")
@Scope(value = "singleton")
public class EmployeeServiceImpl extends BaseService implements EmployeeService {
	/**
	 * 员工 登录
	 */
	public Employee login(String loginName,String password){
		final String jpql = "from Employee as me where me.userName=? and me.password=?";
		Query loginQuery = getEntityManager().createQuery(jpql);
		loginQuery.setParameter(1, loginName);
		loginQuery.setParameter(2, password);
		try{
			return (Employee) loginQuery.getSingleResult();
		}catch(NoResultException e){
			throw new LeoFault(UcConst.ERROR_10100001);
		}
	}
	

}
