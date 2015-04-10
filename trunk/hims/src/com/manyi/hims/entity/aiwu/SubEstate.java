package com.manyi.hims.entity.aiwu;


import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

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

import lombok.Data;

/**
 * 子划分entity
 * @author fuhao
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="estateId", columnList = "estateId")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class SubEstate implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4776076612621475521L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "SubEstate")
	@TableGenerator(name = "SubEstate", allocationSize = 1)
	private int id;
	/**
	 * 编号(导入子划分时使用)
	 */
	private String number;
	/**
	 * 子划分的名称
	 */
	private String name;
	
	/**
	 * 小区id
	 */
	private int estateId;
		
	/**
	 * 子划分状态 (预留 暂时用不到)
	 * 1有效，2未审核，3无效, 4删除
	 */
	private int status;
		
	/**
	 * 添加时间
	 */
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;
}
