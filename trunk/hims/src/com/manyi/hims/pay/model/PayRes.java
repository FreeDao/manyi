package com.manyi.hims.pay.model;

import java.math.BigDecimal;

import com.manyi.hims.Response;
import com.manyi.hims.util.EntityUtils;

public class PayRes extends Response{
	private int payId;
	private String userName;//经纪人名称
	private String mobile;//经纪人手机号码
	private String account;//支付宝帐号
	private int userState;//用户状态
	private int userDisable;//用户的属性
	private String userDisableStr;//
	private String userStateStr;
	private BigDecimal paySum;//支付金额
	private int payState;//支付状态
	private String payStateStr;
	private String payReason;//付款理由
	private String remark;//失败理由
	private String addTime;//审核时间
	private String payTime;//支付时间
	
	public int getUserDisable() {
		return userDisable;
	}
	public void setUserDisable(int userDisable) {
		this.userDisable = userDisable;
	}
	public String getUserDisableStr() {
		return userDisableStr;
	}
	public void setUserDisableStr(String userDisableStr) {
		this.userDisableStr = userDisableStr;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public void setUserStateStr(String userStateStr) {
		this.userStateStr = userStateStr;
	}
	public void setPayStateStr(String payStateStr) {
		this.payStateStr = payStateStr;
	}
	public String getPayReason() {
		return payReason;
	}
	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}
	public int getPayId() {
		return payId;
	}
	public void setPayId(int payId) {
		this.payId = payId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getUserState() {
		return userState;
	}
	public void setUserState(int userState) {
		this.userState = userState;
		// 1 已审核、 2审核中、3  审核失败  ,4删除/冻结;
		this.userStateStr = EntityUtils.StatusEnum.getByValue(userState).getDesc();
	}
	public BigDecimal getPaySum() {
		return paySum;
	}
	public void setPaySum(BigDecimal paySum) {
		this.paySum = paySum;
	}
	public int getPayState() {
		return payState;
	}
	public void setPayState(int payState) {
		this.payState = payState;
		////0,未付款;1,付款成功,2,付款失败
		switch (payState) {
		case 0:
			this.payStateStr ="未付款";
			break;
		case 1:
			this.payStateStr ="付款成功";
			break;
		case 2:
			this.payStateStr ="付款失败";
			break;

		default:
			break;
		}
	}
	public String getUserStateStr() {
		return userStateStr;
	}
	public String getPayStateStr() {
		return payStateStr;
	}
	
}