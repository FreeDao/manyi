package com.manyi.hims.check.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.check.cons.custConstants;
import com.manyi.hims.check.model.AntiCheatRequest;
import com.manyi.hims.check.model.AntiCheatResponse;
import com.manyi.hims.check.model.AntiCheatResponse.AntiCheat;
import com.manyi.hims.check.model.CSCheckNumResponse;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.check.model.CSSingleResponse;
import com.manyi.hims.check.model.IsShanghaiResponse;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceContactResponse;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceHistoryResponse;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceResponse;
import com.manyi.hims.check.model.CSSingleResponse.HouseResponse;
import com.manyi.hims.check.model.CommitCheckAllRequest;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.MobileLocation;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.util.PayUtil;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.PushUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.HouseStateResonEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;

@Service("custServService")
public class CustServServiceImpl extends BaseService implements CustServService, custConstants{
    
	@Autowired
	private PushUtils pushUtils;
	
	public Response getSingle(CSSingleRequest css){
		logger.info("houseResourceId  : {}" ,css.getId());

		HouseResource hourseResource = getEntityManager().find(HouseResource.class ,css.getId());
		if (hourseResource == null) {
			return new Response(1586002, "查无此记录");
		}
		
		HouseResponse h = new HouseResponse();
		HouseResourceResponse hrr = new HouseResourceResponse();
		List<HouseResourceContactResponse> hrcrList = new ArrayList<HouseResourceContactResponse>();
		List<HouseResourceHistoryResponse> slhrList = new ArrayList<HouseResourceHistoryResponse>();
		
		Residence house = getEntityManager().find(Residence.class,css.getId());
		logger.info("house id:{}",house.getHouseId());
		List<HouseResourceContact> shList = getHostListBySourceLogId(css.getId(),hourseResource);
		
		if (shList != null && shList.size() > 0) {
			for (HouseResourceContact  sh : shList) {
				HouseResourceContactResponse shr = new HouseResourceContactResponse();
				BeanUtils.copyProperties(sh,shr);
				hrcrList.add(shr);
			}
			
		}else {
			hrcrList = null;
		}
		
		BeanUtils.copyProperties(hourseResource, hrr);
		BeanUtils.copyProperties(house, h);
		
		if ( hourseResource.getUserId() != 0) {
			User user = getEntityManager().find(User.class, hourseResource.getUserId());
			logger.info("userName 经纪人姓名: {}" ,(user == null ? "无" : user.getRealName()));
			if (user != null) {
				
				hrr.setUserName(user.getRealName());
				hrr.setUserMobile(user.getMobile());
				hrr.setDoorName(user.getDoorName());
			}
			
			
		}
		if (hrr.getOperatorId() > 0) {
			Employee emp = getEntityManager().find(Employee.class, hrr.getOperatorId());
			if (emp != null ) {
				hrr.setOperatorName(emp.getRealName());
			}
		}
		hrr.setHouseStateStr(HouseStateEnum.getByValue(hrr.getHouseState()).getDesc());
		hrr.setActionTypeStr(ActionTypeEnum.getByValue(hrr.getActionType()).getDesc());
		hrr.setStatusStr(StatusEnum.getByValue(hrr.getStatus()).getDesc());
		hrr.setStateReasonStr(HouseStateResonEnum.getByValue(hrr.getStateReason()).getDesc());

		Query query = getEntityManager().createNativeQuery(
				"select Estate.road , Estate.areaId EstateAreaId, Estate.name EstateName, "
				+ "Town.areaId TownAreaId, Town.name TownName, City.areaId CityAreaId, City.name CityName ,Province.areaId ProvinceAreaId, Province.name ProvinceName from "
				+ "Area Estate inner join Area Town  on Estate.parentId=Town.areaId inner join Area City  on Town.parentId=City.areaId "
				+ " inner join Area Province  on City.parentId=Province.areaId WHERE Estate.areaId=:areaId  "
				);
			query.setParameter("areaId" , house.getEstateId());
			Object obj = query.getSingleResult();
			if ( obj instanceof   Object[] ){  
				//如果为true则强转成String数组  
				Object [] arr = ( Object[] ) obj ;  
				logger.debug(arr.toString());
				if (house != null) {
					
					h.setEstateName(arr[2] == null ? "" : (String)arr[2]);
					h.setSubEstateName(h.getEstateName() +(arr[0] == null ? "" : (String)arr[0]));
					h.setTownName(arr[4] == null ? null : (String)arr[4]);
					h.setCityName(arr[6] == null ? null : (String)arr[6]);
					h.setProvinceName(arr[8] == null ? null : (String)arr[8]);
					
				}
			}
			SubEstate subEstate = getEntityManager().find(SubEstate.class, house.getSubEstateId());
			if (subEstate != null) {
				h.setSubEstateId(subEstate.getId());
				h.setSubEstateStr(subEstate.getName());
			}
			
		
		Response response = new CSSingleResponse(hrcrList,h,hrr,slhrList);
		System.out.println("000000000------>>" + hrr.getPublishDate().toString());
		
		return response;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	private List<HouseResourceContact> getHostListBySourceLogId(int houseId,HouseResource hr){
		
		if (hr.getActionType() == ActionTypeEnum.PUBLISH.getValue() && hr.getStatus() != StatusEnum.FAILD.getValue()) {

			Query query = getEntityManager().createQuery(
					"select hrh from HouseResourceHistory hrh where hrh.houseId=:houseId and hrh.status = 2 and checkNum=0");

			query.setParameter("houseId", houseId);
			HouseResourceHistory hrhBefore = null;// may be null exception
			Object objRes = null;
			try {
				objRes = query.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("find no result {}", e);
			}

			if (objRes != null) {
				hrhBefore = (HouseResourceHistory) objRes;

				query = getEntityManager().createQuery(
						"select sh from HouseResourceContact sh WHERE sh.historyId=:historyId and sh.enable=true and sh.status != 3");
				query.setParameter("historyId", hrhBefore.getHistoryId());
				List<HouseResourceContact> list = query.getResultList();
				return list;
			} else {
				logger.info("find no result {}", houseId);
				return null;
			}
		} else {

			Query query = getEntityManager().createQuery(
					"select sh from HouseResourceContact sh WHERE sh.houseId=:houseId and sh.enable=true and sh.status != 3");
			query.setParameter("houseId", houseId);
			List<HouseResourceContact> list = query.getResultList();
			return list;
		}
	}
	

	//格式化操作
	private String formatText(String format,Object... args) {
		return String.format(sell_format,args);
	}


	public Response submitCheckAll(CommitCheckAllRequest ccar) {
		logger.info("submit check parameter:{}",ReflectionToStringBuilder.toString(ccar));
		HouseResource hr = getEntityManager().find(HouseResource.class, ccar.getHouseId());
		if (hr.getStatus() != StatusEnum.ING.getValue()) {
			logger.info("submit the check unused:{}",ccar.getHouseId());
			return new Response(0,"重复审核，无效");
		}
		//查询经纪人发起的任务日志 checkNum=0 且 status =2 后面需要更新掉这个第0条记录
		Query query = getEntityManager().createQuery("select hrh from HouseResourceHistory hrh where hrh.houseId=:houseId and hrh.status = 2 and checkNum=0");
		query.setParameter("houseId", hr.getHouseId());
		HouseResourceHistory hrhBefore = null;//may be null exception
		Object objRes = null;
		try {
			objRes = query.getSingleResult() ;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("find no result {}",e);
		}
		
		if (objRes != null ) {
			hrhBefore = (HouseResourceHistory)objRes;
		}
		ResidenceResourceHistory hrh = new ResidenceResourceHistory();
		BeanUtils.copyProperties(hr, hrh);
		//hrh.setHouseId(hr.getHouseId());
		
		HouseResourceTemp hrt = getEntityManager().find(HouseResourceTemp.class, ccar.getHouseId());
		
		Residence residence = getEntityManager().find(Residence.class, ccar.getHouseId());
		
		hrh.setNote(ccar.getNote());
		hrh.setOperatorId(hr.getOperatorId());
		hr.setOperatorId(0);
		
		hr.setCheckNum(hr.getCheckNum() + 1);
		hrh.setCheckNum(hr.getCheckNum());
		//审核中
		if (ccar.getStatus() == StatusEnum.ING.getValue() ) {
			//需要再次审核
			hr.setStatus(ccar.getStatus());
			hrh.setStatus(ccar.getStatus());
			
			if (hr.getCheckNum() >= Global.CHECK_AGAIN_TIMES) {
				ccar.setStatus(StatusEnum.FAILD.getValue());
				//hr.setStatus(ccar.getStatus());
				//hrh.setStatus(ccar.getStatus());
				//GOTO failed case if (ccar.getStatus() == StatusEnum.FAILD.getValue() ) 
			} else {
				hr.setResultDate(new Date());
				hrh.setResultDate(new Date());
			}
		}

		//审核失败
		if (ccar.getStatus() == StatusEnum.FAILD.getValue() ) {
			//审核失败
			//标记为失败删除
			if (hr.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
				if (hrhBefore != null) {
					/*query = getEntityManager().createQuery(
							"update HouseResourceContact sh set sh.status = 3 ,sh.backTime =:backTime WHERE sh.historyId=:historyId and sh.enable=true and sh.status != 3");
					query.setParameter("backTime", new Date());
					query.setParameter("historyId", hrhBefore.getHistoryId());
					query.executeUpdate();*/
					query = getEntityManager().createQuery("select sh from HouseResourceContact sh  WHERE sh.historyId=:historyId and sh.enable=true and sh.status != 3");
					//set sh.status = 3 ,sh.backTime =:backTime
					query.setParameter("historyId", hrhBefore.getHistoryId());
					List<HouseResourceContact> hrc = query.getResultList();
					if (hrc != null && hrc.size() > 0) {
						for (int i = 0; i < hrc.size(); i++) {
							hrc.get(i).setStatus(3);
							hrc.get(i).setBackTime(new Date());
							getEntityManager().merge(hrc.get(i));
						}
					}
					
					
				}
			}
			
			//house不变 直接回滚houseResource，状态置为failed
			if (hrt != null) {
				//如果temp中有数据 则恢复以前的状态
				BeanUtils.copyProperties(hrt, hr);
				residence.setBalconySum(hrt.getBalconySum());
				residence.setBedroomSum(hrt.getBedroomSum());
				residence.setLivingRoomSum(hrt.getLivingRoomSum());
				residence.setWcSum(hrt.getWcSum());
				residence.setSpaceArea(hrt.getSpaceArea());
				getEntityManager().merge(residence);
				getEntityManager().remove(hrt);//删除 temp
				
				if (hr.getStatus() == StatusEnum.ING.getValue()) {
					logger.info("error data restored to change status is {}" , hr.getHouseId());
					hr.setStatus(StatusEnum.FAILD.getValue());
				}
			}else {
				//temp中没有数据，则标记审核失败
				//hr.setResultDate(new Date());
				hr.setStatus(StatusEnum.FAILD.getValue());
				hr.setHouseState(HouseStateEnum.NEITHER.getValue());
			}
			
			hrh.setStatus(ccar.getStatus());
			hrh.setResultDate(new Date());
			hr.setCheckNum(0);
			//added by howard 
			hr.setResultDate(new Date());
			
		}else if (ccar.getStatus() == StatusEnum.SUCCESS.getValue() ) {
			
			
			hr.setStatus(ccar.getStatus());
			hrh.setStatus(ccar.getStatus());
			hr.setResultDate(new Date());
			hrh.setResultDate(new Date());
			hr.setCheckNum(0);
			if (hr.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
				
				
				if (HouseStateEnum.SELL.getValue() == hr.getHouseState()) {
					hr.setSellPrice(ccar.getSellPrice());
					hrh.setSellPrice(ccar.getSellPrice());
					
				}else if (HouseStateEnum.RENT.getValue() == hr.getHouseState()) {
					hr.setRentPrice(ccar.getRentPrice());
					hrh.setRentPrice(ccar.getRentPrice());
					//出租的时候. 添加  可入住 时间  / 租期(到期时间)
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						if(StringUtils.isNotBlank(ccar.getRentFreeDate())){
							hr.setRentFreeDate(sdf.parse(ccar.getRentFreeDate()));
							hrh.setRentFreeDate(hr.getRentFreeDate());
						}
						if(StringUtils.isNotBlank(ccar.getRentTermDate())){
							hr.setRentTermDate(sdf.parse(ccar.getRentTermDate()));
							hrh.setRentTermDate(hr.getRentTermDate());
						}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}/*else if (HouseStateEnum.RENTANDSELL.getValue() == hr.getHouseState()) {
					logger.info("now unreached case houseId :{}" ,hr.getHouseId());
					hr.setRentPrice(ccar.getRentPrice());
					if (hr.getRentPrice() != null && hr.getRentPrice().compareTo(ccar.getRentPrice()) != 0) {
						hrh.setRentPrice(ccar.getRentPrice());
					}
					hr.setSellPrice(ccar.getSellPrice());
					if (hr.getSellPrice() .compareTo(ccar.getSellPrice()) != 0) {
						hrh.setSellPrice(ccar.getSellPrice());
					}
				}*/
				
				//切换状态
				if (hrt != null ) {
					if ((HouseStateEnum.RENT.getValue() == hrt.getHouseState() &&
							HouseStateEnum.SELL.getValue() == hr.getHouseState())
							 || 
							(HouseStateEnum.SELL.getValue() == hrt.getHouseState() &&
									HouseStateEnum.RENT.getValue() == hr.getHouseState())) {
						hr.setHouseState(HouseStateEnum.RENTANDSELL.getValue());
					}
				}
			
				//审核成功的话 有需要的话再将residence更新
				if (residence.getBedroomSum() == ccar.getBedroomSum() && 
						residence.getLivingRoomSum() == ccar.getLivingRoomSum() &&
						residence.getWcSum() == ccar.getWcSum() &&
						residence.getSpaceArea().compareTo( ccar.getSpaceArea()) == 0
						) {
					// 暂时不需处理
				}else {
					//copy to HouseHistory...
					if (residence.getBedroomSum() != ccar.getBedroomSum()) {
						residence.setBedroomSum(ccar.getBedroomSum());
					}
					if (residence.getLivingRoomSum() != ccar.getLivingRoomSum()) {
						residence.setLivingRoomSum( ccar.getLivingRoomSum());
					}
					if (residence.getWcSum() != ccar.getWcSum()) {
						residence.setWcSum(ccar.getWcSum());
					}
					if (residence.getSpaceArea().compareTo( ccar.getSpaceArea()) != 0) {
						residence.setSpaceArea(ccar.getSpaceArea());
					}
					ResidenceHistory hh = new ResidenceHistory();
					BeanUtils.copyProperties(residence, hh);
					
					getEntityManager().persist(hh);
					getEntityManager().merge(residence);
				}
			}else if (hr.getActionType() == ActionTypeEnum.CHANGE.getValue()) {
				if (hr.getHouseState() == HouseStateEnum.RENT.getValue() || 
						hr.getHouseState() == HouseStateEnum.SELL.getValue() ) {
					
					/*if (hr.getStateReason() == HouseStateResonEnum.HAD_RENTED.getValue()  ) {
						
						//改盘时候 如果已经出租或已经出售 则归档用户
						disableContact(hrhBefore ,HouseStateEnum.RENT.getValue());
						
					} else if (hr.getStateReason() == HouseStateResonEnum.HAD_SELLED.getValue()  ) {
						
						//改盘时候 如果已经出租或已经出售 则归档用户
						disableContact(hrhBefore ,HouseStateEnum.SELL.getValue());
						
					}*/
					//无论什么理由都归档
					if (hr.getHouseState() == HouseStateEnum.RENT.getValue() ) {
						
						//改盘时候 如果已经出租或已经出售 则归档用户
						disableContact(hrhBefore ,HouseStateEnum.RENT.getValue());
						
					} else if (hr.getHouseState() == HouseStateEnum.SELL.getValue() ) {
						
						//改盘时候 如果已经出租或已经出售 则归档用户
						disableContact(hrhBefore ,HouseStateEnum.SELL.getValue());
						
					}
					
					hr.setHouseState(HouseStateEnum.NEITHER.getValue());
					
				}else if (hr.getHouseState() == HouseStateEnum.RENTANDSELL.getValue()) {
					if (hr.getStateReason() == HouseStateResonEnum.HAD_RENTED.getValue() || hr.getStateReason() == HouseStateResonEnum.NO_RENT.getValue()) {
						hr.setHouseState(HouseStateEnum.SELL.getValue());
						disableContact(hrhBefore ,HouseStateEnum.RENT.getValue());
					}else if (hr.getStateReason() == HouseStateResonEnum.NO_SELL.getValue() ) {
						hr.setHouseState(HouseStateEnum.RENT.getValue());
						disableContact(hrhBefore ,HouseStateEnum.SELL.getValue());
					}else if (hr.getStateReason() == HouseStateResonEnum.HAD_SELLED.getValue() ||
							hr.getStateReason() == HouseStateResonEnum.NORENT_NOSELL.getValue() || 
							hr.getStateReason() == HouseStateResonEnum.NOSELL_HADRENT.getValue()) {
						hr.setHouseState(HouseStateEnum.NEITHER.getValue());
						disableContact(hrhBefore ,HouseStateEnum.RENT.getValue());
						disableContact(hrhBefore ,HouseStateEnum.SELL.getValue());
					}
					
					
				}
				
				
				
			} else if (hr.getActionType() == ActionTypeEnum.LOOP.getValue()) {
				hr.setHouseState(HouseStateEnum.NEITHER.getValue());
				disableContact(hrhBefore ,HouseStateEnum.SELL.getValue());
				disableContact(hrhBefore ,HouseStateEnum.RENT.getValue());
			}
			
			
			if (hrt != null ) {
				getEntityManager().remove(hrt);//删除 temp
			}
			
		}
		hrh.setBedroomSum(residence.getBedroomSum());
		hrh.setLivingRoomSum( residence.getLivingRoomSum());
		hrh.setWcSum(residence.getWcSum());
		hrh.setBalconySum(residence.getBalconySum());
		hrh.setSpaceArea(residence.getSpaceArea());
		hrh.setAfterCheckHouseState(hr.getHouseState());
		getEntityManager().merge(hr);
		getEntityManager().persist(hrh);
		
		logger.info("submit hr parameter:{}",ReflectionToStringBuilder.toString(hr));
		logger.info("submit hrh parameter:{}",ReflectionToStringBuilder.toString(hrh));
		//放入checknum=0的记录
		if (hrhBefore != null) {
			hrhBefore.setStatus(hrh.getStatus());
			hrhBefore.setResultDate(hrh.getResultDate());
			hrhBefore.setOperatorId(hrh.getOperatorId());
			
			//hrhBefore.setRentPrice(hrh.getRentPrice());//hrhBefore.setRentPrice(hr.getRentPrice());
			//hrhBefore.setSellPrice(hrh.getSellPrice());hrhBefore.setSellPrice(hr.getSellPrice());
			
			//hrhBefore.setHouseState(hrh.getHouseState());
			hrhBefore.setAfterCheckHouseState(hr.getHouseState());
			//hrhBefore.setStateReason(hr.getStateReason());
			if (ccar.getStatus() == StatusEnum.ING.getValue() ) {
				hrhBefore.setOperatorId(0);
			}
			logger.info("submit hrhBefore parameter:{}",ReflectionToStringBuilder.toString(hrhBefore));
			getEntityManager().merge(hrhBefore);
		}
		
		///////
		if (ccar.getStatus() == StatusEnum.SUCCESS.getValue()  || ccar.getStatus() == StatusEnum.FAILD.getValue() ) {
			if (hrh.getActionType() == ActionTypeEnum.PUBLISH.getValue() || 
					hrh.getActionType() == ActionTypeEnum.CHANGE.getValue() ) {
				//审核成功或失败  user的logcount+1
				User user = getEntityManager().find(User.class, hr.getUserId());
				if (user != null && user.getUid() > 0) {
					user.setCreateLogCount(user.getCreateLogCount() + 1 );
					getEntityManager().merge(user);
				}
			}
			
		}
		String pushMSg = "";
		if (ccar.getStatus() == StatusEnum.FAILD.getValue()) {
		    if (hrh.getActionType() == EntityUtils.ActionTypeEnum.PUBLISH.getValue() && hrh.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()) {
                pushMSg = "租房";
            }else if (hrh.getActionType() == EntityUtils.ActionTypeEnum.PUBLISH.getValue() && hrh.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()) {
                pushMSg = "售房";
            }else if (hrh.getActionType() == EntityUtils.ActionTypeEnum.CHANGE.getValue()  ) {
                pushMSg = "改盘";
            }
		    if(!pushMSg.equals("")){
		    	sendHouseCheckPushMsg(pushMSg, "失败",hrh);
		    }
		}
		
		if (ccar.getStatus() == StatusEnum.SUCCESS.getValue() ) {
			logger.info("after check success go into pay ...");
			
			//给经纪人 打钱
			if (hrh.getActionType() == EntityUtils.ActionTypeEnum.PUBLISH.getValue() && hrh.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()) {
				Pay pay = PayUtil.createPay(new AddPayReq(hr.getUserId(),EntityUtils.AwardTypeEnum.RENT.getValue()));
				this.getEntityManager().persist(pay);
				pushMSg = "租房";
			}else if (hrh.getActionType() == EntityUtils.ActionTypeEnum.PUBLISH.getValue() && hrh.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()) {
				Pay pay = PayUtil.createPay(new AddPayReq(hr.getUserId(),EntityUtils.AwardTypeEnum.SELL.getValue()));
				this.getEntityManager().persist(pay);
				pushMSg = "售房";
			}else if (hrh.getActionType() == EntityUtils.ActionTypeEnum.CHANGE.getValue()  ) {
				Pay pay = PayUtil.createPay(new AddPayReq(hr.getUserId(),EntityUtils.AwardTypeEnum.CHANGE.getValue()));
				this.getEntityManager().persist(pay);
				pushMSg = "改盘";
			}
			if(!pushMSg.equals("")){
				sendHouseCheckPushMsg(pushMSg, "成功",hrh);
			}
		}
		return new Response(0,"成功");
	}
	
	/**
	 * @date 2014年6月12日 上午10:52:45
	 * @author Tom
	 * @description  
	 * @param note 为理由
	 */
	public Response setLunXunSuccess(int houseId, String note) {
		
		CommitCheckAllRequest commitCheckAllRequest = new CommitCheckAllRequest();
		commitCheckAllRequest.setStatus(1);
		commitCheckAllRequest.setHouseId(houseId);
		commitCheckAllRequest.setNote(note);
		
		return this.submitCheckAll(commitCheckAllRequest);

	}
	
	/**
	 * @date 2014年6月12日 上午10:52:45
	 * @author Tom
	 * @description  
	 * @param note 为理由
	 */
	public Response checkLunXunAndSetLunXunSuccess(int houseId, String note) {
		String jpql = " from HouseResource where status = 2 and houseId = ? and actionType = 4";
		List<Object> list = this.getEntityManager().createQuery(jpql).setParameter(1, houseId).getResultList();
		
		if (list != null && list.size() > 0) {
			
			CommitCheckAllRequest commitCheckAllRequest = new CommitCheckAllRequest();
			commitCheckAllRequest.setStatus(1);
			commitCheckAllRequest.setHouseId(houseId);
			commitCheckAllRequest.setNote(note);
			
			return this.submitCheckAll(commitCheckAllRequest);
			
		} else {
			return new Response();
		}

	}
	
	private void sendHouseCheckPushMsg(String pushMSg,String result,ResidenceResourceHistory rrh){
		if(rrh.getUserId()>0){
			User user = getEntityManager().find(User.class, rrh.getUserId());
			House house = getEntityManager().find(House.class, rrh.getHouseId());
			Estate estate = getEntityManager().find(Estate.class, house.getEstateId());
			String buildingStr = "";
			if(!house.getBuilding().equals("0")){
				buildingStr = house.getBuilding()+"栋";
			}
			String msg = estate.getName()+buildingStr+house.getRoom()+"室的"+pushMSg;
			if(user!=null){
				pushUtils.sendHouseCheckPushMsg(user,msg,result); 
			}
		}
	}
	
	public CSCheckNumResponse getCheckNum(EmployeeModel employee) {
		int userId = employee.getId();
		logger.info("operatorId is {}", userId);
		Calendar currentDate = new GregorianCalendar(); 

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		System.out.println("start:" + currentDate.getTime());
		Date timeStart = currentDate.getTime();
		currentDate = new GregorianCalendar();   
		  
		currentDate.set(Calendar.HOUR_OF_DAY, 23);  
		currentDate.set(Calendar.MINUTE, 59);  
		currentDate.set(Calendar.SECOND, 59);
		System.out.println("end:" + currentDate.getTime());
		Date timeEnd = currentDate.getTime();
		//dateFormat.parse(source);
		
		
		CSCheckNumResponse cs = new CSCheckNumResponse();
		cs.setCheckChange(((Long)getEntityManager().createQuery("select count(1) from HouseResourceHistory where operatorId=:userId and actionType=2 and checkNum != 0 and createTime >:startTime and createTime <:endTime")
				.setParameter("userId",userId).setParameter("startTime", timeStart).setParameter("endTime", timeEnd).getSingleResult()).intValue());
		cs.setCheckLoop(((Long)getEntityManager().createQuery("select count(1) from HouseResourceHistory where operatorId=:userId and actionType=4 and checkNum != 0  and createTime >:startTime and createTime <:endTime")
				.setParameter("userId",userId).setParameter("startTime", timeStart).setParameter("endTime", timeEnd).getSingleResult()).intValue());
		cs.setCheckPublishRent(((Long)getEntityManager().createQuery("select count(1) from HouseResourceHistory where operatorId=:userId and actionType=1 and checkNum != 0  and houseState=1 and createTime >:startTime and createTime <:endTime")
				.setParameter("userId",userId).setParameter("startTime", timeStart).setParameter("endTime", timeEnd).getSingleResult()).intValue());
		cs.setCheckPublishSell(((Long)getEntityManager().createQuery("select count(1) from HouseResourceHistory where operatorId=:userId and actionType=1 and checkNum != 0  and houseState=2 and createTime >:startTime and createTime <:endTime")
				.setParameter("userId",userId).setParameter("startTime", timeStart).setParameter("endTime", timeEnd).getSingleResult()).intValue());
		return cs;
	}
	
	
	public IsShanghaiResponse isShanghai(String mobile) {
		if (StringUtils.isNotBlank(mobile) && mobile.length() > 7) {
			mobile = mobile.substring(0,7);
			Query query = getEntityManager().createQuery(
					"select ml from MobileLocation ml where ml.areaCode = '021' and ml.mobileNumber=:mobile ");
			query.setParameter("mobile", mobile);
			List<MobileLocation> mls = query.getResultList();
			
			if (mls != null && mls.size() > 0) {
				return new IsShanghaiResponse(1);
			}
			//return new IsShanghaiResponse(1);
			
			
		}
		
		return new IsShanghaiResponse(0);
		
		
	}
	
	public AntiCheatResponse antiCheat(AntiCheatRequest anti) {
		String sql = "select estateId,estate.name,building,room,hr.houseState from House h left join HouseResourceContact hrc on h.houseId=hrc.houseId  left join HouseResource hr on hr.houseId = h.houseId left join Area estate on estate.areaId = h.estateId where h.status =1 and h.estateId = ? and hrc.hostMobile =?";
		Query query  = getEntityManager().createNativeQuery(sql).setParameter(1, anti.getEstateId()).setParameter(2, anti.getMobile()).setMaxResults(30);
		List<Object[]> objList = query.getResultList();
		AntiCheatResponse antiResponse = new AntiCheatResponse();
		if (objList != null && objList.size() > 0) {
			for (Object[] row : objList) {
				int i = 0;
				AntiCheat a = new AntiCheat();
				a.setEstateId(Integer.parseInt(row[i++] + ""));
				a.setEstateName(row[i++] + "");
				a.setBuilding(row[i++] + "");
				a.setRoom(row[i++] + "");
				a.setHouseState(Integer.parseInt(row[i++] + ""));
				a.setHouseStateStr(EntityUtils.HouseStateEnum.getByValue(a.getHouseState()).getDesc());
				antiResponse.getList().add(a);
			}
		}
		
		return antiResponse;
	}

	public void disableContact(HouseResourceHistory hrhBefore ,int type) {
		
		if (hrhBefore == null) {
			return ;
		}
		disableContactByHouseId(hrhBefore.getHouseId(), type);
		
	}

	public void disableContactByHouseId(int houseId ,int type) {
		if (houseId <= 0) {
			return;
		}
		Query query = getEntityManager().createQuery("select sh from HouseResourceContact sh  WHERE sh.houseId=:houseId and sh.enable=true and sh.status != 3 and sh.houseState =:afterHouseState");
		//set sh.status = 3 ,sh.backTime =:backTime
		query.setParameter("houseId", houseId);
		query.setParameter("afterHouseState", type);
		List<HouseResourceContact> hrc = query.getResultList();
		if (hrc != null && hrc.size() > 0) {
			for (int i = 0; i < hrc.size(); i++) {
				hrc.get(i).setEnable(false);
				hrc.get(i).setBackTime(new Date());
				getEntityManager().merge(hrc.get(i));
			}
		}
	}


	
}