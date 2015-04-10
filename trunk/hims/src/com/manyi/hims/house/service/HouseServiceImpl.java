package com.manyi.hims.house.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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
import com.manyi.hims.entity.BdTask;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.House;
import com.manyi.hims.entity.HouseHistory;
import com.manyi.hims.entity.HouseHistory_;
import com.manyi.hims.entity.HouseImageFile;
import com.manyi.hims.entity.HouseResource;
import com.manyi.hims.entity.HouseSupportingMeasures;
import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.ResidenceHistory;
import com.manyi.hims.entity.UserTask;
import com.manyi.hims.entity.aiwu.Building;
import com.manyi.hims.entity.aiwu.SubEstate;
import com.manyi.hims.estate.EstateUtil;
import com.manyi.hims.estate.model.ChangeImgJsonModel;
import com.manyi.hims.estate.model.ChangeJsonModel;
import com.manyi.hims.house.model.HouseDetailRes;
import com.manyi.hims.house.model.HouseReq;
import com.manyi.hims.house.model.HouseRes;
import com.manyi.hims.usertask.service.UserTaskPhotoService;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.ActionTypeEnum;
import com.manyi.hims.util.EntityUtils.HouseResourceType;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.houseSuppoertEnum;

@Service(value = "houseService")
@Scope(value = "singleton")

public class HouseServiceImpl extends BaseService implements HouseService {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier("userTaskPhotoService")
	private UserTaskPhotoService userTaskPhotoService;
	
	/**
	 *  编辑 时 , 加载 房屋 页面
	 */
	@Override
	public HouseDetailRes findHouseById(int houseId) {
		if (houseId == 0) {
			return new HouseDetailRes(1, "房屋id为空！");
		}
		House h = this.getEntityManager().find(House.class, houseId);
		HouseResource hr= this.getEntityManager().find(HouseResource.class, houseId);
		
		HouseDetailRes res= new HouseDetailRes();
		if(h!= null){
			BeanUtils.copyProperties(h, res);
			Building bui = this.getEntityManager().find(Building.class,h.getBuildingId());
			if(bui != null){
				SubEstate sube =  this.getEntityManager().find(SubEstate.class, bui.getSubEstateId());
				if(sube != null){
					res.setSubEstateId(sube.getId());
					res.setSubEstateName(sube.getName());
				}
			}
			if (StringUtils.isNotBlank(res.getAliasName())) {
				//处理成[{'id':'1','name':''}] json字符串
				JSONArray json = JSONArray.fromObject(res.getAliasName());
				List<String> list = (List<String>) JSONArray.toCollection(json, String.class);
				if (list != null && list.size() > 0) {
					StringBuilder sb = new StringBuilder("[");
					for (int i = 0; i < list.size(); i++) {
						String name = list.get(i);
						if (i != 0) {
							sb.append(",");
						}
						sb.append("{\"id\":\"0\" , \"name\" : \"" + name + "\"}");
					}
					sb.append("]");
					res.setAliasName(sb.toString());
				}
			}else{
				res.setAliasName("[]");
			}
		}
		if(hr != null){
			BeanUtils.copyProperties(hr, res);
			res.setStatusStr(EntityUtils.StatusEnum.getByValue(res.getStatus()).getDesc());
			res.setHouseStateStr(EntityUtils.HouseStateEnum.getByValue(res.getHouseState()).getDesc());
		}
		//获取 房屋的有效图片     
		String jpql = "from HouseImageFile h where h.houseId = ? and h.enable = ? order by h.orderId asc ";
		List<HouseImageFile> images =  this.getEntityManager().createQuery(jpql).setParameter(1, houseId).setParameter(2, 1).getResultList();
		res.setImages(images);
		
		//获取 房屋的 配套信息
		jpql="from HouseSupportingMeasures h where h.houseId =? and h.enable = ? ";
		List<HouseSupportingMeasures> supporings =  this.getEntityManager().createQuery(jpql).setParameter(1, houseId).setParameter(2, 1).getResultList();
		if(supporings != null && supporings.size()>0){
			res.setSupporing(supporings.get(0));
		}
		
		return res;
	}
	
