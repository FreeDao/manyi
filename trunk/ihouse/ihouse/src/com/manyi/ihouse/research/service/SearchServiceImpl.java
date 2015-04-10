package com.manyi.ihouse.research.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Service;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.base.SearchPageResponse;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.UserCollection;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.Estate;
import com.manyi.ihouse.map.model.MapMarkModel;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.map.service.ComparatorHouseBaseModel;
import com.manyi.ihouse.research.model.KeywordResponse;
import com.manyi.ihouse.research.model.SearchRequest;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.HousePropertyUtils;
import com.manyi.ihouse.util.IObjectUtils;

@Service(value = "searchServiceImpl")
public class SearchServiceImpl extends BaseService implements SearchService {

	@Override
	public KeywordResponse keywordService(SearchRequest request) {
		KeywordResponse response = new KeywordResponse();
		String sql = "SELECT area FROM Estate area WHERE area.name LIKE :keyword";
		Query query = getEntityManager().createQuery(sql);
		query.setParameter("keyword", "%" + request.getKey() + "%");
		query.setMaxResults(10);
		List<Estate> areas = query.getResultList();
		List<String> keywords = new ArrayList<String>();
		if (areas != null && areas.size() > 0) {
			for (Area area : areas) {
				keywords.add(IObjectUtils.convertString(area.getName()));
			}
		}
		response.setKeywords(keywords);
		return response;
	}

	@Override
	public KeywordResponse supportCityService() {
		KeywordResponse response = new KeywordResponse();
		List<String> list = new ArrayList<String>();
		list.add("北京");
		list.add("上海");
		response.setSupportCity(list);
		return response;
	}

