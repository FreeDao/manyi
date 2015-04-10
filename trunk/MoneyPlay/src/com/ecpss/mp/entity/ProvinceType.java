package com.ecpss.mp.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import static javax.persistence.CascadeType.REMOVE;

/**
 * 省的类型（例如：直辖市、自治区、特别行政区，普通省）
 * Entity implementation class for Entity: ProvinceType
 * 
 */
@Entity
public class ProvinceType implements Serializable {

	private int ptid;
	private String title;//抬头
	private List<Province> provinces;
	private static final long serialVersionUID = 1L;

	public ProvinceType() {
		super();
	}

	@Id
	@GeneratedValue
	public int getPtid() {
		return this.ptid;
	}

	public void setPtid(int ptid) {
		this.ptid = ptid;
	}

	@Column(nullable = false, unique = true, length = 20)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@OneToMany(mappedBy = "type", cascade = REMOVE)
	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}

}
