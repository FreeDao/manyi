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
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.manyi.ihouse.util.NumberUtils;

/**
 * 用户
 * @author shenyamin
 *
 */
@Entity
@Table(name = "iw_user")
public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6753874431430673571L;
	
	public static void main(String[] args) {
		System.out.println(Gender.FEMALE.ordinal());
	}
	
	/**
	 * 性别 1,男;2,女;3,保密;
	 * 
	 */
	public enum Gender {

		MALE(1, "男"),
		
		FEMALE(2, "女"),
		
		UNKNOW(3, "保密");

		private int value;
		private String desc;

		private Gender(int value, String desc) {
			this.value = value;
			this.desc = desc;
		}

		public static Gender getByValue(int value) {
			for (Gender ge : values()) {
				if (NumberUtils.isEqual(value, ge.value)) {
					return ge;
				}
			}
			return UNKNOW;
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
	@GeneratedValue(strategy = TABLE, generator = "iwUser")
	@TableGenerator(name = "iwUser", allocationSize = 1)
	private Long id; //用户ID
	
	@Column(length=50)
	private String mobile; //手机号码
	
	@Column(name="mobile_sn", length=50)
	private String mobileSn; //手机唯一标识 用户自动登陆
	
	@Column(name="real_name", length=50)
	private String name; //姓名
    
    @Column(length=50)
    private String password; //密码
	
	private int gender; //性别 1：男，2：女, 3:保密
	
	@Column(length=4000)
	private String memo; //备注
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="agent_id")
	private Agent agent;// 经纪人
	
	/**
	 * 用户最后登录时间
	 */
	private Date last_login_time;
	
	/**
	 * 用户状态
	 */
	private int user_type;
	
	/**
	 * 系统状态
	 */
	private int sys_status;
	
	/**
	 * 业务状态
	 */
	@Column(columnDefinition="int default 0")
	private int biz_status;
    
	/**
	 * 修改时间
	 */
	@Column(columnDefinition="TIMESTAMP", insertable = true, updatable = true)  
	@Temporal(TemporalType.TIMESTAMP)
    private Date update_time; //
    
	/**
	 * 注册时间
	 */
    private Date create_time; 
	
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<UserCollection> userCollection = new HashSet<UserCollection>(); //我的收藏
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<SeekHouse> seeHouse = new HashSet<SeekHouse>(); //看房单
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<Appointment> appointment = new HashSet<Appointment>(); //约看
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<UserFeedback> userFeedback = new HashSet<UserFeedback>(); //意见反馈

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobileSn() {
		return mobileSn;
	}

	public void setMobileSn(String mobileSn) {
		this.mobileSn = mobileSn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Set<UserCollection> getUserCollection() {
		return userCollection;
	}

	public void setUserCollection(Set<UserCollection> userCollection) {
		this.userCollection = userCollection;
	}

	public Set<SeekHouse> getSeeHouse() {
		return seeHouse;
	}

	public void setSeeHouse(Set<SeekHouse> seeHouse) {
		this.seeHouse = seeHouse;
	}

	public Set<UserFeedback> getUserFeedback() {
		return userFeedback;
	}

	public void setUserFeedback(Set<UserFeedback> userFeedback) {
		this.userFeedback = userFeedback;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
	}

    public Date getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(Date last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public int getSys_status() {
        return sys_status;
    }

    public void setSys_status(int sys_status) {
        this.sys_status = sys_status;
    }

    public int getBiz_status() {
        return biz_status;
    }

    public void setBiz_status(int biz_status) {
        this.biz_status = biz_status;
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