	@Override
	public SearchPageResponse<HouseBaseModel> searchHouseList(SearchRequest request) {
		SearchPageResponse<HouseBaseModel> pageResponse = new SearchPageResponse<HouseBaseModel>();
		// room数量
		int r1 = 0, r2 = 0, r3 = 0, r4 = 0;
		int d1 = 0, d2 = 0, d3 = 0, d4 = 0;
		String city = request.getCity();
		String keyword = request.getKey();
		long low = request.getLowPrice();
		long high = request.getHighPrice();
		Date inhouse = request.getInhouse();
		// 装修类型
		if ((request.getDecorate() % 10) > 0) {
			d1 = 1;
		}
		if ((request.getDecorate() / 10 % 10) > 0) {
			d2 = 2;
		}
		if ((request.getDecorate() / 100 % 10) > 0) {
			d3 = 3;
		}
		if ((request.getDecorate() / 1000 % 10) > 0) {
			d4 = 4;
		}
		// 房间数目
		if ((request.getRoom() % 10) > 0) {
			r1 = 1;
		}
		if ((request.getRoom() / 10 % 10) > 0) {
			r2 = 2;
		}
		if ((request.getRoom() / 100 % 10) > 0) {
			r3 = 3;
		}
		if ((request.getRoom() / 1000 % 10) > 0) {
			r4 = 4;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT r.houseId,hr.rentPrice,r.bedroomSum,r.livingRoomSum,r.wcSum");
		builder.append(",r.picNum,r.decorateType,e.name,hr.publishDate,r.layers,e.areaId,e.latitude,e.longitude,r.serialCode");// r.floor,
		// if(request.getUserId() > 0){
		// builder.append(" ,iuc.house_id");
		// }
		builder.append(" FROM House r JOIN HouseResource hr on r.houseId = hr.houseId");
		builder.append(" JOIN Area e on r.estateId = e.areaId");
		builder.append(" JOIN address ad on r.estateId = ad.estateId");
		// if(request.getUserId() > 0){
		// builder.append(" LEFT JOIN iw_user_collection iuc on r.houseId = iuc.house_id");
		// }
		builder.append(" WHERE "); // hr.houseState = 1 AND online环境必须打开
		if (low > 0 || high > 0) {
			if (low > 0) {
				builder.append("(hr.rentPrice >= " + low);
			} else {
				builder.append("(");
			}
			if (low > 0 && high > 0) {
				builder.append(" AND ");
			}
			if (high > 0) {
				builder.append(" hr.rentPrice <= " + high + ") AND ");
			} else {
				builder.append(") AND ");
			}
		}
		if (city.contains("上海")) {
			builder.append(" (SUBSTRING(r.serialCode,1,10) = '0000100001') ");
		} else {
			builder.append(" (SUBSTRING(r.serialCode,1,10) = '0000100002') ");
		}
		if (r1 > 0 || r2 > 2 || r3 > 0 || r4 > 0) {
			builder.append(" AND (");
			if (r1 != 0) {
				builder.append(" r.bedroomSum=" + r1);
			}
			if (r1 != 0 && r2 != 0) {
				builder.append(" OR ");
			}
			if (r2 != 0) {
				builder.append(" r.bedroomSum=" + r2);
			}
			if (r2 != 0 && r3 != 0) {
				builder.append(" OR ");
			}
			if (r3 != 0) {
				builder.append(" r.bedroomSum=" + r3);
			}
			if (r3 != 0 && r4 != 0) {
				builder.append(" OR ");
			}
			if (r4 != 0) {
				builder.append(" r.bedroomSum=" + r4);
			}
			builder.append(") ");
		}
		if (d1 > 0 || d2 > 2 || d3 > 0 || d4 > 0) {
			builder.append(" AND (");
			if (d1 != 0) {
				builder.append(" r.decorateType=" + d1);
			}
			if (d1 != 0 && d2 != 0) {
				builder.append(" OR ");
			}
			if (d2 != 0) {
				builder.append(" r.decorateType=" + d2);
			}
			if (d2 != 0 && d3 != 0) {
				builder.append(" OR ");
			}
			if (d3 != 0) {
				builder.append(" r.decorateType=" + d3);
			}
			if (d3 != 0 && d4 != 0) {
				builder.append(" OR ");
			}
			if (d4 != 0) {
				builder.append(" r.decorateType=" + d4);
			}
			builder.append(")");
		}
		if (keyword != null && keyword.length() > 0) {
			builder.append(" AND (e.name LIKE '%" + keyword + "%'");
			builder.append(" OR ad.address LIKE '% " + keyword + " %')");
		}

		switch (request.getSequence()) {
		case 1:
			builder.append(" ORDER BY hr.publishDate DESC");
			break;
		case 2:
			builder.append(" ORDER BY hr.rentprice ASC");
			break;
		case 3:
			builder.append(" ORDER BY hr.rentprice DESC");
			break;
		case 4:
			builder.append(" ORDER BY hr.publishDate DESC");
			break;
		}

		builder.append(" limit " + request.getOffset() + "," + request.getPageSize() + "");
		Query query = getEntityManager().createNativeQuery(builder.toString());
		// query.setFirstResult(request.getOffset());
		// query.setMaxResults(request.getRows());
		List<Object[]> list = query.getResultList();
		List<HouseBaseModel> houseList = new ArrayList<HouseBaseModel>();
		if (list != null && list.size() > 0) {
			for (Object[] obj : list) {
				HouseBaseModel response = new HouseBaseModel();
				int i = 0;
				response.setHouseId(IObjectUtils.convertInt(obj[i++]));
				response.setRentPrice(IObjectUtils.convertString(obj[i++]));
				response.setBedroomSum(IObjectUtils.convertInt(obj[i++]));
				response.setLivingRoomSum(IObjectUtils.convertInt(obj[i++]));
				response.setWcSum(IObjectUtils.convertInt(obj[i++]));
				response.setPicNum(IObjectUtils.convertInt(obj[i++]));
				response.setDecorateType(HousePropertyUtils.convertDecorateType(IObjectUtils.convertInt(obj[i++])));
				response.setEstateName(IObjectUtils.convertString(obj[i++]));
				Object dateObj = obj[i++];
				response.setPubDate(dateObj == null ? "" : DateUtil.fattrDate((Date) dateObj));
				response.setFloorType(HousePropertyUtils.convertFloorType(IObjectUtils.convertInt(obj[i++])));
				response.setEstateId(IObjectUtils.convertInt(obj[i++]));
				response.setLat(IObjectUtils.convertDouble(obj[i++]));
				response.setLon(IObjectUtils.convertDouble(obj[i++]));
				response.setAreaName(houseBlock(IObjectUtils.convertString(obj[i++])));
				response.setDistance(0);
				houseList.add(response);
			}
		}

		// 查询用户收藏列表
		List<UserCollection> collections = new ArrayList<UserCollection>();
		if (request.getUserId() > 0) {
			// 查询用户
			User u = getEntityManager().find(User.class, request.getUserId());
			if (u != null) {
				String collectionSQL = "SELECT clc FROM UserCollection clc WHERE clc.user = ?1";
				query = getEntityManager().createQuery(collectionSQL);
				query.setParameter(1, u);
				collections = query.getResultList();
			}
		}
		// 房源列表和收藏列表比较，如果相对应的house已收藏，HouseBaseModel.setCollectionState = 0
		if (collections.size() > 0 && list.size() > 0) {
			for (UserCollection collection : collections) {
				for (HouseBaseModel model : houseList) {
					if (model.getHouseId() == collection.getHouseId()) {
						model.setCollectionState(1);
					}
				}
			}
		}
		if (request.getSequence() == 4) {
			ComparatorHouseBaseModel comparator = new ComparatorHouseBaseModel();
			Collections.sort(list, comparator);
		}

		pageResponse.setRows(houseList);
		pageResponse.setTotal(houseList.size());
		pageResponse.setRecommendList(null);
		if (houseList.size() < 10) {
			/** 推荐列表 **/
		}
		return pageResponse;
	}

	private String houseBlock(String serialCode) {
		// 查询区域
		String sql = "SELECT a FROM Area a WHERE a.serialCode in(:codes)";// SUBSTR("+ serialCode +",1,15)";
		Query query = getEntityManager().createQuery(sql);
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
			for (Area area : areas) {
				areaBuilder.append(area.getName() + " ");
			}
			// 区域板块
			return areaBuilder.toString();
		} else {
			return "";
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.manyi.ihouse.research.service.SearchService#searchHouseMapMark(com.manyi.ihouse.research.model.SearchRequest)
	 */
	@Override
	public MapResponse searchHouseMapMark(SearchRequest request) {
		// int houseSum = 0;
		MapResponse response = new MapResponse();
		String keyword = request.getKey() == null ? "" : request.getKey();
		String sql = "select a.areaid , a.name, count(1) as acount,a.latitude,a.longitude"
				+ " from house h join area a on h.estateId = a.areaId" + " join houseresource hr on h.houseId = hr.houseId"
				+ " join address ad on a.areaId = ad.estateId" + " where hr.`status` in (1,3)";
		if (keyword.length() > 0) {
			sql += " and a.name LIKE '%" + keyword + "%' or ad.address LIKE '%" + keyword + "%'";
		}
		sql += " group by a.areaId ";// limit "+ request.getOffset()+","+request.getPageSize()+"
		Query query = getEntityManager().createNativeQuery(sql);
		List<Object[]> objList = query.getResultList();
		List<MapMarkModel> markList = new ArrayList<MapMarkModel>();
		if (objList != null && objList.size() > 0) {
			for (Object[] obj : objList) {
				MapMarkModel mark = new MapMarkModel();
				int i = 0;
				mark.setAreaId(IObjectUtils.convertInt(obj[i++]));
				mark.setAreaName(IObjectUtils.convertString(obj[i++]));
				mark.setHouseNum(IObjectUtils.convertInt(obj[i++]));
				mark.setLat(IObjectUtils.convertDouble(obj[i++]));
				mark.setLon(IObjectUtils.convertDouble(obj[i++]));
				mark.setGrade(3);// 地图级别：小区
				markList.add(mark);
			}
		}
		response.setMarkList(markList);
		response.setAllnum(markList.size());
		return response;
	}
}
