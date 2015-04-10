package com.manyi.hims.entity.aiwu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;

/**
 * 地铁站台
 * @author shenyamin
 *
 */
@Data
@Entity
@Table(name="subwaystation")
public class SubwayStation implements Serializable{
	private static final long serialVersionUID = -1299005331720283396L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="subwaystation_key")
	@TableGenerator(name="subwaystation_key",initialValue=2378,allocationSize=1)
	private int id;//站台id
	private Integer subwayId;
	
	@Column(nullable=false,length=100)
	private String stationName;//站台名称
	
	@Column(nullable=true,length=500)
	private String stationDesc;//站台描述
	
	@Column(nullable=false)
	private String satationLon;//经度
	
	@Column(nullable=false)
	private String stationLat;//纬度
	//经纬度的geoHash值
	private String geoHash;
	
}
