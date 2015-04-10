package com.manyi.hims.pay.model;

import java.math.BigDecimal;

import com.manyi.hims.Response;

public class StatisPayRes extends Response{
	/**
	 * 总笔数
	 */
	private int payNum;
	/**
	 * 成功笔数
	 */
	private int success;
	/**
	 * 审核中笔数
	 */
	private int ing;
	/**
	 * 审核失败笔数
	 */
	private int failed;
	/**
	 * 总金额
	 */
	private BigDecimal paySum =new BigDecimal(0);
	/**
	 * 已付金额
	 */
	private BigDecimal sucessSum =new BigDecimal(0);
	private BigDecimal ingSum =new BigDecimal(0);
	private BigDecimal failedSum =new BigDecimal(0);
	public int getPayNum() {
		return payNum;
	}
	public void setPayNum(int payNum) {
		this.payNum = payNum;
	}
	public int getSuccess() {
		return success;
	}
	public void setSuccess(int success) {
		this.success = success;
	}
	public int getIng() {
		return ing;
	}
	public void setIng(int ing) {
		this.ing = ing;
	}
	public int getFailed() {
		return failed;
	}
	public void setFailed(int failed) {
		this.failed = failed;
	}
	public BigDecimal getPaySum() {
		return paySum;
	}
	public void setPaySum(BigDecimal paySum) {
		this.paySum = paySum;
	}
	public BigDecimal getSucessSum() {
		return sucessSum;
	}
	public void setSucessSum(BigDecimal sucessSum) {
		this.sucessSum = sucessSum;
	}
	public BigDecimal getIngSum() {
		return ingSum;
	}
	public void setIngSum(BigDecimal ingSum) {
		this.ingSum = ingSum;
	}
	public BigDecimal getFailedSum() {
		return failedSum;
	}
	public void setFailedSum(BigDecimal failedSum) {
		this.failedSum = failedSum;
	}
	
}
