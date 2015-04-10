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
import org.springframework.transaction.annotation.Transactional;

import com.leo.common.Page;
import com.manyi.hims.BaseService;
import com.manyi.hims.Response;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.check.model.FloorRequest;
import com.manyi.hims.common.HouseEntity;
import com.manyi.hims.common.HouseSearchRequest;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseResourceHistory;
import com.manyi.hims.entity.HouseResourceTemp;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.estate.service.EstateService;
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
	
	@Autowired
	private EstateService estateService;
	
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
	private void makeSql(HouseSearchRequest req, List<Object> pars, StringBuffer jpql, Boolean isSell) {
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
		jpql.append("h.serialCode, ");
		jpql.append("h.subEstateId ");
		
		jpql.append("FROM Residence h, HouseResource r WHERE h.houseId = r.houseId AND ((r.actionType in (1, 2) and r.status in (1, 2)) OR (r.actionType = 3 AND r.status = 3) OR (r.actionType = 4 AND r.status = 2)) ");
		
		//设置出租或出售   houseState;//1出租，2出售，3即租又售
		jpql.append(isSell ? " AND (r.houseState = 2 or r.houseState = 3)" : " AND (r.houseState = 1 or r.houseState = 3)");
		
		//设置片区serialCode
		jpql.append(" AND h.serialCode like ?");
		pars.add(req.getSerialCode() + "%");
		
		//设置房型  5室以下
		if (req.getBedroomSum() != 0 && req.getBedroomSum() != 100) {
			jpql.append(" AND h.bedroomSum = ?");
			pars.add(req.getBedroomSum());
		}
		//设置房型  5室以上
		if (req.getBedroomSum() != 0 && req.getBedroomSum() == 100) {
			jpql.append(" AND h.bedroomSum >= 6");
		}
		
		if (isSell) {
			//设置价格区间
			if (req.getStartPrice() != null) {
				jpql.append(" AND r.sellPrice >= ?");
				pars.add(req.getStartPrice());
			}
			if (req.getEndPrice() != null) {
				jpql.append(" AND r.sellPrice <= ?");
				pars.add(req.getEndPrice());
			}
		} else {
			//设置价格区间
			if (req.getStartPrice() != null) {
				jpql.append(" AND r.rentPrice >= ?");
				pars.add(req.getStartPrice());
			}
			if (req.getEndPrice() != null) {
				jpql.append(" AND r.rentPrice <= ?");
				pars.add(req.getEndPrice());
			}
		}
		
		//设置面积区间
		if (req.getStartSpaceArea() != null) {
			jpql.append(" AND h.spaceArea >= ?");
			pars.add(req.getStartSpaceArea());
		}
		if (req.getEndSpaceArea() != null) {
			jpql.append(" AND h.spaceArea <= ?");
			pars.add(req.getEndSpaceArea());
		}
//		设置时间戳
//		if (req.getMarkTime() != null && req.getMarkTime() > 0) {
//			jpql.append(" AND r.publishDate <= ? ");
//			pars.add(new Date(req.getMarkTime()));
//		}	
		
		jpql.append(" order by r.publishDate desc");
	}
	
	
	/**
	 * @date 2014年5月16日 上午10:13:04
	 * @author Tom  
	 * @param cityId 北京/上海
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	@Transactional(readOnly=true)
	public List<HouseEntity> indexList(int cityId, Boolean isSell) {
		HouseSearchRequest req = new HouseSearchRequest();
		req.setSerialCode(areaService.getSerialCodeByAreaId(cityId));
		req.setStart(0);
		req.setMax(50);
		
		Page<HouseEntity> page = this.queryHouseResourceByPage(req, isSell);
		return page.getRows();
	}
	
	
	
	/**
	 * @date 2014年4月29日 下午8:23:58
	 * @author Tom  
	 * @param req 页面传来的参数
	 * @param 标识是否是出售，  true 出售； false出租
	 * @description  
	 * 根据app端发来的请求返回出租/出售信息
	 */
	@Transactional(readOnly=true)
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
		
		this.makeSql(req, pars, jpql, isSell);
			
		Query jpqlQuery = this.getEntityManager().createQuery(jpql.toString());
		
		jpqlQuery.setHint("org.hibernate.cacheable", true);//来实现读取二级缓存
		
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				jpqlQuery.setParameter(i + 1, pars.get(i));
			}
		}	
		
		Page<HouseEntity> page = new Page<HouseEntity>();
		
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
			entity.setSubEstateName(estateService.getSubEstateById(Integer.parseInt(row[12].toString())).getName());
			
			if (page.getRows() == null) {
				page.setRows(new ArrayList<HouseEntity>());
			}
			page.getRows().add(entity);
		}
			
		return page;
	}
	
	
	/**
	 * @date 2014年6月6日 下午8:00:35
	 * @author Tom
	 * @param lunxunFlag 是否排斥轮询任务  。 
	 * 
	 * 	true 轮询任务进行中，不可以发布其他任务; 
	 * 	false 轮询任务进行中，可以发布其他任务
	 * 
	 * @description  
	 * 
	 * 查询该房源正在进行的任务
	 * 
	 * @return 
	 * 
	 * 	EntityUtils.TaskRepulsionEnum 枚举类的 value
	 * 	0,未知; 1,发布;2,改盘;3,举报;4,轮询;5,BD拍照;6,中介拍照;7,没有任何任务
	 * 
	 */
	public int getOngoingTask(int houseId, boolean lunxunFlag) {
//		String jpql = " from HouseResource where status = 2 and houseId = ? ";
		
//		if (lunxunFlag) {
//			//轮询任务进行中，不可以发布其他任务
//			jpql += " and actionType in (1, 2, 3, 4)";
//		} else {
//			//轮询任务进行中，可以发布其他任务
//			jpql += " and actionType in (1, 2, 3)";
//		}
//		List<Object> list = this.getEntityManager().createQuery(jpql).setParameter(1, houseId).getResultList();
//		if (list != null && list.size() > 0) {
//			HouseResource houseResource = (HouseResource)list.get(0);
//			return houseResource.getActionType();
//		}
		
		List<Object> list = this.getEntityManager().createQuery("select b from House h , BdTask b where h.currBDTaskId = b.id and h.houseId = ? and b.taskStatus in ( 1,3,4,6 ) ").setParameter(1, houseId).getResultList();
		/**
		 * 如果是1,3,4则说明当前处在任务中,发布bd zj任务的时候要另外单独判断审核成功是否能再发
		 */
		if (list != null && list.size() > 0) {
			return EntityUtils.TaskRepulsionEnum.BD_TASK.getValue();
		}
		/**
		 * 如果是1,2则说明当前处在任务中,发布bd zj任务的时候要另外单独判断审核成功是否能再发
		 */
		list = this.getEntityManager().createQuery("select u from House h , UserTask u where h.currUserTaskId = u.id and h.houseId = ?  and  u.taskStatus in (1,2,3)").setParameter(1, houseId).getResultList();
		if (list != null && list.size() > 0) {
			return EntityUtils.TaskRepulsionEnum.USER_TASK.getValue();
		}
		return EntityUtils.TaskRepulsionEnum.NOTASK.getValue();
		
	}
	
	/**
	 * @date 2014年6月6日 下午8:00:35
	 * @author fuhao
	 * @param lunxunFlag 是否排斥轮询任务  。 
	 * 
	 * 	true 轮询任务进行中，不可以发布其他任务; 
	 * 	false 轮询任务进行中，可以发布其他任务
	 * 
	 * @description  
	 * 
	 * 查询该房源正在进行的任务
	 * 
	 * @return 
	 * 
	 * 	EntityUtils.TaskRepulsionEnum 枚举类的 value
	 * 	0,未知; 1,发布;2,改盘;3,举报;4,轮询;5,BD拍照;6,中介拍照;7,没有任何任务
	 * 
	 */
	public int getOngoingTaskAll(int houseId, boolean lunxunFlag) {
		String jpql = " from HouseResource where status = 2 and houseId = ? ";
		
		if (lunxunFlag) {
			//轮询任务进行中，不可以发布其他任务
			jpql += " and actionType in (1, 2, 3, 4)";
		} else {
			//轮询任务进行中，可以发布其他任务
			jpql += " and actionType in (1, 2, 3)";
		}
		List<Object> list = this.getEntityManager().createQuery(jpql).setParameter(1, houseId).getResultList();
		if (list != null && list.size() > 0) {
			HouseResource houseResource = (HouseResource)list.get(0);
			return houseResource.getActionType();
		}
		list = this.getEntityManager().createQuery("select b from House h , BdTask b where h.currBDTaskId = b.id and h.houseId = ? and b.taskStatus in ( 1,3,4 ) ").setParameter(1, houseId).getResultList();
		/**
		 * 如果是1,3,4则说明当前处在任务中,发布bd zj任务的时候要另外单独判断审核成功是否能再发
		 */
		if (list != null && list.size() > 0) {
			return EntityUtils.TaskRepulsionEnum.BD_TASK.getValue();
		}
		/**
		 * 如果是1,2则说明当前处在任务中,发布bd zj任务的时候要另外单独判断审核成功是否能再发
		 */
		list = this.getEntityManager().createQuery("select u from House h , UserTask u where h.currUserTaskId = u.id and h.houseId = ?  and  u.taskStatus in (1,2)").setParameter(1, houseId).getResultList();
		if (list != null && list.size() > 0) {
			return EntityUtils.TaskRepulsionEnum.USER_TASK.getValue();
		}
		return EntityUtils.TaskRepulsionEnum.NOTASK.getValue();
	}
	
	private Response checkAllowTakePhotoQuery(int houseState, String serialCode, HouseResource houseResource, BigDecimal houseSpaceArea) {
		Response response = new Response();

		String jpql = "select price, spaceArea from AllowTakePhoto a where houseState = ? and ? like CONCAT(serialCode,'%') ORDER BY serialCode desc ";
		Query query = getEntityManager().createNativeQuery(jpql);
		query.setParameter(1, houseState);
		query.setParameter(2, serialCode);
		List<Object[]> allowTakePhotoList = query.setFirstResult(0).setMaxResults(1).getResultList();
		
		if (allowTakePhotoList != null && allowTakePhotoList.size() == 1) {
			BigDecimal price = new BigDecimal(allowTakePhotoList.get(0)[0].toString());
			BigDecimal spaceArea = new BigDecimal(allowTakePhotoList.get(0)[1].toString());
		
			if (houseState == EntityUtils.HouseStateEnum.RENT.getValue() && houseResource.getRentPrice() != null) {
				if (houseResource.getRentPrice().compareTo(price) < 0) {
					logger.info("不能领取出租价格小于{}元/月的房源！", price.intValue());
					response.setErrorCode(-1);
					response.setMessage("不能领取出租价格小于" + price.intValue() + "元/月的房源！");
				} 
			} 
			if (houseState == EntityUtils.HouseStateEnum.SELL.getValue() && houseResource.getSellPrice() != null) {
				if (houseResource.getSellPrice().compareTo(price) < 0) {
					logger.info("不能领取出售价格小于{}万元的房源！", price.intValue());
					response.setErrorCode(-1);
					response.setMessage("不能领取出售价格小于" + price.intValue() + "万元的房源！");
				} 
			} 
			if (houseSpaceArea != null) {
				if (houseSpaceArea.compareTo(spaceArea) < 0) {
					logger.info("不能领取面积小于{}平米的房源！", spaceArea.intValue());
					response.setErrorCode(-1);
					response.setMessage("不能领取面积小于" + spaceArea.intValue() + "平米的房源！");
				}
			}
			
			if (response.getErrorCode() == 0) {
				response.setMessage("可以发布");
			}
		} else {
			logger.info("该区域暂不能领取拍照任务！");
			response.setErrorCode(-1);
			response.setMessage("该区域暂不能领取拍照任务！");
		}
		
		return response;
	}
	
	/**
	 * @date 2014年6月12日 下午6:35:09
	 * @author Tom
	 * @description  
	 * 检测是否可以领取拍照任务
	 */
	public Response checkAllowTakePhoto(int houseId) {
		Response response = new Response();
		
		HouseResource houseResource = getEntityManager().find(HouseResource.class, houseId);
		if (houseResource.getHouseState() == EntityUtils.HouseStateEnum.NEITHER.getValue()) {
			logger.info("该房源不租不售，不允许拍照！");
			
			response.setErrorCode(-1);
			response.setMessage("该房源不租不售，不允许拍照！");
		} else {
			Residence house = getEntityManager().find(Residence.class, houseId);
			
			//当前房源出租 或 出售
			if (houseResource.getHouseState() == EntityUtils.HouseStateEnum.RENT.getValue() || houseResource.getHouseState() == EntityUtils.HouseStateEnum.SELL.getValue()) {
				return checkAllowTakePhotoQuery(houseResource.getHouseState(), house.getSerialCode(), houseResource, house.getSpaceArea());
				
			} else if (houseResource.getHouseState() == EntityUtils.HouseStateEnum.RENTANDSELL.getValue()) {
				response = checkAllowTakePhotoQuery(EntityUtils.HouseStateEnum.RENT.getValue(), house.getSerialCode(), houseResource, house.getSpaceArea());
				
				if (response.getErrorCode() == 0 && "可以发布".equals(response.getMessage())) {
					return response;
					
				} else {
					return checkAllowTakePhotoQuery(EntityUtils.HouseStateEnum.SELL.getValue(), house.getSerialCode(), houseResource, house.getSpaceArea());
					
				}
			}
		}
		
		return response;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	

