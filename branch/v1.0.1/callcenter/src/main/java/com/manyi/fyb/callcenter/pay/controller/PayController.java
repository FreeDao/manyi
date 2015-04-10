package com.manyi.fyb.callcenter.pay.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.manyi.fyb.callcenter.base.controller.BaseController;
import com.manyi.fyb.callcenter.employee.model.EmployeeModel;
import com.manyi.fyb.callcenter.pay.model.ImportPayReq;
import com.manyi.fyb.callcenter.pay.model.PayReq;
import com.manyi.fyb.callcenter.pay.model.PayRes;
import com.manyi.fyb.callcenter.pay.model.PayResPage;
import com.manyi.fyb.callcenter.utils.ActionExcel;
import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.HttpClientHelper;

@Controller
@RequestMapping("/pay")
public class PayController extends BaseController {
	
	Logger log = Logger.getLogger(PayController.class);
	/**
	 * 跳转到 对应的 审核 列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/payGrid")
	private String index(HttpServletRequest request ){
		return "pay/payGrid";
	}
	
	/**
	 * 列表页面
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/payList")
	@ResponseBody
	public String list(PayReq req,HttpServletRequest request ) {
		
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/pay/payList.rest",req);
		return jobj.toString();
	}
	
	
	/**
	 * 导出  数据信息 前 统计 是否存在 需要导出的 数据
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/exportExcelCount")
	@ResponseBody
	public String exportExcelCount(PayReq req, HttpServletRequest request, HttpServletResponse response) {
		req.setIfExport(true);// 导出数据
		req.setPage(0);
		req.setRows(0);
		// 添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/pay/payList.rest", req);
		PayResPage page = (PayResPage) JSONObject.toBean(jobj, PayResPage.class);
		if (page != null && page.getRows() != null && page.getRows().size() > 0) {
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 导出  数据信息
	 * @param session
	 * @param req
	 * @return
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/exportExcel")
	@ResponseBody
	public String exportExcel(PayReq req,HttpServletRequest request ,HttpServletResponse response) {
		req.setIfExport(true);//导出数据
		req.setPage(0);
		req.setRows(0);
		//添加参数
		JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/pay/payList.rest",req);
		PayResPage page = (PayResPage) JSONObject.toBean(jobj,PayResPage.class);
		if(page != null && page.getRows() != null && page.getRows().size()>0){
			if( jobj.containsKey("rows") && jobj.getJSONArray("rows") != null){
				page.setRows(JSONArray.toList(jobj.getJSONArray("rows"), PayRes.class));
			}
			log.info("开始导出支付数据..");
			String xlsName ="export-usermoney-"+(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象

			//设置 表格中 每一列的宽度 
			sheet.setColumnWidth(0, 20*256);
			sheet.setColumnWidth(1, 30*256);
			sheet.setColumnWidth(2, 20*256);
			sheet.setColumnWidth(3, 30*256);
			sheet.setColumnWidth(4, 10*256);
			sheet.setColumnWidth(5, 10*256);
			
			//创建一行
			HSSFRow row= sheet.createRow(0);
			HSSFCell cell = row.createCell(0);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 往第一个单元格中写入信息
			cell.setCellValue("批次号");
			
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("付款日期");
			 cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("付款人Email");
			 cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("账户名称");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总金额(元)");
			 cell = row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总笔数");
			
			row=sheet.createRow(1);//创建第二行数据
			cell = row.createCell(0);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			
			String pici = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			cell.setCellValue(pici);
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("meihong@fyb365.com");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("上海房友宝商务咨询有限公司");
			cell = row.createCell(4);
			HSSFCellStyle textStyle = workbook.createCellStyle();
			HSSFDataFormat format = workbook.createDataFormat();
			textStyle.setDataFormat(format.getFormat("text"));
			cell.setCellStyle(textStyle);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("");
			cell = row.createCell(5);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(page.getRows().size()+"");
			
			//创建一行
			row= sheet.createRow(2);
			cell = row.createCell(0);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 往第一个单元格中写入信息
			cell.setCellValue("商户流水号");
			cell = row.createCell(1);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("收款人Email");
			cell = row.createCell(2);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("收款人姓名");
			cell = row.createCell(3);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("付款金额(元)");
			cell = row.createCell(4);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("付款理由");
			
			int len = page.getRows().size();
			BigDecimal sum = new BigDecimal(0);
			for (int i = 0; i <len ; i++) {
				PayRes res =page.getRows().get(i);
				//创建一行
				row= sheet.createRow(3+i);
				cell = row.createCell(0);
				// 设置单元格内容为字符串型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 往第一个单元格中写入信息
				cell.setCellValue(res.getPayId()+"");
				cell = row.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(res.getAccount());
				cell = row.createCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(res.getUserName());
				
				// 每笔金额
				cell = row.createCell(3);
				HSSFCellStyle textStyle1 = workbook.createCellStyle();
				HSSFDataFormat format1 = workbook.createDataFormat();
				textStyle1.setDataFormat(format1.getFormat("text"));
				cell.setCellStyle(textStyle1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(res.getPaySum().doubleValue());
				// 累加总和
				sum = sum.add(res.getPaySum());
				cell = row.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(res.getPayReason());
			}
			
			//所有导出钱数总合
			sheet.getRow(1).getCell(4).setCellValue(sum.doubleValue()+"");
			
			//写入到 客户端response
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("content-disposition", "attachment;filename="+xlsName+".xls");
			log.info("笔数:"+page.getRows().size()+" , 总金额: "+sum+"  , "+xlsName+".xls 文件生产 成功.");
			try {
				OutputStream sof= response.getOutputStream();
				workbook.write(sof);
				sof.flush();
				sof.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return "0";
		}
	}
	
	/**
	 * 导入  数据信息
	 * @param session
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/importExcel")
	@ResponseBody
	public void importExcel(HttpServletRequest request, HttpServletResponse response, @RequestParam("payExcel") MultipartFile excel) {
		if (excel != null && !excel.isEmpty()) {
			StringBuffer sb = new StringBuffer("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"+ excel.getName() + "\n");
			System.out.println(sb.toString());
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				int lastNum = readSheet.getLastRowNum();
				EmployeeModel mp = (EmployeeModel) request.getSession().getAttribute(Constants.LOGIN_SESSION);
				List<ImportPayReq> reqs = new ArrayList<ImportPayReq>();
				if (lastNum >= 3) {
					for (int i = 3; i <= lastNum; i++) {
						HSSFRow row= readSheet.getRow(i);
						HSSFCell cell = row.getCell(0);
						ImportPayReq req = new ImportPayReq();
						String id= ActionExcel.changeCellToString(cell);
						if("".equals(id)){
							continue;
						}
						cell = row.getCell(5);
						String zhi= ActionExcel.changeCellToString(cell);
						if("".equals(zhi)){
							continue;
						}
						cell = row.getCell(8);
						String state= ActionExcel.changeCellToString(cell);
						cell = row.getCell(9);
						String remark= ActionExcel.changeCellToString(cell);
						
						req.setEmployeeId(mp.getId());
						req.setPayId(Integer.valueOf(id));
						req.setSerialNumber(zhi);
						req.setState(state);
						req.setRemark(remark);
						
						reqs.add(req);
					}
				}
				if (reqs.size() > 0) {
					JSONObject jobj = HttpClientHelper.sendRestInterShortObject("/rest/pay/importExcel.rest", reqs);
					if(jobj != null){
						if(jobj.containsKey("errorCode")){
							response.getWriter().print("<script>parent.returnParentFunction("+jobj.get("errorCode")+");</script>");
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
