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
 * 地铁线路
 * @author shenyamin
 *
 */
@Data
@Entity
@Table(name="subwayline")
public class SubwayLine implements Serializable{
	private static final long serialVersionUID = -9177568477701824220L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="subwayline_key")
	@TableGenerator(name="subwayline_key",initialValue=1233,allocationSize=1)
	private Integer id;
	
	@Column(nullable=false)
	private int cityCode;
	
	@Column(nullable=false,length=5)
	private int lineNumber;
	
	@Column(nullable=false,length=200)
	private String lineName;
	
	@Column(nullable=true,length=500)
	private String lineDescription;
	//地铁中心点经度
	private String lon;
	//纬度
	private String lat;
	//经度纬度的geoHash值
	private String geoHash;
	//所属地铁线
	private Integer parentId;
	
	@Column(nullable=false)
	private boolean lineState = true;
	
}
