package com.ecpss.mp.uc.service;

import java.util.List;

import com.ecpss.mp.entity.City;

public interface CityService  {
	public List<City> getCityList();
	public List<City> getCityByPage(Integer pageNo,Integer pageSize);
	public List<City> getCityById(String id);
	public void save(City city);
	public void update(City city);
	public void delete(String id);
}
