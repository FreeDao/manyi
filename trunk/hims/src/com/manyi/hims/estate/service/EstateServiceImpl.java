package com.manyi.hims.estate.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.common.service.CommonService;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.Address_;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.EstateHistory;
import com.manyi.hims.entity.Estate_;
import com.manyi.hims.entity.Province;
import com.manyi.hims.entity.Town;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.aiwu.EstateImage;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.estate.EstateUtil;
import com.manyi.hims.estate.model.ChangeImgJsonModel;
import com.manyi.hims.estate.model.ChangeJsonModel;
import com.manyi.hims.estate.model.EstateDetailRes;
import com.manyi.hims.estate.model.EstateImageRes;
import com.manyi.hims.estate.model.EstateReq;
import com.manyi.hims.estate.model.EstateRes;
import com.manyi.hims.estate.model.EstateResponse;
import com.manyi.hims.estate.model.EstateVerifyReq;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.service.PayService;
import com.manyi.hims.uc.PinYin;
import com.manyi.hims.util.CollectionUtils;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.Geohash;
import com.manyi.hims.util.PushUtils;
import com.manyi.hims.util.enums.estate.EstateTypeEnum;

/**
 * @author zxc
 */
@Service(value = "estateService")
@Scope(value = "singleton")
public class EstateServiceImpl extends BaseService implements EstateService {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("payService")
	private PayService payService;

	public void setPayService(PayService payService) {
		this.payService = payService;
	}

	@Autowired
	private PushUtils pushUtils;

	@Autowired
	private CommonService commonService;

