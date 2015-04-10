package com.manyi.hims.sourcelog.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.EstateHistory;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.entity.House_;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.User_;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.rent.RentConst;
import com.manyi.hims.sourcelog.SourceLogConst;
import com.manyi.hims.sourcelog.SourceLogResponse;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.LoadInfoResponse;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.UpdateDiscContentRequest;
import com.manyi.hims.sourcelog.controller.SourceLogRestController.UpdateDiscResponse;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.EntityUtils;

/**
 * 记录信息
 * 
 * @author tiger
 * 
 */
@Service(value = "sourceLogService")
@Scope(value = "singleton")
public class SourceLogServiceImpl extends BaseService implements SourceLogService {
	
	@Autowired
	private CommonService commonService;
	  Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 *  清除 用户历史发布记录
	 */
	@Override 
	public int clearPublishLog(int userId){
		if (userId ==0){
			// 必选项不能为空!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		}
		/*
		String jqpl="from HouseResourceHistory history where history.userId = ?";
		Query query  = this.getEntityManager().createQuery(jqpl);
		query.setParameter(1, userId);
		List<HouseResourceHistory> hs= query.getResultList();
		int temp =0;
		if(hs != null && hs.size()>0){
			for (int i = 0; i < hs.size(); i++) {
				HouseResourceHistory history = hs.get(i);
				history.setIfClear(255);// // 清除 历史记录,把状态修改成 删除 状态
				this.getEntityManager().merge(history);
				temp ++;
			}
		}*/
		
		int temp =0;
		User user = this.getEntityManager().find(User.class, userId);
		user.setShowHistoryData(255);//清除 历史记录,把状态修改成 删除 状态
		this.getEntityManager().merge(user);
		this.getEntityManager().flush();
		if(user.getShowHistoryData() == 255){
			temp =1;
		}
		return temp;
	}
	
	/**
	 * 加载发布记录信息
	 */
	@Override
	public List<SourceLogResponse> indexList(int userId) {
		CriteriaBuilder cb= this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.equal(root_user.get(User_.uid), userId));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			u.setCreateLogCount(0);
			getEntityManager().merge(u);
			
			if(u.getShowHistoryData() == 255){
				//清除状态
				//1300034;//请打开查看发布记录选项
				//throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300034);
				List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
				return slogs;
			}
		} else {
			//1300033;//请传入正确的用户ID
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		
		//得到当前用户的 操作记录
		return findPageLog(userId,0,20,null);//获取首页的记录数据
	}
	
