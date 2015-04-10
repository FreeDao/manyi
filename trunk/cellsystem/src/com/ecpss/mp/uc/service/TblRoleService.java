package com.ecpss.mp.uc.service;

import java.util.List;

import com.ecpss.mp.entity.TblRole;

public interface TblRoleService {

	void save(TblRole tblrole);

	void update(TblRole tblrole);

	void delete(String id);

	List<TblRole> getTblRoleById(String id);

	List<TblRole> getTblRoleByPage(Integer pageNo, Integer pageSize);

}
