package com.manyi.hims.sourcemanage.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DateUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.Response;
import com.manyi.hims.bd.controller.HouseResourcePhotoController.TaskRequest;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.Town;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.residence.service.ResidenceService;
import com.manyi.hims.sourcemanage.model.SourceManageRequest;
import com.manyi.hims.sourcemanage.model.SourceManageResponse;
import com.manyi.hims.sourcemanage.model.SourceManageResponse.Audit;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;

/**
 * @date 2014年4月17日 下午12:51:16
 * @author Tom 
 * @description  
 * 房源管理详情页，SourceManageServiceImpl
 */
@Service(value = "sourceManageService")
@Scope(value = "singleton")
public class SourceManageServiceImpl  extends BaseService implements SourceManageService{


	@Autowired
	private HouseResourceService houseResourceService;
	
	@Autowired
	private ResidenceService residenceService;
	
	/**
	 * @date 2014年4月17日 下午1:50:38
	 * @author Tom  
	 * @description  
	 * 房源基础信息
	 */
	public SourceManageResponse getSourceBaseInfo(int houseId) {
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		
		Residence residence = this.getEntityManager().find(Residence.class, houseId);
//		房源编号：MY98686
		sourceManageResponse.setHouseId(residence.getHouseId());
//		楼栋号：
		sourceManageResponse.setBuilding(residence.getBuilding());
//		房型：2室2厅1卫
		sourceManageResponse.setBedroomSum(residence.getBedroomSum());
		sourceManageResponse.setLivingRoomSum(residence.getLivingRoomSum());
		sourceManageResponse.setWcSum(residence.getWcSum());
//		室号：
		sourceManageResponse.setRoom(residence.getRoom());
//		面积：
		sourceManageResponse.setSpaceArea(residence.getSpaceArea());
		
//		小区
		Estate estate = this.getEntityManager().find(Estate.class, residence.getEstateId());
		sourceManageResponse.setEstateName(estate.getName());
//		板块
		Town town = this.getEntityManager().find(Town.class, estate.getParentId());
		sourceManageResponse.setTownName(town.getName());
//		区域
		City city = this.getEntityManager().find(City.class, town.getParentId());
		sourceManageResponse.setCityName(city.getName());
		
		return sourceManageResponse;
	}
	
	

	/**
	 * @date 2014年4月17日 下午1:52:54
	 * @author Tom  
	 * @description  
	 * 租售信息
	 */
	public SourceManageResponse getSourceRentAndSellList(SourceManageResponse sourceManageResponse, int houseId) {
		HouseResource houseResource = this.getEntityManager().find(HouseResource.class, houseId);
		
		sourceManageResponse.setHouseState(houseResource.getHouseState());
		sourceManageResponse.setRentPrice(houseResource.getRentPrice());
		sourceManageResponse.setSellPrice(houseResource.getSellPrice());
		sourceManageResponse.setIsAuditing(houseResource.getStatus() == StatusEnum.ING.getValue());
		sourceManageResponse.setActionTypeStr(ActionTypeEnum.getByValue(houseResource.getActionType()).getDesc());

		return sourceManageResponse;
	}
	 
	
	/**
	 * @date 2014年4月17日 下午1:52:54
	 * @author Tom  
	 * @description  
	 * 房屋信息重复性检测
	 */
	private SourceManageResponse checkHouseDouble(SourceManageRequest sourceManageRequest, Residence residence) {
		Query query = this.getEntityManager().createQuery("from House h where h.room = ?1 and h.building = ?2 and h.floor = ?3 and h.estateId = ?4 and h.houseId <> ?5");
		
		query.setParameter(1, sourceManageRequest.getRoom());
		query.setParameter(2, residence.getBuilding());
		query.setParameter(3, residence.getFloor());
		query.setParameter(4, residence.getEstateId());
		query.setParameter(5, residence.getHouseId());
		
		List<Object> list = query.getResultList();
		if (list.size() == 1) {
			SourceManageResponse sourceManageResponse = new SourceManageResponse();
			sourceManageResponse.setErrorCode(-1);
			sourceManageResponse.setMessage("与已有房屋信息重复，请重新填写室号！");
			return sourceManageResponse;
		} else {
			return null;
		}
	}
	
	/**
	 * @date 2014年4月28日 下午6:04:01
	 * @author Tom  
	 * @description  
	 * 将上一条住宅信息转移到History表中，并将当前住宅信息更新掉
	 */
	private void updateResidence(SourceManageRequest sourceManageRequest) {
		Residence residence = getEntityManager().find(Residence.class, sourceManageRequest.getHouseId());
		
		//如果有更新
		if (sourceManageRequest.getBedroomSum() != residence.getBedroomSum()
				|| sourceManageRequest.getLivingRoomSum() != residence.getLivingRoomSum()
				|| sourceManageRequest.getWcSum() != residence.getWcSum() 
				|| (!sourceManageRequest.getRoom().equals(residence.getRoom()))
				|| sourceManageRequest.getSpaceArea().compareTo(residence.getSpaceArea()) != 0) {
			//先备份
			residenceService.copyResidence2History(residence);
			
			residence.setBedroomSum(sourceManageRequest.getBedroomSum());
			residence.setLivingRoomSum(sourceManageRequest.getLivingRoomSum());
			residence.setWcSum(sourceManageRequest.getWcSum());
			residence.setRoom(sourceManageRequest.getRoom());
			residence.setSpaceArea(sourceManageRequest.getSpaceArea());

			this.getEntityManager().merge(residence);
		}
	}
	
