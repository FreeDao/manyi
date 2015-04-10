/**
 * 
 */
package com.manyi.hims.uc.service;

import java.util.List;

import com.manyi.hims.entity.Residence;
import com.manyi.hims.entity.User;
import com.manyi.hims.uc.controller.UserRestController.AgainRegistRequest;
import com.manyi.hims.uc.controller.UserRestController.AwardPageRequest;
import com.manyi.hims.uc.controller.UserRestController.BindPaypalRequest;
import com.manyi.hims.uc.controller.UserRestController.ChangePaypalRequest;
import com.manyi.hims.uc.controller.UserRestController.FailedDetailRes;
import com.manyi.hims.uc.controller.UserRestController.RegistRequest;

/**
 * @author lei
 *
 */
public interface UserService {
	public User login(String loginName,String password);
	public void insertResidence(Residence residence);
	public long findLoginPwd(String phone);
	public boolean checkVerify(String verifyCode,String sessionCode);
	public void updateLoginPwd(String phone,String newPwd);
	
	public void modifyLoginPwd(int uid,String oldPwd,String newPwd);
	
	/**
	 * 获取用户信息包括密码
	 */
	public User getUser(int uid,String passwd);
	
	/**
	 * 检测手机号是否已经注册
	 * @param moblie
	 * @return
	 */
	public int checkMobile(String moblie);
	/**
	 * 注册时  获取短信验证码
	 * @param moblie
	 * @return
	 */
	public String findMsgCode(String moblie);
	/**
	 * 用户注册
	 * @param request
	 * @return
	 */
	public int regist(RegistRequest req, String codeMsg);
	/**
	 * 登录
	 * @author fangyouhui
	 *
	 */
	public UserInfo userLogin(String loginName,String password);
	/**
	 * 我的账户
	 * @author fangyouhui
	 *
	 */
	public MyAccountResponse myAccount(int uid);
	/**
	 *  新增小区
	 * @author fangyouhui
	 * @param esateName
	 * @param parentId
	 * @param address
	 */
	public void addEsate(String esateName,int estateId,String address,int uid);
	
	/**
	 * 检查 输入的 密码
	 * @param password
	 */
	public int checkPassword(String password);
	
	/**
	 * 举报
	 * @author fangyouhui
	 *
	 */
	public ReportResponse report(int houseId);
	/**
	 * 我的奖金
	 * @param employee
	 * @return
	 */
	public List<AwardResponse> awardForMe (int uid,Long markTime) ;
	/**
	 * 我的奖金加载列表
	 * @param awardPage
	 * @return
	 */
	public List<AwardResponse> findAwardPage(AwardPageRequest awardPage);
	/**
	 * 绑定支付宝
	 * @param employee
	 * @return
	 */
	public int bindingPaypal(BindPaypalRequest bindPaypal);
	/**
	 * 修改支付宝
	 * @param employee
	 * @return
	 */
	public int changePaypal(ChangePaypalRequest changePaypal);
	/**
	 * init acryonym data
	 * @param houseId
	 * @param reportLogTypeId
	 * @param remark
	 * @param uid
	 */
	public int initAcronymData();
	public void reportSubmit(int houseId,int reportLogTypeId,String remark,int uid);
	public static class ReportResponse{
		private int houseId;//房子ID
		private boolean sellEnabled;//是否在租
		private boolean rentEnabled;//是否在售
		private String estateName;
		private String building;
		private String room;
		public int getHouseId() {
			return houseId;
		}
		public void setHouseId(int houseId) {
			this.houseId = houseId;
		}
		public boolean isSellEnabled() {
			return sellEnabled;
		}
		public void setSellEnabled(boolean sellEnabled) {
			this.sellEnabled = sellEnabled;
		}
		public boolean isRentEnabled() {
			return rentEnabled;
		}
		public void setRentEnabled(boolean rentEnabled) {
			this.rentEnabled = rentEnabled;
		}
		public String getEstateName() {
			return estateName;
		}
		public void setEstateName(String estateName) {
			this.estateName = estateName;
		}
		public String getBuilding() {
			return building;
		}
		public void setBuilding(String building) {
			this.building = building;
		}
		public String getRoom() {
			return room;
		}
		public void setRoom(String room) {
			this.room = room;
		}
	}
	public static class AwardResponse{
		private String awardNum;   //奖金数额
		private String awardDate;  //发奖日期
		private Long markTime;
		private String payType;  //发布奖金类型
		
