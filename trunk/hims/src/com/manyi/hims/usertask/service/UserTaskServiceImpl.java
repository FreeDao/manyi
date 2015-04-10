package com.manyi.hims.usertask.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.check.service.CustServService;
import com.manyi.hims.common.HouseInfoResponse;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.entity.UserTask;
import com.manyi.hims.house.service.HouseService;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.usertask.UserTaskConst;
import com.manyi.hims.usertask.controller.UserTaskController.FindHouseResourceRequest;
import com.manyi.hims.usertask.controller.UserTaskController.FindHouseResponse;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.UserTaskStatusEnum;
import com.manyi.hims.util.OSSObjectUtil;
@Service(value = "userTaskService")
@Scope(value = "singleton")
public class UserTaskServiceImpl extends BaseService implements UserTaskService {
	
	private Logger logger = LoggerFactory.getLogger(UserTaskServiceImpl.class);

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private HouseResourceService houseResourceService;
	
	@Autowired
	private CustServService custServService;
	

	@Override
	public int userTaskCount(int uid) {
		if(uid==0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		String jpql = "select count(1) from UserTask task where task.userId=? and task.taskStatus=1";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter(1, uid);
		long counts = (long)query.getSingleResult();
		int result = (int)counts;
		return result;
	}
	
	@Override
	public Response addUserTask(int uid, int houseId) {
		
		Response response = new Response();
		if(uid==0 || houseId==0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		
		//检测是否可以领取拍照任务
		response = houseResourceService.checkAllowTakePhoto(houseId);
		if (response.getErrorCode() != 0) {
			return response;
		}
		
		//中介看房任务领取，将该轮询置为成功
		response = custServService.checkLunXunAndSetLunXunSuccess(houseId, "中介看房任务领取，将该轮询置为成功！");
		if (response.getErrorCode() != 0) {
			logger.info("中介看房任务领取，将该轮询置为成功操作，错误！");
			throw new LeoFault(UserTaskConst.UserTask_ERROR150000);
		}
		
		int taskCount = userTaskCount(uid);
		if(taskCount>=10){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150003);
		}
		int taskRepulsionCode  = findHouseResource4UserTaskPhoto(houseId).getTaskRepulsionCode();
		if(taskRepulsionCode!=0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150004);
		}
	
		UserTask userTask = new UserTask();
		userTask.setUserId(uid);
		userTask.setHouseId(houseId);
		userTask.setTaskStatus(UserTaskStatusEnum.START.getValue());
		
		getEntityManager().persist(userTask);
		
		House house = getEntityManager().find(House.class, houseId);
		house.setCurrUserTaskId(userTask.getId());
		getEntityManager().merge(house);
		
		return response;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public UserTaskDetailResponse userTaskDetail(int userTaskId) {
		if(userTaskId==0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		UserTaskDetailResponse taskDetail = new UserTaskDetailResponse();
		UserTask task = getEntityManager().find(UserTask.class, userTaskId);
		if(task==null){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		HouseInfoResponse houseInfo = commonService.getHouseInfo(task.getHouseId());
		
		String jpql = "from HouseSupportingMeasures hsm where hsm.houseId=? and hsm.userTaskId=?";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter(1, task.getHouseId());
		query.setParameter(2, task.getId());
		List<HouseSupportingMeasures>  hsmList = query.getResultList();
		HouseSupportingMeasures houseSupportingMeasures = null;
		if(hsmList!=null && hsmList.size()>0){
			houseSupportingMeasures = hsmList.get(0);
			BeanUtils.copyProperties(houseSupportingMeasures, taskDetail);
		}
		taskDetail.setHouseInfo(houseInfo);
		BeanUtils.copyProperties(task, taskDetail);
		
		taskDetail.setPhotoStrArray(this.getPhotoStrArray(task.getHouseId(), userTaskId));
		return taskDetail;
	}
	
	/**
	 * @date 2014年6月9日 下午4:35:15
	 * @author Tom
	 * @description  
	 * 返回图片缩略图 list
	 */
	private List<String> getPhotoStrArray(int houseId, int userTaskId) {
		List<HouseImageFile> list = houseService.getHouseImageList(houseId, userTaskId);
		List<String> returnList = new ArrayList<>();	
		
		for (HouseImageFile houseImageFile : list) {
			
			returnList.add(OSSObjectUtil.getUrl(houseImageFile.getThumbnailKey(), Global.aliyun_image_path_timeout));
			
			
//			logger.info("加密前：{}", thumbnailKey);
//			byte[] encryptResult = AESEncrypter.encrypt(thumbnailKey, Global.AES_PASSWORD);
//			String encryptResultStr = AESEncrypter.parseByte2HexStr(encryptResult);
//			logger.info("加密后：{}", encryptResultStr);
			
		}
		return returnList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserTaskDetailResponse> userTaskIndex(int uid) {
		if(uid==0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		String jpql = "from UserTask task  where task.userId=? and task.taskStatus=1 order by task.createTime desc";
		Query query = getEntityManager().createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		query.setParameter(1, uid);
		List<UserTask> userTaskList = query.getResultList();
		List<UserTaskDetailResponse> infos = getUserTaskList(userTaskList);
		return infos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<UserTaskDetailResponse> userTaskIndexById(int uid,int first,int max) {
		if(uid==0){
			throw new LeoFault(UserTaskConst.UserTask_ERROR150001);
		}
		String jpql = "from UserTask task where task.userId=? and task.taskStatus in(2,3,4) order by task.uploadPhotoTime desc";
		String jpql_count = "select count(1) from UserTask task  where task.userId=? and task.taskStatus in(2,3,4)";
		
		Query query_count = getEntityManager().createQuery(jpql_count);
		query_count.setParameter(1, uid);
		long counts = (long)query_count.getSingleResult();
		int total = (int)counts;
		Page<UserTaskDetailResponse> page = new Page<UserTaskDetailResponse>();
		if(total<=0){
			page.setRows(new ArrayList<UserTaskDetailResponse>());
		}else{
			page.setTotal(total);
		}
		
		Query query = getEntityManager().createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		query.setParameter(1, uid);
		List<UserTask> userTaskList = query.setFirstResult(first).setMaxResults(max).getResultList();
		
		List<UserTaskDetailResponse> infos = getUserTaskList(userTaskList);
		page.setRows(infos);
		return page;
	}
	
	public  List<UserTaskDetailResponse> getUserTaskList(List<UserTask> list){
		List<UserTaskDetailResponse> infos = new ArrayList<UserTaskDetailResponse>();
		if(list!=null && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				UserTaskDetailResponse response = new UserTaskDetailResponse();
				UserTask task = list.get(i);
				HouseInfoResponse houseInfo = commonService.getHouseInfo(task.getHouseId());
				response.setHouseInfo(houseInfo);
				BeanUtils.copyProperties(task, response);
				infos.add(response);
			}
		}
		return infos;
	}
	
	/**
	 * @date 2014年6月6日 下午2:50:19
	 * @author Tom
	 * @description  
	 * 查询房源是否满足拍照的条件
	 */
	public FindHouseResponse findHouseResource4UserTaskPhoto(FindHouseResourceRequest findHouseResourceRequest) {
		FindHouseResponse findHouseResponse = new FindHouseResponse();
				
		House house = houseService.findHouse(findHouseResourceRequest.getSubEstateId(), findHouseResourceRequest.getBuilding(), findHouseResourceRequest.getRoom());
		if (house == null) {
			findHouseResponse.setTaskRepulsionCode(-1);
			findHouseResponse.setMessage("不存在该房源！");
			return findHouseResponse;
		}

		//检测是否可以领取拍照任务
		Response response= houseResourceService.checkAllowTakePhoto(house.getHouseId());
		if (response.getErrorCode() != 0) {
			findHouseResponse.setErrorCode(response.getErrorCode());
			findHouseResponse.setMessage(response.getMessage());
			return findHouseResponse;
		}
				
		return findHouseResource4UserTaskPhoto(house.getHouseId());
	}
	
	/**
	 * @date 2014年6月6日 下午2:50:19
	 * @author Tom
	 * @description  
	 * 查询房源是否满足拍照的条件
	 */
	public FindHouseResponse findHouseResource4UserTaskPhoto(int houseId) {
		FindHouseResponse findHouseResponse = new FindHouseResponse();
		
		findHouseResponse.setHouseId(houseId);
		
		//是否有在进行的排斥任务
		int taskFlag = houseResourceService.getOngoingTask(houseId, false);
		if (EntityUtils.TaskRepulsionEnum.NOTASK.getValue() != taskFlag) {
			
			if (taskFlag == EntityUtils.TaskRepulsionEnum.PUBLISH.getValue()
					|| taskFlag == EntityUtils.TaskRepulsionEnum.CHANGE.getValue()
					|| taskFlag == EntityUtils.TaskRepulsionEnum.REPORT.getValue()
					|| taskFlag == EntityUtils.TaskRepulsionEnum.LOOP.getValue()) {

				findHouseResponse.setTaskRepulsionCode(-2);
				findHouseResponse.setMessage("该房源正在审核中，无法进行拍照任务！");
				return findHouseResponse;
				
			} else if (taskFlag == EntityUtils.TaskRepulsionEnum.BD_TASK.getValue()) {

				findHouseResponse.setTaskRepulsionCode(-3);
				findHouseResponse.setMessage("该房源正在进行BD看房任务中，无法进行拍照任务！");
				return findHouseResponse;
				
			} else if (taskFlag == EntityUtils.TaskRepulsionEnum.USER_TASK.getValue()) {

				findHouseResponse.setTaskRepulsionCode(-4);
				findHouseResponse.setMessage("该房源正在进行中介看房任务中，无法进行拍照任务！");
				return findHouseResponse;
				
			}
		}
		
		findHouseResponse.setTaskRepulsionCode(0);
		findHouseResponse.setMessage("该房源满足拍照任务！");
		return findHouseResponse;

	}
	
	/**
	 * @date 2014年6月12日 下午5:06:52
	 * @author Tom
	 * @description  
	 * 将48小时前任务 设置过期
	 */
	public void setUserTask48HoursExpired() {
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -2);
		Date date = calendar.getTime();
		
		List<UserTask> userTaskList = getEntityManager().createQuery("from UserTask a where a.taskStatus = 1 and a.createTime <= ?").setParameter(1, date).getResultList();
		
		for (UserTask userTask : userTaskList) {
			userTask.setTaskStatus(EntityUtils.UserTaskStatusEnum.PASTDUE.getValue());
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
