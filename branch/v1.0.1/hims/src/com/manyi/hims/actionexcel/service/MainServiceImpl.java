package com.manyi.hims.actionexcel.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.multipart.MultipartFile;

import com.leo.common.util.DataUtil;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.actionexcel.model.CustomerWorkInfo;
import com.manyi.hims.actionexcel.model.OperationsInfo;
import com.manyi.hims.actionexcel.model.ResidenceInfo;
import com.manyi.hims.entity.Address;
import com.manyi.hims.entity.Area;
import com.manyi.hims.entity.City;
import com.manyi.hims.entity.City_;
import com.manyi.hims.entity.Estate;
import com.manyi.hims.entity.Town;
import com.manyi.hims.entity.Town_;
import com.manyi.hims.entity.User;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.util.InfoUtils;

@Service(value = "mainService")
@Scope(value = "singleton")
public class MainServiceImpl extends BaseService implements MainService {

	String path = "/mnt/disk/fybao/actions";

	/**
	 * 通过 parentid 得到所有的 地区信息
	 * 
	 * @param parentId
	 * @return
	 */
	public List<Area> loadArae(String type, String parentId) {
		String jpql = "";
		if (type.indexOf("province") >= 0) {
			jpql = "from City as _area where _area.parentId = ?";
		} else if (type.indexOf("city") >= 0) {
			jpql = "from Town as _area where _area.parentId = ?";
		} else {
			jpql = "from City as _area where _area.parentId = ?";
			parentId = "1";
		}
		Query areaQuery = getEntityManager().createQuery(jpql);
		areaQuery.setParameter(1, Integer.valueOf(parentId));
		return areaQuery.getResultList();
	}

