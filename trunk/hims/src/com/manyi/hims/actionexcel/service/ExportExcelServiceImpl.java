/**
 * 
 */
package com.manyi.hims.actionexcel.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DateUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.actionexcel.model.BDInfo;
import com.manyi.hims.actionexcel.model.BDWorkCountModel;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.ExportInfo4User;
import com.manyi.hims.actionexcel.model.ExportUserInfo;
import com.manyi.hims.actionexcel.model.HourseHostInfo;
import com.manyi.hims.actionexcel.model.MainRequest;
import com.manyi.hims.actionexcel.model.User2infoRequest;
import com.manyi.hims.area.service.AreaService;
import com.manyi.hims.util.EntityUtils.HouseStateEnum;
import com.manyi.hims.util.EntityUtils.SourceLogType;
import com.manyi.hims.util.ExcelUtils;
import com.manyi.hims.util.ExcelUtils.IExcel;

/**
 * @author zxc
 */
@SuppressWarnings("unchecked")
@Service(value = "exportExcelService")
@Scope(value = "singleton")
public class ExportExcelServiceImpl extends BaseService implements ExportExcelService {

	final static Logger log = LoggerFactory.getLogger(ExportExcelServiceImpl.class);

	final static String xls_format = "export-hourseHostInfo-";
	final static String[] title = { "房主姓名", "房主手机", "总房产数量", "房子id", "经纪人姓名", "经纪人手机" };

	@Autowired
	private AreaService areaService;
	
	/**
	 * 反作弊 中介是房东
	 */
	@Override
	public List<ExportInfo4User> getExportInfo4User(User2infoRequest info) {
		log.info(JSONObject.fromObject(info).toString());
		
		String tempTels = "";
		String tempNames = "";
		String dateStr = "";
		
		if (!"".equals(info.getUserTels())) {
			tempTels = " and mobile in (" + changeStr(info.getUserTels()) + ") ";
		}
		
		if (!"".equals(info.getUserNames())) {
			tempNames = " and realName in (" + changeStr(info.getUserNames()) + ") ";
		}
		
		if (!"".equals(info.getStart())) {
			dateStr += " and hrh.publishDate >= str_to_date('" + info.getStart() + "','%Y-%m-%d')";
		}
		if (!"".equals(info.getEnd())) {
			dateStr += " and hrh.publishDate <= str_to_date('" + info.getEnd() + "','%Y-%m-%d')";
		}
		
		StringBuffer jpqlsb = new StringBuffer();
		
		jpqlsb.append("select a.houseId, estateName, building, room, hostMobile, realName, mobile, userId, publishDate, currenthouseState, houseState, status from");
		jpqlsb.append("    (select h.houseId, estateName, building, room, hostMobile from ");
		jpqlsb.append("         (select houseId, estateId, serialCode, building, room, status from house where serialCode like :serialCode) h, ");
		jpqlsb.append("         (select areaId, name estateName from area a where a.status = 1 and serialCode like :serialCode) estate, ");
		jpqlsb.append("         (select houseId, hostMobile from houseresourcecontact where enable = 1 and status <> 3) hsc ");
		jpqlsb.append("     where estate.areaId = h.estateId and h.houseId = hsc.houseId) a, ");
		jpqlsb.append("    (select realName, mobile, userId, publishDate, currenthouseState, houseState, status, hrh.houseId from ");
//		jpqlsb.append("         (select u.realName, u.mobile, u.uid from user u where u.disable = 1 and u.state = 1 and u.ifInner = 0 " + tempTels + tempNames + ") u, "); 
		jpqlsb.append("         (select u.realName, u.mobile, u.uid from user u where u.ifInner = 0 " + tempTels + tempNames + ") u, "); 
		jpqlsb.append("         (select hrh.houseId, hrh.publishDate, hrh.houseState, hrh.status, hrh.userId from HouseResourceHistory hrh where hrh.checkNum = 0 and hrh.status <> 4 and hrh.userId <> 0 " + dateStr + ") hrh, ");
		jpqlsb.append("         (select houseId, houseState currenthouseState from houseresource where status <> 4) hr ");
		jpqlsb.append("     where u.uid = hrh.userId and hrh.houseId = hr.houseId) b ");
		jpqlsb.append("where a.houseId = b.houseId order by a.houseId ");
			
		Query query = this.getEntityManager().createNativeQuery(jpqlsb.toString());
		log.info(jpqlsb.toString());
		
		query.setParameter("serialCode", areaService.getSerialCodeByAreaId((info.getAreaId() == 0 ? info.getCityType() : info.getAreaId())) + "%");
		log.info(areaService.getSerialCodeByAreaId((info.getAreaId() == 0 ? info.getCityType() : info.getAreaId())) + "%");
		
		List<Object[]> subes = query.getResultList();
		log.info("导出数据行数：" + subes.size());
		
		List<ExportInfo4User> resultList = new ArrayList<ExportInfo4User>();

		if (subes != null && subes.size() > 0) {
			for (Object[] row : subes) {
				resultList.add(initExportInfo4User(row));
			}
		}
		return resultList;
	}
	
