package com.manyi.ihouse.map.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.manyi.ihouse.base.BaseService;
import com.manyi.ihouse.base.PageResponse;
import com.manyi.ihouse.entity.SubwayLine;
import com.manyi.ihouse.entity.SubwayPoint;
import com.manyi.ihouse.entity.SubwayStation;
import com.manyi.ihouse.entity.User;
import com.manyi.ihouse.entity.UserCollection;
import com.manyi.ihouse.entity.hims.Area;
import com.manyi.ihouse.entity.hims.Estate;
import com.manyi.ihouse.entity.hims.House;
import com.manyi.ihouse.entity.hims.HouseImageFile;
import com.manyi.ihouse.map.model.MapMarkModel;
import com.manyi.ihouse.map.model.MapRequest;
import com.manyi.ihouse.map.model.MapResponse;
import com.manyi.ihouse.map.model.SubwayLinePoint;
import com.manyi.ihouse.map.model.SubwayLineResponse;
import com.manyi.ihouse.map.model.SubwayResponse;
import com.manyi.ihouse.user.model.HouseBaseModel;
import com.manyi.ihouse.util.DateUtil;
import com.manyi.ihouse.util.Geohash;
import com.manyi.ihouse.util.HousePropertyUtils;
import com.manyi.ihouse.util.IObjectUtils;
import com.manyi.ihouse.util.MapUtils;

