package com.manyi.hims.rent.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseRecordEntity;
import com.manyi.hims.common.PublishHouseRequest;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.Town;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.houseresource.service.HouseResourceService;
import com.manyi.hims.rent.RentConst;
import com.manyi.hims.sell.SellConst;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.EntityUtils;

@Service(value = "rentService")
@Scope(value = "singleton")

public class RentServiceImpl extends BaseService implements RentService {
	
	private Logger log = LoggerFactory.getLogger(RentServiceImpl.class);
	
	@Autowired
	private HouseResourceService houseResourceService;
	
	@Autowired
	private CommonService commonService;
	/**
	 * 出租信息首页列表
	 */
	@Transactional(readOnly=true)
	public List<HouseEntity> indexList(int cityId) {
		return houseResourceService.indexList(cityId, false);
	}
	
	
	/*
	 * 发布出租信息
	 */
	@Override
	public int publishRentInfo(PublishHouseRequest req) {

		log.info("publishRentInfo: "+JSONObject.fromObject(req).toString());
		
		// 处理 联系人 电话号码 信息 提示对应的结果

		if (StringUtils.isBlank(req.getHostName()) || ",".equals(req.getHostName())) {
			// 1100032;//请输入业主姓名
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100032);
		} else {
			if (req.getHostName().length() <= 1) {
				// 联系人姓名至少包含两个字符
				throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100042);
			}
		}
		if (StringUtils.isBlank(req.getHoustMobile())) {
			// 1100033;//请输入联系电话
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100033);
		}

		String hostName = req.getHostName();
		String[] names = hostName.split(",");
		String hostMobile = req.getHoustMobile();
		String[] moblies = hostMobile.split(",");
		for (int i = 0; i < names.length; i++) {
			// 先检查所有的 名字 是否合法. 一个以上字符
			String name = names[i];
			if(DataUtil.isChinese(name)==false){
				throw new LeoFault(UcConst.UC_ERROR100026);
			}
			if (i == 0) {
				if (name.length() == 0) {
					// 1100032;//请输入业主姓名
					throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100032);
				}
				if (name.length() < 2) {
					// 联系人姓名至少包含两个字符
					throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100042);
				} else {
					if (moblies.length == 0 || (moblies.length > 0 && StringUtils.isBlank(moblies[0]))) {
						// 1100033;//请输入联系电话
						throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100033);
					} else {
						if (!DataUtil.checkNum(0, 0, moblies[i])) {
							// 手机号码格式不正确
							throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
						}
					}
				}
			} else {
				if (name.length() == 1) {
					// 当第二位 填写了联系人
					// 联系人姓名至少包含两个字符
					throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100042);
				} else if (name.length() > 1) {
					// 姓名正确,检查电话号码
					if (moblies.length > i) {
						if (StringUtils.isBlank(moblies[i])) {
							// 1100033;//请输入联系电话
							throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100033);
						} else {
							if (!DataUtil.checkNum(0, 0, moblies[i])) {
								// 手机号码格式不正确
								throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
							}
						}
					} else {
						// 1100033;//请输入联系电话
						throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100033);
					}
				}
			}
		}
		//
		if (moblies.length > names.length) {
			// 1100032;//请输入业主姓名
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100032);
		}

		if (req.getPrice() == null || req.getPrice().equals(new BigDecimal(0))) {
			// 1100034;//请输入价格
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100034);
		}
		if (req.getPrice().doubleValue() >= 10000000d ) {
			// 1100081;//请输入合法的价格
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100081);
		}
		if (req.getBedroomSum() == 0 || req.getWcSum() == 0 || req.getLivingRoomSum() == -1) {
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100037);
		}

		if (req.getSpaceArea() == null || req.getSpaceArea().equals(new BigDecimal(0))) {
			// 1100036;//请输入面积
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100036);
		}
		if (req.getSpaceArea().doubleValue() >= 10000d ) {
			// //请输入合法面积
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100080);
		}
		if (req.getHouseId() == 0) {
			// 必选项不能为空!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);
		}
		
		/**
		 * 同一天同一手机号码只能发布两次
		 * 
		 */
		for (int j = 0; j < moblies.length; j++) {
			if(commonService.publishCount(moblies[j])>0){
				throw new LeoFault(CommonConst.CHECK_PUBLISH_1100054);
			}
		}
		/**
		 * 限制特定区域出租价格
		 */
		if(getSerialCodeByHouseId(req.getHouseId(),req.getPrice().doubleValue())==false){
			throw new LeoFault(RentConst.HISTORY_ERROR120012);
		}
		// 发布 出售 入库之前 需要判断该房源是否 在某个动作 审核中 的状态
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

		// 先查询一下 这个房子是否是 在售 状态
		CriteriaQuery<HouseResource> cq= cb.createQuery(HouseResource.class);
		Root<HouseResource> root= cq.from(HouseResource.class);
		
		List<Predicate> pres = new ArrayList<Predicate>();
		pres.add(cb.equal(root.get(HouseResource_.houseId), req.getHouseId()));
		cq.where(pres.toArray(new Predicate[0]));//先 查询是否有这个房子 的 房屋状态信息
		List<HouseResource> hrs= this.getEntityManager().createQuery(cq).getResultList();
		
		if(hrs != null && hrs.size()>0){
			HouseResource hr = hrs.get(0);
			//非第一次发布 信息
//			Path<Integer> path = root.get(HouseResource_.actionType);
//			//1发布，2改盘，3举报
//			path.in(EntityUtils.ActionTypeEnum.PUBLISH.getValue(),EntityUtils.ActionTypeEnum.REPORT.getValue(),EntityUtils.ActionTypeEnum.CHANGE.getValue());
//			pres.add(cb.in(path));//1发布，2改盘，3举报
//			cq.where(pres.toArray(new Predicate[0]));
//			hrs= this.getEntityManager().createQuery(cq).getResultList();
			if(hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()){
				//审核中....
				int actionType =hr.getActionType();//1发布，2改盘，3举报，4轮询，5抽查
				int state = hr.getHouseState();//1出租，2出售，3即租又售，4即不租也不售
				if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && (state == EntityUtils.HouseStateEnum.RENT.getValue() || state == EntityUtils.HouseStateEnum.RENTANDSELL.getValue())){
					//出租1100046;//该房源已有出租在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100046);
				}else if(EntityUtils.ActionTypeEnum.LOOP.getValue() == actionType || EntityUtils.ActionTypeEnum.RAMDOM.getValue() == actionType){
					//出租1100046;//该房源已有出租在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100046);
				}else if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && state == EntityUtils.HouseStateEnum.SELL.getValue()){
					//出售 1100045;//该房源已有出售在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100045);
				}else if(EntityUtils.ActionTypeEnum.CHANGE.getValue() == actionType){
					//该房源已有改盘在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100043);
				}else if(EntityUtils.ActionTypeEnum.REPORT.getValue() == actionType){
					//该房源已有举报在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100044);
				}
			}else{
				//处于 稳定状态
				
				//判断 是否 正在发布出租(审核中/审核成功的)
				if((hr.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue() || hr.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue() ) 
						&& ( hr.getStatus() == EntityUtils.StatusEnum.SUCCESS.getValue() || hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()) ){
					//SELL_ERROR110001 , //该房源已发布出租
					throw new LeoFault(RentConst.RENT_ERROR120001);
				}
				
//				try {
					//可以发布
					Residence house = this.getEntityManager().find(Residence.class, hr.getHouseId());
//					HouseResourceTemp tmp = new HouseResourceTemp();
//					BeanUtils.copyProperties( house,tmp);
//					BeanUtils.copyProperties( hr,tmp);
//					this.getEntityManager().persist(tmp);
//					this.getEntityManager().flush();
					
					HouseResourceTemp houseTmp = getEntityManager().find(HouseResourceTemp.class, house.getHouseId());
					if(houseTmp!=null){
						BeanUtils.copyProperties(house,houseTmp);
						BeanUtils.copyProperties(hr,houseTmp);
						getEntityManager().merge(houseTmp);
					}else{
						houseTmp = new HouseResourceTemp();
						BeanUtils.copyProperties(house,houseTmp);
						BeanUtils.copyProperties(hr,houseTmp);
						getEntityManager().persist(houseTmp);
					}
					
					//修改成 当前 经纪人 上传的 房型
					house.setBedroomSum(req.getBedroomSum());
					house.setWcSum(req.getWcSum());
					house.setLivingRoomSum(req.getLivingRoomSum());
					house.setSpaceArea(req.getSpaceArea());
					this.getEntityManager().merge(house);
					
					/*
					//判断是否在 出租, 是出租的话. 把状态 改成  租售
					if(hr.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()){
						hr.setHouseState(EntityUtils.HouseStateEnum.RENTANDSELL.getValue());
					}else{
						hr.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());
					}
					*/
					//上面这个 步骤 在 审核 完成的时候  完成, 发布的时候. 直接修改 这个房源信息为 出租的
					hr.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());
					
					hr.setStatus(EntityUtils.StatusEnum.ING.getValue());
					hr.setRentPrice(req.getPrice());
					hr.setUserId(req.getUserId());
					hr.setActionType(EntityUtils.ActionTypeEnum.PUBLISH.getValue());//发布
					hr.setResultDate(null);
					hr.setPublishDate(new Date());
					hr.setOperatorId(0);//值空
					hr.setCheckNum(0);
					hr.setRemark("");
					
					//修改成 经纪人 当前上传的 房源信息数据(价格)
					this.getEntityManager().merge(hr);
					log.info("发布出租 : HouseResource {} " +ReflectionToStringBuilder.toString(hr));
					
					ResidenceHistory houseHistory =new ResidenceHistory();
					BeanUtils.copyProperties( house,houseHistory);
					this.getEntityManager().persist(houseHistory);
					
					ResidenceResourceHistory hrHistory = new ResidenceResourceHistory();
					BeanUtils.copyProperties( hr,hrHistory);
					
					hrHistory.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());//历史表中 存放的是  用户的动作 (出租)
					hrHistory.setBedroomSum(house.getBedroomSum());
					hrHistory.setWcSum(house.getWcSum());
					hrHistory.setLivingRoomSum(house.getLivingRoomSum());
					hrHistory.setSpaceArea(house.getSpaceArea());
					
					this.getEntityManager().persist(hrHistory);
					this.getEntityManager().flush();
					
					int sid=hr.getHouseId();
					//添加 联系人电话信息
					if (req.getHostName() != null && req.getHostName().length() > 0) {
						for (int i = 0; i < names.length; i++) {
							HouseResourceContact sh = new HouseResourceContact();
							sh.setHostName(names[i]);
							sh.setHostMobile(moblies[i]);
							System.out.println(names[i]);
							sh.setEnable(true);
							sh.setHouseId(sid);
							sh.setHistoryId(hrHistory.getHistoryId());
							sh.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());//出租
							this.getEntityManager().persist(sh);// 保存
						}
					}
					this.getEntityManager().flush();
					
					log.info("publishRentInfo: 发布出租信息保存成功");
					
