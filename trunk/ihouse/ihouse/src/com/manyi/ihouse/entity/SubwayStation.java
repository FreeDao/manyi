package com.manyi.ihouse.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * 地铁站台
 * @author shenyamin
 *
 */
@Entity
@Table(name="iw_subwaystation")
public class SubwayStation implements Serializable{
	private static final long serialVersionUID = -1299005331720283396L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="subwaystation_key")
	@TableGenerator(name="subwaystation_key",initialValue=200,allocationSize=1)
	private int subwayStationId;//站台id
	
	private String stationDesc;

	@Column(nullable=false)
	private String stationLat;

	@Column(nullable=false)
	private String stationLon;

	@Column(nullable=false)
	private String stationName;

	@Column(nullable=false)
	private String stationOrder;

	
	private int stationStatus;

	//bi-directional many-to-one association to Iw2Subwayline	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subwayLineId")
	private SubwayLine subwayLine;
	
	@Column(nullable=false)
	private String stationGeoHash; //经纬度hash值
	
	
	

	public SubwayStation() {
	}

	public int getSubwayStationId() {
		return this.subwayStationId;
	}

	public void setSubwayStationId(int subwayStationId) {
		this.subwayStationId = subwayStationId;
	}

	public String getStationDesc() {
		return this.stationDesc;
	}

	public void setStationDesc(String stationDesc) {
		this.stationDesc = stationDesc;
	}

	public String getStationLat() {
		return this.stationLat;
	}

	public void setStationLat(String stationLat) {
		this.stationLat = stationLat;
	}

	public String getStationLon() {
		return this.stationLon;
	}

	public void setStationLon(String stationLon) {
		this.stationLon = stationLon;
	}

	public String getStationName() {
		return this.stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getStationOrder() {
		return this.stationOrder;
	}

	public void setStationOrder(String stationOrder) {
		this.stationOrder = stationOrder;
	}

	public int getStationStatus() {
		return this.stationStatus;
	}

	public void setStationStatus(int stationStatus) {
		this.stationStatus = stationStatus;
	}

	public SubwayLine getSubwayLine() {
		return this.subwayLine;
	}

	public void setSubwayline(SubwayLine subwayLine) {
		this.subwayLine = subwayLine;
	}

	public String getStationGeoHash() {
		return stationGeoHash;
	}

	public void setStationGeoHash(String geoHash) {
		this.stationGeoHash = geoHash;
	}
	
	
	
	
}
