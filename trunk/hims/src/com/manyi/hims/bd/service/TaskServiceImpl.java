package com.manyi.hims.bd.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.manyi.hims.BaseService;
import com.manyi.hims.bd.controller.TaskController.HisTaskDetailResponse;
import com.manyi.hims.bd.controller.TaskController.TaskPromptResponse;
import com.manyi.hims.bd.controller.TaskController.TaskResponse;
import com.manyi.hims.bd.service.EmployeeService.TaskDetailsResponse;
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.util.EntityUtils.TaskStatusEnum;
@Service(value = "taskService")
@Scope(value = "singleton")
public class TaskServiceImpl extends BaseService implements TaskService {

	@Autowired
	EmployeeService employeeService;
	@Override
	public Page<TaskResponse> findTaskByUserId(int userId,
			Integer taskStatus, Date beginDate, Date endDate, int first,
			int max) {

		String sql = "select b.id,b.houseId,b.taskDate,b.taskStatus,a.name,h.building,h.room,ad.address from bdtask b,house h,area a,(SELECT address.estateId AS estateId, GROUP_CONCAT(address.address SEPARATOR '/') AS address  FROM  Address address GROUP BY address.estateId) ad where b.houseId = h.houseId and h.estateId = a.areaId and ad.estateId = h.estateId and  b.employeeId = ?";
				
		String sql_count = "select count(1) from bdtask b where b.employeeId = ?";
		List<Object> param = new ArrayList<Object>();
		param.add(userId);
         
		if(beginDate != null){
			sql += " and b.taskDate >= ?";
			sql_count += " and b.taskDate >= ?";
			param.add(beginDate);
		}
        if(endDate != null){
        	sql += " and b.taskDate < ?";
			sql_count += " and b.taskDate < ?";
			param.add(endDate);
		}
        // 历史任务  按照 时间倒序；     只有一个结束时间 null -- endDate ，
        // 今日、未来任务 按时间正顺序 
        if (beginDate == null && endDate != null) {
        	sql += " order by b.taskDate desc ";
        } else {
        	sql += " order by b.taskDate asc ";
        }
        
		Query query_count = getEntityManager().createNativeQuery(sql_count);
		Query query = getEntityManager().createNativeQuery(sql);
		
		
		for (int i = 0; i < param.size(); i++) {
			//count_query.setParameter(i + 1, pars.get(i));
			query.setParameter(i + 1, param.get(i));
			query_count.setParameter(i + 1, param.get(i));
		}
		List<BigInteger> counts = query_count.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		Page<TaskResponse> pages = new Page<TaskResponse>(first,max);
		if(total<=0){
			pages.setRows(new ArrayList<TaskResponse>(0));
		}else
			pages.setTotal((int)total);
		List<Object[]> task = query.setFirstResult(first).setMaxResults(max).getResultList();
		
		List<TaskResponse> infos = mapResult(task);
		
		pages.setRows(infos);
		return pages;
		
	}

	private List<TaskResponse> mapResult(List<Object[]> task) {
       List<TaskResponse> infos = new ArrayList<TaskResponse>();
		for(int i=0;i<task.size();i++){
			TaskResponse resp =  new TaskResponse();
			
			Object[] row = task.get(i);
			resp.setId(Long.parseLong(row[0].toString()));
			resp.setHouseId(Integer.parseInt(row[1].toString()));
			resp.setDate((Date)row[2]);
			resp.setTaskStatus(Integer.parseInt(row[3].toString()));
			resp.setTaskStatusCn(TaskStatusEnum.getByValue(Integer.parseInt(row[3].toString())).getDesc4app());
			resp.setHouseName(row[4].toString());
			resp.setBuilding(row[5].toString());
			resp.setRoom(row[6].toString());
			resp.setAddress(row[7].toString());

			infos.add(resp);
		}
		return infos;
	}

	/**
	 * 周一00:00:00  到 下周一 00:00:00  之间的数据
	 * 
	 * 例如：
	 * 2014-06-23 00:00:00 <= 数据  < 2014-06-30 00:00:00
	 * 
	 */
	public TaskPromptResponse countWeekTask(int employeeId) {
		String jpql = "select count(1) from bdtask b where b.taskDate >= date_sub(curdate(),INTERVAL WEEKDAY(curdate()) DAY) and b.taskDate <date_sub(curdate(),INTERVAL WEEKDAY(curdate()) - 7 DAY)";
	    
	    TaskPromptResponse prom = new TaskPromptResponse();
		/*本周任务数*/
	    jpql += " and b.employeeId=?";
	    
	    prom.setWeekTaskCount(weekTashCount(jpql,null,employeeId));
	    jpql += " and b.taskstatus = ?";
	    /*已完成任务数*/
	    prom.setFinishCount(weekTashCount(jpql, TaskStatusEnum.LOOK_SUCCESS.getValue(),employeeId));
	    /*失败任务数*/
	    prom.setFailCount(weekTashCount(jpql, TaskStatusEnum.LOOK_FALIED.getValue(),employeeId));
	    /*未完成任务数*/
	    prom.setUnfinishCount(weekTashCount(jpql, TaskStatusEnum.ORDER_SUCCESS.getValue(),employeeId));

		return prom;
	}

	private int weekTashCount(String sql,Integer status,int employeeId) {
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter(1, employeeId);
		if(status != null) {
			query.setParameter(2,status);
		}
		
		Object obj = query.getSingleResult();
		if(obj == null)
			return 0;
		return Integer.parseInt(obj.toString());
		
	}

	@Override
	public HisTaskDetailResponse hisTaskDetail(int taskId, int taskStatus) {

		/* 获取任务详情 */
		HisTaskDetailResponse resp = new HisTaskDetailResponse();
		resp.setTaskdetail(employeeService.taskDetails(taskId));
		TaskDetailsResponse taskDetails = resp.getTaskdetail();
		
		Integer houseId = null;
		BdTask bdTask = this.getEntityManager().find(BdTask.class, taskId);
		
		if (resp.getTaskdetail() != null) {
			if (taskStatus == TaskStatusEnum.LOOK_SUCCESS.getValue()) {
				resp.setSuccess(true);
				houseId = resp.getTaskdetail().getHouseId();

				/* 获取配套信息 */
				HouseSupportingMeasures houseSupport = getHouseAuxiliary(houseId);
				House house = this.getEntityManager().find(House.class, houseId);
				if (houseSupport != null && house.getDecorateType() == 2) {
					resp.setHouseSupport(houseSupport.getSupportingMeasuresStr());
				} else {
					resp.setHouseSupport("毛坯");
				}

				resp.setTaskImgStr(bdTask.getTaskImgStr());//获取照片信息
				resp.setHouseTypeStr(bdTask.getHouseTypeStr());// 房型变化字段
				resp.setRentPriceStr(bdTask.getRentPriceStr());// 出租金额变化字段
				resp.setSellPriceStr(bdTask.getSellPriceStr());// 出售金额变化字段
				resp.setSpaceAreaStr(bdTask.getSpaceAreaStr());// 面积变化字段
				
			} else {
				taskDetails.setRemark(bdTask.getRemark());
				resp.setTaskdetail(taskDetails);
				resp.setSuccess(false);
			}
		}

		return resp;
	}


	private HouseSupportingMeasures getHouseAuxiliary(Integer houseId) {
		Query query = this.getEntityManager().createQuery("from HouseSupportingMeasures where houseid = ? ");
		query.setParameter(1, houseId);
		List<HouseSupportingMeasures> list = query.getResultList();
		if(list == null || list.size() <=0){
			return null;
		}
		return list.get(0);
	}

}
