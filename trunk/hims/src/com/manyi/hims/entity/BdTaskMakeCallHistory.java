package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class BdTaskMakeCallHistory {
	/**
	 * historyId
	 */
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "BdTaskMakeCallHistory")
	@TableGenerator(name = "BdTaskMakeCallHistory", allocationSize = 1)
	private int historyId;
	/**
	 * BDtaskId
	 */
	private int bdTaskId;
	/**
	 * 打电话的人
	 */
	private int phoneMaker;
	
	private String phoneMakerName;
	
	private int employeeId;
	
	private String employeeName;
	
	private int taskStatus;
	/**
	 * 打电话的内容反馈
	 */
	private String note;
	
	private Date taskDate; // 任务时间
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;

}
