package com.ecpss.mp.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * 业主
 * Entity implementation class for Entity: Owner
 * 
 */
@Entity
public class Owner implements Serializable {

	private int oid;
	private String userName;//用户名
	private String mobile;//手机号
	private String phone;//座机，估计一般企业业主才会登记
	private String email;
	private boolean disable;//是否禁用
	private String password;
	private String realName;//身份证姓名
	private int gender;//姓别-1男人，0保密，1女人
	private String introduce;//介绍
	private static final long serialVersionUID = 1L;

	public Owner() {
		super();
	}

	@Id
	@GeneratedValue
	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
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

	@Column(unique = true, length = 18)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(unique = true, length = 30)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