	/**
	 * @date 2014年5月29日 下午4:23:13
	 * @author Tom
	 * @description  
	 * 将输入参数进行数据库所用的(1,2,3)格式
	 */
	private String changeStr(String str) {
		String[] s = str.split("/");
		String returnStr = "";
		
		for (String temp : s) {
			if ("".equals(temp)) {
				continue;
			}
			returnStr += "'" + temp + "',";
		}
		
		if (returnStr.length() > 0) {
			returnStr = returnStr.substring(0, returnStr.length() - 1);
		}
		return returnStr;
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
	@Override
	public List<ExportUserInfo> getExportUserInfo(MainRequest req) {
		
		log.info(" 反作弊:房东多套房  参数: " +ReflectionToStringBuilder.toString(req));
		
		List<ExportUserInfo> resultList = new ArrayList<ExportUserInfo>();
		
		String sql="SELECT hrh.houseId, hc.hostName, hc.hostMobile, hrh.historyId, hrh.actionType, hrh.houseState, hrh.status ,h.room, h.building, estate.name estateName , u.realName, u.mobile, u.ifInner , hrh.publishDate,userArea.name userAreaName "+
				" FROM houseresourcecontact AS hc , houseresourcehistory AS hrh , house AS h , area AS estate ,area town,area dis , user AS u,area userArea "+
				" WHERE hc.historyId = hrh.historyId AND hrh.checkNum = 0 AND hrh.houseId = h.houseId AND h.estateId = estate.areaId AND estate.parentId = town.areaId AND town.parentId = dis.areaId AND hrh.userId = u.uid AND u.areaId = userArea.areaId AND "+ 
				" dis.parentId = ?  AND "+
				" hc.hostMobile in ( SELECT hostMobile from (SELECT count(hct.contactId) num ,hct.hostMobile from houseresourcecontact hct join houseresourcehistory history ON hct.historyId = history.historyId  "+
				" where hct.enable = 1 AND DATE_FORMAT(history.publishDate, '%Y-%m-%d') >= '"+req.getStartDate()+"' AND DATE_FORMAT(history.publishDate, '%Y-%m-%d') < '"+req.getEndDate()+"' GROUP BY hct.hostMobile) h where h.num > ? ) ";

		if(!StringUtils.isBlank(req.getStartDate())){
			sql +=" AND DATE_FORMAT( hrh.publishDate ,'%Y-%m-%d') >= '"+req.getStartDate()+"'";
		}
		if(!StringUtils.isBlank(req.getEndDate())){
			sql +=" AND DATE_FORMAT( hrh.publishDate ,'%Y-%m-%d') < '"+req.getEndDate()+"'";
		}
		sql += " ORDER BY hc.hostMobile ASC , hrh.publishDate ASC ";
		Query query = this.getEntityManager().createNativeQuery(sql);
		query.setParameter(1, req.getCityType()).setParameter(2, req.getNumber());
		List<Object[]> rows= query.getResultList();
		if(rows != null && rows.size()>0){
			for (int i = 0; i < rows.size(); i++) {
				Object[] row = rows.get(i);
				ExportUserInfo info =new ExportUserInfo();
				int k=0;
				info.setHouseId(row[k++]+"");
				info.setHostName((row[k] == null ? "" : row[k]+""));
				k++;
				info.setHostMobile(row[k++]+"");
				info.setHistoryId(row[k++]+"");
				info.setActionType(Integer.parseInt((row[k] == null ? "0" : row[k]+"")));
				k++;
				info.setHouseState(Integer.parseInt((row[k] == null ? "0" : row[k]+"")));
				k++;
				info.setStatus(Integer.parseInt((row[k] == null ? "0" : row[k]+"")));
				k++;
				info.setRoom(row[k++]+"");
				info.setBuilding(row[k++]+"");
				info.setEstateName(row[k++]+"");
				info.setUserName(row[k++]+"");
				info.setUserMobile(row[k++]+"");
				info.setIfInner(Integer.parseInt((row[k] == null ? "0" : row[k]+"")));
				k++;
				info.setPublishDate((Date) row[k++]);
				info.setUserAreaStr(row[k++] + "");
				resultList.add(info);
			}
		}
		
		return resultList;
	}

	private List<String> getUserMobileList() {
		String jpql = "select al.hostMobile as hostMobile from "
				+ " (select tb.houseId as houseId,tb.hostName as hostName,tb.hostMobile as hostMobile,"
				+ " max(tb.createTime) as createTime,tb.estateName as estateName,tb.road as road,tb.building as building,tb.floor as floor,tb.room as room from "
				+ " (select hc.houseId as houseId,hc.hostName as hostName,hc.hostMobile as hostMobile,"
				+ " hr.createTime as createTime,a.name estateName,a.road as road,h.building as building,h.floor as floor,h.room as room"
				+ " from HouseResourceContact hc"
				+ " join HouseResourceHistory hr on (hc.houseId = hr.houseId and hr.createTime is not null)"
				+ " join House h on (h.houseId = hr.houseId)" + " join Area a on (h.estateId = a.areaId and a.DTYPE = 'Estate')"
				+ " order by hc.hostMobile " + " ) tb group by tb.houseId" + " ) al group by al.hostMobile"
				+ " having count(al.houseId) > 2";
		Query query = this.getEntityManager().createNativeQuery(jpql);
		List<Object[]> subes = query.getResultList();
		List<String> resultList = new ArrayList<String>();
		if (subes != null && subes.size() > 0) {
			for (Object row : subes) {
				resultList.add(format4Null(row));
			}
		}
		return resultList;
	}

	/**
	 * BD数据查询
	 */
	@Override
	public List<BDInfo> getBDInfoList(String mobile) {
		String jpql = "select su.bdName,su.createTime,su.uid,su.realName,su.mobile,su.rentSum,su.sellSum,su.modifySum,su.reportSum,"
				+ " su.rentSec,su.sellSec,su.modifySec,su.reportSec,su.rentSum+su.sellSum+su.modifySum+su.reportSum as allSum,"
				+ " su.rentSec+su.sellSec+su.modifySec+su.reportSec as allSec from "
				+ " (select re.bdName as bdName,re.createTime as createTime,re.uid as uid,re.realName as realName,re.mobile as mobile,"
				+ " sum(CASE WHEN re.actionType='1' AND (re.houseState='1' OR re.houseState='3') THEN 1 ELSE 0 END) as rentSum,"
				+ " sum(CASE WHEN re.actionType='1' AND re.houseState='2' THEN 1 ELSE 0 END) as sellSum,"
				+ " sum(CASE WHEN re.actionType='2' THEN 1 ELSE 0 END) as modifySum,"
				+ " sum(CASE WHEN re.actionType='3' THEN 1 ELSE 0 END) as reportSum,"
				+ " sum(CASE WHEN re.actionType='1' AND (re.houseState='1' OR re.houseState='3') AND re.status='1' THEN 1 ELSE 0 END) as rentSec,"
				+ " sum(CASE WHEN re.actionType='1' AND re.houseState='2' AND re.status='1' THEN 1 ELSE 0 END) as sellSec,"
				+ " sum(CASE WHEN re.actionType='2' AND re.status='1' THEN 1 ELSE 0 END) as modifySec,"
				+ " sum(CASE WHEN re.actionType='3' AND re.status='1' THEN 1 ELSE 0 END) as reportSec from"
				+ " (select bd.realName as bdName,agent.uid as uid,agent.realName as realName,agent.mobile as mobile,hr.actionType as actionType,hr.status as status,"
				+ " hr.createTime as createTime,hr.checkNum as checkNum,hr.houseState as houseState"
				+ " from User bd join User agent on (agent.ifInner = 0 and agent.disable = 1 and bd.spreadId = agent.spreadName)"
				+ " left join HouseResourceHistory hr on (agent.uid = hr.userId and hr.createTime is not null)"
				+ " where bd.ifInner=1 and bd.disable = 1) re" + " group by re.uid) su";

		if (StringUtils.isNotEmpty(mobile)) {
			jpql = jpql + " where su.mobile = " + mobile;
		}

		Query query = this.getEntityManager().createNativeQuery(jpql);
		List<Object[]> subes = query.getResultList();
		List<BDInfo> resultList = new ArrayList<BDInfo>();
		if (subes != null && subes.size() > 0) {
			for (Object[] row : subes) {
				resultList.add(initBDInfo(row));
			}
		}
		return resultList;
	}

	/**
	 * 每个客服业绩考核报表
	 */
	@Override
	public List<CSInfo> getCSInfoList(String startTime, String endTime) {
		List<CSInfo> resultList = new ArrayList<CSInfo>();
		if (StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)) {
			return resultList;
		}
		String jpql = "select r.employeeId,r.realName,r.userName,sum(r.success) as success,sum(r.ing) as ing,sum(r.faild) as faild,sum(r.success)+sum(r.ing)+sum(r.faild) as allCount from "
				+ " (select eh.employeeId as employeeId,eh.realName as realName,eh.userName as userName,eh.houseId as houseId,"
				+ " (CASE WHEN eh.status='1' THEN '审核通过' WHEN eh.status='2' THEN '再审核' ELSE '审核失败' END) as statusText,"
				+ " (CASE WHEN ((eh.status='1' and eh.actionType in (1,2)) or ( eh.status='3' and eh.actionType in (4) )) THEN 1 ELSE 0 END) as success,"
				+ " (CASE WHEN eh.status='2' THEN 1 ELSE 0 END) as ing,"
				+ " (CASE WHEN ((eh.status='3' and eh.actionType in (1,2)) or ( eh.status='1' and eh.actionType in (4) )) THEN 1 ELSE 0 END) as faild from "
				+ " (select e.employeeId as employeeId,e.realName as realName,e.userName as userName,hr.houseId as houseId,hr.createTime as createTime,hr.status as status,hr.checkNum as checkNum,hr.actionType as actionType"
				+ " from employee e join HouseResourceHistory hr on (e.employeeId =hr.operatorId)"
				+ " group by hr.historyId"
				+ " having createTime <= date_format( \'"
				+ endTime
				+ "\' ,'%Y-%m-%d %H:%i:%S') and createTime >= date_format( \'"
				+ startTime + "\' ,'%Y-%m-%d %H:%i:%S')" + " and checkNum > 0) eh) r " + " group by r.employeeId";

		Query query = this.getEntityManager().createNativeQuery(jpql);
		List<Object[]> subes = query.getResultList();
		if (subes != null && subes.size() > 0) {
			for (Object[] row : subes) {
				resultList.add(initCSInfo(row));
			}
		}
		return resultList;
	}

