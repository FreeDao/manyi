package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import java.util.List;

import javax.persistence.*;

/**
 * 地铁站
 * Entity implementation class for Entity: MetroStation
 * 
 */
@Entity
public class MetroStation implements Serializable {

	private int msid;
	private String name;
	private String introduce;
	private int longitude;//经度
	private int latitude;//纬度
	private List<MetroExport> exports;//出口
	private Metro owner;
	private static final long serialVersionUID = 1L;

	public MetroStation() {
		super();
	}

	@Id
	@GeneratedValue
	public int getMsid() {
		return this.msid;
	}

	public void setMsid(int msid) {
		this.msid = msid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	
	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	@OneToMany(mappedBy = "owner")
	public List<MetroExport> getExports() {
		return exports;
	}

	public void setExports(List<MetroExport> exports) {
		this.exports = exports;
	}

	@ManyToOne
	public Metro getOwner() {
		return owner;
	}

	public void setOwner(Metro owner) {
		this.owner = owner;
	}

}
