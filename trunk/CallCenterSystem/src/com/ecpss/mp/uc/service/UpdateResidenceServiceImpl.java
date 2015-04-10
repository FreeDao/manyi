package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.entity.House;
import com.ecpss.mp.entity.HouseDirection;
import com.ecpss.mp.entity.HouseType;
import com.ecpss.mp.entity.House_;
@Service(value = "updateResidenceService")
@Scope(value = "singleton")
public class UpdateResidenceServiceImpl extends BaseService implements UpdateResidenceService{
	@Override
	public void updateResdence(int hid, ResidenceInfo resdienveInfo) {
		// TODO Auto-generated method stub
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<House> cq = cb.createQuery(House.class);
		Root<House> root_hosut = cq.from(House.class);
		cq.where(cb.equal(root_hosut.get(House_.hid), hid));
		cq.select(root_hosut);
		
		List<House> housList = getEntityManager().createQuery(cq).getResultList();
		if(housList!=null){
			House house = housList.get(0);
			//修改house
			house.setBuilding(resdienveInfo.getBuilding());
			house.setFloor(resdienveInfo.getFloor());
			house.setRoom(resdienveInfo.getRoom());
			house.setLayers(resdienveInfo.getLayers());
			house.setCoveredArea(resdienveInfo.getCoveredArea());
			house.setSpaceArea(resdienveInfo.getSpaceArea());
			
			//修改房屋 的类型
			int typeId = resdienveInfo.getTypeId();
			HouseType houseType =getEntityManager().find(HouseType.class, typeId);
			house.setType(houseType);
			
			//修改房屋的朝向
			int directionId = resdienveInfo.getDirectionId();
			HouseDirection direction = getEntityManager().find(HouseDirection.class, directionId);
			house.setDirection(direction);
			
			//修改所属小区
			
			
			
			getEntityManager().merge(house);
			
		}
	}
}
