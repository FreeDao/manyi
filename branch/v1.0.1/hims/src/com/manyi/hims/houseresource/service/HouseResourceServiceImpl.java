package com.manyi.hims.houseresource.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.manyi.hims.BaseService;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.util.DateUtil;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.StatusEnum;

/**
 * @date 2014年4月26日 下午2:30:54
 * @author Tom
 * @description 房源服务类
 */
@Service(value = "houseResourceService")
@Scope(value = "singleton")
public class HouseResourceServiceImpl extends BaseService implements HouseResourceService {
	
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将houseResource复制到Temp表中
	 */
	public void copyHouseResource2Temp(HouseResource houseResource) {
		HouseResourceTemp houseResourceTemp = new HouseResourceTemp();
		BeanUtils.copyProperties(houseResource, houseResourceTemp);
		
		this.getEntityManager().persist(houseResourceTemp);
	}

	/**
	 * @date 2014年4月27日 下午8:47:43
	 * @author Tom  
	 * @description  
	 * 将houseResource复制到History表中
	 * 
	 * @param state  StatusEnum.SUCCESS.getValue()  StatusEnum.Fail.getValue()  
	 * @param targetHouseState  本次操作房源的目标租售状态
	 * 
	 */
	public void copyHouseResource2History(HouseResource houseResource, String note, int operatorId, int state, int targetHouseState) {
		HouseResourceHistory houseResourceHistory = new HouseResourceHistory();
		
		House house = this.getEntityManager().find(House.class, houseResource.getHouseId());
		
		BeanUtils.copyProperties(houseResource, houseResourceHistory);
		
		houseResourceHistory.setSpaceArea(house.getSpaceArea());
		houseResourceHistory.setNote(note);
		houseResourceHistory.setOperatorId(operatorId);
		houseResourceHistory.setStatus(state);
		houseResourceHistory.setCheckNum(houseResource.getCheckNum() + 1);
		houseResourceHistory.setResultDate(new Date());
		houseResourceHistory.setActionType(EntityUtils.ActionTypeEnum.REPORT.getValue());
		
		houseResourceHistory.setAfterCheckHouseState(targetHouseState);
		
		this.getEntityManager().persist(houseResourceHistory);
	}
	
	/**
	 * @date 2014年4月30日 下午5:48:23
	 * @author Tom  
	 * @description  
	 * 删除houseResourceTemp对象
	 */
	public void removeHouseResourceTemp(int houseId) {
		HouseResourceTemp houseResourceTemp = this.getEntityManager().getReference(HouseResourceTemp.class, houseId);
		
		this.getEntityManager().remove(houseResourceTemp);

	}
	
	/**
	 * @date 2014年4月30日 下午5:49:38
	 * @author Tom  
	 * @description  
	 * 更新houseResource对象
	 */
	public void mergeHouseResource(HouseResource houseResource) {
		this.getEntityManager().merge(houseResource);

	}
	
	/**
	 * @date 2014年4月27日 下午8:46:36
	 * @author Tom  
	 * @description  
	 * 发布举报
	 */
	public void publicAudit(int houseId) {
		
		HouseResource houseResource = this.getEntityManager().find(HouseResource.class, houseId);
		copyHouseResource2Temp(houseResource);
		
		//状态，1审核通过,2审核中，3审核失败, 4无效/删除
		houseResource.setStatus(StatusEnum.ING.getValue());
		//举报
		houseResource.setActionType(ActionTypeEnum.REPORT.getValue());
		this.getEntityManager().merge(houseResource);
	}
	
	/**
	 * @date 2014年4月30日 下午4:59:08
	 * @author Tom  
	 * @description  
	 * 返回HouseResource对象
	 */
	public HouseResource getHouseResourceByHouseId(int houseId) {
		return this.getEntityManager().find(HouseResource.class, houseId);
	}
	
