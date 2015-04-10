package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 我的收藏
 * @author hubin
 *
 */
@Entity
@Table(name="iw_user_collection")
public class UserCollection implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1098468108791055716L;
	
	@Id
	@GeneratedValue(strategy = TABLE, generator = "iwUserCollection")
	@TableGenerator(name = "iwUserCollection", allocationSize = 1)
	private Long id; //ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user; //用户
	
	@Column(name="house_id")
	private Long houseId; //房源ID
	
    /**
     * 修改时间
     */
    @Column(columnDefinition="TIMESTAMP", insertable = true, updatable = true)  
    @Temporal(TemporalType.TIMESTAMP)
    private Date update_time; //
    
    /**
     * 收藏时间
     */
    private Date create_time; 
    
    /**
     * 备注
     */
    private String memo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getHouseId() {
		return houseId;
	}

	public void setHouseId(Long houseId) {
		this.houseId = houseId;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


}
