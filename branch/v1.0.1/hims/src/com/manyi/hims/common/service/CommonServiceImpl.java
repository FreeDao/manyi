package com.manyi.hims.common.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.common.AutoUpdateResponse;
import com.manyi.hims.common.CommonConst;
import com.manyi.hims.common.RecordResponse;
import com.manyi.hims.common.RecordSourceHost;
import com.manyi.hims.common.controller.CommonRestController.EstateResponse;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.Address_;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.Area_;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.City_;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.EstateHistory;
import com.manyi.hims.entity.EstateHistory_;
import com.manyi.hims.entity.Estate_;
import com.manyi.hims.entity.Feedback;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceContact;
import com.manyi.hims.entity.HouseResourceContact_;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceHistory_;
import com.manyi.hims.entity.HouseResourceViewCount;
import com.manyi.hims.entity.HouseResourceViewCount_;
import com.manyi.hims.entity.HouseResource_;
import com.manyi.hims.entity.House_;
import com.manyi.hims.entity.Province;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.ResidenceResourceHistory;
import com.manyi.hims.entity.Residence_;
import com.manyi.hims.entity.Town;
import com.manyi.hims.entity.Town_;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.Version;
import com.manyi.hims.entity.Version_;
import com.manyi.hims.rent.RentConst;
import com.manyi.hims.sell.SellConst;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.CollectionUtils;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;

/**
 * @author zxc
 */
@Service(value = "commonService")
@Scope(value = "singleton")
public class CommonServiceImpl extends BaseService implements CommonService {

	static final Set<String> city = new HashSet<String>(Arrays.asList(new String[] { "上海", "北京", "天津", "重庆" }));

	@Override
	public RecordResponse loadEstateInfo(int historyId) {
		EstateHistory estate = execEqualQuerySingle(historyId, EstateHistory.class, EstateHistory_.historyId);
		Town town = execEqualQuerySingle(estate.getParentId(), Town.class, Town_.areaId);
		City city = getHouseCity(estate.getParentId());
		List<Address> address = execEqualQueryList(estate.getAreaId(), Address.class, Address_.estateId);
		RecordResponse recordResponse = new RecordResponse();

		recordResponse.setEstateId(estate.getAreaId());
		recordResponse.setEstateName(estate.getName());
		recordResponse.setAreaId(city.getAreaId());
		recordResponse.setAreaName(city.getName());
		recordResponse.setTownName(town.getName());

		recordResponse.setAddress(CollectionUtils.getProperty(address, "address"));

		recordResponse.setSourceLogTypeId(EntityUtils.SourceLogType.ADD_ESTATE.getValue());
		recordResponse.setSourceLogTypeStr(EntityUtils.SourceLogType.ADD_ESTATE.getDesc());

		recordResponse.setSourceState(estate.getStatus());
		recordResponse.setSourceStateStr(EntityUtils.StatusEnum.getByValue(estate.getStatus()).getDesc());

		recordResponse.setPublishDate(estate.getCreateTime());
		return recordResponse;
	}

	/**
	 * 获取Area实体中的SerialCode公用方法
	 */
	@Override
	public String getSerialCode4Area(Integer parentId) {
		if (parentId == null) {
			return StringUtils.EMPTY;
		}
		Area parentArea = execEqualQuerySingle(parentId, Area.class, Area_.areaId);
		if (parentArea == null) {
			logger.error("zxc--#--" + "00001");
			return "00001";
		}
		List<Area> lists = execEqualQueryList(parentId, Area.class, Area_.parentId);
		final String serialCodePar = parentArea.getSerialCode();
		StringBuffer sf = new StringBuffer(serialCodePar);
		if (lists == null || lists.size() == 0) {
			sf.append("00001");
			logger.error("zxc--#--" + sf.toString());
			return sf.toString();
		}

		Collections.sort(lists, new Comparator<Area>() {

			@Override
			public int compare(Area o1, Area o2) {

				if (StringUtils.isEmpty(o2.getSerialCode())) {
					return -1;
				}
				if (StringUtils.isEmpty(o1.getSerialCode())) {
					return 1;
				}

				String serialCode1 = subSerialCode(serialCodePar, o1.getSerialCode());
				String serialCode2 = subSerialCode(serialCodePar, o2.getSerialCode());

				return Integer.valueOf(serialCode1) > Integer.valueOf(serialCode2) ? -1 : 1;
			}
		});
		Area area = lists.get(0);
		String areaSerialCode = subSerialCode(serialCodePar, area.getSerialCode());
		sf.append(String.format("%05d", Integer.valueOf(areaSerialCode) + 1));
		logger.error("zxc--#--" + sf.toString());
		return sf.toString();
	}

