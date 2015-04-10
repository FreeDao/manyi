package com.manyi.hims.actionexcel.service;

import java.text.ParseException;
import java.util.List;

import com.manyi.hims.actionexcel.model.BDWorkDailyModel;
import com.manyi.hims.actionexcel.model.UserAliPayExcelModel;
import com.manyi.hims.actionexcel.model.UserExcelRequest;
import com.manyi.hims.actionexcel.model.UserPublishExcelModel;
import com.manyi.hims.actionexcel.model.UserRegisterExcelModel;


public interface UserExcelService {
	
	public UserAliPayExcelModel getAliExcel();
	
	public List<UserPublishExcelModel> getPublishExcel(UserExcelRequest ue) throws ParseException;
	
	public List<UserRegisterExcelModel> getRegisterExcel(UserExcelRequest ue) throws ParseException;

	public BDWorkDailyModel getBDWorkDaily(UserExcelRequest ue) throws ParseException;

}
