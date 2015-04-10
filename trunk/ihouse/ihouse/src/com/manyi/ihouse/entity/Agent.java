package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 经纪人信息实体类
 * @author hubin
 *
 */
@Entity
@Table(name = "iw_agent")
public class Agent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8669077363330337274L;
	
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = TABLE, generator = "iwAgent")
	@TableGenerator(name = "iwAgent", allocationSize = 1)
	private long id;
	
	/**
	 * 经纪人姓名
	 */
	@Column(name="uname", length=20)
	private String name;
	
	/**
	 * 经纪人编号
	 */
	@Column(name="serial_number",length=50)
	private String serialNumber;
	
	@Column(length=50)
	private String password;
	
	/**
	 * 手机号码
	 */
	@Column(length=50)
	private String mobile;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 经纪人头像照片URL
	 */
    @Column(name="photo_url")
	private String photoUrl;
    
    /**
     * 更新日期
     */
    @Column(columnDefinition="TIMESTAMP", insertable = true, updatable = true)  
    @Temporal(TemporalType.TIMESTAMP)
    private Date update_time;
    
    /**
     * 创建日期
     */
    private Date create_time;
    
    /**
     * status状态
     */
    private int status;

	@OneToMany(mappedBy = "agent", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