	private String subSerialCode(final String serialCodePar, String areaSerialCode) {
		int index = areaSerialCode.indexOf(serialCodePar);
		return index == 0 ? areaSerialCode.substring(index + serialCodePar.length()) : areaSerialCode;
	}

	@Override
	public boolean insertFeedback(int uid, String context) {
		Feedback feedback = new Feedback();
		feedback.setContext(context);
		feedback.setUid(uid);
		feedback.setCreateTime(new java.util.Date());
		getEntityManager().persist(feedback);
		return true;
	}

	/**
	 * 加载记录信息
	 */
	@Override
	public RecordResponse loadInfoLog(int houseId, int historyId) {
		Residence residence = getResidence(houseId);
		HouseResourceHistory houseResourceHistory = getHouseResourceHistory(historyId);
		List<HouseResourceContact> houseResourceContactList = getHouseResourceContact(historyId);
		Estate estate = getHouseSubEstate(residence.getEstateId());
		City city = getHouseCity(estate.getParentId());

		RecordResponse result = new RecordResponse();
		copy4RecordResponce(houseId, residence, houseResourceHistory, estate, city, result);

		List<RecordSourceHost> hostList = new ArrayList<RecordSourceHost>();
		if (houseResourceContactList != null && houseResourceContactList.size() > 0) {
			for (HouseResourceContact contact : houseResourceContactList) {
				hostList.add(new RecordSourceHost(contact.getHostName(), contact.getHostMobile()));
			}
		}
		result.setHosts(hostList);
		return result;
	}

