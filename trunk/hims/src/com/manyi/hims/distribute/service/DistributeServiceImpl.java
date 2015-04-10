package com.manyi.hims.distribute.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.distribute.model.DistributeRequest;
import com.manyi.hims.distribute.model.DistributeResponse;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.entity.DistributionHistory;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceHistory_;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.util.EntityUtils;

@Service (value="distributeService")
@Scope (value="singleton")
public class DistributeServiceImpl extends BaseService implements DistributeService {
	
	private final static int PENDING_PERIOD = Global.CHECK_AGAIN_MIDTIME; //6 hours

	@Override
	public Response distribute() {
		//TODO:后台自动分配，暂不实现
		return new Response(0, "成功");
	}
	
	/**
     * 找到房子后的分配的逻辑主路口
     */
	private void distributeHouseResoiurces2Employee(DistributeRequest distributeRequest) {
		if(distributeRequest==null ||  distributeRequest.getHouseIds()==null || distributeRequest.getHouseIds().length==0) {
			logger.error("unable to distribute...");
			return;
		}
		for(int houseId : distributeRequest.getHouseIds()){
			distributeHouseResoiurces2Employee(distributeRequest, houseId);
		}
	}
	
	private void distributeHouseResoiurces2Employee(DistributeRequest distributeRequest, int houseId) {

//		this.getEntityManager().createQuery("UPDATE HouseResource SET operatorId=? WHERE houseId=?")
//			.setParameter(1, distributeRequest.getEmployeeId())
//			.setParameter(2, houseId)
//			.executeUpdate();
		HouseResource houseResource = this.getEntityManager().find(HouseResource.class, houseId);
		if(houseResource!=null){
			houseResource.setOperatorId(distributeRequest.getEmployeeId());
		}
		
        //Step 2: write a record to DistriEmployeeHistory
        DistributionHistory distributionHistory = new DistributionHistory();
        distributionHistory.setAddTime(new Date());
        distributionHistory.setCanceled(distributeRequest.isCanceled());
        distributionHistory.setEmployeeId(distributeRequest.getEmployeeId());
        distributionHistory.setLookStatus(distributeRequest.getLookStatus());
        distributionHistory.setManagerId(distributeRequest.getManagerId());
        distributionHistory.setSourceLogId(houseId);
        this.getEntityManager().persist(distributionHistory);
        
        //更新HouseResourceHistory表 operatorId  
//        this.getEntityManager().createQuery("UPDATE HouseResourceHistory SET operatorId=? WHERE houseId=? and checkNum = 0 and status = 2")
//			.setParameter(1, distributeRequest.getEmployeeId())
//			.setParameter(2, houseId)
//			.executeUpdate();
//        List<HouseResourceHistory> houseResourceHistories = 
//        	this.getEntityManager().createQuery("select h from HouseResourceHistory h where h.houseId=? and h.checkNum=0 and h.status=?")
//        		.setParameter(0, houseId)
//        		.setParameter(2, EntityUtils.StatusEnum.ING.getValue())
//        		.getResultList();
        CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
        CriteriaQuery<HouseResourceHistory> query = builder.createQuery(HouseResourceHistory.class);
        Root<HouseResourceHistory> root = query.from(HouseResourceHistory.class);
        query.select(root).where(builder.and(
        	builder.equal(root.get(HouseResourceHistory_.houseId), houseId),
        	builder.equal(root.get(HouseResourceHistory_.checkNum), 0),
        	builder.equal(root.get(HouseResourceHistory_.status), EntityUtils.StatusEnum.ING.getValue())
        ));
        List<HouseResourceHistory> houseResourceHistories = this.getEntityManager().createQuery(query).getResultList();
        if (houseResourceHistories!=null && houseResourceHistories.size() > 0) {
			HouseResourceHistory houseResourceHistory = houseResourceHistories.get(0);
			houseResourceHistory.setOperatorId(distributeRequest.getEmployeeId());
		}
	}
	
	
    /**
     * 找到房子后的分配的逻辑
     */
	@Override
	public Response distribute(DistributeRequest distributeRequest) {
		this.distributeHouseResoiurces2Employee(distributeRequest);
		return new Response(0, "成功");
	}
	
	/**
	 * @date 2014年5月6日 下午20:50:38
	 * @author Tom  
	 * @description  
	 * 分配举报任务
	 */
	public Response distribute4Report(DistributeRequest distributeRequest) {
		this.distributeHouseResoiurces2Employee(distributeRequest);
		return new Response(0, "成功");
	}