@Service(value = "mapService")
public class MapServiceImpl extends BaseService implements MapService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	private int houseSum = 0;

	@Override
	public SubwayResponse stationsCollectionById(MapRequest request) {
		int sum = 0;
		int cityCode = Integer.parseInt(request.getCity());
		int lineNo = request.getSubwayNo();
		SubwayResponse response = new SubwayResponse();
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		String sql = "SELECT sl FROM SubwayLine sl WHERE lineCityCode=?1 AND sl.subwayLineId=?2 AND sl.lineStatus=1";
		Query query = getEntityManager().createQuery(sql);
		query.setParameter(1, cityCode);
		query.setParameter(2, lineNo);
		List<SubwayLine> line = query.getResultList();
		models = getEstateListBySubwayLine(line);
		response.setMarkList(models);
		// 计算总房源
		for (MapMarkModel m : models) {
			sum += m.getHouseNum();
		}

		response.setCenterLat(IObjectUtils.convertDouble(line.get(0).getLineLat()));
		response.setCenterLon(IObjectUtils.convertDouble(line.get(0).getLineLon()));
		// 分叉地铁
		// StringBuilder builder2 = new StringBuilder();
		// builder2.append("SELECT a.satationLon * 100000000,  a.stationLat * 100000000,a.geoHash");
		// builder2.append(" FROM subwayline a JOIN subwaystation b ON a.id = b.subwayid");
		// builder2.append(" WHERE a.cityCode = "+ cityCode
		// +" AND a.parentId = "+ lineNo +" AND a.lineState = 1");
		// Query childLine =
		// getEntityManager().createNativeQuery(builder2.toString());
		// objs.clear();
		// models.clear();
		// objs = query.getResultList();
		// models = queryModel(objs);
		// response.setChildMarkList(models);
		// 计算总房源
		// for(MapMarkModel m :models){
		// sum += m.getHouseNum();
		// }
		// SubwayLine line = getEntityManager().find(SubwayLine.class, lineNo);
		// response.setCenterPoint("(" + line.getLon() + "," + line.getLat() +
		// ")");
		return response;

	}

	@Override
	public MapResponse ArroundEstateMarkBySubwayLine(MapRequest request) {
		String sql = "SELECT sl FROM SubwayLine sl WHERE lineCityCode=?1 AND sl.subwayLineId=?2 AND sl.lineStatus=1";
		Query query = getEntityManager().createQuery(sql);
		query.setParameter(1, Integer.parseInt(request.getCity()));
		query.setParameter(2, request.getSubwayNo());
		List<SubwayLine> line = query.getResultList();
		List<MapMarkModel> tempMarkList = queryArroundEstateBySubwayLine(line);
		List<MapMarkModel> markList = new ArrayList<MapMarkModel>();
		String geo = Geohash.encode(request.getLat(), request.getLon()).substring(0, 6);
		int sum = 0;
		if (tempMarkList == null || tempMarkList.size() == 0) {
			MapResponse response = new MapResponse();
			response.setMarkList(null);
			response.setAllnum(0);
			response.setMessage("数据为空");
			return response;
		}
		for (MapMarkModel model : tempMarkList) {
			if (model.getGeoHash() != null && model.getGeoHash().length() > 6 && geo.equals(model.getGeoHash().substring(0, 6))) {
				markList.add(model);
				sum += model.getHouseNum();
			}
		}
		MapResponse response = new MapResponse();
		response.setMarkList(markList);
		response.setAllnum(sum);
		return response;
	}

	@Override
	public PageResponse subwayLinePoint(MapRequest request) {
		PageResponse<SubwayLinePoint> page = new PageResponse<SubwayLinePoint>();
		List<SubwayLinePoint> list = new ArrayList<SubwayLinePoint>();
		// String sql = "SELECT sl FROM SubwayLine sl WHERE lineCityCode = ?1 AND subwayLineId = ?2";
		SubwayLine line = getEntityManager().find(SubwayLine.class, request.getSubwayNo());
		if (line != null && line.getSubwayLineId() > 0) {
			String sql = "SELECT sp FROM SubwayPoint sp WHERE sp.subwayLine = ?1 ORDER BY sp.pointOrder ASC";
			Query query = getEntityManager().createQuery(sql);
			query.setParameter(1, line);
			List<SubwayPoint> points = query.getResultList();
			if (points != null && points.size() > 0) {
				for (SubwayPoint point : points) {
					SubwayLinePoint response = new SubwayLinePoint();
					response.setPointDesc(point.getPointDesc());
					response.setPointGeoHash(point.getPointGeoHash());
					response.setPointLat(IObjectUtils.convertDouble(point.getPointLat()));
					response.setPointLon(IObjectUtils.convertDouble(point.getPointLon()));
					response.setPointStatus(point.getPointStatus());
					response.setSequence(point.getPointOrder());
					list.add(response);
				}
			}
		}
		page.setRows(list);
		return page;
	}

	@Override
	public PageResponse<SubwayLineResponse> subwayLineListByCity(MapRequest request) {
		PageResponse<SubwayLineResponse> page = new PageResponse<SubwayLineResponse>();
		List<SubwayLineResponse> list = new ArrayList<SubwayLineResponse>();
		String sql = "SELECT sl FROM SubwayLine sl WHERE lineCityCode = ?";
		Query query = getEntityManager().createQuery(sql);
		query.setParameter(1, Integer.parseInt(request.getCity()));
		query.setFirstResult(0);
		query.setMaxResults(9);
		List<SubwayLine> lines = query.getResultList();
		if (lines != null && lines.size() > 0) {
			for (SubwayLine line : lines) {
				SubwayLineResponse response = new SubwayLineResponse();
				response.setLineCityCode(line.getLineCityCode());
				response.setLineDesc(line.getLineDesc());
				response.setLineGeoHash(line.getLineGeoHash());
				response.setLineLat(IObjectUtils.convertDouble(line.getLineLat()));
				response.setLineLon(IObjectUtils.convertDouble(line.getLineLon()));
				response.setLineName(line.getLineName());
				response.setLineStatus(line.getLineStatus());
				response.setSubwayLineId(IObjectUtils.convertInt(line.getSubwayLineId()));
				response.setLevel(IObjectUtils.convertInt(line.getLevel()));
				list.add(response);
			}
		}
		page.setRows(list);
		return page;
	}

	@Override
	public MapResponse houseMarkByLevel(MapRequest request) {
		// TODO Auto-generated method stub
		MapResponse response = new MapResponse();
		int level = request.getLevel();
		switch (level) {
		case 12:
		case 13:// 区域
		case 14:
			// 具体实现的业务逻辑
			response.setMarkList(listByProvince(request.getCity()));
			response.setAllnum(houseSum);
			response.setType(1);
			break;
		case 15:
		case 16:// 板块
		case 17:// 板块
			String blockGeoHash = Geohash.encode(request.getLat(), request.getLon()).substring(0, 5);
			response.setMarkList(listByArea(request.getCity(), blockGeoHash));
			response.setAllnum(houseSum);
			response.setType(2);
			break;
		case 18:// 小区
		case 19:// 小区
			String zoneGeoHash = Geohash.encode(request.getLat(), request.getLon()).substring(0, 6);
			response.setMarkList(listByZone(zoneGeoHash));
			response.setAllnum(houseSum);
			response.setType(3);
			break;
		default:
			break;
		}
		return response;
	}

	@Override
	public PageResponse<MapResponse> houseMarkByZone(MapRequest request) {
		log.info("获得用户点击地图上Mark的参数", ReflectionToStringBuilder.toString(request));

		String sql = "";
		return null;
	}

	@Override
	public PageResponse<HouseBaseModel> houseSummaryList(MapRequest request) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		String serialCode = "";
		Query query = null;
		Estate estate = this.getEntityManager().find(Estate.class, request.getAreaId());
		serialCode = estate.getSerialCode() != null ? estate.getSerialCode() : "";
		builder.append("SELECT r.houseId,hr.rentPrice as rentPrice");
		builder.append(",r.serialCode,e.`name`,r.bedroomSum,r.livingRoomSum,r.wcSum");// r.floor,
		builder.append(",r.picNum,r.decorateType,hr.publishDate,r.layers,e.areaId,e.longitude,e.latitude");
		builder.append(" FROM House r JOIN HouseResource hr on r.houseId = hr.houseId");
		builder.append(" JOIN Area e on r.estateId = e.areaId");
		builder.append(" WHERE hr.status in (1,3)");
		// 列表排序 1:时间 2:价格低-高3：价格高-低 4：距离
		if (serialCode.length() > 0) {
			builder.append(" AND r.serialCode LIKE '" + serialCode + "%'");
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
		builder.append(" limit " + request.getOffset() + " , " + request.getPageSize() + "");
		query = this.getEntityManager().createNativeQuery(builder.toString());
		PageResponse<HouseBaseModel> page = new PageResponse<HouseBaseModel>();
		List<HouseBaseModel> list = baseModels(query.getResultList(), request);
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
				for (HouseBaseModel model : list) {
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
		page.setRows(list);
		page.setTotal(countSummaryList(serialCode));
		return page;
	}

	private int countSummaryList(String serialCode) {
		String sql = "SELECT h FROM House h,HouseResource hr WHERE h.houseId=hr.houseId AND hr.status in (1,3) AND h.serialCode LIKE '"
				+ serialCode + "%'";
		Query query = getEntityManager().createQuery(sql);
		// query.setParameter(1, serialCode);
		List<House> list = query.getResultList();
		if (list != null && list.size() > 0) {
			return list.size();
		} else {
			return 0;
		}
	}

	@Override
	public PageResponse<HouseBaseModel> houseSummaryListByLevel(MapRequest request) {
		// TODO Auto-generated method stub
		String serialCode = "";
		if (request.getCity().contains("北京")) {
			serialCode = "0000100002";
		} else {
			serialCode = "0000100001";
		}
		StringBuilder builder = new StringBuilder();
		Query query = null;
		builder.append("SELECT r.houseId,hr.rentPrice as rentPrice");
		builder.append(",r.serialCode,e.`name`,r.bedroomSum,r.livingRoomSum,r.wcSum");// r.floor,
		builder.append(",r.picNum,r.decorateType,hr.publishDate,r.layers,e.areaId,e.longitude,e.latitude");
		builder.append(" FROM House r JOIN HouseResource hr on r.houseId = hr.houseId");
		builder.append(" JOIN Area e on r.estateId = e.areaId");
		builder.append(" WHERE hr.status in (1,3)");
		if (request.getLevel() < 15) {
			builder.append(" AND r.serialCode LIKE '" + serialCode + "%'");
		} else if (request.getLevel() > 14 && request.getLevel() < 18) {
			builder.append(" AND e.axisHash LIKE '" + Geohash.encode(request.getLat(), request.getLon()).substring(0, 5) + "'");
		} else {
			builder.append(" AND e.axisHash LIKE '" + Geohash.encode(request.getLat(), request.getLon()).substring(0, 6) + "'");
		}

		// 列表排序 1:时间 2:价格低-高3：价格高-低 4：距离
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
		builder.append(" limit " + request.getOffset() + " , " + request.getPageSize() + "");
		query = this.getEntityManager().createNativeQuery(builder.toString());
		PageResponse<HouseBaseModel> page = new PageResponse<HouseBaseModel>();
		List<HouseBaseModel> list = baseModels(query.getResultList(), request);

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
				for (HouseBaseModel model : list) {
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
		page.setRows(list);
		page.setTotal(list.size());
		return page;
	}

	private List<HouseBaseModel> baseModels(List<Object[]> objs, MapRequest request) {
		String serialCode = "";
		double lon = 0, lat = 0;
		List<HouseBaseModel> list = new ArrayList<HouseBaseModel>();
		if (objs != null && objs.size() > 0) {
			for (Object[] row : objs) {
				int col = 0;
				long houseId = 0;
				HouseBaseModel response = new HouseBaseModel();
				houseId = IObjectUtils.convertLong(row[col++]);
				response.setHouseId(houseId);
				response.setRentPrice(IObjectUtils.convertString(row[col++]));
				serialCode = IObjectUtils.convertString(row[col++]);
				response.setEstateName(IObjectUtils.convertString(row[col++]));
				response.setBedroomSum(IObjectUtils.convertInt(row[col++]));
				response.setLivingRoomSum(IObjectUtils.convertInt(row[col++]));
				response.setWcSum(IObjectUtils.convertInt(row[col++]));
				response.setPicNum(IObjectUtils.convertInt(row[col++]));
				response.setDecorateType(HousePropertyUtils.convertDecorateType(IObjectUtils.convertInt(row[col++])));
				Object obj = row[col++];
				if (obj != null) {
					response.setPubDate(DateUtil.pubDateFormat((Date) obj));
				} else {
					response.setPubDate("");
				}
				response.setFloorType(HousePropertyUtils.convertFloorType(IObjectUtils.convertInt(row[col++])));
				response.setEstateId(IObjectUtils.convertInt(row[col++]));
				lon = IObjectUtils.convertDouble(row[col++]);
				lat = IObjectUtils.convertDouble(row[col++]);
				String sql2 = "SELECT hif FROM HouseImageFile hif WHERE hif.houseId = ?1";
				Query query = getEntityManager().createQuery(sql2);
				query.setParameter(1, houseId);
				List<HouseImageFile> imgList = query.getResultList();
				Map<String, String> hdUrls = new HashMap<String, String>();
				if (imgList != null && imgList.size() > 0) {
					String[] urls = new String[imgList.size()];
					int i = 0;
					for (HouseImageFile img : imgList) {
						String url = "http://house-images.oss-cn-hangzhou.aliyuncs.com/" + img.getThumbnailKey();
						String url2 = "http://house-images.oss-cn-hangzhou.aliyuncs.com/" + img.getImgKey();
						urls[i] = url;
						hdUrls.put(url2, img.getDescription());
						i++;
					}
					response.setPicUrls(urls);
				} else {
					response.setPicUrls(null);
				}
				response.setHdUrls(hdUrls);
				response.setAreaName(getBlockString(serialCode));
				if (request.getSequence() == 4 && request.getLon() != 0 && request.getLat() != 0 && lon != 0 && lat != 0) {
					response.setDistance(MapUtils.GetDistance(request.getLon(), request.getLat(), lon, lat));
				} else {
					response.setDistance(0);
				}
				list.add(response);
			}
		}
		return list;
	}

	/**
	 * 返回板块区域 ”黄埔 南京东路“
	 * 
	 * @param serialCode
	 * @return
	 */
	public String getBlockString(String serialCode) {
		String sql = "SELECT a FROM Area a WHERE a.serialCode in(:codes)";// SUBSTR("+ serialCode +",1,15)";
		Query query = getEntityManager().createQuery(sql);
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
			return areaBuilder.toString();
		} else {
			return "";
		}
	}

	/**
	 * @param stations
	 * @return
	 */
	private List<MapMarkModel> getEstateListBySubwayLine(List<SubwayLine> subwayLine) {
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		if (subwayLine != null && subwayLine.size() > 0) {
			for (SubwayStation row : subwayLine.get(0).getSubwayStations()) {
				MapMarkModel model = new MapMarkModel();
				model.setGrade(3);
				model.setLon(IObjectUtils.convertDouble(row.getStationLon()));
				model.setLat(IObjectUtils.convertDouble(row.getStationLat()));
				String geo = IObjectUtils.convertString(row.getStationGeoHash());
				if (geo.length() > 0) {
					model.setGeoHash(geo.substring(0, 5));
					// 查询站点附近的房源数量
					String sql = "SELECT h FROM House h,HouseResource hr,Estate e WHERE hr.status in (1,3) AND h.houseId = hr.houseId  AND h.estateId = e.areaId AND e.axisHash LIKE ?1";
					Query query = getEntityManager().createQuery(sql);
					query.setParameter("1", "'" + model.getGeoHash() + "%'");
					List<House> houses = query.getResultList();
					if (houses != null && houses.size() > 0) {
						// 得到房源数量
						model.setHouseNum(houses.size());
					} else {
						model.setHouseNum(0);
					}
				}
				models.add(model);
			}
		}
		return models;
	}

	/**
	 * 地铁线上附近所有的房子，以小区分类
	 * 
	 * @param stations
	 * @return
	 */
	private List<MapMarkModel> queryArroundEstateBySubwayLine(List<SubwayLine> line) {
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		if (line != null && line.size() > 0) {
			for (SubwayStation row : line.get(0).getSubwayStations()) {
				String geo = IObjectUtils.convertString(row.getStationGeoHash());
				if (geo.length() > 0) {
					MapMarkModel model = new MapMarkModel();
					// 查询站点附近的房源数量
					String sql = "SELECT a.areaId,a.name,a.longitude,a.latitude,count(1),a.axisHash "
							+ " FROM house h,houseresource hr,area a where hr.status in (1,3) AND h.houseid = hr.houseId AND h.estateId = a.areaId"
							+ " AND a.axisHash LIKE ?1 GROUP BY a.areaId";
					Query query = getEntityManager().createNativeQuery(sql);
					query.setParameter(1, "'" + geo.substring(0, 6) + "%'");
					List<Object[]> list = query.getResultList();
					if(list != null && list.size() > 0){
						for (Object[] col : list) {
							int i = 0;
							model.setAreaId(IObjectUtils.convertInt(col[i++]));
							model.setAreaName(IObjectUtils.convertString(col[i++]));
							model.setLon(IObjectUtils.convertDouble(col[i++]));
							model.setLat(IObjectUtils.convertDouble(col[i++]));
							model.setHouseNum(IObjectUtils.convertInt(col[i++]));
							model.setGeoHash(IObjectUtils.convertString(col[i++]));
						}
						models.add(model);
					}
				}
			}
		}
		return models;
	}

	/**
	 * 省级房源列表 每个区域的房源数量
	 * 
	 * @return
	 */
	private List<MapMarkModel> listByProvince(String areaName) {
		houseSum = 0;
		StringBuilder builder = new StringBuilder();
		// 区域code
		String serialCode = "";
		if (areaName.contains("北京")) {
			serialCode = "0000100002";
		} else {
			serialCode = "0000100001";
		}
		builder.append("SELECT aaa.areaId,aaa.name,cnt,aaa.longitude,aaa.latitude FROM area aaa JOIN");
		builder.append("(");
		builder.append(" SELECT aa.areaId,aa.parentId,aa.name,bb.cnt,aa.longitude,aa.latitude FROM area aa JOIN ");
		builder.append("(");
		builder.append(" SELECT a.parentId,COUNT(1) as cnt FROM house h ");
		builder.append(" JOIN area a ON h.estateId = a.areaId ");
		builder.append(" JOIN houseresource hr ON h.houseId = hr.houseId");
		builder.append(" WHERE hr.status in (1,3) AND h.serialCode LIKE '" + serialCode + "%' ");// h
		builder.append(" GROUP BY a.parentId");
		builder.append(" ) bb ON aa.areaId = bb.parentId ");
		builder.append(" ) ccc ON aaa.areaId = ccc.parentId");
		builder.append(" GROUP BY aaa.areaid");
		Query query = getEntityManager().createNativeQuery(builder.toString());
		List<Object[]> objs = new ArrayList<Object[]>();
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		objs = query.getResultList();
		if (objs != null && objs.size() > 0) {
			int i = 0;
			for (Object[] row : objs) {
				int col = 0;
				MapMarkModel model = new MapMarkModel();
				model.setAreaId(IObjectUtils.convertInt(row[col++]));
				model.setAreaName(IObjectUtils.convertString(row[col++]));
				model.setGrade(1);
				model.setHouseNum(IObjectUtils.convertInt(row[col++]));
				model.setLon(IObjectUtils.convertDouble(row[col++]));
				model.setLat(IObjectUtils.convertDouble(row[col++]));
				// 计算房源总数
				houseSum += model.getHouseNum();
				models.add(model);
			}
			i++;
		}

		return models;
	}

	/**
	 * 板块房源列表 每个区域的房源数量
	 * 
	 * @return
	 */
	private List<MapMarkModel> listByArea(String area, String geoHash) {
		houseSum = 0;
		StringBuilder builder = new StringBuilder();
		// 区域code
		builder.append("SELECT aa.areaId,aa.name,bb.cnt,aa.longitude,aa.latitude FROM area aa JOIN ");
		builder.append(" (");
		builder.append(" SELECT a.parentId,COUNT(1) as cnt FROM house h ");
		builder.append(" JOIN area a ON h.estateId = a.areaId ");
		builder.append(" JOIN houseresource hr ON h.houseId = hr.houseId");
		builder.append(" WHERE hr.status in (1,3)");
		builder.append(" GROUP BY a.parentId ");// limit 1,30
		builder.append(" ) bb ON aa.areaId = bb.parentId ");
		builder.append("WHERE aa.axisHash LIKE '" + geoHash + "%'");
		Query query = getEntityManager().createNativeQuery(builder.toString());
		List<Object[]> objs = new ArrayList<Object[]>();
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		objs = query.getResultList();
		if (objs != null && objs.size() > 0) {
			int i = 0;
			for (Object[] row : objs) {
				int col = 0;
				MapMarkModel model = new MapMarkModel();
				model.setAreaId(IObjectUtils.convertInt(row[col++]));
				model.setAreaName(IObjectUtils.convertString(row[col++]));
				model.setGrade(2);
				model.setHouseNum(IObjectUtils.convertInt(row[col++]));
				model.setLon(IObjectUtils.convertDouble(row[col++]));
				model.setLat(IObjectUtils.convertDouble(row[col++]));
				// 计算房源总数
				houseSum += model.getHouseNum();
				models.add(model);
			}
			i++;
		}

		return models;
	}

	/**
	 * 附近房源列表 每个区域的房源数量
	 * 
	 * @return
	 * @param geoHash
	 *            经纬度换算的geoHash值
	 */
	private List<MapMarkModel> listByZone(String geoHash) {
		houseSum = 0;
		StringBuilder builder = new StringBuilder();
		// 区域code
		builder.append("SELECT a.areaid,a.name, count(1) AS cnt,a.longitude,a.latitude FROM house h");
		builder.append(" JOIN area a ON h.estateId = a.areaId");
		builder.append(" JOIN houseresource hr ON h.houseId = hr.houseId");
		builder.append(" WHERE hr.status in (1,3) AND a.axisHash LIKE '" + geoHash + "%'");
		builder.append(" GROUP BY a.areaid");
		Query query = getEntityManager().createNativeQuery(builder.toString());
		List<Object[]> objs = new ArrayList<Object[]>();
		List<MapMarkModel> models = new ArrayList<MapMarkModel>();
		objs = query.getResultList();
		if (objs != null && objs.size() > 0) {
			int i = 0;
			for (Object[] row : objs) {
				int col = 0;
				MapMarkModel model = new MapMarkModel();
				model.setAreaId(IObjectUtils.convertInt(row[col++]));
				model.setAreaName(IObjectUtils.convertString(row[col++]));
				model.setGrade(3);
				model.setHouseNum(IObjectUtils.convertInt(row[col++]));
				model.setLon(IObjectUtils.convertDouble(row[col++]));
				model.setLat(IObjectUtils.convertDouble(row[col++]));
				// 计算房源总数
				houseSum += model.getHouseNum();
				models.add(model);
			}
			i++;
		}

		return models;
	}

}
