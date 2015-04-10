/**
 * 
 */
package com.manyi.hims.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.manyi.hims.Response;

/**
 * @author zxc
 * 
 */
public class RecordResponse extends Response {

	private int houseId;// 房屋ID
	private String building;// 楼座编号（例如：22栋，22坐，22号）
	private String room;// 房号（例如：1304室，1004－1008室等）
	private BigDecimal spaceArea;// 内空面积
	private int bedroomSum;// 几房
	private int livingRoomSum;// 几厅
	private int wcSum;// 几卫

	private int estateId;// 小区ID
	private String estateName;// 小区名称
	private int areaId;// 行政区ID
	private String areaName;// 行政区name
	private String townName;// 板块名称
	private List<String> address;//小区地址

	private int sourceState;// 审核状态
	private String sourceStateStr;// 审核状态对应的文本

	private Date publishDate;// 发布时间
	private int sourceLogTypeId; // 发布状态
	private String sourceLogTypeStr; // 发布状态对应的文本
	private BigDecimal price;// 价格
	private List<RecordSourceHost> hosts;// 保存联系人的方式
	private String remark;// 备注
	private int stateReason;// 导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）, 5，不售不租，6，不售已租，我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
	private String stateReasonStr;// 导致状态的可能原因,默认值0，1已租，2不租（不想租）,3已售，4，不售（不想售）, 5，不售不租，6，不售已租，我们提供选择，若以后需要新增理由直接增加。这样需要检索由某种原因导致的房源不租不售信息很方便
	private int changeHouseType;// 该盘类型(1出租改盘，2出售改盘，3租售改盘)
	private String reportRemark;// 举报备注

	public int getHouseId() {
		return houseId;
	}

	public List<String> getAddress() {
		return address;
	}

	public void setAddress(List<String> address) {
		this.address = address;
	}

	public int getChangeHouseType() {
		return changeHouseType;
	}

	public String getReportRemark() {
		return reportRemark;
	}

	public String getTownName() {
		return townName;
	}

	public void setTownName(String townName) {
		this.townName = townName;
	}

	public void setReportRemark(String reportRemark) {
		this.reportRemark = reportRemark;
	}

	public void setChangeHouseType(int changeHouseType) {
		this.changeHouseType = changeHouseType;
	}

	public String getStateReasonStr() {
		return stateReasonStr;
	}

	public void setStateReasonStr(String stateReasonStr) {
		this.stateReasonStr = stateReasonStr;
	}

	public int getStateReason() {
		return stateReason;
	}

	public void setStateReason(int stateReason) {
		this.stateReason = stateReason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<RecordSourceHost> getHosts() {
		return hosts;
	}

	public void setHosts(List<RecordSourceHost> hosts) {
		this.hosts = hosts;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public BigDecimal getSpaceArea() {
		return spaceArea;
	}

	public void setSpaceArea(BigDecimal spaceArea) {
		this.spaceArea = isNull(spaceArea).setScale(2, BigDecimal.ROUND_HALF_UP);
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

	public int getEstateId() {
		return estateId;
	}

	public void setEstateId(int estateId) {
		this.estateId = estateId;
	}

	public String getEstateName() {
		return estateName;
	}

	public void setEstateName(String estateName) {
		this.estateName = estateName;
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = isNull(price).setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	public int getSourceState() {
		return sourceState;
	}

	public void setSourceState(int sourceState) {
		this.sourceState = sourceState;
	}

	public String getSourceStateStr() {
		return sourceStateStr;
	}

	public void setSourceStateStr(String sourceStateStr) {
		this.sourceStateStr = sourceStateStr;
	}

	public int getSourceLogTypeId() {
		return sourceLogTypeId;
	}

	public void setSourceLogTypeId(int sourceLogTypeId) {
		this.sourceLogTypeId = sourceLogTypeId;
	}

	public String getSourceLogTypeStr() {
		return sourceLogTypeStr;
	}

	public void setSourceLogTypeStr(String sourceLogTypeStr) {
		this.sourceLogTypeStr = sourceLogTypeStr;
	}

	@SuppressWarnings("unchecked")
	protected static <T extends Number> T isNull(T n) {
		return (T) (n == null ? 0 : n);
	}
}
