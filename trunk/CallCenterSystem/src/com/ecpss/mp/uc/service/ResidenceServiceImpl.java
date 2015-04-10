package com.ecpss.mp.uc.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.entity.Area;
import com.ecpss.mp.entity.Area_;
import com.ecpss.mp.entity.Estate;
import com.ecpss.mp.entity.Estate_;
import com.ecpss.mp.entity.HouseStatus;
import com.ecpss.mp.entity.HouseType;
import com.ecpss.mp.entity.Residence;
import com.ecpss.mp.entity.Residence_;
import com.leo.common.Page;

@Service(value = "residenceService")
@Scope(value = "singleton")
public class ResidenceServiceImpl extends BaseService implements ResidenceService {
	
	/**
	 * getAllList
	 * @return
	 */
	public List<Residence> getAllList(){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
		cq.from(Residence.class);
		List<Residence> list = getEntityManager().createQuery(cq).getResultList();
		return list;
	}
	
	public Page<ResidenceInfo> getResidencesInfoByPage2(ResidenceInfo request, Integer pageNo, Integer pageSize, String sort, String order){

		String count_jpql="select count(1) from Residence r where r.hid != '' ";
		String jpql="from Residence r where r.hid != '' ";
		List pars =new ArrayList();
		//为 查询 语句 添加条件
		if(request != null){
			if(request.getAreaId() != 0 && StringUtils.isNotBlank(String.valueOf(request.getAreaId()))){
				//区域 搜索条件
				if(request.getAreaType() != null){
					if( request.getAreaType().indexOf("city") >=0 ){
						count_jpql +=" and r.estate.parent.parent.areaId = ? ";
						jpql +=" and r.estate.parent.parent.areaId = ? ";
						pars.add(request.getAreaId());
					}else if( request.getAreaType().indexOf("town") >=0 ){
						count_jpql +=" and r.estate.parent.areaId = ? ";
						jpql +=" and r.estate.parent.areaId = ? ";
						pars.add(request.getAreaId());
					}
				}
			}
			if(request.getStatusId() != 0 && StringUtils.isNotBlank(String.valueOf(request.getStatusId()))){
				//房屋状态信息
				count_jpql +=" and r.status.hsid = ? ";
				jpql +="  and r.status.hsid = ? ";
				pars.add(request.getStatusId());
			}
		}
		Query countQuery= this.getEntityManager().createQuery(count_jpql);
		if(pars.size() >0){
			for (int i = 0; i < pars.size() ; i++) {
				countQuery.setParameter((i+1), pars.get(i));
			}
		}
		Long count = (Long)countQuery.getSingleResult();
		
		Page<ResidenceInfo> page = new Page<ResidenceInfo>(pageNo==null?1:pageNo,pageSize==null?0:pageSize);
		if(count >0){
			page.setTotal(Integer.parseInt(count+""));
			
			Query query =  getEntityManager().createQuery(jpql);
			if(pars.size() >0){
				for (int i = 0; i < pars.size() ; i++) {
					query.setParameter((i+1), pars.get(i));
				}
			}
			List<Residence> list =query.getResultList();
			
			List<ResidenceInfo> infos =new  ArrayList<ResidenceInfo>();
			for (int i = 0; list != null && i < list.size(); i++) {
				Residence residence = list.get(i);
				ResidenceInfo info = new ResidenceInfo();
				info.setRoom(residence.getRoom());
				info.setBalconySum(residence.getBalconySum());
				info.setBedroomSum(residence.getBedroomSum());
				info.setBuilding(residence.getBuilding());
				info.setCoveredArea(residence.getCoveredArea());
				info.setDirectionId(residence.getDirection().getHdid());
				info.setDirectionTitle(residence.getDirection().getTitle());
				info.setEstateId(residence.getEstate().getAreaId());
				info.setEstateTitle(residence.getEstate().getName());
				info.setFloor(residence.getFloor());
				info.setHid(residence.getHid());
				info.setLayers(residence.getLayers());
				info.setLivingRoomSum(residence.getLivingRoomSum());
				info.setMainOwnerId(residence.getMainOwner().getOid());
				info.setMainOwnerTitle(residence.getMainOwner().getRealName());
				info.setRightId(residence.getRight().getHrid());
				info.setRightTitle(residence.getRight().getType());
				info.setWcSum(residence.getWcSum());
				info.setSpaceArea(residence.getSpaceArea());
				info.setTypeId(residence.getType().getHtid());
				info.setTypeTitle(residence.getType().getTitle());
				info.setAreaId(residence.getEstate().getParent().getAreaId());
				info.setAreaTitle(residence.getEstate().getParent().getName());
				info.setStatusId(residence.getStatus().getHsid());
				info.setStatusTitle(residence.getStatus().getType());
				
				infos.add(info);
			}
			
			page.setRows(infos);
		}
		return page;
	}