	/**
	 * 编辑小区 (包括 图片 , 子划分, 别名,坐标 等等信息)
	 */
	@Override
	public Response editEsate(EstateDetailRes req) {
		log.info("修改小区 传递的参数: {} ",ReflectionToStringBuilder.toString(req));
		
		List<ChangeJsonModel> addressList =EstateUtil.jsonStrToList(req.getAddress(),ChangeJsonModel.class);
		List<ChangeJsonModel> subestateList = EstateUtil.jsonStrToList(req.getSubestate(),ChangeJsonModel.class);
		List<ChangeJsonModel> aliasNames = EstateUtil.jsonStrToList(req.getAliasName(),ChangeJsonModel.class);
		List<ChangeImgJsonModel> imgs = EstateUtil.jsonStrToList(req.getImgkeys(),ChangeImgJsonModel.class);
		
		Estate estate  = this.getEntityManager().find(Estate.class, req.getEstateId());
		
		//地址
		if(addressList != null && addressList.size()>0){
			for (int j = 0; j < addressList.size(); j++) {
				ChangeJsonModel m = addressList.get(j);
				if("".equals(m.getId())){
					//新增一条记录
					Address address = new Address();
					address.setAddress(m.getName());
					address.setEstateId(req.getEstateId());
					address.setStatus(EntityUtils.AreaStatusEnum.SUCCESS.getValue());
					this.getEntityManager().persist(address);
				}else{
					Address add = this.getEntityManager().find(Address.class, Long.parseLong(m.getId()));
					if("".equals(m.getName())){
						//删除
						add.setStatus(EntityUtils.AreaStatusEnum.DISABLED.getValue());
					}else{
						//修改
						add.setAddress(m.getName());
					}
					this.getEntityManager().merge(add);
				}
			}
		}
		//子划分
		if(subestateList != null && subestateList.size()>0){
			for (int j = 0; j < subestateList.size(); j++) {
				ChangeJsonModel m = subestateList.get(j);
				if("".equals(m.getId())){
					//新增一条记录
					SubEstate sube = new SubEstate();
					sube.setAddTime(new Date());
					sube.setName(m.getName());
					sube.setStatus(EntityUtils.AreaStatusEnum.SUCCESS.getValue());
					sube.setEstateId(req.getEstateId());
					this.getEntityManager().persist(sube);
				}else{
					SubEstate add = this.getEntityManager().find(SubEstate.class, Integer.parseInt(m.getId()));
					if("".equals(m.getName())){
						//删除
						add.setStatus(EntityUtils.AreaStatusEnum.DISABLED.getValue());
					}else{
						//修改
						add.setName(m.getName());
					}
					this.getEntityManager().merge(add);
				}
			}
		}
		//图片
		if(imgs != null && imgs.size()>0){
			for (int j = 0; j < imgs.size(); j++) {
				ChangeImgJsonModel m = imgs.get(j);
				if("0".equals(m.getType())){
					//小区边界图
					estate.setEstateRangeImgKey(m.getName());
					continue;
				}
				if("".equals(m.getId())){
					//新增一条记录
					EstateImage img = new EstateImage();
					img.setAddTime(new Date());
					img.setImgKey(m.getName());
					img.setStatus(EntityUtils.AreaStatusEnum.SUCCESS.getValue());
					img.setOrderNum(Integer.parseInt(m.getOrderNum()));
					img.setType(Integer.parseInt(m.getType()));
					img.setEstateId(req.getEstateId());
					img.setThumbnailKey(img.getImgKey() + ".thumbnail");
					this.getEntityManager().persist(img);
				}else{
					EstateImage add = this.getEntityManager().find(EstateImage.class, Integer.parseInt(m.getId()));
					if("".equals(m.getName())){
						//删除
						add.setStatus(EntityUtils.AreaStatusEnum.DISABLED.getValue());
					}else{
						//修改
						add.setImgKey(m.getName());
						add.setOrderNum(Integer.parseInt(m.getOrderNum()));
						add.setThumbnailKey(add.getImgKey() + ".thumbnail");
					}
					
					this.getEntityManager().merge(add);
				}
			}
		}
		//别名
		StringBuilder estateNameSb = new StringBuilder("[");
		if(aliasNames != null && aliasNames.size()>0){
			for (int j = 0; j < aliasNames.size(); j++) {
				ChangeJsonModel m = aliasNames.get(j);
				if(!"".equals(m.getName())){
					if(j != 0){
						estateNameSb.append(",");
					}
					estateNameSb.append("\""+m.getName()+"\"");
				}
			}
		}
		estateNameSb.append("]");
		
		//修改小区信息
		
		estate.setParentId(req.getTownId());
		estate.setAliasName(estateNameSb.toString());
		estate.setLatitude(req.getLatitude());
		estate.setLongitude(req.getLongitude());
		estate.setName(req.getName());
		estate.setMainGateAddress(req.getMainGateAddress());
		estate.setMainGateLatitude(req.getMainGateLatitude());
		estate.setMainGateLongitude(req.getMainGateLongitude());
		estate.setMainGateStreetSceneryLink(req.getMainGateStreetSceneryLink());
		estate.setConstructDate(req.getConstructDate());
		estate.setType(req.getType());
		if(StringUtils.isNotBlank(estate.getLatitude()) && StringUtils.isNotBlank(estate.getLongitude())){
			String hash = Geohash.encode(estate.getLatitude(), estate.getLongitude());
			estate.setAxisHash(hash);
		}
		if(StringUtils.isNotBlank(estate.getMainGateLatitude()) && StringUtils.isNotBlank(estate.getMainGateLongitude())){
			String hash = Geohash.encode(estate.getMainGateLatitude(), estate.getMainGateLongitude());
			estate.setMainGateAxisHash(hash);
		}
		
		return null;
	}
	
	@Override
	public EstateResponse detailEsate(String estateId) {
		if (StringUtils.isBlank(estateId)) {
			return new EstateResponse(1, "小区id为空！");
		}
		List<Address> address = execEqualQueryList(Integer.valueOf(estateId), Address.class, Address_.estateId);
		EstateResponse estateResponse = new EstateResponse();
		estateResponse.setAreaRoad(StringUtils.join(CollectionUtils.getProperty(address, "address"), "/"));
		return estateResponse;
	}

