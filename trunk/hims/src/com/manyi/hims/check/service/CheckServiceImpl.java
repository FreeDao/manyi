package com.manyi.hims.check.service;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.check.controller.CheckRestController.CheckRes;
import com.manyi.hims.check.model.CheckReq;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.entity.Employee;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.User;
import com.manyi.hims.util.CommonUtils;
import com.manyi.hims.util.EntityUtils;

@Service(value = "checkService")
@Scope(value = "singleton")

public class CheckServiceImpl extends BaseService implements CheckService {
	

	
	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<CheckRes> checktaskList(CheckReq req) {
		Employee emp =null;
		if(req.getEmployeeId() !=0){
			emp = this.getEntityManager().find(Employee.class, req.getEmployeeId());
		}
		
		String count_sql  ="select count(1)  from HouseResourceHistory hr join House h on h.houseId = hr.houseId join Area e on "
				+" e.areaId = h.estateId join Area town on town.areaId = e.parentId join Area city on city.areaId = town.parentId join Area province on province.areaId = city.parentId "
				+" left join User user on user.uid = hr.userId left join Employee emp on emp.employeeId = hr.operatorId  "
				+" where  (  hr.checkNum = 0 ) ";//审核成功 . 审核失败. 审核中
		String sql  ="select hr.historyId ,province.areaId provinceId, province.name provinceName , city.areaId areaId, city.name areaName, town.areaId townid, town.name townNam,e.areaId estateId , e.name estateName,e.road, "
				+" h.building,h.room, user.uid publishId,user.realName publishName, hr.publishDate , emp.employeeId opaeratorId , emp.realName operatorName, "
				+" hr.status sourceState ,hr.actionType ,hr.houseState,h.houseId from HouseResourceHistory hr join House h on h.houseId = hr.houseId join Area e "
				+" on e.areaId = h.estateId join Area town on town.areaId = e.parentId join Area city on city.areaId = town.parentId join Area province on province.areaId = city.parentId left join User user "
				+" on user.uid = hr.userId left join Employee emp on emp.employeeId = hr.operatorId  "
				+" where (  hr.checkNum = 0  ) ";
		
		//添加  筛选条件
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Object> pars =new ArrayList<Object>();
		
		//审核类型
		if(req.getCheckType() != -1){
			if(req.getCheckType() == 0){
				//发布出售
				count_sql += " and hr.actionType = "+EntityUtils.ActionTypeEnum.PUBLISH.getValue() +" and  hr.houseState in ( "+EntityUtils.HouseStateEnum.SELL.getValue()+" , "+EntityUtils.HouseStateEnum.RENTANDSELL.getValue()+" ) ";
				sql += " and hr.actionType = "+EntityUtils.ActionTypeEnum.PUBLISH.getValue() +" and  hr.houseState in ( "+EntityUtils.HouseStateEnum.SELL.getValue()+" , "+EntityUtils.HouseStateEnum.RENTANDSELL.getValue()+" ) ";
			}else if(req.getCheckType() == 1){
				//发布出租
				count_sql += " and hr.actionType = "+EntityUtils.ActionTypeEnum.PUBLISH.getValue() +" and  hr.houseState in ( "+EntityUtils.HouseStateEnum.RENT.getValue()+" , "+EntityUtils.HouseStateEnum.RENTANDSELL.getValue()+" ) ";
				sql += " and hr.actionType = "+EntityUtils.ActionTypeEnum.PUBLISH.getValue() +" and  hr.houseState in ( "+EntityUtils.HouseStateEnum.RENT.getValue()+" , "+EntityUtils.HouseStateEnum.RENTANDSELL.getValue()+" ) ";
			}else{
				count_sql += " and hr.actionType =  "+req.getCheckType();
				sql += " and hr.actionType =  "+req.getCheckType();
			}
		}
		
		// 发布人
		if (!StringUtils.isBlank(req.getPublishName())) {
			count_sql += " and user.realName like ? ";
			sql += " and user.realName like ? ";
			pars.add("%" + req.getPublishName() + "%");
		}
		// 发布时间
		if (req.getStartPublishDate() != null) {
			count_sql += " and hr.publishDate >= ? ";
			sql += " and hr.publishDate >= ? ";
			try {
				pars.add(sdf.parse(req.getStartPublishDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (req.getEndPublishDate() != null) {
			count_sql += " and hr.publishDate <= ? ";
			sql += " and hr.publishDate <= ? ";
			try {
				pars.add(sdf.parse(req.getEndPublishDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 负责人
		if (!StringUtils.isBlank(req.getOperServiceName())) {
			count_sql += " and emp.realName like ? ";
			sql += " and emp.realName like ? ";
			pars.add("%" + req.getOperServiceName().trim() + "%");
		}
		
		// 审核状态
		/*if (!StringUtils.isBlank(req.getOperServiceState())) {
			count_sql += " and hr.status in ( ";
			sql += " and hr.status in ( ";
			String[] serviceStates = req.getOperServiceState().split(",");
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ";
			sql += " ) ";
		}*/
		
		if (!StringUtils.isBlank(req.getOperServiceState())) {
			count_sql += " and ( ( hr.actionType in (1,2,3) and hr.status in ( ";
			sql += " and (  ( hr.actionType in (1,2,3) and hr.status in ( ";
			String[] serviceStates = req.getOperServiceState().split(",");
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ";
			sql += " ) ";
			count_sql += " ) ";
			sql += " ) ";
			
			count_sql += " or ( hr.actionType in (4) and hr.status in ( ";
			sql += " or ( hr.actionType in (4) and hr.status in ( ";
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				
				if ("1".equals(serviceStates[i])) {
					serviceStates[i] = "3";
				}else if ("3".equals(serviceStates[i])) {
					serviceStates[i] = "1";
				}
				
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ) ) ";
			sql += " ) ) ) ";
			
		}
		
		//角色
		 //1管理员,2客服经理，3客服人员,4地推经理,5地推人员 ,6财务
		if (emp != null) {
			if (emp.getRole() == 3) {
				count_sql += " and emp.employeeId = ? ";
				sql += " and emp.employeeId = ? ";
				pars.add(emp.getEmployeeId());
			} else if (emp.getRole() == 5) {
				count_sql += " and emp.employeeId = ? ";
				sql += " and emp.employeeId = ? ";
				pars.add(emp.getEmployeeId());
			}
			/*if (emp.getCityId() >= 0) {
				count_sql += " and province.areaId = ? ";
				sql += " and province.areaId = ? ";
			}*/
		}
		
		if (req.getCityType() > 0) {
			count_sql += "  and province.areaId = ?  ";
			sql += " and province.areaId = ? ";
			pars.add(req.getCityType());
		}
		
		sql += " order by hr.publishDate desc ";//审核成功 . 审核失败. 审核中

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
		PageResponse<CheckRes> page = new PageResponse<CheckRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage() - 1) * req.getRows());// 起始下标
			query.setMaxResults(req.getRows());// 查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = (total / req.getRows());
			page.setTotalPage(n);// 总页数
			List<Object[]> objs = query.getResultList();
			if (objs != null && objs.size() > 0) {
				for (Object[] row : objs) {
					CheckRes check = new CheckRes();
					int i =0;

					check.setLogId(Integer.parseInt(row[i++]+""));
					check.setProvinceId(Integer.parseInt(row[i++]+""));
					check.setProvinceName(row[i++]+"");
					check.setAreaId(Integer.parseInt(row[i++]+""));
					check.setAreaName(row[i++]+"");
					check.setTownId(Integer.parseInt(row[i++]+""));
					check.setTownName(row[i++]+"");
					check.setEstateId(Integer.parseInt(row[i++]+""));
					check.setEstateName(row[i] + (row[i+1] == null ? "" : row[i+1]+""));
					i++;
					i++;
					check.setBuiling(row[i++]+"");
					check.setRoom(row[i++]+"");
					check.setPublishId(Integer.parseInt(row[i] == null ? "0" : row[i]+""));
					i++;
					check.setPublishName(row[i] == null ? "-": row[i]+"");
					i++;
					check.setPublishDate( row[i] == null ? null : (Date)(row[i]));
					i++;
					check.setOperServiceId(row[i] == null ? 0 : Integer.parseInt(row[i]+""));
					i++;
					check.setOperServiceName(row[i] == null ? "-" : row[i]+"");
					i++;
					check.setOperServiceState(row[i] == null ? 0 :Integer.parseInt(row[i]+""));
					i++;
					int actionType = Integer.parseInt(row[i++]+"");//1发布，2，改盘，3举报，4轮询，5抽查 
					int houseState = Integer.parseInt(row[i++]+"");//1出租，2出售，3即租又售，4即不租也不售
					
					int houseId = Integer.parseInt(row[i++]+"");
					check.setHouseId(houseId);
					
					if(actionType == 1){
						if(houseState == 1){
							check.setCheckType("1");//出租
						}else if(houseState == 2){
							check.setCheckType("0");//出售
						}else if(houseState ==3 ){
							//出租出售
							//check.setCheckType("10");//出售
							if(check.getHouseId() >0){

								//出售出租
								String jqpl="from ResidenceResourceHistory hr where hr.actionType = 1  and hr.userId =? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.createTime desc ";
								query = this.getEntityManager().createQuery(jqpl);
								query.setParameter(1, check.getPublishId());
								List<ResidenceResourceHistory> hsitorys =query.setFirstResult(0).setMaxResults(2).getResultList();
								if(hsitorys != null && hsitorys.size() >1){
									ResidenceResourceHistory history  = hsitorys.get(1);
									//1出租，2出售
									if(history.getHouseState() == 1){
										//上一条是出租
										check.setCheckType("1");
									}else{
										//上一条是出售
										check.setCheckType("0");
									}
									
								}
								
							}
						}else if(houseState == 4){
							
							String jqpl="from ResidenceResourceHistory hr where hr.actionType = 1  and hr.userId =? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.createTime desc ";
							query = this.getEntityManager().createQuery(jqpl);
							query.setParameter(1, check.getPublishId());
							List<ResidenceResourceHistory> hsitorys = query.setFirstResult(0).setMaxResults(2).getResultList();
							if(hsitorys != null && hsitorys.size() >1){
								ResidenceResourceHistory history  = hsitorys.get(1);
								//1出租，2出售
								if(history.getHouseState() == 1){
									check.setCheckType("1");
								}else{
									check.setCheckType("0");
								}
								
							}
						}
					}else{
						check.setCheckType(actionType+"");//出售
					}
					
					if (actionType == EntityUtils.ActionTypeEnum.LOOP.getValue()) {
						//转换轮询状态
						check.setOperServiceStateStr(CommonUtils.loopTransformStatusDesc(check.getOperServiceState()));
						
					}
					
					
					if(page.getRows() == null){
						page.setRows(new ArrayList<CheckRes>());
					}
					page.getRows().add(check);
					
				}
			}else{
				page.setRows(new ArrayList<CheckRes>());
			}
		}else{
			page.setRows(new ArrayList<CheckRes>());
		}
		
		return page;
	}
	
	
	
	
	
	
	
	@Override
	public void addContact(HouseResourceContact oper) {
		getEntityManager().persist(oper);
	}
	
	@Override
	public void submitResult(HouseResource oper) {
		getEntityManager().merge(oper);
	}

	/**
	 * @date 2014年4月22日 下午10:24:39
	 * @author Tom  
	 * @description  
	 * 地推人员审核操作
	 */
	public void audit4operFloor(FloorRequest floorRequest) {
////		修改地推日志
//		ReportLog reportLog = this.getEntityManager().find(ReportLog.class, floorRequest.getSourceLogId());
//		reportLog.setSourceState(floorRequest.getSourceState());
//		this.getEntityManager().persist(reportLog);
//		
////		添加看房审核记录
//		SourceLogHistory sourceLogHistory = new SourceLogHistory();
//		sourceLogHistory.setIfLookStatus(1);
//		sourceLogHistory.setLookNote(floorRequest.getLookNote());
//		this.getEntityManager().persist(sourceLogHistory);
		
	}
	
	
	public String getUser4UserId(int userId) {
		User user = this.getEntityManager().find(User.class, userId);
		return user.getMobile() + "-" + user.getRealName();
	}
	


	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	
/*	
	@Override
	public PageResponse<CheckRes> checktaskList(CheckReq req) {
		Employee emp =null;
		if(req.getEmployeeId() !=0){
			emp = this.getEntityManager().find(Employee.class, req.getEmployeeId());
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String count_sql="SELECT COUNT(1) FROM SourceLog log left join User user on log.userId =user.uid left join Employee cust on log.operCustServiceId = cust.employeeId left join Employee floor on log.operFloorServiceId = floor.employeeId where  log.sourceLogId <>0 ";
		String sql="SELECT log.sourceLogId logId,log.createTime publishDate,log.operCustServiceState ,log.operFloorServiceState,cust.employeeId operCustServiceId ,cust.userName custName,floor.employeeId operFloorServiceId ,floor.userName floorName,user.uid userId ,user.realName userName ,area.areaId,area.name areaName,town.areaId townId,town.name townName,sube.areaId estateId ,sube.name estateName ,sube.road raod ,house.building,house.room,house.bedroomSum ,house.livingRoomSum,house.wcSum,log.DTYPE  FROM SourceLog log ";
		List<Object> pars =new ArrayList<Object>();
		//审核类型
		//1,发布出售;2,发布出租;3,改盘;4,举报;5,客服轮询;6,抽查看房
		switch (req.getCheckType()) {
		case 1:
			pars.add("SellLog"); 
			sql +=" left join SourceInfo info on info.sourceInfoId = log.sellInfoId join House house on house.houseId = info.houseId ";
			break;
		case 2:
			pars.add("RentLog");
			sql +=" left join SourceInfo info on info.sourceInfoId = log.rentInfoId join House house on house.houseId = info.houseId ";
			break;
		case 3:
			pars.add("UpdateDiscLog");
			sql +=" left join SourceInfo info on ( info.sourceInfoId = log.rentInfoId or  info.sourceInfoId = log.sellInfoId) join House house on house.houseId = info.houseId ";
			break;
		case 4:
			pars.add("ReportLog");
			sql +=" left join House house on house.houseId = log.houseId ";
			break;
		case 5:
			pars.add("LoopLog");
			sql +=" left join House house on house.houseId = log.houseId ";
			break;
		case 6:
			pars.add("RandomLog");
			sql +=" left join House house on house.houseId = log.houseId ";
			break;
		}
		if(req.getCheckType() !=0){
			sql += " join Area sube on sube.areaId = house.estateId join Area esta on esta.areaId = sube.parentId join Area town on town.areaId = esta.parentId join Area area on area.areaId = town.parentId  left join User user on log.userId =user.uid left join Employee cust on log.operCustServiceId = cust.employeeId left join Employee floor on log.operFloorServiceId = floor.employeeId where log.userId =user.uid and log.sourceLogId <>0 ";
			count_sql +=" and log.DTYPE =? ";
			sql +=" and log.DTYPE =? ";
		}else{
			//查询全部
			String tmp =" select * from ( " +sql;
			tmp +=" left join SourceInfo info on info.sourceInfoId = log.sellInfoId join House house on house.houseId = info.houseId ";
			tmp +=" join Area sube on sube.areaId = house.estateId join Area esta on esta.areaId = sube.parentId join Area town on town.areaId = esta.parentId join Area area on area.areaId = town.parentId  left join User user on log.userId =user.uid left join Employee cust on log.operCustServiceId = cust.employeeId left join Employee floor on log.operFloorServiceId = floor.employeeId where log.userId =user.uid and log.sourceLogId <>0 ";
			tmp +=" and log.DTYPE ='SellLog' ";
			tmp +=" union ";
			tmp += sql+" left join SourceInfo info on info.sourceInfoId = log.rentInfoId join House house on house.houseId = info.houseId ";
			tmp +=" join Area sube on sube.areaId = house.estateId join Area esta on esta.areaId = sube.parentId join Area town on town.areaId = esta.parentId join Area area on area.areaId = town.parentId  left join User user on log.userId =user.uid left join Employee cust on log.operCustServiceId = cust.employeeId left join Employee floor on log.operFloorServiceId = floor.employeeId where log.userId =user.uid and log.sourceLogId <>0 ";
			tmp +=" and log.DTYPE ='RentLog' ";
			tmp +=" union ";
			tmp += sql+" left join House house on house.houseId = log.houseId ";
			tmp +=" join Area sube on sube.areaId = house.estateId join Area esta on esta.areaId = sube.parentId join Area town on town.areaId = esta.parentId join Area area on area.areaId = town.parentId  left join User user on log.userId =user.uid left join Employee cust on log.operCustServiceId = cust.employeeId left join Employee floor on log.operFloorServiceId = floor.employeeId where log.userId =user.uid and log.sourceLogId <>0 ";
			tmp +=" and log.DTYPE in ( 'ReportLog' ,'LoopLog','RandomLog') ";
			tmp +=" ) A  where A.logId <>0 ";
			sql = tmp;
			
			// 发布人
			if (!StringUtils.isBlank(req.getPublishName())) {
				count_sql += " and user.realName like ? ";
				sql += " and A.userName like ? ";
				pars.add("%" + req.getPublishName() + "%");
			}
			if (req.getStartPublishDate() != null) {
				count_sql += " and log.createTime >= ? ";
				sql += " and A.publishDate >= ? ";
				try {
					pars.add(sdf.parse(req.getStartPublishDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (req.getEndPublishDate() != null) {
				count_sql += " and log.createTime <= ? ";
				sql += " and A.publishDate <= ? ";
				try {
					pars.add(sdf.parse(req.getEndPublishDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 客服负责人
			if (!StringUtils.isBlank(req.getOperServiceName())) {
				count_sql += " and cust.userName like ? ";
				sql += " and A.custName like ? ";
				pars.add("%" + req.getOperServiceName().trim() + "%");
			}
			// 地推看房负责人
//			if (!StringUtils.isBlank(req.getOperFloorServiceName())) {
//				count_sql += " and floor.userName like ? ";
//				sql += " and A.floorName like ? ";
//				pars.add("%" + req.getOperFloorServiceName().trim() + "%");
//			}
//			// 客服审核状态
//			if (!StringUtils.isBlank(req.getOperServiceState())) {
//				count_sql += " and log.operCustServiceState in ( ";
//				sql += " and A.operCustServiceState in ( ";
//				String[] custStates = req.getOperServiceState().split(",");
//				for (int i = 0; i < custStates.length; i++) {
//					if (i != 0) {
//						count_sql += " , ";
//						sql += " , ";
//					}
//					count_sql += custStates[i];
//					sql += custStates[i];
//				}
//				count_sql += " ) ";
//				sql += " ) ";
//			}
			// 地推看房审核状态
//			if (!StringUtils.isBlank(req.getOperFloorServiceState())) {
//				count_sql += " and log.operFloorServiceState in ( ";
//				sql += " and A.operFloorServiceState in ( ";
//				String[] floorStates = req.getOperFloorServiceState().split(",");
//				for (int i = 0; i < floorStates.length; i++) {
//					if (i != 0) {
//						count_sql += " , ";
//						sql += " , ";
//					}
//					count_sql += floorStates[i];
//					sql += floorStates[i];
//				}
//				count_sql += " ) ";
//				sql += " ) ";
//			}
			
			//角色
			 //1管理员,2客服经理，3客服人员,4地推经理,5地推人员 ,6财务
			if(emp != null){
				if(emp.getRole() == 3){
					count_sql += " and log.operCustServiceId = ? ";
					sql += " and A.operCustServiceId = ? ";
					pars.add(emp.getEmployeeId());
				}else if(emp.getRole() ==5){
					count_sql += " and log.operFloorServiceId = ? ";
					sql += " and A.operFloorServiceId = ? ";
					pars.add(emp.getEmployeeId());
				}
			}
			
			//排序
			sql += " order by A.publishDate desc ";

		}
		
		if (req.getCheckType() != 0) {
			// 发布人
			if (!StringUtils.isBlank(req.getPublishName())) {
				count_sql += " and user.realName like ? ";
				sql += " and user.realName like ? ";
				pars.add("%" + req.getPublishName() + "%");
			}
			// 发布时间
			if (req.getStartPublishDate() != null) {
				count_sql += " and log.createTime >= ? ";
				sql += " and log.createTime >= ? ";
				try {
					pars.add(sdf.parse(req.getStartPublishDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (req.getEndPublishDate() != null) {
				count_sql += " and log.createTime <= ? ";
				sql += " and log.createTime <= ? ";
				try {
					pars.add(sdf.parse(req.getEndPublishDate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// 客服负责人
//			if (!StringUtils.isBlank(req.getOperCustServiceName())) {
//				count_sql += " and cust.userName like ? ";
//				sql += " and cust.userName like ? ";
//				pars.add("%" + req.getOperCustServiceName().trim() + "%");
//			}
//			// 地推看房负责人
//			if (!StringUtils.isBlank(req.getOperFloorServiceName())) {
//				count_sql += " and floor.userName like ? ";
//				sql += " and floor.userName like ? ";
//				pars.add("%" + req.getOperFloorServiceName().trim() + "%");
//			}
//			// 客服审核状态
//			if (!StringUtils.isBlank(req.getOperCustServiceState())) {
//				count_sql += " and log.operCustServiceState in ( ";
//				sql += " and log.operCustServiceState in ( ";
//				String[] custStates = req.getOperCustServiceState().split(",");
//				for (int i = 0; i < custStates.length; i++) {
//					if (i != 0) {
//						count_sql += " , ";
//						sql += " , ";
//					}
//					count_sql += custStates[i];
//					sql += custStates[i];
//				}
//				count_sql += " ) ";
//				sql += " ) ";
//			}
//			// 地推看房审核状态
//			if (!StringUtils.isBlank(req.getOperFloorServiceState())) {
//				count_sql += " and log.operFloorServiceState in ( ";
//				sql += " and log.operFloorServiceState in ( ";
//				String[] floorStates = req.getOperFloorServiceState().split(",");
//				for (int i = 0; i < floorStates.length; i++) {
//					if (i != 0) {
//						count_sql += " , ";
//						sql += " , ";
//					}
//					count_sql += floorStates[i];
//					sql += floorStates[i];
//				}
//				count_sql += " ) ";
//				sql += " ) ";
//			}
			
			//角色
			 //1管理员,2客服经理，3客服人员,4地推经理,5地推人员 ,6财务
			if (emp != null) {
				if (emp.getRole() == 3) {
					count_sql += " and log.operCustServiceId = ? ";
					sql += " and log.operCustServiceId = ? ";
					pars.add(emp.getEmployeeId());
				} else if (emp.getRole() == 5) {
					count_sql += " and log.operFloorServiceId = ? ";
					sql += " and log.operFloorServiceId = ? ";
					pars.add(emp.getEmployeeId());
				}
			}

		//排序
		sql += " order by "+req.getOrderby();
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
		PageResponse<CheckRes> page = new PageResponse<CheckRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage()-1)*req.getRows());//起始下标
			query.setMaxResults(req.getRows());//查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = (total/req.getRows());
			page.setTotalPage(n);//总页数
			List<Object[]> objs = query.getResultList();
			if(objs != null && objs.size()>0){
				for (Object[] row : objs) {
					CheckRes check = new CheckRes();
					check.setLogId(Integer.parseInt(row[0]+""));
					check.setPublishDate((Date) row[1]);
					check.setPublishId(Integer.parseInt(row[8]+""));
					check.setPublishName(row[9]+"");
					check.setAreaId(Integer.parseInt(row[10]+""));
					check.setAreaName(row[11]+"");
					check.setTownId(Integer.parseInt(row[12]+""));
					check.setTownName(row[13]+"");
					check.setEstateId(Integer.parseInt(row[14]+""));
					check.setEstateName(row[15] + (row[16] == null ? "" : row[16]+""));
					check.setBuiling(row[17]+"");
					check.setRoom(row[18]+"");
//					String bedroomSum = row[19]+"";
//					String livingRoomSum = row[20]+"";
//					String wcSum = row[21]+"";
					check.setCheckType(row[22]+"");
					if(page.getRows() == null){
						page.setRows(new ArrayList<CheckRes>());
					}
					page.getRows().add(check);
				}
			}else{
				page.setRows(new ArrayList<CheckRes>());
			}
		}else{
			page.setRows(new ArrayList<CheckRes>());
		}
		
		return page;
	}
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
