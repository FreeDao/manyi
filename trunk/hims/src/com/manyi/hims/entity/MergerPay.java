package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: MergerPay
 * 
 */
@Data
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MergerPay implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "MergerPay")
	@TableGenerator(name = "MergerPay", allocationSize = 1)
	private int mergePayId;
	/**
	 * 搜索的开始时间
	 */
	private Date start;
	/**
	 * 搜索条件的 结束时间
	 */
	private Date end;
	/**
	 * 合并的的id 使用 , 隔开
	 * 对应的payId
	 */
	@Column(length = 9200)
	private String payIds;
	/**
	 * 新增时间
	 */
	private Date addTime;
	/**
	 * 笔数
	 */
	private int stroke;
	/**
	 * 这一次的 总金额
	 */
	private BigDecimal money;
	/**
	 * 支付状态
	 */
	private int payState;
	/**
	 * 支付时间
	 */
	private Date payTime;
	
	/**
	 * 失败原因
	 */
	private String remark;
	
	private int userId;
	private String userName;
	private String account;
	/**
	 * 操作员ID
	 */
	private int employeeId;
	private String serialNumber;

}
