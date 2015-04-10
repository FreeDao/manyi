package com.ecpss.mp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: RentInfo
 *
 */
@Entity

public class RentInfo implements Serializable {

	
	private long riid;
	private BigDecimal price;
	private Date trustDate;
	private Date freeDate;
	private static final long serialVersionUID = 1L;

	public RentInfo() {
		super();
	}   
	@Id    
	@GeneratedValue
	public long getRiid() {
		return this.riid;
	}

	public void setRiid(long riid) {
		this.riid = riid;
	}   
	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}   
	public Date getTrustDate() {
		return this.trustDate;
	}

	public void setTrustDate(Date trustDate) {
		this.trustDate = trustDate;
	}   
	public Date getFreeDate() {
		return this.freeDate;
	}

	public void setFreeDate(Date freeDate) {
		this.freeDate = freeDate;
	}
   
}
