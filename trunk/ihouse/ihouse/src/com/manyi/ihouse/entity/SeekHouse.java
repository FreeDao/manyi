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

import com.manyi.ihouse.util.NumberUtils;

/**
 * 看房单
 * @author hubin
 *
 */
@Entity
@Table(name="iw_seekhouse")
public class SeekHouse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1288059827917805838L;
	
	/**
	 * 房源信息;
	 * 
	 */
	public enum HouseState {

		开始(0, "开始"),
		
		预约中(1, "预约中"),
		
		待确认(2, "待确认"),
		
		待看房(3, "待看房"),
		
		待登记(4, "待登记"),
		
		已看房(5, "已看房"),
		
		未看房(6, "未看房"),
        
		改期中(7, "改期中"),
        
        取消看房(8, "取消看房"),

        已下架(9, "已下架");

		private int value;
		private String desc;

		private HouseState(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static HouseState getByValue(int value) {
			for (HouseState ge : values()) {
				if (NumberUtils.isEqual(value, ge.value)) {
					return ge;
				}
			}
			return 开始;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}
	}

	@Id
	@GeneratedValue(strategy = TABLE, generator = "iwSeekHouse")
	@TableGenerator(name = "iwSeekHouse", allocationSize = 1)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(name="house_id")
	private Long houseId;
	
	private int state; //房源状态 （0，”开始“）（1, "预约中"）；(2, "待确认")；(3, "待看房")；(4, "待登记")；(5, "已看房")；(6, "未看房")
	
	private Integer recommend_source; //用户的1  ； 经纪人推荐 2

    /**
	 * 经纪人id
	 */
	private long agent_id;
	
	/**
	 * 备注
	 */
	private String memo;
	
	/**
	 * 希望约看时间
	 */
	@Column(name="wish_time")
	private String wishTime;
	

    /**
     * 修改时间
     */
    @Column(columnDefinition="TIMESTAMP", insertable = true, updatable = true)  
    @Temporal(TemporalType.TIMESTAMP)
    private Date update_time; //
    
    /**
     * 创建时间
     */
    private Date create_time; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="appointment_id")
	private Appointment appointment; //约会
    
    public Integer getRecommend_source() {
        return recommend_source;
    }

    public void setRecommend_source(Integer recommend_source) {
        this.recommend_source = recommend_source;
    }
	
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

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getWishTime() {
		return wishTime;
	}

	public void setWishTime(String wishTime) {
		this.wishTime = wishTime;
	}

	public Appointment getAppointment() {
		return appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

    public long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(long agent_id) {
        this.agent_id = agent_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
	
}
