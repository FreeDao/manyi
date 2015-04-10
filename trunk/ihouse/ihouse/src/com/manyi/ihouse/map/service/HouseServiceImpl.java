package com.manyi.ihouse.map.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.Response;
import com.manyi.ihouse.entity.Appointment;
import com.manyi.ihouse.entity.SeekHouse;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.UserCollection;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.Estate;
import com.manyi.ihouse.entity.hims.EstateImage;
import com.manyi.ihouse.entity.hims.HouseImageFile;
import com.manyi.ihouse.entity.hims.HouseSupportingMeasures;
import com.manyi.ihouse.map.model.HouseDetailResponse;
import com.manyi.ihouse.map.model.HouseRequest;
import com.manyi.ihouse.map.model.LookHouseRequest;
import com.manyi.ihouse.map.model.ZoneDetailResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.HousePropertyUtils;
import com.manyi.ihouse.util.IObjectUtils;
import com.manyi.ihouse.util.MapUtils;
import com.manyi.ihouse.util.OSSObjectUtil;

@Service(value = "houseInfoService")
public class HouseServiceImpl extends BaseService implements HouseService {

	public String combine(boolean b) {
		if (b)
			return "1";
		else
			return "0";
	}

	@Override
	public HouseDetailResponse houseDetailService(HouseRequest request) {
		HouseDetailResponse response = new HouseDetailResponse();
		String serialCode = "";
		String zoneAddress = "";
		StringBuilder property = new StringBuilder();
		StringBuilder builder = new StringBuilder();
		String hsmSQL = "SELECT hsm FROM HouseSupportingMeasures hsm WHERE hsm.houseId = ?1 AND hsm.enable = 1";
		Query query = getEntityManager().createQuery(hsmSQL);
		query.setParameter(1, request.getHouseId());

		List<HouseSupportingMeasures> hsmList = query.getResultList();
		if (hsmList != null && hsmList.size() > 0) {
			// 格式:电视|冰箱|洗衣机|空调|热水器|床|沙发|浴缸|暖气|中央空调|衣帽间|车位|院落|露台|阁楼|
			HouseSupportingMeasures hsm = hsmList.get(0);
			property.append(hsm.isHasTV() ? "1" : "0");
			property.append(hsm.isHasRefrigerator() ? "1" : "0");
			property.append(hsm.isHasWashingMachine() ? "1" : "0");
			property.append(hsm.isHasAirConditioner() ? "1" : "0");
			property.append(hsm.isHasWaterHeater() ? "1" : "0");
			property.append(hsm.isHasBed() ? "1" : "0");
			property.append(hsm.isHasSofa() ? "1" : "0");
			property.append(hsm.isHasBathtub() ? "1" : "0");
			property.append(hsm.isHasCentralHeating() ? "1" : "0");
			property.append(hsm.isHasCentralAirConditioning() ? "1" : "0");
			property.append(hsm.isHasCloakroom() ? "1" : "0");
			property.append(hsm.isHasReservedParking() ? "1" : "0");
			property.append(hsm.isHasCourtyard() ? "1" : "0");
			property.append(hsm.isHasGazebo() ? "1" : "0");
			property.append(hsm.isHasPenthouse() ? "1" : "0");
		} else {
			property.append("000000000000000");
		}
		response.setHouseConfig(property.toString());
		builder.append("SELECT aa.rent,aa.hroom,bb.`name`,bb.adress,aa.hfloor,aa.direction,");
		builder.append("aa.decorateType,aa.lon,aa.lat,aa.finishDate,aa.totalBuilding,aa.totalTruckSpace");
		builder.append(",bb.areaId,bb.cnt,SYSDATE() as inhouse,bb.serialCode,aa.publishDate FROM");
		builder.append(" (SELECT h.houseId, hr.rentPrice AS rent");
		builder.append(",CONCAT(h.bedroomSum,'房',h.livingRoomSum,'厅',h.wcSum,'卫') AS hroom");
		builder.append(",CONCAT(a.`name`,h.building,'号楼',h.room,'室') AS adress");
		builder.append(",h.spaceArea,h.floor AS hfloor");
		builder.append(",h.estateId,h.houseDirectionId AS direction,h.decorateType,a.longitude AS lon,a.latitude AS lat");
		builder.append(",date(a.finishDate) as finishDate,a.totalBuilding,a.totalTruckSpace,date(hr.publishDate) as publishDate");
		builder.append(" FROM house h JOIN houseresource hr ON h.houseId = hr.houseId");
		builder.append(" JOIN area a ON h.estateId = a.areaId");
		builder.append(" WHERE h.houseId=" + request.getHouseId() + ") aa");
		builder.append(" JOIN");
		builder.append(" (SELECT b.areaId,b.`name`,COUNT(1) AS CNT,c.address AS adress,b.serialCode");
		builder.append(" FROM house a JOIN area b ON a.estateId = b.areaId");
		builder.append(" JOIN address c ON a.estateId = c.estateId");
		builder.append(" WHERE b.areaId=" + request.getEsateId() + ") bb ON aa.estateId=bb.areaId");
		query = getEntityManager().createNativeQuery(builder.toString());
		List<Object[]> objs = new ArrayList<Object[]>();
		objs = query.getResultList();
		if (objs != null && objs.size() > 0) {
			int i = 0;
			Object[] obj = objs.get(0);
			response.setHouseId(request.getHouseId());
			response.setRentPrice(IObjectUtils.floatConvertString(obj[i++]));
			response.setHouseRoom(IObjectUtils.convertString(obj[i++]));
			response.setZoneName(IObjectUtils.convertString(obj[i++]));
			zoneAddress = IObjectUtils.convertString(obj[i++]);
			response.setFloorType(HousePropertyUtils.convertFloorType(IObjectUtils.convertInt(obj[i++])));
			response.setForward(IObjectUtils.convertString(obj[i++]));
			response.setDecorateType(HousePropertyUtils.convertDecorateType(IObjectUtils.convertInt(obj[i++])));
			response.setLon(IObjectUtils.convertDouble(obj[i++]));
			response.setLat(IObjectUtils.convertDouble(obj[i++]));
			Object object = obj[i++];
			if (object != null) {
				response.setFinishDate(DateUtil.formatDate2(object));
			} else {
				response.setFinishDate("");
			}
			response.setOwners(IObjectUtils.convertString(obj[i++]));
			response.setParkings(IObjectUtils.convertString(obj[i++]));
			response.setAreaId(IObjectUtils.convertInt(obj[i++]));
			response.setHouseLet(IObjectUtils.convertString(obj[i++]));
			Object object2 = obj[i++];
			if (object2 != null) {
				response.setInHouseDate(DateUtil.formatDate2(object2));
			} else {
				response.setInHouseDate("");
			}
			serialCode = IObjectUtils.convertString(obj[i++]);
			Object object3 = obj[i++];
			if (object3 != null) {
				response.setPublishDate(DateUtil.formatDate2(object3));
			} else {
				response.setPublishDate("");
			}
		} else {
			response.setHouseId(request.getHouseId());
			response.setRentPrice("");
			response.setHouseRoom("");
			response.setZoneName("");
			response.setFloorType("");
			response.setForward("");
			response.setDecorateType("");
			response.setLon(0);
			response.setLat(0);
			response.setHouseConfig("000000000000000");
			response.setFinishDate("");
			response.setOwners("");
			response.setParkings("");
			response.setAreaId(request.getEsateId());
			response.setHouseLet("");
			response.setInHouseDate("");
			response.setPublishDate("");
		}
		// 查询区域
		String sql = "SELECT a FROM Area a WHERE a.serialCode in(:codes)";// SUBSTR("+ serialCode +",1,15)";
		query = getEntityManager().createQuery(sql);
		ArrayList<String> codes = new ArrayList<String>();
		// 判断长度是否大于20
		if (serialCode.length() >= 20) {
			codes.add(serialCode.substring(0, 15));
			codes.add(serialCode.substring(0, 20));
		} else if (serialCode.length() >= 15 && serialCode.length() < 20) {
			codes.add(serialCode.substring(0, 15));
		}
		List<Area> areas = new ArrayList<Area>();
		if (codes.size() > 0) {
			query.setParameter("codes", codes);
			areas = query.getResultList();
		}

		StringBuilder areaBuilder = new StringBuilder();
		if (areas != null && areas.size() > 0) {
			int n = 1;
			for (Area area : areas) {
				if (n == 1) {
					// 小区地址
					zoneAddress = area.getName() + zoneAddress;
				}
				areaBuilder.append(area.getName() + " ");
			}
			// 区域板块
			response.setBlock(areaBuilder.toString());
		} else {
			response.setBlock("");
		}
		String sql2 = "SELECT hif FROM HouseImageFile hif WHERE hif.houseId = ?1";
		// 查询图片地址
		query = getEntityManager().createQuery(sql2);
		query.setParameter(1, request.getHouseId());
		List<HouseImageFile> imgList = query.getResultList();
		Map<String, String> urls = new HashMap<String, String>();
		Map<String, String> hdUrls = new HashMap<String, String>();
		if (imgList != null && imgList.size() > 0) {
			for (HouseImageFile file : imgList) {
				String url = "http://house-images.oss-cn-hangzhou.aliyuncs.com/" + file.getThumbnailKey();
				String url2 = "http://house-images.oss-cn-hangzhou.aliyuncs.com/" + file.getImgKey();
				urls.put(url, file.getDescription());
				hdUrls.put(url2, file.getDescription());
			}
		}
		if (request.getUserId() > 0) {

			// 查询用户
			User u = getEntityManager().find(User.class, request.getUserId());
			if (u != null) {
				String collectionSQL = "SELECT uc FROM UserCollection uc WHERE user=?1";
				query = getEntityManager().createQuery(collectionSQL);
				query.setParameter(1, u);
				List<UserCollection> collections = query.getResultList();
				// 房源列表和收藏列表比较，如果相对应的house已收藏，HouseBaseModel.setCollectionState = 0
				if (collections.size() > 0) {
					for (UserCollection collection : collections) {
						if (request.getHouseId() == collection.getHouseId()) {
							response.setFavorite(true);
						}
					}
				}
			}
		}
		response.setZoneAddress(zoneAddress);
		response.setUrls(urls);
		response.setHdUrls(hdUrls);
		response.setHouseType("老公房");
		if (response.getLon() > 0 && response.getLat() > 0 && !StringUtils.isEmpty(request.getLon())
				&& !StringUtils.isEmpty(request.getLat())) {
			double d = MapUtils.GetDistance(response.getLon(), response.getLat(), Double.parseDouble(request.getLon()),
					Double.parseDouble(request.getLat()));
			String dis = String.valueOf(d);
			if (dis.contains(".")) {
				int len = dis.lastIndexOf('.');
				response.setDistance(String.valueOf(d).substring(0, len));
			}
		} else {
			response.setDistance("0");
		}
		// 判断房源是否已经在看房单
		response.setExistLookHouse(existLookHouse(request));
		return response;
	}

