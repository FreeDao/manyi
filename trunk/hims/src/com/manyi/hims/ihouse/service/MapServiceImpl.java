package com.manyi.hims.ihouse.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.ihouse.model.HouseSummaryResponse;
import com.manyi.hims.ihouse.model.LatLng;
import com.manyi.hims.ihouse.model.MapMarkModel;
import com.manyi.hims.ihouse.model.MapRequest;
import com.manyi.hims.ihouse.model.MapResponse;

@Service(value = "mapService")
@Scope(value="singleton")
public class MapServiceImpl extends BaseService implements MapService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public MapResponse houseMarkByLevel(MapRequest reqest) {
		// TODO Auto-generated method stub
		MapResponse response = new MapResponse();
		int level = reqest.getLevel();
		LatLng p = null;//reqest.getPoint();
		switch (level) {
		case 13://区域
			//具体实现的业务逻辑
			response.setMarkList(listByArea(p));
			break;
		case 14:
		case 15://板块
			response.setMarkList(listByBlock(p));
			break;
		case 16://小区
		case 17://小区
			response.setMarkList(listByZone(p));
			break;
		default:
			break;
		}
		return response;
	}

	@Override
	public PageResponse<MapResponse> houseMarkByZone(MapRequest request) {
		log.info("获得用户点击地图上Mark的参数",ReflectionToStringBuilder.toString(request));
		
		String sql = "";
		return null;
	}
	
