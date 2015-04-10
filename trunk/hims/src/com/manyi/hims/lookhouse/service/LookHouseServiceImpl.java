package com.manyi.hims.lookhouse.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.check.model.CSSingleRequest;
import com.manyi.hims.check.service.CustServService;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.BdTaskMakeCallHistory;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.UserTask;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.lookhouse.model.LookHouseReq;
import com.manyi.hims.lookhouse.model.LookHouseRes;
import com.manyi.hims.lookhouse.model.MakeCallResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.BdTaskMakeCallHistoryResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.BdTaskResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.EmployeeResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.HouseResourceContactResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.HouseResourceHistoryResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.HouseResourceResponse;
import com.manyi.hims.lookhouse.model.MakeCallResponse.HouseResponse;
import com.manyi.hims.lookhouse.model.PlanReq;
import com.manyi.hims.lookhouse.model.PlanRes;
import com.manyi.hims.lookhouse.model.RandomReq;
import com.manyi.hims.lookhouse.model.SubmitRequest;
import com.manyi.hims.lookhouse.model.UserLookHouseReq;
import com.manyi.hims.lookhouse.model.UserLookHouseRes;
import com.manyi.hims.lookhouse.model.UserTaskSubmitRequest;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.util.PayUtil;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.HouseStateResonEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;
import com.manyi.hims.util.EntityUtils.TaskStatusEnum;
import com.manyi.hims.util.PushUtils;


@Service(value = "lookHouseService")
@Scope(value = "singleton")

