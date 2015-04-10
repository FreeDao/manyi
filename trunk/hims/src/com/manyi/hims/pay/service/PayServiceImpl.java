package com.manyi.hims.pay.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.DataUtil;
import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.entity.MergerPay;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.User;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.controller.PayRestController.ImportPayReq;
import com.manyi.hims.pay.model.PayReq;
import com.manyi.hims.pay.model.PayRes;
import com.manyi.hims.pay.model.StatisPayRes;
import com.manyi.hims.pay.util.PayUtil;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.PushUtils;
import com.manyi.hims.util.PushUtils.PushPayInfo;

@Service(value = "payService")
@Scope(value = "singleton")

public class PayServiceImpl extends BaseService implements PayService {
	Logger log = LoggerFactory.getLogger(PayServiceImpl.class);
    
    @Autowired
    private PushUtils pushUtils;
    
	@Override
	public int addPay(AddPayReq req) {
		Pay pay = PayUtil.createPay(req);
		this.getEntityManager().persist(pay);
		this.getEntityManager().flush();
		return 0;
	}
	/**
	 * 添加 经纪人的 金额记录数
	 * @param userId
	 */
	public void addUserCount(int userId){
		User user = this.getEntityManager().find(User.class, userId);
		user.setCreateAwardCount(user.getCreateAwardCount()+1);
		this.getEntityManager().merge(user);
		this.getEntityManager().flush();
		
//		this.getEntityManager().createNativeQuery("update User u set u.createAwardCount = u.createAwardCount+1 where u.uid = "+userId).executeUpdate();
	}
	
	/**
	 * 简单支付报表
	 */
	@Override
	public StatisPayRes statisticsPay(PayReq req) {
		String sql=" SELECT COUNT(p.payId) payNum , SUM(CASE WHEN p.payState =1 THEN 1 ELSE 0 END) success, SUM(CASE WHEN p.payState =0 THEN 1 ELSE 0 END) ing, "+
				" SUM(CASE WHEN p.payState =2 THEN 1 ELSE 0 END) failed , SUM(p.paySum) paySum , SUM(CASE WHEN p.payState =1 THEN p.paySum ELSE 0 END) sucessSum, "+
				" SUM(CASE WHEN p.payState =0 THEN p.paySum ELSE 0 END) ingSum, SUM(CASE WHEN p.payState =2 THEN p.paySum ELSE 0 END) failedSum from pay p join user u on u.uId = p.userId and p.payId <>0 ";
		
		List<Object> pars =new ArrayList<Object>();

		// 按照 状态搜索
		if (req.getPayState() != -1) {
			sql += " and p.payState = ? ";
			pars.add(req.getPayState());
		}
		// 经纪人的 姓名
		if (!StringUtils.isBlank(req.getUserName())) {
			sql += " and u.realName like ? ";
			pars.add("%" + req.getUserName() + "%");
		}

		// 经纪人的 支付宝帐号
		if (!StringUtils.isBlank(req.getUserAccount())) {
			sql += " and u.account = ? ";
			pars.add(req.getUserAccount());
		}

		if (req.getStart() != null) {
			sql += " and p.addTime >= ?   ";
			pars.add(req.getStart());
		}
		if (req.getEnd() != null) {
			sql += " and p.addTime < ?  ";
			pars.add(req.getEnd());
		}
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		StatisPayRes res = new StatisPayRes();
		List<Object[]> objs = query.getResultList();
		if(objs != null && objs.size()>0){
			Object[] row = objs.get(0);
			int k=0;
			res.setPayNum(DataUtil.toInt(row[k++]));
			res.setSuccess(DataUtil.toInt(row[k++]));
			res.setIng(DataUtil.toInt(row[k++]));
			res.setFailed(DataUtil.toInt(row[k++]));
			
			res.setPaySum((BigDecimal) (row[k] == null ? new BigDecimal(0) : row[k]));
			k++;
			res.setSucessSum((BigDecimal) (row[k] == null ? new BigDecimal(0) : row[k]));
			k++;
			res.setIngSum((BigDecimal) (row[k] == null ? new BigDecimal(0) : row[k]));
			k++;
			res.setFailedSum((BigDecimal) (row[k] == null ? new BigDecimal(0) : row[k]));
		}
		return res;
	}
	
