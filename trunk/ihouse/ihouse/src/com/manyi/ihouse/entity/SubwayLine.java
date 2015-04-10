package com.manyi.ihouse.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 地铁线路�?
 * @author shenyamin
 *
 */
@Entity
@Table(name="iw_subwayline")
public class SubwayLine implements Serializable{
	private static final long serialVersionUID = -9177568477701824220L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="subwayline_key")
	@TableGenerator(name="subwayline_key",initialValue=100,allocationSize=1)
	private int subwayLineId;//主键ID
	
	
	@Column(nullable=false)
	private Integer lineCityCode; //城市编号,区号（不含0）

	private String lineDesc; //线路描述
	
	@Column(nullable=false,length=30)
	private String lineName; //线路名称
	
	@Column(nullable=false)
	private int lineStatus; //线路状态1，2，，，
	
	@Column(nullable=false)
	private String lineLat; //线路纬度
	
	@Column(nullable=false) 
	private String lineLon; //线路经度
	
	@Column(nullable=false)
	private String lineGeoHash; //经纬度hash值
	
	private int level;//地图层级

	//bi-directional many-to-one association to subwaystation
	@OneToMany(mappedBy="subwayLine", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubwayStation> subwayStations = new HashSet<SubwayStation>();

	
	//bi-directional many-to-one association to subwaystation
		@OneToMany(mappedBy="subwayLine", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<SubwayPoint> subwayPoints = new HashSet<SubwayPoint>();
	public SubwayLine() {
	}

	public int getSubwayLineId() {
		return this.subwayLineId;
	}

	public void setSubwayLineId(int subwayLineId) {
		this.subwayLineId = subwayLineId;
	}

	public Integer getLineCityCode() {
		return this.lineCityCode;
	}

	public void setLineCityCode(Integer lineCityCode) {
		this.lineCityCode = lineCityCode;
	}

	public String getLineDesc() {
		return this.lineDesc;
	}

	public void setLineDesc(String lineDesc) {
		this.lineDesc = lineDesc;
	}

	public String getLineName() {
		return this.lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public int getLineStatus() {
		return this.lineStatus;
	}

	public void setLineStatus(int lineStatus) {
		this.lineStatus = lineStatus;
	}

	public Set<SubwayStation> getSubwayStations() {
		return this.subwayStations;
	}

	public void setSubwayStations(Set<SubwayStation> subwayStations) {
		this.subwayStations = subwayStations;
	}

	public String getLineLat() {
		return lineLat;
	}

	public void setLineLat(String lineLat) {
		this.lineLat = lineLat;
	}

	public String getLineLon() {
		return lineLon;
	}

	public void setLineLon(String lineLon) {
		this.lineLon = lineLon;
	}

	public String getLineGeoHash() {
		return lineGeoHash;
	}

	public void setLineGeoHash(String lineGeoHash) {
		this.lineGeoHash = lineGeoHash;
	}

	public Set<SubwayPoint> getSubwayPoints() {
		return subwayPoints;
	}

	public void setSubwayPoints(Set<SubwayPoint> subwayPoints) {
		this.subwayPoints = subwayPoints;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
	
	
	
	
}
