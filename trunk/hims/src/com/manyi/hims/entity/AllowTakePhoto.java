package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;

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
 * Entity implementation class for Entity: AllowTakePhoto
 * 
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class AllowTakePhoto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "AllowTakePhoto")
	@TableGenerator(name = "AllowTakePhoto", allocationSize = 1)
	private long id;
	
	private int houseState;//  租售状态	1出租，2出售
	private String areaName;// 区域名（例如：上海、湖北、长宁、古北）
	private String serialCode; //对区域进行编号，按照层级关系叠加规则，例如：中国： 00001，北京:0000100001，上海: 0000100002，长宁: 000010000200001
	
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal price = new BigDecimal(0);; //租金 / 到手价
	
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	

}
