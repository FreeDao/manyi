package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: Employee
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Employee implements Serializable {

	private static final long serialVersionUID = -585034570491574045L;

	private int employeeId;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	
	/**
	 * 手机号
	 */
	private String mobile;
	
	/**
	 * email
	 */
	private String email;
	
	/**
	 * 身份证号 
	 */
	private String number;
	
	/**
	 * 是否禁用
	 */
	private boolean disable;
	
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 身份证姓名
	 */
	private String realName;
	
	/**
	 * 姓别-1男人，0保密，1女人
	 */
	private int gender;
	
	/**
	 * 工号
	 */
	private String jobNumber;
	
	/**
	 * 介绍
	 */
	private String introduce; 
	
	/**
	 * 角色 1管理员,2客服经理，3客服人员,4地推经理,5地推人员 6财务
	 */
	private int role;
	
	/**
	 * 是否在工作模式
	 */
	private boolean workingMode; 
	
	/**
	 * ucserver的用户名
	 */
	@Getter @Setter private String ucServerName;
	
	/**
	 * ucserver的密码
	 */
	@Getter @Setter private String ucServerPwd;
	
	/**
	 * ucserver的用户组
	 */
	@Getter @Setter private String ucServerGroupId;
	
	
	
	private Date addTime; //时间

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Employee")
	@TableGenerator(name = "Employee", allocationSize = 1)
	public int getEmployeeId() {
		return this.employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	@Column(unique = true, nullable = false, length = 30)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(unique = true, length = 11)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(unique = true, length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 16, unique = true)
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(nullable = false)
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

	@Column(length = 30)
	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	@Column(nullable = false)
	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getJobNumber() {
		return this.jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getRole() {
		return this.role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
	@Column(nullable = false)
	public boolean getWorkingMode() {
		return this.workingMode;
	}
	
	public void setWorkingMode(boolean workingMode) {
		this.workingMode = workingMode;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
}
