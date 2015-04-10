package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Entity implementation class for Entity: DbTask
 * 
 */
/**
 * @author tom
 *
 */
@Data
@Entity
@Table(indexes={@Index(name="houseId", columnList = "houseId"), @Index(name="employeeId", columnList = "employeeId")})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)

public class BdTask implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "BdTask")
	@TableGenerator(name = "BdTask", allocationSize = 1)
	private int id;
	private int houseId;
	private int employeeId;  //地推人员id
	private int manageId;  //管理员id 
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	@Getter @Setter private Date createTime; //任务抽查 出来的时间
	@Getter @Setter private int phoneMakerNum;//打电话的次数
	private Date taskDate; // 电话沟通的  预约看房的时间
	private Date finishDate; // 审核完成时间
	private String longitude;// 坐标 经度
	private String latitude;// 坐标 纬度
	private int taskStatus; // 1任务开启(电话预约中) 2预约失败 3自此预约,4预约成功（看房任务开启） 5看房失败 6看房成功
	private String explainStr; // 对该房屋情况作一个简要说明
	private String remark; // 如果任务失败，则要作一个简要的情况说明
	
	private String taskImgStr; // 任务图片统计字段
	private String houseTypeStr; // 房型变化字段
	private String rentPriceStr; // 出租金额变化字段
	private String sellPriceStr; // 出售金额变化字段
	private String spaceAreaStr; // 面积变化字段
	private int aliyunOSSAgain; // 上传图片失败，需要再次上传图片标识 1 需要， 0 不需要

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseId() {
		return houseId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getExplainStr() {
		return explainStr;
	}

	public void setExplainStr(String explainStr) {
		this.explainStr = explainStr;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
 
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public int getManageId() {
		return manageId;
	}

	public void setManageId(int manageId) {
		this.manageId = manageId;
	}

	public String getTaskImgStr() {
		return taskImgStr;
	}

	public void setTaskImgStr(String taskImgStr) {
		this.taskImgStr = taskImgStr;
	}

	public String getHouseTypeStr() {
		return houseTypeStr;
	}

	public void setHouseTypeStr(String houseTypeStr) {
		this.houseTypeStr = houseTypeStr;
	}

	public String getRentPriceStr() {
		return rentPriceStr;
	}

	public void setRentPriceStr(String rentPriceStr) {
		this.rentPriceStr = rentPriceStr;
	}

	public String getSellPriceStr() {
		return sellPriceStr;
	}

	public void setSellPriceStr(String sellPriceStr) {
		this.sellPriceStr = sellPriceStr;
	}

	public String getSpaceAreaStr() {
		return spaceAreaStr;
	}

	public void setSpaceAreaStr(String spaceAreaStr) {
		this.spaceAreaStr = spaceAreaStr;
	}

	public int getAliyunOSSAgain() {
		return aliyunOSSAgain;
	}

	public void setAliyunOSSAgain(int aliyunOSSAgain) {
		this.aliyunOSSAgain = aliyunOSSAgain;
	}


}
