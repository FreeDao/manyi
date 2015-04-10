package com.ecpss.mp.entity;

import static javax.persistence.InheritanceType.JOINED;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToOne;
import javax.persistence.ManyToMany;

/**
 * 不动产基类
 * Entity implementation class for Entity: House
 * 
 */
@Entity
@Inheritance(strategy = JOINED)
public class House implements Serializable {

	private int hid;
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	private HouseStatus status;// 房屋状态 （有效 出售 出租 自住 预订 暂缓 未知 无效）
	private HouseType type;// 类型（例如：多层、高层）
	private HouseDirection direction;// 朝向（例如：南北、东西）
	private Estate estate;// 所属小区或楼盘
	private Owner mainOwner;// 主产权人
	private BigDecimal coveredArea;// 建筑面积
	private BigDecimal spaceArea;// 内空面积
	private HouseRight right;//产权
	private List<HouseCertificate> certificates;//证件（例如：房产证、土地证等）

	private static final long serialVersionUID = 1L;

	public House() {
		super();
	}

	@Id
	@GeneratedValue
	public int getHid() {
		return this.hid;
	}

	public void setHid(int hid) {
		this.hid = hid;
	}

	public String getBuilding() {
		return this.building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public int getFloor() {
		return this.floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getLayers() {
		return this.layers;
	}

	@ManyToOne
	public HouseStatus getStatus() {
		return status;
	}

	public void setStatus(HouseStatus status) {
		this.status = status;
	}

	public void setLayers(int layers) {
		this.layers = layers;
	}

	@ManyToOne
	public HouseType getType() {
		return type;
	}

	public void setType(HouseType type) {
		this.type = type;
	}

	@ManyToOne
	public HouseDirection getDirection() {
		return direction;
	}

	public void setDirection(HouseDirection direction) {
		this.direction = direction;
	}

	@ManyToOne
	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	@ManyToOne
	public Owner getMainOwner() {
		return mainOwner;
	}

	public void setMainOwner(Owner mainOwner) {
		this.mainOwner = mainOwner;
	}

	@Column(scale = 2, precision = 5)
	public BigDecimal getCoveredArea() {
		return coveredArea;
	}

	@Column(scale = 2, precision = 5)
	public void setCoveredArea(BigDecimal coveredArea) {
		this.coveredArea = coveredArea;
	}

	public BigDecimal getSpaceArea() {
		return spaceArea;
	}

	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = spaceArea;
	}

	@ManyToOne
	public HouseRight getRight() {
		return right;
	}

	public void setRight(HouseRight right) {
		this.right = right;
	}

	@ManyToMany
	public List<HouseCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<HouseCertificate> certificates) {
		this.certificates = certificates;
	}

}