		public String getAwardNum() {
			return awardNum;
		}
		public void setAwardNum(String awardNum) {
			this.awardNum = awardNum;
		}
		public String getAwardDate() {
			return awardDate;
		}
		public void setAwardDate(String awardDate) {
			this.awardDate = awardDate;
		}
		public Long getMarkTime() {
			return markTime;
		}
		public void setMarkTime(Long markTime) {
			this.markTime = markTime;
		}
		public String getPayType() {
			return payType;
		}
		public void setPayType(String payType) {
			this.payType = payType;
		}
		
	}
	public static class BindPaypalResponse{
		private String paypalAccount; //支付宝账号

		public String getPaypalAccount() {
			return paypalAccount;
		}

		public void setPaypalAccount(String paypalAccount) {
			this.paypalAccount = paypalAccount;
		}
	}
	public static class ChangePaypalResponse{
		private String paypalAccount;	//支付宝账号
		
		private String password;	//个人账号密码

		public String getPaypalAccount() {
			return paypalAccount;
		}

		public void setPaypalAccount(String paypalAccount) {
			this.paypalAccount = paypalAccount;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
	}
	public static class MyAccountResponse{
		private double blance;//账户余额
		private String spreadId;//注册成功自动生成推广码
		private int spreanCount;//已推广人数
		private int awardCount; //我的奖金记录数
		private String paypalAccount; //我的支付宝账号(如果支付宝账号为空未绑定否则为绑定)
		private int createCount;//发布记录
		public double getBlance() {
			return blance;
		}
		public void setBlance(double blance) {
			this.blance = blance;
		}
		public String getSpreadId() {
			return spreadId;
		}
		public void setSpreadId(String spreadId) {
			this.spreadId = spreadId;
		}
		public int getSpreanCount() {
			return spreanCount;
		}
		public void setSpreanCount(int spreanCount) {
			this.spreanCount = spreanCount;
		}
		public int getCreateCount() {
			return createCount;
		}
		public void setCreateCount(int createCount) {
			this.createCount = createCount;
		}
		public int getAwardCount() {
			return awardCount;
		}
		public void setAwardCount(int awardCount) {
			this.awardCount = awardCount;
		}
		public String getPaypalAccount() {
			return paypalAccount;
		}
		public void setPaypalAccount(String paypalAccount) {
			this.paypalAccount = paypalAccount;
		}
		
		
	}
	public static class UserInfo{
		private int uid;
		private String userName;//用户名
		private int state;//账户状态  0 审核中、  1 已审核、2  审核失败
		private int sumCount; //每日限制查看的额的总数量
		private int PublishCount;//今天已经使用的数量
		private String alipayAccount;
		private int areaId;
		public int getUid() {
			return uid;
		}
		public void setUid(int uid) {
			this.uid = uid;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getSumCount() {
			return sumCount;
		}
		public void setSumCount(int sumCount) {
			this.sumCount = sumCount;
		}
		public int getPublishCount() {
			return PublishCount;
		}
		public void setPublishCount(int publishCount) {
			PublishCount = publishCount;
		}
		public String getAlipayAccount() {
			return alipayAccount;
		}
		public void setAlipayAccount(String alipayAccount) {
			this.alipayAccount = alipayAccount;
		}
		public int getAreaId() {
			return areaId;
		}
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}
	}
//	public int testRegist(RegistRequest req);
	
	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public FailedDetailRes getFailedDetail(String mobile);
	
	/**
	 * 房源宝用户注册修改
	 * @param params
	 * @return
	 */
	public int againRegist(AgainRegistRequest params);
	
}
