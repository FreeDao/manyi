package com.manyi.hims.actionexcel.controller;

/**
 * 
 */

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Data;

import org.springframework.beans.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.async.DeferredResult;

import com.manyi.hims.Global;
import com.manyi.hims.Response;
import com.manyi.hims.RestController;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.CustomerWorkInfo;
import com.manyi.hims.actionexcel.model.OperationsInfo;
import com.manyi.hims.actionexcel.service.MainService;
import com.manyi.hims.employee.model.EmployeeModel;
import com.manyi.hims.employee.model.EmployeeResponse;
import com.manyi.hims.test.service.TestService;
import com.manyi.hims.util.ExcelUtils;
import com.manyi.hims.util.ExcelUtils.IExcel;

/**
 * @author lei
 * 
 */
@Controller
@RequestMapping("/rest/action")
@SessionAttributes(Global.SESSION_UID_KEY)
public class MainExcelRestController extends RestController {
	
	@Autowired
	@Qualifier("mainService")
	private MainService mainService;
	
	@Autowired
	private TestService testService;
	
	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	public static class ExportSourceInfo{
		private Date start ;
		private Date end ;
		private int state;
		private int type;
		
		public Date getStart() {
			return start;
		}
		public void setStart(Date start) {
			this.start = start;
		}
		public Date getEnd() {
			return end;
		}
		public void setEnd(Date end) {
			this.end = end;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
	}
	
	
	/**
	 *
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/checkUser.rest", produces = "application/json")
	@ResponseBody
	public Response checkUser(HttpServletRequest request,String mobile) {
		
		final int tmpNum = this.mainService.checkUser(mobile);
		
		return new Response(){};
	}
	
	@RequestMapping(value = "/importEstate.rest", produces="application/json")
	@ResponseBody
	public DeferredResult<Response> addArea() {//@RequestBody ImportEstate params

		//testService.addArea();
		
		//String url = "/root/excel/";
		String url = "C:/Users/fangyouhui/Desktop/北京Area/北京/xls/";
		
		
		List<String> list = new ArrayList<>();
//		list.add("1朝阳1609.xls");
//		list.add("2海定1687.xls");
//		list.add("3丰台1010.xls");
//		list.add("4东城483.xls");
//		list.add("5西城638.xls");
//		list.add("6崇文239.xls");
//		list.add("7宣武498.xls");
//		list.add("8石景山253.xls");
//		list.add("9昌平510.xls");
//		list.add("10大兴420.xls");
//		list.add("11通州489.xls");
//		list.add("12顺义231.xls");
//		list.add("13房山360.xls");
//		list.add("14密云149.xls");
//		list.add("15门头沟174.xls");
//		list.add("16怀柔109.xls");
//		list.add("17延庆35.xls");
//		list.add("18平谷76.xls");
		list.add("19beijingzhoubian.xls");
		
		for (String s : list) {
			System.out.println("start ::: " + s);
			mainService.improtArea(url + s);
		}
		
		
		
		
		DeferredResult<Response> dr = new DeferredResult<Response>();
		dr.setResult(new Response());
		return dr;
	}
	
	
	/**
	 *导出房源信息 (客服需要验证 的数据)
	 * @param request
	 * @param filePath
	 * @return
	 */
	@RequestMapping(value = "/exportSourceInfo.rest", produces = "application/json")
	@ResponseBody
	public Response exportSourceInfo(HttpServletRequest request,ExportSourceInfo info) {
		
		//exportSourceInfo(Date start , Date end , int state,int type)
		final int tmpNum = this.mainService.exportSourceInfo(info.getStart(),info.getEnd(),info.getState(),info.getType());
		
		return new Response(){
			private int num;
			{
				this.num=tmpNum;
				if(tmpNum == 1){
					this.setMessage("路径不能为空.");
				}
			}
			public int getNum() {
				return num;
			}
			public void setNum(int num) {
				this.num = num;
			}
			
		};
	}
	
	
	/**
	 * 对应的 区域的数据导入到excel中
	 * @param request
	 * @param areaName
	 * @return
	 */
	@RequestMapping(value = "/export.rest", produces = "application/json")
	@ResponseBody
	public Response exportExcel(HttpServletRequest request,String areaName) {
		
		final String path = this.mainService.exportToExcel(request.getServletContext(),areaName);
		
		return new Response(){
			{
				if(path != ""){
					this.setMessage(path);
				}else{
					this.setMessage("导出失败!");
				}
			}
		};
	}
	
	
	
	/**
	 * 根据 类型 加载  不同的 数据字典 数据字典
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/loadlist.rest", produces = "application/json")
	@ResponseBody
	public Response load_list(HttpServletRequest request, ListPars pars) {
		Map<String,List>  map= (Map<String, List>) request.getServletContext().getAttribute("appconfigs");
		final List obj = 	map.get(pars.getListName());
		return new Response(){
			List rows = new ArrayList();
			{
				//BeanUtils.copyProperties(this, obj);
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
			
		};
	}
	
	/**
	 * 根据 类型 加载  不同的 数据字典 数据字典
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/loadarealist.rest", produces = "application/json")
	@ResponseBody
	public Response load_list2(HttpServletRequest request, ListPars pars) {
		final List obj = 	this.mainService.loadArae(pars.listName, pars.parentId);
		return new Response(){
			List rows = new ArrayList();
			{
				//BeanUtils.copyProperties(this, obj);
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
			
		};
	}
	
	/**
	 * 客服工作量
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/customerWork.rest", produces = "application/json")
	@ResponseBody
	public Response customerWork(HttpServletRequest request,CustomerWorkInfo workInfo) {
		String publishDate = workInfo.getPublishDateStr();
		if(publishDate==null || "".equals(publishDate)) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			publishDate = sdf.format(date);
		}
		final List obj = 	this.mainService.customerWork(publishDate);
		return new Response(){
			List rows = new ArrayList();
			{
				this.rows = obj;
			}
			public List getRows() {
				return rows;
			}
			public void setRows(List list) {
				this.rows = list;
			}
			
		};
	}
	final static String[] bd_title = { "用户名", "手机号", "中介人新增", "中介人审核中"};
	/**
	 * BD运营报表
	 * @param session
	 * @param pars
	 * @return
	 */
	@RequestMapping(value = "/bDOperations.rest", produces = "application/json")
	@ResponseBody
	public Response bDOperations(HttpServletRequest request, HttpServletResponse response,OperationsInfo info) {
		final List<OperationsInfo> list = 	this.mainService.bDOperations(response,info);
		if (list == null || list.size() == 0) {
			return new Response(1, "未查到数据！");
		}
		ExcelUtils.exportExcel(response, list, "export-bDOperations-", new IExcel<OperationsInfo>() {
			@Override
			public void initHSSRow(List<OperationsInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					OperationsInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRealName()+ "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getMobile()+ "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getTotalNum()+ "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getVerifyNum() + "");
				}
			}
		}, bd_title);
		return null;
	}
	public static class ListPars{
		private String listName ;
		private String parentId;
		public String getListName() {
			return listName;
		}
		public void setListName(String listName) {
			this.listName = listName;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		
	}
	@Data
	public static class ImportEstate{
		private String filePath;
	}

}
