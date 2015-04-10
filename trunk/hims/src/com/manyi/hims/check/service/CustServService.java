package com.manyi.hims.check.service;

import com.manyi.hims.Response;
import com.manyi.hims.check.model.AntiCheatRequest;
import com.manyi.hims.check.model.AntiCheatResponse;
import com.manyi.hims.check.model.CSCheckNumResponse;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.check.model.CommitCheckAllRequest;
import com.manyi.hims.check.model.IsShanghaiResponse;
import com.manyi.hims.employee.model.EmployeeModel;

public interface CustServService {
	
	public Response getSingle(CSSingleRequest css);
	
	public Response submitCheckAll(CommitCheckAllRequest ccar);
	
	/**
	 * @date 2014年6月12日 上午10:52:45
	 * @author Tom
	 * @description  
	 * @param note 为理由
	 */
	public Response setLunXunSuccess(int houseId, String note);
	
	/**
	 * @date 2014年6月12日 上午10:52:45
	 * @author Tom
	 * @description  
	 * @param note 为理由
	 */
	public Response checkLunXunAndSetLunXunSuccess(int houseId, String note);
	
	public CSCheckNumResponse getCheckNum(EmployeeModel employee);
	
	public IsShanghaiResponse isShanghai(String mobile);
	
	public AntiCheatResponse antiCheat(AntiCheatRequest anti);
	
	public void disableContactByHouseId(int houseId ,int type);

}