	private List<HouseResourceContact> getHouseResourceContact(int historyId) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResourceContact> cq = cb.createQuery(HouseResourceContact.class);
		Root<HouseResourceContact> root = cq.from(HouseResourceContact.class);
		cq.where(cb.equal(root.get(HouseResourceContact_.historyId), historyId));
		return this.getEntityManager().createQuery(cq).getResultList();
	}

	private Estate getHouseSubEstate(int estateId) {
		return execEqualQuerySingle(estateId, Estate.class, Estate_.areaId);
	}

	private City getHouseCity(int estateId) {
		Town town = execEqualQuerySingle(estateId, Town.class, Town_.areaId);
		return execEqualQuerySingle(town.getParentId(), City.class, City_.areaId);
	}

	private HouseResourceHistory getHouseResourceHistory(int historyId) {
		return execEqualQuerySingle(historyId, HouseResourceHistory.class, HouseResourceHistory_.historyId);
	}

	private Residence getResidence(int houseId) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
		Root<Residence> root = cq.from(Residence.class);
		cq.where(cb.equal(root.get(Residence_.houseId), houseId));
		cq.orderBy(cb.desc(root.get(Residence_.updateTime)));
		return this.getEntityManager().createQuery(cq).getSingleResult();
	}

	/**
	 * 根据sourceId查询 业主信息SourceHost
	 */
	@Override
	public HouseResourceContact getSourceHost(Integer sourceLogId) {
		if (sourceLogId == null || sourceLogId == 0) {
			throw new LeoFault(RentConst.Rent_ERROR120006);
		}
		HouseResourceContact sourceHost = null;
		List<HouseResourceContact> sourceHostList = execEqualQueryList(sourceLogId, HouseResourceContact.class,
				HouseResourceContact_.houseId);
		if (sourceHostList.size() > 0) {
			sourceHost = sourceHostList.get(0);
		}
		return sourceHost == null ? new HouseResourceContact() : sourceHost;
	}

	/**
	 * 全量 到得所以的城市列表
	 * 
	 * @author zxc
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<City> getAllCityList() {
		String jpql = "from Province a";
		Query query = this.getEntityManager().createQuery(jpql);
		List<Province> lists = query.getResultList();
		jpql = "from City a";
		Query query_ = this.getEntityManager().createQuery(jpql);
		List<City> lists_ = query_.getResultList();
		List<City> citylist = new ArrayList<City>();
		Set<Integer> set = new HashSet<Integer>();
		if (lists != null && lists.size() > 0) {
			for (Province province : lists) {
				if (city.contains(province.getName())) {
					City city = new City();
					city.setName(province.getName());
					city.setCreateTime(province.getCreateTime());
					city.setParentId(province.getParentId());
					city.setAreaId(province.getAreaId());
					citylist.add(city);
					set.add(province.getAreaId());
				}
			}
		}
		for (City city : lists_) {
			if (!set.contains(city.getParentId())) {
				citylist.add(city);
			}
		}
		return citylist;
	}

	/**
	 * 通过时间查询 到得所以的城市列表
	 */
	@Override
	public List<City> getCityListByTime(Date formTime, Date toTime) {
		return null;
	}

	/**
	 * 更加parentId查询Area
	 */
	@Override
	public List<Area> findAreaByParentId(int parentId, boolean flag) {
		String jpql = "select a.areaId , a.name ,a.parentId from Area a where a.parentId = ? ";
		Query query = this.getEntityManager().createQuery(jpql);
		query.setParameter(1, parentId);
		List lists = query.getResultList();
		List<Area> arealist = new ArrayList<Area>();
		if (lists != null && lists.size() > 0) {
			for (int i = 0; i < lists.size(); i++) {
				Object[] rows = (Object[]) lists.get(i);
				Area tmp = new Area();
				tmp.setAreaId(Integer.parseInt(rows[0] + ""));
				tmp.setName(rows[1] + "");
				tmp.setParentId(Integer.parseInt(rows[2] + ""));
				arealist.add(tmp);
			}
		}
		if (parentId != 1 && flag) {
			// 是否加全部的节点
			Area area = this.getEntityManager().find(Area.class, parentId);
			getEntityManager().clear();
			if (area != null) {
				area.setName(area.getName() + "全部");
				arealist.add(0, area);
			}
		}
		return arealist;
	}

	/**
	 * @date 2014年4月29日 下午3:21:59
	 * @author Tom
	 * @description 根据父区域code查询下属区域列表
	 */
	public List<Area> findAreaBySerialCode(String serialCode) {
		// String
		// jpql="select com.manyi.hims.entity.Area(a.serialCode, a.name) from Area a where a.serialCode like '?_____' ORDER BY serialCode ";
		String sql = "select a.areaId, a.name from Area a where a.serialCode like :serialCode and a.dtype <> 'Estate' ORDER BY serialCode ";
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter("serialCode", serialCode + "_____");

		List<Object[]> lists = query.getResultList();

		List<Area> arealist = new ArrayList<Area>();
		for (Object[] obj : lists) {
			Area area = new Area();
			area.setAreaId(Integer.parseInt(obj[0].toString()));
			area.setName(obj[1].toString());
			arealist.add(area);
		}

		return arealist;
	}

	/**
	 * 是否强制更新
	 */
	@Override
	public AutoUpdateResponse automaticUpdates(String versionStr) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Version> cq_version = cb.createQuery(Version.class);
		Root<Version> root_version = cq_version.from(Version.class);
		cq_version.where(cb.and(cb.equal(root_version.get(Version_.type), 0)));
		cq_version.orderBy(cb.desc(root_version.get(Version_.deployTime)));
		List<Version> versionList = getEntityManager().createQuery(cq_version).getResultList();
		AutoUpdateResponse autoEntity = new AutoUpdateResponse();
		if (versionList != null && versionList.size() > 0) {
			Version version = versionList.get(0);
			autoEntity.setUrl(version.getUrl());
			autoEntity.setMessage(version.getMessage());
			autoEntity.setIfForced(version.isIfForced());
			autoEntity.setVersion(version.getVersion());
		}
		
		if (autoEntity.getVersion().equals(versionStr)) {
			autoEntity.setMessage("当前已是最新版本");//当前已是最新版本
		}
		/*
		 * StringBuilder sb = new StringBuilder(); if(Math.random()>0.5) { sb.append("房源宝已发布最新版本，你的当前版本已无法使用，请立即更新。"); } else {
		 * sb.append("2.0版本已发布,更新内容：\n\n"); sb.append("1. 加了好多东西\n"); sb.append("2.修了好多Bug"); } //String url =
		 * "http://127.0.0.1:8080/rest/common/automaticUpdates.rest"; autoEntity.setMessage(sb.toString());
		 * autoEntity.setUrl("http://www.fyb365.com/Fybao.apk"); autoEntity.setVersion(2.0 ); autoEntity.setForce(false);
		 */
		return autoEntity;
	}

	public boolean isLetter(String str) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[a-z0-9A-Z]+");
		java.util.regex.Matcher m = pattern.matcher(str);
		return m.matches();
	}

	/**
	 * 逐字查询小区列表(审核通过有效的小区)
	 * 
	 * @param loopPosition
	 *            匹配字段位置
	 * @param name
	 *            匹配字段名称
	 * @param beforeSize
	 *            已有集合大小
	 * @return 小区集合
	 */
	@SuppressWarnings("unchecked")
	private List<Estate> loopEstateByName(int loopPosition, String name, int beforeSize) {
		String jpql = " FROM Estate estate  WHERE  estate.name LIKE ? AND estate.status=1 ";
		Query exact_query = this.getEntityManager().createQuery(jpql);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < loopPosition; i++) {
			sb.append("_");
		}
		sb.append(name).append("%");
		exact_query.setParameter(1, sb.toString());
		List<Estate> exactList = exact_query.setFirstResult(0).setMaxResults(50 - beforeSize).getResultList();
		return exactList;
	}
	/**
	 * 去除List 重复数据
	 * 
	 */
	private List<Estate> removeDuplicateDate(List<Estate> distEsate, List<Estate> orgEsate) {
		//
		List<Estate> estates = new ArrayList<Estate>();
		List<Estate> delList = new ArrayList<Estate>();
		if(distEsate.size()==0) {
			estates.addAll(orgEsate);
		} else {
			for(Estate estate : orgEsate) {
				if(distEsate.contains(estate)) {
					delList.add(estate);
				}
			}
			estates.addAll(distEsate);
			if(delList.size()>0) {
				orgEsate.removeAll(delList);
			}
			estates.addAll(orgEsate);
		}
		return estates;
	}
	/**
	 * 通过小区名字查找 小区列表
	 */
	@Override
	public List<EstateResponse> findEstateByName(String name) {
		List<EstateResponse> respList = new ArrayList<EstateResponse>();
		if (isLetter(name)) {
			// 通过首字母查询小区
			respList = findEstateByInitials(name);
			return respList;
		}
		List<Estate> estateList = new ArrayList<Estate>();
		if (StringUtils.isBlank(name)) {
			// 小区名称不能为空
			throw new LeoFault(CommonConst.COMMON_ESTATE_9000001);
		}
		boolean flag = true;
		int beforeSize = 0;
		int index = 0;
		while (flag) {
			List<Estate> afterList = loopEstateByName(index, name, beforeSize);
			index++;
			estateList = removeDuplicateDate(estateList,afterList);
			beforeSize += afterList.size();
			//estateList.addAll(afterList);
			// 如何集合超过50 或者尝试10次后结束循环
			if (beforeSize >= 50 || index >= 10) {
				flag = false;
			}
		}

		for (Estate area : estateList) {
			EstateResponse response = new EstateResponse();
			response.setName(area.getName());
			response.setAreaId(area.getAreaId());
			// response.setSerialCode(area.getSerialCode());
			respList.add(response);
		}
		return respList;
	}

	/**
	 * 通过小区首字母查找 小区列表
	 */
	@SuppressWarnings("unchecked")
	public List<EstateResponse> findEstateByInitials(String name) {
		if (StringUtils.isBlank(name)) {
			// 小区名称不能为空
			throw new LeoFault(CommonConst.COMMON_ESTATE_9000001);
		}
		name = name.toLowerCase();
		String jpql = "FROM Estate estate WHERE estate.nameAcronym LIKE ? AND estate.status=1 ";
		Query query = this.getEntityManager().createQuery(jpql);
		query.setParameter(1, "%" + name + "%");
		List<Estate> lists = query.setFirstResult(0).setMaxResults(50).getResultList();
		List<EstateResponse> estateList = new ArrayList<EstateResponse>();
		for (Estate area : lists) {
			EstateResponse response = new EstateResponse();
			response.setName(area.getName());
			response.setAreaId(area.getAreaId());
			// response.setSerialCode(area.getSerialCode());
			estateList.add(response);
		}

		return estateList;
	}

	public int clearUserPublishCount() {
		String jpql = "update PublishCount p set p.usedCount = 0 ";
		Query query = this.getEntityManager().createQuery(jpql);
		return query.executeUpdate();
	}

	/**
	 * 查看房源详情 可查看次数
	 */
	@Override
	public int houseCount(int uid) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<HouseResourceViewCount> cq_count = cb.createQuery(HouseResourceViewCount.class);
		Root<HouseResourceViewCount> root_count = cq_count.from(HouseResourceViewCount.class);
		cq_count.where(cb.and(cb.equal(root_count.get(HouseResourceViewCount_.userId), uid))).select(root_count);
		HouseResourceViewCount publishCount = getEntityManager().createQuery(cq_count).getSingleResult();
		int count = publishCount.getSumCount() - publishCount.getPublishCount();// 天今剩余的次数
		if (count <= 0) {
			// 用光了.
			throw new LeoFault(UcConst.UC_ERROR100007);
		} else {
			publishCount.setPublishCount(publishCount.getPublishCount() + 1);
			getEntityManager().merge(publishCount);
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	private void copy4RecordResponce(int houseId, Residence residence, HouseResourceHistory houseResourceHistory, Estate estate, City city,
			RecordResponse result) {
		result.setAreaId(city.getAreaId());
		result.setAreaName(city.getName());
		result.setBedroomSum(((ResidenceResourceHistory)houseResourceHistory).getBedroomSum());
		result.setBuilding(residence.getBuilding());
		result.setEstateId(residence.getEstateId());
		result.setEstateName(estate.getName());
		result.setHouseId(houseId);
		result.setLivingRoomSum(((ResidenceResourceHistory)houseResourceHistory).getLivingRoomSum());
		BigDecimal price = houseResourceHistory.getHouseState() == 1 ? houseResourceHistory.getRentPrice() : houseResourceHistory
				.getSellPrice();
		result.setPrice(price == null ? new BigDecimal(0) : price);
		result.setPublishDate(houseResourceHistory.getPublishDate());
		result.setRoom(residence.getRoom());

		String sourceLogTypeStr = null;
		int sourceLogTypeId = 0;
		if (houseResourceHistory.getActionType() == 1) {
			if (houseResourceHistory.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()) {
				String jqpl = "from ResidenceResourceHistory hr where hr.actionType = 1 and hr.userId =? and ( hr.status in (1,2,3) and hr.checkNum = 0 ) order by hr.createTime desc ";
				Query query = this.getEntityManager().createQuery(jqpl);
				query.setParameter(1, houseResourceHistory.getUserId());
				List<ResidenceResourceHistory> hsitorys = query.setFirstResult(0).setMaxResults(2).getResultList();
				if (hsitorys != null && hsitorys.size() > 1) {
					ResidenceResourceHistory history = hsitorys.get(1);
					sourceLogTypeId = history.getHouseState() == 1 ? 2 : 1;
				}
			} else {
				sourceLogTypeId = houseResourceHistory.getHouseState();
			}
			sourceLogTypeStr = EntityUtils.SourceLogType.getByValue(sourceLogTypeId).getDesc();

		} else if (houseResourceHistory.getActionType() == 2) {
			sourceLogTypeStr = EntityUtils.ActionTypeEnum.CHANGE.getDesc();
			sourceLogTypeId = 3;
		} else if (houseResourceHistory.getActionType() == 3) {
			sourceLogTypeStr = EntityUtils.ActionTypeEnum.REPORT.getDesc();
			sourceLogTypeId = 4;
		} else if (houseResourceHistory.getActionType() == 6) {
			sourceLogTypeStr = EntityUtils.ActionTypeEnum.ADD_ESTATE.getDesc();
			sourceLogTypeId = 5;
		}

		List<Address> address = execEqualQueryList(residence.getEstateId(), Address.class, Address_.estateId);
		result.setAddress(CollectionUtils.getProperty(address, "address"));
		Town town = execEqualQuerySingle(estate.getParentId(), Town.class, Town_.areaId);
		result.setTownName(town.getName());
		result.setSourceLogTypeId(sourceLogTypeId);
		result.setSourceLogTypeStr(sourceLogTypeStr);

		result.setSourceState(houseResourceHistory.getStatus());
		String sourceStateStr = EntityUtils.StatusEnum.getByValue(houseResourceHistory.getStatus()).getDesc();
		result.setSourceStateStr(sourceStateStr);
		result.setSpaceArea(houseResourceHistory.getSpaceArea() == null ? new BigDecimal(0) : houseResourceHistory.getSpaceArea());
		result.setWcSum(((ResidenceResourceHistory)houseResourceHistory).getWcSum());
		result.setRemark(houseResourceHistory.getRemark());
		result.setStateReason(houseResourceHistory.getStateReason());
		result.setStateReasonStr(EntityUtils.HouseStateResonEnum.getByValue(houseResourceHistory.getStateReason()).getDesc());

		int changeHouseType = 0;
		if (houseResourceHistory.getStateReason() == 1 || houseResourceHistory.getStateReason() == 2) {
			changeHouseType = 1;
		} else if (houseResourceHistory.getStateReason() == 4 || houseResourceHistory.getStateReason() == 3) {
			changeHouseType = 2;
		} else if (houseResourceHistory.getStateReason() == 5 || houseResourceHistory.getStateReason() == 6) {
			changeHouseType = 3;
		}
		result.setChangeHouseType(changeHouseType);
		String reportRemark = StringUtils.EMPTY;
		if (houseResourceHistory.getStateReason() == 7) {
			reportRemark = "该房源未出租/出售";
		} else if (houseResourceHistory.getStateReason() == 8) {
			reportRemark = "该房源地址不存在";
		} else if (houseResourceHistory.getStateReason() == 9) {
			reportRemark = houseResourceHistory.getRemark();
		}
		result.setReportRemark(reportRemark);
	}

	@Override
	public SellInfoResponse chenkHoustIsSell(int estateId, String building, String room, int houseType) {

		if (houseType == 0) {
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
		if (building.length() > 1) {
			String str = building.substring(0, 1);
			if ("0".equals(str)) {
				throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100082);
			}
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
		/* 检查小区 id是否存在 */
		CriteriaQuery<Estate> cq_estate = cb.createQuery(Estate.class);
		Root<Estate> root_estate = cq_estate.from(Estate.class);
		cq_estate.where(cb.and(cb.and(cb.equal(root_estate.get(Estate_.areaId), estateId)))).select(root_estate);
		List<Estate> estateList = getEntityManager().createQuery(cq_estate).getResultList();
		if (estateList == null || estateList.size() <= 0) {
			throw new LeoFault(CommonConst.HOUSE_PUBLISH_1100041);// 该小区不存在,请重新搜索
		}
		// Estate sube = this.getEntityManager().find(Estate.class, estateId);
		Estate sube = estateList.get(0);

		/* 先跟据传过来的小区id查询是否有这套房子,如果有就进在查询是否在出售状态，如果没有就新增房源 */
		CriteriaQuery<House> cq_houst = cb.createQuery(House.class);
		Root<House> root_houst = cq_houst.from(House.class);
		cq_houst.where(cb.and(cb.equal(root_houst.get(House_.estateId), estateId)),
				cb.and(cb.equal(root_houst.get(House_.building), building)), cb.and(cb.equal(root_houst.get(House_.room), room))).select(
				root_houst);
		List<House> houseList = getEntityManager().createQuery(cq_houst).getResultList();
		House house = null;
		if (houseList == null || houseList.size() <= 0) {

			// 获取 estate的 serialCode
			Estate tmpEstate = this.getEntityManager().find(Estate.class, estateId);
			String serialCode = "";
			if (tmpEstate != null) {
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
			if (sellList != null && sellList.size() > 0) {
				HouseResource houseResource = sellList.get(0);
				// 发布出租
				if (houseType == 1) {
					// 在租和在租在售的不能发布
					if (houseResource.getStatus() == StatusEnum.ING.getValue()) {
						if (houseResource.getActionType() > 0) {
							if ((houseResource.getHouseState() == HouseStateEnum.RENT.getValue() || houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue())
									&& houseResource.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
								// 该房源已发布出租
								throw new LeoFault(RentConst.RENT_ERROR120001);
							}
							if (houseResource.getHouseState() == HouseStateEnum.SELL.getValue() && houseResource.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
								// 在出售审核中
								throw new LeoFault(RentConst.Rent_ERROR120007);
							}
							if (houseResource.getActionType() == ActionTypeEnum.CHANGE.getValue()) {
								// 已有改盘审核中
								throw new LeoFault(UcConst.UC_ERROR100014);
							}
							if (houseResource.getActionType() == ActionTypeEnum.REPORT.getValue()) {
								// 已有举报审核中
								throw new LeoFault(UcConst.UC_ERROR100015);
							}
							if (houseResource.getActionType() == ActionTypeEnum.LOOP.getValue() || houseResource.getActionType() == ActionTypeEnum.RAMDOM.getValue()) {
								throw new LeoFault(RentConst.RENT_ERROR120001);
							}
						}
					}
					/**
					 * 审核失败直接发
					 */
					if (houseResource.getStatus() == StatusEnum.FAILD.getValue()) {
						return new SellInfoResponse(houseResource.getHouseId());
					}
					if(houseResource.getStatus() == StatusEnum.SUCCESS.getValue()){
						/**
						 * 改盘审核成功并且在租在售的不能发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.CHANGE.getValue()){
							if(houseResource.getHouseState() == HouseStateEnum.RENT.getValue() ||
									houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue()){
								throw new LeoFault(RentConst.RENT_ERROR120001);
							}else{
								return new SellInfoResponse(houseResource.getHouseId());
							}
						}
						/**
						 * 举报审核成功直接发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.REPORT.getValue()){
							return new SellInfoResponse(houseResource.getHouseId());
						}
						/**
						 * 发布出租成功和发布租售成功不能发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.PUBLISH.getValue()){
							if(houseResource.getHouseState() == HouseStateEnum.RENT.getValue() ||
									houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue()){
								throw new LeoFault(RentConst.RENT_ERROR120001);
							}else{
								return new SellInfoResponse(houseResource.getHouseId());
							}
						}
						/**
						 * 轮询审核成功直接发
						 */
						if(houseResource.getActionType() == ActionTypeEnum.LOOP.getValue()){
							return new SellInfoResponse(houseResource.getHouseId());
						}
//						else{
//							return new SellInfoResponse(houseResource.getHouseId());
//						}
						
					}
					// else{
					// 如果这套房子在售，改为在租在售
					// if(houseResource.getHouseState()==2){
					// houseResource.setHouseState(3);
					// getEntityManager().persist(houseResource);
					// }
					return new SellInfoResponse(houseResource.getHouseId());
					// }
				}
				// 发布出售
				if (houseType == 2) {
					// 在租和在租在售的不能发布
					if (houseResource.getStatus() == StatusEnum.ING.getValue()) {
						if (houseResource.getActionType() > 0) {
							if ((houseResource.getHouseState() == HouseStateEnum.SELL.getValue()|| houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue())
									&& houseResource.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
								// 该房源已发布出售
								throw new LeoFault(SellConst.SELL_ERROR110001);
							}
							if (houseResource.getHouseState() == HouseStateEnum.RENT.getValue() && houseResource.getActionType() == ActionTypeEnum.PUBLISH.getValue()) {
								// 在出租审核中
								throw new LeoFault(RentConst.Rent_ERROR120008);
							}
							if (houseResource.getActionType() == ActionTypeEnum.CHANGE.getValue()) {
								// 已有改盘审核中
								throw new LeoFault(UcConst.UC_ERROR100014);
							}
							if (houseResource.getActionType() == ActionTypeEnum.REPORT.getValue()) {
								// 已有举报审核中
								throw new LeoFault(UcConst.UC_ERROR100015);
							}
							if (houseResource.getActionType() == ActionTypeEnum.LOOP.getValue() || houseResource.getActionType() == ActionTypeEnum.RAMDOM.getValue()) {
								throw new LeoFault(SellConst.SELL_ERROR110001);
							}
						}
					}
					/**
					 * 审核失败直接发
					 */
					if (houseResource.getStatus() == StatusEnum.FAILD.getValue()) {
						return new SellInfoResponse(houseResource.getHouseId());
					}
					if(houseResource.getStatus() == StatusEnum.SUCCESS.getValue()){
						/**
						 * 改盘审核成功并且在租在售的不能发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.CHANGE.getValue()){
							if(houseResource.getHouseState() == HouseStateEnum.SELL.getValue() ||
									houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue()){
								throw new LeoFault(SellConst.SELL_ERROR110001);
							}else{
								return new SellInfoResponse(houseResource.getHouseId());
							}
						}
						/**
						 * 举报审核成功直接发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.REPORT.getValue()){
							return new SellInfoResponse(houseResource.getHouseId());
						}
						/**
						 * 发布出租成功和发布租售成功不能发
						 */
						if(houseResource.getActionType()==ActionTypeEnum.PUBLISH.getValue()){
							if(houseResource.getHouseState() == HouseStateEnum.SELL.getValue() ||
									houseResource.getHouseState() == HouseStateEnum.RENTANDSELL.getValue()){
								throw new LeoFault(SellConst.SELL_ERROR110001);
							}else{
								return new SellInfoResponse(houseResource.getHouseId());
							}
						}
						/**
						 * 轮询审核成功直接发
						 */
						if(houseResource.getActionType() == ActionTypeEnum.LOOP.getValue()){
							return new SellInfoResponse(houseResource.getHouseId());
						}
						
//						if(houseResource.getActionType()==ActionTypeEnum.PUBLISH.getValue()){
//							throw new LeoFault(SellConst.SELL_ERROR110001);
//						}else{
//							return new SellInfoResponse(houseResource.getHouseId());
//						}
					}
					
					// else{
					// 如果这套房子在租，改为在租在售
					// if(houseResource.getHouseState()==1){
					// houseResource.setHouseState(3);
					// getEntityManager().persist(houseResource);
					// }
					return new SellInfoResponse(houseResource.getHouseId());
					// }
				}
			}
		}
		return new SellInfoResponse(house.getHouseId());
	}

	/**
	 * 当审核通过和失败后 app端我的发布记录+1
	 */
	@Override
	public void updateUserCreateLogCount(int uid) {
		User user = getEntityManager().find(User.class, uid);
		if (user != null) {
			user.setCreateLogCount(user.getCreateLogCount() + 1);
		}

	}
}
