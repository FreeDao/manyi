package com.manyi.fyb.callcenter.user.model;

import java.util.Date;

import com.manyi.fyb.callcenter.base.model.Response;

public class UserResponse extends Response{
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
	private int state;//账户状态  1审核中、  0 已审核、2  审核失败  
	private String stateStr;
	private int createLogCount;//我的发布记录   条数
	private Date createTime;
	private String createTimeStr;
	private String alipayAccount;
	private double waitPayBlance;//待支付金额
	private double havePayBlance;//已支付金额
	private int spreadCount;
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
	public String getDisableStr() {
		return disableStr;
	}
	public void setDisableStr(String disableStr) {
		this.disableStr = disableStr;
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
	public String getStateStr() {
		return stateStr;
	}
	public void setStateStr(String stateStr) {
		this.stateStr = stateStr;
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
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public double getWaitPayBlance() {
		return waitPayBlance;
	}
	public void setWaitPayBlance(double waitPayBlance) {
		this.waitPayBlance = waitPayBlance;
	}
	public double getHavePayBlance() {
		return havePayBlance;
	}
	public void setHavePayBlance(double havePayBlance) {
		this.havePayBlance = havePayBlance;
	}
	public int getSpreadCount() {
		return spreadCount;
	}
	public void setSpreadCount(int spreadCount) {
		this.spreadCount = spreadCount;
	}
}
