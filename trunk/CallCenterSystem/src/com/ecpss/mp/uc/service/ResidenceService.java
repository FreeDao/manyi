package com.ecpss.mp.uc.service;

import java.util.List;

import com.ecpss.mp.controller.ResidenceRestController.ResidenceInfo;
import com.ecpss.mp.entity.Residence;
import com.leo.common.Page;

public interface ResidenceService  {
	public List<Residence> getAllList();
	public Residence getEntityById(String id);
	public void save(Residence residence);
	public void update(Residence residence);
	public void delete(String id);
	public Page<Residence> getEntityByPage(ResidenceInfo residence, Integer pageNo, Integer pageSize, String sort, String order);
	
	public Page<ResidenceInfo> getResidencesInfoByPage(ResidenceInfo request, Integer pageNo, Integer pageSize, String sort, String order);
	
}
