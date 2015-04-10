package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: Address
 * 
 */
@Entity
@Table(indexes={@Index(name="estateId", columnList = "estateId")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Address implements Serializable {

	private long addressId;
	private String address;
	
	/*********************************** FK ***********************************/
	private int estateId;// 地址所属的楼盘或小区
	/*********************************** FK ***********************************/

	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Address")
	@TableGenerator(name = "Address", allocationSize = 1)
	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

}
