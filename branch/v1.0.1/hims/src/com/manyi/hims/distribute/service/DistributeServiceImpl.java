package com.manyi.hims.distribute.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.manyi.hims.entity.DistributionHistory;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceHistory_;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.util.EntityUtils;

@Service (value="distributeService")
@Scope (value="singleton")
@SuppressWarnings("unused")
public class DistributeServiceImpl extends BaseService implements DistributeService {
	
	private final static int NUM_LIMIT = 10;
	private final static int MAX_DISTRIBUTED_COUNT = 10;
	private final static int MAX_CHECK_NUM = 5;
	private final static int PENDING_PERIOD = Global.CHECK_AGAIN_MIDTIME; //6 hours
	private final static boolean DEBUG = false;

	@Override
	public Response distribute() {
		//TODO:后台自动分配，暂不实现
		return new Response(0, "成功");
	}
	
	private void distributeHouseResoiurces2Employee4Auto(DistributeRequest distributeRequest) {
		if(distributeRequest==null || 
				distributeRequest.getHouseIds()==null || 
				distributeRequest.getHouseIds().length==0)
		{
			return;
		}
		for(int houseId : distributeRequest.getHouseIds()){
			HouseResource houseResource = this.getEntityManager().find(HouseResource.class, houseId);
            boolean validState = this.getValidHouseResourceStatuses().contains(houseResource.getStatus());
            if(houseResource==null ||
                            (houseResource.getOperatorId()>0 && houseResource.getOperatorId()==distributeRequest.getEmployeeId()) ||
                            houseResource.getCheckNum()>=MAX_CHECK_NUM ||
                            !validState ||
                            (validState && houseResource.getResultDate() != null && houseResource.getResultDate().getTime() > (new Date().getTime()-PENDING_PERIOD))
                            ){
                    continue;
            }
            distributeHouseResoiurces2Employee(distributeRequest, houseId);
		
		}
	}
	
	private void distributeHouseResoiurces2Employee4Report(DistributeRequest distributeRequest) {
		if(distributeRequest==null || 
				distributeRequest.getHouseIds()==null || 
				distributeRequest.getHouseIds().length==0)
		{
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
        if (houseResourceHistories!=null && houseResourceHistories.size()==1) {
			HouseResourceHistory houseResourceHistory = houseResourceHistories.get(0);
			houseResourceHistory.setOperatorId(distributeRequest.getEmployeeId());
		}
	}
	
	
    private List<Integer> getValidHouseResourceStatuses(){
        List<Integer> validStates = new ArrayList<Integer>();
        if(DEBUG){
                validStates.add(EntityUtils.StatusEnum.SUCCESS.getValue());
        }
        validStates.add(EntityUtils.StatusEnum.ING.getValue());
        return validStates;
    }

	@Override
	public Response distribute(DistributeRequest distributeRequest) {
		this.distributeHouseResoiurces2Employee4Auto(distributeRequest);
		return new Response(0, "成功");
	}
	
	/**
	 * @date 2014年5月6日 下午20:50:38
	 * @author Tom  
	 * @description  
	 * 分配举报任务
	 */
	public Response distribute4Report(DistributeRequest distributeRequest) {
		this.distributeHouseResoiurces2Employee4Report(distributeRequest);
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
		if(houseResources==null || houseResources.size()==0){
			return null;
		}
		return houseResources.get(0);
	}
	
	/**
	 * @author chris
	 * @return
	 */
	private List<HouseResource> getEarliestUncheckedHouseResources(int numLimit) {
		
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResource> query = builder.createQuery(HouseResource.class);
		Root<HouseResource> root = query.from(HouseResource.class);
		
		
		List<Integer> requiredTypes = new ArrayList<Integer>();
		
		requiredTypes.add(EntityUtils.ActionTypeEnum.PUBLISH.getValue());
		requiredTypes.add(EntityUtils.ActionTypeEnum.CHANGE.getValue());
		requiredTypes.add(EntityUtils.ActionTypeEnum.LOOP.getValue());
				
		query.select(root).where(builder.and(
			builder.equal(root.get(HouseResource_.operatorId), 0),
			builder.or(
				builder.isNull(root.get(HouseResource_.resultDate)),
				builder.lessThan(root.get(HouseResource_.resultDate), new Date(new Date().getTime()-PENDING_PERIOD))
			),
			builder.equal(root.get(HouseResource_.status), EntityUtils.StatusEnum.ING.getValue()),
			root.get(HouseResource_.actionType).in(requiredTypes)
		)).orderBy(builder.asc(root.get(HouseResource_.checkNum)),builder.asc(root.get(HouseResource_.actionType)),builder.asc(root.get(HouseResource_.publishDate)));
		List<HouseResource> houseResources = this.getEntityManager().createQuery(query).setMaxResults(numLimit).getResultList();
		if(houseResources==null || houseResources.size()==0){
			return null;
		}
		return houseResources;
	}
	
	private HouseResource getEarliestUncheckedHouseResource(){
		List<HouseResource> houseResources = this.getEarliestUncheckedHouseResources(1);
		if(houseResources!=null && houseResources.size()==1){
			return houseResources.get(0);
		}
		return null;
	}
	
	@Override
	public synchronized Response autoDistribute(int employeeId) {
		HouseResource houseResource = this.getEarliestUncheckedHouseResource();
		
		if(houseResource != null){
			DistributeRequest distributeRequest = new DistributeRequest();
			distributeRequest.setEmployeeId(employeeId);
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
	
}