	/**
	 * 加载发布记录信息(分页)
	 */
	@Override
	public List<SourceLogResponse> findPageLog(int userId,int start , int end ,Long currTime) {
		log.info(" 加载发布记录信息 userId {} start {} end {} currTime {} ",userId, start , end , currTime);
		
		CriteriaBuilder cb= this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root_user.get(User_.uid), userId)));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			u.setCreateLogCount(0);
			getEntityManager().merge(u);
			if(u.getShowHistoryData() == 255){
				//清除状态
				//1300034;//请打开查看发布记录选项
				//throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300034);
				List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
				return slogs;
			}
		} else {
			//1300033;//请传入正确的用户ID
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		
		if(currTime == null){
			currTime = System.currentTimeMillis();
		}
		String jqpl  ="select hr ,e ,h  from ResidenceResourceHistory hr,Residence h, Estate e  where hr.houseId = h.houseId and h.estateId = e.areaId  and hr.userId =? and hr.publishDate < ? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.publishDate desc ";//审核成功 . 审核失败. 审核中
		Query query = this.getEntityManager().createQuery(jqpl);
		query.setHint("org.hibernate.cacheable", true);
		query.setParameter(1, userId);
		query.setParameter(2, new Date(currTime));
		List<Object[]> logs =query.setFirstResult(start).setMaxResults(end).getResultList();
		
		//得到当前用户所有的 操作记录
		List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
		if(logs != null && logs.size()>0){
			int len = logs.size();
			for (int i = 0; i < len; i++) {
				Object[] row =logs.get(i);
				HouseResourceHistory log =(HouseResourceHistory) row[0];
				Estate estate = (Estate) row[1];
				Residence h = (Residence) row[2];
				
				SourceLogResponse slr = new SourceLogResponse();
				slr.setHouseId(h.getHouseId());
				slr.setHistoryId(log.getHistoryId());
				slr.setBuilding(h.getBuilding());
				slr.setEstateId(estate.getAreaId());
				slr.setEstateName(estate.getName() + (estate.getRoad() == null ? "": estate.getRoad()));
				slr.setMarkTime(new Date().getTime());
				slr.setPublishDate(log.getPublishDate());
				slr.setRoom(h.getRoom());
				slr.setSourceState(log.getStatus());
				SubEstate subEstate = getEntityManager().find(SubEstate.class, h.getSubEstateId());
				slr.setSubEstateName(subEstate==null?null:subEstate.getName());
				if(log.getActionType() == 1){
					if(log.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()){
						//出租
						slr.setTypeId(0);
						slr.setTypeName("发布出租");
					}else if(log.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()){
						//出售
						slr.setTypeId(1);
						slr.setTypeName("发布出售");
					}else if(log.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()){
						//出售出租
						jqpl="from ResidenceResourceHistory hr where hr.actionType = 1  and hr.userId =? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.createTime desc ";
						query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, userId);
						List<ResidenceResourceHistory> hsitorys = query.setFirstResult(0).setMaxResults(2).getResultList();
						if(hsitorys != null && hsitorys.size() >1){
							ResidenceResourceHistory history  = hsitorys.get(1);
							//1出租，2出售
							if(history.getHouseState() == 1){
								slr.setTypeId(1);
								slr.setTypeName("发布出售");
							}else{
								slr.setTypeId(0);
								slr.setTypeName("发布出租");
							}
							
						}
					}else if(log.getHouseState() == EntityUtils.HouseStateEnum.NEITHER.getValue()){
						//出售出租
						jqpl="from ResidenceResourceHistory hr where hr.actionType = 1  and hr.userId =? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.createTime desc ";
						query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, userId);
						List<ResidenceResourceHistory> hsitorys = query.setFirstResult(0).setMaxResults(2).getResultList();
						if(hsitorys != null && hsitorys.size() >1){
							ResidenceResourceHistory history  = hsitorys.get(1);
							//1出租，2出售
							if(history.getHouseState() == 1){
								slr.setTypeId(0);
								slr.setTypeName("发布出租");
							}else{
								slr.setTypeId(1);
								slr.setTypeName("发布出售");
							}
							
						}
					}
				}else{
					slr.setTypeId(log.getActionType());
					slr.setTypeName(EntityUtils.ActionTypeEnum.getByValue(log.getActionType()).getDesc());
				}
				if(log.getStatus() == EntityUtils.StatusEnum.SUCCESS.getValue()){
					//审核成功
					
					//返回的 钱
					if(log.getActionType()  == EntityUtils.ActionTypeEnum.PUBLISH.getValue()){
						//发布
						if(log.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()){
							//出售
							slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(1).getDesc());
						}else if(log.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()){
							//出租
							slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(2).getDesc());
						}else if(log.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()){
							//出租出售
							if(slr.getTypeId() ==1){
								//出售
								slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(1).getDesc());
							}else{
								//出租
								slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(2).getDesc());
							}
						}
					}else if(log.getActionType()  == EntityUtils.ActionTypeEnum.CHANGE.getValue()){
						//改盘
						slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(3).getDesc());
					}else if(log.getActionType()  == EntityUtils.ActionTypeEnum.REPORT.getValue()){
						//举报
						slr.setReturnMoney(EntityUtils.AwardTypeEnum.getByValue(4).getDesc());
					}
				}
				slogs.add(slr);
			}
		}
		log.info("下发审核记录列表: {} ",JSONArray.fromObject(slogs).toString());
		return slogs;
	}
	
	
	/**
	 * 加载发布 小区记录信息
	 */
	@Override
	public List<SourceLogResponse> indexListAddAreaLog(int userId) {
		log.info(" 加载发布小区记录信息 userId {} ",userId);
		
		CriteriaBuilder cb= this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.equal(root_user.get(User_.uid), userId));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			u.setCreateLogCount(0);
			getEntityManager().merge(u);
			
			if(u.getShowHistoryData() == 255){
				//清除状态
				//1300034;//请打开查看发布记录选项
				//throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300034);
				List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
				return slogs;
			}
		} else {
			//1300033;//请传入正确的用户ID
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		
		//得到当前用户的 操作记录

		return findPageAddAreaLog(userId,0,20,null);//获取首页的记录数据
	}
	
	/**
	 * 加载发布小区记录信息
	 */
	@Override
	public List<SourceLogResponse> findPageAddAreaLog(int userId, int start, int end, Long currTime) {
		log.info(" 加载发布小区记录信息 userId {} start {} end {} currTime {} ",userId, start , end , currTime);
		
		CriteriaBuilder cb= this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root_user = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root_user.get(User_.uid), userId)));
		List<User> users = getEntityManager().createQuery(cq_user).getResultList();
		if (users != null && users.size() > 0) {
			User u = users.get(0);
			u.setCreateLogCount(0);
			getEntityManager().merge(u);
			if(u.getShowHistoryData() == 255){
				//清除状态
				//1300034;//请打开查看发布记录选项
				//throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300034);
				List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
				return slogs;
			}
		} else {
			//1300033;//请传入正确的用户ID
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300033);
		}
		
		String jqpl = "from EstateHistory e where e.userId = ? ";//审核成功 . 审核失败. 审核中
		if(currTime != null){
			jqpl += "and e.createTime < ?";
		}
		jqpl += " order by e.createTime desc ";
		
		
		Query query = this.getEntityManager().createQuery(jqpl);
		query.setParameter(1, userId);
		if(currTime != null){
			query.setParameter(2, new Date(currTime));
		}
		List<EstateHistory> estates =query.setFirstResult(start).setMaxResults(end).getResultList();
		//得到当前用户所有的 操作记录
		List<SourceLogResponse> slogs = new ArrayList<SourceLogResponse>();
		if (estates != null && estates.size()>0) {
			for (int i = 0; i < estates.size(); i++) {
				EstateHistory estate = estates.get(i);
				SourceLogResponse log = new SourceLogResponse();
				log.setHistoryId(estate.getHistoryId());
				log.setEstateId(estate.getAreaId());
				log.setEstateName(estate.getName());
				log.setMarkTime(currTime);
				log.setPublishDate(estate.getCreateTime());
				log.setSourceState(estate.getStatus());//审核状态
				if(estate.getStatus() == EntityUtils.StatusEnum.SUCCESS.getValue()){
					//审核 成功之后 添加 返回的 钱
					log.setReturnMoney(EntityUtils.AwardTypeEnum.ADDESTATE.getDesc());
				}
				log.setTypeId(EntityUtils.ActionTypeEnum.ADD_ESTATE.getValue());
				log.setTypeName(EntityUtils.ActionTypeEnum.ADD_ESTATE.getDesc());
				slogs.add(log);
			}
		}
		log.info("下发审核记录列表: {} ",JSONArray.fromObject(slogs).toString());
		return slogs;
	}
	
	/**
	 * 改盘第一步 判断是否能够改盘
	 * 任何操作 存在 审核中的   情况 下 都 不能进行下一步
	 * 房子 没有 在售/在租 状态下  
	 * @param estateId
	 * @param building 必须是纯数字
	 * @param room 标准4位数字, 2位数字 提示message,3位  前面补全一个0
	 * @return
	 */
	@Override
	public UpdateDiscResponse checkCanUpdateDisc(int estateId, String building, String room) {
		log.info("改盘第一步 estateId {} bulding {}  room {}", estateId, building, room);
		if(estateId==0){
			throw new LeoFault(RentConst.Rent_ERROR120003);
		}
		if(StringUtils.isBlank(building)){
			throw new LeoFault(RentConst.Rent_ERROR120004);
		}
		if(StringUtils.isBlank(room)){
			throw new LeoFault(RentConst.Rent_ERROR120005);
		}
//		if(building.startsWith("0") && building.length()>1){
//			// 必须不是 以 0 开头
//			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100051);
//		}
		
//		if (!DataUtil.checkNum(3, 4, room)) {
//			// 标准4位数字, 2位数字 提示message
//			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100040);
//		}
//		// room 3位 前面补全一个0
//		if (room.length() == 3) {
//			room = "0" + room;
//		}
		building = commonService.checkBuilding(building);
		room = commonService.changeRoomStr(room);
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		/*检查小区  id是否存在*/
		SubEstate sube = this.getEntityManager().find(SubEstate.class, estateId);
		if(sube == null){
			log.debug("小区不存在 estateId {}",estateId);
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);//该小区不存在,请重新搜索
		}
		/*先跟据传过来的小区id查询是否有这套房子,如果有就进在查询是否在出售状态，如果没有就新增房源*/
		CriteriaQuery<House> cq_houst = cb.createQuery(House.class);
		Root<House> root_houst = cq_houst.from(House.class);
		cq_houst.where(cb.and(cb.equal(root_houst.get(House_.estateId), sube.getEstateId())),
				cb.and(cb.equal(root_houst.get(House_.building), building)),
				cb.and(cb.equal(root_houst.get(House_.room), room))).select(root_houst);
		List<House> houes =  getEntityManager().createQuery(cq_houst).getResultList();
		House house = null;
		if(houes != null && houes.size()>0 ){
			house = houes.get(0);
		}
		UpdateDiscResponse udResponse = new UpdateDiscResponse();
		if (house == null) {
			
			log.debug("该房源未出租或出售 新增的房子 不可以改盘  Residence {}");
			
			//该房源未出租或出售 新增的房子 不可以改盘
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTSELL_NOTRENT_1300031);
		}else{
			udResponse.setHouseId(house.getHouseId());
			CriteriaQuery<HouseResource> cq= cb.createQuery(HouseResource.class);
			Root<HouseResource> root= cq.from(HouseResource.class);
			
			List<Predicate> pres = new ArrayList<Predicate>();
			pres.add(cb.equal(root.get(HouseResource_.houseId), house.getHouseId()));
			cq.where(pres.toArray(new Predicate[0]));//先 查询是否有这个房子 的 房屋状态信息
			List<HouseResource> hrs= this.getEntityManager().createQuery(cq).getResultList();
			
			if(hrs != null && hrs.size()>0){
				HouseResource hr = hrs.get(0);
				if(hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()){
					//审核中....
					int actionType =hr.getActionType();//1发布，2改盘，3举报，4轮询，5抽查
					int state = hr.getHouseState();//1出租，2出售，3即租又售，4即不租也不售
					if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && (state == EntityUtils.HouseStateEnum.SELL.getValue() || state == EntityUtils.HouseStateEnum.RENTANDSELL.getValue())){
						//出售 1100045;//该房源已有出售在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100045);
					}else if(EntityUtils.ActionTypeEnum.LOOP.getValue() == actionType){
						// 该房源已有改盘在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
					}else if(EntityUtils.ActionTypeEnum.RAMDOM.getValue() == actionType){
						// 该房源已有改盘在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
					}if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && state == EntityUtils.HouseStateEnum.RENT.getValue()){
						//出租1100046;//该房源已有出租在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100046);
					}else if(EntityUtils.ActionTypeEnum.CHANGE.getValue() == actionType){
						//该房源已有改盘在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
					}else if(EntityUtils.ActionTypeEnum.REPORT.getValue() == actionType){
						//该房源已有举报在审核中
						throw new LeoFault(CommonConst.CHECK_PUBLISH_1100044);
					}
				}else{
					//处于 稳定状态
					
					//判断 是否 正在发布出售/出租
					if(hr.getHouseState() == EntityUtils.HouseStateEnum.NEITHER.getValue()){
						//1300031;//该房源未出租或出售
						throw new LeoFault(SourceLogConst.SOURCELOG_NOTSELL_NOTRENT_1300031);
					}else{
						//在出租 /出售状态  可以改盘
						if(hr.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()){
							udResponse.setRentEnabled(true);
							udResponse.setSellEnabled(false);
						}else if(hr.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()){
							udResponse.setRentEnabled(false);
							udResponse.setSellEnabled(true);
						}else if(hr.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()){
							udResponse.setRentEnabled(true);
							udResponse.setSellEnabled(true);
						}
					}
					
				}
				log.debug("该房源未出租或出售 新增的房子 不可以改盘  Residence {}",ReflectionToStringBuilder.toString(udResponse));
			}else{
				//该房源未出租或出售 新增的房子 不可以改盘
				throw new LeoFault(SourceLogConst.SOURCELOG_NOTSELL_NOTRENT_1300031);
			}
			
		}
		return udResponse;
	}
		
	/**
	 * 改盘第二步,提交 改盘信息
	 */
	@Override
	public int updateDisc(UpdateDiscContentRequest req){
		log.debug("改盘第二步,提交 改盘信息 UpdateDiscContentRequest {}" ,ReflectionToStringBuilder.toString(req));
		
		if( req.getHouseId() ==0){
			// 必选项不能为空!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		} 
		if(req.getSellType() == 0 && req.getRentType() == 0){
			//1100050;//请选择出租或出售状态
			throw new LeoFault(CommonConst.CHECK_PUBLISH_1100050);
		}
		if(req.getRentType() == 1 && req.getSellType() ==1){
			// 用户 同时 选择  出租/出售1100049;//不能同时选择已租已售状态
			throw new LeoFault(CommonConst.CHECK_PUBLISH_1100049);
		}
		if(StringUtils.isBlank(req.getRemark())){
			// 请输入改盘理由
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100038);
		}
		if(StringUtils.isNotBlank(req.getRemark())){
			if(DataUtil.isChinese(req.getRemark())==false){
				throw new LeoFault(UcConst.UC_ERROR100026);
			}
		}
		
		//改盘  入库之前 需要判断该房源是否 在某个动作 审核中 的状态 
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		CriteriaQuery<HouseResource> cq= cb.createQuery(HouseResource.class);
		Root<HouseResource> root= cq.from(HouseResource.class);
		
		List<Predicate> pres = new ArrayList<Predicate>();
		pres.add(cb.equal(root.get(HouseResource_.houseId), req.getHouseId()));
		cq.where(pres.toArray(new Predicate[0]));//先 查询是否有这个房子 的 房屋状态信息
		List<HouseResource> hrs= this.getEntityManager().createQuery(cq).getResultList();
		
		if (hrs != null && hrs.size() > 0) {
			HouseResource hr = hrs.get(0);
			if (hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()) {
				// 审核中....
				int actionType = hr.getActionType();// 1发布，2改盘，3举报，4轮询，5抽查
				int state = hr.getHouseState();// 1出租，2出售，3即租又售，4即不租也不售
				if (EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && (state == EntityUtils.HouseStateEnum.SELL.getValue() || state == EntityUtils.HouseStateEnum.RENTANDSELL.getValue())) {
					// 出售 1100045;//该房源已有出售在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100045);
				} else if (EntityUtils.ActionTypeEnum.LOOP.getValue() == actionType) {
					// 该房源已有改盘在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
				} else if(EntityUtils.ActionTypeEnum.RAMDOM.getValue() == actionType){
					// 该房源已有改盘在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
				} else if (EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && state == EntityUtils.HouseStateEnum.RENT.getValue()) {
					// 出租1100046;//该房源已有出租在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100046);
				} else if (EntityUtils.ActionTypeEnum.CHANGE.getValue() == actionType) {
					// 该房源已有改盘在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
				} else if (EntityUtils.ActionTypeEnum.REPORT.getValue() == actionType) {
					// 该房源已有举报在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100044);
				}
			} else {
				// 处于 稳定状态
				
				//判断 是否 正在发布出售/出租
				if(hr.getHouseState() == EntityUtils.HouseStateEnum.NEITHER.getValue()){
					//1300031;//该房源未出租或出售
					throw new LeoFault(SourceLogConst.SOURCELOG_NOTSELL_NOTRENT_1300031);
				}else{
					
					//新增temp数据 保存 原来的数据信息
					Residence house = this.getEntityManager().find(Residence.class, hr.getHouseId());
//					HouseResourceTemp tmp = new HouseResourceTemp();
//					BeanUtils.copyProperties( house,tmp);
//					BeanUtils.copyProperties( hr,tmp);
//					this.getEntityManager().persist(tmp);
//					this.getEntityManager().flush();
					HouseResourceTemp tmp = getEntityManager().find(HouseResourceTemp.class, hr.getHouseId());
					if(tmp==null){
						tmp = new HouseResourceTemp();
						BeanUtils.copyProperties( house,tmp);
						BeanUtils.copyProperties( hr,tmp);
						this.getEntityManager().persist(tmp);
					}else{
						BeanUtils.copyProperties( house,tmp);
						BeanUtils.copyProperties( hr,tmp);
						this.getEntityManager().merge(tmp);
					}


					//当前房源 既租又售
					if(hr.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()){
						if(req.getSellType() == 1){
							//已出售
							hr.setStateReason(EntityUtils.HouseStateResonEnum.HAD_SELLED.getValue());
							//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());//不租不售
							if(req.getRentType() == 1){
								//已出租
							}else if(req.getRentType() == 2){
								//不出租
								//不租不售
							}else{
								//没有选择
								//不租不售
							}
						}else if(req.getSellType() == 2){
							//不出售
							
							if(req.getRentType() == 1){
								//已出租
								//不售不租
								hr.setStateReason(EntityUtils.HouseStateResonEnum.NOSELL_HADRENT.getValue());
								//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
							}else if(req.getRentType() == 2){
								//不出租
								//不售不租
								hr.setStateReason(EntityUtils.HouseStateResonEnum.NORENT_NOSELL.getValue());
								//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
							}else{
								//没有选择
								//在租
								hr.setStateReason(EntityUtils.HouseStateResonEnum.NO_SELL.getValue());
								//hr.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());//不售 在租
							}
						}else{
							//没有选择
							if(req.getRentType() == 1){
								//已出租
								//已售
								hr.setStateReason(EntityUtils.HouseStateResonEnum.HAD_RENTED.getValue());
								//hr.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());
							}else if(req.getRentType() == 2){
								//不出租
								//已售
								hr.setStateReason(EntityUtils.HouseStateResonEnum.NO_RENT.getValue());
								//hr.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());
							}else{
								//没有选择
							}
						}
					} else if (hr.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()) {
						// 仅仅是在售
						if (req.getSellType() == 1) {
							//已售
							hr.setStateReason(EntityUtils.HouseStateResonEnum.HAD_SELLED.getValue());
							//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
						} else if (req.getSellType() == 2) {
							//不售
							hr.setStateReason(EntityUtils.HouseStateResonEnum.NO_SELL.getValue());
							//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
						} else {
							//客户端没有选择
						}
					} else if (hr.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()) {
						// 仅仅是在租
						if (req.getRentType() == 1) {
							//已租
							hr.setStateReason(EntityUtils.HouseStateResonEnum.HAD_RENTED.getValue());
							//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
						} else if (req.getRentType() == 2) {
							//不租
							hr.setStateReason(EntityUtils.HouseStateResonEnum.NO_RENT.getValue());
							//hr.setHouseState(EntityUtils.HouseStateEnum.NEITHER.getValue());
						} else {
							//客户端没有选择
						}
					}
					
					hr.setStatus(EntityUtils.StatusEnum.ING.getValue());//审核中...
					hr.setUserId(req.getUserId());
					hr.setRemark(req.getRemark());//改盘 理由
					hr.setActionType(EntityUtils.ActionTypeEnum.CHANGE.getValue());//改盘
					hr.setResultDate(null);
					hr.setPublishDate(new Date());
					hr.setOperatorId(0);//值空
					
					this.getEntityManager().merge(hr);
					this.getEntityManager().flush();
					
					ResidenceHistory houseHistory =new ResidenceHistory();
					BeanUtils.copyProperties(house,houseHistory);
					this.getEntityManager().persist(houseHistory);
					
					ResidenceResourceHistory hrHistory = new ResidenceResourceHistory();
					BeanUtils.copyProperties(house, hrHistory);
					BeanUtils.copyProperties( hr ,hrHistory);
					hrHistory.setStatus(EntityUtils.StatusEnum.ING.getValue());//审核中...
					hrHistory.setHouseState(EntityUtils.ActionTypeEnum.CHANGE.getValue());
					
					this.getEntityManager().persist(hrHistory);
					log.info("改盘完成 ");
					return 0;
				}
			}
		}else{
			//该房源未出租或出售 新增的房子 不可以改盘
			throw new LeoFault(SourceLogConst.SOURCELOG_NOTSELL_NOTRENT_1300031);
		}
		return 0;
	}
	
	/**
	 * 加载出售记录信息
	 */
	@Override
	public LoadInfoResponse loadSellInfoLog(int logId){
//		SellLog log =this.getEntityManager().find(SellLog.class, logId);
//		if(log == null){
//			//未查到该操作记录
//			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300032);
//		}
//		LoadInfoResponse response = new LoadInfoResponse();
//		response.setBedroomSum(log.getBedroomSum());
//		response.setLivingRoomSum(log.getLivingRoomSum());
//		response.setPrice(log.getPrice());
//		response.setSpaceArea(log.getSpaceArea());
//		response.setWcSum(log.getWcSum());
//		
//		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<SourceHost> cq= cb.createQuery(SourceHost.class);
//		Root<SourceHost> root = cq.from(SourceHost.class);
//		cq.where(cb.equal(root.get(SourceHost_.sourceLogId), logId));
//		List<SourceHost> hosts = this.getEntityManager().createQuery(cq).getResultList();
//		response.setHosts(hosts);
//		return response;
		return null;
	}
	
	/**
	 * 加载出租记录信息
	 */
	@Override
	public LoadInfoResponse loadRentInfoLog(int logId) {
//		RentLog log =this.getEntityManager().find(RentLog.class, logId);
//		if(log == null){
//			//未查到该操作记录
//			throw new LeoFault(SourceLogConst.SOURCELOG_NOTDATA_1300032);
//		}
//		LoadInfoResponse response = new LoadInfoResponse();
//		response.setBedroomSum(log.getBedroomSum());
//		response.setLivingRoomSum(log.getLivingRoomSum());
//		response.setPrice(log.getPrice());
//		response.setSpaceArea(log.getSpaceArea());
//		response.setWcSum(log.getWcSum());
//		
//		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<SourceHost> cq= cb.createQuery(SourceHost.class);
//		Root<SourceHost> root = cq.from(SourceHost.class);
//		cq.where(cb.equal(root.get(SourceHost_.sourceLogId), logId));
//		List<SourceHost> hosts = this.getEntityManager().createQuery(cq).getResultList();
//		response.setHosts(hosts);
//		return response;
		return null;
	}
	
}
