package com.ecpss.mp.uc.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ecpss.mp.BaseService;
import com.ecpss.mp.entity.TblPermissiongroup;
@Service(value = "tblPermissiongroupService")
@Scope(value = "singleton")
public class TblPermissiongroupServiceImpl extends BaseService implements  TblPermissiongroupService{

	@Override
	public void save(TblPermissiongroup tblpermissiongroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(TblPermissiongroup tblpermissiongroup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

}
