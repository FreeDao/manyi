package com.manyi.hims.check.service;

import com.manyi.hims.Response;
import com.manyi.hims.check.model.CSCheckNumResponse;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.check.model.CommitCheckAllRequest;
import com.manyi.hims.check.model.IsShanghaiResponse;
import com.manyi.hims.employee.model.EmployeeModel;

public interface CustServService {
	
	public Response getSingle(CSSingleRequest css);
	
	public Response submitCheckAll(CommitCheckAllRequest ccar);
	
	public CSCheckNumResponse getCheckNum(EmployeeModel employee);
	
	public IsShanghaiResponse isShanghai(String mobile);
	
	public void disableContactByHouseId(int houseId ,int type);

}
