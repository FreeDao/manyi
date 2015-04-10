package com.manyi.hims.entity;


import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: OperateUser
 *
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class UserHistory implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	//private int userId;//操作的用户id operatedId

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "UserHistory")
	@TableGenerator(name = "UserHistory", allocationSize = 1)
	private int id;
	private int operator; //employeeID
	private Date addTime; //时间
	private int userId;// userId 
	private int prevStatus ;//先前状态  审核状态0审核成功,1审核中,2审核失败
	private int afterStatus ;//之后状态
	private boolean enabled;//true 启用此用户 false则为禁用此用户
	
	private int actionType ;//1审核成功，2审核失败，3冻结帐号，4启用，5添加推广人员.
	
	public UserHistory() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

	public int getPrevStatus() {
		return prevStatus;
	}

	public void setPrevStatus(int prevStatus) {
		this.prevStatus = prevStatus;
	}

	public int getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(int afterStatus) {
		this.afterStatus = afterStatus;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	
}