	/**
	 * 编辑小区 时 , 加载 小区 页面 areaId  小区ID
	 */
	@Override
	public EstateDetailRes findEstateById(String areaId) {
		EstateDetailRes res = new EstateDetailRes();
		if(areaId ==  null  || "".equals(areaId)){
			log.info("小区ID为null , 数据不正确");
			return res;
		}
		String jpql ="select e , t, c , p  from Estate e , Town t, City c , Province p where e.parentId = t.areaId and t.parentId = c.areaId and c.parentId = p.areaId and  e.areaId = ?";
		Query query = this.getEntityManager().createQuery(jpql).setParameter(1, Integer.parseInt(areaId));
		List<Object[]> estates =  query.getResultList();
		if(estates != null && estates.size()>0){
			Object[] rows = estates.get(0);
			Estate estate =(Estate) rows[0];
			Town town = (Town) rows[1];
			City area = (City) rows[2];
			Province city = (Province) rows[3];
			
			BeanUtils.copyProperties(estate, res);
			if(StringUtils.isBlank(res.getAliasName())){
				res.setAliasName("[]");
			}else{
				//处理成[{'id':'1','name':''}] json字符串
				JSONArray json = JSONArray.fromObject(res.getAliasName());
				List<String> list= (List<String>) JSONArray.toCollection(json,String.class);
				if(list != null && list.size()>0){
					StringBuilder sb = new StringBuilder("[");
					for (int i = 0; i < list.size(); i++) {
						String name = list.get(i);
						if(i != 0){
							sb.append(",");
						}
						sb.append("{\"id\":\"0\" , \"name\" : \""+name+"\"}");
					}
					sb.append("]");
					res.setAliasName(sb.toString());
				}
				
			}
			res.setEstateId(estate.getAreaId());
			res.setStatusStr(EntityUtils.AreaStatusEnum.getByValue(res.getStatus()).getDesc());
			res.setTypeStr(EstateTypeEnum.getByValue(estate.getType()).getDesc());
			
			res.setTownId(town.getAreaId());
			res.setTownName(town.getName());
			res.setAreaId(area.getAreaId());
			res.setAreaName(area.getName());
			res.setCityId(city.getAreaId());
			res.setCityName(city.getName());
			
			//查询地址
			jpql = " from Address a where a.estateId = ? ";
			Query query1 = this.getEntityManager().createQuery(jpql).setParameter(1, estate.getAreaId());
			List<Address> address =  query1.getResultList();
			try {
				String json = EstateUtil.objProperToJsonStr(address, new String[]{"addressId","address"});
				json = json.replace("AddressId","id").replace("Address", "name");
				res.setAddress(json);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//查询子划分
			jpql = " from SubEstate a where a.estateId = ?";
			Query query2 = this.getEntityManager().createQuery(jpql).setParameter(1, estate.getAreaId());
			List<SubEstate> subEstates =  query2.getResultList();
			try {
				String json = EstateUtil.objProperToJsonStr(subEstates, new String[]{"id","name"});
				json = json.replace("Id","id").replace("Name", "name");
				res.setSubestate(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//小区图片
			jpql = " from EstateImage a where a.estateId = ? and a.status <> ? order by a.type asc , a.orderNum asc ";
			Query query3 = this.getEntityManager().createQuery(jpql).setParameter(1, estate.getAreaId()).setParameter(2, EntityUtils.AreaStatusEnum.DISABLED.getValue());
			List<EstateImageRes> imgs =  query3.getResultList();
			res.setImages(imgs);
			
		}
		return res;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Response editEsate(String esateName, String estateId, String address) {
		if (StringUtils.isBlank(esateName)) {
			return new Response(1, "小区名称为空！");
		}
		if (StringUtils.isBlank(estateId)) {
			return new Response(1, "小区id为空！");
		}
		if (StringUtils.isBlank(address)) {
			return new Response(1, "小区地址为空！");
		}

		int estateIdTemp = Integer.valueOf(estateId);
		Estate estate = execEqualQuerySingle(estateIdTemp, Estate.class, Estate_.areaId);
		if (estate == null) {
			return new Response(1, "小区记录为空！");
		}
		
		List<Address> addressList = execEqualQueryList(estateIdTemp, Address.class, Address_.estateId);
		if (addressList != null && addressList.size() != 0) {
			if (StringUtils.equals(esateName, estate.getName()) && StringUtils.equals(address, StringUtils.join(CollectionUtils.getProperty(addressList, "address"), "/"))) {
				return new Response(1, "提交的小区名称,小区地址数据均未变化！");
			}
		}
		
		List<Estate> list = this.getEntityManager().createQuery("from Estate e where e.name = ?1").setParameter(1, esateName).getResultList();
		if (list != null && list.size() > 0) {
			for(Estate _estate : list){
				List<Address> addList = execEqualQueryList(_estate.getAreaId(), Address.class, Address_.estateId);
				if (addList != null && addList.size() != 0) {
					if (StringUtils.equals(esateName, _estate.getName()) && StringUtils.equals(address, StringUtils.join(CollectionUtils.getProperty(addList, "address"), "/"))) {
						return new Response(1, "提交的小区名称,小区地址数据已存在！");
					}
				}
			}
		}
		
		estate.setName(esateName);
		String nameAcronym = PinYin.converterToFirstSpell(esateName);
		estate.setNameAcronym(nameAcronym);
		
		for (Address addr : addressList) {
			this.getEntityManager().remove(addr);
		}
		insertAddress(address, estateIdTemp);
		return new Response();
	}

	@Override
	public Response addEsate(final String esateName, final int estateId, int areaId, String address, String serialCode) {
		// 验证非空
		if (areaId == 0) {
			return new Response(1, "区域id为空！");
		}
		if (StringUtils.isBlank(esateName)) {
			return new Response(1, "小区名称为空！");
		}
		if (estateId == 0) {
			return new Response(1, "小区id为空！");
		}
		if (StringUtils.isBlank(address)) {
			return new Response(1, "小区地址为空！");
		}

		List<Estate> estateList = execPredicate(2, Estate.class, new IPredicateParam<Estate>() {

			@Override
			public void initPredicate(Root<Estate> root, CriteriaBuilder cb, List<Predicate> list) {
				list.add(cb.and(cb.equal(root.get(Estate_.parentId), estateId)));
				list.add(cb.and(cb.equal(root.get(Estate_.name), esateName)));
			}
		});

		if (estateList != null && estateList.size() > 0) {
			return new Response(1, "该小区已存在！");
		} else {
			Estate estate = new Estate();
			estate.setName(esateName);
			estate.setParentId(estateId);
			estate.setStatus(EntityUtils.StatusEnum.SUCCESS.getValue());
			estate.setSerialCode(serialCode);
			getEntityManager().persist(estate);
			
			String nameAcronym = PinYin.converterToFirstSpell(esateName);
			estate.setNameAcronym(nameAcronym);

			insertAddress(address, estate.getAreaId());
		}
		return new Response();
	}

	private void insertAddress(String address, int estateId) {
		if (address.contains("/")) {
			String[] address_ = address.split("/");
			for (String add : address_) {
				Address ads = new Address();
				ads.setAddress(add);
				ads.setEstateId(estateId);
				getEntityManager().persist(ads);
			}
		} else {
			Address ads = new Address();
			ads.setAddress(address);
			ads.setEstateId(estateId);
			getEntityManager().persist(ads);
		}
	}

	/**
	 * 
	 * 通过搜索 得到对应的 列表内容
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<EstateRes> estateList(EstateReq req) {
		String sql_count = "select count(1)  from Area town join Area e on (e.parentId =town.areaId  and e.DTYPE='Estate') join Area area "+
				" on (town.parentId = area.areaId and town.DTYPE='Town' and area.DTYPE='City') where e.areaId <>0  and e.status=1 ";
		String sql = "select e.areaId estateId,e.name estateName,e.road road,area.areaId areaId,area.name areaName,town.areaId townId,town.name townName,e.createTime,p.areaId pId,p.name pName "+
				" from Area e join Area town on  (e.parentId =town.areaId  and e.DTYPE='Estate')  join Area area "+
				" on  (town.parentId = area.areaId and town.DTYPE='Town' and area.DTYPE='City') join Area p on (area.parentId = p.areaId and p.DTYPE='Province') where e.areaId <>0 and e.status=1";

		List<Object> pars = new ArrayList<Object>();
		//按照小区  ID 搜索
		if (req.getEstateId() > 0) {
			sql_count += " and e.areaId = ? ";
			sql += " and e.areaId = ? ";
			pars.add(req.getEstateId()); 
		}
		
		// 按照小区 名称 搜索
		if (StringUtils.isNotBlank(req.getEstateName())) {
			String name = "%" + req.getEstateName() + "%";
			sql += " and ( e.name like ? or e.road like ? ) ";
			sql_count += " and ( e.name like ? or e.road like ? ) ";
			pars.add(name);
			pars.add(name);
		}
		// 按照 区域板块
		
		if (req.getCityType() > 0) {
			sql_count += " and area.parentId = ? ";
			sql += " and area.parentId = ? ";
			pars.add(req.getCityType()); 
			
		}
		if ( req.getParentId() > 0) {
				// 按照行政区域搜索
			sql_count += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getParentId());
		} 
		if (req.getAreaId() > 0 ) {
				// 按照 行政区下面的 片区 进行搜索
			sql_count += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
		}
		
	/*	
		// 通过 区域 进行搜索 房源信息
		if (req.getAreaId() != 0) {
			if (req.getParentId() == 2) {
				// 按照行政区域搜索
				sql_count += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getAreaId());
			} else {
				// 按照 行政区下面的 片区 进行搜索
				sql_count += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
			}
		}*/
		
		
		// 按照 审核状态 搜索
		if (req.getSourceState() != 0) {

		}
		Query count_query = this.getEntityManager().createNativeQuery(sql_count);
		Query query = this.getEntityManager().createNativeQuery(sql);
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
		PageResponse<EstateRes> page = new PageResponse<EstateRes>();
		List<EstateRes> resList= new ArrayList<EstateRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage() - 1) * req.getRows());// 起始下标
			query.setMaxResults(req.getRows());// 查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = ((total-1) / req.getRows()+1);
			page.setTotalPage(n);// 总页数
			List<Object[]> subes = query.getResultList();
			if (subes != null && subes.size() > 0) {
				for (Object[] row : subes) {
					EstateRes res = new EstateRes();
					res.setEstateId(Integer.valueOf(row[0] + ""));
					res.setEstateName(row[1] + "");
					res.setRoad(row[2] == null ? "" : row[2] + "");
					res.setAreaId(Integer.valueOf(row[3] + ""));
					res.setAreaName(row[4] + "");
					res.setTownId(Integer.valueOf(row[5] + ""));
					res.setTownName(row[6] + "");
					res.setPublishDate((Date) row[7]);
					res.setCityId(Integer.valueOf(row[8] + ""));
					res.setCityName(row[9] + "");
					
					sql = " select count(hr.houseId) houseSum , sum(CASE WHEN hr.houseState =1  THEN 1 ELSE 0 END) rentSum , "+
							" sum(CASE WHEN  hr.houseState = 2 THEN 1 ELSE 0 END ) sellSum , sum(CASE WHEN  hr.houseState = 3 THEN 1 ELSE 0 END ) srSum ,"+
							" sum(CASE WHEN  hr.houseState = 4 THEN 1 ELSE 0 END ) notSum from HouseResource hr join House h on h.houseId = hr.houseId where h.estateId =  "
							+ res.getEstateId();// 小区下房源数量
					List<Object[]> estateObjList = this.getEntityManager().createNativeQuery(sql).getResultList();
					
					// 统计 小区下面的租售 房源数量
					int houseNum = 0;
					// 小区下面 在租 房源数量
					int rentNum = 0;
					// 小区下面 在售 房源数量
					int sellNum = 0;
					// 小区 下面 在租在售的房源 数量
					int srNum =0;
					// 小区下面 不租不售的房源
					int notNum = 0;
					if(estateObjList != null && estateObjList.size()>0){
						Object[] estateObj = estateObjList.get(0);
						BigInteger tmp = (BigInteger) estateObj[0];
						if(tmp != null)
						houseNum = tmp.intValue();
						BigDecimal tmp1 = (BigDecimal) estateObj[1];
						if(tmp1 != null)
						rentNum = tmp1.intValue();
						tmp1 = (BigDecimal) estateObj[2];
						if(tmp1 != null)
						sellNum = tmp1.intValue();
						tmp1 = (BigDecimal) estateObj[3];
						if(tmp1 != null)
						srNum = tmp1.intValue();
						tmp1 = (BigDecimal) estateObj[4];
						if(tmp1 != null)
						notNum = tmp1.intValue();
						log.info("estast id :{}  , name : {} , houseNum :{} ,rentNum :{}, sellNum:{},srNum :{},notNum:{}",
								res.getEstateId(),res.getEstateName(),houseNum,rentNum,sellNum,srNum,notNum);
					}else{
						log.debug("error 没有统计出小区房源信息数量   estast id :{}  , name : {}  ",res.getEstateId(),res.getEstateName());
					}
					res.setHouseNum(houseNum);
					res.setSellNum(sellNum+srNum);
					res.setRentNum(rentNum+srNum);
					resList.add(res);
				}
			}
		}
		page.setRows(resList);
		return page;
	}

