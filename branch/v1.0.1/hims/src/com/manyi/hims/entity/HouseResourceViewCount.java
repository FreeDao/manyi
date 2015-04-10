package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 用户 每天查看 出租/出售 的数量限制
 * Entity implementation class for Entity: PublishCount
 *
 */
@Entity

@Table(indexes = { @Index(name = "userId", columnList = "userId") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseResourceViewCount implements Serializable {
	private static final long serialVersionUID = 1L;
	   
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "HouseResourceViewCount")
	@TableGenerator(name = "HouseResourceViewCount", allocationSize = 1)
	private int publishCountId;
	private int sumCount; //每日限制查看的额的总数量
	private int publishCount;//今天已经使用的数量
	private int userId;//用户id
	public int getPublishCountId() {
		return publishCountId;
	}
	public void setPublishCountId(int publishCountId) {
		this.publishCountId = publishCountId;
	}
	public int getSumCount() {
		return sumCount;
	}
	public void setSumCount(int sumCount) {
		this.sumCount = sumCount;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getPublishCount() {
		return publishCount;
	}
	public void setPublishCount(int publishCount) {
		this.publishCount = publishCount;
	}
	
   
	
}
