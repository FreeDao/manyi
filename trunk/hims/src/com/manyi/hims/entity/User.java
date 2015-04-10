package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
@Table(indexes = { @Index(name = "areaId", columnList = "areaId") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int uid;
	private String userName;// 用户名
	private String mobile;// 手机号
	private String email;
	private boolean disable;// 是否禁用(0禁用，1启用)
	private String password;
	private String realName;// 身份证姓名
	private int gender;// 姓别-1男人，0保密，1女人
	private String introduce;// 介绍
	private double blance;// 账户余额
	private String spreadId;// 注册成功自动生成推广码
	private String spreadName;// 推广人推广码,被邀请码
	private String code;// 身份证号码
	private String codeUrl; // 身份证图片地址
	private String cardUrl;// 名片图片地址
	private int cityId;// 城市ID(北上广)
	private int areaId;// 区域
	@Getter @Setter private int townId;// 所属区域 下一级别
	private int state;// 账户状态 1 已审核、 2审核中、3 审核失败 ,4删除/冻结;
	private int createLogCount;// 我的发布记录 （提示）条数
	private int createAwardCount; // 我的奖金记录 （提示） 条数
	private Date createTime;
	private String account;// 支付宝帐号

	private int ifInner;// 是否内部. (0,普通经纪人,默认;1,内部人/推广人)

	private int showHistoryData;// 客户端是否显示我所有的发布记录 default value 1(show), if 255 do not show any historical operations

	private String remark;// 审核失败原因
	private String doorName;// 门店名称

	private String doorNumber;// 门店电话

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "User")
	@TableGenerator(name = "User", allocationSize = 1)
	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Column(unique = true, length = 18)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(length = 128)
	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	@Column(length = 128)
	public String getCardUrl() {
		return cardUrl;
	}

	public void setCardUrl(String cardUrl) {
		this.cardUrl = cardUrl;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	@Column(unique = true, length = 20)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(unique = true, nullable = false, length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(unique = true, length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column
	public boolean getDisable() {
		return this.disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}

	@Column(length = 128, nullable = false)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(length = 20)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column
	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	@Column
	public double getBlance() {
		return blance;
	}

	public void setBlance(double blance) {
		this.blance = blance;
	}

	@Column(unique = true, nullable = false, length = 20)
	public String getSpreadId() {
		return spreadId;
	}

	public void setSpreadId(String spreadId) {
		this.spreadId = spreadId;
	}

	public String getSpreadName() {
		return spreadName;
	}

	public void setSpreadName(String spread_name) {
		this.spreadName = spread_name;
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
	}

	public int getIfInner() {
		return ifInner;
	}

	public int getCreateAwardCount() {
		return createAwardCount;
	}

	public void setCreateAwardCount(int createAwardCount) {
		this.createAwardCount = createAwardCount;
	}

	public void setIfInner(int ifInner) {
		this.ifInner = ifInner;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDoorName() {
		return doorName;
	}

	public void setDoorName(String doorName) {
		this.doorName = doorName;
	}

	public String getDoorNumber() {
		return doorNumber;
	}

	public void setDoorNumber(String doorNumber) {
		this.doorNumber = doorNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Column(columnDefinition = "int default 1", nullable = false)
	public int getShowHistoryData() {
		return showHistoryData;
	}

	public void setShowHistoryData(int showHistoryData) {
		this.showHistoryData = showHistoryData;
	}

}