	/**
	 * 拼装查询条件
	 */
	private String executeSqlCommand(StringBuilder sql, EstateReq req, List<Object> pars) {
		// 按照小区 名称 搜索
		if (StringUtils.isNotBlank(req.getEstateName())) {
			String name = "%" + req.getEstateName() + "%";
			sql.append(" and ( ah.name like ?) ");
			pars.add(name);
		}
		// 按照 区域板块 通过 区域 进行搜索 房源信息
		if (req.getCityType() > 0) {
			sql.append(" and city.parentId = ? ");
			pars.add(req.getCityType());

		}
		if (req.getParentId() > 0) {
			// 按照行政区域搜索
			sql.append(" and city.areaId = ? ");
			pars.add(req.getParentId());
		}
		if (req.getAreaId() > 0) {
			// 按照 行政区下面的 片区 进行搜索
			sql.append(" and town.areaId = ? ");
			pars.add(req.getAreaId());
		}
		
		/*
		// 按照 区域板块 通过 区域 进行搜索 房源信息
		if (req.getAreaId() != 0) {
			if (req.getParentId() == 2) {
				// 按照行政区域搜索
				sql.append(" and city.areaId = ?  ");
				pars.add(req.getAreaId());
			} else {
				// 按照 行政区下面的 片区 进行搜索
				sql.append(" and town.areaId = ?  ");
				pars.add(req.getAreaId());
			}
		}*/
		
		
		// 按照 审核状态 搜索
		if (req.getSourceState() != 0) {
			sql.append(" and ah.status=?  ");
			pars.add(req.getSourceState());
		}
		return sql.toString();
	}

