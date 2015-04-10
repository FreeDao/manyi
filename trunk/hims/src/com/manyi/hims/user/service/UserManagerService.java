package com.manyi.hims.user.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import com.leo.common.Page;
import com.manyi.hims.Response;
import com.manyi.hims.user.conroller.UserManagerController.getUserRequest;
import com.manyi.hims.user.model.UserRequest;
import com.manyi.hims.user.model.UserValidationModel;
public interface UserManagerService {

	public Page<UserResponse> getUser(getUserRequest request,Integer page, Integer rows,String realName,String spreanName,int type,String mobile,int state);
	
	public void updateUserState(int uid,int state,String doorName,String doorNumber,String remark,int operator);
	
	public UserResponse getUserById(int uid);
	
	public void disabledUser(int uid,int operator);
	
	public void addUser(UserRequest user);
	
	public Page<UserHistoryList> getUserHistory(Integer page, Integer rows,int uid);
	
	public List<UserValidationModel> getUserModelList(UserValidationModel uv);
	
	//public void getSourceLog(int uid);
	/**
	 *  修改BD信息
	 * @param request
	 */
	public void updateUser(UserUpdateRequest request);
	
	@Data
	public static class UserUpdateRequest{
		private int uid;
		private String password;
		private int cityId;
		private int areaId;
	}
	
	public static class UserHistoryList{
		@Getter @Setter private int id;
		@Getter @Setter private int operator; //employeeID
		@Getter @Setter private String operatorName; //employeeID
		private Date addTime; //时间
		@Getter @Setter private String addTimeStr;// userId 
		@Getter @Setter private int userId;// userId 
		@Getter @Setter private String userName;// userId
		@Getter @Setter private int actionType ;//1审核成功，2审核失败，3冻结帐号，4启用，5添加推广人员.
		@Getter @Setter private String actionTypeStr;
		public Date getAddTime() {
			return addTime;
		}
		public void setAddTime(Date addTime) {
			this.addTime = addTime;
			this.addTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(addTime);
		}
	}
	
	public static class UserResponse extends Response{
		private int uid;
		private String mobile;//手机号
		private boolean disable;//是否禁用
		private String disableStr;
		private String realName;//身份证姓名
		private String spreadId;//注册成功自动生成推广码
		private String spreadName;//注册邀请人
		private String code;//身份证号码
		private String codeUrl ; //身份证图片地址
		private String cardUrl;//名片图片地址
		private String areaName ;//所属区域
		private String cityName; //城市名
		private int state;//账户状态  1审核中、  0 已审核、2  审核失败  
		private String stateStr;
		private int createLogCount;//我的发布记录   条数
		private Date createTime;
		private String createTimeStr;
		private String alipayAccount;
		private BigDecimal waitPayBlance;//待支付金额
		private BigDecimal havePayBlance;//已支付金额
		private int spreadCount;
		
		/**
		 * 邀请人 是否 BD(1,BD ;  0, 外部人员)
		 */
		private int otherInner;
		
		public int getOtherInner() {
			return otherInner;
		}
		public void setOtherInner(int otherInner) {
			this.otherInner = otherInner;
		}
		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public boolean isDisable() {
			return disable;
		}
		public void setDisable(boolean disable) {
			this.disable = disable;
		}
		public String getRealName() {
			return realName;
		}
		public void setRealName(String realName) {
			this.realName = realName;
		}
		public String getSpreadId() {
			return spreadId;
		}
		public void setSpreadId(String spreadId) {
			this.spreadId = spreadId;
		}
		public String getSpreadName() {
			return spreadName;
		}
		public void setSpreadName(String spreadName) {
			this.spreadName = spreadName;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getCodeUrl() {
			return codeUrl;
		}
		public void setCodeUrl(String codeUrl) {
			this.codeUrl = codeUrl;
		}
		public String getCardUrl() {
			return cardUrl;
		}
		public void setCardUrl(String cardUrl) {
			this.cardUrl = cardUrl;
		}
		public String getAreaName() {
			return areaName;
		}
		public void setAreaName(String areaName) {
			this.areaName = areaName;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getCreateLogCount() {
			return createLogCount;
		}
		public void setCreateLogCount(int createLogCount) {
			this.createLogCount = createLogCount;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
			if(createTime!=null){
				this.createTimeStr = new SimpleDateFormat("yyyy-MM-dd").format(createTime);
			}
		}
		public String getStateStr() {
			return stateStr;
		}
		public void setStateStr(String stateStr) {
			this.stateStr = stateStr;
		}
		public String getDisableStr() {
			return disableStr;
		}
		public void setDisableStr(String disableStr) {
			this.disableStr = disableStr;
		}
		public String getCreateTimeStr() {
			return createTimeStr;
		}
		public String getAlipayAccount() {
			return alipayAccount;
		}
		public void setAlipayAccount(String alipayAccount) {
			this.alipayAccount = alipayAccount;
		}
		public void setCreateTimeStr(String createTimeStr) {
			this.createTimeStr = createTimeStr;
		}
		public int getSpreadCount() {
			return spreadCount;
		}
		public void setSpreadCount(int spreadCount) {
			this.spreadCount = spreadCount;
		}
		public BigDecimal getWaitPayBlance() {
			return waitPayBlance;
		}
		public void setWaitPayBlance(BigDecimal waitPayBlance) {
			this.waitPayBlance = waitPayBlance;
		}
		public BigDecimal getHavePayBlance() {
			return havePayBlance;
		}
		public void setHavePayBlance(BigDecimal havePayBlance) {
			this.havePayBlance = havePayBlance;
		}
		public String getCityName() {
			return cityName;
		}
		public void setCityName(String cityName) {
			this.cityName = cityName;
		}
	}
}
