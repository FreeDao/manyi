package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 物业公司信息
 * Entity implementation class for Entity: PropertyManager
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class PropertyManager implements Serializable {
	private static final long serialVersionUID = 1L;

	private int propertyManagerId;
	private String companyName;
	private String fax;
	private String phone;
	private String introduce;
	
	
	/*********************************** FK ***********************************/
	private long addressId;
	/*********************************** FK ***********************************/

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "PropertyManager")
	@TableGenerator(name = "PropertyManager", allocationSize = 1)
	public int getPropertyManagerId() {
		return this.propertyManagerId;
	}

	public void setPropertyManagerId(int propertyManagerId) {
		this.propertyManagerId = propertyManagerId;
	}

	@Column(nullable = false, unique = true, length = 30)
	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	@Column(length = 18)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(length = 18)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