	/**
	 * 获取小区状态为 未审核和审核失败的小区 数量
	 * 
	 * @param req
	 * @return
	 */
	private int estateLogCount(EstateReq req) {
		StringBuilder sql_count = new StringBuilder("SELECT count(1)  ");
		sql_count.append(" FROM areahistory ah  ");
		sql_count.append(" JOIN Area town ON (ah.parentId =town.areaId AND ah.DTYPE='EstateHistory' AND town.DTYPE='Town') ");
		sql_count.append(" JOIN Area city ON (town.parentId = city.areaId AND town.DTYPE='Town' AND city.DTYPE='City')  ");
		sql_count.append(" JOIN Area p ON (city.parentId = p.areaId AND p.DTYPE='Province')  ");
		sql_count.append(" JOIN User user ON user.uid = ah.userId ");
		sql_count.append(" JOIN (SELECT address.estateId AS estateId, GROUP_CONCAT(address.address SEPARATOR '/') AS address ");
		sql_count.append(" FROM  Address address ");
		sql_count.append(" GROUP BY address.estateId) address ON address.estateId = ah.areaId ");
		sql_count.append(" WHERE ah.areaId <>0 AND ah.status IN (1,2,3) ");
//		sql_count.append(" FROM areahistory ah  ");
//		sql_count.append(" JOIN Area town ON (ah.parentId =town.areaId AND ah.DTYPE='EstateHistory' AND town.DTYPE='Town') ");
//		sql_count.append(" JOIN Area city ON (town.parentId = city.areaId AND town.DTYPE='Town' AND city.DTYPE='City')  ");
//		sql_count.append(" JOIN user user on user.uid = ah.userId ");
//		sql_count.append(" WHERE ah.areaId <> 0 AND ah.status IN (1,2,3) ");
		List<Object> pars = new ArrayList<Object>();
		String executeSql = executeSqlCommand(sql_count, req, pars);
		Query count_query = this.getEntityManager().createNativeQuery(executeSql);
		packageParamter(pars, count_query);
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		return total;
	}

