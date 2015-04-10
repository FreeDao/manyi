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
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 不动产基类 Entity implementation class for Entity: House
 * 
 */
@Data
@Entity
@Table(indexes = { @Index(name = "mainOwnerId", columnList = "mainOwnerId"), @Index(name = "serialCode", columnList = "serialCode") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class House implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "House")
	@TableGenerator(name = "House", allocationSize = 1)
	private int houseId;
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal coveredArea = new BigDecimal(0);// 建筑面积
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	private int houseRightId;// 产权	
	private int status;//1有效，2未审核，3无效, 4删除
	private Date updateTime;
	
	/*********************************** FK ***********************************/
	private int certificates;// 拥有的产证 0没有产证，1房产证，2土地证 1&2双证 和HouseCertificate的id进行&操作
	private int houseTypeId;// 类型（对应HouseType'Id）
	private int houseDirectionId;// 朝向（对应HouseDirection'Id）
	private int estateId;// 所属小区或楼盘（对应Estate'Id）
	private int subEstateId;// 所属子划分Id
	private int mainOwnerId;// 主产权人ID(对应 Owner'ownerId)
	private String otherOwnersId;//其他产权人Id（对应 Owner'Id）格式：1,2,3,4，需要在程序中解析出id集合
	/*********************************** FK ***********************************/
	private String serialCode;//引用Area表中serialCode编号（具体小区的serialCode）
	private int decorateType; //1 毛坯，2 装修,3简装,4精装,5豪装
	
	private int picNum; //图片数目
	private int currBDTaskId;//当前bdtask; 
	private int currUserTaskId;//当前userTask; 
	private int buildingId;//当前楼栋的id; 
	
	/**
	 * 别名  有多个 需要定一个分隔符
	 */
	private String aliasName;
	
}
