package com.manyi.hims.actionexcel.controller;

/**
 * 
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.manyi.hims.BaseController;
import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.actionexcel.model.BDInfo;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.ExportInfo4User;
import com.manyi.hims.actionexcel.model.ExportSourceInfo;
import com.manyi.hims.actionexcel.model.ExportUserInfo;
import com.manyi.hims.actionexcel.model.HourseHostInfo;
import com.manyi.hims.actionexcel.model.MainRequest;
import com.manyi.hims.actionexcel.service.ExportExcelService;
import com.manyi.hims.actionexcel.service.MainService;
import com.manyi.hims.actionexcel.service.UserBankService;
import com.manyi.hims.util.ExcelUtils;
import com.manyi.hims.util.ExcelUtils.IExcel;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/action")
@SessionAttributes(Global.SESSION_UID_KEY)
public class MainExcelController extends BaseController {

	@Autowired
	@Qualifier("mainService")
	private MainService mainService;

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	@Autowired
	@Qualifier("exportExcelService")
	private ExportExcelService exportExcelService;

	@Autowired
	@Qualifier("userBankService")
	private UserBankService userBankService;

	public void setExportExcelService(ExportExcelService exportExcelService) {
		this.exportExcelService = exportExcelService;
	}

	final static String[] cs_title = { "客服ID", "客服实际名字", "客服昵称", "审核成功", "再审核", "审核失败", "总数" };
	final static String[] bd_title = { "BD名字", "创建时间", "UID", "中介名字", "中介手机", "中介上传出售总数", "中介上传出租总数", "中介人上传改盘总数", "中介人上传举报总数",
			"中介上传出售审核成功", "中介上传出租审核成功", "中介人上传改盘审核成功", "中介人上传举报审核成功", "总上传数", "总上传审核成功数" };
	final static String[] us_title = { "UID", "中介名字", "中介手机", "房东名字", "房东手机", "创建时间", "小区名字", "房屋地址", "楼座编号", "楼层", "房号" };
	final static String[] ur_title = { "房屋ID", "房东名字", "房东手机", "创建时间", "小区名字", "房屋地址", "楼座编号", "楼层", "房号" };

	/**
	 * CS绩效报表
	 */
	@RequestMapping(value = "/cSSearch", method = RequestMethod.POST)
	public Response cSSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		if (StringUtils.isEmpty(info.getEndCreateTime())) {
			returnMsg(response, "截至时间为空！");
			return null;
		}
		if (StringUtils.isEmpty(info.getBeginCreateTime())) {
			returnMsg(response, "起始时间为空！");
			return null;
		}
		List<CSInfo> list = this.exportExcelService.getCSInfoList(info.getBeginCreateTime(), info.getEndCreateTime());
		if (list == null || list.size() == 0) {
			returnMsg(response, "未查到数据！");
			return null;
		}
		ExcelUtils.exportExcel(response, list, "export-CSInfo-", new IExcel<CSInfo>() {

			@Override
			public void initHSSRow(List<CSInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					CSInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getEmployeeId() + "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRealName() + "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUserName() + "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getSuccess() + "");
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getIng() + "");
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getFaild() + "");
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAllCount() + "");
				}
			}

		}, cs_title);
		return null;
	}

	/**
	 * BD手机号查询报表
	 */
	@RequestMapping(value = "/bDRptSearch", method = RequestMethod.POST)
	public Response bDRptSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		// if (StringUtils.isEmpty(info.getMobile())) {
		// return new Response(1, "手机号为空！");
		// }
		List<BDInfo> list = this.exportExcelService.getBDInfoList(info.getMobile());
		if (list == null || list.size() == 0) {
			returnMsg(response, "未查到数据！");
			return null;
		}
		ExcelUtils.exportExcel(response, list, "export-BDRpt-", new IExcel<BDInfo>() {

			@Override
			public void initHSSRow(List<BDInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					BDInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getBdName() + "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getCreateTime() + "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUid() + "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRealName() + "");
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getMobile() + "");
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRentSum() + "");
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getSellSum() + "");
					cell = hssrow.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getModifySum() + "");
					cell = hssrow.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getReportSum() + "");
					cell = hssrow.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRentSec() + "");
					cell = hssrow.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getSellSec() + "");
					cell = hssrow.createCell(11);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getModifySec() + "");
					cell = hssrow.createCell(12);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getReportSec() + "");
					cell = hssrow.createCell(13);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAllSum() + "");
					cell = hssrow.createCell(14);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAllSec() + "");
				}
			}

		}, bd_title);
		return null;
	}

	/**
	 * 反作弊:中介是房东
	 */
	@RequestMapping(value = "/exportInfo4UserSearch", method = RequestMethod.POST)
	public Response exportInfo4UserSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		if (info.getNumber() == null) {
			returnMsg(response, "数字不能空！");
			return null;
		}
		List<ExportInfo4User> list = this.exportExcelService.getExportInfo4User(info.getNumber());
		if (list == null || list.size() == 0) {
			returnMsg(response, "未查到数据！");
			return null;
		}
		ExcelUtils.exportExcel(response, list, "export-exportInfo4User-", new IExcel<ExportInfo4User>() {

			@Override
			public void initHSSRow(List<ExportInfo4User> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					ExportInfo4User row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUid() + "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAgentName() + "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAgentMobile() + "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostName() + "");
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostMobile() + "");
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getCreateTime() + "");
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getEstateName() + "");
					cell = hssrow.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoad() + "");
					cell = hssrow.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getBuilding() + "");
					cell = hssrow.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getFloor() + "");
					cell = hssrow.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoom() + "");
				}
			}

		}, us_title);
		return null;
	}

	/**
	 * 反作弊:房东多套房
	 */
	@RequestMapping(value = "/exportUserInfoRptSearch", method = RequestMethod.POST)
	public Response exportUserInfoRptSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		if (info.getNumber() == null) {
			returnMsg(response, "数字不能空！");
			return null;
		}
		List<ExportUserInfo> list = this.exportExcelService.getExportUserInfo(info.getNumber());
		if (list == null || list.size() == 0) {
			returnMsg(response, "未查到数据！");
			return null;
		}
		ExcelUtils.exportExcel(response, list, "export-exportUserInfoRpt-", new IExcel<ExportUserInfo>() {

			@Override
			public void initHSSRow(List<ExportUserInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					ExportUserInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHouseId() + "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostName() + "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostMobile() + "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getCreateTime() + "");
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getEstateName() + "");
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoad() + "");
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getBuilding() + "");
					cell = hssrow.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getFloor() + "");
					cell = hssrow.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoom() + "");
				}
			}

		}, ur_title);
		return null;
	}

	/**
	 * 查询SourceHost,SourceInfo,SourceLog,User 并导出hostName,hostMobile,houseId,realName,mobile
	 * 
	 * @param request
	 * @param areaName
	 * @return
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/exportHourseHostInfo", method = RequestMethod.POST)
	@ResponseBody
	public Response exportHourseHostInfoExcel(HttpServletRequest request, HttpServletResponse response, ExportHourceHostInfo info) {
		List<HourseHostInfo> list = this.exportExcelService.getHourseHostInfoList(info.getHourceCount());
		boolean isSuccess = this.exportExcelService.exportHourseHostInfo(response, list);
		return null;
	}

	/**
	 * 对应的 小区信息导入到 数据库中(初始 信息)
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/improtInitEstate.rest")
	public void improtEstate(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {

		this.mainService.improtEstate2(file);
		try {
			// request.getRequestDispatcher("/test.html").forward(request, response);
			response.getWriter().print("finish");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 房源信息导入到数据库中(初始房源信息)
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/improtInitSourceInfo.rest", method = RequestMethod.POST)
	public void improtInitSourceInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {

		this.mainService.improtInitSourceInfo(file);

		try {
			// request.getRequestDispatcher("/test.html").forward(request, response);
			response.getWriter().print("finish");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 房源信息导入到数据库中
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/improtSourceInfo.rest", method = RequestMethod.POST)
	public void improtSourceInfo(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {

		this.mainService.improtSourceInfo(file);
		try {
			// request.getRequestDispatcher("/test.html").forward(request, response);
			response.getWriter().print("finish");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 导入 用户的关联 关系(通过邀请码推荐的)
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/importUserRelation", method = RequestMethod.POST)
	public void importUserRelation(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {

		String msg = this.userBankService.importUserRelation(file);
		returnMsg(response, msg);
	}

	private void returnMsg(HttpServletResponse response, String msg) {
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/plain; charset=utf-8");
			response.getWriter().print(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 房源信息经纪人 银行账号信息
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/improtUserBank", method = RequestMethod.POST)
	public void improtUserBank(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {

		this.userBankService.improtUserBank(file);
		try {
			response.getWriter().print("finish");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 导出房源信息 (客服需要验证 的数据)
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportSourceInfo", method = RequestMethod.POST)
	public String exportSourceInfo(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {

		// exportSourceInfo(Date start , Date end , int state,int type)
		// final int tmpNum = this.mainService.exportSourceInfo(response,info.getStart(),info.getEnd(),info.getState(),info.getType());

		return null;
	}

	/**
	 * 1.导出 经纪人 上传 房源数量 2.导出 经纪人 结算金额
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportUserNum", method = RequestMethod.POST)
	public String exportUserNum(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {

		// exportSourceInfo(Date start , Date end , int state,int type)
		int tmpNum = -1;
		// if(info.getType() == 2){
		// //导出 经纪人 结算金额
		// tmpNum = this.userBankService.exportUserBank(response,info.getStart(),info.getEnd());
		// }else{
		// //1.导出 经纪人 上传 房源数量
		// tmpNum = this.userBankService.exportUserNum(response,info.getStart(),info.getEnd());
		// }

		return null;
	}

	/**
	 * 导出 callcenter 某个时间段内 所有的人的 审核情况
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportUserCallCenterCheckNum")
	public String exportUserCallCenterCheckNum(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {
		Date t1 = null;
		Date t2 = null;
		try {
			if(info != null){
				if(info.getStart() != null)
				t1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info.getStart());
				if(info.getEnd() != null)
				t2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(info.getEnd());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		this.userBankService.exportUserCallCenterCheckNum(response, t1, t2);
		return null;
	}

	public static class ExportHourceHostInfo {
		private int hourceCount;

		public int getHourceCount() {
			return hourceCount;
		}

		public void setHourceCount(int hourceCount) {
			this.hourceCount = hourceCount;
		}
	}

}