	/**
	 * 获取小区状态为 未审核和审核失败的小区详细信息
	 * 
	 * @param req
	 * @return
	 */
	private List<Object[]> estateLogDetailList(EstateReq req) {
		StringBuilder sql_result = new StringBuilder(
				"SELECT ah.areaId logId,ah.name estateName ,address.address road,city.areaId areaId,city.name areaName,town.areaId townId,town.name townName,ah.createTime,ah.status,user.uid publishId,user.realName publishName,p.areaId pId, p.name pName ");
		sql_result.append(" FROM areahistory ah  ");
		sql_result.append(" JOIN Area town ON (ah.parentId =town.areaId AND ah.DTYPE='EstateHistory' AND town.DTYPE='Town') ");
		sql_result.append(" JOIN Area city ON (town.parentId = city.areaId AND town.DTYPE='Town' AND city.DTYPE='City')  ");
		sql_result.append(" JOIN Area p ON (city.parentId = p.areaId AND p.DTYPE='Province')  ");
		sql_result.append(" JOIN User user ON user.uid = ah.userId ");
		sql_result.append(" JOIN (SELECT address.estateId AS estateId, GROUP_CONCAT(address.address SEPARATOR '/') AS address ");
		sql_result.append(" FROM  Address address ");
		sql_result.append(" GROUP BY address.estateId) address ON address.estateId = ah.areaId ");
		sql_result.append(" WHERE ah.areaId <>0 AND ah.status IN (1,2,3) ");
		List<Object> pars = new ArrayList<Object>();
		String executeSql = executeSqlCommand(sql_result, req, pars);
		Query query = this.getEntityManager().createNativeQuery(executeSql);
		query.setFirstResult((req.getPage() - 1) * req.getRows());// 起始下标
		query.setMaxResults(req.getRows());// 查询出来的数量/条数
		packageParamter(pars, query);
		List<Object[]> subes = new ArrayList<Object[]>();
		subes = query.getResultList();
		return subes;
	}

