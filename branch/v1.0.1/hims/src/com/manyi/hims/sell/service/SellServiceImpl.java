package com.manyi.hims.sell.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.check.model.CSSingleResponse.HouseResourceResponse;
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.common.PublishHouseRequest;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.Estate_;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.entity.House_;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.Town;
import com.manyi.hims.rent.RentConst;
import com.manyi.hims.sell.SellConst;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.DateUtil;
import com.manyi.hims.util.EntityUtils;

/**
 * 出售
 * @author tiger
 *
 */
@Service(value = "sellService")
@Scope(value = "singleton")
public class SellServiceImpl extends BaseService implements SellService{
	private Logger log = LoggerFactory.getLogger(SellServiceImpl.class);
	
	/**
	 * 查看房源详情
	 */
	@Override
	public HouseEntity sellDetails(int houseId) {
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
//				Estate subEstate = getEntityManager().find(Estate.class, residence.getEstateId());
//				Estate estate = getEntityManager().find(Estate.class, subEstate.getParentId());
				CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
				CriteriaQuery<Estate> cq_estate = cb.createQuery(Estate.class);
				Root<Estate> root_estate = cq_estate.from(Estate.class);
				cq_estate.where(cb.and(cb.and(cb.equal(root_estate.get(Estate_.areaId), residence.getEstateId())))).select(root_estate);
				List<Estate> estateList = getEntityManager().createQuery(cq_estate).getResultList();
				if(estateList==null || estateList.size()<=0){
					throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);//该小区不存在,请重新搜索
				}
				Estate estate = estateList.get(0);
				// 行政区域
				Town town = getEntityManager().find(Town.class, estate.getParentId());
				City city = getEntityManager().find(City.class, town.getParentId());
				entity.setAreaId(city.getAreaId());
				entity.setAreaName(city.getName());
				entity.setBedroomSum(residence.getBedroomSum());
				entity.setBuilding(residence.getBuilding());
				entity.setEstateId(estate.getAreaId());
				entity.setEstateName(estate.getName());
				entity.setHouseId(residence.getHouseId());
				entity.setLivingRoomSum(residence.getLivingRoomSum());
				entity.setPrice(houseResource.getSellPrice());
				entity.setPublishDate(houseResource.getPublishDate());
				entity.setRoom(residence.getRoom());
				entity.setSpaceArea(residence.getSpaceArea());
				entity.setWcSum(residence.getWcSum());
				entity.setSourceState(houseResource.getStatus());
			} catch (Exception e) {
				e.printStackTrace();
				throw new LeoFault(CommonConst.COMMON_9000051);
			}
			return entity;
		}
	}

	
	/**
	 * 出售 首页
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HouseEntity> indexList(){
		// 能够下发 的数据  1(发布出租/出售)审核中/审核成功; 2 改盘 审核中(改盘以前的类型,但是 在审核中)/ 审核成功/审核失败(具体的得看是否 是不租不售了) 3举报 审核失败(还原的temp表)
		String jpql="select s,h,e,town,city from HouseResource s,Residence h,Estate e,Town town,City city where "+
				"  s.houseState in ("+EntityUtils.HouseStateEnum.SELL.getValue()+","+EntityUtils.HouseStateEnum.RENTANDSELL.getValue()+
				" ) and ( ( s.actionType in ( "+EntityUtils.ActionTypeEnum.PUBLISH.getValue()+","+EntityUtils.ActionTypeEnum.CHANGE.getValue() +" ) "+
				"   and  s.status in (" + EntityUtils.StatusEnum.SUCCESS.getValue()+","+EntityUtils.StatusEnum.ING.getValue() +" ) )  or  ( s.actionType =" + 
				EntityUtils.ActionTypeEnum.LOOP.getValue()+" and s.status = "+EntityUtils.StatusEnum.ING.getValue()+"  ) ) "
				+" and s.houseId = h.houseId and h.estateId = e.areaId and e.parentId = town.areaId and town.parentId = city.areaId  order by s.publishDate desc ";//在售  , 1审核成功/2审核中, 发布,改盘,举报(举报失败的)
		/*
		String jpql="select s,h,e,town,city from HouseResource s,Residence h,Estate e,Town town,City city ,HouseResourceTemp tmp where "+
				" ( (s.houseState in (1,3) and s.actionType = 1 and tmp.houseId = 0 ) or (s.actionType = 2 and ( (tmp.houseId = s.houseId and s.status = 2) or  s.status = 1 ) ) )  and "+
				"s.houseId = h.houseId and h.estateId = e.areaId and e.parentId = town.areaId and town.parentId = city.areaId  order by s.publishDate desc ";//在售  , 1审核成功/2审核中, 发布,改盘,举报(举报失败的)
		*/
		
		Query query = this.getEntityManager().createQuery(jpql);
		query.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		query.setFirstResult(0);
		query.setMaxResults(50);
		List<Object[]> lists =  query.getResultList();
		List<HouseEntity> entitys = new ArrayList<HouseEntity>();
		if(lists != null && lists.size()>0){
			for (Object[] info : lists) {
				HouseEntity entity = new HouseEntity();
				//房源信息
				HouseResource hr = (HouseResource) info[0];
				//房屋信息
				Residence house = (Residence) info[1];
				//子小区
				Estate subEstate =(Estate) info[2];
				if(subEstate == null){
					continue;
				}
				//小区
				Estate estate = (Estate) info[2];
				if(estate == null){
					continue;
				}
				//小区所属片区
				Town area1= (Town) info[3];
				if(area1 == null){
					continue;
				}
				//行政区域
				City area = (City) info[4];
				if(area == null){
					continue;
				}
				//当前房屋 出租状态 信息
				int sourceState = hr.getStatus();//得到当前房屋的出租 审核状态
				entity.setPrice(hr.getSellPrice());
				entity.setLivingRoomSum(house.getLivingRoomSum());
				entity.setSpaceArea(house.getSpaceArea());
				entity.setWcSum(house.getWcSum());
				entity.setBedroomSum(house.getBedroomSum());
				
				entity.setAreaId(area.getAreaId());
				entity.setAreaName(area.getName());
				entity.setTownId(area1.getAreaId());
				entity.setTownName(area1.getName());
				entity.setBuilding(house.getBuilding());
				String eName = estate.getName();
				entity.setEstateName(eName);
				entity.setEstateId(estate.getAreaId());
				entity.setHouseId(house.getHouseId());
				entity.setRoom(house.getRoom());
				entity.setPublishDate(hr.getPublishDate());
				entity.setPublishStr(DateUtil.fattrDate(hr.getPublishDate()));
				entity.setSourceState(sourceState); // 审核状态
				entity.setMarkTime(System.currentTimeMillis());
				entitys.add(entity);
			}
		}
		return entitys;
	}
	
	/*
	 * 发布出售信息
	 */
	@Override
	public int publishSellInfo(PublishHouseRequest req) {

		log.info("publishSellInfo: {} ",ReflectionToStringBuilder.toString(req));
		
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
		if (req.getPrice().doubleValue() >= 10000000000d ) {
			// 1100081;//请输入合法的价格
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100081);
		}
		if (req.getBedroomSum() == 0 || req.getWcSum() == 0 || req.getLivingRoomSum() == -1) {
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100037);
		}

		if (req.getSpaceArea() == null || req.getSpaceArea().equals(new BigDecimal(0))) {
			// //请输入面积
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
				if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && (state == EntityUtils.HouseStateEnum.SELL.getValue() || state == EntityUtils.HouseStateEnum.RENTANDSELL.getValue())){
					//出售 1100045;//该房源已有出售在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100045);
				}else if(EntityUtils.ActionTypeEnum.LOOP.getValue() == actionType || EntityUtils.ActionTypeEnum.RAMDOM.getValue() == actionType){
					//出售 1100045;//该房源已有出售在审核中
					throw new LeoFault(CommonConst.CHECK_PUBLISH_1100045);
				}else if(EntityUtils.ActionTypeEnum.PUBLISH.getValue() == actionType && state == EntityUtils.HouseStateEnum.RENT.getValue()){
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
				
				//判断 是否 正在发布出售(审核中/审核成功的)
				if((hr.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue() || hr.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue() ) 
						&& ( hr.getStatus() == EntityUtils.StatusEnum.SUCCESS.getValue() || hr.getStatus() == EntityUtils.StatusEnum.ING.getValue()) ){
					//SELL_ERROR110001 , //该房源已发布出售
					throw new LeoFault(SellConst.SELL_ERROR110001);
				}
				
//				try {
					//可以发布
					Residence house = this.getEntityManager().find(Residence.class, hr.getHouseId());
					HouseResourceTemp tmp = new HouseResourceTemp();
					BeanUtils.copyProperties(house,tmp);
					BeanUtils.copyProperties(hr,tmp);
					this.getEntityManager().persist(tmp);
					//this.getEntityManager().flush();
					
					//修改成 当前 经纪人 上传的 房型
					house.setBedroomSum(req.getBedroomSum());
					house.setWcSum(req.getWcSum());
					house.setLivingRoomSum(req.getLivingRoomSum());
					house.setSpaceArea(req.getSpaceArea());
					//this.getEntityManager().merge(house);
					
					
					/*
					//判断是否在 出租, 是出租的话. 把状态 改成  租售
					if(hr.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue()){
						hr.setHouseState(EntityUtils.HouseStateEnum.RENTANDSELL.getValue());
					}else{
						hr.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());
					}
					*/
					
					//上面这个 步骤 在 审核 完成的时候  完成, 发布的时候. 直接修改 这个房源信息为 出售的
					hr.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());
					
					hr.setStatus(EntityUtils.StatusEnum.ING.getValue());
					hr.setSellPrice(req.getPrice());
					hr.setUserId(req.getUserId());
					hr.setActionType(EntityUtils.ActionTypeEnum.PUBLISH.getValue());//发布
					hr.setResultDate(null);
					hr.setPublishDate(new Date());
					hr.setOperatorId(0);//值空
					hr.setCheckNum(0);
					hr.setRemark("");
					
					//修改成 经纪人 当前上传的 房源信息数据
					this.getEntityManager().merge(hr);
					log.info("insert a sell  修改完毕之后, HouseResource: {} ",ReflectionToStringBuilder.toString(hr));
					
					ResidenceHistory rh =new ResidenceHistory();
					BeanUtils.copyProperties(house , rh);
					this.getEntityManager().persist(rh);
					
					ResidenceResourceHistory hrHistory = new ResidenceResourceHistory();
					BeanUtils.copyProperties( hr ,hrHistory);
					
					hrHistory.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());//历史表中 存放的是  用户的动作 (出售)
					hrHistory.setBedroomSum(house.getBedroomSum());
					hrHistory.setWcSum(house.getWcSum());
					hrHistory.setLivingRoomSum(house.getLivingRoomSum());
					hrHistory.setSpaceArea(house.getSpaceArea());
					
					this.getEntityManager().persist(hrHistory);
					//this.getEntityManager().flush();
					
					
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
							sh.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());//出售
							this.getEntityManager().persist(sh);// 保存
						}
					}
					//this.getEntityManager().flush();
					
					log.info("publishSellInfo: 发布出售信息保存成功");
