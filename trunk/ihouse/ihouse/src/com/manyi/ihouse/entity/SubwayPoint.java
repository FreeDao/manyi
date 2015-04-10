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
 * 地铁
 * @author shenyamin 
 *
 */
@Entity
@Table(name="iw_subwaypoint")
public class SubwayPoint implements Serializable{
	private static final long serialVersionUID = -5527773150752712905L;

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy=GenerationType.TABLE,generator="subwaypoint_key")
	@TableGenerator(name="subwaypoint_key",initialValue=200,allocationSize=1)
	private int pointId;//站台id
	
	private String pointDesc;

	@Column(nullable=false)
	private String pointLat;

	@Column(nullable=false)
	private String pointLon;

	@Column(nullable=false)
	private int pointOrder;

	
	private int pointStatus;

	//bi-directional many-to-one association to Iw2Subwayline	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="subwayLineId")
	private SubwayLine subwayLine;
	
	@Column(nullable=false)
	private String pointGeoHash; //经纬度hash值

	public int getPointId() {
		return pointId;
	}

	public void setPointId(int pointId) {
		this.pointId = pointId;
	}

	public String getPointDesc() {
		return pointDesc;
	}

	public void setPointDesc(String pointDesc) {
		this.pointDesc = pointDesc;
	}

	public String getPointLat() {
		return pointLat;
	}

	public void setPointLat(String pointLat) {
		this.pointLat = pointLat;
	}

	public String getPointLon() {
		return pointLon;
	}

	public void setPointLon(String pointLon) {
		this.pointLon = pointLon;
	}

	public int getPointOrder() {
		return pointOrder;
	}

	public void setPointOrder(int pointOrder) {
		this.pointOrder = pointOrder;
	}

	public int getPointStatus() {
		return pointStatus;
	}

	public void setPointStatus(int pointStatus) {
		this.pointStatus = pointStatus;
	}

	public SubwayLine getSubwayLine() {
		return subwayLine;
	}

	public void setSubwayLine(SubwayLine subwayLine) {
		this.subwayLine = subwayLine;
	}

	public String getPointGeoHash() {
		return pointGeoHash;
	}

	public void setPointGeoHash(String pointGeoHash) {
		this.pointGeoHash = pointGeoHash;
	}
	
	
}
