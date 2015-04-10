package com.ecpss.mp.entity;

import com.ecpss.mp.entity.Address;
import java.io.Serializable;
import java.lang.String;
import javax.persistence.*;

/**
 * 物业公司信息
 * Entity implementation class for Entity: PropertyManager
 * 
 */
@Entity
public class PropertyManager implements Serializable {

	private int pmid;
	private String companyName;
	private String fax;
	private String phone;
	private Address address;
	private String introduce;
	private static final long serialVersionUID = 1L;

	public PropertyManager() {
		super();
	}

	@Id
	@GeneratedValue
	public int getPmid() {
		return this.pmid;
	}

	public void setPmid(int pmid) {
		this.pmid = pmid;
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

	@OneToOne
	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
