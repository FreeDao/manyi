package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Getter;
import lombok.Setter;

/**
 * Entity implementation class for Entity: HouseResourceHistory
 *
 */
@Entity
@Table(indexes = { @Index(name = "houseId", columnList = "houseId"), @Index(name = "checkNum", columnList = "checkNum"), @Index(name = "userId", columnList = "userId") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseResourceHistory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "HouseResourceHistory")
	@TableGenerator(name = "HouseResourceHistory", allocationSize = 1)
	private int historyId;
	private int houseId;//对应的House Resource ID，多对1
	private BigDecimal rentPrice;//出租价格
	
	
	//@Getter @Setter private BigDecimal checkRentPrice;//审核后的出租价格
	//@Getter @Setter private BigDecimal checkSellPrice;//审核后的出售价格
	
	private BigDecimal sellPrice;//出售价格
	
	private BigDecimal spaceArea;// 内空面积
	
	private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
	private Date rentFreeDate;// 可入住时间
	private Date entrustDate;//委托时间
	private int houseState;//1出租，2出售，3即租又售，4即不租也不售
	@Getter @Setter
	private int afterCheckHouseState;//1出租，2出售，3即租又售，4即不租也不售
	private int stateReason;//导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）, 5，不售不租，6，不售已租，我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
	private int actionType;//1发布，2，改盘，3举报，4轮询，5抽查 ,6新增小区对应HouseResourceTypeEnum
	private Date publishDate;//发布时间，此房源被发布，改盘，轮询的时间
	private int operatorId;//审核人的Id，每次审核完成清空，此字段跟Employee表关联role,是客服，就是分配的客服人员，是地推，就是分配的地推人员
	private int status;//状态，1审核通过,2审核中，3审核失败, 4删除
	private int checkNum;//客服的审核次数
	private String remark;//备注	
	private int userId;//经纪人用户对象，若有系统发起，比如：轮询，userId为0
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date createTime;//记录时间
	private Date resultDate; //审核完成时间(包含 审核通过,审核失败 的结果的时候 的时间)
	
	@Getter @Setter
	private String note;
	
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreateTime() {
		return createTime;
	}
	
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}
	public int getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(int operatorId) {
		this.operatorId = operatorId;
	}
	
	/*public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}

	private int checkStatus;//审核状态，0审核通过，1审核中，2审核失败
	 */
	
	public HouseResourceHistory() {
		super();
	}   
	public int getHistoryId() {
		return this.historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
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
	public BigDecimal getSpaceArea() {
		return spaceArea;
	}
	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = spaceArea;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
   
}
