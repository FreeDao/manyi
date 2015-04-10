package com.ecpss.mp.entity;

import java.io.Serializable;
import java.lang.String;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: House
 * 
 */
@Entity
public class House implements Serializable {

	private int hid;
	private String building;//楼座编号（例如：22栋，22坐，22号）
	private int floor;//楼层
	private String room;//房号（例如：1304室，1004－1008室等）
	private int layers;//总层高
	private int bedroomSum;//几房
	private int livingRoomSum;//几厅
	private int wcSum;//几卫
	private int balconySum;//几阳台
	
	private HouseUsage usage;//用途（例如：别墅、住宅、商住两用、商铺）
	private HouseType type;//类型（例如：多层、高层）
	private HouseDirection direction;//朝向（例如：南北、东西）
	private Estate estate;//所属小区或楼盘
	private Owner mainOwner;//主产权人
	private BigDecimal coveredArea;//建筑面积
	private BigDecimal spaceArea;//内空面积
	
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

	public void setLayers(int layers) {
		this.layers = layers;
	}

	public int getBedroomSum() {
		return this.bedroomSum;
	}

	public void setBedroomSum(int bedroomSum) {
		this.bedroomSum = bedroomSum;
	}

	public int getLivingRoomSum() {
		return this.livingRoomSum;
	}

	public void setLivingRoomSum(int livingRoomSum) {
		this.livingRoomSum = livingRoomSum;
	}

	public int getWcSum() {
		return this.wcSum;
	}

	public void setWcSum(int wcSum) {
		this.wcSum = wcSum;
	}

	public int getBalconySum() {
		return this.balconySum;
	}

	public void setBalconySum(int balconySum) {
		this.balconySum = balconySum;
	}

	@ManyToOne
	public HouseUsage getUsage() {
		return usage;
	}

	public void setUsage(HouseUsage usage) {
		this.usage = usage;
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

}