//	@Override
//	public SubwayResponse houseListBySubway(SubwayRequest request){
//		
//		if(request.getLineNo() == 2){
//			SubwayResponse response = new SubwayResponse();
//			response.setHouseCount(7348);
//			response.setMarkList(listBySubway(new LatLng()));
////			response.setStationMap(null);
//			return response;
//		}
//		return null;
//	}
	
	@Override
	public PageResponse<HouseSummaryResponse> houseSummaryList(MapRequest request) {
		// TODO Auto-generated method stub
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT r.houseId,hr.rentPrice,r.bedroomSum,r.livingRoomSum,r.balconySum,r.wcSum");
		builder.append(",r.picNum,r.decorateType,e.name,hr.publishDate,r.layers");//r.floor,
		builder.append(" FROM Residence r JOIN HouseResource hr on r.houseId = hr.houseId");
		builder.append(" JOIN Estate e on r.estateId = e.areaId");
		builder.append(" WHERE hr.houseState = 1");
		builder.append(" AND r.houseId in (" + request.getHouseId() + ")");
		Query query = this.getEntityManager().createNativeQuery(builder.toString());
		PageResponse<HouseSummaryResponse> page = new PageResponse<HouseSummaryResponse>();
		List<HouseSummaryResponse> list = new ArrayList<HouseSummaryResponse>();
		query.setFirstResult((request.getPage() - 1) * request.getRows());//起始下标
		query.setMaxResults(request.getRows());
		page.setCurrentPage(request.getPage());
		page.setPageSize(request.getRows());
		page.setTotal(999);
		List<Object[]> objs = query.getResultList();
		if(objs != null && objs.size() > 0){
			for(Object[] row : objs){
				int col = 0;
				HouseSummaryResponse response = new HouseSummaryResponse();
				response.setHouseId(Integer.parseInt(row[col++] + ""));
				response.setRentPrice(Integer.parseInt(row[col++] + "") * 10000);
				response.setBedrooms(Integer.parseInt(row[col++] + ""));
				response.setLivingromms(Integer.parseInt(row[col++] + ""));
				response.setBalconies(Integer.parseInt(row[col++] + ""));
				response.setWashingrooms(Integer.parseInt(row[col++] + ""));
				response.setPictures(Integer.parseInt(row[col++] + ""));
				response.setDecorationType(Integer.parseInt(row[col++] + ""));
				response.setZoonName(row[col++].toString());//小区名称
				response.setPublicDate(row[col++].toString());
				response.setFloorType(Integer.parseInt(row[col++] + ""));
				list.add(response);
			}
		}
		page.setRows(list);
		return page;
	}
	
	private String decorationType(int type){
		switch(type){
			case 0:
				return "毛坯";
			case 1:
				return "装修";
			default:
				return "";
		}
	}
	
	
	/**
	 * 区域房源列表
	 * @return
	 */
	private List<MapMarkModel> listByArea(LatLng p){
		List<MapMarkModel> list = new ArrayList<>();
		MapMarkModel model = new MapMarkModel();
		LatLng point = new LatLng();
		point.setLatitude(31229664);
		point.setLongitude(121455063);
		model.setAreaId(0);
		model.setAreaName("静安寺");
		model.setGrade(13);
		model.setHouseNum(15);
		model.setMarkPoint(point);
		
		MapMarkModel model2 = new MapMarkModel();
		model2.setAreaName("长宁区");
		model2.setHouseNum(65);
		LatLng point2 = new LatLng();
		point2.setLatitude(31226452);
		point2.setLongitude(121430055);
		model2.setMarkPoint(point2);
		
		MapMarkModel model3 = new MapMarkModel();
		model3.setAreaName("黄浦区");
		model3.setHouseNum(656);
		LatLng point3 = new LatLng();
		point3.setLatitude(31238062);
		point3.setLongitude(121489846);
		model3.setMarkPoint(point3);
		
		MapMarkModel model4 = new MapMarkModel();
		model4.setAreaName("闸北区");
		model4.setHouseNum(253);
		LatLng point4 = new LatLng();
		point4.setLatitude(31254363);
		point4.setLongitude(121465412);
		model4.setMarkPoint(point4);
		
		MapMarkModel model5 = new MapMarkModel();
		model5.setAreaName("徐汇区");
		model5.setHouseNum(599);
		LatLng point5 = new LatLng();
		point5.setLatitude(31195073);
		point5.setLongitude(121442128);
		model5.setMarkPoint(point5);
		
		list.add(model);
		list.add(model2);
		list.add(model3);
		list.add(model4);
		list.add(model5);
		return list;
	}
	
	/**
	 * 区域房源列表
	 * @return
	 */
	private List<MapMarkModel> listByBlock(LatLng p){
		List<MapMarkModel> list = new ArrayList<>();
		MapMarkModel model = new MapMarkModel();
		LatLng point = new LatLng();
		point.setLatitude(31248868);//121.431923,31.248868
		point.setLongitude(121431923);
		model.setAreaId(0);
		model.setAreaName("玫瑰园");
		model.setGrade(13);
		model.setHouseNum(445);
		model.setMarkPoint(point);
		
		MapMarkModel model2 = new MapMarkModel();
		model2.setAreaName("长宁区");
		model2.setHouseNum(234);
		LatLng point2 = new LatLng();
		point2.setLatitude(31238124);//121.44529,31.238124
		point2.setLongitude(12144529);
		model2.setMarkPoint(point2);
		
		MapMarkModel model3 = new MapMarkModel();
		model3.setAreaName("黄浦区");
		model3.setHouseNum(533);
		LatLng point3 = new LatLng();
		point3.setLatitude(31207737);//121.429767,31.207737
		point3.setLongitude(121429767);
		model3.setMarkPoint(point3);
		
		MapMarkModel model4 = new MapMarkModel();
		model4.setAreaName("闸北区");
		model4.setHouseNum(253);
		LatLng point4 = new LatLng();
		point4.setLatitude(31218362);//121.475042,31.218362
		point4.setLongitude(121475042);
		model4.setMarkPoint(point4);
		
		MapMarkModel model5 = new MapMarkModel();
		model5.setAreaName("徐汇区");
		model5.setHouseNum(765);
		LatLng point5 = new LatLng();
		point5.setLatitude(31209838);//121.43451,31.209838
		point5.setLongitude(12143451);
		model5.setMarkPoint(point5);
		
		list.add(model);
		list.add(model2);
		list.add(model3);
		list.add(model4);
		list.add(model5);
		return list;
	}
	
	/**
	 * 区域房源列表
	 * @return
	 */
	private List<MapMarkModel> listByZone(LatLng p){
		List<MapMarkModel> list = new ArrayList<>();
		MapMarkModel model = new MapMarkModel();
		LatLng point = new LatLng();
		point.setLatitude(31230467);//121.459052,31.230467
		point.setLongitude(121459052);
		model.setAreaId(0);
		model.setAreaName("玫瑰园");
		model.setGrade(13);
		model.setHouseNum(655);
		model.setMarkPoint(point);
		
		MapMarkModel model2 = new MapMarkModel();
		model2.setAreaName("长宁区");
		model2.setHouseNum(234);
		LatLng point2 = new LatLng();
		point2.setLatitude(31225619);//121.466023,31.225619
		point2.setLongitude(121466023);
		model2.setMarkPoint(point2);
		
		MapMarkModel model3 = new MapMarkModel();
		model3.setAreaName("黄浦区");
		model3.setHouseNum(433);
		LatLng point3 = new LatLng();
		point3.setLatitude(31234419);//121.452368,31.234419
		point3.setLongitude(121452368);
		model3.setMarkPoint(point3);
		
		MapMarkModel model4 = new MapMarkModel();
		model4.setAreaName("闸北区");
		model4.setHouseNum(553);
		LatLng point4 = new LatLng();
		point4.setLatitude(31231702);//121.460274,31.231702
		point4.setLongitude(121460274);
		model4.setMarkPoint(point4);
		
		MapMarkModel model5 = new MapMarkModel();
		model5.setAreaName("徐汇区");
		model5.setHouseNum(235);
		LatLng point5 = new LatLng();
		point5.setLatitude(3123445);//121.459842,31.23445
		point5.setLongitude(121459842);
		model5.setMarkPoint(point5);
		
		list.add(model);
		list.add(model2);
		list.add(model3);
		list.add(model4);
		list.add(model5);
		return list;
	}
	
	
	/**
	 * 区域房源列表
	 * @return
	 */
	private List<MapMarkModel> listBySubway(LatLng p){
		List<MapMarkModel> list = new ArrayList<>();
		MapMarkModel model = new MapMarkModel();
		LatLng point = new LatLng();
		point.setLatitude(31226391);//121.438966,31.226391
		point.setLongitude(121438966);
		model.setAreaId(0);
		model.setAreaName("江苏路");
		model.setGrade(13);
		model.setHouseNum(151);
		model.setMarkPoint(point);//,31.229602
		
		MapMarkModel model2 = new MapMarkModel();
		model2.setAreaName("静安寺站");
		model2.setHouseNum(62);
		LatLng point2 = new LatLng();
		point2.setLatitude(31229602);
		point2.setLongitude(121452656);
		model2.setMarkPoint(point2);
		
		MapMarkModel model3 = new MapMarkModel();
		model3.setAreaName("南京西路站");
		model3.setHouseNum(656);
		LatLng point3 = new LatLng();//121.466705,31.236241
		point3.setLatitude(31236241);
		point3.setLongitude(121466705);
		model3.setMarkPoint(point3);
		
		MapMarkModel model4 = new MapMarkModel();
		model4.setAreaName("人民广场");
		model4.setHouseNum(253);
		LatLng point4 = new LatLng();
		point4.setLatitude(3123902);//121.481689,31.23902
		point4.setLongitude(121481689);
		model4.setMarkPoint(point4);
		
		MapMarkModel model5 = new MapMarkModel();
		model5.setAreaName("南京东路");
		model5.setHouseNum(599);
		LatLng point5 = new LatLng();
		point5.setLatitude(31243095);//121.490277,31.243095
		point5.setLongitude(121490277);
		model5.setMarkPoint(point5);
		
		list.add(model);
		list.add(model2);
		list.add(model3);
		list.add(model4);
		list.add(model5);
		return list;
	}

	
	

}
