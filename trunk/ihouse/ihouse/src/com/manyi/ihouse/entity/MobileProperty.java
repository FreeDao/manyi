package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name = "mobileproperty")
public class MobileProperty implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3446662613810311967L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "mobileproperty")
	@TableGenerator(name = "mobileproperty",initialValue = 2555, allocationSize = 1)
	private long id;
	@Column(length=100)
	private String osType;//系统
	@Column(length=100)
	private String osVersion;//系统版本号
	@Column(length=100)
	private String netType;//网络类型
	@Column(length=100)
	private String imei;//唯一设备ID
	@Column(length=100)
	private String phoneNumber;//手机号码
	@Column(length=100)
	private String brand;//手机品牌
	@Column(length=100)
	private String model;//手机型号
	@Column(length=100)
	private String support;//运营商
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public String getOsType() {
		return osType;
	}
	public void setOsType(String osType) {
		this.osType = osType;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getNetType() {
		return netType;
	}
	public void setNetType(String netType) {
		this.netType = netType;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSupport() {
		return support;
	}
	public void setSupport(String support) {
		this.support = support;
	}
}
