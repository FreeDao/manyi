package com.manyi.hims.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: HouseResourceTemp
 *
 */
@Entity

public class HouseResourceTemp implements Serializable {

	private static final long serialVersionUID = 1L;
	   
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	private int houseId;
	private int userId;//
	@Column(scale = 2, precision = 19)
	private BigDecimal rentPrice;//出租价格
	@Column(scale = 2, precision = 19)
	private BigDecimal sellPrice;//出售价格
	private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
	private Date rentFreeDate;// 可入住时间
	private Date entrustDate;//委托时间
	@Column(nullable = false)
	private int houseState;//1出租，2出售，3即租又售，4即不租也不售
	private int stateReason;//导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
	private int actionType;//1发布，2，改盘，3举报，4轮询，5抽查
	private Date publishDate;//发布时间，此房源被发布，改盘，轮询的时间
	private int operatorId;//审核人的Id，每次审核完成清空，此字段跟Employee表关联role,是客服，就是分配的客服人员，是地推，就是分配的地推人员
	private int status;//状态，1审核通过,2审核中，3审核失败4删除无效
	private int checkNum;//客服的审核次数
	private Date resultDate;//客服确认结果 时间(包含 审核通过,审核失败 的结果的时候 的时间)
	private String remark;//备注
	//House Columns
	/*
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private int floor;// 楼层
	private String room;// 房号（例如：1304室，1004－1008室等）
	private int layers;// 总层高
	@Column(scale = 2, precision = 5)
	private BigDecimal coveredArea;// 建筑面积
	 */
	//precision表示数值的总长度，scale表示小数点所占的位数
	@Column(scale = 2, precision = 7)
	private BigDecimal spaceArea;// 内空面积
	/*
	private int houseRightId;// 产权	
	private boolean houseEnabled;
	private int houseStatus;//0有效，1未审核，2无效	
	*/
	/*********************************** FK ***********************************/
	/*
	private boolean certificates;// 拥有的产证 0没有产证，1房产证，2土地证 1&2双证 和HouseCertificate的id进行&操作
	private int houseTypeId;// 类型（对应HouseType'Id）
	private int houseDirectionId;// 朝向（对应HouseDirection'Id）
	private int estateId;// 所属小区或楼盘（对应Estate'Id）
	private int mainOwnerId;// 主产权人ID(对应 Owner'ownerId)
	private String otherOwnersId;//其他产权人Id（对应 Owner'Id）格式：1,2,3,4，需要在程序中解析出id集合
	*/
	/*********************************** FK ***********************************/
	
	//If Residence
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫
	private int balconySum;// 几阳台
	
	//If Office
	/*
	 *Office Columns 
	 */
	//If Plant
	/*
	 *  Plant Columns 
	 */
	//If Store
	/*
	 *  Store Columns 
	 */
	//If TruckSpace
	/*
	 * TruckSpace
	 */
	//Temp表冗余所有的字段，用户回溯
	
	public HouseResourceTemp() {
		super();
	}   
	public int getHouseId() {
		return this.houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public BigDecimal getRentPrice() {
		return rentPrice;
	}
	public void setRentPrice(BigDecimal rentPrice) {
		this.rentPrice = rentPrice;
	}
	public BigDecimal getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}
	public boolean isGotPrice() {
		return isGotPrice;
	}
	public void setGotPrice(boolean isGotPrice) {
		this.isGotPrice = isGotPrice;
	}
	public Date getRentFreeDate() {
		return rentFreeDate;
	}
	public void setRentFreeDate(Date rentFreeDate) {
		this.rentFreeDate = rentFreeDate;
	}
	public Date getEntrustDate() {
		return entrustDate;
	}
	public void setEntrustDate(Date entrustDate) {
		this.entrustDate = entrustDate;
	}
	public int getHouseState() {
		return houseState;
	}
	public void setHouseState(int houseState) {
		this.houseState = houseState;
	}
	public int getStateReason() {
		return stateReason;
	}
	public void setStateReason(int stateReason) {
		this.stateReason = stateReason;
	}
	public int getActionType() {
		return actionType;
	}
	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(int checkNum) {
		this.checkNum = checkNum;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getBedroomSum() {
		return bedroomSum;
	}
	public void setBedroomSum(int bedroomSum) {
		this.bedroomSum = bedroomSum;
	}
	public int getLivingRoomSum() {
		return livingRoomSum;
	}
	public void setLivingRoomSum(int livingRoomSum) {
		this.livingRoomSum = livingRoomSum;
	}
	public int getWcSum() {
		return wcSum;
	}
	public void setWcSum(int wcSum) {
		this.wcSum = wcSum;
	}
	public int getBalconySum() {
		return balconySum;
	}
	public void setBalconySum(int balconySum) {
		this.balconySum = balconySum;
	}
	public BigDecimal getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = spaceArea;
	}
   
}