//				} catch (Exception e) {
//					log.error("publishSellInfo",e);
//				}
				
			}
		}else{
			
			//修改 房型,面积
			Residence house = this.getEntityManager().find(Residence.class, req.getHouseId());
			house.setBedroomSum(req.getBedroomSum());
			house.setWcSum(req.getWcSum());
			house.setLivingRoomSum(req.getLivingRoomSum());
			house.setSpaceArea(req.getSpaceArea());
			//this.getEntityManager().merge(house);
			
			
			//第一次 发布信息
			HouseResource hr = new HouseResource();
			hr.setActionType(EntityUtils.ActionTypeEnum.PUBLISH.getValue());//发布
			hr.setCheckNum(0);
			hr.setGotPrice(true);
			hr.setHouseId(req.getHouseId());
			hr.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());
			hr.setPublishDate(new Date());
			hr.setSellPrice(req.getPrice());
			hr.setStatus(EntityUtils.StatusEnum.ING.getValue());//审核中
			hr.setUserId(req.getUserId());
			hr.setOperatorId(0);
			
//			try {
				getEntityManager().persist(hr);
				
				//Residence res= this.getEntityManager().find(Residence.class, req.getHouseId());
				ResidenceHistory houseHistory =new ResidenceHistory();
				BeanUtils.copyProperties(house, houseHistory);
				getEntityManager().persist(houseHistory);
				
				ResidenceResourceHistory hrHistory = new ResidenceResourceHistory();
				BeanUtils.copyProperties( hr,hrHistory);
				hrHistory.setBedroomSum(house.getBedroomSum());
				hrHistory.setWcSum(house.getWcSum());
				hrHistory.setLivingRoomSum(house.getLivingRoomSum());
				hrHistory.setSpaceArea(house.getSpaceArea());
				
				this.getEntityManager().persist(hrHistory);
				//this.getEntityManager().flush();
				
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
						sh.setHouseState(EntityUtils.HouseStateEnum.SELL.getValue());//出售
						this.getEntityManager().persist(sh);// 保存
					}
				}
				//this.getEntityManager().flush();
				
				log.info("publishSellInfo: 发布出售信息保存成功");
