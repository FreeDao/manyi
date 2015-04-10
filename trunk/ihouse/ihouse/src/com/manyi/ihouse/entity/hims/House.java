package com.manyi.ihouse.entity.hims;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 不动产基类 Entity implementation class for Entity: House
 * 
 */
@Entity
@Table(indexes = { @Index(name = "mainOwnerId", columnList = "mainOwnerId"), @Index(name = "serialCode", columnList = "serialCode") })
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class House implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "House")
	@TableGenerator(name = "House", allocationSize = 1)
	private int houseId;
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal coveredArea = new BigDecimal(0);// 建筑面积
	@Column(scale = 2, precision = 7,nullable=false)
	private BigDecimal spaceArea = new BigDecimal(0);// 内空面积
	private int houseRightId;// 产权	
	private int status;//1有效，2未审核，3无效, 4删除
	private Date updateTime;
	
	/*********************************** FK ***********************************/
	private int certificates;// 拥有的产证 0没有产证，1房产证，2土地证 1&2双证 和HouseCertificate的id进行&操作
	private int houseTypeId;// 类型（对应HouseType'Id）
	private int houseDirectionId;// 朝向（对应HouseDirection'Id）
	private int estateId;// 所属小区或楼盘（对应Estate'Id）
	private int mainOwnerId;// 主产权人ID(对应 Owner'ownerId)
	private String otherOwnersId;//其他产权人Id（对应 Owner'Id）格式：1,2,3,4，需要在程序中解析出id集合
	/*********************************** FK ***********************************/
	private String serialCode;//引用Area表中serialCode编号（具体小区的serialCode）
	private int decorateType; //1 毛坯，2 装修
	
	@Getter @Setter private int picNum; //图片数目
	@Getter @Setter private int currBDTaskId;//当前bdtask; 
	@Getter @Setter private int currUserTaskId;//当前userTask; 
	
		
	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
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

	public int getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(int houseTypeId) {
		this.houseTypeId = houseTypeId;
	}

	public int getHouseDirectionId() {
		return houseDirectionId;
	}

	public void setHouseDirectionId(int houseDirectionId) {
		this.houseDirectionId = houseDirectionId;
	}

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public int getMainOwnerId() {
		return mainOwnerId;
	}

	public void setMainOwnerId(int mainOwnerId) {
		this.mainOwnerId = mainOwnerId;
	}

	public String getOtherOwnersId() {
		return otherOwnersId;
	}

	public void setOtherOwnersId(String otherOwnersId) {
		this.otherOwnersId = otherOwnersId;
	}

	public BigDecimal getCoveredArea() {
		return coveredArea;
	}

	public void setCoveredArea(BigDecimal coveredArea) {
		this.coveredArea = coveredArea;
	}

	public BigDecimal getSpaceArea() {
		return spaceArea;
	}

	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = spaceArea;
	}

	public int getHouseRightId() {
		return houseRightId;
	}

	public void setHouseRightId(int houseRightId) {
		this.houseRightId = houseRightId;
	}

	public int getCertificates() {
		return certificates;
	}

	public void setCertificates(int certificates) {
		this.certificates = certificates;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public int getDecorateType() {
		return decorateType;
	}

	public void setDecorateType(int decorateType) {
		this.decorateType = decorateType;
	}

	 
	
}
