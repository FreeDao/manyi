package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.manyi.ihouse.util.NumberUtils;

/**
 * 约会
 * @author hubin
 *
 */
@Entity
@Table(name = "iw_appointment")
public class Appointment {
	/**
	 * 约会状态;
	 * 
	 */
	public enum AppointmentState {

		待确认(1, "待确认"),

		待看房(2, "待看房"),

		经纪人已签到(3, "经纪人已签到"),

        未到未看房(4, "未到未看房"),

		已到未看房(5, "已到未看房"),

        已到已看房(6, "已到已看房"),

        已改期(7, "已改期"),

		已取消(8, "已取消");

		private int value;
		private String desc;

		private AppointmentState(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static AppointmentState getByValue(int value) {
			for (AppointmentState ge : values()) {
				if (NumberUtils.isEqual(value, ge.value)) {
					return ge;
				}
			}
			return 待确认;
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
	
	/**
	 * 取消约会原因状态;
	 * 1-我已租到房子；2-我不租了；3-其他
	 */
	public enum CancelReason {

		我已租到房子(1, "我已租到房子"),

		我不租了(2, "我不租了"),

		其他(3, "其他");

		private int value;
		private String desc;

		private CancelReason(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static CancelReason getByValue(int value) {
			for (CancelReason ge : values()) {
				if (NumberUtils.isEqual(value, ge.value)) {
					return ge;
				}
			}
			return 其他;
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
	@GeneratedValue(strategy = TABLE, generator = "iwAppointment")
	@TableGenerator(name = "iwAppointment", allocationSize = 1)
	private Long id; //约会ID
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user; //用户ID
	
	/**
	 * 经纪人id
	 */
	private long agent_id;

	/**
	 * 约看时间
	 */
	private Date appointment_time; //约看时间
	
	/**
	 * 经纪人签到时间
	 */
	private Date agent_checkin_time;
	
	/**
	 * 经纪人签到地址
	 */
	private String agent_checkin_addr;
    
    /**
     * 用户签到时间
     */
    private Date user_checkin_time;
    
    /**
     * 用户签到地址
     */
    private String user_checkin_addr;
    
	@Column(name="meet_address")
	private String meetAddress; //碰头地点
	
	private String memo;//备注
	
	@Column(name="appointment_state")
	private Integer appointmentState; //约看单状态   (1, "待确认"),(2, "待看房"),(3, "经纪人已签到"),(4, "已到已看房"),(5, "已到未看房"),(6, "未到未看房"),(7, "已取消"),	(8, "已改期");
	
	@Column(nullable=true)
	private Integer ability; //专业知识  分数 1-5分

    @Column(nullable=true)
	private Integer appearance; //仪容仪表  分数 1-5分

    @Column(nullable=true)
	private Integer attitude; //服务态度 分数 1-5分
	
	private String appraise; //评价
	
	@Column(name="change_date_memo")
	private String changeDateMemo; //改期说明
	
    @Column(name="cancel_memo")
	private String cancelMemo; //取消说明
    
    private Integer aware_state; //知晓状态 Null或0：未知晓，1：已知晓

    @Column(name="cancel_reason",nullable=true)
	private Integer cancelReason; //取消原因   1-我已租到房子；2-我不租了；3-其他；
	
	/**
	 * 看房单号
	 * 编号规则：交易类型-合约类型-城市-日期-4位合约编号
	 * 业务类型有三种：
     *   租房  缩写R (for rental)
     *   二手房  缩写S (for sales)
     *   新房 缩写N (for new)
     * 一套合约一共有三个类型：
     *   看房协议 缩写K  (for看房)
     *   租约  缩写 C (for contract) 
     *   收款单（for 房东）缩写 B (for billing)
	 * R-K-sh(城市）-YYYYMMDD-4位随机数
	 * eg. R-K-sh-20140616-0015
	 */
	@Column(name="seehouse_number", length=30)
	private String seehouseNumber; 

	@OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
	private Set<SeekHouse> seekHouse = new HashSet<SeekHouse>(); // 约看的房源

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<SeekhouseHistory> seekhouseHistory = new HashSet<SeekhouseHistory>(); // 约看的房源历史

	public Set<SeekhouseHistory> getSeekhouseHistory() {
        return seekhouseHistory;
    }

    public void setSeekhouseHistory(Set<SeekhouseHistory> seekhouseHistory) {
        this.seekhouseHistory = seekhouseHistory;
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

	public String getMeetAddress() {
		return meetAddress;
	}

	public void setMeetAddress(String meetAddress) {
		this.meetAddress = meetAddress;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getAppointmentState() {
		return appointmentState;
	}

	public void setAppointmentState(Integer appointmentState) {
		this.appointmentState = appointmentState;
	}

	public String getComment() {
		return appraise;
	}

	public void setComment(String comment) {
		this.appraise = comment;
	}

	public Integer getAbility() {
		return ability;
	}

	public void setAbility(Integer ability) {
		this.ability = ability;
	}

	public Integer getAppearance() {
		return appearance;
	}

	public void setAppearance(Integer appearance) {
		this.appearance = appearance;
	}

	public Integer getAttitude() {
		return attitude;
	}

	public void setAttitude(Integer attitude) {
		this.attitude = attitude;
	}

	public Set<SeekHouse> getSeekHouse() {
		return seekHouse;
	}

	public void setSeekHouse(Set<SeekHouse> seekHouse) {
		this.seekHouse = seekHouse;
	}

	public String getChangeDateMemo() {
		return changeDateMemo;
	}

	public void setChangeDateMemo(String changeDateMemo) {
		this.changeDateMemo = changeDateMemo;
	}

	public String getCancelMemo() {
		return cancelMemo;
	}

	public void setCancelMemo(String cancelMemo) {
		this.cancelMemo = cancelMemo;
	}

	public Integer getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(Integer cancelReason) {
		this.cancelReason = cancelReason;
	}

    public String getSeehouseNumber() {
        return seehouseNumber;
    }

    public void setSeehouseNumber(String seehouseNumber) {
        this.seehouseNumber = seehouseNumber;
    }

    public long getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(long agent_id) {
        this.agent_id = agent_id;
    }

    public Date getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(Date appointment_time) {
        this.appointment_time = appointment_time;
    }

    public Date getAgent_checkin_time() {
        return agent_checkin_time;
    }

    public void setAgent_checkin_time(Date agent_checkin_time) {
        this.agent_checkin_time = agent_checkin_time;
    }

    public String getAgent_checkin_addr() {
        return agent_checkin_addr;
    }

    public void setAgent_checkin_addr(String agent_checkin_addr) {
        this.agent_checkin_addr = agent_checkin_addr;
    }

    public Date getUser_checkin_time() {
        return user_checkin_time;
    }

    public void setUser_checkin_time(Date user_checkin_time) {
        this.user_checkin_time = user_checkin_time;
    }

    public String getUser_checkin_addr() {
        return user_checkin_addr;
    }

    public void setUser_checkin_addr(String user_checkin_addr) {
        this.user_checkin_addr = user_checkin_addr;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public Integer getAware_state() {
        return aware_state;
    }

    public void setAware_state(Integer aware_state) {
        this.aware_state = aware_state;
    }
	
}