//			} catch (Exception e) {
//				log.error("publishSellInfo",e);
//			}
		}
		
		return 0;
	}
	
	/**
	 * 修改 发布 出售记录信息
	 */
	@Override
	public int updatePublishSellInfo(PublishHouseRequest req) {
		if (req.getHouseId() == 0 || req.getPrice() == null || req.getPrice().equals(new BigDecimal(0)) || req.getSpaceArea() == null
				|| req.getPrice().equals(new BigDecimal(0)) || req.getHostName() == null || req.getHostName().length() < 1) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000033);// 必选项不能为空!
		}
		if (checkPhoneNum(req.getHoustMobile())) {
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);// 手机号码格式不正确
		}
		HouseResource hResource = executeFindQuery(HouseResource.class, req.getHouseId());
		Residence residence = executeFindQuery(Residence.class, req.getHouseId());
		if (hResource.getStatus() != 2) {
			throw new LeoFault(SellConst.SELL_ERROR110001);//该房源不在审核中，已发布出售
		}
		
		residence.setSpaceArea(req.getSpaceArea());
		residence.setBedroomSum(req.getBedroomSum());
		residence.setLivingRoomSum(req.getLivingRoomSum());
		residence.setWcSum(req.getWcSum());
		hResource.setSellPrice(req.getPrice());
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
				this.getEntityManager().persist(sh);
			}
		}
		return 0;
	}
	
	/**
	 * 
	 */
	@Override
	public SellInfoResponse chenkHoustIsSell(int estateId, String building, String room,int houseType) {

		if(houseType==0){
			throw new LeoFault(SellConst.SELL_ERROR110003);
		}
		if (estateId == 0) {
			throw new LeoFault(SellConst.SELL_ERROR110003);
		}
		if (StringUtils.isBlank(building)) {
			throw new LeoFault(SellConst.SELL_ERROR110004);
		}
		if (StringUtils.isBlank(room)) {
			throw new LeoFault(SellConst.SELL_ERROR110005);
		}
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
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		/*检查小区  id是否存在*/
		CriteriaQuery<Estate> cq_estate = cb.createQuery(Estate.class);
		Root<Estate> root_estate = cq_estate.from(Estate.class);
		cq_estate.where(cb.and(cb.and(cb.equal(root_estate.get(Estate_.areaId), estateId)))).select(root_estate);
		List<Estate> estateList = getEntityManager().createQuery(cq_estate).getResultList();
		if(estateList==null || estateList.size()<=0){
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);//该小区不存在,请重新搜索
		}
