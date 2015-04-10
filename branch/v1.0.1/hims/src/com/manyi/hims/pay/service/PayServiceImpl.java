package com.manyi.hims.pay.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.manyi.hims.BaseService;
import com.manyi.hims.PageResponse;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.User;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.controller.PayRestController.ImportPayReq;
import com.manyi.hims.pay.controller.PayRestController.PayReq;
import com.manyi.hims.pay.controller.PayRestController.PayRes;
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
	 * 通过搜索 得到对应的 列表内容
	 * @param req
	 * @return
	 */
	@Override
	public PageResponse<PayRes> payList(PayReq req) {
		String count_sql="SELECT COUNT(1) FROM Pay p join User u on u.uId = p.userId where p.payId <>0 ";//关联有效的用户
		String sql="SELECT p.payId,u.realName,u.mobile,u.account,u.state,p.paySum,p.payState,p.payReason FROM Pay p join User u on u.uId = p.userId where p.payId <>0 ";
		List<Object> pars =new ArrayList<Object>();
		if(req.isIfExport()){
			//导出数据 , 必须加入 条件 -->审核通过的 user
			count_sql += " and u.state = ? ";
			sql += " and u.state = ? ";
			pars.add(EntityUtils.StatusEnum.SUCCESS.getValue());
			//2 . 需要添加   没有付款的 状态 (未付款,付款失败)
			count_sql += " and p.payState in (0,2 ) ";
			sql += " and p.payState in (0,2 ) ";
		}else{
			// 按照 状态搜索
			if (req.getPayState() != -1) {
				count_sql += " and p.payState = ? ";
				sql += " and p.payState = ? ";
				pars.add(req.getPayState());
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
		
		sql +=" order by p.addTime desc ";
		
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
					payResList.add(res);
				}
			}
		}
		page.setRows(payResList);
		return page;
	}
}
