package com.ecpss.mp.uc.service;

import java.util.List;

import com.ecpss.mp.entity.TblOperator;

public interface TblOperatorService {

	void save(TblOperator operator);

	void update(TblOperator operator);

	void delete(String id);

	List<TblOperator> getTblOperatorById(String id);

	List<TblOperator> getTblOperatorByPage(Integer pageNo, Integer pageSize);

}