	/**
	 * 上传在审核中
	 * 
	 * @param publishDate
	 * @param pars
	 * @return
	 */
	public String loadVerifySql(String publishDate, List<Object> pars) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder loadSB = new StringBuilder();
		loadSB.append(" SELECT '上传在审核中' AS type, ");
		loadSB.append(" COUNT(CASE WHEN checkNum=0 THEN checkNum END) oneC, ");
		loadSB.append(" COUNT(CASE WHEN checkNum=1 THEN checkNum END) twoC, ");
		loadSB.append(" COUNT(CASE WHEN checkNum=2 THEN checkNum END) threeC, ");
		loadSB.append(" COUNT(CASE WHEN checkNum=3 THEN checkNum END) fourC, ");
		loadSB.append(" COUNT(CASE WHEN checkNum=4 THEN checkNum END) fiveC, ");
		loadSB.append(" publishDate FROM houseresource  hr  ");
		loadSB.append(" WHERE hr.actionType IN (1,2) AND  hr.status=2  ");
		if (publishDate != null) {
			loadSB.append(" 	 AND  hr.publishDate <= ? ");
		}
		try {
			pars.add(sdf.parse(publishDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loadSB.toString();
	}

	/**
	 * 轮询在审核中
	 * 
	 * @param publishDate
	 * @param pars
	 * @return
	 */
	public String pollVerifySql(String publishDate, List<Object> pars) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuilder pollSB = new StringBuilder();
		pollSB.append(" SELECT '轮询在审核中' AS type, ");
		pollSB.append(" COUNT(CASE WHEN checkNum=0 THEN checkNum END) oneC, ");
		pollSB.append(" COUNT(CASE WHEN checkNum=1 THEN checkNum END) twoC, ");
		pollSB.append(" COUNT(CASE WHEN checkNum=2 THEN checkNum END) threeC, ");
		pollSB.append(" COUNT(CASE WHEN checkNum=3 THEN checkNum END) fourC, ");
		pollSB.append(" COUNT(CASE WHEN checkNum=4 THEN checkNum END) fiveC, ");
		pollSB.append(" publishDate FROM houseresource  hr2  ");
		pollSB.append(" WHERE hr2.actionType IN (4)  AND  hr2.status=2  ");
		if (publishDate != null) {
			pollSB.append(" 	 AND  hr2.publishDate <= ? ");
		}
		try {
			pars.add(sdf.parse(publishDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollSB.toString();
	}

	public Date getDate(String dateString, int beforeDays) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date inputDate = dateFormat.parse(dateString);

		Calendar cal = Calendar.getInstance();
		cal.setTime(inputDate);

		int inputDayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		System.out.println(inputDayOfYear);
		cal.set(Calendar.DAY_OF_YEAR, inputDayOfYear - beforeDays);

		return cal.getTime();
	}

	public static void main(String[] args) {
		MainServiceImpl s = new MainServiceImpl();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {

			Date date = s.getDate("2013-05-14 16:45:30", 10);
			System.out.println(sdf.format(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getStringByDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 明日新增
	 */
	public String tomNewVerifySql(String publishDate, List<Object> pars) {
		StringBuilder pollSB = new StringBuilder();
		pollSB.append(" SELECT '明日新增' AS type, ");
		pollSB.append(" COUNT(checkNum) oneC, ");
		pollSB.append(" publishDate FROM houseresource  hr3  ");
		pollSB.append(" WHERE hr3.actionType IN (1,2)  AND  hr3.status=1  ");
		if (publishDate != null) {
			pollSB.append(" 	 AND  hr3.publishDate >= ? AND hr3.publishDate <= ? ");
		}
		try {
			Date date1 = getDate(publishDate, 10);
			Date date2 = getDate(publishDate, 9);
			pars.add(date1);
			pars.add(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollSB.toString();
	}

	/**
	 * 后天新增
	 */
	public String yesNewVerifySql(String publishDate, List<Object> pars) {
		StringBuilder pollSB = new StringBuilder();
		pollSB.append(" SELECT '后天新增' AS type, ");
		pollSB.append(" COUNT(checkNum) oneC, ");
		pollSB.append(" publishDate FROM houseresource  hr3  ");
		pollSB.append(" WHERE hr3.actionType IN (1,2)  AND  hr3.status=1  ");
		if (publishDate != null) {
			pollSB.append(" 	 AND  hr3.publishDate >= ? AND hr3.publishDate <= ? ");
		}
		try {
			Date date1 = getDate(publishDate, 9);
			Date date2 = getDate(publishDate, 8);
			pars.add(date1);
			pars.add(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollSB.toString();
	}

	/**
	 * 封装查询sql参数
	 * 
	 * @param pars
	 * @param query
	 */
	private void packageParamter(List<Object> pars, Query query) {
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i + 1, pars.get(i));
			}
		}
	}

	/**
	 * 客服工作量
	 */
	public List<CustomerWorkInfo> customerWork(String publishDate) {
		List<Object> pars = new ArrayList<Object>();
		List<CustomerWorkInfo> customerList = new ArrayList<CustomerWorkInfo>();
		StringBuilder customerWork = new StringBuilder();
		customerWork.append(loadVerifySql(publishDate, pars));
		customerWork.append(" UNION ALL ");
		customerWork.append(pollVerifySql(publishDate, pars));
		Query query = this.getEntityManager().createNativeQuery(customerWork.toString());
		packageParamter(pars, query);
		List<Object[]> cwInfo = new ArrayList<Object[]>();
		cwInfo = query.getResultList();
		//
		int index = 0;
		if (cwInfo != null && cwInfo.size() > 0) {
			for (Object[] row : cwInfo) {
				index ++;
				CustomerWorkInfo res = new CustomerWorkInfo();
				res.setCustomType(row[0] + "");
				res.setOneNum(Integer.parseInt(row[1] + ""));
				res.setTwoNum(Integer.parseInt(row[2] + ""));
				res.setThreeNum(Integer.parseInt(row[3] + ""));
				res.setFourNum(Integer.parseInt(row[4] + ""));
				res.setFiveNum(Integer.parseInt(row[5] + ""));
				res.setPublishDateStr("");
				if(index ==1 ) {
					res.setTomNum("");
					res.setYesNum(" ");
				} else {
					//明日新增
					pars =new ArrayList<Object>();
					String tomSql = tomNewVerifySql(publishDate,pars);
					query = this.getEntityManager().createNativeQuery(tomSql);
					packageParamter(pars, query);
					List<Object[]> tomInfo = new ArrayList<Object[]>();
					tomInfo = query.getResultList();
					if(tomInfo != null && tomInfo.size()>0) {
						for(Object[] row1 : tomInfo) {
							res.setTomNum(row1[0]+":"+Integer.parseInt(row1[1]+""));
						}
					}
					//后日新增
					pars =new ArrayList<Object>();
					String yesSql = yesNewVerifySql(publishDate,pars);
					query = this.getEntityManager().createNativeQuery(yesSql);
					packageParamter(pars, query);
					List<Object[]> yesInfo = new ArrayList<Object[]>();
					yesInfo = query.getResultList();
					if(yesInfo != null && yesInfo.size()>0) {
						for(Object[] row2 : yesInfo) {
							res.setYesNum(row2[0]+":"+Integer.parseInt(row2[1]+""));
						}
					}
				}
				customerList.add(res);
			}
		}

		return customerList;

	}

	/**
	 * BD运营报表24sql
	 */
	public String bDOperationsWith24Sql(List<Object> pars) {
		StringBuilder pollSB = new StringBuilder();
		pollSB.append(" SELECT u1.realName, u1.mobile,  ");
		pollSB.append(" COUNT(u2.state) newNum, ");
		pollSB.append(" COUNT(CASE WHEN u2.state=2 THEN u2.state END) verifyNum ");
		pollSB.append(" FROM User u1 , User u2 ");
		pollSB.append(" WHERE u1.spreadId = u2.spreadName AND u1.ifInner=1  ");
		pollSB.append(" AND u1.createTime <= ? AND u1.createTime >= ? ");
		pollSB.append(" GROUP BY u1.spreadId ");
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = new Date();
			Date date2 = getDate(dateFormat.format(date1), 1);
			pars.add(date1);
			pars.add(date2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollSB.toString();
	}

	/**
	 * BD运营报表sql
	 */
	public String bDOperationsWithSql(OperationsInfo info, List<Object> pars) {
		StringBuilder pollSB = new StringBuilder();
		pollSB.append(" SELECT u1.realName, u1.mobile,  ");
		pollSB.append(" COUNT(u2.state) newNum, ");
		pollSB.append(" COUNT(CASE WHEN u2.state=2 THEN u2.state END) verifyNum ");
		pollSB.append(" FROM User u1 , User u2 ");
		pollSB.append(" WHERE u1.spreadId = u2.spreadName AND u1.ifInner=1  ");
		if (info.getBeginCreateTime() != null) {
			pollSB.append(" AND u1.createTime <= ? AND u1.createTime >= ? ");
		}
		pollSB.append(" GROUP BY u1.spreadId ");
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pars.add(dateFormat.parse(info.getEndCreateTime()));
			pars.add(dateFormat.parse(info.getBeginCreateTime()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pollSB.toString();
	}

	/**
	 * BD运营报表
	 */
	@Override
	public List<OperationsInfo> bDOperations(HttpServletResponse response, OperationsInfo info) {
		List<Object> pars = new ArrayList<Object>();
		List<OperationsInfo> operList = new ArrayList<OperationsInfo>();
		String sql = "";
		if (info.getBeginCreateTime() == null || "".equals(info.getBeginCreateTime())) {
			sql = bDOperationsWith24Sql(pars);
		} else {
			sql = bDOperationsWithSql(info, pars);
		}
		Query query = this.getEntityManager().createNativeQuery(sql);
		packageParamter(pars, query);
		List<Object[]> cwInfo = new ArrayList<Object[]>();
		cwInfo = query.getResultList();
		if (cwInfo != null && cwInfo.size() > 0) {
			for (Object[] row : cwInfo) {
				if (row[0] == null) {
					continue;
				}
				OperationsInfo res = new OperationsInfo();
				res.setRealName(row[0] == null ? "" : row[0] + "");
				res.setMobile(row[1] == null ? "" : row[1] + "");
				res.setTotalNum(Integer.parseInt(row[2] + ""));
				res.setVerifyNum(Integer.parseInt(row[3] + ""));
				operList.add(res);
			}
		}
		/*
		 * if(operList.size()>0) { getXlsFileForBDOperations(response,operList,info); }
		 */
		return operList;
	}

	@Override
	public int checkUser(String mobile) {
		if (StringUtils.isBlank(mobile)) {
			// 请输入手机号
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000048);
		}
		// 电话号码的验证 13[0-9] , 15[0-9] , 18[0-9], 11位数字
		if (!DataUtil.checkMobile(mobile)) {
			// 手机号码格式不正确!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000034);
		}

		final String jpql = "from User u where u.mobile= ? ";
		Query query = getEntityManager().createQuery(jpql);
		query.setParameter(1, mobile);
		List<User> users = query.getResultList();
		if (users != null && users.size() > 0) {
			User user = users.get(0);
			user.setState(0);// 审核通过
			this.getEntityManager().merge(user);

			// 发送短信
			// 您的房源宝账号已审核通过，赶快登录吧。【房源宝】
			// 发送短息
			String content = "您的房源宝账号已审核通过，赶快登录吧。【房源宝】";
			System.out.println(content);
			try {
				boolean inputLine = InfoUtils.sendSMS(mobile, content);
				// boolean inputLine = true;
				if (inputLine == false) {
					throw new LeoFault(UcConst.UC_ERROR100017);
				}
			} catch (Exception e) {
				e.printStackTrace();
				// 注册验证码发送失败
				throw new LeoFault(UcConst.UC_ERROR100017);
			}
		} else {
			// 手机号码已经注册
			// 1000049;// 该手机号码未注册!
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000049);
		}
		return 0;
	}

	@Override
	public int exportUserNum(HttpServletResponse response, Date start, Date end) {
		// Date start , Date end , int state 过滤数据的条件
		List<Date> pars = new ArrayList<Date>();
		String sql = "select A.*,B.houseNum,B.finishNum,B.checkNum, B.finedNum from "
				+ " ( SELECT u.realName,u.mobile, COUNT(1) houseSum, sum( CASE WHEN log.sourceState = 0 THEN 1 ELSE 0 END ) finishSum ,sum( CASE WHEN log.sourceState = 1 THEN 1 ELSE 0 END ) `checkSum` ,sum( CASE WHEN log.sourceState = 2 THEN 1 ELSE 0 END ) finedSum "
				+ " FROM sourcelog log JOIN USER u ON u.uid = log.userId "
				+ " GROUP BY u.realName,u.mobile ) A ,"
				+ " ( SELECT u.realName,u.mobile, COUNT(1) houseNum, sum( CASE WHEN log.sourceState = 0 THEN 1 ELSE 0 END ) finishNum ,sum( CASE WHEN log.sourceState = 1 THEN 1 ELSE 0 END ) checkNum ,sum( CASE WHEN log.sourceState = 2 THEN 1 ELSE 0 END ) finedNum "
				+ " FROM sourcelog log JOIN USER u ON u.uid = log.userId " + " where log.sourceLogId <>0 ";
		if (start != null) {
			sql += " and ( ( log.filshDate >= ? ";
			pars.add(start);
		}
		if (end != null) {
			sql += " and log.filshDate < ? )  or log.filshDate is null ) ";
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
				e.printStackTrace();
			}
		}
		return 0;
	}

	@Override
	public int exportSourceInfo(HttpServletResponse response, Date start, Date end, int state, int type) {
		return 0;
	}

	/**
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @param state
	 *            状态(1审核中, 0审核通过,2审核失败,-1全部)
	 * @param type
	 *            (1出售,2出租,-1全部)
	 * @return
	 */
	@Override
	public int exportSourceInfo(Date start, Date end, int state, int type) {
		return 0;
	}

	@Override
	public int improtSourceInfo(MultipartFile excel) {
		return 0;
	}

	/**
	 * 房源信息导入到数据库中
	 */
	@Override
	public int improtSourceInfo(String filePath) {

		return 0;
	}

	@Override
	public int improtInitSourceInfo(MultipartFile excel) {
		return 0;
	}

	/**
	 * 房源信息导入到数据库中(导入 初始房源信息)
	 */
	@Override
	public int improtInitSourceInfo(String filePath) {
		return 0;
	}

	/**
	 * 子 小区信息导入到数据库中
	 */
	@Override
	public int improtSubEstate(String filePath) {
		if (filePath != null && filePath.length() > 0) {
			filePath = filePath.trim();
			StringBuffer sb = new StringBuffer("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:" + filePath
					+ "\n");
			System.out.println(filePath);
			File file = new File(filePath);
			try {
				// 以下语句读取生成的Excel文件内容
				FileInputStream fIn = new FileInputStream(file);
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);
				HSSFCell cell = row.getCell(0);
				String city = cell.getStringCellValue();
				cell = row.getCell(1);
				String town = cell.getStringCellValue();
				cell = row.getCell(2);
				String estate = cell.getStringCellValue();
				cell = row.getCell(3);
				String address = cell.getStringCellValue();
				sb.append(city + "\t" + town + "\t" + estate + "\t" + address + "\n");
				System.out.println(city + "\t" + town + "\t" + estate + "\t" + address);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("行数:" + num);
				sb.append("行数:" + num + "\n");
				for (int i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate + ",开始解析入库");
							sb.append("i:" + i + "\t" + estate + ",开始解析入库\n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									// Estate es = estates.get(0);
									// SubEstate ees = new SubEstate(es);
									// ees.setRoad(null);
									// this.getEntityManager().persist(ees);
									// sb.append("subestateId:"+ees.getAreaId()+" "+estate+" parentId:"+es.getAreaId()+"\n");
								} else {
									System.out.println(i + ",父小区不存在:" + estate);
									sb.append(i + ",父小区不存在:" + estate + "\n");
								}
							}
						}
					}
				}
				System.out.println("解析入库完成.");
				sb.append("完成时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "解析入库完成.\n\n\n");
				File pathFile = new File(path + "/log");
				if (pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/import_subestate.log");
				FileOutputStream sof = new FileOutputStream(logFile, true);
				sof.write(sb.toString().getBytes());
				sof.flush();
				sof.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}

		return 0;
	}

	@Override
	public int improtEstate2(MultipartFile excel) {
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
				HSSFCell cell = row.getCell(0);
				String city = cell.getStringCellValue();
				cell = row.getCell(1);
				String town = cell.getStringCellValue();
				cell = row.getCell(2);
				String estate = cell.getStringCellValue();
				cell = row.getCell(3);
				String nong = "";
				if (cell != null) {
					nong = ActionExcel.changeCellToString(cell);
				}
				cell = row.getCell(4);
				String address = cell.getStringCellValue();
				sb.append(city + "\t" + town + "\t" + estate + "\t" + nong + "\t" + address + "\n");
				System.out.println(city + "\t" + town + "\t" + estate + "\t" + nong + "\t" + address);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("行数:" + num);
				sb.append("行数:" + num + "\n");
				for (int i = 1; i <= num; i++) {
					// 第一遍 读取所有的 父级菜单
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						nong = "";
						if (cell != null) {
							nong = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(4);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate + ",开始解析入库");
							sb.append("i:" + i + "\t" + estate + " \n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个父级小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									System.out.println(i + ",这个区域下面,已经存在父级小区:" + estate);
									sb.append(i + ",这个区域下面,已经存在父级小区:" + estate + "\n");
								} else {
									// 添加父级小区
									Estate estateNew = new Estate();
									estateNew.setCreateTime(new Date());
									estateNew.setName(estate);
									estateNew.setParentId(parentTown.getAreaId());
									this.getEntityManager().persist(estateNew);// 添加小区
									// 小区跟地址对应起来
									if (address != null) {
										String[] addrs = address.split("，");
										for (int j = 0; j < addrs.length; j++) {
											String addr = addrs[j].trim();
											Address addrNew = new Address();
											addrNew.setEstateId(estateNew.getAreaId());
											addrNew.setAddress(addr);
											this.getEntityManager().persist(addrNew);// 添加地址
										}
									}
								}
							}
						}
					}
				}
				sb.append("开始解析子小区\n");
				for (int i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						nong = "";
						if (cell != null) {
							nong = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(4);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate);
							sb.append("i:" + i + "\t 子小区: " + estate + " \n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									Estate es = estates.get(0);
									// SubEstate ees = new SubEstate(es);
									// ees.setRoad(nong);
									// this.getEntityManager().persist(ees);
									// sb.append("subestateId:"+ees.getAreaId()+" "+estate+" parentId:"+es.getAreaId()+"\n");
								} else {
									System.out.println(i + ",父小区不存在:" + estate);
									sb.append(i + ",父小区不存在:" + estate + "\n");
								}
							}
						}
					}
				}

				System.out.println("解析入库完成.");
				sb.append("完成时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "解析入库完成.\n\n\n");
				File pathFile = new File(path + "/log");
				if (pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/import_estate.log");
				FileOutputStream sof = new FileOutputStream(logFile, true);
				sof.write(sb.toString().getBytes());
				sof.flush();
				sof.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}

		return 0;
	}

	/**
	 * 小区信息导入到数据库中(带有二级地址的)
	 */
	@Override
	public int improtEstate2(String filePath) {
		if (filePath != null && filePath.length() > 0) {
			filePath = filePath.trim();
			StringBuffer sb = new StringBuffer("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:" + filePath
					+ "\n");
			System.out.println(filePath);
			File file = new File(filePath);
			try {
				// 以下语句读取生成的Excel文件内容
				FileInputStream fIn = new FileInputStream(file);
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);
				HSSFCell cell = row.getCell(0);
				String city = cell.getStringCellValue();
				cell = row.getCell(1);
				String town = cell.getStringCellValue();
				cell = row.getCell(2);
				String estate = cell.getStringCellValue();
				cell = row.getCell(3);
				String nong = "";
				if (cell != null) {
					nong = ActionExcel.changeCellToString(cell);
				}
				cell = row.getCell(4);
				String address = cell.getStringCellValue();
				sb.append(city + "\t" + town + "\t" + estate + "\t" + nong + "\t" + address + "\n");
				System.out.println(city + "\t" + town + "\t" + estate + "\t" + nong + "\t" + address);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("行数:" + num);
				sb.append("行数:" + num + "\n");
				for (int i = 1; i <= num; i++) {
					// 第一遍 读取所有的 父级菜单
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						nong = "";
						if (cell != null) {
							nong = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(4);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate + ",开始解析入库");
							sb.append("i:" + i + "\t" + estate + " \n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个父级小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									System.out.println(i + ",这个区域下面,已经存在父级小区:" + estate);
									sb.append(i + ",这个区域下面,已经存在父级小区:" + estate + "\n");
								} else {
									// 添加父级小区
									Estate estateNew = new Estate();
									estateNew.setCreateTime(new Date());
									estateNew.setName(estate);
									estateNew.setParentId(parentTown.getAreaId());
									this.getEntityManager().persist(estateNew);// 添加小区
									// 小区跟地址对应起来
									if (address != null) {
										String[] addrs = address.split("，");
										for (int j = 0; j < addrs.length; j++) {
											String addr = addrs[j].trim();
											Address addrNew = new Address();
											addrNew.setEstateId(estateNew.getAreaId());
											addrNew.setAddress(addr);
											this.getEntityManager().persist(addrNew);// 添加地址
										}
									}
								}
							}
						}
					}
				}
				sb.append("开始解析子小区\n");
				for (int i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						nong = "";
						if (cell != null) {
							nong = ActionExcel.changeCellToString(cell);
						}
						cell = row.getCell(4);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate);
							sb.append("i:" + i + "\t 子小区: " + estate + " \n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									Estate es = estates.get(0);
									// SubEstate ees = new SubEstate(es);
									// ees.setRoad(nong);
									// this.getEntityManager().persist(ees);
									// sb.append("subestateId:"+ees.getAreaId()+" "+estate+" parentId:"+es.getAreaId()+"\n");
								} else {
									System.out.println(i + ",父小区不存在:" + estate);
									sb.append(i + ",父小区不存在:" + estate + "\n");
								}
							}
						}
					}
				}

				System.out.println("解析入库完成.");
				sb.append("完成时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "解析入库完成.\n\n\n");
				File pathFile = new File(path + "/log");
				if (pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/import_estate.log");
				FileOutputStream sof = new FileOutputStream(logFile, true);
				sof.write(sb.toString().getBytes());
				sof.flush();
				sof.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}

		return 0;
	}

	/**
	 * 小区信息导入到数据库中
	 */
	@Override
	public int improtEstate(String filePath) {
		if (filePath != null && filePath.length() > 0) {
			filePath = filePath.trim();
			StringBuffer sb = new StringBuffer("时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 文件:" + filePath
					+ "\n");
			System.out.println(filePath);
			File file = new File(filePath);
			try {
				// 以下语句读取生成的Excel文件内容
				FileInputStream fIn = new FileInputStream(file);
				HSSFWorkbook readWorkBook = new HSSFWorkbook(fIn);
				HSSFSheet readSheet = readWorkBook.getSheetAt(0);
				HSSFRow row = readSheet.getRow(0);
				HSSFCell cell = row.getCell(0);
				String city = cell.getStringCellValue();
				cell = row.getCell(1);
				String town = cell.getStringCellValue();
				cell = row.getCell(2);
				String estate = cell.getStringCellValue();
				cell = row.getCell(3);
				String address = cell.getStringCellValue();
				sb.append(city + "\t" + town + "\t" + estate + "\t" + address + "\n");
				System.out.println(city + "\t" + town + "\t" + estate + "\t" + address);
				// 获取excel的行数
				int num = readSheet.getLastRowNum();
				System.out.println("行数:" + num);
				sb.append("行数:" + num + "\n");
				for (int i = 1; i <= num; i++) {
					row = readSheet.getRow(i);
					if (row != null) {
						cell = row.getCell(0);
						city = ActionExcel.changeCellToString(cell);
						cell = row.getCell(1);
						town = ActionExcel.changeCellToString(cell);
						cell = row.getCell(2);
						estate = ActionExcel.changeCellToString(cell);
						cell = row.getCell(3);
						address = ActionExcel.changeCellToString(cell);

						if (estate != null && !"".equals(estate)) {
							System.out.println("i:" + i + "\t" + estate + ",开始解析入库");
							sb.append("i:" + i + "\t" + estate + ",开始解析入库\n");
							CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
							CriteriaQuery<City> cq = cb.createQuery(City.class);
							Root<City> rootCity = cq.from(City.class);
							cq.where(cb.equal(rootCity.get(City_.name), city));
							List<City> citys = this.getEntityManager().createQuery(cq).getResultList();
							if (citys != null && citys.size() > 0) {
								int cityId = citys.get(0).getAreaId();
								Town parentTown;
								CriteriaQuery<Town> cqTown = cb.createQuery(Town.class);
								Root<Town> rootTown = cqTown.from(Town.class);
								cqTown.where(cb.equal(rootTown.get(Town_.name), town), cb.equal(rootTown.get(Town_.parentId), cityId));
								List<Town> towns = this.getEntityManager().createQuery(cqTown).getResultList();
								if (towns != null && towns.size() > 0) {
									parentTown = towns.get(0);
								} else {
									Town townNew = new Town();
									townNew.setCreateTime(new Date());
									townNew.setParentId(cityId);
									townNew.setName(town);
									this.getEntityManager().persist(townNew);// 添加片区
									parentTown = townNew;
								}
								// 检查是否存在这个小区
								String jpql = " from Estate e where e.name = ? and e.parentId = ? ";
								Query query = this.getEntityManager().createQuery(jpql);
								query.setParameter(1, estate);
								query.setParameter(2, parentTown.getAreaId());
								List<Estate> estates = query.getResultList();
								if (estates != null && estates.size() > 0) {
									System.out.println(i + ",这个区域下面,已经存在小区:" + estate);
									sb.append(i + ",这个区域下面,已经存在小区:" + estate + "\n");
								} else {
									// 添加小区
									Estate estateNew = new Estate();
									estateNew.setCreateTime(new Date());
									estateNew.setName(estate);
									estateNew.setParentId(parentTown.getAreaId());
									this.getEntityManager().persist(estateNew);// 添加小区
									// 小区跟地址对应起来
									if (address != null) {
										String[] addrs = address.split("，");
										for (int j = 0; j < addrs.length; j++) {
											String addr = addrs[j].trim();
											Address addrNew = new Address();
											addrNew.setEstateId(estateNew.getAreaId());
											addrNew.setAddress(addr);
											this.getEntityManager().persist(addrNew);// 添加地址
										}
									}
								}
							}
						}
					}
				}
				System.out.println("解析入库完成.");
				sb.append("完成时间: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "解析入库完成.\n\n\n");
				File pathFile = new File(path + "/log");
				if (pathFile.exists()) {
					pathFile.mkdirs();
				}
				File logFile = new File(path + "/log/import_init_estate.log");
				FileOutputStream sof = new FileOutputStream(logFile, true);
				sof.write(sb.toString().getBytes());
				sof.flush();
				sof.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return 1;
		}

		return 0;
	}

	/**
	 * 导出到excel
	 */
	@Override
	public String exportToExcel(ServletContext servletContext, String areaName) {

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		BasicDataSource dbs = (BasicDataSource) wac.getBean("dataSourceSql");
		String str = "";
		try {
			long starttime = System.currentTimeMillis();
			String xlsFile = "d:/fangyou_data/filter/" + areaName;
			HSSFWorkbook workbook = new HSSFWorkbook(); // 产生工作簿对象
			HSSFSheet sheet = workbook.createSheet(); // 产生工作表对象
			// 设置第一个工作表的名称为firstSheet
			// 为了工作表能支持中文，设置字符编码为UTF_16
			workbook.setSheetName(0, areaName);

			Connection con = dbs.getConnection();
			// String
			// sql=" select distinct e.DistrictName , e.EstateName ,p.address from Property p , Estate e where p.EstateID = e.EstateID and e.DistrictName = ? ORDER BY e.EstateName asc ";
			String sql = "  select distinct e.DistrictName , e.EstateName ,p.address from Property p , Estate e where p.EstateID = e.EstateID and e.DistrictName = ? "
					+ "and e.EstateName not like '%新里%' "
					+ "and e.EstateName not like '%老公寓%' "
					+ "and e.EstateName not like '%公房%' "
					+ "and e.EstateName not like '%商铺%' "
					+ "and e.EstateName not like '%办公楼%' "
					+ "and e.EstateName not like '%仓库%' "
					+ "and e.EstateName not like '%厂房%' "
					+ "and e.EstateName not like '%别墅%' "
					+ "and e.EstateName not like '%石库门%' "
					+ "and e.EstateName not like '%洋房%' " + " ORDER BY e.EstateName asc ";

			PreparedStatement sta = con.prepareStatement(sql);
			sta.setString(1, areaName);
			ResultSet res = sta.executeQuery();

			if (res != null) {
				System.out.println("取出数据...");
				int i = 0;

				// 设置 表格中 每一列的宽度
				sheet.setColumnWidth(0, 50 * 256);
				sheet.setColumnWidth(1, 20 * 256);

				// 产生一行
				HSSFRow rowTitle = sheet.createRow(i);

				// 产生第一个单元格
				HSSFCell cell1 = rowTitle.createCell(0);
				// 设置单元格内容为字符串型
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellValue("小区");

				cell1 = rowTitle.createCell(1);
				// 设置单元格内容为字符串型
				cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell1.setCellValue("栋座");

				while (res.next()) {
					String ename = res.getString("EstateName");
					String address = res.getString("address");

					System.out.println(ename + "," + address);

					// 产生一行
					HSSFRow row = sheet.createRow(i + 1);
					i++;
					// 产生第一个单元格
					HSSFCell cell = row.createCell(0);
					// 设置单元格内容为字符串型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(ename);

					cell = row.createCell(1);
					// 设置单元格内容为字符串型
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(address);

				}
				xlsFile += "-" + i + ".xls";
				FileOutputStream fOut = new FileOutputStream(xlsFile);
				workbook.write(fOut);
				fOut.flush();
				fOut.close();
				System.out.println("文件生成...");
			}
			sta.close();
			con.close();
			long endtime = System.currentTimeMillis();
			str = "数据导出成功! 用时:" + (endtime - starttime) + "ms, 文件路径:" + xlsFile;
			System.out.println(str);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return str;

	}

	/**
	 * 测试 其他的 数据库连接(sql server )
	 */
	public List<ResidenceInfo> sqlServertest(ServletContext servletContext) {
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		BasicDataSource dbs = (BasicDataSource) wac.getBean("dataSourceSql");
		// System.out.println(dbs.getDriverClassName());
		try {
			Connection con = dbs.getConnection();
			Statement sta = con.createStatement();
			String sql = " select top 100 DistrictName,Status,PropertyType,PropertyUsage,Remark,OwnerMobile,OwnerTel,PropertyNo,OwnerName from Property_copy where OwnerMobile like '%XXX%' and  Propertyno not like 'A%' order by propertyID asc ";
			// 分页的方法
			// select top pageSize * from Property where PropertyID not in (select top (pageSize*(pageNo-1)) propertyId from property order
			// by propertyId ) order by propertyId;
			ResultSet res = sta.executeQuery(sql);
			List<ResidenceInfo> infos = null;
			if (res != null) {
				while (res.next()) {
					ResidenceInfo info = new ResidenceInfo();
					info.setAreaTitle(res.getString("DistrictName"));
					info.setStatusTitle(res.getString("Status"));
					info.setTypeTitle(res.getString("PropertyType"));
					info.setDirectionTitle(res.getString("PropertyUsage"));
					info.setRemark(res.getString("Remark"));
					info.setOwnerMobile(res.getString("OwnerMobile"));
					info.setOwnerName(res.getString("OwnerName"));
					info.setOwnerTel(res.getString("OwnerTel"));
					info.setId(res.getString("PropertyNo"));
					if (infos == null) {
						infos = new ArrayList<ResidenceInfo>();
					}
					infos.add(info);
				}
			}
			con.close();
			return infos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

}