//				} catch (Exception e) {
//					log.error("publishRentInfo",e);
//				}
				
			}
		}else{
			//第一次 发布信息
			HouseResource hr = new HouseResource();
			hr.setActionType(EntityUtils.ActionTypeEnum.PUBLISH.getValue());//发布
			hr.setCheckNum(0);
			hr.setGotPrice(true);
			hr.setHouseId(req.getHouseId());
			hr.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());//出租
			hr.setPublishDate(new Date());
			hr.setRentPrice(req.getPrice());
			hr.setStatus(EntityUtils.StatusEnum.ING.getValue());//审核中
			hr.setUserId(req.getUserId());
			
//			try {
				
				//修改 房型,面积
				Residence house = this.getEntityManager().find(Residence.class, req.getHouseId());
				house.setBedroomSum(req.getBedroomSum());
				house.setWcSum(req.getWcSum());
				house.setLivingRoomSum(req.getLivingRoomSum());
				house.setSpaceArea(req.getSpaceArea());
				this.getEntityManager().merge(house);
				
				
				this.getEntityManager().persist(hr);
				
				Residence tmpHouse= this.getEntityManager().find(Residence.class, req.getHouseId());
				ResidenceHistory residenceHistory =new ResidenceHistory();
				BeanUtils.copyProperties( tmpHouse,residenceHistory);
				this.getEntityManager().persist(residenceHistory);
				
				ResidenceResourceHistory hrHistory = new ResidenceResourceHistory();
				BeanUtils.copyProperties( hr ,hrHistory);
				hrHistory.setBedroomSum(house.getBedroomSum());
				hrHistory.setWcSum(house.getWcSum());
				hrHistory.setLivingRoomSum(house.getLivingRoomSum());
				hrHistory.setSpaceArea(house.getSpaceArea());
				
				this.getEntityManager().persist(hrHistory);
				this.getEntityManager().flush();
				
				int sid=hr.getHouseId();
				//添加 联系人电话信息
				if (req.getHostName() != null && req.getHostName().length() > 0) {
					for (int i = 0; i < names.length; i++) {
						HouseResourceContact sh = new HouseResourceContact();
						sh.setHostName(names[i]);
						sh.setHostMobile(moblies[i]);
						sh.setEnable(true);
						sh.setHouseId(sid);
						sh.setHistoryId(hrHistory.getHistoryId());
						sh.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());
						this.getEntityManager().persist(sh);// 保存
					}
				}
				this.getEntityManager().flush();
				
				log.info("publishRentInfo: 发布出租信息保存成功");
				
