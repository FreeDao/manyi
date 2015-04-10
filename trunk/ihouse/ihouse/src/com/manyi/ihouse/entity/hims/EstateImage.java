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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * 小区图片entity
 * @author fuhao
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="estateId", columnList = "estateId")})
public class EstateImage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7228183560181116425L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "EstateImage")
	@TableGenerator(name = "EstateImage", allocationSize = 1)
	private int id;
	/**
	 * 小区图片类型 小区内景为1,外景是2
	 */
	private int type;
	
	/**
	 * 小区id
	 */
	private int estateId;
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
