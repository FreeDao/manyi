package com.manyi.hims.distribute.service;

import com.manyi.hims.Response;
import com.manyi.hims.distribute.model.DistributeRequest;
import com.manyi.hims.employee.model.EmployeeModel;

public interface DistributeService {
	public Response distribute();
	public Response distribute(DistributeRequest distributeRequest);
	
	/**
	 * @date 2014年5月6日 下午20:50:38
	 * @author Tom  
	 * @description  
	 * 分配举报任务
	 */
	public Response distribute4Report(DistributeRequest distributeRequest);
		
	public Response pendingRecovery();
	public Response autoDistribute(EmployeeModel emp) ;
	public Response getDistributedHouseResource(int employeeId);
}
