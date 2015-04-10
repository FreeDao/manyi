package com.manyi.hims.actionexcel.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.actionexcel.model.BDWorkDailyModel;
import com.manyi.hims.actionexcel.model.UserAliPayExcelModel;
import com.manyi.hims.actionexcel.model.UserExcelRequest;
import com.manyi.hims.actionexcel.model.UserPublishExcelModel;
import com.manyi.hims.actionexcel.model.UserRegisterExcelModel;

@Service(value = "userExcelService")
@Scope(value = "singleton")
public class UserExcelServiceImpl extends BaseService implements UserExcelService{
	
	/**
	 * 得到截止当前时间绑定支付宝数目的存量
	 */
	public UserAliPayExcelModel getAliExcel() {
		int had = DataUtil.toInt(getEntityManager().createNativeQuery("SELECT COUNT(uid)  FROM `user` WHERE (`user`.account is NOT NULL and `user`.account <> '') and `user`.state = 1 and `user`.`disable` = TRUE" ).getSingleResult());
		int no = DataUtil.toInt(getEntityManager().createNativeQuery("SELECT COUNT(uid)  FROM `user` WHERE (`user`.account is  NULL or `user`.account = '') and `user`.state = 1 and `user`.`disable` = TRUE" ).getSingleResult());
		return new UserAliPayExcelModel(had,no,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public List<UserPublishExcelModel> getPublishExcel(UserExcelRequest ue) throws ParseException {
		
		String sql1 = "SELECT DATE_FORMAT(hrh.createTime,'%Y-%m-%d') days, hrh.userId , COUNT(hrh.userId) userCount FROM houseresourcehistory hrh left join `user` u on u.uid = hrh.userId WHERE hrh.checkNum = 0 and (hrh.actionType = 1) "
				+ " ";
				
		
		List<Object> pars =new ArrayList<Object>();
		
		if (ue.getCityId() > 0) {
			sql1 += " and u.cityId = ? ";
			pars.add(ue.getCityId());
		}
		if (StringUtils.isNotBlank(ue.getEndDate())) {
			sql1 += " and hrh.createTime <= ? ";
			pars.add(ue.getEndDate());
		}
		if (StringUtils.isNotBlank(ue.getStartDate())) {
			sql1 += " and hrh.createTime >= ? ";
			pars.add(ue.getStartDate());
		}
		sql1 += "GROUP BY days,hrh.userId";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long start = sdf.parse(ue.getStartDate()).getTime();
		long end = sdf.parse(ue.getEndDate()).getTime();
		long result = (end - start) / (24 * 60 * 60 * 1000);
		System.out.println("差:" + result + "天");
		
		Query query = getEntityManager().createNativeQuery(sql1);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i + 1, pars.get(i));
			}
		}
		List<Object[]> objs = query.getResultList();
		List<UserPublishExcelModel> list = new ArrayList<UserPublishExcelModel>();
		for (long i = start; i <= end ; i += 24 * 60 * 60 * 1000) {
			UserPublishExcelModel upem = new UserPublishExcelModel(new SimpleDateFormat("yyyy-MM-dd").format(new Date(i)));
			list.add(upem);
			
		}
		if (objs != null && objs.size() > 0) {
			for (Object[] row : objs) {
				for (UserPublishExcelModel upem : list) {
					if (upem.getDate().equals(row[0])) {
						int publishNum = Integer.parseInt(row[2]+"");
						if (publishNum == 1) {
							upem.setH1(upem.getH1() + 1);
						}else if (publishNum >= 2 && publishNum <= 10) {
							upem.setH2to10(upem.getH2to10() + 1);
						}else if (publishNum >= 11 && publishNum <= 50) {
							upem.setH11to50(upem.getH11to50() + 1);
						}else if (publishNum >= 51 && publishNum <= 100) {
							upem.setH51to100(upem.getH51to100() + 1);
						}else if (publishNum >= 101) {
							upem.setH101(upem.getH101() + 1);
						}
						break;
					}
				}
			}
		}
		