public class LookHouseServiceImpl extends BaseService implements LookHouseService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("custServService")
	private CustServService custServService ;
	
	  @Autowired
	    private PushUtils pushUtils;
	
	/**
	 *  通过 user task id 获得缩略图 图片
	 */
	@Override
	public List<HouseImageFile> getHouseImageList(int id) {
		Query query = this.getEntityManager().createQuery("from HouseImageFile where userTaskId = ? order by orderId ");
		query.setParameter(1, id);
		return (List<HouseImageFile>)query.getResultList();
	}
	
	/**
	 * 通过 user task id 查询 user task 任务
	 */
	@Override
	public UserLookHouseRes getUserTaskById(int id) {
		String jpql="select user,task from UserTask task , User user  where user.uid = task.userId and id = ?";
		Query query = this.getEntityManager().createQuery(jpql);
		query.setParameter(1, id);
		List<Object[]> rows = query.getResultList();
		UserLookHouseRes res = new UserLookHouseRes();
		if(rows != null && rows.size()>0){
			User user = (User)rows.get(0)[0];
			UserTask task = (UserTask)rows.get(0)[1];
			BeanUtils.copyProperties(task, res);
			res.setBedroomSum(task.getAfterBedroomSum());
			res.setLivingRoomSum(task.getAfterLivingRoomSum());
			res.setWcSum(task.getAfterWcSum());
			
			res.setUserName(user.getRealName());
			res.setTaskStatusStr(EntityUtils.UserTaskStatusEnum.getByValue(res.getTaskStatus()).getDesc());
			res.setNote(task.getNote());
			res.setLayers(task.getLayers());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			res.setUploadPhotoTimeStr((res.getUploadPhotoTime() == null ? "-": sdf.format(res.getUploadPhotoTime()) ));
			res.setCreateTimeStr((res.getCreateTime() == null ? "-": sdf.format(res.getCreateTime()) ));
			res.setFinishDateStr((res.getFinishDate() == null ? "-": sdf.format(res.getFinishDate()) ));
		}
		return res;
	}
	
	/**
	 * 经纪人 看房 房源任务信息  列表
	 */
	@Override
	public PageResponse<UserLookHouseRes> userLookHouseList(UserLookHouseReq req) {
		log.info("经纪人 看房 房源任务信息  列表 参数: {} ",ReflectionToStringBuilder.toString(req));
		

		String count_sql="SELECT COUNT(task.id) FROM UserTask task join House h on h.houseId = task.houseId join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ " join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId join HouseResource log on log.houseId = h.houseId left join user u on u.uId = task.userId "
				+ " left join employee emp on emp.employeeId = task.operatorId "
				+" where log.status <> 4 and h.houseId <>0 ";
		String sql="SELECT h.houseId,city.areaId cityId,city.name cityName,area.areaId areaId , area.name areaName , town.areaId townId,town.name townName,sube.areaId estateId, "
				+ "sube.name estateName,sube.road road,h.building,h.room,log.houseState houseState ,log.publishDate publishDate,log.actionType logType ,log.status,u.uid, u.realName , "
				+" task.id taskId,task.createTime addTaskTime , task.taskStatus ,task.operatorId , emp.realName empName , h.picNum,task.finishDate "
				+ " FROM UserTask task join House h on h.houseId = task.houseId join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ " join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId join HouseResource log on h.houseId = log.houseId left join user u on u.uId = task.userId "
				+ " left join employee emp on emp.employeeId = task.operatorId  "
				+ "where log.status <>4  and h.houseId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		// 通过 区域 进行搜索 房源信息
		
		if (req.getCityType() > 0) {
			count_sql += " and area.parentId = ? ";
			sql += " and area.parentId = ? ";
			pars.add(req.getCityType());
			
		}else{
			count_sql += " and area.parentId = 2 ";
			sql += " and area.parentId = 2 ";
		}
		if ( req.getParentId() > 0) {
				// 按照行政区域搜索
				count_sql += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getParentId());
		} 
		if (req.getAreaId() > 0 ) {
				// 按照 行政区下面的 片区 进行搜索
				count_sql += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
		}
		
		//按照小区 名称 搜索
		if (StringUtils.isNotBlank(req.getEstateName())) {
			count_sql += " and ( sube.name like ? or sube.road like ? ) ";
			sql += " and ( sube.name like ? or sube.road like ? ) ";
			pars.add("%" + req.getEstateName() + "%");
			pars.add("%" + req.getEstateName() + "%");
		}

		// 按照 出售状态搜索
		if (req.getHouseState() != 0) {
			count_sql += " and log.houseState = ? ";
			sql += " and log.houseState = ? ";
			pars.add(req.getHouseState());
		}

		// 按照 task 任务状态
		if (!StringUtils.isBlank(req.getTaskState())) {
			count_sql += " and task.taskStatus in ( " + req.getTaskState() + " )";
			sql += " and task.taskStatus in ( " + req.getTaskState() + " )";
		}
		//按照 负责人 搜索
		if (!StringUtils.isBlank(req.getEmployeeName())) {
			count_sql += " and emp.realName like ? ";
			sql += " and emp.realName like ? ";
			pars.add("%"+req.getEmployeeName()+"%");
		}
		
		//按照 发布人搜索
		if (!StringUtils.isBlank(req.getUserName())) {
			count_sql += " and u.realName like ? ";
			sql += " and u.realName like ? ";
			pars.add("%"+req.getUserName()+"%");
		}
		if (!StringUtils.isBlank(req.getUserMobile())) {
			count_sql += " and u.mobile = ? ";
			sql += " and u.mobile = ? ";
			pars.add(req.getUserMobile());
		}
		
		//按照 任务领取时间  搜索
		if(!StringUtils.isBlank(req.getCreateTimeStart())){
			count_sql += " and task.createTime >= ? ";
			sql += " and task.createTime >= ? ";
			pars.add(req.getCreateTimeStart());
		}
		if(!StringUtils.isBlank(req.getCreateTimeEnd())){
			count_sql += " and task.createTime < ? ";
			sql += " and task.createTime < ? ";
			pars.add(req.getCreateTimeEnd());
		}
		
		//按照 任务审核时间  搜索
		if(!StringUtils.isBlank(req.getFinishDateStart())){
			count_sql += " and task.createTime >= ? ";
			sql += " and task.createTime >= ? ";
			pars.add(req.getFinishDateStart());
		}
		if(!StringUtils.isBlank(req.getFinishDateEnd())){
			count_sql += " and task.finishDate < ? ";
			sql += " and task.finishDate < ? ";
			pars.add(req.getFinishDateEnd());
		}
		
		sql +=" order by task.createTime desc ";
		
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		PageResponse<UserLookHouseRes> page = new PageResponse<UserLookHouseRes>();
		List<UserLookHouseRes> looks= new ArrayList<UserLookHouseRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage() - 1) * req.getRows());// 起始下标
			query.setMaxResults(req.getRows());// 查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = ((total-1) / req.getRows()+1);
			page.setTotalPage(n);// 总页数
			List<Object[]> objs = query.getResultList();
			if (objs != null && objs.size() > 0) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				
				for (Object[] row : objs) {
					int i = 0;
					UserLookHouseRes house = new UserLookHouseRes();
					house.setHouseId(Integer.parseInt(row[i++]+""));
					
					house.setCityId(Integer.parseInt(row[i++]+""));
					house.setCityName(row[i++]+"");
					
					house.setAreaId(Integer.parseInt(row[i++]+""));
					house.setAreaName(row[i++]+"");
					house.setTownId(Integer.parseInt(row[i++]+""));
					house.setTownName(row[i++]+"");
					house.setEstateId(Integer.parseInt(row[i++]+""));
					house.setEstateName(row[i] + (row[i+1] == null ? "" : row[i+1]+""));
					i++;
					i++;
					house.setBuiling(row[i++]+"");
					house.setRoom(row[i++]+"");
					String houseState = row[i] == null ? "0" : row[i]+"";//出售状态
					i++;
					house.setHouseState(Integer.parseInt(houseState));
					house.setHouseStateStr(HouseStateEnum.getByValue(Integer.parseInt(houseState)).getDesc());
					i++;
					//row[i] 房子最后一次 发布时间
					int actionType = DataUtil.toInt(row[i++]);
					int houseStatus = DataUtil.toInt(row[i++]);
					house.setUserId(Integer.parseInt((row[i] ==  null ? "0" : row[i]+"")));
					i++;
					house.setUserName((row[i] ==  null ? "-" : row[i]+""));
					i++;
					house.setId(Integer.parseInt(row[i++]+""));
					house.setCreateTime((Date) row[i++]);
					house.setCreateTimeStr(sdf.format(house.getCreateTime()));
					house.setTaskStatus(Integer.parseInt(row[i++]+""));
					house.setTaskStatusStr(EntityUtils.UserTaskStatusEnum.getByValue(house.getTaskStatus()).getDesc());
					house.setEmployeeId(Integer.parseInt((row[i] ==  null ? "0" : row[i]+"")));
					i++;
					house.setEmployeeName((row[i] ==  null ? "-" : row[i]+""));
					i++;
					//row[i] 图片数量
					i++;
					house.setFinishDate((Date) row[i]);
					if(house.getFinishDate() != null){
						house.setFinishDateStr(sdf.format(house.getFinishDate()));
					}else{
						house.setFinishDateStr("-");
					}
					looks.add(house);
				}
			}
		}
		page.setRows(looks);
		return page;
	
	}
	
	/**
	 * 查看BD某个时间段的排班情况
	 */
	@Override
	public PageResponse<PlanRes> planList(PlanReq req) {
		log.info("查看BD某个时间段的排班情况: {} ",ReflectionToStringBuilder.toString(req));
		
		PageResponse<PlanRes> res = new PageResponse<PlanRes>();
		String sql =" SELECT task.id , sube.name estateName , task.taskDate ,emp.employeeId , emp.realName ,area.name areaName "
				+" FROM BdTask task join House h on h.houseId = task.houseId join Area sube on sube.areaId = h.estateId "
				+" join Employee emp on emp.employeeId = task.employeeId left join Area area on emp.areaId = area.areaId ";
		sql +=" where task.employeeId <> 0  and task.employeeId =? and DATE_FORMAT(task.taskDate,'%Y-%m-%d') >=? and DATE_FORMAT(task.taskDate,'%Y-%m-%d') <=? ";
		
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter(1, req.getBdId());
		
		//计算出 起始时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DAY_OF_MONTH, req.getAction()*7);// 当使用action的时候. 才使用这句
		
		//现在使用的是  起始 时间作为 参数
		if(req.getStart() == null){
			req.setStart(sdf.format(new Date()));
		}
		try {
			cal.setTime(sdf.parse(req.getStart()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;// 获得今天是一周的第几天，星期日是第1天，星期一是第2天
		int mondayPlus;
		if (dayOfWeek == 1) {
			//星期一
			mondayPlus = 0;
		} else {
			mondayPlus = 1 - dayOfWeek;
		}
		cal.add(Calendar.DATE, mondayPlus);
		Date monday = cal.getTime();
		query.setParameter(2, sdf.format(monday));
		
		//计算出 截止时间(星期日)
		cal.add(Calendar.DATE,7);
		Date sunday = cal.getTime();
		query.setParameter(3, sdf.format(sunday));
		
		log.info("参数: {} ","BdId:"+req.getBdId()+" , 起始时间: "+sdf.format(monday) +" , 截止时间: "+sdf.format(sunday));
		
		sql += " order by task.taskDate asc ";
		List<Object[]> rows= query.getResultList();
		List<PlanRes> plans = new ArrayList<PlanRes>();
		if(rows != null && rows.size()>0){
			for (Object[] row : rows) {
				int i = 0;
				PlanRes plan = new PlanRes();
				plan.setId(Integer.parseInt(row[i++]+""));
				plan.setEstateName(row[i++]+"");
				Date d =(Date) row[i++];
				plan.setTaskDate(d.getTime());
				int eId = Integer.parseInt(row[i++]+"");
				plan.setBdName(row[i++]+"");
				plan.setAreaName(row[i] == null ? "无":row[i]+"");
				plans.add(plan);
			}
		}
		res.setRows(plans);
		return res;
	}
	
	
	/**
	 *  随机抽查看房 选择房子,启动 看房任务
	 */
	@Override
	public Response randomBdTask(RandomReq req) {
		log.info("随机抽查看房参数: {} ",ReflectionToStringBuilder.toString(req));
		
		Response res = new Response();
		//(log.actionType in(1,2,3) or (log.actionType =4 and log.status = 2 ) ) and  
		
		String count_sql=" SELECT COUNT(1) FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId  join HouseResource log on log.houseId = h.houseId  "
				+" left join BdTask dbt on dbt.id = h.currBDTaskId left join UserTask usertask on usertask.id = h.currUserTaskId "
				+" where log.status <> 4 and h.houseId <>0 and (dbt.taskStatus is null or dbt.taskStatus ='' or dbt.taskStatus =0 ) "
				+"  and (usertask.taskStatus is null or usertask.taskStatus ='' or usertask.taskStatus =0 ) ";
		String sql=" SELECT h.houseId FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId  join HouseResource log on log.houseId = h.houseId  "
				+" left join BdTask dbt on dbt.id = h.currBDTaskId left join UserTask usertask on usertask.id = h.currUserTaskId "
				+" where log.status <> 4 and h.houseId <>0 and (dbt.taskStatus is null or dbt.taskStatus ='' or dbt.taskStatus =0 ) "
				+"  and (usertask.taskStatus is null or usertask.taskStatus ='' or usertask.taskStatus =0 ) ";
		
		List<Object> pars =new ArrayList<Object>();
		// 通过 区域 进行搜索 房源信息
		if (req.getCityType() > 0) {
			count_sql += " and area.parentId = ? ";
			sql += " and area.parentId = ? ";
			pars.add(req.getCityType());

		}
		if (req.getParentId() > 0) {
			// 按照行政区域搜索
			count_sql += " and area.areaId = ? ";
			sql += " and area.areaId = ? ";
			pars.add(req.getParentId());
		}
		if (req.getAreaId() > 0) {
			// 按照 行政区下面的 片区 进行搜索
			count_sql += " and town.areaId = ? ";
			sql += " and town.areaId = ? ";
			pars.add(req.getAreaId());
		}
		
		if(req.getHouseState() !=0){
			//出租/出售状态
			count_sql += " and log.houseState in (3,?) ";
			sql += " and log.houseState in (3,?) ";
			pars.add(req.getHouseState());
		}
		
		//面积
		if(req.getSpaceAreaStart() != 0){
			count_sql += " and h.spaceArea >= ? ";
			sql += " and h.spaceArea >= ? ";
			pars.add(req.getSpaceAreaStart());
		}
		if(req.getSpaceAreaEnd() != 0){
			count_sql += " and h.spaceArea<= ? ";
			sql += " and h.spaceArea <= ? ";
			pars.add(req.getSpaceAreaEnd());
		}
		//发布时间
		if(!StringUtils.isBlank(req.getPublishDateStart())){
			count_sql += " and DATE_FORMAT(log.publishDate,'%Y-%m-%d') >= ? ";
			sql += " and DATE_FORMAT(log.publishDate,'%Y-%m-%d') >= ? ";
			pars.add(req.getPublishDateStart());
		}
		if(!StringUtils.isBlank(req.getPublishDateEnd())){
			count_sql += " and DATE_FORMAT(log.publishDate,'%Y-%m-%d') <= ? ";
			sql += " and DATE_FORMAT(log.publishDate,'%Y-%m-%d') <= ? ";
			pars.add(req.getPublishDateEnd());
		}
		
		//出租/出售价格
		if(req.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()){
			//选择 出租
			if(req.getRentPriceStart() != 0){
				//出租价格
				count_sql += " and  log.rentPrice >= ? ";
				sql += " and  log.rentPrice >= ? ";
				pars.add(req.getRentPriceStart());
			}
			if(req.getRentPriceEnd() != 0){
				//出租价格
				count_sql += " and  log.rentPrice <= ? ";
				sql += " and  log.rentPrice <= ? ";
				pars.add(req.getRentPriceEnd());
			}
		}else{
			//出售
			if(req.getSellPriceStart() != 0){
				//出售价格
				count_sql += " and  log.sellPrice >= ? ";
				sql += " and  log.sellPrice >= ? ";
				pars.add(req.getSellPriceStart());
			}
			if(req.getSellPriceEnd() != 0){
				//出售价格
				count_sql += " and  log.sellPrice <= ? ";
				sql += " and  log.sellPrice <= ? ";
				pars.add(req.getSellPriceEnd());
			}
		}
		
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		log.info("满足搜索条件的 房源数量: {} " ,total);
		
		if (total > 0) {
			query.setFirstResult(0).setMaxResults(req.getCheckNum());
			if(total > req.getCheckNum()){
				total = req.getCheckNum();
			}
			List<Integer> houseIds = query.getResultList();
			if(houseIds != null && houseIds.size()>0){
				StringBuffer sb = new StringBuffer();
				String jpql="from HouseResource hr where hr.houseId = ";
				for (int i = 0; i < houseIds.size() && i < total; i++) {
					int id = houseIds.get(i);
					sb.append(id+",");
					BdTask task =new BdTask();
					task.setCreateTime(new Date());
					task.setManageId(req.getManageId());
					task.setHouseId(id);
					task.setTaskStatus(EntityUtils.TaskStatusEnum.START.getValue());//开始看房任务,未预约
					this.getEntityManager().persist(task);
					
					//轮询 审核中的时候, 生产这条BD 看房任务之前, 直接把轮询任务审核成功 
					List<HouseResource> hrs= this.getEntityManager().createQuery(jpql+id).getResultList();
					if(hrs != null && hrs.size()>0){
						HouseResource hr =hrs.get(0);
						if(hr.getActionType() == EntityUtils.ActionTypeEnum.LOOP.getValue() && hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()){
							//轮询
							custServService.checkLunXunAndSetLunXunSuccess(id, "BD生成看房任务的时候,需要把轮询的审核成功.就是回滚.");
						}
					}
					
					//修改 house的 当前的task的id
					House h = this.getEntityManager().find(House.class, id);
					h.setCurrBDTaskId(task.getId());
					this.getEntityManager().merge(h);
					
					//this.getEntityManager().createQuery("update House h set h.currBDTaskId = "+task.getId() +" where h.houseId = "+id).executeUpdate();
				}
				res.setMessage(total+"");
				log.info("抽取出来的房源数量: {} ,houseIds : {} ",total,sb.toString());
			}
		}else{
			res.setErrorCode(1);
			res.setMessage("没有满足条件的房源!");
		}
		return res;
	}
	
	/**
	 * 选择房子,启动 看房任务
	 */
	@Override
	public Response addBdTask(LookHouseReq req) {
		Response res = new Response();
		if(req.getHouseIds() != null && req.getHouseIds().length()>0){
			String[] ids = req.getHouseIds().split(",");
			String jpql="from HouseResource hr where hr.houseId = ";
			for (String id : ids) {
				BdTask task =new BdTask();
				task.setCreateTime(new Date());
				task.setManageId(req.getManageId());
				task.setHouseId(Integer.parseInt(id));
				task.setTaskStatus(EntityUtils.TaskStatusEnum.START.getValue());//开始看房任务,未预约
				this.getEntityManager().persist(task);
				
				//轮询 审核中的时候, 生产这条BD 看房任务之前, 直接把轮询任务审核成功
				List<HouseResource> hrs= this.getEntityManager().createQuery(jpql+id).getResultList();
				if(hrs != null && hrs.size()>0){
					HouseResource hr =hrs.get(0);
					if(hr.getActionType() == EntityUtils.ActionTypeEnum.LOOP.getValue() && hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()){
						//轮询
						custServService.checkLunXunAndSetLunXunSuccess(Integer.parseInt(id), "BD生成看房任务的时候,需要把轮询的审核成功.就是回滚.");
					}
				}
				
				//修改 house的 当前的task的id
				this.getEntityManager().createQuery("update House h set h.currBDTaskId = "+task.getId() +" where h.houseId = "+id).executeUpdate();
			}
			this.getEntityManager().flush();
		}else{
			res.setErrorCode(1);
			res.setMessage("请选择房子,IDS不能为空.");
		}
		return res;
	}
	
	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<LookHouseRes> lookHouseList(LookHouseReq req) {
		String count_sql="SELECT COUNT(task.id) FROM BdTask task join House h on h.houseId = task.houseId join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ " join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId left join HouseResource log on log.houseId = h.houseId left join user u on u.uId = log.userId "
				+ " left join employee emp on emp.employeeId = task.employeeId "
				+" where log.status <> 4 and h.houseId <>0 ";
		String sql="SELECT h.houseId,city.areaId cityId,city.name cityName,area.areaId areaId , area.name areaName , town.areaId townId,town.name townName,sube.areaId estateId, "
				+ "sube.name estateName,sube.road road,h.building,h.room,log.houseState houseState ,log.publishDate publishDate,log.actionType logType ,log.status,u.uid, u.realName , "
				+" task.id taskId,task.createTime addTaskTime , task.taskStatus ,task.employeeId , emp.realName empName , h.picNum,task.taskDate "
				+ " FROM BdTask task join House h on h.houseId = task.houseId join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ " join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId left join HouseResource log on h.houseId = log.houseId left join user u on u.uId = log.userId "
				+ " left join employee emp on emp.employeeId = task.employeeId  "
				+ "where log.status <>4  and h.houseId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		// 通过 区域 进行搜索 房源信息
		
		if (req.getCityType() > 0) {
			count_sql += " and area.parentId = ? ";
			sql += " and area.parentId = ? ";
			pars.add(req.getCityType());
			
		}
		if ( req.getParentId() > 0) {
				// 按照行政区域搜索
				count_sql += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getParentId());
		} 
		if (req.getAreaId() > 0 ) {
				// 按照 行政区下面的 片区 进行搜索
				count_sql += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
		}
		
		//按照小区 名称 搜索
		if (StringUtils.isNotBlank(req.getEstateName())) {
			count_sql += " and ( sube.name like ? or sube.road like ? ) ";
			sql += " and ( sube.name like ? or sube.road like ? ) ";
			pars.add("%" + req.getEstateName() + "%");
			pars.add("%" + req.getEstateName() + "%");
		}

		// 按照 出售状态搜索
		if (req.getHouseState() != 0) {
			count_sql += " and log.houseState = ? ";
			sql += " and log.houseState = ? ";
			pars.add(req.getHouseState());
		}

		// 按照 task 任务状态
		if (!StringUtils.isBlank(req.getTaskState())) {
			count_sql += " and task.taskStatus in ( " + req.getTaskState() + " )";
			sql += " and task.taskStatus in ( " + req.getTaskState() + " )";
		}
		//按照 负责人 搜索
		if (!StringUtils.isBlank(req.getEmployeeName())) {
			count_sql += " and emp.realName like ? ";
			sql += " and emp.realName like ? ";
			pars.add("%"+req.getEmployeeName()+"%");
		}
		
		//按照 发布人搜索
		if (!StringUtils.isBlank(req.getUserName())) {
			count_sql += " and u.realName like ? ";
			sql += " and u.realName like ? ";
			pars.add("%"+req.getUserName()+"%");
		}
		
		//按照 看房时间  搜索
		if(!StringUtils.isBlank(req.getTaskDateStart())){
			count_sql += " and task.taskDate >= ? ";
			sql += " and task.taskDate >= ? ";
			pars.add(req.getTaskDateStart());
		}
		if(!StringUtils.isBlank(req.getTaskDateEnd())){
			count_sql += " and task.taskDate < ? ";
			sql += " and task.taskDate < ? ";
			pars.add(req.getTaskDateEnd());
		}
		
		sql +=" order by task.createTime desc ";
		
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		PageResponse<LookHouseRes> page = new PageResponse<LookHouseRes>();
		List<LookHouseRes> looks= new ArrayList<LookHouseRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage() - 1) * req.getRows());// 起始下标
			query.setMaxResults(req.getRows());// 查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = ((total-1) / req.getRows()+1);
			page.setTotalPage(n);// 总页数
			List<Object[]> objs = query.getResultList();
			if (objs != null && objs.size() > 0) {
				for (Object[] row : objs) {
					int i = 0;
					LookHouseRes house = new LookHouseRes();
					house.setHouseId(Integer.parseInt(row[i++]+""));
					
					house.setCityId(Integer.parseInt(row[i++]+""));
					house.setCityName(row[i++]+"");
					
					house.setAreaId(Integer.parseInt(row[i++]+""));
					house.setAreaName(row[i++]+"");
					house.setTownId(Integer.parseInt(row[i++]+""));
					house.setTownName(row[i++]+"");
					house.setEstateId(Integer.parseInt(row[i++]+""));
					house.setEstateName(row[i] + (row[i+1] == null ? "" : row[i+1]+""));
					i++;
					i++;
					house.setBuiling(row[i++]+"");
					house.setRoom(row[i++]+"");
					String houseState = row[i] == null ? "0" : row[i]+"";//出售状态
					i++;
					house.setHouseState(Integer.parseInt(houseState));
					house.setHouseStateStr(HouseStateEnum.getByValue(Integer.parseInt(houseState)).getDesc());
					house.setPublishDate((Date) row[i++]);
					int actionType = DataUtil.toInt(row[i++]);
					int houseStatus = DataUtil.toInt(row[i++]);
					house.setUserId(Integer.parseInt((row[i] ==  null ? "0" : row[i]+"")));
					i++;
					house.setUserName((row[i] ==  null ? "-" : row[i]+""));
					i++;
					house.setId(Integer.parseInt(row[i++]+""));
					house.setAddTaskDate((Date) row[i++]);
					house.setTaskState(Integer.parseInt(row[i++]+""));
					house.setTaskStateStr(EntityUtils.TaskStatusEnum.getByValue(house.getTaskState()).getDesc());
					house.setEmployeeId(Integer.parseInt((row[i] ==  null ? "0" : row[i]+"")));
					i++;
					house.setEmployeeName((row[i] ==  null ? "-" : row[i]+""));
					i++;
					house.setPicNum(Integer.parseInt((row[i] ==  null ? "0" : row[i]+"")));
					i++;
					house.setTaskDate((Date) row[i]);
					looks.add(house);
				}
			}
		}
		page.setRows(looks);
		return page;
	}
	
	
	public Response single(CSSingleRequest css) {

		logger.info("bdtask-->houseResourceId  : {}" ,css.getId());
		BdTask bdTask = getEntityManager().find(BdTask.class, css.getId());
		List<BdTaskMakeCallHistory> bdmk = getEntityManager().createQuery("select bdtmc from BdTaskMakeCallHistory bdtmc where bdtmc.bdTaskId=:bdTaskId ").setParameter("bdTaskId", bdTask.getId()).getResultList();

		HouseResource hourseResource = getEntityManager().find(HouseResource.class ,bdTask.getHouseId());
		if (hourseResource == null) {
			return new Response(1586002, "查无此记录");
		}
		BdTaskResponse bdt = new BdTaskResponse();
		List<BdTaskMakeCallHistoryResponse> bdtmch = new ArrayList<BdTaskMakeCallHistoryResponse>();
		HouseResponse h = new HouseResponse();
		HouseResourceResponse hrr = new HouseResourceResponse();
		List<HouseResourceContactResponse> hrcrList = new ArrayList<HouseResourceContactResponse>();
		List<HouseResourceHistoryResponse> slhrList = new ArrayList<HouseResourceHistoryResponse>();
		
		Residence house = getEntityManager().find(Residence.class,bdTask.getHouseId());
		logger.info("house id:{}",house.getHouseId());
		List<HouseResourceContact> shList = getHostListBySourceLogId(bdTask.getHouseId(),hourseResource);
		
		if (shList != null && shList.size() > 0) {
			for (HouseResourceContact  sh : shList) {
				HouseResourceContactResponse shr = new HouseResourceContactResponse();
				BeanUtils.copyProperties(sh,shr);
				hrcrList.add(shr);
			}
			
		}else {
			hrcrList = null;
		}
		
		if (bdmk != null && bdmk.size() > 0) {
			for (BdTaskMakeCallHistory  mc : bdmk) {
				BdTaskMakeCallHistoryResponse shr = new BdTaskMakeCallHistoryResponse();
				BeanUtils.copyProperties(mc,shr);
				shr.setTaskStatusStr(EntityUtils.TaskStatusEnum.getByValue(shr.getTaskStatus()).getDesc());
				bdtmch.add(shr);
			}
			
		}else {
			bdmk = null;
		}
		
		BeanUtils.copyProperties(hourseResource, hrr);
		BeanUtils.copyProperties(house, h);
		BeanUtils.copyProperties(bdTask, bdt);
		
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

		int cityId = 0 ;
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
					cityId = Integer.parseInt(arr[7] + "");
				}
			}
			
			SubEstate subEstate = getEntityManager().find(SubEstate.class, house.getSubEstateId());
			if (subEstate != null) {
				h.setSubEstateId(subEstate.getId());
				h.setSubEstateStr(subEstate.getName());
			}
			
			String jpql = "select e from Employee e where 1=1 and  e.role in (4,5) and  e.disable = false ";//BD经理,BD人员
			System.out.println(cityId);
			if (cityId > 0) {
				
				jpql += "and ( e.cityId = 0 or  e.cityId = "+cityId + " )";
			}
			List<EmployeeResponse> empList = new ArrayList<EmployeeResponse>();
			List<Employee> emp = getEntityManager().createQuery(jpql).getResultList();
			if (emp != null && emp.size() > 0) {
				for (Employee  one : emp) {
					EmployeeResponse shr = new EmployeeResponse();
					BeanUtils.copyProperties(one,shr);
					empList.add(shr);
					logger.info(shr.getRealName() + ":1:"  + ":");
				}
				
			}else {
				empList = null;
			}
		
		Response response = new MakeCallResponse(hrcrList,h,hrr,slhrList,bdtmch,bdt,empList);
		
		return response;
	
		
	}
	
	@SuppressWarnings("unchecked")
	private List<HouseResourceContact> getHostListBySourceLogId(int houseId,HouseResource hr){
		
		Query query = getEntityManager().createQuery(
				"select sh from HouseResourceContact sh WHERE sh.houseId=:houseId and sh.enable=true and sh.status != 3 order by sh.contactId desc ");
		query.setParameter("houseId", houseId);
		query.setMaxResults(7);
		List<HouseResourceContact> list = query.getResultList();
		return list;
	}
	
	public Response submit(SubmitRequest sr) {
		BdTask bdTask = getEntityManager().find(BdTask.class, sr.getId());
		if(bdTask.getTaskStatus() == EntityUtils.TaskStatusEnum.ORDER_AGAIN.getValue() || bdTask.getTaskStatus() == EntityUtils.TaskStatusEnum.START.getValue() || bdTask.getTaskStatus() == EntityUtils.TaskStatusEnum.LOOK_FALIED.getValue() ) {
			
		}else {
			return new Response(1,"hasFinshed");
		}
		Date date = null;
		SimpleDateFormat sdfFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdfFormat.parse(sr.getLookDate() + " " + sr.getLookTime());
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		String sql ="select count(1) from BdTask where employeeId = " + sr.getLookhouseEmpId() + " and taskDate = '" + sr.getLookDate() + " " + sr.getLookTime() +"'" ;
		if (DataUtil.toInt(getEntityManager().createNativeQuery(sql).getSingleResult()) > 0) {
			return new Response(2,"hasOwnedTask");
		}
		bdTask.setTaskStatus(sr.getStatus());
		bdTask.setPhoneMakerNum(bdTask.getPhoneMakerNum() + 1) ;
		bdTask.setExplainStr(sr.getNote());
		if (sr.getStatus() == TaskStatusEnum.ORDER_SUCCESS.getValue()) {
			bdTask.setEmployeeId(sr.getLookhouseEmpId());
			bdTask.setTaskDate(date);
		}
		
		getEntityManager().merge(bdTask);
		Employee e = getEntityManager().find(Employee.class, sr.getOperatorId());
		BdTaskMakeCallHistory bdt = new BdTaskMakeCallHistory();
		if (sr.getStatus() == TaskStatusEnum.ORDER_SUCCESS.getValue()) {
			Employee e1 = getEntityManager().find(Employee.class, sr.getLookhouseEmpId());
			bdt.setEmployeeName(e1.getRealName());
			bdt.setEmployeeId(sr.getLookhouseEmpId());
			bdt.setTaskDate(bdTask.getTaskDate());
		}
		
		bdt.setTaskStatus(sr.getStatus());
		bdt.setBdTaskId(sr.getId());
		bdt.setNote(sr.getNote());
		bdt.setPhoneMaker(sr.getOperatorId());
		bdt.setPhoneMakerName(e.getRealName());
		getEntityManager().persist(bdt);
		return new Response(0,"成功");
	}
	
	public Response userTaskSubmit(UserTaskSubmitRequest uts) {
		logger.info("submit uts parameter:{}",ReflectionToStringBuilder.toString(uts));
		UserTask ut = getEntityManager().find(UserTask.class , uts.getId());
		if (ut.getTaskStatus() == EntityUtils.UserTaskStatusEnum.FAILD.getValue() || ut.getTaskStatus() == EntityUtils.UserTaskStatusEnum.SUCCESS.getValue()) {
			return new Response(1,"hasFinshed");
		} else if (ut.getTaskStatus() != EntityUtils.UserTaskStatusEnum.ING.getValue()) {
			return new Response(2,"illegelStatus");
		}
		
		ut.setAfterDecorateType(Integer.parseInt(uts.getDecorateType()));
		ut.setTaskStatus(uts.getStatus());
		ut.setOperatorId(uts.getOperatorId());
		ut.setLayers(uts.getLayers());
		ut.setNote(uts.getNote() +" 选择的房型 "+EntityUtils.HouseDecorateTypeEnum.getByValue(Integer.parseInt(uts.getDecorateType())).getDesc());
		ut.setFinishDate(new Date());//审核完成
		getEntityManager().merge(ut);
		
		Residence house = this.getEntityManager().find(Residence.class, ut.getHouseId());
		
		if(uts.getStatus() == EntityUtils.UserTaskStatusEnum.SUCCESS.getValue()){
			String jpql = "select hf  from HouseImageFile hf  where hf.houseId= ? and hf.enable = 1";
			Query query = getEntityManager().createQuery(jpql).setParameter(1, ut.getHouseId());
			List<HouseImageFile> hif = query.getResultList();
			if (hif != null && hif.size() > 0) {
				for (HouseImageFile houseImageFile : hif) {
					houseImageFile.setEnable(0);
					getEntityManager().merge(houseImageFile);
				}
			}
			jpql = "select hf from HouseImageFile hf where hf.userTaskId= ? ";
			query = getEntityManager().createQuery(jpql).setParameter(1, ut.getId());
			hif = query.getResultList();
			if (hif != null && hif.size() > 0) {
				for (HouseImageFile houseImageFile : hif) {
					houseImageFile.setEnable(1);
					getEntityManager().merge(houseImageFile);
				}
			}
			
			
			jpql = "select hf  from HouseSupportingMeasures hf  where hf.houseId= ? and hf.enable = 1";
			query = getEntityManager().createQuery(jpql).setParameter(1, ut.getHouseId());
			List<HouseSupportingMeasures> hs = query.getResultList();
			if (hs != null && hs.size() > 0) {
				for (HouseSupportingMeasures hsm : hs) {
					hsm.setEnable(0);
					getEntityManager().merge(hsm);
				}
			}
			jpql = "select hf from HouseSupportingMeasures hf where hf.userTaskId= ? ";
			query = getEntityManager().createQuery(jpql).setParameter(1, ut.getId());
			hs = query.getResultList();
			if (hs != null && hs.size() > 0) {
				for (HouseSupportingMeasures hsm : hs) {
					hsm.setEnable(1);
					getEntityManager().merge(hsm);
				}
			}
			
			//修改房屋装修状况
			if(house != null && StringUtils.isNotBlank(uts.getDecorateType())){
				house.setDecorateType(Integer.parseInt(uts.getDecorateType()));
				
				house.setLivingRoomSum(ut.getAfterLivingRoomSum());
				house.setBedroomSum(ut.getAfterBedroomSum());
				house.setWcSum(ut.getAfterWcSum());
				this.getEntityManager().merge(house);
			}
			
			//添加支付记录
			AddPayReq req = new AddPayReq();
			req.setSource(EntityUtils.AwardTypeEnum.LOOKHOUSE.getValue());
			req.setUserId(ut.getUserId());
			Pay  pay = PayUtil.createPay(req);
			log.info("支付 添加一条记录, 审核 看房成功, {}",ReflectionToStringBuilder.toString(pay));
			this.getEntityManager().persist(pay);
			
		}
		
		//发送推送消息
		User user = this.getEntityManager().find(User.class,ut.getUserId());
		Estate estate = this.getEntityManager().find(Estate.class, house.getEstateId());
		String buildingStr = "";
		if(!house.getBuilding().equals("0")){
			buildingStr = house.getBuilding()+"栋";
		}
		pushUtils.sendUserLookHouseMsg(user.getMobile(),estate.getName()+buildingStr+house.getRoom()+"室",
				EntityUtils.UserTaskStatusEnum.getByValue(uts.getStatus()).getDesc());
		
		return new Response(0,"成功");
	}
	
}