	@Override
	public Response pendingRecovery() {
		//TODO:冻结状态恢复，暂不实现，暂时也没有你冻结状态，仅更新时间
		return new Response(0, "成功");
	}
	
	/**
	 * @author howard
	 * 
	 */
	private HouseResource getUnFinishedHouseResource(int employeeId){
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResource> query = builder.createQuery(HouseResource.class);
		Root<HouseResource> root = query.from(HouseResource.class);
		query.select(root).where(builder.equal(root.get(HouseResource_.operatorId), employeeId)).orderBy(builder.asc(root.get(HouseResource_.publishDate)));
		List<HouseResource> houseResources = this.getEntityManager().createQuery(query).setMaxResults(1).getResultList();
		if(houseResources==null || houseResources.size() == 0){
			return null;
		}
		return houseResources.get(0);
	}
	
	/**
	 * 找到需要分配的一个房子
	 * @author chris
	 * @return
	 */
	private List<HouseResource> getEarliestUncheckedHouseResources(EmployeeModel emp,int numLimit) {
		/*
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResource> query = builder.createQuery(HouseResource.class);
		Root<HouseResource> root = query.from(HouseResource.class);
		
		
		List<Integer> requiredTypes = new ArrayList<Integer>();
		
		requiredTypes.add(EntityUtils.ActionTypeEnum.PUBLISH.getValue());
		requiredTypes.add(EntityUtils.ActionTypeEnum.CHANGE.getValue());
		requiredTypes.add(EntityUtils.ActionTypeEnum.LOOP.getValue());
				
		query.select(root).where(builder.and(
			//builder.lessThan(root.get(HouseResource_.checkNum), Global.CHECK_AGAIN_TIMES),
			// 去除不租不售的房子 
			builder.notEqual(root.get(HouseResource_.houseState),EntityUtils.HouseStateEnum.NEITHER.getValue()),
			// 选择没有operatorId的houseResource
			builder.equal(root.get(HouseResource_.operatorId), 0),
			builder.or(
				builder.isNull(root.get(HouseResource_.resultDate)),
				builder.lessThan(root.get(HouseResource_.resultDate), new Date(new Date().getTime()-PENDING_PERIOD))
			),
			builder.equal(root.get(HouseResource_.status), EntityUtils.StatusEnum.ING.getValue()),
			root.get(HouseResource_.actionType).in(requiredTypes)
		)).orderBy(builder.asc(root.get(HouseResource_.actionType)),builder.asc(root.get(HouseResource_.publishDate)),builder.asc(root.get(HouseResource_.checkNum)));
		List<HouseResource> houseResources = this.getEntityManager().createQuery(query).setMaxResults(numLimit).getResultList();
		*/
		String jpql="select hr.houseId from HouseResource hr join House h on hr.houseId = h.houseId join Area estate on h.estateId =estate.areaId join Area town on estate.parentId = town.areaId "+
				" join  Area district on town.parentId = district.areaId join Area city on district.parentId = city.areaId "+
				" where  hr.houseId <> 0 ";
		//jpql += " and hr.checkNum < "+Global.CHECK_AGAIN_TIMES;
		jpql += " and hr.houseState != "+EntityUtils.HouseStateEnum.NEITHER.getValue();
		jpql += " and hr.operatorId = 0 ";
		jpql += " and ( hr.resultDate is null or hr.resultDate < ? )";
		jpql += " and hr.status =  "+EntityUtils.StatusEnum.ING.getValue();
		jpql += " and hr.actionType in ( "+EntityUtils.ActionTypeEnum.PUBLISH.getValue()+" , "+EntityUtils.ActionTypeEnum.CHANGE.getValue() +" , "+EntityUtils.ActionTypeEnum.LOOP.getValue()+" )";
		
		//添加 城市 筛选
		if(emp.getCityId() > 0){
			jpql += " and city.areaId =  "+emp.getCityId();
		}
		jpql += " order by case when  hr.actionType  =1 and hr.houseState = 1 then 1 when hr.actionType  =2 and (hr.houseState = 1 or hr.houseState =3) then 2  when hr.actionType  =4 and (hr.houseState = 1 or hr.houseState =3) then 3  "
				+ " when hr.actionType  =1 and hr.houseState = 2 then 4 when hr.actionType  =2 and hr.houseState = 2 then 5 when hr.actionType  =4 and hr.houseState = 2 then 6 else 7 end "
				+ " asc , hr.publishDate asc , hr.checkNum asc ";
		
		Query query =this.getEntityManager().createNativeQuery(jpql);
		Date d = new Date(new Date().getTime()-PENDING_PERIOD);
		query.setParameter(1, d);
		List<Integer> rows = query.setMaxResults(numLimit).getResultList();
		List<HouseResource> houseResources = new ArrayList<HouseResource>();
		if(rows != null && rows.size()>0){
			HouseResource hr = new HouseResource();
			hr.setHouseId(Integer.parseInt(rows.get(0)+""));
			houseResources.add(hr);
		}
		
		if(houseResources==null || houseResources.size()==0){
			return null;
		}
		return houseResources;
	}
	/**
	 * 找到需要分配的一个房子
	 * @return
	 */
	private HouseResource getEarliestUncheckedHouseResource(EmployeeModel emp){
		List<HouseResource> houseResources = this.getEarliestUncheckedHouseResources(emp,1);
		if(houseResources != null && houseResources.size() > 0){
			return houseResources.get(0);
		}
		return null;
	}
	