	/**
	 * @date 2014年4月23日 下午1:52:23
	 * @author Tom  
	 * @description  
	 * 返回updateHouseResourceHistory对象
	 * 当审核成功、失败的时候，
	 * 
	 * @param targetHouseState  本次操作房源的目标租售状态
	 */
	public void updateHouseResourceHistory(FloorRequest floorRequest, int status, int targetHouseState) {
        Query query = this.getEntityManager().createQuery("from HouseResourceHistory where checkNum = 0 and status = 2 and houseId=?");
        Object obj = query.setParameter(1, floorRequest.getHouseId()).getSingleResult();
        if (obj != null) {
        	HouseResourceHistory houseResourceHistory = (HouseResourceHistory)obj;
        	houseResourceHistory.setOperatorId(floorRequest.getEmployeeId());
        	houseResourceHistory.setStatus(status);
        	houseResourceHistory.setNote(floorRequest.getLookNote());
    		houseResourceHistory.setResultDate(new Date());
    		
    		houseResourceHistory.setAfterCheckHouseState(targetHouseState);
        }
	}
	
	/**
	 * @date 2014年4月23日 下午1:52:23
	 * @author Tom  
	 * @description  
	 * 返回HouseResourceTemp对象
	 */
	public HouseResourceTemp getHouseResourceTemp(int houseId) {
		
		
		return this.getEntityManager().find(HouseResourceTemp.class, houseId);
	}
	
	
	
