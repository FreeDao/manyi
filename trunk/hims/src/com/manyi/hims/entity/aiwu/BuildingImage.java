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
 * 楼栋图片entity
 * @author fuhao
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="buildingId", columnList = "buildingId")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class BuildingImage implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6210686833350165916L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "BuildingImage")
	@TableGenerator(name = "BuildingImage", allocationSize = 1)
	private int id;
	/**
	 * 楼栋图片类型  内景为1,外景是2,信箱图为3,电梯图为4
	 */
	private int type;
	
	/**
	 * 楼栋id
	 */
	private int buildingId;
	
	/**
	 * 图片描述
	 */
	private String description;
	
	/**
	 * 图片后缀名称
	 */
	private String imgExt;
	
	/**
	 * 阿里云图片key
	 */
	private String imgKey;
	
	/**
	 * 阿里云缩略图key
	 */
	private String thumbnailKey;
	
	/**
	 * 图片当前状态 1可用 2不可用 (新增的时候请添加默认值为1)
	 */
	private int status;
	
	/**
	 * 图片排序次序
	 */
	private int orderNum;
	
	/**
	 * 添加时间
	 */
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;
	
}
