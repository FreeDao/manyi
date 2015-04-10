package com.manyi.hims.user.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.Page;
import com.leo.common.util.MD5Digest;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.employee.EmployeeConst;
import com.manyi.hims.entity.HouseResourceViewCount;
import com.manyi.hims.entity.Pay;
import com.manyi.hims.entity.User;
import com.manyi.hims.entity.UserHistory;
import com.manyi.hims.entity.User_;
import com.manyi.hims.entity.VerifyCode;
import com.manyi.hims.pay.controller.PayRestController.AddPayReq;
import com.manyi.hims.pay.util.PayUtil;
import com.manyi.hims.uc.UcConst;
import com.manyi.hims.user.conroller.UserManagerController.getUserRequest;
import com.manyi.hims.user.model.UserRequest;
import com.manyi.hims.user.model.UserValidationModel;
import com.manyi.hims.util.EntityUtils;
import com.manyi.hims.util.EntityUtils.StatusEnum;
import com.manyi.hims.util.EntityUtils.UserHistoryEnum;
import com.manyi.hims.util.InfoUtils;
@Service(value = "userManagerService")
@Scope(value = "singleton")
public class UserManagerServiceImpl extends BaseService implements UserManagerService{
	
	@Value("${constants.uploadBasePath}")
	private String UPLOAD_BASE_PATH;
	@Override

	public Page<UserResponse> getUser(getUserRequest user,Integer page, Integer rows,String realName,String spreandName,int type,String mobile,int state) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		String sql = "select area.name,user.mobile,user.disable,user.realName,user.spreadId,user.state,user.createTime,user1.realName u1,user.ifInner,"
				+ "user.uid,area1.name cityName,user.account,user.codeUrl,user.cardUrl,user.code  "
				+ "from User user left join User  user1 on user.spreadName=user1.spreadId left join Area area on user.areaId=area.areaId left join Area area1 on user.cityId=area1.areaId ";
		
		String sql_count = "select count(1) from User user left join User  user1 on user.spreadName=user1.spreadId ";
		if(type==0){
			sql += " where user.ifInner=0";
			sql_count += " where user.ifInner=0";
		}else{
			sql += " where user.ifInner=1";
			sql_count += " where user.ifInner=1";
		}
		List<Object> pars = new ArrayList<Object>();
//		pars.add(type);
		if(StringUtils.isNotBlank(realName)){
			sql += " and user.realName=?";
			sql_count += " and user.realName=?";
			pars.add(realName);
		}
		if(StringUtils.isNotBlank(user.getRecommendedMobile())){
			sql += " and user.spreadName=?";
			sql_count += " and user.spreadName=?";
			//sql_count += "  left join User  user1 on user.spreadId=user1.spreadName where user1.spreadName=?";
			pars.add(user.getRecommendedMobile());
		}
		if (user.getStartRegistDate() != null) {
			sql += " and user.createTime >?";
			sql_count += " and user.createTime >?";
			pars.add(user.getStartRegistDate());
		}
		
		if (user.getEndRegistDate() != null) {
			sql += " and user.createTime <?";
			sql_count += " and user.createTime <?";
			pars.add(user.getEndRegistDate());
		}
		
		if(StringUtils.isNotBlank(mobile)){
			sql += " and user.mobile=?";
			sql_count += " and user.mobile=?";
			pars.add(mobile);
		}
		if(state!=0){
			sql += " and user.state=?";
			sql_count += " and user.state=?";
			pars.add(state);
		}
		
		if(user.getCityId()!=0){
			sql += " and user.cityId=? ";
			sql_count += " and user.cityId=? ";
			pars.add(user.getCityId());
		}

		sql += " order by user.createTime desc";
//		CriteriaQuery<Long> cq_count = cb.createQuery(Long.class);
//		Root<User> root_count = cq_count.from(User.class);
//		if(type==0){
//			cq_count.where(cb.and(cb.equal(root_count.get(User_.ifInner), 0))).select(cb.count(root_count));
//		}else{
//			cq_count.where(cb.and(cb.equal(root_count.get(User_.ifInner), 1))).select(cb.count(root_count));
//		}
//		long counts = getEntityManager().createQuery(cq_count).getSingleResult();
		Query query_count = getEntityManager().createNativeQuery(sql_count);
		Query query = getEntityManager().createNativeQuery(sql);
		