	/**
	 * @date 2014年4月28日 下午6:04:01
	 * @author Tom  
	 * @description  
	 * 将上一条房源信息转移到History表中，并将当房源信息更新掉
	 */
	private void updateHouseResource(SourceManageRequest sourceManageRequest) {
		HouseResource houseResource = getEntityManager().find(HouseResource.class, sourceManageRequest.getHouseId());
		
		//如果有更新
		if (sourceManageRequest.getHouseState() != houseResource.getHouseState()
				|| sourceManageRequest.getRentPrice() != houseResource.getRentPrice()
				|| sourceManageRequest.getSellPrice() != houseResource.getSellPrice()) {
			
			
			//先备份
//			houseResourceService.copyHouseResource2History(houseResource);
			HouseResourceHistory houseResourceHistory = new HouseResourceHistory();
			BeanUtils.copyProperties(houseResource, houseResourceHistory);
			
			houseResourceHistory.setOperatorId(sourceManageRequest.getOperatorId());
			houseResourceHistory.setResultDate(new Date());
			this.getEntityManager().persist(houseResourceHistory);
			
			if (HouseStateEnum.RENT.getValue() == sourceManageRequest.getHouseState()) {
				houseResource.setRentPrice(sourceManageRequest.getRentPrice());
				houseResource.setHouseState(sourceManageRequest.getHouseState());
			} else if (HouseStateEnum.SELL.getValue() == sourceManageRequest.getHouseState()) {
				houseResource.setSellPrice(sourceManageRequest.getSellPrice());
				houseResource.setHouseState(sourceManageRequest.getHouseState());
			} else if (HouseStateEnum.RENTANDSELL.getValue() == sourceManageRequest.getHouseState()) {
				houseResource.setRentPrice(sourceManageRequest.getRentPrice());
				houseResource.setSellPrice(sourceManageRequest.getSellPrice());
				houseResource.setHouseState(sourceManageRequest.getHouseState());
			} else if (HouseStateEnum.NEITHER.getValue() == sourceManageRequest.getHouseState()) {
				houseResource.setRentPrice(sourceManageRequest.getRentPrice());
				houseResource.setSellPrice(sourceManageRequest.getSellPrice());
				houseResource.setHouseState(sourceManageRequest.getHouseState());
			}
			//客服的审核次数
			houseResource.setCheckNum(0);
			houseResource.setResultDate(new Date());
			this.getEntityManager().merge(houseResource);

		}
	}
	
	/**
	 * @date 2014年5月19日 下午8:12:16
	 * @author Tom
	 * @description  
	 * 更新房源的价格信息
	 */
	public Response checkUpdateSource(TaskRequest taskRequest) {

//		检测变量范围
		if (taskRequest.getSellPrice().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.GOT_PRICE.getValue())) > 0) {
		//到手价：不大于10,000,000,000（100亿）
			Response temp = new Response();
			temp.setErrorCode(-1);
			temp.setMessage("到手价：不能大于100亿");
			return temp;
			
		} else if (taskRequest.getRentPrice().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.RENT_PRICE.getValue())) > 0) {
		//租    金：不大于10,000,000（1000万）
			Response temp = new Response();
			temp.setErrorCode(-1);
			temp.setMessage("租金：不能大于1000万");
			return temp;
		} else {
			HouseResource houseResource = this.getEntityManager().find(HouseResource.class, taskRequest.getHouseId());
			HouseResourceHistory houseResourceHistory = new HouseResourceHistory();
			
			//有一个数据有变化就存历史
			if (((houseResource.getSellPrice()==null?new BigDecimal(0):houseResource.getSellPrice()).compareTo(taskRequest.getSellPrice()) != 0) || ((houseResource.getRentPrice()==null?new BigDecimal(0):houseResource.getRentPrice()).compareTo(taskRequest.getRentPrice()) != 0)) {
//			存历史
				BeanUtils.copyProperties(houseResource, houseResourceHistory);
				this.getEntityManager().persist(houseResourceHistory);
			}
			
//			更新价格
			houseResource.setSellPrice(taskRequest.getSellPrice());
			houseResource.setRentPrice(taskRequest.getRentPrice());
			return new Response();
		}
	}
	
	/**
	 * @date 2014年4月20日 下午4:53:58
	 * @author Tom  
	 * @description  
	 * 房源修改
	 */
	public SourceManageResponse updateSourceManage(SourceManageRequest sourceManageRequest) {
		
		Residence residence = getEntityManager().find(Residence.class, sourceManageRequest.getHouseId());
		
		//检测房子唯一性
		SourceManageResponse sourceManageResponse = checkHouseDouble(sourceManageRequest, residence);
		if(sourceManageResponse != null) {
			return sourceManageResponse;
		}
		
//		检测变量范围
		if (sourceManageRequest.getSellPrice().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.GOT_PRICE.getValue())) > 0) {
		//到手价：不大于10,000,000,000（100亿）
			SourceManageResponse temp = new SourceManageResponse();
			temp.setErrorCode(-1);
			temp.setMessage("到手价：不能大于100亿");
			return temp;
			
		} else if (sourceManageRequest.getRentPrice().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.RENT_PRICE.getValue())) > 0) {
		//租    金：不大于10,000,000（1000万）
			SourceManageResponse temp = new SourceManageResponse();
			temp.setErrorCode(-1);
			temp.setMessage("租金：不能大于1000万");
			return temp;
		} else if (sourceManageRequest.getSpaceArea().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.SPACE_AREA.getValue())) > 0) {
		//面    积：不大于10,000平方米；
			SourceManageResponse temp = new SourceManageResponse();
			temp.setErrorCode(-1);
			temp.setMessage("面积：不能大于10,000平方米");
			return temp;
		}
		
