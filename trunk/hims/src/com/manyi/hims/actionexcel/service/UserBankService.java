package com.manyi.hims.actionexcel.service;


import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.manyi.hims.actionexcel.model.BDWorkByDayModel;
import com.manyi.hims.actionexcel.model.ExportSourceInfo;


public interface UserBankService  {

	public int improtUserBank(MultipartFile excel);
	
	public int exportUserNum(HttpServletResponse response, Date start, Date end);

	public int exportUserBank(HttpServletResponse response, Date start, Date end);
	
	/**
	 * 导入 用户的关联 关系(通过邀请码推荐的)
	 * @param excel
	 * @return
	 */
	public String importUserRelation(MultipartFile excel);

	/**
	 * 导出 callcenter 某个时间段内 所有的人的 审核情况
	 * @param response
	 * @param start
	 * @param end
	 * @return
	 */
	public int exportUserCallCenterCheckNum(HttpServletResponse response, Date start, Date end);

	/**
	 *  导出 callcenter 导出房源运营总表
	 * @param response
	 * @param info
	 */
	public void exportHouseOperation(HttpServletResponse response, ExportSourceInfo info);

	/**
	 * 导出 callcenter 财务运营总表
	 * @param response
	 * @param info
	 */
	public void exportFinanceOperation(HttpServletResponse response, ExportSourceInfo info);

	/**
	 *  导出 callcenter BD日常工作报表
	 * @param response
	 * @param info
	 */
	public Map<String, BDWorkByDayModel> exportBdWorkByDay( ExportSourceInfo info);

}