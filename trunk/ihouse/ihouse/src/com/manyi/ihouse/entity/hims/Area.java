package com.manyi.ihouse.entity.hims;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 区域化分基类 Entity implementation class for Entity: Area
 * 
 */
@Entity
@Table(indexes = { @Index(name = "parentId", columnList = "parentId"), @Index(name = "serialCode", columnList = "serialCode") })
//@Cacheable
//@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	private int areaId;
	private String name;// 区域名（例如：上海、湖北、长宁、古北）
	private Date createTime;
	private String path;// 坐标路径 ，格式（json）：[[0,0],[1,1],[2,2]]
	private String remark;

	/*********************************** FK ***********************************/
	private int parentId;
	/*********************************** FK ***********************************/
	private int status;//1有效，2未审核，3无效, 4删除
	private String serialCode;//对区域进行编号，按照层级关系叠加规则，例如：中国： 00001，北京:0000100001，上海: 0000100002，长宁: 000010000200001
	private int userId;//目前由谁新增的小区，将来可能会让用户新增片区，或者城镇，所以userId放在Aera里面

	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "Area")
	@TableGenerator(name = "Area", allocationSize = 1)
	public int getAreaId() {
		return this.areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

//	@Column(nullable = false, unique = true, length = 20)
	@Column(nullable = false, length = 20)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
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

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	@Column(length = 1000)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@Column (nullable=false)
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