	public Page<ResidenceInfo> getResidencesInfoByPage(ResidenceInfo request, Integer pageNo, Integer pageSize, String sort, String order){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> cq_count =cb.createQuery(Long.class);
		CriteriaQuery<Residence> cq = cb.createQuery(Residence.class);
		
		Root<Residence> root_count =  cq_count.from(Residence.class);
		Root<Residence> root =  cq.from(Residence.class);
		
		List<Predicate> count_pred =new  ArrayList<Predicate>();
		List<Predicate> root_pred = new ArrayList<Predicate>();
		
		if(request != null){
			//为 查询 语句 添加条件
			if(request.getAreaId() != 0 && StringUtils.isNotBlank(String.valueOf(request.getAreaId()))){
				//区域 搜索条件
				//Area _area = getAreaById(request.getAreaId());
				if(request.getAreaType().indexOf("city")>=0){
					root_pred.add(cb.equal(root.get(Residence_.estate).get(Estate_.parent).get(Area_.parent).get(Area_.areaId), request.getAreaId()));
					count_pred.add(cb.equal(root_count.get(Residence_.estate).get(Estate_.parent).get(Area_.parent).get(Area_.areaId),  request.getAreaId()));
				}else if(request.getAreaType().indexOf("town") >=0){
					root_pred.add(cb.equal(root.get(Residence_.estate).get(Estate_.parent).get(Area_.areaId), request.getAreaId()));
					count_pred.add(cb.equal(root_count.get(Residence_.estate).get(Estate_.parent).get(Area_.areaId), request.getAreaId()));
				}
				
			}
			if(request.getTypeId() != 0 && StringUtils.isNotBlank(String.valueOf(request.getTypeId()))){
				//房屋类型信息
				HouseType tmpHoustype = getHousType(request.getTypeId());
				count_pred.add(cb.equal(root_count.get(Residence_.type), tmpHoustype));
				root_pred.add(cb.equal(root.get(Residence_.type), tmpHoustype));
			}
			if(request.getStatusId() != 0 && StringUtils.isNotBlank(String.valueOf(request.getStatusId()))){
				//房屋状态信息
				HouseStatus tmpHousStatus = getHouseStatus(request.getStatusId());
				count_pred.add(cb.equal(root_count.get(Residence_.status), tmpHousStatus));
				root_pred.add(cb.equal(root.get(Residence_.status), tmpHousStatus));
			}
		}
		
		
		//添加 搜索条件
		if(count_pred.size()>0){
			cq_count.where(count_pred.toArray(new Predicate[0]));
		}
		if(root_pred.size()>0){
			cq.where(root_pred.toArray(new Predicate[0]));
		}
		
		cq_count.select(cb.count(root_count));
		Long count = getEntityManager().createQuery(cq_count).getSingleResult();// 该条sql 查询 所得的数量
		
		if("asc".equalsIgnoreCase(order)){
			cq.orderBy(cb.asc(root.get(sort)));
		}else if("desc".equalsIgnoreCase(order)){
			cq.orderBy(cb.desc(root.get(sort)));
		}
		cq.select(root);
		
		Page<ResidenceInfo> page = new Page<ResidenceInfo>(pageNo==null?1:pageNo,pageSize==null?0:pageSize);
		
		page.setTotal(Integer.parseInt(count+""));
		List<Residence> list = getEntityManager().createQuery(cq).getResultList();
		
		List<ResidenceInfo> infos =new  ArrayList<ResidenceInfo>();
		for (int i = 0; list != null && i < list.size(); i++) {
			Residence residence = list.get(i);
			ResidenceInfo info = new ResidenceInfo();
			info.setRoom(residence.getRoom());
			info.setBalconySum(residence.getBalconySum());
			info.setBedroomSum(residence.getBedroomSum());
			info.setBuilding(residence.getBuilding());
			info.setCoveredArea(residence.getCoveredArea());
			info.setDirectionId(residence.getDirection().getHdid());
			info.setDirectionTitle(residence.getDirection().getTitle());
			info.setEstateId(residence.getEstate().getAreaId());
			info.setEstateTitle(residence.getEstate().getName());
			info.setFloor(residence.getFloor());
			info.setHid(residence.getHid());
			info.setLayers(residence.getLayers());
			info.setLivingRoomSum(residence.getLivingRoomSum());
			info.setMainOwnerId(residence.getMainOwner().getOid());
			info.setMainOwnerTitle(residence.getMainOwner().getRealName());
			info.setRightId(residence.getRight().getHrid());
			info.setRightTitle(residence.getRight().getType());
			info.setWcSum(residence.getWcSum());
			info.setSpaceArea(residence.getSpaceArea());
			info.setTypeId(residence.getType().getHtid());
			info.setTypeTitle(residence.getType().getTitle());
			info.setAreaId(residence.getEstate().getParent().getAreaId());
			info.setAreaTitle(residence.getEstate().getParent().getName());
			info.setStatusId(residence.getStatus().getHsid());
			info.setStatusTitle(residence.getStatus().getType());
			
			infos.add(info);
		}
		
		page.setRows(infos);
		
		return page;
	}
	
	
	
	/**
	 * 通过 房屋的id- 的到 小区信息
	 * @param houseId
	 * @return
	 */
    public Estate getEstateByHouseId(int houseId){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Estate> cq = cb.createQuery(Estate.class);
        Root<Residence>  root = cq.from(Residence.class);
        Join<Residence, Estate> merchantPath = root.join(Residence_.estate,JoinType.LEFT);
        cq.where(cb.equal(root.get(Residence_.hid), houseId)).select(merchantPath);
        return getEntityManager().createQuery(cq).getSingleResult();
    }
    
