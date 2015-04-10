package com.manyi.hims.building.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.Response;
import com.manyi.hims.building.model.BuildingReq;
import com.manyi.hims.building.model.BuildingRes;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.aiwu.Building;
import com.manyi.hims.entity.aiwu.BuildingImage;
import com.manyi.hims.estate.EstateUtil;
import com.manyi.hims.estate.model.ChangeImgJsonModel;
import com.manyi.hims.estate.model.ChangeJsonModel;
import com.manyi.hims.util.EntityUtils;

@Service(value = "buildingService")
@Scope(value = "singleton")
public class BuildingServiceImpl extends BaseService implements BuildingService {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 编辑栋座 (包括 图片 , 子划分, 别名,坐标 等等信息)
	 */
	@Override
	public Response editBuilding(BuildingRes req) {
		if(req.getId() ==0){
			return new Response(1,"楼栋ID为空!");
		}
		
		List<ChangeJsonModel> aliasNames = EstateUtil.jsonStrToList(req.getAliasName(),ChangeJsonModel.class);
		List<ChangeImgJsonModel> imgs = EstateUtil.jsonStrToList(req.getImgkeys(),ChangeImgJsonModel.class);
		
		//修改栋座
		Building bui= this.getEntityManager().find(Building.class,req.getId());
		bui.setLatitude(req.getLatitude());
		bui.setLongitude(req.getLongitude());
		bui.setTotalLayers(req.getTotalLayers());
		bui.setFinishDate(req.getFinishDate());
		
		//别名
		if(aliasNames != null && aliasNames.size()>0){
			StringBuilder sb = new StringBuilder("[");
			for (int j = 0; j < aliasNames.size(); j++) {
				ChangeJsonModel m = aliasNames.get(j);
				if(!"".equals(m.getName())){
					if(j != 0){
						sb.append(",");
					}
					sb.append("\""+m.getName()+"\"");
				}
			}
			sb.append("]");
			bui.setAliasName(sb.toString());
		}
		//图片
		if(imgs != null && imgs.size()>0){
			for (int j = 0; j < imgs.size(); j++) {
				ChangeImgJsonModel m = imgs.get(j);
				if("".equals(m.getId())){
					//新增一条记录
					BuildingImage img = new BuildingImage();
					img.setAddTime(new Date());
					img.setImgKey(m.getName());
					img.setStatus(EntityUtils.AreaStatusEnum.SUCCESS.getValue());
					img.setOrderNum(Integer.parseInt(m.getOrderNum()));
					img.setType(Integer.parseInt(m.getType()));
					img.setBuildingId(req.getId());
					img.setThumbnailKey(img.getImgKey() + ".thumbnail");
					this.getEntityManager().persist(img);
				}else{
					BuildingImage add = this.getEntityManager().find(BuildingImage.class, Integer.parseInt(m.getId()));
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
		this.getEntityManager().merge(bui);
		
		return new Response(0,"楼栋编辑完成!");
	}
	
	/*
	 * 编辑楼栋 时 , 加载 楼栋页面
	 * (non-Javadoc)
	 * @see com.manyi.hims.building.service.BuildingService#findEstateById(java.lang.String)
	 */
	@Override
	public BuildingRes findBuildingById(String id) {
		BuildingRes res = new BuildingRes();
		if(id ==  null  || "".equals(id)){
			log.info("楼栋ID为null , 数据不正确");
			return res;
		}
		Building building = this.getEntityManager().find(Building.class, Integer.parseInt(id));
		Estate estate = this.getEntityManager().find(Estate.class, building.getEstateId());
		res.setSerialCode(estate.getSerialCode());
		
		if(building != null ){
			BeanUtils.copyProperties(building, res);
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
			res.setStatusStr(EntityUtils.AreaStatusEnum.getByValue(res.getStatus()).getDesc());
			//楼栋图片
			String jpql = " from BuildingImage a where a.buildingId = ? and a.status <> ? order by a.type asc , a.orderNum asc ";
			Query query = this.getEntityManager().createQuery(jpql).setParameter(1, building.getId()).setParameter(2, EntityUtils.AreaStatusEnum.DISABLED.getValue());
			List<BuildingImage> imgs =  query.getResultList();
			res.setImages(imgs);
		}
		return res;
	}

	/**
	 * 
	 * 通过搜索 得到对应的 列表内容
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<BuildingRes> buildingList(BuildingReq req) {
		String sql_count = "select count(1)  from Building bui join SubEstate sube on sube.id = bui.subEstateId join Area e "+
				" on (e.areaId =sube.estateId  and e.DTYPE='Estate') join Area town on ( e.parentId = town.areaId and town.DTYPE='Town' ) join Area area "+
				" on (town.parentId = area.areaId  and area.DTYPE='City') join Area city on (area.parentId = city.areaId and city.DTYPE='Province') where bui.id <>0 ";
		
		String sql = "select bui.id,bui.name,sube.id subeId,sube.name subeName,e.areaId estateId,e.name estateName,town.areaId townId,town.name townName, "+
				" area.areaId areaId , area.name areaName,city.areaId cityId , city.name cityName from Building bui join SubEstate sube on sube.id = bui.subEstateId join Area e "+
				" on (e.areaId =sube.estateId  and e.DTYPE='Estate') join Area town on ( e.parentId = town.areaId and town.DTYPE='Town' ) join Area area "+
				" on (town.parentId = area.areaId  and area.DTYPE='City') join Area city on (area.parentId = city.areaId and city.DTYPE='Province') where bui.id <>0 ";

		List<Object> pars = new ArrayList<Object>();
		//按照栋座ID查询
		if(req.getId() != null && !"".equals(req.getId())){
			sql_count += " and bui.id = ? ";
			sql += " and bui.id = ? ";
			pars.add(Integer.parseInt(req.getId()));
		}
		if(req.getSubEstateId() != null && !"".equals(req.getSubEstateId())){
			sql_count += " and sube.id = ? ";
			sql += " and sube.id = ? ";
			pars.add(Integer.parseInt(req.getSubEstateId()));
		}
		//按照小区  ID 搜索
		if (req.getEstateId() != null && ! "".equals(req.getEstateId())) {
			sql_count += " and e.areaId = ? ";
			sql += " and e.areaId = ? ";
			pars.add(Integer.parseInt(req.getEstateId()));
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
		PageResponse<BuildingRes> page = new PageResponse<BuildingRes>();
		List<BuildingRes> resList= new ArrayList<BuildingRes>();
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
					int k =0;
					BuildingRes res = new BuildingRes();
					res.setId(Integer.valueOf(row[k++] + ""));
					res.setName(row[k++] + "");
					res.setSubEstateId(Integer.valueOf(row[k++] + ""));
					res.setSubEstateName(row[k++] + "");
					res.setEstateId(Integer.valueOf(row[k++] + ""));
					res.setEstateName(row[k++]+"");
					res.setTownId(Integer.valueOf(row[k++] + ""));
					res.setTownName(row[k++] + "");
					res.setAreaId(Integer.valueOf(row[k++] + ""));
					res.setAreaName(row[k++] + "");
					res.setCityId(Integer.valueOf(row[k++] + ""));
					res.setCityName(row[k++] + "");
					
					sql = " select count(hr.houseId) houseSum , sum(CASE WHEN hr.houseState =1  THEN 1 ELSE 0 END) rentSum , "+
							" sum(CASE WHEN  hr.houseState = 2 THEN 1 ELSE 0 END ) sellSum , sum(CASE WHEN  hr.houseState = 3 THEN 1 ELSE 0 END ) srSum ,"+
							" sum(CASE WHEN  hr.houseState = 4 THEN 1 ELSE 0 END ) notSum from HouseResource hr join House h on h.houseId = hr.houseId where h.buildingId =  "
							+ res.getId();// 楼栋 下房源数量
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

}