	/**
	 * 开始自动分配
	 * 1找到需要分配的一个房子 getEarliestUncheckedHouseResource
	 */
	/*@Override
	public Response autoDistribute(EmployeeModel emp) {
		HouseResource houseResource = this.getEarliestUncheckedHouseResource(emp);
		
		if(houseResource != null){
			DistributeRequest distributeRequest = new DistributeRequest();
			distributeRequest.setEmployeeId(emp.getId());
			distributeRequest.setLookStatus(0);
			int[] houseIds = {houseResource.getHouseId()};
			distributeRequest.setHouseIds(houseIds);
			Response response = this.distribute(distributeRequest);
			if (response != null && response.getErrorCode() == 0) {
				return new DistributeResponse(houseResource.getHouseId());
			}
			return null;
		}
		return new Response(-1,"所有的都完成了 暂时没找到任何新的任务");
	}*/
	
	public Response autoDistribute(EmployeeModel emp) {
		Integer id = this.getEarliest(emp);
		if(id != null){
			DistributeRequest distributeRequest = new DistributeRequest();
			distributeRequest.setEmployeeId(emp.getId());
			distributeRequest.setLookStatus(0);
			int[] houseIds = {id};
			distributeRequest.setHouseIds(houseIds);
			Response response = this.distribute(distributeRequest);
			if (response != null && response.getErrorCode() == 0) {
				return new DistributeResponse(id);
			}
			return null;
		}
		return new Response(-1,"所有的都完成了 暂时没找到任何新的任务");
	}

	@Override
	public Response getDistributedHouseResource(int employeeId) {
		HouseResource houseResource = getUnFinishedHouseResource(employeeId);
		if (houseResource != null && houseResource.getHouseId() != 0 ) {
			logger.info("find an earlier unfinished task of {}",houseResource.getHouseId());
			return new DistributeResponse(houseResource.getHouseId());
		}
		return null;
	}
	
	
	
	private Integer getEarliest(EmployeeModel emp){
		String jpql="select hr.houseId from HouseResource hr join House h on hr.houseId = h.houseId join Area estate on h.estateId =estate.areaId join Area town on estate.parentId = town.areaId "+
				" join  Area district on town.parentId = district.areaId join Area city on district.parentId = city.areaId "+
				" where  hr.houseId <> 0 ";
		//jpql += " and hr.checkNum < "+Global.CHECK_AGAIN_TIES;
		jpql += " and hr.houseState != "+EntityUtils.HouseStateEnum.NEITHER.getValue();
		jpql += " and hr.operatorId = 0 ";
		jpql += " and ( hr.resultDate is null or hr.resultDate < ? )";
		jpql += " and hr.status =  "+EntityUtils.StatusEnum.ING.getValue();
		jpql += " and hr.actionType in ( "+EntityUtils.ActionTypeEnum.PUBLISH.getValue()+" , "+EntityUtils.ActionTypeEnum.CHANGE.getValue() +" , "+EntityUtils.ActionTypeEnum.LOOP.getValue()+" )";
		
		//添加 城市 筛选
		if(emp.getCityId() > 0){
			jpql += " and city.areaId =  "+emp.getCityId();
		}
		jpql += " order by hr.actionType asc,hr.houseState asc , hr.publishDate asc , hr.checkNum asc ";
		
		Query query =this.getEntityManager().createNativeQuery(jpql);
		Date d = new Date(new Date().getTime()-PENDING_PERIOD);
		query.setParameter(1, d);
		List<Integer> rows = query.setMaxResults(1).getResultList();
		if(rows != null && rows.size()>0){
			return (Integer.parseInt(rows.get(0)+""));
		}
		
		return null;
	}
}
