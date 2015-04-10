package com.manyi.fyb.callcenter.estate.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import lombok.Data;

/**
 * 小区图片entity
 * @author fuhao
 *
 */
@Data
public class EstateImage implements Serializable {
	
	private static final long serialVersionUID = -7228183560181116425L;
	
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
	private Date addTime;
	/**
	 * 
	 */
	private String imgKeyStr;
	
}