    /**
     * 通过 房屋的id- 的到 小区信息
     * @param esId
     * @return
     */
    public Estate getEstateById(int esId){
    	Estate estate = getEntityManager().find(Estate.class, esId);
    	return estate; 
    }
    /**
	 * 通过 房屋的id- 的到 区域信息
	 * @param aId
	 * @return
	 */
    public Area getAreaById(int aId){
    	Area area = getEntityManager().find(Area.class, aId);
    	return area; 
    }
    
    /**
     * 根据 id得到 房屋类型
     * @param htId
     * @return
     */
    public HouseType getHousType(int htId){
    	HouseType ht = new HouseType();
    	ht = getEntityManager().find(HouseType.class, htId);
    	return ht;
    }
    
    /**
     * 根据 id得到 房屋状态
     * @param hsId
     * @return
     */
    public HouseStatus getHouseStatus(int hsId){
    	HouseStatus ht = new HouseStatus();
    	ht = getEntityManager().find(HouseStatus.class, hsId);
    	return ht;
    }
    
	
	/**
	 * 分页查询 数据
	 * @param residence Residence对象
	 * @param pageNo 当前页面
	 * @param pageSize 一页多少数据
	 * @param sort 排序字段
	 * @param order asc / desc 
	 * @return
	 */
	public Page<Residence> getEntityByPage(ResidenceInfo residence , Integer pageNo,Integer pageSize,String sort,String order){
		
		
		String sql_count="select count(1) from Residence ";
		BigInteger countResidence =(BigInteger) getEntityManager().createNativeQuery(sql_count).getSingleResult();
		
		Page<Residence> page = new Page<Residence>(pageNo==null ? 1:pageNo,pageSize==null ? 0 :pageSize);
		if(countResidence.intValue() <= 0){
			page.setRows(new ArrayList<Residence>(0));
		}else{
			page.setTotal(countResidence.intValue());
		}
		
		String jpql ="select r from Residence r ";
		 
		List<Residence> list =getEntityManager().createQuery(jpql).setFirstResult(page.getFirstResultIndex()).setMaxResults(pageSize).getResultList();
		page.setRows(list);
		
		
	//	String sql="select  r.hid,r.balconySum,r.bedroomSum,r.livingRoomSum,r.wcSum "+
	//	", h.building,h.coveredArea,h.floor,h.layers,h.room,h.spaceArea  "+
	//	", h.direction_hdid,h.estate_areaId,h.mainOwner_oid,h.right_hrid,h.type_htid "+
	//	", hd.title directionTitle  "+
	//	", a.name areaName  "+
	//	", o.realName ownerName "+
	//	", hr.type hrType  "+
	//	", ht.title htTitle  "+
	//	"from Residence r join House h on h.hid = r.hid  "+
	//	"left join HouseDirection hd on hd.hdid = h.direction_hdid "+ 
	//	"left join Area a on a.areaId = h.estate_areaId  "+
	//	"left join Owner o on o.oid = h.mainOwner_oid = o.oid "+
	//	"left join HouseRight hr on hr.hrid = h.right_hrid  "+
	//	"left join HouseType ht on ht.htid = h.type_htid "; 
	//		
	//		Query query = getEntityManager().createNativeQuery(sql);
	//		List list1 = query.setFirstResult(page.getFirstResultIndex()).setMaxResults(pageSize).getResultList();
	//		page.setRows(list1);
	//		
		
		
		return page;
		
	}

	/**
	 * 拼凑 where 语句
	 * @param Residence
	 * @param cb
	 * @param tmp_residence
	 * @return
	 */
	private Expression<Boolean>  concatWheres(Residence Residence, CriteriaBuilder cb, Root<Residence> tmp_residence) {
		Predicate wheres = cb.isNotNull(tmp_residence);
		if(Residence != null){
//			if(StringUtils.isNotBlank(Residence.getResidencename())){
//				Predicate prd1 = cb.and(cb.like(tmp_residence.get(Residence_.residencename), "%"+Residence.getResidencename()+"%"));
//				wheres.getExpressions().add(prd1);
//			}
//			if(StringUtils.isNotBlank(Residence.getAddress())){
//				Predicate prd2 = cb.and(cb.equal(tmp_residence.get(Residence_.districtname),Residence.getDistrictname()));
//				wheres.getExpressions().add(prd2);
//			}
		}
		return wheres;
	}
	
	/**
	 * 通过id查选对象
	 */
	@Override
	public Residence getEntityById(String id) {
		return getEntityManager().find(Residence.class, id);
	}
	

	@Override
	public void save(Residence Residence) {
		getEntityManager().persist(Residence);
	}

	@Override
	public void update(Residence Residence) {
		getEntityManager().merge(Residence);//合并
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		String sql="delete from Residence _Residence where _Residence.rid = ?";
		getEntityManager().createNativeQuery(sql).executeUpdate();
	}
	

}
