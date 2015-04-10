package com.manyi.hims.entity;

import com.manyi.hims.entity.Employee;

import java.io.Serializable;
import java.lang.String;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: CallCenterUser
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class CallCenterUser extends Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	private String operatorNumber;// 呼叫员特有的编号

	public String getOperatorNumber() {
		return operatorNumber;
	}

	public void setOperatorNumber(String operatorNumber) {
		this.operatorNumber = operatorNumber;
	}

}
