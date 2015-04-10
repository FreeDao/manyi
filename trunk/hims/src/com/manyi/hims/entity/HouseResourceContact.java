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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 出租/出售 信息 关联的 用户联系方式
 * 
 * @author tiger
 * 
 */
@Entity
@Table(indexes = { @Index(name = "houseId", columnList = "houseId") })
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
public class HouseResourceContact implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "HouseResourceContact")
	@TableGenerator(name = "HouseResourceContact", allocationSize = 1)
	private int contactId;
	private String hostName;//联系人姓名
	private String hostMobile;//联系人电话
	private int houseId; //关联房源id
	private boolean enable; //禁用代表历史联系人   启用代表当前联系人   1 true为当前;  0 false 为历史
	private Date backTime;//归档时间 '
	private int historyId;// 可以为空 ,初始话的时候是空的,当审核完成的时候 需要添加这个 外键  houseResourceHistory
	
	@Getter @Setter private int houseState;//是出租1 出售2状态 // 1出租，2出售
	
	private int status;//(默认为未勾选状态，即未打电话状态  标记为0),第一类统一称为“有效”1，第二类为“待确认”2，第三类为“无效”3 因审核失败而删除 added by fuhao tips tom 

	public int getHouseId() {
		return houseId;
	}

	public int getHistoryId() {
		return historyId;
	}

	public void setHistoryId(int historyId) {
		this.historyId = historyId;
	}

	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getBackTime() {
		return backTime;
	}

	public void setBackTime(Date backTime) {
		this.backTime = backTime;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostMobile() {
		return hostMobile;
	}

	public void setHostMobile(String hostMobile) {
		this.hostMobile = hostMobile;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	


}
