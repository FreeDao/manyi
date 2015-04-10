package com.manyi.hims.bd.service;

import java.util.ArrayList;
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

import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.bd.BdConst;
import com.manyi.hims.bd.controller.BdController.UpdateHouseRequest;
import com.manyi.hims.bd.service.EmployeeService.HouseResourceContantResponse;
import com.manyi.hims.employee.EmployeeConst;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Employee_;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.Town;
import com.manyi.hims.util.EntityUtils.RoleEnum;
@Service(value = "employeeService_db")
@Scope(value = "singleton")
public class EmployeeServiceImpl extends BaseService implements EmployeeService{
	@Override
	public BdLoginResponse login(String userName, String password) {
		if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101001);
		}
//		if(!DataUtil.checkMobile(userName)){
//			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101001);
//		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Employee> cq_employee = cb.createQuery(Employee.class);
		Root<Employee> root_employee = cq_employee.from(Employee.class);
		cq_employee.where(cb.and(cb.equal(root_employee.get(Employee_.userName), userName)),
				cb.and(cb.equal(root_employee.get(Employee_.password), password))).select(root_employee);
		List<Employee> employeeList = getEntityManager().createQuery(cq_employee).getResultList();
		BdLoginResponse result = new BdLoginResponse();
		if(employeeList!=null && employeeList.size()>0){
			Employee employee = employeeList.get(0);
			result.setUserName(employee.getUserName());
			result.setEmployeeId(employee.getEmployeeId());
			/**
			 * 只有地推经理和地推专员可以登录
			 */
			if(!(employee.getRole()==RoleEnum.FLOOR_MEMBER_ROLE.getRoleNum() || employee.getRole()==RoleEnum.FLOOR_MANAGER_ROLE.getRoleNum())){
				throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101006);
			}
			if(employee.getDisable()==true){
				throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101003);
			}
		}else{
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101001);
		}
		return result;
	}

	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public TaskDetailsResponse taskDetails(int taskId) {
		TaskDetailsResponse result = new TaskDetailsResponse();
		if(taskId==0){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		/**
		 * 任务
		 */
		BdTask task = getEntityManager().find(BdTask.class, taskId);
		if(task==null){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		/**
		 * 住宅
		 */
		Residence house = getEntityManager().find(Residence.class, task.getHouseId());
		if(house==null){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		
		/**
		 * 房屋状态
		 */
		HouseResource houseResource = getEntityManager().find(HouseResource.class, house.getHouseId());
		if(houseResource==null){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		/**
		 * 小区
		 */
		Estate estate = getEntityManager().find(Estate.class, house.getEstateId());
		if(estate==null){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		/**
		 * 地址
		 */
		//String jpql_address = "from Address ads where ads.estateId=?";
		String sql = "SELECT address.estateId AS estateId, GROUP_CONCAT(address.address SEPARATOR '/') AS address  FROM  Address address where address.estateId=? GROUP BY address.estateId";
		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter(1, estate.getAreaId());
		List<Object[]> addressList = query.getResultList();
		String address = null;
		if(addressList!=null || addressList.size()>0){
			Object[] rows  = addressList.get(0);
			address = rows[1].toString();
		}
		if(address==null){
			throw new LeoFault(BdConst.BD_ERROR140001);
		}
		/**
		 * 联系人信息
		 */
		String jqpl_houseContant = "from HouseResourceContact host where host.houseId=? and host.enable = true";
		Query query_houstContant = getEntityManager().createQuery(jqpl_houseContant);
		query_houstContant.setParameter(1, house.getHouseId());
		List<HouseResourceContact> hostList = query_houstContant.getResultList();
		List<HouseResourceContantResponse> infos = new ArrayList<HouseResourceContantResponse>();
		if(hostList!=null || hostList.size()>0){
			for (int i = 0; i < hostList.size(); i++) {
				HouseResourceContantResponse host = new HouseResourceContantResponse();
				host.setHostMobile(hostList.get(i).getHostMobile());
				host.setHostName(hostList.get(i).getHostName());
				infos.add(host);
			}
		}
		BeanUtils.copyProperties(task, result);
		BeanUtils.copyProperties(estate, result);
		BeanUtils.copyProperties(houseResource, result);
		BeanUtils.copyProperties(house, result);
//		result.setTaskDate(new Date());
//		if(houseResource.getHouseState()==1){
//			result.setRentPrice(houseResource.getRentPrice());
//		}else if(houseResource.getHouseState()==2){
//			result.setSellPrice(houseResource.getSellPrice());
//		}else if(houseResource.getHouseState()==3){
//			result.setRentPrice(houseResource.getRentPrice());
//			result.setSellPrice(houseResource.getSellPrice());
//		}
		result.setHostList(infos);
		result.setAddress(address);
		return result;
	}
	@Override
	public void updateHouse(UpdateHouseRequest pars) {
		// TODO Auto-generated method stub
		
	}
}