	public Response lookHouseService(LookHouseRequest request) {
		Response response = new Response();
		long userId = request.getUserId();

		User user = null;
		if (userId != 0) {
			user = this.getEntityManager().find(User.class, userId);
		}
		if (user == null) {
			response.setMessage("用户不存在");
			return response;
		}
		HouseRequest houseRequest = new HouseRequest();
		houseRequest.setHouseId(request.getHouseId());
		houseRequest.setUserId(request.getUserId());
		int exist = existLookHouse(houseRequest);
		if (exist == 1) {
			response.setMessage("此房源已经添加到看房单，请勿重新添加");
			return response;
		}
		// 插入"我要看房"表
		// Appointment a = new Appointment();
		// SeekHouse seek = new SeekHouse();
		// seek.setUser(user);
		// seek.setCreate_time(new Date());
		// seek.setUpdate_time(new Date());
		// seek.setHouseId(request.getHouseId());
		// seek.setState(0);
		// seek.setAppointment(a);
		// this.getEntityManager().persist(seek);
		String insertSql = "INSERT INTO iw_seekhouse(user_id,house_id,appointment_id,agent_id,state,recommend_source) VALUES (" + userId
				+ "," + request.getHouseId() + ",0,0,0,1)";
		Query query = getEntityManager().createNativeQuery(insertSql);
		query.executeUpdate();
		// 维护用户看房表
		String sql = "UPDATE iw_user_statistics SET inchart_num = inchart_num + 1 WHERE user_id = ?1";
		query = getEntityManager().createNativeQuery(sql);
		query.setParameter(1, request.getUserId());
		query.executeUpdate();

		response.setMessage("0");
		return response;
	}