		return list;
	}
	
	public List<UserRegisterExcelModel> getRegisterExcel(UserExcelRequest ue) throws ParseException {
		List<Object> pars1 =new ArrayList<Object>();
		List<Object> pars2 =new ArrayList<Object>();
		List<Object> pars3 =new ArrayList<Object>();

		String sqlTotal = "SELECT dayTotal ,total.total,success,fail,BDRecommendNum,ZJRecommendNum,NOTRecommendNum from ";
		
		sqlTotal += " (SELECT DATE_FORMAT(createTime,'%Y-%m-%d') dayTotal,  count(`user`.uid ) total  FROM `user`  where 1 = 1 ";
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY  dayTotal ) total LEFT JOIN ";
		
		sqlTotal += " (SELECT DATE_FORMAT(createTime,'%Y-%m-%d') daySuccess,   count(`user`.uid ) success  , `user`.`state` stat FROM `user`  where `user`.state = 1  " ;
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY  daySuccess ) succ on total.dayTotal = succ.daySuccess LEFT JOIN ";
		
		sqlTotal += " (SELECT DATE_FORMAT(createTime,'%Y-%m-%d') dayFail,  count( `user`.uid ) fail  , `user`.`state` stat FROM `user`  where `user`.state = 3 ";
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY  dayFail )  fail on total.dayTotal = fail.dayFail LEFT JOIN ";
		
		sqlTotal += " (SELECT DATE_FORMAT(`user`.createTime,'%Y-%m-%d') dayBDRecommend,  count(`user`.uid ) BDRecommendNum  FROM `user` inner JOIN `user` BD on `user`.spreadName = BD.spreadId  where `user`.state = 1 and `user`.spreadName is not null and `user`.spreadName <> '' and  BD.ifInner = 1 ";
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY dayBDRecommend ) bdRecomm on total.dayTotal = bdRecomm.dayBDRecommend LEFT JOIN ";
		
		sqlTotal +=  " (SELECT DATE_FORMAT(`user`.createTime,'%Y-%m-%d') dayZJRecommend,  count(`user`.uid ) ZJRecommendNum FROM `user` LEFT JOIN `user` ZJ on `user`.spreadName = ZJ.spreadId  where `user`.state = 1  and `user`.spreadName is not null and `user`.spreadName <> ''  and ZJ.ifInner = 0 ";
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY dayZJRecommend ) zjRecomm on total.dayTotal = zjRecomm.dayZJRecommend LEFT JOIN ";
		
		sqlTotal += " (SELECT DATE_FORMAT(`user`.createTime,'%Y-%m-%d') dayNOTRecommend,  count(`user`.uid ) NOTRecommendNum  FROM `user`   where `user`.state = 1  and `user`.spreadName is  null or `user`.spreadName = ''  ";
		sqlTotal = getparams(sqlTotal, "`user`", pars1, ue);
		sqlTotal += " GROUP BY dayNOTRecommend ) notRecomm on total.dayTotal = notRecomm.dayNOTRecommend ";
				
		
		String sqlPublish = "SELECT DATE_FORMAT(h.createTime,'%Y-%m-%d') days, COUNT(distinct h.userId) FROM	houseresourcehistory h left join `user` on `user`.uid = h.userId WHERE h.checkNum = 0 and (h.actionType = 1 or h.actionType = 2 )  ";
		sqlPublish = getparams(sqlPublish, "h", pars2, ue);
		sqlPublish += " GROUP BY days;";
		
		/**
		 * 查不到举报了多少人 只能查多少人发起举报了 举报了多少套房子
		 */
		String sqlReport = "SELECT DATE_FORMAT(h.createTime,'%Y-%m-%d') days, COUNT(distinct h.userId) FROM	houseresourcehistory h left join `user` on `user`.uid = h.userId WHERE h.checkNum = 0 and (h.actionType = 3)  ";
		sqlReport = getparams(sqlReport, "h", pars3, ue);
		sqlReport += " GROUP BY days;";
		
		
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		long start = sdf.parse(ue.getStartDate()).getTime();
		long end = sdf.parse(ue.getEndDate()).getTime();
		long result = (end - start) / (24 * 60 * 60 * 1000);
		System.out.println("差:" + result + "天");
		List<UserRegisterExcelModel> list = new ArrayList<UserRegisterExcelModel>();
		for (long i = start; i <= end; i += 24 * 60 * 60 * 1000) {
			
			UserRegisterExcelModel upem = new UserRegisterExcelModel(new SimpleDateFormat("yyyy-MM-dd").format(new Date(i)));
			list.add(upem);
		}
		Query query = getEntityManager().createNativeQuery(sqlTotal);
		if (pars1.size() > 0) {
			for (int i = 0; i < pars1.size(); i++) {
				query.setParameter(i + 1, pars1.get(i));
			}
		}
		List<Object[]> objs1 = query.getResultList();
		if (objs1 != null && objs1.size() > 0) {
			for (Object[] row : objs1) {
				for (UserRegisterExcelModel u : list) {
					if (u.getDate().equals(row[0])) {
						u.setTotal(Integer.parseInt(row[1]+""));
						u.setSuccess(row[2] == null ? 0 :Integer.parseInt(row[2]+""));
						u.setFail(row[3] == null ? 0 :Integer.parseInt(row[3]+""));
						u.setBDRecommendNum(row[4] == null ? 0 :Integer.parseInt(row[4]+""));
						u.setZJRecommendNum(row[5] == null ? 0 :Integer.parseInt(row[5]+""));
						u.setNOTRecommendNum(row[6] == null ? 0 :Integer.parseInt(row[6]+""));
						
						if (u.getTotal() != 0) {
							//double d = 0;
							//DecimalFormat df = new DecimalFormat("0.00%");
							//d = (((double)u.getSuccess())/u.getTotal());
							//df.format(d);
							u.setRate(new BigDecimal((0.0 + u.getSuccess())/u.getTotal()));
						}
						break;
					}
				}
			}
		}
		
		query = getEntityManager().createNativeQuery(sqlPublish);
		if (pars2.size() > 0) {
			for (int i = 0; i < pars2.size(); i++) {
				query.setParameter(i + 1, pars2.get(i));
			}
		}
		List<Object[]> objs2 = query.getResultList();
		if (objs2 != null && objs2.size() > 0) {
			for (Object[] row : objs2) {
				for (UserRegisterExcelModel u : list) {
					if (u.getDate().equals(row[0])) {
						u.setUserPublishNum(Integer.parseInt(row[1]+""));
						break;
					}
				}
			}
		}
		
		
		query = getEntityManager().createNativeQuery(sqlReport);
		if (pars3.size() > 0) {
			for (int i = 0; i < pars3.size(); i++) {
				query.setParameter(i + 1, pars3.get(i));
			}
		}
		List<Object[]> objs3 = query.getResultList();
		if (objs3 != null && objs3.size() > 0) {
			for (Object[] row : objs3) {
				for (UserRegisterExcelModel u : list) {
					if (u.getDate().equals(row[0])) {
						u.setUserReportedNum(Integer.parseInt(row[1]+""));
						break;
					}
				}
			}
		}
		
		for (UserRegisterExcelModel u : list) {
			if (u.getTotal() == 0) {
				u.setRate(new BigDecimal(0));
			}else {
				if (u.getRate() == null) {
					u.setRate(new BigDecimal(1));
				}
			}
		}
		
		return list;
	}
	
	public BDWorkDailyModel getBDWorkDaily(UserExcelRequest ue) throws ParseException {
		
		return null;
	}
	
	private String  getparams(String sqlTotal, String args,List<Object> pars,UserExcelRequest ue) {
		if (StringUtils.isNotBlank(ue.getEndDate())) {
			sqlTotal += " and " + args + ".createTime <= ? ";
			pars.add(ue.getEndDate());
		}
		if (StringUtils.isNotBlank(ue.getStartDate())) {
			sqlTotal += " and " + args + ".createTime >= ? ";
			pars.add(ue.getStartDate());
		}
		if ("h".equals(args)) {
			args = "`user`";
		}
		if (StringUtils.isNotBlank(ue.getRecommenderMobile())) {
			sqlTotal += " and " + args + ".spreadName = ? ";
			pars.add(ue.getStartDate());
		}
		if (ue.getCityId() > 0) {
			sqlTotal += " and " + args + ".cityId = ? ";
			pars.add(ue.getCityId());
		}
		if (ue.getAreaId() > 0) {
			sqlTotal += " and " + args + ".areaId = ? ";
			pars.add(ue.getAreaId());
		}
		
		return sqlTotal;
	}

}