	/**
	 * 导入 合并之后的 数据
	 */
	@Override
	public int importMergeExportExcel(List<ImportPayReq> reqs) {
		if(reqs!= null && reqs.size()>0){
			int len = reqs.size();
			List<PushPayInfo> payinfos = new ArrayList<PushPayInfo>(); 
			for (int i = 0; i < len; i++) {
				ImportPayReq req = reqs.get(i);
				MergerPay mergerPay = this.getEntityManager().find(MergerPay.class, req.getPayId());//得到合并的ID
				if(mergerPay != null){
					String payState =req.getState();
					mergerPay.setPayTime(new Date());
					mergerPay.setSerialNumber(req.getSerialNumber());
					mergerPay.setEmployeeId(req.getEmployeeId());
					String payIds = mergerPay.getPayIds(); 
					
					//判断 该条数据  是否 已经 成功付款
					if(mergerPay.getPayState() != 1){
						//成功付款 的 项目 不需要再次 修改
						if("成功".equals(payState)){
							mergerPay.setPayState(1);
							mergerPay.setRemark("");
							if(mergerPay != null){
								addUserCount(mergerPay.getUserId());//添加 经纪人的 金额记录
								User user = this.getEntityManager().find(User.class, mergerPay.getUserId());
								if(user != null){
								    PushPayInfo pushInfo = new PushPayInfo(user.getMobile(),mergerPay.getMoney().intValue()+"");
								    payinfos.add(pushInfo);
								}
							}
						}else if("失败".equals(payState)){
							mergerPay.setPayState(2);
							mergerPay.setRemark(req.getRemark());
						}
						this.getEntityManager().merge(mergerPay);
						log.info("mergerPay 信息修改之后的内容  {} ",ReflectionToStringBuilder.toString(mergerPay));
						
						//修改pay表
						String sql ="update Pay p set p.payState = ? ,p.remark = ?,p.payTime = ?,p.serialNumber = ? , p.employeeId = ? where p.payId in ( "+payIds+" )";
						Query query = this.getEntityManager().createNativeQuery(sql);
						query.setParameter(1, mergerPay.getPayState());
						query.setParameter(2, mergerPay.getRemark());
						query.setParameter(3, mergerPay.getPayTime());
						query.setParameter(4, mergerPay.getSerialNumber());
						query.setParameter(5, mergerPay.getEmployeeId());
						int temp = query.executeUpdate();
						
						if(temp >0){
							log.info("支付信息修改成功: payIds :{} , 影响行数: {} ", payIds,temp);
						}else{
							log.info("支付信息修改影响行数为0: payIds :"+ payIds);
						}
						
					}
				}else{
					log.debug("问题: payId 不存在  "+ req.getPayId());
				}
			}
			try {
				//payinfos 中存放的就是 所有的 支付 成功的手机号码 ,推送支付成功消息
				pushUtils.sendPayedPushMsg(payinfos);
			} catch (Exception e) {
				log.error("推送支付成功消息失败",e);
			}
		}
		return 0;
	}
	
	@Override
	public int importExcel(List<ImportPayReq> reqs) {
		if(reqs!= null && reqs.size()>0){
			int len = reqs.size();
			List<PushPayInfo> payinfos = new ArrayList<PushPayInfo>(); 
			for (int i = 0; i < len; i++) {
				ImportPayReq req = reqs.get(i);
				Pay pay= this.getEntityManager().find(Pay.class, req.getPayId());
				if(pay != null){
					String payState =req.getState();
					pay.setPayTime(new Date());
					pay.setSerialNumber(req.getSerialNumber());
					pay.setEmployeeId(req.getEmployeeId());
					
					//判断 该条数据  是否 已经 成功付款
					if(pay.getPayState() != 1){
						//成功付款 的 项目 不需要再次 修改
						if("成功".equals(payState)){
							pay.setPayState(1);
							Pay temp = this.getEntityManager().find(Pay.class, pay.getPayId());
							if(temp != null){
								addUserCount(temp.getUserId());//添加 经纪人的 金额记录
								User user = this.getEntityManager().find(User.class, temp.getUserId());
								if(user != null){
								    PushPayInfo pushInfo = new PushPayInfo(user.getMobile(),temp.getPaySum().intValue()+"");
								    payinfos.add(pushInfo);
								}
							}
						}else if("失败".equals(payState)){
							pay.setPayState(2);
							pay.setRemark(req.getRemark());
						}
						this.getEntityManager().merge(pay);
					}
				}else{
					log.debug("问题: payId 不存在  "+ req.getPayId());
				}
			}
			try {
				//payinfos 中存放的就是 所有的 支付 成功的手机号码 ,推送支付成功消息
				pushUtils.sendPayedPushMsg(payinfos);
			} catch (Exception e) {
				log.error("推送支付成功消息失败",e);
			}
		}
		return 0;
	}
	
