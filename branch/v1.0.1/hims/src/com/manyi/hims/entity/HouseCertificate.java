package com.manyi.hims.entity;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: HouseCertificate
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseCertificate implements Serializable {
	private static final long serialVersionUID = 1L;

	private int certificateId;//拥有的产证  0没有产证，1房产证，2土地证 
	private String certificateType;
	private String introduce;

	@Id
	public int getCertificateId() {
		return this.certificateId;
	}

	public void setCertificateId(int certificateId) {
		this.certificateId = certificateId;
	}

	public String getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

}