//		更新房子
		updateResidence(sourceManageRequest);
		
//		更新房源
		updateHouseResource(sourceManageRequest);
		
		return new SourceManageResponse();
	}
	

	/**
	 * @date 2014年4月17日 下午1:53:36
	 * @author Tom  
	 * @description  
	 * 审核记录
	 */
	@SuppressWarnings("unchecked")
	public SourceManageResponse getAuditMessageInfo(int houseId) {
		Query query = getEntityManager().createQuery("from HouseResourceHistory where houseId = :houseId and checkNum <> 0 order by resultDate desc");
		query.setParameter("houseId", houseId);
		List<HouseResourceHistory> list = (List<HouseResourceHistory>)query.getResultList();
		
		List<Audit> auditlAuditList = new ArrayList<Audit>();
		Audit audit = null;
		for (HouseResourceHistory houseResourceHistory : list) {
			audit = new Audit();
			audit.setType(ActionTypeEnum.getByValue(houseResourceHistory.getActionType()).getDesc());
			audit.setFinishDate(DateUtil.formatDate("yyyy-MM-dd HH:mm:ss", houseResourceHistory.getResultDate()));
			audit.setNote(houseResourceHistory.getNote());
			audit.setAuditState(StatusEnum.getByValue(houseResourceHistory.getStatus()).getDesc());
			if (houseResourceHistory.getActionType() == EntityUtils.ActionTypeEnum.LOOP.getValue()) { 
				audit.setAuditState(CommonUtils.loopTransformStatusDesc(houseResourceHistory.getStatus()));
				
			}
			audit.setHistoryId(houseResourceHistory.getHistoryId());
			
			Employee employee = getEntityManager().find(Employee.class, houseResourceHistory.getOperatorId());
			audit.setRealName(employee.getRealName());
			
			auditlAuditList.add(audit);
		}
		SourceManageResponse sourceManageResponse = new SourceManageResponse();
		sourceManageResponse.setAuditList(auditlAuditList);
		return sourceManageResponse;
	}

	/**
	 * @date 2014年4月17日 下午1:55:12
	 * @author Tom  
	 * @description  
	 * 房源修改记录
	 */
	
	public SourceManageResponse getSourceEditInfo(int houseId) {
//		Query query = getEntityManager().createNativeQuery("select a.logText, a.addTime, b.userName from HouseHistory a left join Employee b on a.operator = b.employeeId where a.houseId = :houseId order by a.addTime desc");
//		query.setParameter("houseId", houseId);
//		List<Object[]> list = (List<Object[]>)query.getResultList();
//		
//		List<HouseHis> houseHisList = new ArrayList<>();
//		HouseHis houseHis = null; 
//		for (Object[] obj : list) {
//			houseHis = new HouseHis();
//			houseHis.setLogText(obj[0].toString());
//			houseHis.setAddTime(obj[1].toString());
//			houseHis.setUserName(obj[2].toString());
//			houseHisList.add(houseHis);
//		}
//		
//		SourceManageResponse sourceManageResponse = new SourceManageResponse();
//		sourceManageResponse.setHouseHisList(houseHisList);
//		return sourceManageResponse;
		return null;
	}


	
	/**
	 * @date 2014年4月21日 下午12:16:34
	 * @author Tom  
	 * @description  
	 * 删除房源信息，假删除
	 */
	public void deleteSourceInfo(int houseId, int operatorId) {
		HouseResource houseResource = getEntityManager().getReference(HouseResource.class, houseId);
	
		houseResource.setStatus(StatusEnum.DISABLED.getValue());
		houseResource.setOperatorId(operatorId);
		this.getEntityManager().merge(houseResource);
		logger.info("用户operatorId = " + operatorId + "将房源信息houseId = " + houseId + "删除!");

	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