	/**
	 * 下发搜索  合并之后的列表
	 */
	@Override
	public PageResponse<PayRes> payMergeList(PayReq req) {
		String sql="SELECT p.userId,group_concat(payid) payids ,u.realName,u.account,count(p.payId) stroke, sum(p.paySum) money from pay p JOIN `user` u on u.uid = p.userId "+
				" where p.payState in (0,2) and u.account is not null AND u.account <>'' AND u.disable = 1 ";
		
		String payid_sql ="SELECT p.payId from pay p JOIN `user` u on u.uid = p.userId  where p.payState in (0,2) and u.account is not null AND u.account <>'' AND u.disable = 1 ";
		
		List<Object> pars =new ArrayList<Object>();
		if(req.getStart() != null){
			sql += " and p.addTime >= ?  ";
			payid_sql +=" and p.addTime >= ? ";
			pars.add(req.getStart());
		}
		if(req.getEnd() != null){
			sql += " and p.addTime < ? ";
			payid_sql +=" and p.addTime < ? ";
			pars.add(req.getEnd());
		}
		sql +=" GROUP BY p.userId ORDER BY p.userId asc , p.addTime desc ";
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				query.setParameter(i + 1, pars.get(i));
			}
		}
		PageResponse<PayRes> page = new PageResponse<PayRes>();
		ArrayList<PayRes> payResList = new ArrayList<PayRes>();
		
		List<Object[]> objs = query.getResultList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(objs != null && objs.size()>0){
			for (Object[] row : objs) {
				int k =0;
				int userId = DataUtil.toInt(row[k++]);
				
				String temp_sql =payid_sql;
				
				temp_sql +=" and p.userId =  " + userId ;
				Query payid_query = this.getEntityManager().createNativeQuery(temp_sql);
				if (pars.size() > 0) {
					for (int i = 0; i < pars.size(); i++) {
						payid_query.setParameter(i + 1, pars.get(i));
					}
				}
				List<Integer> payidsObject = payid_query.getResultList();
				StringBuffer ids = new StringBuffer();
				if(payidsObject != null && payidsObject.size()>0){
					for (Integer pids : payidsObject) {
						int t = 0;
						if(pids instanceof Integer){
							t = DataUtil.toInt(pids);
						}
						ids.append(t+",");
					}
				}
				String tempIds  = ids.toString();
				if(tempIds != null && tempIds.length()>0){
					tempIds = tempIds.substring(0, tempIds.length()-1);
				}
				String payIds = tempIds;
				
//				String payIds = (row[k] == null ? "" : row[k]+"");
				k++;
				String userName =(row[k] == null ? "" : row[k]+"");
				k++;
				String account =(row[k] == null ? "" : row[k]+"");
				k++;
				int stroke = DataUtil.toInt(row[k++]);
				BigDecimal money = (BigDecimal) row[k++];
				MergerPay mergepay = new MergerPay();
				mergepay.setAddTime(new Date());
				mergepay.setMoney(money);
				mergepay.setPayIds(payIds);
				mergepay.setStroke(stroke);
				mergepay.setAccount(account);
				mergepay.setUserId(userId);
				mergepay.setUserName(userName);
				try {
					if(req.getStart() != null){
						mergepay.setStart(sdf.parse(req.getStart()));
					}
					if(req.getEnd() != null){
						mergepay.setEnd(sdf.parse(req.getEnd()));
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.getEntityManager().persist(mergepay);
				
				PayRes res = new PayRes();
				res.setAccount(account);
				res.setPayId(mergepay.getMergePayId());
				res.setPaySum(money);
				res.setUserName(userName);
				payResList.add(res);
			}
		}
		page.setRows(payResList);
		return page;
	}
	
	
	/**
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<PayRes> payList(PayReq req) {
		String count_sql="SELECT COUNT(1) FROM Pay p join User u on u.uId = p.userId where p.payId <>0 ";//关联有效的用户
		String sql="SELECT p.payId,u.realName,u.mobile,u.account,u.state,p.paySum,p.payState,p.payReason ,p.remark,p.addTime ,p.payTime,u.disable,u.cityId FROM Pay p join User u on u.uId = p.userId where p.payId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		if(req.isIfExport()){
			//导出数据 ,1. 必须加入 条件 -->审核通过的 user
			count_sql += " and u.state = ? and u.disable = 1 ";// 审核通过 and 没有禁用
			sql += " and u.state = ?  and u.disable = 1 ";// 审核通过 and 没有禁用
			pars.add(EntityUtils.StatusEnum.SUCCESS.getValue());
			//2 . 需要添加   没有付款的 状态 (未付款,付款失败)
			count_sql += " and p.payState in (0,2 ) ";
			sql += " and p.payState in (0,2 ) ";
			//3. 不需要导出 空支付宝帐号的 用户
			count_sql += " and u.account is not null  and u.account <> '' ";
			sql += " and u.account is not null and u.account <> '' ";
		}else{
			// 按照 状态搜索
			if (req.getPayState() != -1) {
				count_sql += " and p.payState = ? ";
				sql += " and p.payState = ? ";
				pars.add(req.getPayState());
			}
			//经纪人的 姓名
			if(!StringUtils.isBlank(req.getUserName())){
				count_sql += " and u.realName like ? ";
				sql += " and u.realName like ? ";
				pars.add("%"+req.getUserName()+"%");
			}
			
			//经纪人的 支付宝帐号
			if(!StringUtils.isBlank(req.getUserAccount())){
				count_sql += " and u.account = ? ";
				sql += " and u.account = ? ";
				pars.add(req.getUserAccount());
			}
		}
		
		if(req.getStart() != null){
			count_sql += " and p.addTime >= ?  ";
			sql += " and p.addTime >= ?   ";
			pars.add(req.getStart());
		}
		if(req.getEnd() != null){
			count_sql += " and p.addTime < ? ";
			sql += " and p.addTime < ?  ";
			pars.add(req.getEnd());
		}
		
		sql +=" order by p.userId asc , p.addTime desc  ";
		
		Query count_query= this.getEntityManager().createNativeQuery(count_sql);
		Query query= this.getEntityManager().createNativeQuery(sql);
		if (pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
			}
		}
		
		List<BigInteger> counts = count_query.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		PageResponse<PayRes> page = new PageResponse<PayRes>();
		ArrayList<PayRes> payResList = new ArrayList<PayRes>();
		if (total > 0) {
			if(req.getPage() == 0 && req.getRows() == 0){
			}else{
				query.setFirstResult((req.getPage()-1)*req.getRows());//起始下标
				query.setMaxResults(req.getRows());//查询出来的数量/条数
				page.setPageSize(req.getRows());
				page.setCurrentPage(req.getPage());
				page.setTotal(total);
				int n = ((total-1)/req.getRows()+1);
				page.setTotalPage(n);//总页数
			}
			List<Object[]> objs = query.getResultList();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(objs != null && objs.size()>0){
				for (Object[] row : objs) {
					PayRes res = new PayRes();
					res.setPayId(Integer.valueOf(row[0]+""));
					res.setUserName(row[1]+"");
					res.setMobile(row[2]+"");
					res.setAccount(row[3] == null ? "" : row[3]+"");
					res.setUserState(Integer.valueOf(row[4]+""));
					res.setPaySum((BigDecimal) row[5]);
					res.setPayState(Integer.valueOf(row[6]+""));
					res.setPayReason(row[7]+"");
					res.setRemark(row[8] == null ? "":row[8]+"");
					Date d= (Date) row[9];
					if(d != null){
						res.setAddTime(sdf.format(d));
					}
					Date d2= (Date) row[10];
					if(d2 != null){
						res.setPayTime(sdf.format(d2));
					}
					Boolean disBoolean = (Boolean) row[11];
					if(disBoolean == null){
						res.setUserDisable(-1);
					}else{
						if(disBoolean){
							res.setUserDisable(1);
						}else{
							res.setUserDisable(0);
						}
					}
					if(res.getUserDisable() != -1){
						res.setUserDisableStr(EntityUtils.DisabledEnum.getByNumValue(res.getUserDisable()).getDesc());
					}
					payResList.add(res);
				}
			}
		}
		page.setRows(payResList);
		return page;
	}
}
