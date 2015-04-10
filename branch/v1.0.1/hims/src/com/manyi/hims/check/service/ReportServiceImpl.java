package com.manyi.hims.check.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.Response;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.User;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.service.PayService;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;
import com.manyi.hims.util.PushUtils;

@Service(value = "reportService")
@Scope(value = "singleton")

public class ReportServiceImpl extends BaseService implements ReportService {

	@Autowired
	HouseResourceService houseResourceService;
	
	@Autowired
	PayService payService;
	
	@Autowired
	PushUtils pushUtils;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	CustServService custServService;
	
	/**
	 * @date 2014年4月30日 下午5:30:15
	 * @author Tom  
	 * @description  
	 * 举报:	审核通过
	 * 
	 * 操作步骤如下：
	 * 1、更新houseResourceHistory原有对象为checkNum=0 的对象状态
	 * 2、将houseResource数据写入 houseResourceHistory
	 * 3、更新houseResource对象
	 * 4、删除houseResourceTemp对象
	 * 5、调用支付接口
	 * 6、调用推送接口
	 * 7、调用显示app端记录数字接口
	 * 8、将房子的联系人归档，置为 3
	 */
	public Response auditSuccess(FloorRequest floorRequest) {

		//1、
		houseResourceService.updateHouseResourceHistory(floorRequest, StatusEnum.SUCCESS.getValue(), HouseStateEnum.NEITHER.getValue());
		
		HouseResource houseResource = houseResourceService.getHouseResourceByHouseId(floorRequest.getHouseId());
		
//		2、将houseResource数据写入 houseResourceHistory
		houseResourceService.copyHouseResource2History(houseResource, floorRequest.getLookNote(), floorRequest.getEmployeeId(), StatusEnum.SUCCESS.getValue(), HouseStateEnum.NEITHER.getValue());
		
//		设置属性
		houseResource.setStatus(StatusEnum.SUCCESS.getValue());
		houseResource.setOperatorId(0);
		houseResource.setHouseState(HouseStateEnum.NEITHER.getValue());
		houseResource.setCheckNum(0);
		houseResource.setResultDate(new Date());
		
//		3、更新houseResource对象
		houseResourceService.mergeHouseResource(houseResource);
		
//		4、删除houseResourceTemp对象
		houseResourceService.removeHouseResourceTemp(floorRequest.getHouseId());

//		5、调用支付接口
		AddPayReq req = new AddPayReq(houseResource.getUserId(), EntityUtils.AwardTypeEnum.REPORT.getValue());
		payService.addPay(req);
		
//		6、调用推送接口
		sendPushMsg(houseResource,"成功");
		
//		7、调用显示app端记录数字接口
		commonService.updateUserCreateLogCount(houseResource.getUserId());
		
//		8、将房子的联系人归档，置为 3
		custServService.disableContactByHouseId(floorRequest.getHouseId(), HouseStateEnum.RENT.getValue());
		custServService.disableContactByHouseId(floorRequest.getHouseId(), HouseStateEnum.SELL.getValue());
		
		return new Response();
	}

	/**
	 * @date 2014年4月30日 下午5:30:15
	 * @author Tom  
	 * @description  
	 * 举报:	审核失败
	 * 
	 * 操作步骤如下：
	 * 1、更新houseResourceHistory原有对象为checkNum=0 的对象状态
	 * 2、将houseResource数据写入 houseResourceHistory
	 * 3、将houseResourceTemp对象还原，到houseResource对象
	 * 4、删除houseResourceTemp对象
	 * 5、调用推送接口
	 * 6、调用显示app端记录数字接口
	 * 
	 */
	public Response auditFail(FloorRequest floorRequest) {
		HouseResourceTemp houseResourceTemp = houseResourceService.getHouseResourceTemp(floorRequest.getHouseId());
		
		//1、
		houseResourceService.updateHouseResourceHistory(floorRequest, StatusEnum.FAILD.getValue(), houseResourceTemp.getHouseState());
				
		HouseResource houseResource = houseResourceService.getHouseResourceByHouseId(floorRequest.getHouseId());
		
//		2、将houseResource数据写入 houseResourceHistory
		houseResourceService.copyHouseResource2History(houseResource, floorRequest.getLookNote(), floorRequest.getEmployeeId(), StatusEnum.FAILD.getValue(), houseResourceTemp.getHouseState());
		
//		3、将houseResourceTemp对象还原，到houseResource对象
//		设置houseResource属性
		houseResource.setActionType(houseResourceTemp.getActionType());
		houseResource.setStatus(houseResourceTemp.getStatus());
		houseResource.setOperatorId(0);
		houseResource.setResultDate(new Date());
		houseResource.setCheckNum(0);
		houseResource.setRemark(houseResourceTemp.getRemark());
		
//		更新houseResource对象
		houseResourceService.mergeHouseResource(houseResource);
		
//		4、删除houseResourceTemp对象
		houseResourceService.removeHouseResourceTemp(floorRequest.getHouseId());
		
//		5、调用推送接口
		sendPushMsg(houseResource,"失败");
		
//		6、调用显示app端记录数字接口
		commonService.updateUserCreateLogCount(houseResource.getUserId());
		
		return new Response();
	}

	private void sendPushMsg(HouseResource houseResource,String result) {
		User user = getEntityManager().find(User.class, houseResource.getUserId());
		House house = getEntityManager().find(House.class, houseResource.getHouseId());
		Estate estate = getEntityManager().find(Estate.class, house.getEstateId());
		String buildingStr = "";
		if(!house.getBuilding().equals("0")){
			buildingStr = house.getBuilding()+"栋";
		}
		String msg = estate.getName()+buildingStr+house.getRoom()+"室";
		pushUtils.sendReportCheckPushMsg(user, msg,result);
	}

	 
 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