	/**
	 *编辑 房屋
	 */
	@Override
	public Response editHouse(HouseDetailRes req) {
		log.info("修改房屋 传递的参数: {} ",ReflectionToStringBuilder.toString(req));

		if(req.getHouseId() ==0){
			return new Response(1,"房屋ID为空!");
		}
		
		List<ChangeJsonModel> aliasNames = EstateUtil.jsonStrToList(req.getAliasName(),ChangeJsonModel.class);
		List<ChangeImgJsonModel> imgs = EstateUtil.jsonStrToList(req.getImgkeys(),ChangeImgJsonModel.class);
		
		//修改房屋
		House house= this.getEntityManager().find(House.class,req.getHouseId());
		house.setFloor(req.getFloor());
		
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
			house.setAliasName(sb.toString());
		}
		//图片
		if(imgs != null && imgs.size()>0){
			int livingRoomOrderId=3;
			int bedRoomOrderId=13;
			int wcOrderId = 24;
			for (int j = 0; j < imgs.size(); j++) {
				ChangeImgJsonModel m = imgs.get(j);
				if("".equals(m.getId())){
					//新增一条记录
					HouseImageFile img = new HouseImageFile();
					img.setAddTime(new Date());
					img.setImgKey(m.getName());
					img.setEnable(1);//可用
					int orderId = Integer.parseInt(m.getOrderNum());
					String desc ="";
					if("wc".equals(m.getType())){
						img.setOrderId(wcOrderId+orderId);
						desc ="卫生间";
					}else if("bedRoom".equals(m.getType())){
						img.setOrderId(bedRoomOrderId + orderId);
						desc ="卧室";
					}else if("livingRoom".equals(m.getType())){
						img.setOrderId(livingRoomOrderId + orderId);
						desc ="客厅";
					}
					HouseResourceType ht = HouseResourceType.getByValue(img.getOrderId());
					if(ht.getValue() == 0){
						img.setDescription(desc);
					}else{
						img.setDescription(ht.getDesc());
					}
					img.setType(m.getType());
					img.setHouseId(req.getHouseId());
					img.setThumbnailKey(img.getImgKey() + ".thumbnail");
					this.getEntityManager().persist(img);
				}else{
					HouseImageFile img = this.getEntityManager().find(HouseImageFile.class, Integer.parseInt(m.getId()));
					if("".equals(m.getName()) || "0".equals(m.getName())){
						//删除
						img.setEnable(0);
					}else{
						//修改
						img.setImgKey(m.getName());
						img.setThumbnailKey(img.getImgKey() + ".thumbnail");
						
						int orderId = Integer.parseInt(m.getOrderNum());
						String desc ="";
						if("wc".equals(m.getType())){
							img.setOrderId(wcOrderId+orderId);
							desc ="卫生间";
						}else if("bedRoom".equals(m.getType())){
							img.setOrderId(bedRoomOrderId + orderId);
							desc ="卧室";
						}else if("livingRoom".equals(m.getType())){
							img.setOrderId(livingRoomOrderId + orderId);
							desc ="客厅";
						}
						HouseResourceType ht = HouseResourceType.getByValue(img.getOrderId());
						if(ht.getValue() == 0){
							img.setDescription(desc);
						}else{
							img.setDescription(ht.getDesc());
						}
					}
					
					this.getEntityManager().merge(img);
				}
			}
		}
		//房屋的配置信息
		HouseSupportingMeasures supp = req.getSupporing();
		if( supp!= null){
			if(supp.getId() != 0){
				//修改
				HouseSupportingMeasures tmp =this.getEntityManager().find(HouseSupportingMeasures.class, supp.getId());
				tmp.setBdTaskId(0);
				tmp.setUserTaskId(0);
				tmp.setEnable(1);
				tmp.setHasAirConditioner(supp.isHasAirConditioner());
				tmp.setHasBathtub(supp.isHasBathtub());
				tmp.setHasBed(supp.isHasBed());
				tmp.setHasCentralAirConditioning(supp.isHasCentralAirConditioning());
				tmp.setHasCentralHeating(supp.isHasCentralHeating());
				tmp.setHasCloakroom(supp.isHasCloakroom());
				tmp.setHasCourtyard(supp.isHasCourtyard());
				tmp.setHasGazebo(supp.isHasGazebo());
				tmp.setHasPenthouse(supp.isHasPenthouse());
				tmp.setHasRefrigerator(supp.isHasRefrigerator());
				tmp.setHasReservedParking(supp.isHasReservedParking());
				tmp.setHasSofa(supp.isHasSofa());
				tmp.setHasTV(supp.isHasTV());
				tmp.setHasWashingMachine(supp.isHasWashingMachine());
				tmp.setHasWaterHeater(supp.isHasWaterHeater());
				try {
					String str = userTaskPhotoService.mapString(tmp);
					tmp.setSupportingMeasuresStr(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.getEntityManager().merge(tmp);
			}else{
				HouseSupportingMeasures tmp =new HouseSupportingMeasures();
				BeanUtils.copyProperties(supp, tmp);
				tmp.setHouseId(req.getHouseId());
				tmp.setEnable(1);
				try {
					String str = userTaskPhotoService.mapString(tmp);
					tmp.setSupportingMeasuresStr(str);
				} catch (Exception e) {
					e.printStackTrace();
				}
				this.getEntityManager().persist(tmp);
			}
		}
		this.getEntityManager().merge(house);
		
		return new Response(0,"楼栋编辑完成!");
	
	}
	
	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<HouseRes> houseList(HouseReq req) {
		String count_sql="SELECT COUNT(1) FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId left join HouseResource log on log.houseId = h.houseId  left join BdTask dbt on dbt.id = h.currBDTaskId   where log.status <> 4 and h.houseId <>0 ";
		String sql="SELECT h.houseId,city.areaId cityId,city.name cityName,area.areaId areaId , area.name areaName , town.areaId townId,town.name townName,sube.areaId estateId, "
				+ "sube.name estateName,sube.road road,h.building,h.room,log.houseState sellState ,log.publishDate sellPublishDate,"
				+ "log.houseState rentState,log.publishDate rentPublishDate,log.houseId logId ,log.publishDate logCreateTime,log.actionType logType ,log.status,dbt.taskStatus,h.picNum ,usertask.taskStatus userTaskStatus "
				+ "FROM House h join Area sube on sube.areaId = h.estateId  join Area town on town.areaId = sube.parentId "
				+ "join Area area on area.areaId = town.parentId join Area city on city.areaId = area.parentId left join HouseResource log on h.houseId = log.houseId "
				+ "left join BdTask dbt on dbt.id = h.currBDTaskId "
				+ "left join UserTask usertask on usertask.id = h.currUserTaskId "
				+ "where log.status <>4  and h.houseId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		if (req.getHouseId() > 0) {
			count_sql += " and h.houseId = ? ";
			sql += " and h.houseId = ? ";
			pars.add(req.getHouseId()); 
			
		}
		// 通过 区域 进行搜索 房源信息
		
		if (req.getCityType() > 0) {
			count_sql += " and area.parentId = ? ";
			sql += " and area.parentId = ? ";
			pars.add(req.getCityType()); 
			
		}
		if ( req.getParentId() > 0) {
				// 按照行政区域搜索
				count_sql += " and area.areaId = ? ";
				sql += " and area.areaId = ? ";
				pars.add(req.getParentId());
		} 
		if (req.getAreaId() > 0 ) {
				// 按照 行政区下面的 片区 进行搜索
				count_sql += " and town.areaId = ? ";
				sql += " and town.areaId = ? ";
				pars.add(req.getAreaId());
		}
		//是否有图片
		if (req.getPicNum() > 0) {
			if(req.getPicNum() ==1){
				//无图片
				count_sql += " and ( h.picNum is null or h.picNum =0 ) ";
				sql += " and ( h.picNum is null or h.picNum =0 ) ";
			}else if(req.getPicNum() == 2){
				//有图片
				count_sql += " and h.picNum >0 ";
				sql += " and h.picNum >0 ";
			}
		}

		//按照小区 名称 搜索
		if(StringUtils.isNotBlank(req.getEstateName())){
			count_sql += " and ( sube.name like ? or sube.road like ? ) ";
			sql += " and ( sube.name like ? or sube.road like ? ) ";
			pars.add("%"+req.getEstateName()+"%");
			pars.add("%"+req.getEstateName()+"%");
		}
		
		
		// 按照 出售状态搜索
		if (req.getSellState() != 0) {
			count_sql += " and log.houseState = ? ";
			sql += " and log.houseState = ? ";
			pars.add(req.getSellState());
		}
		
		// 审核状态
		/*if (!StringUtils.isBlank(req.getOperServiceState())) {
			count_sql += " and log.status in ( ";
			sql += " and log.status in ( ";
			String[] serviceStates = req.getOperServiceState().split(",");
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ";
			sql += " ) ";
		}*/
	
		if (!StringUtils.isBlank(req.getOperServiceState())) {
			count_sql += " and ( ( log.actionType in (1,2,3) and log.status in ( ";
			sql += " and (  ( log.actionType in (1,2,3) and log.status in ( ";
			String[] serviceStates = req.getOperServiceState().split(",");
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ";
			sql += " ) ";
			count_sql += " ) ";
			sql += " ) ";
			
			count_sql += " or ( log.actionType in (4) and log.status in ( ";
			sql += " or ( log.actionType in (4) and log.status in ( ";
			for (int i = 0; i < serviceStates.length; i++) {
				if (i != 0) {
					count_sql += " , ";
					sql += " , ";
				}
				
				if ("1".equals(serviceStates[i])) {
					serviceStates[i] = "3";
				}else if ("3".equals(serviceStates[i])) {
					serviceStates[i] = "1";
				}
				
				count_sql += serviceStates[i];
				sql += serviceStates[i];
			}
			count_sql += " ) ) ) ";
			sql += " ) ) ) ";
			
		}
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
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
		PageResponse<HouseRes> page = new PageResponse<HouseRes>();
		if (total > 0) {
			query.setFirstResult((req.getPage()-1)*req.getRows());//起始下标
			query.setMaxResults(req.getRows());//查询出来的数量/条数
			page.setPageSize(req.getRows());
			page.setCurrentPage(req.getPage());
			page.setTotal(total);
			int n = (total/req.getRows());
			page.setTotalPage(n);//总页数
			List<Object[]> objs = query.getResultList();
			if(objs != null && objs.size()>0){
				for (Object[] row : objs) {
					int i = 0;
					HouseRes house = new HouseRes();
					house.setHouseId(Integer.parseInt(row[i++]+""));
					
					house.setCityId(Integer.parseInt(row[i++]+""));
					house.setCityName(row[i++]+"");
					
					house.setAreaId(Integer.parseInt(row[i++]+""));
					house.setAreaName(row[i++]+"");
					house.setTownId(Integer.parseInt(row[i++]+""));
					house.setTownName(row[i++]+"");
					house.setEstateId(Integer.parseInt(row[i++]+""));
					house.setEstateName(row[i] + (row[i+1] == null ? "" : row[i+1]+""));
					i++;
					i++;
					house.setBuiling(row[i++]+"");
					house.setRoom(row[i++]+"");
					String sellState = row[i] == null ? "0" : row[i]+"";//出售状态
					i++;
					house.setSellState(Integer.parseInt(sellState));
					house.setSellStateStr(HouseStateEnum.getByValue(Integer.parseInt(sellState)).getDesc());
					house.setSellPublishDate((Date) row[i++]);
					String rentState = row[i] == null ? "0" : row[i]+"";//出租状态
					i++;
					house.setRentState(Integer.parseInt(rentState));
					house.setRentPublishDate((Date) row[i]);
					i++;
					house.setLogId(Integer.parseInt(row[i] == null ? "" : row[i]+""));
					i++;
					house.setLogCreateTime((Date) row[i]);
					i++;
					house.setCheckType(row[i] == null ? "0" : row[i] + "");
					house.setCheckTypeStr(ActionTypeEnum.getByValue(Integer.parseInt(row[i] == null ? "0" : row[i] + "")).getDesc());
					i++;
					house.setOperServiceState(row[i] == null ? 0 :Integer.parseInt(row[i]+""));
					i++;
					house.setTaskState(row[i] == null ? 0 :Integer.parseInt(row[i]+""));
					house.setTaskStateStr(EntityUtils.TaskStatusEnum.getByValue(house.getTaskState()).getDesc());
					i++;
					house.setPicNum(row[i] == null ? 0 :Integer.parseInt(row[i]+""));
					i++;
					house.setUserTaskState(row[i] == null ? 0 :Integer.parseInt(row[i]+""));
					house.setUserTaskStateStr(EntityUtils.UserTaskStatusEnum.getByValue(house.getUserTaskState()).getDesc());
					if(page.getRows() == null){
						page.setRows(new ArrayList<HouseRes>());
					}
					page.getRows().add(house);
				}
			}else{
				page.setRows(new ArrayList<HouseRes>());
			}
		}else{
			page.setRows(new ArrayList<HouseRes>());
		}
		
		return page;
	}
	
	
	/**
	 * @date 2014年4月28日 下午5:30:12
	 * @author Tom  
	 * @description  
	 * 将House复制到History表中
	 */
	public void copyHouse2History(House house) {
		HouseHistory houseHistory = new HouseHistory();
		BeanUtils.copyProperties(house, houseHistory);
		this.getEntityManager().persist(houseHistory);
	}
	