//			} catch (Exception e) {
//				log.error("publishRentInfo",e);
//			}
		}
		log.info("publishRentInfo: 发布出租信息保存成功");
		return 0;
	}
	
	/*
	 * 修改发布记录出售信息
	 */
	@Override
	@Deprecated
	public int updatePublishRentInfo(PublishHouseRequest req) {
		if ( req.getHouseId() == 0 || req.getPrice() == null || req.getPrice().equals(new BigDecimal(0)) || req.getSpaceArea() == null
				|| req.getSpaceArea().equals(new BigDecimal(0)) || req.getHostName() == null || req.getHostName().length() < 1) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);// 必选项不能为空!
		}
		if(checkPhoneNum(req.getHoustMobile())){
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);//手机号码格式不正确
		}
		
		HouseResource hResource = executeFindQuery(HouseResource.class, req.getHouseId());
		Residence residence = executeFindQuery(Residence.class, req.getHouseId());
		if (hResource.getStatus() != 2) {
			throw new LeoFault(SellConst.SELL_ERROR110001);//该房源不在审核中，已发布出售
		}
		
		try {
			residence.setSpaceArea(req.getSpaceArea());
			residence.setBedroomSum(req.getBedroomSum());
			residence.setLivingRoomSum(req.getLivingRoomSum());
			residence.setWcSum(req.getWcSum());
			hResource.setRentPrice(req.getPrice());
			this.getEntityManager().merge(residence);
			this.getEntityManager().merge(hResource);
			
			// 添加 联系人电话信息
			if (req.getHostName() != null && req.getHostName().length() > 0) {
				String hostName = req.getHostName();
				String[] names = hostName.split(",");
				String[] moblies = req.getHoustMobile().split(",");
				for (int i = 0; i < names.length; i++) {
					HouseResourceContact sh = new HouseResourceContact();
					sh.setHostName(names[i]);
					sh.setHostMobile(moblies[i]);
					sh.setEnable(true);
					sh.setHouseId(req.getHouseId());
					sh.setHouseState(EntityUtils.HouseStateEnum.RENT.getValue());
					this.getEntityManager().persist(sh);
				}
			}
		} catch (Exception e) {
			log.error("updatePublishRentInfo",e);
			throw new LeoFault(RentConst.RENT_PUBLISH_1200031);
		}
		return 0;
	}
	
	/**
	 * 查看出租房源详情
	 */
	@Override
	public HouseEntity rentDetails(int houseId) {
		HouseEntity entity = new HouseEntity();
		if(houseId==0){
			throw new LeoFault(RentConst.Rent_ERROR120006);
		}
		HouseResource houseResource = null;
		List<HouseResource> houseResourceList = execEqualQueryList(new Integer(houseId), HouseResource.class, HouseResource_.houseId);
		if(houseResourceList.size()>0){
			houseResource = houseResourceList.get(0);
		}
		if(houseResource==null){
			throw new LeoFault(RentConst.Rent_ERROR120006);
		}
		if (houseResource.getStatus() == 2) {
			throw new LeoFault(UcConst.UC_ERROR100013);
		}else {
			try {
				// 房屋信息
				Residence residence = getEntityManager().find(Residence.class, houseId);
				// 小区信息
				SubEstate subEstate = getEntityManager().find(SubEstate.class, residence.getSubEstateId());
				if(subEstate==null){
					logger.info("系统异常，subEstate为null");
					throw new LeoFault(CommonConst.COMMON_9000051);
				}
				Estate estate = getEntityManager().find(Estate.class, subEstate.getEstateId());
//				CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//				CriteriaQuery<Estate> cq_estate = cb.createQuery(Estate.class);
//				Root<Estate> root_estate = cq_estate.from(Estate.class);
//				cq_estate.where(cb.and(cb.and(cb.equal(root_estate.get(Estate_.areaId), residence.getEstateId())))).select(root_estate);
//				List<Estate> estateList = getEntityManager().createQuery(cq_estate).getResultList();
//				if(estateList==null || estateList.size()<=0){
//					throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);//该小区不存在,请重新搜索
//				}
//				Estate estate = estateList.get(0);
				// 行政区域
				Town town = getEntityManager().find(Town.class, estate.getParentId());
				City city = getEntityManager().find(City.class, town.getParentId());
				entity.setAreaId(city.getAreaId());
				entity.setAreaName(city.getName());
				entity.setTownName(town.getName());
				entity.setBedroomSum(residence.getBedroomSum());
				entity.setBuilding(residence.getBuilding());
				entity.setEstateId(estate.getAreaId());
				entity.setEstateName(estate.getName());
				entity.setHouseId(residence.getHouseId());
				entity.setLivingRoomSum(residence.getLivingRoomSum());
				entity.setPrice(houseResource.getRentPrice());
				entity.setPublishDate(houseResource.getPublishDate());
				entity.setRoom(residence.getRoom());
				entity.setSpaceArea(residence.getSpaceArea());
				entity.setWcSum(residence.getWcSum());
				entity.setSourceState(houseResource.getStatus());
				entity.setSubEstateName(subEstate==null?null:subEstate.getName());
			} catch (Exception e) {
				e.printStackTrace();
				throw new LeoFault(CommonConst.COMMON_9000051);
			}
			return entity;
		}
	}
	
	@Override
	public RantInfoResponse chenkHoustIsRent(int estateId, String building, String room) {/*
		if (estateId == 0) {
			throw new LeoFault(RentConst.Rent_ERROR120003);
		}
		if (StringUtils.isBlank(building)) {
			throw new LeoFault(RentConst.Rent_ERROR120004);
		}
		if (StringUtils.isBlank(room)) {
			throw new LeoFault(RentConst.Rent_ERROR120005);
		}
		 先跟据传过来的小区id查询是否有这套房子,如果有就进在查询是否在出售状态，如果没有就新增房源 
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		
		if (!DataUtil.checkNum(0, 0, building)) {
			// 必须是纯数字
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100039);
		}
		if (!DataUtil.checkNum(3, 4, room)) {
			// 标准4位数字, 2位数字 提示message
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100040);
		}
		// room 3位 前面补全一个0
		if (room.length() == 3) {
			room = "0" + room;
		}
		//CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		检查小区  id是否存在
		SubEstate sube = this.getEntityManager().find(SubEstate.class, estateId);
		if(sube == null){
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);//该小区不存在,请重新搜索
		}
		CriteriaQuery<House> cq_houst = cb.createQuery(House.class);
		Root<House> root_houst = cq_houst.from(House.class);
		cq_houst.where(cb.and(cb.equal(root_houst.get(House_.estateId), estateId)),
				cb.and(cb.equal(root_houst.get(House_.building), building)), cb.and(cb.equal(root_houst.get(House_.room), room))).select(
				root_houst);
		List<House> houseList = getEntityManager().createQuery(cq_houst).getResultList();
		House house = null;
		if (houseList.size() == 0) {
			Residence residence = new Residence();
			residence.setEstateId(estateId);
			residence.setBuilding(building);
			residence.setRoom(room);
			residence.setCoveredArea(new BigDecimal(0));// 建筑面积
			residence.setSpaceArea(new BigDecimal(0));// 内空面积
			residence.setBedroomSum(0);
			residence.setLivingRoomSum(0);
			residence.setWcSum(0);
			residence.setBalconySum(0);
			residence.setLayers(0);// 总层高
			residence.setFloor(0);// 楼层
			getEntityManager().persist(residence);
			house = residence;
		} else {
			house = houseList.get(0);
			CriteriaQuery<RentInfo> cq_rant = cb.createQuery(RentInfo.class);
			Root<RentInfo> root_rant = cq_rant.from(RentInfo.class);
			cq_rant.where(cb.and(cb.equal(root_rant.get(RentInfo_.houseId), house.getHouseId()))).select(root_rant);
			List<RentInfo> sellList = getEntityManager().createQuery(cq_rant).getResultList();
			if (sellList!=null && sellList.size() > 0) {
				// 该房源已发布出售
				throw new LeoFault(RentConst.RENT_ERROR120001);
			}

//			// 判断 这个房子相关的任何发布状态是否 在审核中....
//			String sql = "select count(1) from SourceLog log where log.enabled =1 and (log.rentInfoId = ? or log.sellInfoId =? or log.houseId=? ) and log.sourceState = 1 ";
//			Query query = this.getEntityManager().createNativeQuery(sql);
//			query.setParameter(1, house.getHouseId());
//			query.setParameter(2, house.getHouseId());
//			query.setParameter(3, house.getHouseId());
//			BigInteger count = (BigInteger) query.getSingleResult();
//			if (count.bitCount() == 0) {
//				return new RantInfoResponse(house.getHouseId());
//			} else {
//				// 该房源正在发布审核中
//				throw new LeoFault(RentConst.Rent_ERROR120002);
//			}
		}
		return new RantInfoResponse(house.getHouseId());
	*/return null;}

	@Override
	public HouseRecordEntity rentRecordDetails(int logId) {
		HouseRecordEntity hrEntity = new HouseRecordEntity();
		hrEntity.setHouseId(33131);
		hrEntity.setBuilding("1");
		hrEntity.setRoom("2000");
		hrEntity.setSpaceArea(BigDecimal.valueOf(108L));
		hrEntity.setBedroomSum(3);
		hrEntity.setLivingRoomSum(2);
		hrEntity.setWcSum(2);
		hrEntity.setEstateId(20011);
		hrEntity.setEstateName("中远两湾城");
		hrEntity.setAreaId(12);
		hrEntity.setAreaName("黄埔");
		hrEntity.setPublishDate(new Date());
		hrEntity.setSourceState(1);
		hrEntity.setSourceStateStr("审核中");
		hrEntity.setSourceLogTypeId(2);
		hrEntity.setSourceLogTypeStr("出租");
		hrEntity.setPrice(BigDecimal.valueOf(456L));
		return hrEntity;
	}
	
	private boolean checkPhoneNum(String hostMobile){
		boolean flag = false;
		String[] moblies = hostMobile.split(",");
		for (int i = 0; i < moblies.length; i++) {
			if (!DataUtil.checkNum(0, 0, moblies[i])) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public boolean getSerialCodeByHouseId(int houseId,double rentPrice){
		if(houseId==0){
			return false;
		}
		House house = getEntityManager().find(House.class, houseId);
		
		String jpql = "select rentPrice from allowrent a where ? like CONCAT(serialCode,'%') ORDER BY serialCode desc ";
		Query query = getEntityManager().createNativeQuery(jpql);
		query.setParameter(1, house.getSerialCode());
		List<Object> allowRentList = query.setFirstResult(0).setMaxResults(1).getResultList();
		if(allowRentList!=null && allowRentList.size() == 1){
			BigDecimal price = new BigDecimal(allowRentList.get(0).toString());
			if(price!=null && rentPrice < price.doubleValue()){
				return false;
			}
			return true;
		}
		return false;
	}
}
