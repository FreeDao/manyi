package com.manyi.hims.actionexcel.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.leo.common.util.DateUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.User;

@Service(value = "userBankService")
@Scope(value = "singleton")
public class UserBankServiceImpl extends BaseService implements UserBankService {

	Logger log = LoggerFactory.getLogger(UserBankServiceImpl.class);
	
	String path = "/mnt/disk/fybao/actions";

	/**
	 * 导入 用户的关联 关系(通过邀请码推荐的)
	 */
	@Override
	public String importUserRelation(MultipartFile excel) {
		String msg = new String("导入 用户的关联 关系(通过邀请码推荐的) 时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"
				+ excel.getName() + "\n");
		StringBuffer sb = new StringBuffer(msg); 
		if (excel != null && !excel.isEmpty()) {
			log.info(msg);
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);//读取标题
				HSSFCell cell = row.getCell(0);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("第一行标题 数据读取 完毕..");
				msg = "行数:" + num + "\n";
				sb.append(msg);
				log.info(msg);
				for (int i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						String bdName = "";
						if(cell != null){
							bdName = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(1);
						String bdMobile = "";
						if(cell != null ){
							bdMobile = ActionExcel.changeCellToString(cell);
						}else{
							msg = " 行数: "+(i+1)+" , DB手机号码为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						cell = row.getCell(2);
						String userName = "";
						if(cell != null ){
							userName = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(3);
						String userMobile="";
						if(cell != null){
							userMobile = ActionExcel.changeCellToString(cell);
						}else{
							msg =" 行数: "+(i+1)+" , 中介手机号码为空\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						
						//开始解析 内容, 建立  中介-中介的 关联关系
						
						//查询是否 存在 BD人员
						String jqpl="from User u where u.mobile = ?";
						Query query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, bdMobile);
						List<User> bds=  query.getResultList();
						if(bds != null && bds.size()>0){
							
						}else{
							msg = " 行数: "+(i+1)+" bdMobile :  "+bdMobile+" , DB没有注册\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						
						//查询是否 存在 经纪人人员
						jqpl = "from User u where u.mobile = ?";
						query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, userMobile);
						List<User> users=  query.getResultList();
						if(users != null && users.size()>0){
							
						}else{
							msg = " 行数: "+(i+1)+" userMboile : "+userMobile+" ,经纪人没有注册\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
						
						//修改数据,建立关联关系 , 中介的spreadName 修改为 推广人的 邀请码
						jqpl="update User u set u.spreadName = ? where u.mobile =? ";
						query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, bds.get(0).getSpreadId());
						query.setParameter(2, userMobile);
						int tmpNum = query.executeUpdate();
						if(tmpNum > 0){
							msg = " 行数: "+(i+1)+" bdMobile: "+bdMobile+" , userMobile: " +userMobile+",修改成功\n";
							log.info(msg);
							sb.append(msg);
						}else{
							msg = " 行数: "+(i+1)+" bdMobile: "+bdMobile+" , userMobile: " +userMobile+",修改失败\n";
							log.info(msg);
							sb.append(msg);
							continue;
						}
					}
				}
				
				msg ="导入 用户的关联 关系(通过邀请码推荐的) 完成 时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) +" , xsl文件导入完成...\n";
				log.info(msg);
				sb.append(msg);
				
				File pathFile = new File(path+"/log/");
				if (!pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/" + "import-UserRelation.log");
				try {

					if (!logFile.exists()) {
						logFile.createNewFile();
					}
					FileOutputStream sof = new FileOutputStream(logFile);
					sof.write(sb.toString().getBytes());
					sof.flush();
					sof.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					sb.append(e.getMessage());
				} catch (IOException e) {
					e.printStackTrace();
					sb.append(e.getMessage());
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.append("全部 解析完成....").toString();
	}
	
	/**
	 * 导出 callcenter 某个时间段内 所有的人的 审核情况
	 */
	@Override
	public int exportUserCallCenterCheckNum(HttpServletResponse response, Date start, Date end) {
		// Date start , Date end , 过滤数据的条件, 审核时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String xlsName = "export-UserCallCenterCheckNum-" + sdf.format(new Date());
		StringBuffer sb = new StringBuffer(sdf.format(new Date()) + "导出 客服审核情况     搜索条件  start: "
				+ (start == null ? "" : sdf.format(start)) + " end: " + (end == null ? "" : sdf.format(end)) + "\n");
		
		List<Date> pars = new ArrayList<Date>();
		String sqlSell="SELECT count(hr.houseId) hrSum, SUM(CASE WHEN hr.`status` = 2 THEN  1 ELSE 0 END) ing ,SUM(CASE WHEN hr.`status` =1 THEN 1 ELSE 0 END ) success , "+
				" SUM(CASE WHEN hr.`status` =3 THEN 1 ELSE 0 END ) faild from houseresourcehistory hr "+
				" where hr.checkNum > 0 and hr.actionType = 1 and hr.houseState = 1 and hr.resultDate is not NULL ";
		String sqlRent="SELECT count(hr.houseId) hrSum, SUM(CASE WHEN hr.`status` = 2 THEN  1 ELSE 0 END) ing ,SUM(CASE WHEN hr.`status` =1 THEN 1 ELSE 0 END ) success , "+
				" SUM(CASE WHEN hr.`status` =3 THEN 1 ELSE 0 END ) faild from houseresourcehistory hr "+
				" where hr.checkNum > 0 and hr.actionType = 1 and hr.houseState = 2 and hr.resultDate is not NULL ";
		String sqlChange ="SELECT count(hr.houseId) hrSum, SUM(CASE WHEN hr.`status` = 2 THEN  1 ELSE 0 END) ing ,SUM(CASE WHEN hr.`status` =1 THEN 1 ELSE 0 END ) success , "+
				" SUM(CASE WHEN hr.`status` =3 THEN 1 ELSE 0 END ) faild from houseresourcehistory hr "+
				" where hr.checkNum > 0 and hr.actionType = 2 and hr.resultDate < now() and hr.resultDate is not NULL";
		
		if (start != null) {
			sqlSell += " and hr.resultDate >= ? ";
			sqlRent += " and hr.resultDate >= ? ";
			sqlChange += " and hr.resultDate >= ? ";
			pars.add(start);
		}
		if (end != null) {
			sqlSell += " and hr.resultDate < ? ";
			sqlRent += " and hr.resultDate < ? ";
			sqlChange += " and hr.resultDate < ? ";
			pars.add(end);
		}
		
		Query querySell = this.getEntityManager().createNativeQuery(sqlSell);
		Query queryRent = this.getEntityManager().createNativeQuery(sqlRent);
		Query queryChange = this.getEntityManager().createNativeQuery(sqlChange);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				querySell.setParameter(1 + i, pars.get(i));
				queryRent.setParameter(1 + i, pars.get(i));
				queryChange.setParameter(1 + i, pars.get(i));
			}
		}
		List<Object[]> sells = querySell.getResultList();
		List<Object[]> rents = queryRent.getResultList();
		List<Object[]> changes = queryChange.getResultList();
		
		HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
		HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
		workbook.setSheetName(0, xlsName);

		// 设置 表格中 每一列的宽度
		sheet.setColumnWidth(0, 15 * 256);
		sheet.setColumnWidth(1, 10 * 256);
		sheet.setColumnWidth(2, 10 * 256);
		sheet.setColumnWidth(3, 10 * 256);
		sheet.setColumnWidth(4, 10 * 256);
		sheet.setColumnWidth(5, 10 * 256);
		sheet.setColumnWidth(6, 20 * 256);
		sheet.setColumnWidth(7, 10 * 256);
		sheet.setColumnWidth(8, 20 * 256);
		
		// 产生1行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("发布类型");
		cell = row.createCell(1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("审核失败");
		cell = row.createCell(2);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("再审核");
		cell = row.createCell(3);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("审核成功");
		cell = row.createCell(4);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("总审核数");
		cell = row.createCell(5);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("起始审核时间");
		cell = row.createCell(6);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(sdf.format(start));
		cell = row.createCell(7);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("截止审核时间");
		cell = row.createCell(8);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(sdf.format(end));
		
		int hrSum =0;
		int ing = 0;
		int success = 0;
		int faild = 0;
		
		//出售
		if(sells != null && sells.size()>0){
			Object[] objs = sells.get(0);
			BigInteger tmp = (BigInteger) objs[0];
			if(tmp != null){
				hrSum = tmp.intValue();
			}
			BigDecimal tmp1 = (BigDecimal)objs[1];
			if(tmp1 != null){
				ing = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[2];
			if(tmp1 != null){
				success = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[3];
			if(tmp1 != null){
				faild = tmp1.intValue();
			}
		}
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("出售");
		cell = row.createCell(1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(faild+"");
		cell = row.createCell(2);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(ing+"");
		cell = row.createCell(3);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(success+"");
		cell = row.createCell(4);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(hrSum+"");
		
		//出租
		 hrSum =0;
		 ing = 0;
		 success = 0;
		 faild = 0;
		if(rents != null && rents.size()>0){
			Object[] objs = rents.get(0);
			BigInteger tmp = (BigInteger) objs[0];
			if(tmp != null){
				hrSum = tmp.intValue();
			}
			BigDecimal tmp1 = (BigDecimal)objs[1];
			if(tmp1 != null){
				ing = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[2];
			if(tmp1 != null){
				success = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[3];
			if(tmp1 != null){
				faild = tmp1.intValue();
			}
		}
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("出租");
		cell = row.createCell(1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(faild+"");
		cell = row.createCell(2);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(ing+"");
		cell = row.createCell(3);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(success+"");
		cell = row.createCell(4);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(hrSum+"");
		
		//改盘
		hrSum =0;
		 ing = 0;
		 success = 0;
		 faild = 0;
		if(changes != null && changes.size()>0){
			Object[] objs = changes.get(0);
			BigInteger tmp = (BigInteger) objs[0];
			if(tmp != null){
				hrSum = tmp.intValue();
			}
			BigDecimal tmp1 = (BigDecimal)objs[1];
			if(tmp1 != null){
				ing = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[2];
			if(tmp1 != null){
				success = tmp1.intValue();
			}
			tmp1 = (BigDecimal)objs[3];
			if(tmp1 != null){
				faild = tmp1.intValue();
			}
		}
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue("改盘");
		cell = row.createCell(1);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(faild+"");
		cell = row.createCell(2);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(ing+"");
		cell = row.createCell(3);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(success+"");
		cell = row.createCell(4);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellValue(hrSum+"");
		
		try {
			// 写入到 客户端response
			response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
			OutputStream sof1 = response.getOutputStream();
			workbook.write(sof1);

			sof1.flush();
			sof1.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			sb.append(e.getMessage());
		}
		System.out.println("xsl文件生成...");
		
		return 0;
	}
	
	
	@Override
	public int exportUserBank(HttpServletResponse response, Date start, Date end) {
		// Date start , Date end , int state 过滤数据的条件
		List<Date> pars = new ArrayList<Date>();
		String sql = "  SELECT u.realName,u.mobile,  sum( CASE WHEN log.sourceState = 0 THEN 1 ELSE 0 END ) finishNum  "
				+ " FROM sourcelog log JOIN User u ON u.uid = log.userId and log.DTYPE='SellLog' " + " where log.sourceLogId <>0 ";
		if (start != null) {
			sql += " and ( ( log.filshDate >= ? ";
			pars.add(start);
		}
		if (end != null) {
			sql += " and log.filshDate < ?  and log.sourceState <> 1 )  or (log.filshDate is null  and log.sourceState = 1 )) ";
			pars.add(end);
		}
		sql += " GROUP BY u.realName,u.mobile  ";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String xlsName = "export-userbank-" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

		File f = new File(path + "/log");
		if (!f.exists()) {
			f.mkdirs();// 不存在,创建文件夹
		}

		StringBuffer msg = new StringBuffer(sdf.format(new Date()) + "导出经纪人上传出售金额     搜索条件  start: "
				+ (start == null ? "" : sdf.format(start)) + " end: " + (end == null ? "" : sdf.format(end)) + "\n\r");
		Query query = this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(1 + i, pars.get(i));
			}
		}
		List<Object[]> logs = query.getResultList();
		if (logs != null && logs.size() > 0) {

			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			workbook.setSheetName(0, xlsName);

			// 设置 表格中 每一列的宽度
			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 10 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(6, 10 * 256);
			sheet.setColumnWidth(7, 10 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 10 * 256);

			// 产生1行
			HSSFRow rowTitle1 = sheet.createRow(0);
			HSSFCell cell1 = rowTitle1.createCell(0);
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell1.setCellValue("房源宝帐号信息");
			cell1 = rowTitle1.createCell(5);
			cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell1.setCellValue("账户结算信息");
			
			//合并单元格
			//// 四个参数分别是：起始行，结束行，起始列，结束列 
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,4));
			sheet.addMergedRegion(new CellRangeAddress(0,0,5,11));
						
			// 产生2行
			HSSFRow rowTitle = sheet.createRow(1);
			// `realName`, `mobile`, `houseSum`, `finishSum`, `checkSum`, `finedSum`, `houseNum`, `finishNum`, `checkNum`, `finedNum`
			// 产生第一个单元格
			HSSFCell cell = rowTitle.createCell(0);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("来源");
			cell = rowTitle.createCell(1);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("地产公司");

			cell = rowTitle.createCell(2);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("区域");
			cell = rowTitle.createCell(3);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("姓名");
			cell = rowTitle.createCell(4);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("手机号码");
			cell = rowTitle.createCell(5);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("开户姓名");			
			
			cell = rowTitle.createCell(6);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("联系电话");
			cell = rowTitle.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.getStringCellValue();
			cell.setCellValue("所属银行");
			cell = rowTitle.createCell(8);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("分支行信息");
			cell = rowTitle.createCell(9);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("银行卡号");
			cell = rowTitle.createCell(10);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("支付宝帐号");//alipayCode
			cell = rowTitle.createCell(11);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("结算金额");
			cell = rowTitle.createCell(12);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("备注");
			
			
			for (int i = 1, k = 0; i < logs.size(); i++,k++) {
				// `realName`, `mobile`, `houseSum`, `finishSum`, `checkSum`, `finedSum`, `houseNum`, `finishNum`, `checkNum`, `finedNum`
				Object[] row = logs.get(k);
				
				String name = (String) row[0];
				String mobile =(String) row[1];
				Double finishNum = Double.valueOf(row[2]+"");
				String jqpl="select u,c from User u , City c where u.areaId = c.areaId and u.mobile = ? ";
				 Query queryUser =this.getEntityManager().createQuery(jqpl);
				 queryUser.setParameter(1, mobile);
				 List<Object[]> lists = queryUser.getResultList();
				 
				if (lists != null && lists.size()>0) {
					Object[] objs = lists.get(0);
					User user = (User) objs[0];
					User spreadUser = null;
					City city = (City) objs[1];
					
					if(user.getSpreadName() != null && !"".equals(user.getSpreadName())){
						List<User> spUsers = this.getEntityManager().createQuery("from User u where u.spreadId = ?").setParameter(1, user.getSpreadName()).getResultList();
						if(spUsers != null && spUsers.size()>0){
							spreadUser = spUsers.get(0);
						}
						
					}
					// 产生一行
					HSSFRow hssrow = sheet.createRow(i + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(spreadUser == null ? "" : spreadUser.getRealName());
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getCompany());
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(city.getName());
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getRealName());
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(user.getMobile());
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getAccountName() == null ? "" : user.getAccountName());
//					cell = hssrow.createCell(6);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getAccountMobile() == null ? "" :user.getAccountMobile() );
//					cell = hssrow.createCell(7);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getBank() == null ? "" :user.getBank());
//					cell = hssrow.createCell(8);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getSubBank() == null ? "" :user.getSubBank());
//					cell = hssrow.createCell(9);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getBankCode() == null ? "" :user.getBankCode());
//					cell = hssrow.createCell(10);
//					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
//					cell.setCellValue(user.getAlipayCode() == null ? "" :user.getAlipayCode());
//					cell = hssrow.createCell(11);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(finishNum*7);
					
					msg.append(row[0] + "\t" + row[1] + "\t" + "\n\r");
				}
			}

			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			File logFile = new File(path + xlsName + ".log");
			try {

				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				FileOutputStream sof = new FileOutputStream(logFile);
				sof.write(msg.toString().getBytes());
				sof.flush();
				sof.close();

				// 写入到 客户端response
				response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
				OutputStream sof1 = response.getOutputStream();
				workbook.write(sof1);

				sof1.flush();
				sof1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				msg.append(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				msg.append(e.getMessage());
			}
			System.out.println("xsl文件生成...");
		} else {
			try {
				response.getOutputStream().print("Not conform to the requirements of data");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	@Override
	public int exportUserNum(HttpServletResponse response, Date start, Date end) {
		// Date start , Date end , int state 过滤数据的条件
		List<Date> pars = new ArrayList<Date>();
		String sql = "select A.*,B.houseNum,B.finishNum,B.checkNum, B.finedNum from "
				+ " ( SELECT u.realName,u.mobile, COUNT(1) houseSum, sum( CASE WHEN log.sourceState = 0 THEN 1 ELSE 0 END ) finishSum ,sum( CASE WHEN log.sourceState = 1 THEN 1 ELSE 0 END ) `checkSum` ,sum( CASE WHEN log.sourceState = 2 THEN 1 ELSE 0 END ) finedSum "
				+ " FROM sourcelog log JOIN User u ON u.uid = log.userId and log.DTYPE='SellLog' "
				+ " GROUP BY u.realName,u.mobile ) A ,"
				+ " ( SELECT u.realName,u.mobile, COUNT(1) houseNum, sum( CASE WHEN log.sourceState = 0 THEN 1 ELSE 0 END ) finishNum ,sum( CASE WHEN log.sourceState = 1 THEN 1 ELSE 0 END ) checkNum ,sum( CASE WHEN log.sourceState = 2 THEN 1 ELSE 0 END ) finedNum "
				+ " FROM sourcelog log JOIN User u ON u.uid = log.userId and log.DTYPE='SellLog' " + " where log.sourceLogId <>0 ";
		if (start != null) {
			sql += " and ( ( log.filshDate >= ? ";
			pars.add(start);
		}
		if (end != null) {
			sql += " and log.filshDate < ?  and log.sourceState <> 1 )  or (log.filshDate is null  and log.sourceState = 1 )) ";
			pars.add(end);
		}
		sql += " GROUP BY u.realName,u.mobile ) B  where A.mobile = B.mobile ";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String xlsName = "export-usercount-" + (new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

		File f = new File(path + "/log");
		if (!f.exists()) {
			f.mkdirs();// 不存在,创建文件夹
		}

		StringBuffer msg = new StringBuffer(sdf.format(new Date()) + "导出经纪人上传出售房源数量     搜索条件  start: "
				+ (start == null ? "" : sdf.format(start)) + " end: " + (end == null ? "" : sdf.format(end)) + "\n\r");
		Query query = this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(1 + i, pars.get(i));
			}
		}
		List<Object[]> logs = query.getResultList();
		if (logs != null && logs.size() > 0) {

			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			workbook.setSheetName(0, xlsName);

			// 设置 表格中 每一列的宽度
			sheet.setColumnWidth(0, 10 * 256);
			sheet.setColumnWidth(1, 20 * 256);
			sheet.setColumnWidth(2, 10 * 256);
			sheet.setColumnWidth(3, 10 * 256);
			sheet.setColumnWidth(4, 10 * 256);
			sheet.setColumnWidth(5, 10 * 256);
			sheet.setColumnWidth(6, 10 * 256);
			sheet.setColumnWidth(7, 10 * 256);
			sheet.setColumnWidth(8, 10 * 256);
			sheet.setColumnWidth(9, 10 * 256);

			// 产生一行
			HSSFRow rowTitle = sheet.createRow(0);
			// `realName`, `mobile`, `houseSum`, `finishSum`, `checkSum`, `finedSum`, `houseNum`, `finishNum`, `checkNum`, `finedNum`
			// 产生第一个单元格
			HSSFCell cell = rowTitle.createCell(0);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("姓名");
			cell = rowTitle.createCell(1);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("手机号码");

			cell = rowTitle.createCell(2);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总发布出售量");
			cell = rowTitle.createCell(3);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总审核成功量");
			cell = rowTitle.createCell(4);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总审核中量");
			cell = rowTitle.createCell(5);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("总审核失败量");

			/*
			cell = rowTitle.createCell(6);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("发布出售量");
			cell = rowTitle.createCell(7);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("审核成功量");
			cell = rowTitle.createCell(8);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("审核中量");
			cell = rowTitle.createCell(9);
			// 设置单元格内容为字符串型
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("审核失败量");
			 */
			
			
			cell = rowTitle.createCell(6);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("审核成功量");
			cell = rowTitle.createCell(7);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue("审核失败量");
			
			
			for (int i = 0, k = 0; i < logs.size(); i++) {
				// `realName`, `mobile`, `houseSum`, `finishSum`, `checkSum`, `finedSum`, `houseNum`, `finishNum`, `checkNum`, `finedNum`
				Object[] row = logs.get(i);
				// 产生一行
				HSSFRow hssrow = sheet.createRow(i + 1);
				cell = hssrow.createCell(0);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[0] + "");
				cell = hssrow.createCell(1);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[1] + "");
				cell = hssrow.createCell(2);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[2] + "");
				cell = hssrow.createCell(3);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[3] + "");
				cell = hssrow.createCell(4);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[4] + "");
				cell = hssrow.createCell(5);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[5] + "");
				
				/*
				cell = hssrow.createCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[6] + "");
				cell = hssrow.createCell(7);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[7] + "");
				cell = hssrow.createCell(8);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[8] + "");
				cell = hssrow.createCell(9);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[9] + "");
				*/
				
				cell = hssrow.createCell(6);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[7] + "");
				cell = hssrow.createCell(7);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(row[9] + "");
				

				msg.append(row[0] + "\t" + row[1] + "\t" + row[2] + "\t" + row[3] + "\t" + row[4] + "\t" + row[5] + "\t" + row[6] + "\t"
						+ row[7] + "\t" + row[8] + "\t" + row[9] + "\t" + "\n\r");
			}

			File pathFile = new File(path);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			File logFile = new File(path + xlsName + ".log");
			try {

				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				FileOutputStream sof = new FileOutputStream(logFile);
				sof.write(msg.toString().getBytes());
				sof.flush();
				sof.close();

				// 写入到 客户端response
				response.setHeader("content-disposition", "attachment;filename=" + xlsName + ".xls");
				OutputStream sof1 = response.getOutputStream();
				workbook.write(sof1);

				sof1.flush();
				sof1.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				msg.append(e.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
				msg.append(e.getMessage());
			}
			System.out.println("xsl文件生成...");
		} else {
			try {
				response.getOutputStream().print("Not conform to the requirements of data");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}
	
	@Override
	public int improtUserBank(MultipartFile excel) {
		if (excel != null && !excel.isEmpty()) {
			StringBuffer sb = new StringBuffer("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:"
					+ excel.getName() + "\n");
			System.out.println(excel.getName());
			try {
				// 以下语句读取生成的Excel文件内容
				InputStream fIn = excel.getInputStream();
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);
				HSSFCell cell = null;
				
				System.out.println("第一行标题 数据读取 完毕..");
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("行数:" + num);
				sb.append("行数:" + num + "\n");
				for (int i = 2; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(1);
						String company = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						String name = ActionExcel.changeCellToString(cell);
						cell = row.getCell(4);
						if(cell ==  null){
							continue;
						}
						String mobile = ActionExcel.changeCellToString(cell);
						if(mobile == null || "".equals(mobile)){
							continue;
						}
						cell = row.getCell(5);
						String accountName = ActionExcel.changeCellToString(cell);
						cell = row.getCell(6);
						String accountMobile = ActionExcel.changeCellToString(cell);
						cell = row.getCell(7);
						String bank = ActionExcel.changeCellToString(cell);
						cell = row.getCell(8);
						String subBank = ActionExcel.changeCellToString(cell);
						cell = row.getCell(9);
						String bankCode = ActionExcel.changeCellToString(cell);
						cell = row.getCell(10);
						String alipayCode = ActionExcel.changeCellToString(cell);
						
						String jqpl=" from User u where u.mobile = ?";
						javax.persistence.Query query = this.getEntityManager().createQuery(jqpl);
						query.setParameter(1, mobile);
						List<User> users = query.getResultList();
						if(users != null && users.size()>0){
							User user = users.get(0);
//							user.setBank(bank);
//							user.setAccountMobile(accountMobile);
//							user.setAccountName(accountName);
//							user.setBankCode(bankCode);
//							user.setSubBank(subBank);
//							user.setCompany(company);
//							user.setAlipayCode(alipayCode);
							
							this.getEntityManager().merge(user);
							
							sb.append(name+"," +mobile+" 添加银行信息成功: " + bank +"," + subBank+" ,"+ bankCode+" ,"+ accountName +" , "+accountMobile+"\n");
						}else{
							sb.append(name+"," +mobile+" 没有注册\n");
						}
						
					}
				}
				this.getEntityManager().flush();
				System.out.println("解析入库完成.");
				sb.append("完成时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "解析入库完成.\n\n\n");
				File pathFile = new File(path);
				if (pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/" + "import-userbank.log");
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				FileOutputStream sof = new FileOutputStream(logFile, true);
				sof.write(sb.toString().getBytes());
				sof.flush();
				sof.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}

		return 0;
	}

}
