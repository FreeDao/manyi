package com.manyi.hims.test.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;

@Service(value = "testService")
@Scope(value = "singleton")
public class TestServiceImpl extends BaseService implements TestService {

	@Override
	public void sellExamineThrough(String[] houseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rentExamineThrough(String[] houseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addArea() {
		// TODO Auto-generated method stub
		
	}
	
//	@Override
//	public void sellExamineThrough(String[] sourceLogId) {
//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<SellLog> cq = cb.createQuery(SellLog.class);
//		Root<SellLog> root_log = cq.from(SellLog.class);
//		SourceInfo sourceInfo = null;
//		Residence residence = null;
//		SellLog sellLog =  null ;
//		for (int i = 0; i < sourceLogId.length; i++) {
//			cq.where(cb.and(cb.equal(root_log.get(SellLog_.sourceLogId), Integer.valueOf(sourceLogId[i])))).select(root_log);
//			sellLog = getEntityManager().createQuery(cq).getSingleResult();
//			sourceInfo = getEntityManager().find(SourceInfo.class, sellLog.getSellInfoId());
//			residence = getEntityManager().find(Residence.class, sourceInfo.getHouseId());
//			//修改SourceLog的审核状态
//			sellLog.setSourceState(0);
//			sellLog.setFinishDate(new Date());
//			getEntityManager().merge(sellLog);
//			//修改sourceInfo的价格
//			sourceInfo.setPrice(sellLog.getPrice());
//			getEntityManager().merge(sourceInfo);
//			//修改House的几房、几室、几卫、几阳台
//			residence.setBedroomSum(sellLog.getBedroomSum());
//			residence.setLivingRoomSum(sellLog.getLivingRoomSum());
//			residence.setWcSum(sellLog.getWcSum());
//			residence.setSpaceArea(sellLog.getSpaceArea());
//			getEntityManager().merge(residence);
//		}
//		
//		
//	}
//	@Override
//	public void rentExamineThrough(String[] sourceLogId) {
//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<RentLog> cq = cb.createQuery(RentLog.class);
//		Root<RentLog> root_log = cq.from(RentLog.class);
//		SourceInfo sourceInfo = null;
//		Residence residence = null;
//		RentLog rentlLog =  null ;
//		for (int i = 0; i < sourceLogId.length; i++) {
//			cq.where(cb.and(cb.equal(root_log.get(RentLog_.sourceLogId), Integer.valueOf(sourceLogId[i])))).select(root_log);
//			rentlLog = getEntityManager().createQuery(cq).getSingleResult();
//			sourceInfo = getEntityManager().find(SourceInfo.class, rentlLog.getRentInfoId());
//			residence = getEntityManager().find(Residence.class, sourceInfo.getHouseId());
//			//修改SourceLog的审核状态
//			rentlLog.setSourceState(0);
//			rentlLog.setFinishDate(new Date());
//			//getEntityManager().merge(rentlLog);
//			//修改sourceInfo的价格
//			sourceInfo.setPrice(rentlLog.getPrice());
//			getEntityManager().merge(sourceInfo);
//			//修改House的几房、几室、几卫、几阳台
//			residence.setBedroomSum(rentlLog.getBedroomSum());
//			residence.setLivingRoomSum(rentlLog.getLivingRoomSum());
//			residence.setWcSum(rentlLog.getWcSum());
//			residence.setSpaceArea(rentlLog.getSpaceArea());
//			getEntityManager().merge(residence);
//		}
//		
//	}
//	
//	@Override
//	public void addArea() {
//		County county = new County();
//		county.setName("中国");
//		county.setParentId(-1);
//		county.setCreateTime(new Date());
//		getEntityManager().persist(county);
//		
//		Province city = new Province();
//		city.setName("上海");
//		city.setCreateTime(new Date());
//		city.setParentId(county.getAreaId());
//		getEntityManager().persist(city);
//		
//		City t = new City();
//		t.setName("黄埔");
//		t.setCreateTime(new Date());
//		t.setParentId(city.getAreaId());
//		getEntityManager().persist(t);
//		
//		City t1 = new City();
//		t1.setName("卢湾");
//		t1.setCreateTime(new Date());
//		t1.setParentId(city.getAreaId());
//		getEntityManager().persist(t1);
//		
//		
//		City t2 = new City();
//		t2.setName("静安");
//		t2.setCreateTime(new Date());
//		t2.setParentId(city.getAreaId());
//		getEntityManager().persist(t2);
//		
//		City t3 = new City();
//		t3.setName("徐汇");
//		t3.setCreateTime(new Date());
//		t3.setParentId(city.getAreaId());
//		getEntityManager().persist(t3);
//		
//		City t4 = new City();
//		t4.setName("长宁");
//		t4.setCreateTime(new Date());
//		t4.setParentId(city.getAreaId());
//		getEntityManager().persist(t4);
//		
//		City t5 = new City();
//		t5.setName("虹口");
//		t5.setCreateTime(new Date());
//		t5.setParentId(city.getAreaId());
//		getEntityManager().persist(t5);
//		
//		City t6 = new City();
//		t6.setName("杨浦");
//		t6.setCreateTime(new Date());
//		t6.setParentId(city.getAreaId());
//		getEntityManager().persist(t6);
//		
//		City t7 = new City();
//		t7.setName("闸北");
//		t7.setCreateTime(new Date());
//		t7.setParentId(city.getAreaId());
//		getEntityManager().persist(t7);
//		
//		
//		City t8 = new City();
//		t8.setName("普陀");
//		t8.setCreateTime(new Date());
//		t8.setParentId(city.getAreaId());
//		getEntityManager().persist(t8);
//		
//		City t9 = new City();
//		t9.setName("浦东");
//		t9.setCreateTime(new Date());
//		t9.setParentId(city.getAreaId());
//		getEntityManager().persist(t9);
//		
//		City t10 = new City();
//		t10.setName("宝山");
//		t10.setCreateTime(new Date());
//		t10.setParentId(city.getAreaId());
//		getEntityManager().persist(t10);
//		
//		City t11 = new City();
//		t11.setName("闵行");
//		t11.setCreateTime(new Date());
//		t11.setParentId(city.getAreaId());
//		getEntityManager().persist(t11);
//		
//		City t12 = new City();
//		t12.setName("嘉定");
//		t12.setCreateTime(new Date());
//		t12.setParentId(city.getAreaId());
//		getEntityManager().persist(t12);
//		
//		City t13 = new City();
//		t13.setName("松江");
//		t13.setCreateTime(new Date());
//		t13.setParentId(city.getAreaId());
//		getEntityManager().persist(t13);
//		
//		City t14 = new City();
//		t14.setName("青浦");
//		t14.setCreateTime(new Date());
//		t14.setParentId(city.getAreaId());
//		getEntityManager().persist(t14);
//		
//		City t15 = new City();
//		t15.setName("奉贤");
//		t15.setCreateTime(new Date());
//		t15.setParentId(city.getAreaId());
//		getEntityManager().persist(t15);
//		
//		City t16 = new City();
//		t16.setName("金山");
//		t16.setCreateTime(new Date());
//		t16.setParentId(city.getAreaId());
//		getEntityManager().persist(t16);
//		
//		City t17 = new City();
//		t17.setName("崇明");
//		t17.setCreateTime(new Date());
//		t17.setParentId(city.getAreaId());
//		getEntityManager().persist(t17);
//		
//		
//	}
}
