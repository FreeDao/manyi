/**
 * 
 */
package com.manyi.hims.actionexcel.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.actionexcel.model.BDInfo;
import com.manyi.hims.actionexcel.model.CSInfo;
import com.manyi.hims.actionexcel.model.ExportInfo4User;
import com.manyi.hims.actionexcel.model.ExportUserInfo;
import com.manyi.hims.actionexcel.model.HourseHostInfo;
import com.manyi.hims.util.ExcelUtils;
import com.manyi.hims.util.IteratorWrapper;
import com.manyi.hims.util.ExcelUtils.IExcel;
import com.manyi.hims.util.IteratorWrapper.IteratorHandler;

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

	/**
	 * 反作弊 中介是房东
	 */
	@Override
	public List<ExportInfo4User> getExportInfo4User(int temp) {
		String jpql = "select agent.uid as uid,agent.realName as agentName,agent.mobile as agentMobile,hc.hostName as hostName,hc.hostMobile as hostMobile,"
				+ " hr.createTime as createTime,a.name estateName,a.road as road,h.building as building,h.floor as floor,h.room as room"
				+ " from User agent join HouseResourceHistory hr on (agent.uid = hr.userId and hr.createTime is not null)"
				+ " join HouseResourceContact hc on (hc.houseId = hr.houseId and agent.mobile = hc.hostMobile)"
				+ " join House h on (h.houseId = hr.houseId)"
				+ " join Area a on (h.estateId = a.areaId and a.DTYPE = 'Estate')"
				+ " where agent.uid in"
				+ " (select al.uid as uid from"
				+ " (select agent.uid as uid,agent.realName as agentName,agent.mobile as agentMobile,hc.hostName as hostName,hc.hostMobile as hostMobile,"
				+ " hr.createTime as createTime,a.name estateName,a.road as road,h.building as building,h.floor as floor,h.room as room"
				+ " from User agent join HouseResourceHistory hr on (agent.uid = hr.userId and hr.createTime is not null)"
				+ " join HouseResourceContact hc on (hc.houseId = hr.houseId and agent.mobile = hc.hostMobile)"
				+ " join House h on (h.houseId = hr.houseId)"
				+ " join Area a on (h.estateId = a.areaId and a.DTYPE = 'Estate')"
				+ " order by agent.mobile) al"
				+ " group by al.uid,al.agentName,al.agentMobile,al.hostName,al.hostMobile"
				+ " having count(al.createTime) = " + temp + " ) " + " order by agent.mobile";

		Query query = this.getEntityManager().createNativeQuery(jpql);
		List<Object[]> subes = query.getResultList();
		List<ExportInfo4User> resultList = new ArrayList<ExportInfo4User>();
		if (subes != null && subes.size() > 0) {
			for (Object[] row : subes) {
				resultList.add(initExportInfo4User(row));
			}
		}
		return resultList;
	}

	/**
	 * 反作弊 房东多套房
	 */
	@Override
	public List<ExportUserInfo> getExportUserInfo(int temp) {
		List<ExportUserInfo> resultList = new ArrayList<ExportUserInfo>();
		String jpql = "select re.houseId as houseId,re.hostName as hostName,re.hostMobile as hostMobile,"
				+ " max(re.createTime) as createTime,re.estateName as estateName,re.road as road,re.building as building,re.floor as floor,re.room as room "
				+ " from ( select ln.houseId as houseId,ln.hostName as hostName,ln.hostMobile as hostMobile,"
				+ " ln.createTime as createTime,ln.estateName as estateName,ln.road as road,ln.building as building,ln.floor as floor,ln.room as room "
				+ " from ( select hc.houseId as houseId,hc.hostName as hostName,hc.hostMobile as hostMobile,"
				+ " hr.createTime as createTime,a.name estateName,a.road as road,h.building as building,h.floor as floor,h.room as room"
				+ " from HouseResourceContact hc"
				+ " join HouseResourceHistory hr on (hc.houseId = hr.houseId and hr.createTime is not null)"
				+ " join House h on (h.houseId = hr.houseId)" + " join Area a on (h.estateId = a.areaId and a.DTYPE = 'Estate')"
				+ " ) ln where hostMobile in ( %s ) ) re" + " group by re.houseId";

		List<String> userMobileList = getUserMobileList();
		if (userMobileList == null || userMobileList.size() == 0) {
			return resultList;
		}
		
		IteratorWrapper.pagination(userMobileList, 200).iterator(new IteratorHandler<String>() {
			
			@Override
			public boolean handle(int pageNum, List<String> subData, Object... params) {
				EntityManager entityManager = (EntityManager) params[0];
				String jpql = (String) params[1];
				List<ExportUserInfo> resultList = (List<ExportUserInfo>) params[2];
				
				String mobileIn = StringUtils.join(subData.subList(0, subData.size() - 2), ",");
				mobileIn = mobileIn + subData.get(subData.size() -1);
				Query query = entityManager.createNativeQuery(String.format(jpql, mobileIn));
				List<Object[]> subes = query.getResultList();
				if (subes != null && subes.size() > 0) {
					for (Object[] row : subes) {
						resultList.add(initExportUserInfo(row));
					}
				}
				
				return true;
			}
		},this.getEntityManager(),jpql,resultList);
		
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
				+ " join HouseResourceHistory hr on (agent.uid = hr.userId and hr.createTime is not null)"
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
				+ "\' ,'%Y-%m-%d %H:%i:%s') and createTime >= date_format( \'"
				+ startTime + "\' ,'%Y-%m-%d %H:%i:%s')" + " and checkNum > 0) eh) r " + " group by r.employeeId";

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
		exportInfo4User.setUid(format4Null(row[0]));
		exportInfo4User.setAgentName(format4Null(row[1]));
		exportInfo4User.setAgentMobile(format4Null(row[2]));
		exportInfo4User.setHostName(format4Null(row[3]));
		exportInfo4User.setHostMobile(format4Null(row[4]));
		exportInfo4User.setCreateTime(format4Null(row[5]));
		exportInfo4User.setEstateName(format4Null(row[6]));
		exportInfo4User.setRoad(format4Null(row[7]));
		exportInfo4User.setBuilding(format4Null(row[8]));
		exportInfo4User.setFloor(format4Null(row[9]));
		exportInfo4User.setRoom(format4Null(row[10]));
		return exportInfo4User;
	}

	private ExportUserInfo initExportUserInfo(Object[] row) {
		ExportUserInfo exportUserInfo = new ExportUserInfo();
		exportUserInfo.setHouseId(format4Null(row[0]));
		exportUserInfo.setHostName(format4Null(row[1]));
		exportUserInfo.setHostMobile(format4Null(row[2]));
		exportUserInfo.setCreateTime(format4Null(row[3]));
		exportUserInfo.setEstateName(format4Null(row[4]));
		exportUserInfo.setRoad(format4Null(row[5]));
		exportUserInfo.setBuilding(format4Null(row[6]));
		exportUserInfo.setFloor(format4Null(row[7]));
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
}