	@Override
	public ZoneDetailResponse zoneDetailService(HouseRequest request) {
		ZoneDetailResponse response = new ZoneDetailResponse();
		Estate estate = getEntityManager().find(Estate.class, request.getEsateId());
		if (estate == null) {
			response.setMessage("小区ID查找的小区不存在");
			return response;
		}
		Query query = null;
		String serialCode = estate.getSerialCode() == null ? "" : estate.getSerialCode();
		// 查询板块区域
		String sql = "SELECT a FROM Area a WHERE a.serialCode in(:codes)";// SUBSTR("+ serialCode +",1,15)";
		query = getEntityManager().createQuery(sql);
		ArrayList<String> codes = new ArrayList<String>();
		if (serialCode.length() >= 20) {
			// 判断长度是否大于20
			codes.add(serialCode.substring(0, 15));
			codes.add(serialCode.substring(0, 20));
		} else if (serialCode.length() >= 15 && serialCode.length() < 20) {
			codes.add(serialCode.substring(0, 15));
		}
		List<Area> areas = new ArrayList<Area>();
		if (codes.size() > 0) {
			query.setParameter("codes", codes);
			areas = query.getResultList();
		}
		StringBuilder areaBuilder = new StringBuilder();
		if (areas != null && areas.size() > 0) {
			for (Area area : areas) {
				areaBuilder.append(area.getName() + " ");
			}
			response.setBlock(areaBuilder.toString());
		} else {
			response.setBlock("- -");
		}
		BigDecimal d = new BigDecimal("0.00");
		BigDecimal d2 = new BigDecimal(100);
		response.setBuildingDate(estate.getFinishDate() == null ? 0 : estate.getFinishDate().getTime());
		Object developer = estate.getPropertyDevelopers();
		response.setCompany(developer != null && developer.toString().length() > 0 ? developer.toString() : "- -");
		response.setDistance(0);
		BigDecimal fee = estate.getStrataFee() == null ? new BigDecimal("0.00") : estate.getStrataFee();
		response.setFee(d.compareTo(fee) == 0 ? "- -" : fee.toString());
		BigDecimal green = estate.getLandscapingRatio() == null ? new BigDecimal("0.00") : estate.getStrataFee();
		response.setGreenRate(d.compareTo(green) == 0 ? "- -" : green.multiply(d2).toString() + "%");
		String imgSql = "SELECT ei FROM EstateImage ei WHERE ei.estateId = ?1 AND ei.status = ?2";
		query = getEntityManager().createQuery(imgSql);
		query.setParameter(1, request.getEsateId());
		query.setParameter(2, 1);
		List<EstateImage> imgList = query.getResultList();
		Map<String, String> urls = new HashMap<String, String>();
		Map<String, String> hdUrls = new HashMap<String, String>();
		if (imgList != null && imgList.size() > 0) {
			for (EstateImage ei : imgList) {
				if (ei.getThumbnailKey() != null) {
					urls.put("http://house-images.oss-cn-hangzhou.aliyuncs.com/" + ei.getThumbnailKey(), ei.getDescription());
				}
				if (ei.getImgKey() != null) {
					hdUrls.put("http://house-images.oss-cn-hangzhou.aliyuncs.com/" + ei.getImgKey(), ei.getDescription());
				}
			}
		}
		response.setHdUrls(hdUrls);
		// response.setLat(0);
		// response.setLon(0);
		Object park = estate.getTotalTruckSpace();
		response.setParks(park == null && Integer.parseInt(park + "") == 0 ? "- -" : Integer.parseInt(park + "") + "");
		response.setRentRate("- -");
		BigDecimal rj = estate.getPlotRatio() == null ? new BigDecimal("0.00") : estate.getPlotRatio();
		response.setRjRate(d.compareTo(rj) == 0 ? "- -" : rj.multiply(d2).toString() + "%");
		response.setUrls(urls);
		response.setWyCompany("- -");
		response.setWyType("- -");
		response.setZoneAddress("- -");
		response.setZoneName(IObjectUtils.convertString(estate.getName(), "- -"));
		return response;
	}

	/**
	 * 看房单是否存在
	 * 
	 * @param request
	 * @return
	 */
	private int existLookHouse(HouseRequest request) {
		int i = 0;
		if (request.getUserId() > 0) {
			User u = getEntityManager().find(User.class, request.getUserId());
			if (u != null) {
				String lookSQL = "SELECT sh FROM SeekHouse sh WHERE sh.houseId=?1 AND sh.user = ?2";
				Query query = getEntityManager().createQuery(lookSQL);
				query.setParameter(1, (long) request.getHouseId());
				query.setParameter(2, u);
				List<SeekHouse> list = query.getResultList();
				if (list != null && list.size() > 0) {
					i = 1;
				}
			}
		}
		return i;
	}
}