	@Override
	public List<HourseHostInfo> getHourseHostInfoList(int _count) {
		String jpql = "select count(host.hostName) as hostCount,host.hostName,host.hostMobile,host.houseId,user.realName,user.mobile"
				+ " from (select * from Sourcehost sh order by sh.sourceHostId)"
				+ " host join SourceInfo info on (host.houseId = info.houseId and info.DTYPE = 'SellInfo')"
				+ " join SourceLog log on (log.sourceLogId = host.sourceLogId and log.DTYPE = 'SellLog')"
				+ " join User user on (log.userId = user.uid) " + " group by host.hostMobile " + " having hostCount > " + _count;

		Query query = this.getEntityManager().createNativeQuery(jpql);
		List<Object[]> subes = query.getResultList();
		List<HourseHostInfo> resultList = new ArrayList<HourseHostInfo>();
		if (subes != null && subes.size() > 0) {
			for (Object[] row : subes) {
				resultList.add(initHourceHostInfo(row));
			}
		}
		return resultList;
	}

	@Override
	public boolean exportHourseHostInfo(HttpServletResponse response, List<HourseHostInfo> list) {
		return ExcelUtils.exportExcel(response, list, "export-HourseHostInfo-", new IExcel<HourseHostInfo>() {

			@Override
			public void initHSSRow(List<HourseHostInfo> list, HSSFSheet sheet) {
				HSSFCell cell;
				for (int j = 0; j < list.size(); j++) {
					HourseHostInfo row = list.get(j);
					if (row == null) {
						continue;
					}
					HSSFRow hssrow = sheet.createRow(j + 1);
					cell = hssrow.createCell(0);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostName() + "");
					cell = hssrow.createCell(1);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostMobile() + "");
					cell = hssrow.createCell(2);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHostCount() + "");
					cell = hssrow.createCell(3);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getHouseId() + "");
					cell = hssrow.createCell(4);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getRealName() + "");
					cell = hssrow.createCell(5);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellValue(row.getMobile() + "");
				}
			}
		}, title);
	}

	private HourseHostInfo initHourceHostInfo(Object[] row) {
		HourseHostInfo hourseHostInfo = new HourseHostInfo();
		hourseHostInfo.setHostCount(Integer.valueOf(format4Null(row[0])));
		hourseHostInfo.setHostName(format4Null(row[1]));
		hourseHostInfo.setHostMobile(Long.valueOf(format4Null(row[2])));
		hourseHostInfo.setHouseId(Long.valueOf(format4Null(row[3])));
		hourseHostInfo.setRealName(format4Null(row[4]));
		hourseHostInfo.setMobile(Long.valueOf(format4Null(row[5])));
		return hourseHostInfo;
	}

	private BDInfo initBDInfo(Object[] row) {
		BDInfo bdInfo = new BDInfo();
		bdInfo.setBdName(format4Null(row[0]));
		bdInfo.setCreateTime(format4Null(row[1]));
		bdInfo.setUid(format4Null(row[2]));
		bdInfo.setRealName(format4Null(row[3]));
		bdInfo.setMobile(format4Null(row[4]));
		bdInfo.setRentSum(format4Null(row[5]));
		bdInfo.setSellSum(format4Null(row[6]));
		bdInfo.setModifySum(format4Null(row[7]));
		bdInfo.setReportSum(format4Null(row[8]));
		bdInfo.setRentSec(format4Null(row[9]));
		bdInfo.setSellSec(format4Null(row[10]));
		bdInfo.setModifySec(format4Null(row[11]));
		bdInfo.setReportSec(format4Null(row[12]));
		bdInfo.setAllSum(format4Null(row[13]));
		bdInfo.setAllSec(format4Null(row[14]));
		return bdInfo;
	}

	private ExportInfo4User initExportInfo4User(Object[] row) {
		ExportInfo4User exportInfo4User = new ExportInfo4User();
		exportInfo4User.setHouseId(format4Null(row[0]));
		exportInfo4User.setEstateName(format4Null(row[1]));
		exportInfo4User.setBuilding(format4Null(row[2]));
		exportInfo4User.setRoom(format4Null(row[3]));

		exportInfo4User.setCurrentHouseState(HouseStateEnum.getByValue((Integer)row[9]).getDesc());
		exportInfo4User.setPublishHouseState(HouseStateEnum.getByValue((Integer)row[10]).getDesc());
		
		exportInfo4User.setHostMobile(format4Null(row[4]));
		exportInfo4User.setAgentName(format4Null(row[5]));
		exportInfo4User.setAgentMobile(format4Null(row[6]));
		
		exportInfo4User.setPublishDate(DateUtil.formatDate("yyyy/MM/dd hh:mm:ss", (Date)row[8]));
		exportInfo4User.setState(SourceLogType.getByValue((Integer)row[11]).getDesc());
		
		return exportInfo4User;
	}


	private ExportUserInfo initExportUserInfo(Object[] row) {
		ExportUserInfo exportUserInfo = new ExportUserInfo();
		exportUserInfo.setHouseId(format4Null(row[0]));
		exportUserInfo.setHostName(format4Null(row[1]));
		exportUserInfo.setHostMobile(format4Null(row[2]));
//		exportUserInfo.setCreateTime(format4Null(row[3]));
		exportUserInfo.setEstateName(format4Null(row[4]));
//		exportUserInfo.setRoad(format4Null(row[5]));
		exportUserInfo.setBuilding(format4Null(row[6]));
//		exportUserInfo.setFloor(format4Null(row[7]));
		exportUserInfo.setRoom(format4Null(row[8]));
		return exportUserInfo;
	}

	private CSInfo initCSInfo(Object[] row) {
		CSInfo csInfo = new CSInfo();
		csInfo.setEmployeeId(Integer.valueOf(format4Null(row[0])));
		csInfo.setRealName(format4Null(row[1]));
		csInfo.setUserName(format4Null(row[2]));
		csInfo.setSuccess(format4Null(row[3]));
		csInfo.setIng(format4Null(row[4]));
		csInfo.setFaild(format4Null(row[5]));
		csInfo.setAllCount(format4Null(row[6]));
		return csInfo;
	}

	private String format4Null(Object obj) {
		return obj == null ? StringUtils.EMPTY : obj + StringUtils.EMPTY;
	}
	
	@Override
	public List<BDWorkCountModel> getBDWorkCount(int areaId, int cityId, String startDate,String endDate) {
		List<Object> pars = new ArrayList<Object>();
		String sql = "SELECT bd.uid,bd.realName, bd.mobile FROM user bd where bd.ifInner = 1 and bd.disable=1 ";
		if(areaId!=0){
			sql += " and bd.areaId=?";
			pars.add(areaId);
		}
		if(cityId!=0){
			sql += " and bd.cityId=?";
			pars.add(cityId);
		}
		sql += " ORDER BY bd.mobile";
		Query query = getEntityManager().createNativeQuery(sql);
		if (pars!=null && pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i+1, pars.get(i));
			}
		}
		List<Object[]> listObj = query.getResultList();
		List<BDWorkCountModel> bdList = new ArrayList<BDWorkCountModel>();
		if(listObj !=null){
			for (int i=0;i<listObj.size();i++) {
				BDWorkCountModel model = new BDWorkCountModel();
				Object[] rows = listObj.get(i);
				int uid = (int)rows[0];
				String realName = rows[1]==null ? "" : rows[1].toString();
				String mobile = rows[2]==null ? "" : rows[2].toString();
				//直接推广
				//String sql1 = "select count(u.uid) from user u where u.spreadName = ?";
				List<Object> paramter = new ArrayList<Object>();
				String sql1 = "select count(u.uid) from user u LEFT JOIN userhistory uh on u.uid=uh.userId where u.spreadName = ? and u.state=1 and uh.actionType=1 ";
				if(StringUtils.isNotBlank(startDate)){
					sql1 += " and  DATE_FORMAT(uh.addTime,'%Y-%m-%d') day>=?";
					paramter.add(startDate);
				}
				if(StringUtils.isNotBlank(endDate)){
					sql1 += " and  DATE_FORMAT(uh.addTime,'%Y-%m-%d') day <=?";
					paramter.add(endDate);
				}
				Query query1 = getEntityManager().createNativeQuery(sql1);
				query1.setParameter(1, mobile);
				if(paramter!=null && paramter.size()>0){
					for (int j = 1; j < paramter.size(); j++) {
						query1.setParameter(j+1, paramter.get(j));
					}
				}
				BigInteger count = (BigInteger)query1.getSingleResult();
				int directMarket = count.intValue();
				
				//间接推广
				//String sql2 = "SELECT count(u2.uid) from user u2 where u2.spreadName in( select u1.spreadId from user u1 where u1.spreadName = ?)";
				String sql2 = "SELECT count(u2.uid) from user u2 where u2.spreadName in( select u1.spreadId from user u1 LEFT JOIN userhistory uh on u1.uid=uh.userId  where u1.spreadName = ? and u1.state=1 and uh.actionType=1 )";
				if(StringUtils.isNotBlank(startDate)){
					//"and uh.addTime>=? and uh.addTime<=?"
					sql1 += " and  DATE_FORMAT(uh.addTime,'%Y-%m-%d') day>=?";
					paramter.add(startDate);
				}
				if(StringUtils.isNotBlank(endDate)){
					sql1 += " and  DATE_FORMAT(uh.addTime,'%Y-%m-%d') day <=?";
					paramter.add(endDate);
				}
				Query query2 = getEntityManager().createNativeQuery(sql2);
				query2.setParameter(1, mobile);
				if(paramter!=null && paramter.size()>0){
					for (int j = 1; j < paramter.size(); j++) {
						query2.setParameter(j+1, paramter.get(j));
					}
				}
				BigInteger count1 = (BigInteger)query2.getSingleResult();
				int indirectPromotion = count1.intValue();
				
				//活跃中介
				String sql3 = "SELECT COUNT(*) from (SELECT count(hrh.historyId) publishNum  from houseresourcehistory hrh join (select u.uid from user u where u.spreadName = ?) t on t.uid = hrh.userId where hrh.checkNum =0 and hrh.status =1 and hrh.actionType in (1,2,3) GROUP BY t.uid ) t1 where t1.publishNum >10";
				Query query3 = getEntityManager().createNativeQuery(sql3);
				query3.setParameter(1, mobile);
				BigInteger count2 = (BigInteger)query3.getSingleResult();
				int activeUser = count2.intValue();
				
				
				//发布出售量
				String sql_sell = "SELECT COUNT(*) from houseresourcehistory hrh join (select u.uid from user u where u.spreadName = ?) t on t.uid = hrh.userId where hrh.checkNum =0 and hrh.status =1 and hrh.actionType =1 and hrh.houseState =2";
				Query query_sell = getEntityManager().createNativeQuery(sql_sell);
				query_sell.setParameter(1, mobile);
				BigInteger count3 = (BigInteger)query_sell.getSingleResult();
				int createSellCount = count3.intValue();
				
				//发布出租量
				String sql_rent = "SELECT COUNT(*) from houseresourcehistory hrh join (select u.uid from user u where u.spreadName = ?) t on t.uid = hrh.userId where hrh.checkNum =0 and hrh.status =1 and hrh.actionType =1 and hrh.houseState =1 ";
				Query query_rent = getEntityManager().createNativeQuery(sql_rent);
				query_rent.setParameter(1, mobile);
				BigInteger count4 = (BigInteger)query_rent.getSingleResult();
				int createRentCount = count4.intValue();
				
				model.setName(realName);
				model.setDirectMarket(directMarket);
				model.setIndirectPromotion(indirectPromotion);
				model.setActiveUser(activeUser);
				model.setCreateSellCount(createSellCount);
				model.setCreateRentCount(createRentCount);
				bdList.add(model);
			}
		}
		
		return bdList;
	}
}
