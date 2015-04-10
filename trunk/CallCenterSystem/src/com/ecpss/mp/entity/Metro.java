package com.ecpss.mp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 地铁信息
 * Entity implementation class for Entity: Metro
 * 
 */
@Entity
public class Metro implements Serializable {

	private int mid;
	private int number;
	private String title;
	private String introduce;
	private String path;//地铁的坐标路径 ，格式（json）：[[0,0],[1,1],[2,2]]
	private List<MetroStation> stations;
	
	private static final long serialVersionUID = 1L;

	public Metro() {
		super();
	}

	@Id
	@GeneratedValue
	public int getMid() {
		return this.mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIntroduce() {
		return this.introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@OneToMany(mappedBy = "owner")
	public List<MetroStation> getStations() {
		return stations;
	}

	public void setStations(List<MetroStation> stations) {
		this.stations = stations;
	}



}
