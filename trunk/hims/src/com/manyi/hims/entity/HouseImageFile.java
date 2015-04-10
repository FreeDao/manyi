package com.manyi.hims.entity;

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

@Entity
@Table(indexes = { @Index(name = "id", columnList = "id"), @Index(name = "houseId", columnList = "houseId") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class HouseImageFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "HouseImageFile")
	@TableGenerator(name = "HouseImageFile", allocationSize = 1)
	private int id;
	private int houseId;
	private String type;// 类型：卧室bedroom、客厅livingRoom、卫生间wc、、、EntityUtils.HouseResourceType
	private String description;// 描述 主卧、次卧1、次卧2、门牌1、阳台、外景、、、EntityUtils.HouseResourceType
	private String imgExtensionName;// 图片扩展名称
	private String imgKey;// 阿里云存储的key
	private String thumbnailKey;// 阿里云存储的key ； 图片缩略图
	private int orderId;// 呈现图片的顺序 EntityUtils.HouseResourceType 中第一列
	private Date takePhotoTime; //拍摄照片时间
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date addTime;//新增时间
	
	private int bdTaskId; //bd人员任务id， 默认0
	private int userTaskId; // 中介(经纪人)看房任务id， 默认0 
	private int enable;// 是否可用标识，1可用  0 不可用 ，任务可用，图片就可用
	
}
