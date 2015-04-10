package com.manyi.ihouse.entity.hims;

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

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 楼栋entity
 * @author fuhao
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="estateId", columnList = "estateId"),@Index(name="subEstateId", columnList = "subEstateId")})
public class Building implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4430690192810129064L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Building")
	@TableGenerator(name = "Building", allocationSize = 1)
	private int id;
	/**
	 * 楼栋名称
	 */
	private String name;
	
	/**
	 * 楼栋别名 (多个,需要定义分隔符)
	 */
	private String aliasName;
	
	/**
	 * 总层高
	 */
	private int totalLayers;
	
	/**
	 * 竣工年代 范围1000-2500
	 */
	private int finishDate;
	
	/**
	 * 子划分id
	 */
	private int subEstateId;
	
	/**
	 * 小区id 
	 */
	private int estateId;
		
	/**
	 * 楼栋状态 (为小区,子划分拆分预留 暂时用不到)
	 */
	private int status;
	
	/**
	 * 坐标 经度
	 */
	private String longitude;
	/**
	 * 坐标 纬度
	 */
	private String latitude;
	
	/**
	 * 坐标哈希
	 */
	private String geoHash;
		
	/**
	 * 添加时间
	 */
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;
}
