package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * 手机号归属地
 * @author howard
 *
 */
@Entity
@Data
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class MobileLocation {
	
	/**
	 * id 主键
	 */
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "MobileLocation")
	@TableGenerator(name = "MobileLocation", allocationSize = 1)
	private int id;
	
	/**
	 * 手机号前缀
	 */
	@Column(nullable = false, length = 20)
	private String mobileNumber;
	
	/**
	 * 手机号所属地区
	 */
	@Column(nullable = false, length = 50)
	private String mobileArea;
	
	/**
	 * 手机号类型
	 */
	@Column(nullable = false, length = 50)
	private String mobileType;
	
	/**
	 * 地区代码
	 */
	@Column(nullable = false, length = 10)
	private String areaCode;
	
	/**
	 * 邮政编码
	 */
	@Column(nullable = false, length = 50)
	private String postCode;

}
