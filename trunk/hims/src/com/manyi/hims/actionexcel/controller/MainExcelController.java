package com.manyi.hims.actionexcel.controller;

/**
 * 
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.manyi.hims.actionexcel.model.BDWorkByDayModel;
import com.manyi.hims.actionexcel.model.BDWorkCountModel;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.ExportInfo4User;
import com.manyi.hims.actionexcel.model.ExportSourceInfo;
import com.manyi.hims.actionexcel.model.ExportUserInfo;
import com.manyi.hims.actionexcel.model.HourseHostInfo;
import com.manyi.hims.actionexcel.model.MainRequest;
import com.manyi.hims.actionexcel.model.User2infoRequest;
import com.manyi.hims.actionexcel.model.UserAliPayExcelModel;
import com.manyi.hims.actionexcel.model.UserExcelRequest;
import com.manyi.hims.actionexcel.model.UserPublishExcelModel;
import com.manyi.hims.actionexcel.model.UserRegisterExcelModel;
import com.manyi.hims.actionexcel.service.ExportExcelService;
import com.manyi.hims.actionexcel.service.HouseActionService;
import com.manyi.hims.actionexcel.service.MainService;
import com.manyi.hims.actionexcel.service.UserBankService;
import com.manyi.hims.actionexcel.service.UserExcelService;
import com.manyi.hims.util.EntityUtils;
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
	
	@Autowired
	@Qualifier("userExcelService")
	private UserExcelService userExcelService;

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}

	@Autowired
	@Qualifier("exportExcelService")
	private ExportExcelService exportExcelService;
	
	@Autowired
	@Qualifier("userBankService")
	private UserBankService userBankService;

	@Autowired
	@Qualifier("houseActionService")
	private HouseActionService houseActionService;

	public void setExportExcelService(ExportExcelService exportExcelService) {
		this.exportExcelService = exportExcelService;
	}

	final static String[] cs_title = { "客服ID", "客服实际名字", "客服昵称", "审核成功", "再审核", "审核失败", "总数" };
	final static String[] bd_title = { "BD名字", "创建时间", "UID", "中介名字", "中介手机", "中介上传出售总数", "中介上传出租总数", "中介人上传改盘总数", "中介人上传举报总数",
			"中介上传出售审核成功", "中介上传出租审核成功", "中介人上传改盘审核成功", "中介人上传举报审核成功", "总上传数", "总上传审核成功数" };
	final static String[] us_title = { "房源ID", "小区名", "栋", "室", "房屋当前租售状态", "房东电话", "名字", "中介电话", "发布日期", "发布任务类型", "审核状态" };
	final static String[] ur_title = { "房屋ID", "小区名", "栋座", "室号", "租售状态", "房东电话", "经纪人姓名", "经纪人电话", "发布时间","发布类型","审核状态" ,"工作区域"};

	/**
	 * CS绩效报表
	 */
	@RequestMapping(value = "/cSSearch", method = RequestMethod.POST)
	public Response cSSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		if (StringUtils.isEmpty(info.getEndDate())) {
			returnMsg(response, "截至时间为空！");
			return null;
		}
		if (StringUtils.isEmpty(info.getStartDate())) {
			returnMsg(response, "起始时间为空！");
			return null;
		}
		List<CSInfo> list = this.exportExcelService.getCSInfoList(info.getStartDate(), info.getEndDate());
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
	public Response exportInfo4UserSearch(HttpServletRequest request, HttpServletResponse response, User2infoRequest info) {
//		if (info.getNumber() == null) {
//			returnMsg(response, "数字不能空！");
//			return null;
//		}
		List<ExportInfo4User> list = this.exportExcelService.getExportInfo4User(info);
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
					cell.setCellValue(row.getHouseId() + "");
					
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getEstateName() + "");
					
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getBuilding() + "");
					
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoom() + "");
					
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getCurrentHouseState() + "");
					
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostMobile() + "");
					
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAgentName() + "");
					
					cell = hssrow.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getAgentMobile() + "");
					
					cell = hssrow.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getPublishDate() + "");
					
					cell = hssrow.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getPublishHouseState() + "");
					
					cell = hssrow.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getState() + "");
				}
			}

		}, us_title);
		return null;
	}

	/**
	 * 反作弊:房东多套房
	 * 传递进来的参数 
	 * {
	 * 	1.选择房东手机号重复次数大于N（N大于等于2）
	 *  2.选择城市（默认上海）
	 *  3.选择发布时间（时间范围）
	 * }
	 */
	@RequestMapping(value = "/exportUserInfoRptSearch", method = RequestMethod.POST)
	public Response exportUserInfoRptSearch(HttpServletRequest request, HttpServletResponse response, MainRequest info) {
		if (info.getNumber() == null) {
			returnMsg(response, "数字不能空！");
			return null;
		}
		List<ExportUserInfo> list = this.exportExcelService.getExportUserInfo(info);
		if (list == null || list.size() == 0) {
			returnMsg(response, "未查到数据！");
			return null;
		}
		ExcelUtils.exportExcel(response, list, "export_exportUserInfoRpt_", new IExcel<ExportUserInfo>() {

			@Override
			public void initHSSRow(List<ExportUserInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int j = 0; j < list.size(); j++) {
					ExportUserInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHouseId());
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getEstateName());
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getBuilding());
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRoom());
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(EntityUtils.HouseStateEnum.getByValue(row.getHouseState()).getDesc());
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostMobile() + "");
					cell = hssrow.createCell(6);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUserName());
					cell = hssrow.createCell(7);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUserMobile()+"");
					cell = hssrow.createCell(8);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(sdf.format(row.getPublishDate()));
					cell = hssrow.createCell(9);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(EntityUtils.ActionTypeEnum.getByValue(row.getActionType()).getDesc());
					cell = hssrow.createCell(10);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(EntityUtils.StatusEnum.getByValue(row.getStatus()).getDesc());
					cell = hssrow.createCell(11);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getUserAreaStr());
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
	
	/**
	 * 导入 子划分
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/importSubEstate", method = RequestMethod.POST)
	public void importSubEstate(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {
		String msg = this.houseActionService.importSubEstate(file);
		returnMsg(response, msg);
	}
	
	/* 导入 楼栋
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/importBilding", method = RequestMethod.POST)
	public void importBilding(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {
		String msg = this.houseActionService.importBilding(file);
		returnMsg(response, msg);
	}
	 /* 导入 房屋 
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/importHouse", method = RequestMethod.POST)
	public void importHouse(HttpServletRequest request, HttpServletResponse response, @RequestParam("excel") MultipartFile file) {
		String msg = this.houseActionService.importHouse(file);
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
	
	/**
	 * 导出 callcenter 导出房源运营总表
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportHouseOperation")
	public String exportHouseOperation(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {
		Date t1 = null;
		Date t2 = null;
		try {
			if(info != null){
				if(info.getStart() != null && !"".equals(info.getStart())){
					t1 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getStart());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -7);//前7天
					t1 = cal.getTime();
				}
				if(info.getEnd() != null && !"".equals(info.getEnd())){
					t2 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getEnd());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, 1);//
					t2 = cal.getTime();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		info.setT1(t1);
		info.setT2(t2);
		
		this.userBankService.exportHouseOperation(response, info);
		return null;
	}
	
	/**
	 * 导出 callcenter BD日常工作报表
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportBdWorkByDay")
	public String exportBdWorkByDay(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {
		Date t1 = null;
		Date t2 = null;
		try {
			if(info != null){
				if(info.getStart() != null && !"".equals(info.getStart())){
					t1 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getStart());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, -7);//前7天
					t1 = cal.getTime();
				}
				if(info.getEnd() != null && !"".equals(info.getEnd())){
					t2 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getEnd());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, 1);//
					t2 = cal.getTime();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		info.setT1(t1);
		info.setT2(t2);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String xlsName = "export_BDWorkByDay_" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
		
		StringBuffer msg = new StringBuffer(sdf.format(new Date()) + " 导出 callcenter BD日常工作报表   搜索条件  start: "
				+ (info.getT1() == null ? "" : sdf.format(info.getT1())) + " end: " + (info.getT2() == null ? "" : sdf.format(info.getT2())) +
				 "行政区: "+info.getAreaId() +" 城市 : "+ info.getCityType() +"\n\r");
		
		Map<String, BDWorkByDayModel> works = this.userBankService.exportBdWorkByDay(info);
		if(works != null && works.size()>0){
			
			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			workbook.setSheetName(0, xlsName);

			// 设置 表格中 每一列的宽度
			sheet.setColumnWidth(0, 20 * 256);
			sheet.setColumnWidth(1, 15 * 256);
			sheet.setColumnWidth(2, 15 * 256);
			sheet.setColumnWidth(3, 15 * 256);
			sheet.setColumnWidth(4, 15 * 256);
			sheet.setColumnWidth(5, 15 * 256);
			sheet.setColumnWidth(6, 15 * 256);
			sheet.setColumnWidth(7, 15 * 256); 
			sheet.setColumnWidth(8, 15 * 256);
			sheet.setColumnWidth(9, 15 * 256);
			sheet.setColumnWidth(10, 15 * 256);
			sheet.setColumnWidth(11, 15 * 256);
			sheet.setColumnWidth(12, 15 * 256);
			sheet.setColumnWidth(13, 15 * 256);
			
			Iterator<String> iterator1= works.keySet().iterator();
			int i =0;
			while (iterator1.hasNext()) {
				String k = iterator1.next();
				BDWorkByDayModel m =works.get(k);
				String id = m.getBdId();
				String name = m.getBdName();
				String mobile = m.getMobile();
				Iterator<String> iterator2= m.getWorks().keySet().iterator();
				HSSFRow row1= sheet.createRow(0+i);
				
				HSSFCell cell = row1.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(name+"-"+mobile);
				
				HSSFRow row2= sheet.createRow(++i);
				cell = row2.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("-直接推广");
				
				HSSFRow row3= sheet.createRow(++i);
				cell = row3.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue("-间接推广");
				i++;
				i++;//中间 空一行
				int j =1;
				while (iterator2.hasNext()) {
					String day = iterator2.next();
					BDWorkByDayModel  m2 = m.getWorks().get(day);
					
					cell = row1.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(m2.getDay());
					
					cell = row2.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(m2.getCurrNum()+"");
					
					cell = row3.createCell(j);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(m2.getOldNum()+"");
					
					j++;
				}
			}
			responseXLS(xlsName + ".xls",response,workbook);
		}else{
			returnMsg(response, "没有满足条件的内容.");
		}
		
		return null;
	}
	
	/**
	 * 导出 callcenter 财务运营总表
	 * 
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportFinanceOperation")
	public String exportFinanceOperation(HttpServletRequest request, HttpServletResponse response, ExportSourceInfo info) {
		Date t1 = null;
		Date t2 = null;
		try {
			if(info != null){
				if(info.getStart() != null && !"".equals(info.getStart())){
					t1 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getStart());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_WEEK, -7);//前7天
					t1 = cal.getTime();
				}
				if(info.getEnd() != null && !"".equals(info.getEnd())){
					t2 = new SimpleDateFormat("yyyy-MM-dd").parse(info.getEnd());
				}else{
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_MONTH, 1);//
					t2 = cal.getTime();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		info.setT1(t1);
		info.setT2(t2);

		this.userBankService.exportFinanceOperation(response, info);
		return null;
	}
	
	
	@RequestMapping(value = "/getAliExcel")
	@ResponseBody
	public String getAliExcel(HttpServletRequest request, HttpServletResponse response ) {
		UserAliPayExcelModel uape = userExcelService.getAliExcel();
		String arr[] = {"截止当前时间","支付宝账号绑定数目","支付宝账号未绑定数目"};
		HSSFWorkbook hssf = writeExcelFirst(arr,false);
		HSSFSheet sheet = hssf.getSheetAt(0);
		HSSFCell cellTemp = null;
		int i = 0;
		
		cellTemp = sheet.getRow(i++).createCell(1);
		cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellTemp.setCellValue(uape.getDate());
		
		cellTemp = sheet.getRow(i++).createCell(1);
		cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellTemp.setCellValue(uape.getHadBoundNum());
		
		cellTemp = sheet.getRow(i++).createCell(1);
		cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
		cellTemp.setCellValue(uape.getNotBoundNum());
		
		responseXLS("userAliExcel" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xls",response,hssf);
		return null;
	}
	
	@RequestMapping(value = "/getPublishExcel")
	@ResponseBody
	public String getPublishExcel(HttpServletRequest request, HttpServletResponse response ,UserExcelRequest ue) {
		if (StringUtils.isBlank(ue.getStartDate()) && StringUtils.isBlank(ue.getEndDate())) {
			Calendar currentDate = new GregorianCalendar(); 
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			System.out.println("time:" + currentDate.getTime());
			Date endStart = currentDate.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ue.setEndDate(sdf.format(endStart));
			ue.setStartDate(sdf.format(new Date(endStart.getTime() - 6 * 24 * 60 * 60 * 1000)));
		}
		if (StringUtils.isBlank(ue.getStartDate()) || StringUtils.isBlank(ue.getEndDate())) {
			return "bad range";
		}
		
		List<UserPublishExcelModel> list = null;
		try {
			list = userExcelService.getPublishExcel(ue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null) {
			 return "null exception";
		}
		String arr[] = {"日期","1套","2-10套","11-50套","51-100套","101套"};
		HSSFWorkbook hssf = writeExcelFirst(arr,true);
		HSSFSheet sheet = hssf.getSheetAt(0);
		HSSFCell cellTemp = null;
		for (int j = 0; j < list.size(); j++) {
			int i = 0;
			cellTemp = sheet.createRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getDate());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getH1());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getH2to10());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getH11to50());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getH51to100());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getH101());
			
		}
		responseXLS("userPublish" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xls",response,hssf);
		return null;
	}
	
	@RequestMapping(value = "/getRegisterExcel")
	@ResponseBody
	public String getRegisterExcel(HttpServletRequest request, HttpServletResponse response ,UserExcelRequest ue) {
		
		
		if (StringUtils.isBlank(ue.getStartDate()) && StringUtils.isBlank(ue.getEndDate())) {
			Calendar currentDate = new GregorianCalendar(); 
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.SECOND, 0);
			System.out.println("time:" + currentDate.getTime());
			Date endStart = currentDate.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			ue.setEndDate(sdf.format(endStart));
			ue.setStartDate(sdf.format(new Date(endStart.getTime() - 6 * 24 * 60 * 60 * 1000)));
		}
		if (StringUtils.isBlank(ue.getStartDate()) || StringUtils.isBlank(ue.getEndDate())) {
			return "bad range";
		}
		
		
		
		List<UserRegisterExcelModel> list = null;
		
		try {
			list = userExcelService.getRegisterExcel(ue);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list == null) {
			 return "null exception";
		}
		writeExcelRegister(list,"userRegister" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xls",response);
		return null;
	}
	
	private void  writeExcelRegister(List<UserRegisterExcelModel> list,String xlsName,HttpServletResponse response) {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
		HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
		workbook.setSheetName(0, "default");
		//sheet.setColumnWidth(0, 20 * 256);
		String [] arr ={"日期", "总注册数","审核成功数","BD推广","中介推广","自然注册","审核失败数","成功率","发布人数","举报人数"};
		int size = arr.length;
		for (int i = 0; i < size; i++) {
			HSSFCell cellTemp = sheet.createRow(i).createCell(0);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(arr[i]);
		}
		
		HSSFCell cellTemp = null;
		for (int j = 0; j < list.size(); j++) {
			int i = 0;
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getDate());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getTotal());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getSuccess());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getBDRecommendNum());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getZJRecommendNum());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getNOTRecommendNum());
			
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getFail());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			DecimalFormat df = new DecimalFormat("0.00%");
			df.format(list.get(j).getRate());
			cellTemp.setCellValue(df.format(list.get(j).getRate()));
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getUserPublishNum());
			
			cellTemp = sheet.getRow(i++).createCell(j + 1);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getUserReportedNum());
		}
		
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
			// 写入到 客户端response
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * true为写一行标题
	 * false为写一列标题
	 * @param arr
	 * @param ifRow
	 * @return
	 */
	private HSSFWorkbook  writeExcelFirst(String [] arr,boolean ifRow) {
		HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
		HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
		workbook.setSheetName(0, "default");
		sheet.setColumnWidth(0, 20 * 256);
		int size = arr.length;
		for (int i = 0; i < size; i++) {
			if (ifRow) {
				HSSFCell cellTemp = null;
				if(i == 0){
					cellTemp = sheet.createRow(0).createCell(i);
				}else {
					cellTemp = sheet.getRow(0).createCell(i);
				}
				cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
				cellTemp.setCellValue(arr[i]);
			}else {
				HSSFCell cellTemp = sheet.createRow(i).createCell(0);
				cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
				cellTemp.setCellValue(arr[i]);
			}
			
		}
		return workbook;
	}
	/**
	 * BD工作统计报表
	 * @param xlsName
	 * @param response
	 * @param workbook
	 */
	@RequestMapping(value = "/getBDWorkExcel")
	@ResponseBody
	public String getBDWorkExcel(HttpServletRequest request, HttpServletResponse response ,UserExcelRequest ue) {
//		if (StringUtils.isBlank(ue.getStartDate()) && StringUtils.isBlank(ue.getEndDate())) {
//			Calendar currentDate = new GregorianCalendar(); 
//			currentDate.set(Calendar.HOUR_OF_DAY, 0);
//			currentDate.set(Calendar.MINUTE, 0);
//			currentDate.set(Calendar.SECOND, 0);
//			System.out.println("time:" + currentDate.getTime());
//			Date endStart = currentDate.getTime();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			ue.setEndDate(sdf.format(endStart));
//			ue.setStartDate(sdf.format(new Date(endStart.getTime() - 6 * 24 * 60 * 60 * 1000)));
//		}
//		if (StringUtils.isBlank(ue.getStartDate()) || StringUtils.isBlank(ue.getEndDate())) {
//			return "bad range";
//		}
		
		List<BDWorkCountModel> list = null;
		list = exportExcelService.getBDWorkCount(ue.getAreaId(), ue.getCityId(), ue.getStartDate(),ue.getEndDate());
		if (list == null) {
			 return "null exception";
		}
		String arr[] = {"姓名","直接推广","间接推广","活跃中介","发布出售量","发布出租量"};
		HSSFWorkbook hssf = writeExcelFirst(arr,true);
		HSSFSheet sheet = hssf.getSheetAt(0);
		HSSFCell cellTemp = null;
		for (int j = 0; j < list.size(); j++) {
			int i = 0;
			cellTemp = sheet.createRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getName());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getDirectMarket());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getIndirectPromotion());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getActiveUser());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getCreateSellCount());
			
			cellTemp = sheet.getRow(j + 1).createCell(i++);
			cellTemp.setCellType(HSSFCell.CELL_TYPE_STRING);
			cellTemp.setCellValue(list.get(j).getCreateRentCount());
			
		}
		responseXLS("BDWorkCont" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".xls",response,hssf);
		return null;
	}
	
	private void responseXLS(String xlsName , HttpServletResponse response, HSSFWorkbook workbook) {
		try {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
			// 写入到 客户端response
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
