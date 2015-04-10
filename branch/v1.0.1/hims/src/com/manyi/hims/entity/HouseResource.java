package com.manyi.hims.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: HouseResource
 *
 */
@Entity
@Table(indexes={@Index(name="actionType", columnList = "actionType")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseResource implements Serializable {

	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	private int houseId;//1=1 House
	private int userId;//
	@Column(scale = 2, precision = 19)
	private BigDecimal rentPrice;//出租价格
	@Column(scale = 2, precision = 19)
	private BigDecimal sellPrice;//出售价格
	private boolean isGotPrice;//是否到手价，标记sellPrice是否是到手价
	private Date rentFreeDate;// 可入住时间
	private Date entrustDate;//委托时间
	private int houseState;//1出租，2出售，3即租又售，4即不租也不售
	private int stateReason;//导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）, 5，不售不租，6，不售已租，/*举报7，该房源未出租/出售    8，该房源地址不存在，9其他*/我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
	private int actionType;//1发布，2，改盘，3举报，4轮询，5抽查
	private Date publishDate;//发布时间，此房源被发布，改盘，轮询的时间
	private int operatorId;//审核人的Id，每次审核完成清空，此字段跟Employee表关联role,是客服，就是分配的客服人员，是地推，就是分配的地推人员
	private int status;//状态，1审核通过,2审核中，3审核失败, 4无效/删除
	private int checkNum;//客服的审核次数
	private Date resultDate;//客服确认结果 时间(包含 审核通过,审核失败 的结果的时候 的时间)
	private String remark;//备注
	
	public int getHouseId() {
		return houseId;
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

	public HouseResource() {
		super();
	}
   
}