	/**
	 * @date 2014年4月23日 下午12:25:07
	 * @author Tom  
	 * @description  
	 * 加载房屋信息（例如： 中远两湾城 - 22号 - 1102室）
	 */
	public String getHouseMsg(int houseId) {
		Residence residence = this.getEntityManager().find(Residence.class, houseId);
		Estate subEstate = this.getEntityManager().find(Estate.class, residence.getEstateId());
		
		return subEstate.getName() + " - " + residence.getBuilding() + "号 - " + residence.getRoom() + "室";
	}
	
	/**
	 * @date 2014年5月19日 下午8:12:16
	 * @author Tom
	 * @description  
	 * 更新房型 
	 */
	public Response checkUpdateResidence(Residence residence) {

//		检测变量范围
		if (residence.getSpaceArea().compareTo(new BigDecimal(EntityUtils.LimitNumEnum.SPACE_AREA.getValue())) > 0) {
		//面    积：不大于10,000平方米；
			Response temp = new Response();
			temp.setErrorCode(-1);
			temp.setMessage("面积：不能大于10,000平方米");
			return temp;
		} else {
			
			Residence residence1 = this.getEntityManager().find(Residence.class, residence.getHouseId());
			ResidenceHistory residenceHistory = new ResidenceHistory();
			
			//有一个数据有变化就存历史
			if (!(residence.getBedroomSum() == residence1.getBedroomSum()
					&& residence.getLivingRoomSum() == residence1.getLivingRoomSum()
					&& residence.getWcSum() == residence1.getWcSum()
					&& residence.getSpaceArea().compareTo(residence1.getSpaceArea()) == 0)) {
//			存历史
				BeanUtils.copyProperties(residence1, residenceHistory);
				this.getEntityManager().persist(residenceHistory);
			}
			
//			修改当前
			residence1.setBedroomSum(residence.getBedroomSum());// 几房
			residence1.setLivingRoomSum(residence.getLivingRoomSum());// 几厅
			residence1.setWcSum(residence.getWcSum());// 几卫
			residence1.setSpaceArea(residence.getSpaceArea());// 内空面积
			residence1.setDecorateType(residence.getDecorateType()); //1 毛坯，2 装修
			residence1.setPicNum(residence.getPicNum()); //照片数  

			
			return new Response();
		}
	}
	
	
	
	
    @Override
    public List<List<String>> houseModifications(int houseId) {
            CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
            CriteriaQuery<HouseHistory> query = builder.createQuery(HouseHistory.class);
            Root<HouseHistory> root = query.from(HouseHistory.class);
            query.select(root).where(builder.equal(root.get(HouseHistory_.houseId), houseId));
            List<HouseHistory> houseHistories = this.getEntityManager().createQuery(query).getResultList();
            List<List<String>> houseModifications = new ArrayList<List<String>>();
            if(houseHistories!=null && houseHistories.size()>0){
                    for(int i=0, n=houseHistories.size();i<n-1;i++){
                            HouseHistory before = houseHistories.get(i);
                            HouseHistory after = houseHistories.get(i+1);
                            if(before instanceof ResidenceHistory){
                                    ResidenceHistory rbefore = (ResidenceHistory)before;
                                    ResidenceHistory rafter = (ResidenceHistory)after;
                                    List<String> modifications = new ArrayList<String>();
                                    if(rbefore.getBedroomSum()!=rafter.getBedroomSum()){
                                            modifications.add("Bedroom change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getLivingRoomSum()!=rafter.getLivingRoomSum()){
                                            modifications.add("Living root change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getWcSum()!=rafter.getWcSum()){
                                            modifications.add("Wc num change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(rbefore.getBalconySum()!=rafter.getBalconySum()){
                                            modifications.add("Balcony num change from: " + rbefore.getBedroomSum() + ", to:" + rafter.getBedroomSum());
                                    }
                                    if(modifications.size()>0){
                                            houseModifications.add(modifications);
                                    }
                            }
                    }
            }
            return houseModifications;
    }
    
	/**
	 * @date 2014年5月22日 上午1:22:15
	 * @author Tom
	 * @description  
	 * 根据houseId获得小区serialCode  
	 */
	public String getSerialCodeByHouseId(int houseId) {
		House house = this.getEntityManager().find(House.class, houseId);
		return house.getSerialCode();
	}
	

	/**
	 * @date 2014年5月22日 上午1:22:15
	 * @author Tom
	 * @description  
	 * 根据houseId获得DecorateType 	1 毛坯，2 装修  
	 */
	public int getDecorateTypeByHouseId(int houseId) {
		House house = this.getEntityManager().find(House.class, houseId);
		return house.getDecorateType();
	}
	
	@Override
	public int getDecorateTypeByUserTaskId(int id) {
		UserTask task = this.getEntityManager().find(UserTask.class, id);
		return task.getAfterDecorateType();
	}
	
	/**
	 * @date 2014年5月26日 下午1:53:40
	 * @author Tom
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @description  
	 * 查看装修是否是1基本配套，2高级配套
	 */
	public String getHouseSuppoert(int houseId, String isGaoji) throws Exception {
		List<HouseSupportingMeasures> houseSupportingMeasures = this.getEntityManager().createQuery("from HouseSupportingMeasures where houseId = ? and enable=1 ").setParameter(1, houseId).getResultList();
		
		StringBuilder sb = new StringBuilder();
		if(houseSupportingMeasures != null && houseSupportingMeasures.size()>0){
			HouseSupportingMeasures hsm = houseSupportingMeasures.get(0);
			Class<? extends Object> clazz = hsm.getClass();
			Field fields[] = clazz.getDeclaredFields();
			for(Field f:fields){
				if(!f.getName().startsWith("has")){
					continue;
				}
				String methodName = convertMethod("is",f.getName());
				Method get = clazz.getDeclaredMethod(methodName);
				Object key = get.invoke(hsm);
				if(new Boolean(key.toString())) {
					houseSuppoertEnum s = EntityUtils.houseSuppoertEnum.valueOf(String.valueOf(f.getName()));
					if(isGaoji.equals(s.getIsGaoji())) {
						sb.append(s.getCn()).append(" ");
					}
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 通过usertaskid 获取 装修情况
	 * @param taskId
	 * @param isGaoji
	 * @return
	 * @throws Exception
	 */
	@Override
	public String getHouseSuppoertByUserTaskId(int taskId, String isGaoji) throws Exception {
		List<HouseSupportingMeasures> houseSupportingMeasures = this.getEntityManager().createQuery(" from HouseSupportingMeasures where userTaskId = ? ").setParameter(1, taskId).getResultList();
		if(houseSupportingMeasures != null && houseSupportingMeasures.size()>0){
			return findHouseSuppert(houseSupportingMeasures.get(0),isGaoji);
		}else{
			return null;
		}
	}
	
	/**
	 * 查看装修是否是1基本配套，2高级配套
	 * @return
	 * @throws Exception 
	 */
	private String findHouseSuppert(HouseSupportingMeasures measures, String isGaoji) throws Exception{
		StringBuilder sb = new StringBuilder();
		Class<? extends Object> clazz = measures.getClass();
		Field fields[] = clazz.getDeclaredFields();
		for(Field f:fields){
			if(!f.getName().startsWith("has")){
				continue;
			}
			String methodName = convertMethod("is",f.getName());
			Method get = clazz.getDeclaredMethod(methodName);
			Object key = get.invoke(measures);
			if(new Boolean(key.toString())) {
				houseSuppoertEnum s = EntityUtils.houseSuppoertEnum.valueOf(String.valueOf(f.getName()));
				if(isGaoji.equals(s.getIsGaoji())) {
					sb.append(s.getCn()).append(" ");
				}
			}
		}
		return sb.toString();
	}
	
	private String convertMethod(String get, String name) {
		return get+Character.toUpperCase(name.charAt(0))+name.substring(1);
	}
	
	
	/**
	 * @date 2014年5月26日 下午2:41:37
	 * @author Tom
	 * @description  
	 * 获得有效房屋图片列表
	 */
	public List<HouseImageFile> getHouseEnableImageList(int houseId) {
		Query query = this.getEntityManager().createQuery("from HouseImageFile where houseId = ? and enable = 1 order by orderId ");
		query.setParameter(1, houseId);
		return (List<HouseImageFile>)query.getResultList();
	}
	
	/**
	 * @date 2014年5月26日 下午2:41:37
	 * @author Tom
	 * @description  
	 * 获得usertask 拍照详情页面图片
	 */
	public List<HouseImageFile> getHouseImageList(int houseId, int userTaskId) {
		Query query = this.getEntityManager().createQuery("from HouseImageFile where houseId = ? and userTaskId = ? order by orderId ");
		query.setParameter(1, houseId);
		query.setParameter(2, userTaskId);
		return (List<HouseImageFile>)query.getResultList();
	}
	
	/**
	 * @date 2014年5月27日 下午1:54:14
	 * @author Tom
	 * @description  
	 * 获得任务的完成时间和位置信息
	 */
	public BdTask getBDtaskFinishAndLBS(int houseId) {
		Query query = this.getEntityManager().createQuery("from BdTask where taskStatus = 6 and houseId = ? order by finishDate desc ");
		query.setParameter(1, houseId);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<Object> list = query.getResultList();
		return (list.size() == 1 ? (BdTask)list.get(0) : null);
	}
	
	/**
	 * @date 2014年6月6日 下午3:16:06
	 * @author Tom
	 * @description  
	 * 查询房子
	 */
	public House findHouse(int subEstateId, String building, String room) {

		Query query = this.getEntityManager().createQuery("select h from House h, HouseResource hr where h.houseId = hr.houseId and h.subEstateId = ? and h.building = ? and h.room = ? ");
		query.setParameter(1, subEstateId);
		query.setParameter(2, building);
		query.setParameter(3, room);
		List<Object> list = query.getResultList();
		
		if (list.size() >= 1) {
			return (House)list.get(0);
		} else {
			return null;
		}
		
	}
}