	/**
	 * @date 2014年4月29日 下午6:49:03
	 * @author Tom  
	 * @description  
	 * 构造jpql，jcnt，pars参数
	 */
	private void makeSql(HouseSearchRequest req, List<Object> pars, StringBuffer jpql, StringBuffer jcnt, Boolean isSell) {
		jpql.append("SELECT ");
		jpql.append("r.publishDate, ");
		jpql.append(isSell ? "r.sellPrice, " : "r.rentPrice, ");
		jpql.append("r.status, ");
		jpql.append("h.houseId, ");
		jpql.append("h.building, ");
		jpql.append("h.floor, ");
		jpql.append("h.room, ");
		jpql.append("h.spaceArea, ");
		jpql.append("h.bedroomSum, ");
		jpql.append("h.livingRoomSum, ");
		jpql.append("h.wcSum, ");
		jpql.append("h.serialCode ");
		
		jpql.append("FROM Residence h, HouseResource r WHERE h.houseId = r.houseId AND ((r.actionType in (1, 2) and r.status in (1, 2)) OR (r.actionType = 3 AND r.status = 3) OR (r.actionType = 4 AND r.status = 2)) ");
		jcnt.append("SELECT count(r.houseId) FROM Residence h, HouseResource r WHERE h.houseId = r.houseId AND ((r.actionType in (1, 2) and r.status in (1, 2)) OR (r.actionType = 3 AND r.status = 3) OR (r.actionType = 4 AND r.status = 2)) ");
		
		//设置出租或出售   houseState;//1出租，2出售，3即租又售
		jpql.append(isSell ? " AND (r.houseState = 2 or r.houseState = 3)" : " AND (r.houseState = 1 or r.houseState = 3)");
		
		//设置片区serialCode
		jpql.append(" AND h.serialCode like ?");
		jcnt.append(" AND h.serialCode like ?");
		pars.add(req.getSerialCode() + "%");
		
		//设置房型  5室以下
		if (req.getBedroomSum() != 0 && req.getBedroomSum() != 100) {
			jpql.append(" AND h.bedroomSum = ?");
			jcnt.append(" AND h.bedroomSum = ?");
			pars.add(req.getBedroomSum());
		}
		//设置房型  5室以上
		if (req.getBedroomSum() != 0 && req.getBedroomSum() == 100) {
			jpql.append(" AND h.bedroomSum >= 6");
			jcnt.append(" AND h.bedroomSum >= 6");
		}
		
		if (isSell) {
			//设置价格区间
			if (req.getStartPrice() != null) {
				jpql.append(" AND r.sellPrice >= ?");
				jcnt.append(" AND r.sellPrice >= ?");
				pars.add(req.getStartPrice());
			}
			if (req.getEndPrice() != null) {
				jpql.append(" AND r.sellPrice <= ?");
				jcnt.append(" AND r.sellPrice <= ?");
				pars.add(req.getEndPrice());
			}
		} else {
			//设置价格区间
			if (req.getStartPrice() != null) {
				jpql.append(" AND r.rentPrice >= ?");
				jcnt.append(" AND r.rentPrice >= ?");
				pars.add(req.getStartPrice());
			}
			if (req.getEndPrice() != null) {
				jpql.append(" AND r.rentPrice <= ?");
				jcnt.append(" AND r.rentPrice <= ?");
				pars.add(req.getEndPrice());
			}
		}
		
		//设置面积区间
		if (req.getStartSpaceArea() != null) {
			jpql.append(" AND h.spaceArea >= ?");
			jcnt.append(" AND h.spaceArea >= ?");
			pars.add(req.getStartSpaceArea());
		}
		if (req.getEndSpaceArea() != null) {
			jpql.append(" AND h.spaceArea <= ?");
			jcnt.append(" AND h.spaceArea <= ?");
			pars.add(req.getEndSpaceArea());
		}
//		设置时间戳
		if (req.getMarkTime() != null && req.getMarkTime() > 0) {
			jpql.append(" AND r.publishDate <= ? ");
			jcnt.append(" AND r.publishDate <= ? ");
			pars.add(new Date(req.getMarkTime()));
		}	
		
		jpql.append(" order by r.publishDate desc");
		jcnt.append(" order by r.publishDate desc");
	}
	
	
	/**
	 * @date 2014年4月29日 下午8:23:58
	 * @author Tom  
	 * @param req 页面传来的参数
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	public Page<HouseEntity> queryHouseResourceByPage(HouseSearchRequest req, Boolean isSell) {
		
		long currentTimeMillis = 0L;
		if (req.getMarkTime() != null && req.getMarkTime() > 0) {
			currentTimeMillis = req.getMarkTime();
		} else {
			currentTimeMillis = System.currentTimeMillis();
		}
		
//		参数对象
		List<Object> pars = new ArrayList<Object>();
//		SQL
		StringBuffer jpql = new StringBuffer();
//		count SQL
		StringBuffer jcnt = new StringBuffer();
		
		this.makeSql(req, pars, jpql, jcnt, isSell);
			
		Query jpqlQuery = this.getEntityManager().createQuery(jpql.toString());
		Query jcntQuery = this.getEntityManager().createQuery(jcnt.toString());
		
		jcntQuery.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		jpqlQuery.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				jpqlQuery.setParameter(i + 1, pars.get(i));
				jcntQuery.setParameter(i + 1, pars.get(i));
			}
		}	
		
//		条数
		Long count = Long.parseLong(jcntQuery.getSingleResult().toString());
		Page<HouseEntity> page = new Page<HouseEntity>();
		
		if (count > 0) {
			List<Object[]> data = jpqlQuery.setFirstResult(req.getStart()).setMaxResults(req.getMax()).getResultList();
			
			for (Object[] row : data) {
				
				HouseEntity entity = new HouseEntity();
				entity.setPublishDate((Date)row[0]);//发布时间
				entity.setPublishStr(DateUtil.fattrDate(entity.getPublishDate()));
				entity.setPrice(BigDecimal.valueOf(row[1]==null?0:Double.parseDouble(row[1].toString())));//价格
				
				entity.setHouseId(Integer.parseInt(row[3].toString()));//房子id
				entity.setBuilding(row[4].toString());// 楼座编号（例如：22栋，22坐，22号）
				entity.setRoom(row[6].toString());// 房号（例如：1304室，1004－1008室等）
				entity.setSpaceArea(BigDecimal.valueOf(Double.parseDouble(row[7].toString())));// 内空面积
				entity.setBedroomSum(Integer.parseInt(row[8].toString()));// 几房
				entity.setLivingRoomSum(Integer.parseInt(row[9].toString()));// 几厅
				entity.setWcSum(Integer.parseInt(row[10].toString()));// 几卫
				
				//考虑到用缓存，这个位置可以从缓存加载
				entity.setEstateName(areaService.getArea4SerialCode(row[11].toString()).getName());
				entity.setTownName(areaService.getArea4SerialCode(row[11].toString().substring(0, row[11].toString().length() - 5)).getName());
				entity.setAreaName(areaService.getArea4SerialCode(row[11].toString().substring(0, row[11].toString().length() - 10)).getName());
				entity.setSourceState(Integer.parseInt(row[2].toString()));
				entity.setMarkTime(currentTimeMillis);
				
				if (page.getRows() == null) {
					page.setRows(new ArrayList<HouseEntity>());
				}
				page.getRows().add(entity);
			}
			
		}
		return page;
	}
	
}
	

