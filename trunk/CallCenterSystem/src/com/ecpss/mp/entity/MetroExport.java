package com.ecpss.mp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * 地铁出口信息
 * Entity implementation class for Entity: MetroExport
 * 
 */
@Entity
public class MetroExport implements Serializable {

	private int meid;
	private int number;//地铁编号
	private int longitude;//经度
	private int latitude;//纬度
	
	private MetroStation owner;
	private static final long serialVersionUID = 1L;

	public MetroExport() {
		super();
	}

	@Id
	@GeneratedValue
	public int getMeid() {
		return this.meid;
	}

	public void setMeid(int meid) {
		this.meid = meid;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
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

	@ManyToOne
	public MetroStation getOwner() {
		return owner;
	}

	public void setOwner(MetroStation owner) {
		this.owner = owner;
	}

}
