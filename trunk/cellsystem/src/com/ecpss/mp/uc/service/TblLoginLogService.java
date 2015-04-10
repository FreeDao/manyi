package com.ecpss.mp.uc.service;

import java.util.List;

import com.ecpss.mp.entity.TblLogOperate;
import com.ecpss.mp.entity.TblLogOperatorlogin;

public interface TblLoginLogService {
	/**
	 * 查看用户登录日志
	 */
	public List<TblLogOperatorlogin> getLoginLog(); 
	
	/**
	 * 查看用户操作日志
	 */
	
	public List<TblLogOperate> getLogOperate();
}