//		Estate sube = this.getEntityManager().find(Estate.class, estateId);
		Estate sube = estateList.get(0);
		
		/* 先跟据传过来的小区id查询是否有这套房子,如果有就进在查询是否在出售状态，如果没有就新增房源 */
		CriteriaQuery<House> cq_houst = cb.createQuery(House.class);
		Root<House> root_houst = cq_houst.from(House.class);
		cq_houst.where(cb.and(cb.equal(root_houst.get(House_.estateId), estateId)),
				cb.and(cb.equal(root_houst.get(House_.building), building)), cb.and(cb.equal(root_houst.get(House_.room), room))).select(
				root_houst);
		List<House> houseList = getEntityManager().createQuery(cq_houst).getResultList();
		House house = null;
		if (houseList==null || houseList.size() == 0) {
			
			//获取 estate的 serialCode
			Estate tmpEstate = this.getEntityManager().find(Estate.class, estateId);
			String serialCode = "";
			if(tmpEstate != null){
				serialCode = tmpEstate.getSerialCode();
			}
			
			Residence residence = new Residence();
			residence.setEstateId(estateId);
			residence.setSerialCode(serialCode);
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
			
			ResidenceHistory residenceHistory = new ResidenceHistory();
			BeanUtils.copyProperties(residence, residenceHistory);
			getEntityManager().persist(residenceHistory);
			house = residence;
		} else {
			house = houseList.get(0);
			CriteriaQuery<HouseResource> cq_sell = cb.createQuery(HouseResource.class);
			Root<HouseResource> root_sell = cq_sell.from(HouseResource.class);
			cq_sell.where(cb.and(cb.equal(root_sell.get(HouseResource_.houseId), house.getHouseId()))).select(root_sell);
			List<HouseResource> sellList = getEntityManager().createQuery(cq_sell).getResultList();
			if (sellList!=null && sellList.size() > 0) {
				HouseResource houseResource = sellList.get(0);
				//发布出租
				if(houseType==1){
					//在租和在租在售的不能发布
					if(houseResource.getStatus()==2){
						if(houseResource.getHouseState()==1 || houseResource.getHouseState()==3){
							// 该房源已发布出租
							throw new LeoFault(RentConst.RENT_ERROR120001);
						}
						if(houseResource.getActionType()==2){
							//已有改盘审核中
							throw new LeoFault(UcConst.UC_ERROR100014);
						}
						if(houseResource.getActionType()==3){
							//已有举报审核中
							throw new LeoFault(UcConst.UC_ERROR100015);
						}
						if(houseResource.getActionType()==4 || houseResource.getActionType()==5){
							throw new LeoFault(RentConst.RENT_ERROR120001);
						}
					}
//					else{
						//如果这套房子在售，改为在租在售
//						if(houseResource.getHouseState()==2){
//							houseResource.setHouseState(3);
//							getEntityManager().persist(houseResource);
//						}
					return new SellInfoResponse(houseResource.getHouseId()); 
//					}
				}
				//发布出售
				if(houseType==2){
					//在租和在租在售的不能发布
					if(houseResource.getStatus()==2){
						if(houseResource.getHouseState()==2 || houseResource.getHouseState()==3){
							// 该房源已发布出售
							throw new LeoFault(SellConst.SELL_ERROR110001);
						}
						if(houseResource.getActionType()==2){
							//已有改盘审核中
							throw new LeoFault(UcConst.UC_ERROR100014);
						}
						if(houseResource.getActionType()==3){
							//已有举报审核中
							throw new LeoFault(UcConst.UC_ERROR100015);
						}
						if(houseResource.getActionType()==4 || houseResource.getActionType()==5){
							throw new LeoFault(SellConst.SELL_ERROR110001);
						}
					}
//					else{
						//如果这套房子在租，改为在租在售
//						if(houseResource.getHouseState()==1){
//							houseResource.setHouseState(3);
//							getEntityManager().persist(houseResource);
//						}
					return new SellInfoResponse(houseResource.getHouseId()); 
//					}
				}
			}
		}
		return new SellInfoResponse(house.getHouseId());
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


	@Override
	public Page<HouseResourceResponse> searchHouseResources(HouseSearchRequest req) {
		
		return null;
	}
}
