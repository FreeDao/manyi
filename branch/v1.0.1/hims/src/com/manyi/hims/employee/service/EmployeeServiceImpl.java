package com.manyi.hims.employee.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.employee.EmployeeConst;
import com.manyi.hims.employee.model.EmployeeListResponse;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.employee.model.EmployeeResponse;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Employee_;
import com.manyi.hims.util.EntityUtils.DisabledEnum;
import com.manyi.hims.util.EntityUtils.RoleEnum;

@Service(value = "employeeService")
@Scope(value = "singleton")
public class EmployeeServiceImpl extends BaseService implements EmployeeService{
	
	public EmployeeResponse login (EmployeeModel employeeModel){
//		Employee employee = new Employee();
//		employee.setUserName("hhhh");
//		employee.setPassword("111");
//		employee.setEmployeeId(1);
//		
//		employeeModel.setId(1);
//		employeeModel.setRole(1);
		//employeeModel.setRole(2);
		//employeeModel.setRole(3);
		//employeeModel.setRole(4);
		
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Employee> cq_employee = cb.createQuery(Employee.class);
		Root<Employee> root_employee = cq_employee.from(Employee.class);
		cq_employee.where(cb.and(cb.equal(root_employee.get(Employee_.userName), employeeModel.getUserName())),
				cb.and(cb.equal(root_employee.get(Employee_.password), employeeModel.getPassword()))).select(root_employee);
		List<Employee> employeeList = getEntityManager().createQuery(cq_employee).getResultList();
		EmployeeResponse empResponse = null;
		if(employeeList!=null && employeeList.size()>0){
			Employee employee = employeeList.get(0);
			if(employee.getDisable()==true){
				throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101003);
			}
			empResponse = new EmployeeResponse();
//			empResponse.setId(employee.getEmployeeId());
//			empResponse.setUserName(employee.getUserName());
//			empResponse.setRole(employee.getRole());
//			empResponse.setuc
			BeanUtils.copyProperties(employee, empResponse);
			empResponse.setId(employee.getEmployeeId());
		}else{
			//
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101001);
		}
		return empResponse;
	}

	@Override
	public void addEmployee(EmployeeModel employee) {
		if(employee.getUserName().length()>30){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(StringUtils.isBlank(employee.getUserName())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(StringUtils.isBlank(employee.getPassword())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(StringUtils.isBlank(employee.getRealName())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(employee.getRole()==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Employee> cq_employee = cb.createQuery(Employee.class);
		Root<Employee> root_employee = cq_employee.from(Employee.class);
		cq_employee.where(cb.and(cb.equal(root_employee.get(Employee_.userName), employee.getUserName()))).select(root_employee);
		List<Employee> employeeList = getEntityManager().createQuery(cq_employee).getResultList();
		if(employeeList!=null && employeeList.size()>0){
			//
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101002);
		}else{
			Employee emp = new Employee();
			emp.setUserName(employee.getUserName());
			emp.setPassword(employee.getPassword());
			emp.setRole(employee.getRole());
			emp.setRealName(employee.getRealName());
			emp.setUcServerName(employee.getUcServerName());
			emp.setUcServerPwd(employee.getUcServerPwd());
			emp.setUcServerGroupId(employee.getUcServerGroupId());
			getEntityManager().persist(emp);
			
			//添加操作记录
//			EmployeeHistory history = new EmployeeHistory();
//			history.setEmployeeId(employeeId);
//			history.setAddTime(new Date());
//			history.setActionType(0);
//			getEntityManager().persist(history);
		}
	}
	
	@Override
	public void disableEmployee(EmployeeModel employee) {
		if(employee.getId()==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101004);
		}
		Employee emp = getEntityManager().find(Employee.class, employee.getId());
		if(emp.getDisable()==true){
			emp.setDisable(false);
		}else{
			emp.setDisable(true);
		}
		getEntityManager().merge(emp);
	}
	
	@Override
	public void updateEmployee(EmployeeModel employee) {
//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<Employee> cq_employee = cb.createQuery(Employee.class);
//		Root<Employee> root_employee = cq_employee.from(Employee.class);
//		cq_employee.where(cb.and(cb.equal(root_employee.get(Employee_.email), employee.getUserName()))).select(root_employee);
		if(employee.getId()==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101004);
		}
		Employee emp = getEntityManager().find(Employee.class, employee.getId());
		if(StringUtils.isNotBlank(employee.getPassword())){
			emp.setPassword(employee.getPassword());
		}
		if(employee.getRole()!=0){
			emp.setRole(employee.getRole());
		}
		if(StringUtils.isNotBlank(employee.getUcServerName())){
			emp.setUcServerName(employee.getUcServerName());
		}
		if(StringUtils.isNotBlank(employee.getUcServerPwd())){
			emp.setUcServerPwd(employee.getUcServerPwd());
		}
		if(StringUtils.isNotBlank(employee.getUcServerGroupId())){
			emp.setUcServerGroupId(employee.getUcServerGroupId());
		}
		getEntityManager().merge(emp);
	}
	
	@Override
	public Page<EmpResponse> getEmployee(Integer page, Integer rows) {
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> root  = cq.from(Employee.class);
		cq.orderBy(cb.desc(root.get(Employee_.addTime))).select(root);
		
		CriteriaQuery<Long> cq_count = cb.createQuery(Long.class);
		Root<Employee> count = cq_count.from(Employee.class);
		cq_count.select(cb.count(count));
		long counts = getEntityManager().createQuery(cq_count).getSingleResult();
		Page<EmpResponse> pages = new Page<EmpResponse>(page==null?1:page, rows==null?0:rows);
		if(counts<=0){
			pages.setRows(new ArrayList<EmpResponse>(0));
		}else
			pages.setTotal((int)counts);
		
		List<Employee> empList = getEntityManager().createQuery(cq).setFirstResult((page-1) * rows).setMaxResults(rows).getResultList();
		
		List<EmpResponse> infos = new ArrayList<EmpResponse>(empList.size());
		for (Employee e : empList) {
			EmpResponse response = new EmpResponse();
			BeanUtils.copyProperties(e, response);
			response.setRoleStr(RoleEnum.getByValue(response.getRole()).getRoleName());
			response.setDisabledStr(DisabledEnum.getByValue(response.isDisable()).getDesc());
			infos.add(response);
		}
		//page.setRows(infos.subList(page.getFirstResultIndex(), page.getFirstResultIndex()+page.getMaxResultLenght()));
		pages.setRows(infos);
		return pages;
	}
	
	/**
	 * @date 2014年5月4日 上午10:36:31
	 * @author Tom  
	 * @description  
	 * 根据roleId 获取员工列表
	 */
	private List<EmployeeModel> getEmployeeModelList(int roleId) {
		String  jpql = "from Employee as emp where emp.role=:roleId and emp.disable=false";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter("roleId", roleId);
		List<Employee> custerList = query.getResultList();
		
		List<EmployeeModel> custServices = new ArrayList<EmployeeModel>(custerList.size());
		for(Employee e : custerList){
			EmployeeModel model = new EmployeeModel();
			model.setId(e.getEmployeeId());
			model.setUserName(e.getUserName());
			model.setRealName(e.getRealName());
			model.setRole(e.getRole());
			custServices.add(model);
		}
		
		return custServices;

	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeListResponse getEmployeeList() {
		/*String  jpql = "from Employee as emp where emp.role=3 and emp.disable=false";
		Query query = getEntityManager().createQuery(jpql);
		List<Employee> custerList = query.getResultList();
		
		List<EmployeeModel> custServices = new ArrayList<EmployeeModel>(custerList.size());
		for(Employee e : custerList){
			EmployeeModel model = new EmployeeModel();
			model.setId(e.getEmployeeId());
			model.setUserName(e.getUserName());
			model.setRealName(e.getRealName());
			model.setRole(e.getRole());
			custServices.add(model);
		}
		
		String  floor = "from Employee as emp where emp.role=5 and emp.disable=false";
		Query floorQuery = getEntityManager().createQuery(floor);
		List<Employee> floorList = floorQuery.getResultList();
		
		List<EmployeeModel> floorServices = new ArrayList<EmployeeModel>(floorList.size());
		for(Employee e : floorList){
			EmployeeModel model = new EmployeeModel();
			model.setId(e.getEmployeeId());
			model.setUserName(e.getUserName());
			model.setRealName(e.getRealName());
			model.setRole(e.getRole());
			floorServices.add(model);
		}*/
		EmployeeListResponse result = new EmployeeListResponse();
		result.setCustServices(getEmployeeModelList(3));
		result.setFloorServices(getEmployeeModelList(5));
		return result;
	}

	
	/**
	 * @date 2014年5月4日 上午10:38:16
	 * @author Tom  
	 * @description  
	 * 根据roleId查询员工列表
	 */
	public EmployeeListResponse getEmployeeList(int roleId) {
	
		EmployeeListResponse result = new EmployeeListResponse();
		result.setFloorServices(getEmployeeModelList(roleId));

		return result;
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
