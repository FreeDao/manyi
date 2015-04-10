package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;

/**
 * Entity implementation class for Entity: User
 * 
 */
@Entity
public class User implements Serializable {

	private int uid;
	private String userName;
	private String mobile;
	private String email;
	private int digitalAccount;
	private boolean disable;
	private String password;
	private String realName;
	private int gender;
	private String introduce;
	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}

	@Id
	@GeneratedValue
	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	@Column(unique = true, nullable = false, length = 10)
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

	@Column(unique = true, length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(length = 16, unique = true, nullable = false)
	public int getDigitalAccount() {
		return this.digitalAccount;
	}

	public void setDigitalAccount(int digitalAccount) {
		this.digitalAccount = digitalAccount;
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

	@Column(length = 10)
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

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
