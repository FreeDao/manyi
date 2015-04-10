package com.manyi.hims.employee.service;

import java.util.ArrayList;
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
import com.manyi.hims.employee.controller.EmployeeController.Result;
import com.manyi.hims.employee.model.EmployeeListResponse;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.employee.model.EmployeeResponse;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Employee_;
import com.manyi.hims.entity.Province;
import com.manyi.hims.util.EntityUtils.DisabledEnum;
import com.manyi.hims.util.EntityUtils.RoleEnum;

@Service(value = "employeeService")
@Scope(value = "singleton")
public class EmployeeServiceImpl extends BaseService implements EmployeeService{
	
	public EmployeeResponse login (EmployeeModel employeeModel){
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
			BeanUtils.copyProperties(employee, empResponse);
			empResponse.setId(employee.getEmployeeId());
		}else{
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
			//emp.setCityId(employee.getCityId());
			emp.setCityId(employee.getAreaId());//城市ID
			if(employee.getAreaId() > 0) {
				Area area = getEntityManager().find(Area.class, employee.getAreaId());
				emp.setCityName(area == null ? null : area.getName());
			}else {
				emp.setCityName("全部");
			}
			emp.setAreaId(employee.getCityId());//行政区域ID
			if(employee.getCityId() > 0) {
				Area area = getEntityManager().find(Area.class, employee.getCityId());
				emp.setAreaName(area == null ? null : area.getName());
			}else {
				emp.setAreaName("全部");
			}
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
//		if(StringUtils.isNotBlank(employee.getUcServerName())){
			emp.setUcServerName(employee.getUcServerName());
//		}
//		if(StringUtils.isNotBlank(employee.getUcServerPwd())){
			emp.setUcServerPwd(employee.getUcServerPwd());
//		}
//		if(StringUtils.isNotBlank(employee.getUcServerGroupId())){
			emp.setUcServerGroupId(employee.getUcServerGroupId());
//		}
		emp.setCityId(employee.getCityId());//城市
		emp.setAreaId(employee.getAreaId());//行政区
		if(employee.getCityId() > 0) {
			Area area = getEntityManager().find(Area.class, employee.getCityId());
			emp.setCityName(area == null ? null : area.getName());
		}else {
			emp.setCityName("全部");
		}
		if(employee.getAreaId() > 0) {
			Area town = getEntityManager().find(Area.class, employee.getAreaId());
			emp.setAreaName(town == null ? null : town.getName());
		}else {
			emp.setAreaName("全部");
		}
		getEntityManager().merge(emp);
	}
	
	@Override
	public Page<EmpResponse> getEmployee(Integer page, Integer rows) {
		
/*		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
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
//		String hql = "select area.name,area1.name from Area area,Area area1 where area.areaId=? and area1.areaId=?";
//		Query query = getEntityManager().createQuery(hql);
		for (Employee e : empList) {
//			query.setParameter(1, e.getAreaId());
//			query.setParameter(2, e.getCityId());
			
			EmpResponse response = new EmpResponse();
			BeanUtils.copyProperties(e, response);
			response.setRoleStr(RoleEnum.getByValue(response.getRole()).getRoleName());
			response.setDisabledStr(DisabledEnum.getByValue(response.isDisable()).getDesc());
			infos.add(response);
		}
		//page.setRows(infos.subList(page.getFirstResultIndex(), page.getFirstResultIndex()+page.getMaxResultLenght()));
		pages.setRows(infos);*/
		
		String sql_count ="select count(e.employeeId) from Employee e ";
		String sql ="select e.employeeId,e.userName,e.email,e.disable,e.realName,e.role,e.ucServerName,e.ucServerPwd,e.ucServerGroupId,e.cityId,e.areaName areaName,e.areaId ,e.cityName cityName "
				+ "from Employee e "
				+ "order by e.addTime desc ";
		Object countObj = this.getEntityManager().createNativeQuery(sql_count).getSingleResult();
		int counts = 0;
		if(countObj != null){
			counts = DataUtil.toInt(countObj);
		}
		Page<EmpResponse> pages = new Page<EmpResponse>(page==null?1:page, rows==null?0:rows);
		if(counts<=0){
			pages.setRows(new ArrayList<EmpResponse>(0));
		}else
			pages.setTotal((int)counts);
		
		List<Object[]> objs = getEntityManager().createNativeQuery(sql).setFirstResult((page-1) * rows).setMaxResults(rows).getResultList();
		
		
		if (objs != null && objs.size() > 0) {
			List<EmpResponse> infos = new ArrayList<EmpResponse>(objs.size());
			for (Object[] row : objs) {
				int i=0;
				EmpResponse response = new EmpResponse();
				response.setEmployeeId(Integer.parseInt(row[i++] + ""));
				response.setUserName(row[i++] + "");
				response.setEmail(row[i++] + "");
				response.setDisable(Boolean.parseBoolean(row[i++] + ""));
				response.setRealName(row[i++] + "");
				response.setRole(Integer.parseInt(row[i++] + ""));
				response.setUcServerName(row[i++] + "");
				response.setUcServerPwd(row[i++] + "");
				response.setUcServerGroupId(row[i++] + "");
				response.setCityId(Integer.parseInt(row[i++] + ""));
				response.setAreaName(((row[i] == null || "".equals(row[i]+"")) ? "全部" : row[i]+ ""));
				i++;
				response.setAreaId(Integer.parseInt(row[i++] + ""));
				response.setCityName(((row[i] == null || "".equals(row[i]+"")) ? "全部" : row[i]+ ""));
				i++;
				response.setRoleStr(RoleEnum.getByValue(response.getRole()).getRoleName());
				response.setDisabledStr(DisabledEnum.getByValue(response.isDisable()).getDesc());
				infos.add(response);
			}
			pages.setRows(infos);
		}
		
		
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
	
	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public Result getCityByParentId(int parentId) {
		String jpql = "from Area area where area.parentId=?";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter(1, parentId);
		List<Area> areaList = query.getResultList();
		
		List<AreaResponse> infos = new ArrayList<AreaResponse>();
		Result result = new Result();
		for (int i = 0; i < areaList.size(); i++) {
			AreaResponse response = new AreaResponse();
			BeanUtils.copyProperties(areaList.get(i), response);
			infos.add(response);
		}
		result.setList(infos);
		return result;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public Result getProvinde() {
		String jpql = "from Province";
		Query query = getEntityManager().createQuery(jpql);
		List<Province> provinceList = query.getResultList();
		
		List<AreaResponse> infos = new ArrayList<AreaResponse>();
		Result result = new Result();
		for (int i = 0; i < provinceList.size(); i++) {
			AreaResponse response = new AreaResponse();
			BeanUtils.copyProperties(provinceList.get(i), response);
			infos.add(response);
		}
		result.setList(infos);
		return result;
	}
	
	
	
	
	
	
	
}
