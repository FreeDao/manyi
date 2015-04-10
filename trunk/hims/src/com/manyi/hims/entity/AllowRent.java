package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: AllowRent
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class AllowRent implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private long id;
	private String areaName;// 区域名（例如：上海、湖北、长宁、古北）
	private String serialCode; //对区域进行编号，按照层级关系叠加规则，例如：中国： 00001，北京:0000100001，上海: 0000100002，长宁: 000010000200001
	private BigDecimal rentPrice;

	public BigDecimal getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}
	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "AllowRent")
	@TableGenerator(name = "AllowRent", allocationSize = 1)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	 
	public String getAreaName() {
		return areaName;
	}
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
}
