package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 支付表
 * 
 */
@Entity
@Table(indexes = { @Index(name = "userId", columnList = "userId") })
public class Pay implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Pay")
	@TableGenerator(name = "Pay", allocationSize = 1)
	private int payId;
	@Column(scale = 5, precision = 10)
	private BigDecimal paySum = new BigDecimal(0);//支付金额
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;//创建时间
	@Column(nullable=true)
	private Date payTime;//支付时间
	//0,未付款;1,付款成功,2,付款失败
	@Column(columnDefinition="int default 0",nullable=false)
	private int payState;//支付状态 
	@Column(nullable=true)
	private int employeeId;//支付人ID
	private String serialNumber;//支付宝流水号
	private String remark;//失败原因,描述
	private String payReason;//付款理由
	/**
	 * 这笔 数据的 来源(以下动作审核成功的时候,就会添加一笔记录)
	 * 1. 发布出售  7元
	 * 2. 发布出租  5元
	 * 3. 改盘          2元
	 * 4. 举报          20元
	 * 5. 新增小区  10元
	 * 6. 注册成功  10元
	 * 7. 邀请人注册成功 10元
	 */
	private int source;//存入 来源

	/*********************************** FK ***********************************/
	@Column(nullable=false)
	private int userId;
	/*********************************** FK ***********************************/

	public int getPayId() {
		return payId;
	}

	public String getPayReason() {
		return payReason;
	}

	public void setPayReason(String payReason) {
		this.payReason = payReason;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public BigDecimal getPaySum() {
		return paySum;
	}

	public void setPaySum(BigDecimal paySum) {
		this.paySum = paySum;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public int getPayState() {
		return payState;
	}

	public void setPayState(int payState) {
		this.payState = payState;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	

	
}
