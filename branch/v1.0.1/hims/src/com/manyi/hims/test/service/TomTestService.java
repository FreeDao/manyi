package com.manyi.hims.test.service;

import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.Estate;

@Service(value = "tomTestService")
@Scope(value = "singleton")
public class TomTestService extends BaseService {

	/**
	 * @date 2014年4月27日 下午8:18:17
	 * @author Tom  
	 * @description  
	 * 
	 */
	private String getPcode(int code) {

		String s = Integer.toString(code);
		int index = s.length();
		for (int i = index; i < 5; i++) {
			s = "0" + s;
		}
		return s;
	}

	
	/**
	 * 一、
	 * 
	 * @date 2014年4月28日 下午10:26:46
	 * @author Tom  
	 * @description  
	 * 将原有address地址 改到 子小区下
	 */
	public void asdf() {
		Query query = this.getEntityManager().createQuery("from Estate");
		List<Estate> list = query.getResultList();
		
		//遍历主小区
		for(Estate estate : list) {
			
			//主小区 下地址
			Query q = this.getEntityManager().createNativeQuery("select address from t_address where estateId = ?1");
			q.setParameter(1, estate.getAreaId());
			List<Object> addressList = q.getResultList();
			
			
			
			Query q2 = this.getEntityManager().createNativeQuery("select areaid from Area where DTYPE = 'SubEstate' and parentId = ?1");
			q2.setParameter(1, estate.getAreaId());
			//主小区  子小区
			List<Object> areaList = q2.getResultList();
			
			
			//循环将子小区
			for (Object areaObj : areaList) {
				
				for (Object addressObj : addressList) {
					
					Address address = new Address();
					address.setAddress(addressObj.toString());
					address.setEstateId(Integer.parseInt(areaObj.toString()));
					this.getEntityManager().persist(address);
				}
			}
			
		}
		
	}
	
	
	
	
	/**
	 * 
	 * 二、
	 * 
	 * step 1:
	 * @date 2014年4月28日 下午10:38:05
	 * @author Tom  
	 * @description  
	 * 将子小区的parentId更换成主校区的parentId
	 */
//	step 2: delete from Area where dtype = 'Estate'
//	step 3:update Area set name = CONCAT(name,	road) where dtype = 'SubEstate'
//	step 4:update Area set dtype = 'Estate' where dtype = 'SubEstate'


	public void fsd() {
		Query query = this.getEntityManager().createNativeQuery("select areaid, parentId from Area where DTYPE = 'SubEstate'");
		List<Object[]> areaList = query.getResultList();
		
		//遍历子小区
		for (Object[] object : areaList) {
			
//			获取子小区的主校区，parentId
			Estate estate = this.getEntityManager().getReference(Estate.class, Integer.parseInt(object[1].toString()));
			
			Query q1 = this.getEntityManager().createNativeQuery("update Area set parentid = ?1 where areaid = ?2");
			q1.setParameter(1, estate.getParentId());
			q1.setParameter(2, object[0].toString());
			q1.executeUpdate();
		}
		
	
	}
	
	/**
	 * 三、
	 * 
	 * step 1
	 * @date 2014年4月27日 下午8:18:15
	 * @author Tom  
	 * @description  
	 * 生成Area表serialCode方法：
	 * 调用tomTestService.insertCod("", -1);
	 * 
	 * step 2
	 * update House h set h.serialCode = (select serialCode from Area where areaId =  h.estateId)
	 */
	public void insertCod(String pcode, int pid) {

		Query query = getEntityManager().createNativeQuery("select areaId from Area a where a.parentId = ?");
		query.setParameter(1, pid);

		List<Object[]> li = query.getResultList();
		if (li.size() == 0) {
			return;
		}
		for (int i = 1; i < li.size() + 1; i++) {
			Object areaId = li.get(i-1);

			Query q = getEntityManager().createNativeQuery("update Area set serialCode = ? where areaId = ?");
			q.setParameter(1, pcode + getPcode(i));
			q.setParameter(2, Integer.parseInt(areaId.toString()));
			q.executeUpdate();

			insertCod(pcode + getPcode(i), Integer.parseInt(areaId.toString()));
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}
