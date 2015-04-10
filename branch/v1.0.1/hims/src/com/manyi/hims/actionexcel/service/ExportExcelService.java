/**
 * 
 */
package com.manyi.hims.actionexcel.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.manyi.hims.actionexcel.model.BDInfo;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.ExportInfo4User;
import com.manyi.hims.actionexcel.model.HourseHostInfo;
import com.manyi.hims.actionexcel.model.ExportUserInfo;

/**
 * @author zxc
 * 
 */
public interface ExportExcelService {

	public List<HourseHostInfo> getHourseHostInfoList(int _count);

	public boolean exportHourseHostInfo(HttpServletResponse response, List<HourseHostInfo> list);

	// 每个客服考核
	public List<CSInfo> getCSInfoList(String startTime,String endTime);

	// BD 手机号查询 数据查询
	public List<BDInfo> getBDInfoList(String mobile);

	// 反作弊 中介是房东
	public List<ExportInfo4User> getExportInfo4User(int temp);

	// 反作弊 房东多套房
	public List<ExportUserInfo> getExportUserInfo(int temp);
}