	/**
	 * 封装查询sql参数
	 * 
	 * @param pars
	 * @param query
	 */
	private void packageParamter(List<Object> pars, Query query) {
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i + 1, pars.get(i));
			}
		}
	}

	/**
	 * 获取小区状态为 未审核和审核失败的小区列表
	 */
	@Override
	public PageResponse<EstateRes> estateLogList(EstateReq req) {
		int total = estateLogCount(req);
		PageResponse<EstateRes> page = new PageResponse<EstateRes>();
		if (total > 0) {
			List<Object[]> subes = estateLogDetailList(req);
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = (total / req.getRows());
			page.setTotalPage(n);// 总页数
			if (subes != null && subes.size() > 0) {
				for (Object[] row : subes) {
					EstateRes res = new EstateRes();
					res.setLogId(Integer.valueOf(row[0] + ""));
					res.setEstateName(row[1] + "");
					res.setRoad(row[2] == null ? "" : row[2] + "");
					res.setAreaId(Integer.valueOf(row[3] + ""));
					res.setAreaName(row[4] + "");
					res.setTownId(Integer.valueOf(row[5] + ""));
					res.setTownName(row[6] + "");
					res.setPublishDate((Date) row[7]);
					res.setSourceState(Integer.valueOf(row[8] + ""));
					res.setPublishId(Integer.valueOf(row[9] + ""));
					res.setPublishName(row[10] + "");
					res.setCityId(Integer.valueOf(row[11] + ""));
					res.setCityName(row[12] + "");
					
					if (page.getRows() == null) {
						page.setRows(new ArrayList<EstateRes>());
					}
					page.getRows().add(res);
				}
			} else {
				page.setRows(new ArrayList<EstateRes>());
			}
		} else {
			page.setRows(new ArrayList<EstateRes>());
		}

		return page;
	}

	/**
	 * @date 2014年4月16日 上午10:04:10
	 * @author Tom
	 * @description 检索没审核的小区相关信息
	 */
	public EstateResponse findNotPassEstateById(int id) {
		EstateResponse estateResponse = new EstateResponse();
		// @FIXME
		// 这里可以采取数据库关联查询，但是 需要还没有确认。
		// 查询当前AddEstateLog数据
		EstateHistory estateHistory = executeFindQuery(EstateHistory.class, id);
		Estate estate = executeFindQuery(Estate.class, id);
		estateResponse.setAreaName(estateHistory.getName());
		estateResponse.setAreaRoad(estate.getRoad());
		// 板块
		Town town = this.getEntityManager().find(Town.class, estate.getParentId());
		estateResponse.setTownName(town.getName());
		// 区域
		City city = this.getEntityManager().find(City.class, town.getParentId());
		estateResponse.setCityName(city.getName());
		return estateResponse;
	}

	public List<Object> getEstateByName(String estateName) {
		Query query = this.getEntityManager().createNativeQuery("select a	.name from Area a where a.dtype = 'Estate' and a.name like ?1");
		query.setParameter(1, "%" + estateName + "%");

		List<Object> list = query.setFirstResult(0).setMaxResults(10).getResultList();
		return list;
	}

	@Override
	public void checkEstate(int estateId, int status) {
		Estate estate = getEntityManager().find(Estate.class, estateId);
		estate.setStatus(status);
		getEntityManager().merge(estate);

	}

	/**
	 * 通过EstateId获取 EstateHistory
	 */
	private EstateHistory getEstateHistoryByEstateId(Integer estateId) {
		EstateHistory estateHistory = null;
		// 获取小区History 对象
		String historyJPQL = "FROM EstateHistory eh WHERE eh.areaId=? ";
		Query query = this.getEntityManager().createQuery(historyJPQL);
		query.setParameter(1, estateId);
		List<EstateHistory> hsitorys = query.getResultList();

		if (hsitorys != null && hsitorys.size() > 0) {
			estateHistory = hsitorys.get(0);
		}
		return estateHistory;
	}

	/**
	 * 小区审核
	 */

	@Override
	public void estateVerify(EstateVerifyReq req) {
		String remark = req.getRemark();
		Integer estateId = req.getEstateId();
		// 当审核通过时
		String verify = req.getVerify();
		EstateHistory estateHistory = getEstateHistoryByEstateId(estateId);
		Estate estate = this.getEntityManager().find(Estate.class, estateId);
		if (!org.springframework.util.StringUtils.isEmpty(remark) && !"输入审核通过或者失败理由".equals(remark)) {
			estate.setRemark(remark);
		}
		String pushMsgResult = "";
		// 如果成功更新Area 和 AreaHistory 的状态为1
		// 如果失败更新AreaHistory的状体为3 删除Area表数据
		if ("ok".equals(verify)) {
			estate.setStatus(1);
			if (estateHistory != null)
				estateHistory.setStatus(1);
			// 新增小区成功付款
			AddPayReq payReq = new AddPayReq(estate.getUserId(), EntityUtils.AwardTypeEnum.ADDESTATE.getValue());
			payService.addPay(payReq);
			pushMsgResult = "成功";
		} else {
			this.getEntityManager().remove(estate);
			if (estateHistory != null)
				estateHistory.setStatus(3);
			pushMsgResult = "失败";
		}
		commonService.updateUserCreateLogCount(estate.getUserId());
		User user = executeFindQuery(User.class, estate.getUserId());
		pushUtils.sendEstateCheckPushMsg(user, estate.getName(), pushMsgResult);
	}
	
	
	/**
	 * @date 2014年6月23日 下午4:15:18
	 * @author Tom
	 * @description  
	 * 
	 */
	public SubEstate getSubEstateById(int subEstateId) {
		return this.getEntityManager().getReference(SubEstate.class, subEstateId);
	}
	
	/**
	 * @date 2014年6月23日 下午4:15:18
	 * @author Tom
	 * @description  
	 * 根据子划分id获得小区id
	 */
	public int getEstateIdBySubEstateId(int subEstateId) {
		SubEstate subEstate = this.getEntityManager().getReference(SubEstate.class, subEstateId);
		return subEstate.getEstateId();
	}
	
	/**
	 * @date 2014年6月23日 下午4:15:18
	 * @author Tom
	 * @description  
	 * 按小区Id查询  有效子划分
	 */
	public List<SubEstate> findSubEstateListByEstateId(int estateId) {
		Query query = this.getEntityManager().createQuery("from SubEstate where estateId = ? and status = 1");
		query.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		query.setParameter(1, estateId);
		return query.getResultList();
	}

	
}
