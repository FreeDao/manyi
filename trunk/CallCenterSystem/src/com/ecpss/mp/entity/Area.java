package com.ecpss.mp.entity;

import static javax.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * 区域化分基类 Entity implementation class for Entity: Area
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Area implements Serializable {

	private int areaId;
	private String name;// 区域名（例如：上海、湖北、长宁、古北）
	private Date createTime;
	private String path;// 坐标路径 ，格式（json）：[[0,0],[1,1],[2,2]]
	private Area parent;
	private String remark;
	private static final long serialVersionUID = 1L;

	public Area() {
		super();
	}

	@Id
	@GeneratedValue
	public int getAreaId() {
		return this.areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	@Column(nullable = false, unique = true, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@ManyToOne(cascade = ALL)
	public Area getParent() {
		return this.parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
