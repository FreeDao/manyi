package com.ecpss.mp.uc.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.City;
import com.ecpss.mp.entity.City_;

@Service(value = "cityService")
@Scope(value = "singleton")
public class CityServiceImpl extends BaseService implements CityService {
	
	
	/**
	 * getCityList
	 * @return
	 */
	public List<City> getCityList(){
//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<City> cq = cb.createQuery(City.class);
//		cq.from(City.class);
//		return getEntityManager().createQuery(cq).getResultList();
		
		List list= getEntityManager().createNativeQuery("select * from city",City.class).getResultList();
		
		return list;
	}
	
	/**
	 * getCityList
	 * @returnr
	 */
	public List<City> getCityByPage(Integer pageNo,Integer pageSize){
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		cq.from(City.class);
		return getEntityManager().createQuery(cq).getResultList();
	}
	
	/**
	 * 通过id查选对象
	 */
	@Override
	public List<City> getCityById(String id) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<City> cq = cb.createQuery(City.class);
		Root<City> _city = cq.from(City.class);
		cq.where(cb.and(cb.equal(_city.get(City_.cityNo),id))).select(_city);
		
		return getEntityManager().createQuery(cq).getResultList();
	}
	

	@Override
	public void save(City city) {
		getEntityManager().persist(city);
	}

	@Override
	public void update(City city) {
		getEntityManager().merge(city);//合并
	}
	
	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		String sql="delete from City _city where _city.cityNo = ?";
		getEntityManager().createNativeQuery(sql).executeUpdate();
	}
	

}
