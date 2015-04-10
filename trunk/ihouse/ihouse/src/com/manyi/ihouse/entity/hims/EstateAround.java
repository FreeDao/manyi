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
 * 小区周边entity
 * @author fuhao
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="estateId", columnList = "estateId")})
public class EstateAround implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3516794353006265352L;

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "EstateAround")
	@TableGenerator(name = "EstateAround", allocationSize = 1)
	private int id;
	
	/**
	 */
	private int subwayStationId;
	
	/**
	 * 类型 周边公交为1,周边地铁为2,周边餐饮为3,周边超市为4
	 */
	private int type;
	
	/**
	 * 小区id 关联小区
	 */
	private int estateId;
	
	/**
	 * 名称
	 */
	private int name;
	
	/**
	 * 距离长度 单位为米
	 */
	private int distance;
	
	/**
	 * 线路 类型为1即公交的时候可能有字母 所以用String 39路 22路 22A路 类型为2地铁时候为地铁7号线
	 */
	private String lineNum;
	
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
	

}