		if (pars!=null && pars.size() > 0) {
			for (int i = 0; i < pars.size(); i++) {
				//count_query.setParameter(i + 1, pars.get(i));
				query.setParameter(i + 1, pars.get(i));
				query_count.setParameter(i + 1, pars.get(i));
			}
		}
		List<BigInteger> counts = query_count.getResultList();
		int total = 0;
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		Page<UserResponse> pages = new Page<UserResponse>(page==null?1:page,rows==null?0:rows);
		if(total<=0){
			pages.setRows(new ArrayList<UserResponse>(0));
		}else
			pages.setTotal((int)total);
		List<Object[]> users = query.setFirstResult((page-1) * rows).setMaxResults(rows).getResultList();
		
		List<UserResponse> infos = new ArrayList<UserResponse>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i=0;i<users.size();i++){
			UserResponse response =  new UserResponse();
			
			Object[] row = users.get(i);
			response.setAreaName((row[0] ==  null ? "-":row[0].toString()));
			response.setMobile(row[1].toString());
			response.setDisable((boolean)row[2]);
			response.setRealName(row[3].toString());
			response.setSpreadId(row[4].toString());
			response.setState((int)row[5]);
			response.setCreateTime( row[6] == null ? new Date() : (Date) row[6]);
			response.setSpreadName(row[7] == null ? "-": row[7]+"");
			if((int)row[8] ==1){
				String tmp = response.getRealName();
				tmp +="(内部)";
				response.setRealName(tmp);
			}
			response.setUid((int)row[9]);
			response.setCityName(row[10] == null ? null : row[10].toString());
			if(response.isDisable()==true){
				response.setDisableStr("启用");
			}
			if(response.isDisable()==false){
				response.setDisableStr("禁用");
			}
//			response.setDisableStr(DisabledEnum.getByValue(response.isDisable()).getDesc());
			response.setStateStr(StatusEnum.getByValue(response.getState()).getDesc());
			infos.add(response);
		}
		//page.setRows(infos.subList(page.getFirstResultIndex(), page.getFirstResultIndex()+page.getMaxResultLenght()));
		pages.setRows(infos);
		return pages;
	}
	
	
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	@Override
	public UserResponse getUserById(int uid) {
		String sql = "select area.name,user.mobile,user.disable,user.realName,user.spreadId,user.state,user.createTime,user1.realName u1,"
				+ "user.ifInner ,user.uid,user.account,user.codeUrl,user.cardUrl,user.code ,user1.ifInner otherInner "
				+ "from User user left join User  user1 on user.spreadName=user1.spreadId join Area area on user.areaId=area.areaId where  user.uid=?";
		Query query = getEntityManager().createNativeQuery(sql);
		query.setParameter(1, uid);
		List<Object[]> user = query.getResultList();
		
		//已支付金额
		String sql_paySum = "select sum(pay.paySum)  from Pay pay where pay.payState=1 and pay.userId=?";
		Query queryPaySum = getEntityManager().createNativeQuery(sql_paySum);
		queryPaySum.setParameter(1, uid);
		List list = queryPaySum.getResultList();
		BigDecimal bigDecimal = new BigDecimal(0);
		if(list.get(0)!=null){
			bigDecimal = new BigDecimal(list.get(0).toString());
		}
		
		//待支付金额
		String sql_waitPaySum = "select sum(pay.paySum) blance from Pay pay where pay.payState=0 and  pay.userId=?";
		Query aueryWaitPaySum = getEntityManager().createNativeQuery(sql_waitPaySum);
		aueryWaitPaySum.setParameter(1, uid);
		List waitPay_list = aueryWaitPaySum.getResultList();
		BigDecimal wait_bigDecimal = new BigDecimal(0);
		if(waitPay_list.get(0)!=null){
			wait_bigDecimal = new BigDecimal(waitPay_list.get(0).toString());
		}
		UserResponse response = new UserResponse();
		if(user!=null && user.size()>0){
			for (int i = 0; i < user.size(); i++) {
				Object[] row = user.get(i);
				response.setAreaName(row[0].toString());
				response.setMobile(row[1].toString());
				response.setDisable((boolean)row[2]);
				response.setRealName(row[3].toString());
				response.setSpreadId(row[4].toString());
				response.setState((int)row[5]);
				response.setCreateTime((Date) row[6]);
				response.setSpreadName(row[7] == null ? "-": row[7]+"");
				if((int)row[8] ==1){
					String tmp = response.getRealName();
					tmp +="(内部)";
					response.setRealName(tmp);
				}
				response.setUid((int)row[9]);
				response.setAlipayAccount(row[10]==null?"":row[10].toString());
				
				String codeUrl = (row[11] == null ? "" : row[11].toString());
				//codeUrl.replaceAll("\\", "/");
				codeUrl = codeUrl.replaceAll("\\\\", "/");
				codeUrl = codeUrl.replaceFirst(UPLOAD_BASE_PATH,"");
				System.out.println(codeUrl);
				codeUrl  = Global.PIC_BASE_URL + codeUrl;
				
				String cardUrl = (row[12] == null ? "" : row[12].toString());
				cardUrl = cardUrl.replaceAll("\\\\", "/");
				cardUrl = cardUrl.replaceFirst(UPLOAD_BASE_PATH,"");
				cardUrl  = Global.PIC_BASE_URL + cardUrl;
				
				response.setCodeUrl(codeUrl);
				response.setCardUrl(cardUrl);
				response.setCode(row[13].toString());
				response.setOtherInner(Integer.parseInt(row[14] == null ? "-1" : row[14]+""));
//				response.setDisableStr(DisabledEnum.getByValue(response.isDisable()).getDesc());
				if(response.isDisable()==true){
					response.setDisableStr("启用");
				}
				if(response.isDisable()==false){
					response.setDisableStr("禁用");
				}
				response.setStateStr(StatusEnum.getByValue(response.getState()).getDesc());
				response.setWaitPayBlance(wait_bigDecimal);
				response.setHavePayBlance(bigDecimal);
			}
			
		}
		return response;
	}
	
	@Override
	public void updateUserState(int uid,int state,String doorName,String doorNumber,String remark,int operator) {
		if(uid==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		
		User user = getEntityManager().find(User.class, uid);
		UserHistory history = new UserHistory();
		// 审核成功
		if (state == StatusEnum.SUCCESS.getValue()) {
			user.setDoorName(doorName);
			user.setDoorNumber(doorNumber);
			history.setActionType(UserHistoryEnum.SUCCESS.getValue());
			try {
				InfoUtils.sendSMS(user.getMobile(), Global.registSUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("短信发送失败");
			}
		}
		// 审核失败 
		if(state==StatusEnum.FAILD.getValue()){
			user.setRemark(remark);
			history.setActionType(UserHistoryEnum.FAILD.getValue());
			try {
				InfoUtils.sendSMS(user.getMobile(), Global.registFILD);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("短信发送失败");
			}
		}
		user.setState(state);
		getEntityManager().merge(user);
		//添加操作记录
		history.setAddTime(new Date());
		history.setUserId(user.getUid());
		history.setOperator(operator);
		getEntityManager().persist(history);
		
		
		//只有当 推荐的时候,才有 返钱
		if(user.getSpreadName() != null && !"".equals(user.getSpreadName().trim()) &&user.getState()==StatusEnum.SUCCESS.getValue()){
			//添加支付记录
			AddPayReq pay = new AddPayReq();
			pay.setUserId(uid);
			pay.setSource(EntityUtils.AwardTypeEnum.REGIST.getValue());
			Pay  tmp = PayUtil.createPay(pay);
			this.getEntityManager().persist(tmp);
			
			//用经纪人的推广码注册时双方各10元，使用内部推广人员的推广码注册，只有当前注册的人有钱
			String hql = "from User user where user.spreadId=? and user.ifInner=0";
			Query query = getEntityManager().createQuery(hql);
			query.setParameter(1, user.getSpreadName());
			List<User> userResult = query.getResultList();
			
			if(userResult != null && userResult.size()>0){
				User u= userResult.get(0);
				AddPayReq pay2 = new AddPayReq();
				pay2.setUserId(u.getUid());
				pay2.setSource(EntityUtils.AwardTypeEnum.INVITE.getValue());
				Pay  tmp2 = PayUtil.createPay(pay2);
				this.getEntityManager().persist(tmp2);
			}
		}
		this.getEntityManager().flush();
	}
	
	@Override
	public void disabledUser(int uid,int operator) {
		if(uid==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		User user = getEntityManager().find(User.class, uid);
		UserHistory history = new UserHistory();
		if(user.getDisable()==true){
			user.setDisable(false);
			history.setActionType(4);
		}else{
			user.setDisable(true);
			history.setActionType(3);
		}
		getEntityManager().merge(user);
		
		history.setAddTime(new Date());
		history.setUserId(user.getUid());
		history.setOperator(operator);
		getEntityManager().persist(history);
		
	}
	
	@Override
	public void addUser(UserRequest user) {
		if(StringUtils.isBlank(user.getMobile())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(StringUtils.isBlank(user.getPassword())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		if(StringUtils.isBlank(user.getRealName())){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101005);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<User> cq_user = cb.createQuery(User.class);
		Root<User> root = cq_user.from(User.class);
		cq_user.where(cb.and(cb.equal(root.get(User_.mobile), user.getMobile()))).select(root);
		List<User> userList = getEntityManager().createQuery(cq_user).getResultList();
		if(userList!=null && userList.size()>0){
			throw new LeoFault(UcConst.UC_EMPTY_REGIST_1000031);
		}
		User u = new User();
		u.setMobile(user.getMobile());
		u.setPassword(MD5Digest.getMD5Digest(user.getPassword()));
		u.setRealName(user.getRealName());
		u.setIfInner(1);
		u.setState(1);
		u.setCreateTime(new Date());
		u.setDisable(true);
//		String spreadId = null;
//		boolean flag = true;
//		while (flag) {
//			final String jpql = "select count(1) from  User u where u.spreadId =? ";
//			Query query = this.getEntityManager().createQuery(jpql);
//			spreadId = DataUtil.createRandomAlphanum(6);
//			query.setParameter(1, spreadId);
//			Long count = (Long) query.getSingleResult();
//			if (new Long(0).equals(count)) {
//				flag = false;
//			}
//		}
		u.setSpreadId(user.getMobile());
		u.setAreaId(user.getAreaId());
		u.setCityId(user.getCityId());//城市ID
		getEntityManager().persist(u);
		
		UserHistory history = new UserHistory();
		history.setAddTime(new Date());
		history.setUserId(u.getUid());
		history.setOperator(user.getOperator());
		history.setActionType(5);
		getEntityManager().persist(history);
		

		HouseResourceViewCount count = new HouseResourceViewCount();
		count.setPublishCount(0);
		count.setUserId(u.getUid());
		getEntityManager().persist(count);
	}
	
	@Override
	public Page<UserHistoryList> getUserHistory(Integer page, Integer rows,int uid) {
//		String hql = "from UserHistory history where history.userId=? order by history.addTime desc";
//		String hql_count = "select count(1) from UserHistory history where history.userId=?";
		
		String hql = "select u.mobile,his.addTime,his.actionType, emp.realName from UserHistory his LEFT JOIN User u ON his.userId = u.uid INNER JOIN Employee emp ON his.operator = emp.employeeId WHERE his.userId=? ORDER BY his.addTime desc";
		String hql_count = "select COUNT(1) from UserHistory his LEFT JOIN User u ON his.userId = u.uid INNER JOIN Employee emp ON his.operator = emp.employeeId where his.userId=?";
		Query query_count = getEntityManager().createNativeQuery(hql_count);
		query_count.setParameter(1, uid);
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter(1, uid);
//		BigInteger counts = (BigInteger)query_count.getSingleResult();
		int total = 0;
		List<BigInteger> counts = query_count.getResultList();
		if (counts != null && counts.size() > 0) {
			total = counts.get(0).intValue();
		}
		Page<UserHistoryList> pages = new Page<UserHistoryList>(page==null?1:page,rows==null?0:rows);
		if(total<=0){
			pages.setRows(new ArrayList<UserHistoryList>(0));
		}else
			pages.setTotal((int)total);
		
		List<Object[]> userHistory = query.setFirstResult((pages.getCurrentPage()-1)*rows).setMaxResults(rows).getResultList();
		
		List<UserHistoryList> infos = new ArrayList<UserHistoryList>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (int i = 0; i < userHistory.size(); i++) {
			Object[] row = userHistory.get(i);
			UserHistoryList history = new UserHistoryList();
			//BeanUtils.copyProperties(userHistory.get(i), history);
			history.setUserName(row[0].toString());
			history.setAddTime((Date)row[1]);
			history.setActionTypeStr(UserHistoryEnum.getByValue((int)row[2]).getDesc());
			history.setOperatorName(row[3].toString());
			infos.add(history);
		}
		pages.setRows(infos);
		return pages;
	}
	
	/**
	 * 修改BD信息
	 */
	@Override
	public void updateUser(UserUpdateRequest request) {
		if(request.getUid()==0){
			throw new LeoFault(EmployeeConst.EMPLOYEE_ERROR101004);
		}
		User user = getEntityManager().find(User.class, request.getUid());
		if(StringUtils.isNotBlank(request.getPassword())){
			user.setPassword(MD5Digest.getMD5Digest(request.getPassword()));
		}
		user.setCityId(request.getCityId());
		user.setAreaId(request.getAreaId());
		getEntityManager().merge(user);
	}
	
	public List<UserValidationModel> getUserModelList(UserValidationModel uv) {
		
		String hql = "from VerifyCode where mobile = ?";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter(1, uv.getMobile().trim());
		List<VerifyCode>  list = query.getResultList();
		List<UserValidationModel>  uvlist = new ArrayList<UserValidationModel>();
		for (VerifyCode vc : list) {
			UserValidationModel uvm = new UserValidationModel();
			BeanUtils.copyProperties(vc, uvm);
			uvm.setStatusStr(EntityUtils.VerifyCodeEnum.getByValue(uvm.getStatus()).getDesc());
			uvlist.add(uvm);
		}
		return uvlist;
		
	}
}
