package com.manyi.ihouse.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 爱屋用户意见反馈
 * @author hubin
 *
 */
@Entity
@Table(name = "iw_user_feedback")
public class UserFeedback implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 331710697263609863L;

	@Id
	@GeneratedValue(strategy = TABLE, generator = "iwUserFeedback")
	@TableGenerator(name = "iwUserFeedback", allocationSize = 1)
	private Long id; //意见反馈Id
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user; //用户
	
	@Column(name="mobile_sn")
	private String mobileSn; //手机设备唯一标识，用于（意见反馈无需注册登录即可使用。不过一旦用户登录，这个反馈信息就应绑定到对应账号，以便我们在后台进行查询）
	
	@Column(name="content")
	private String feedbackContent; //意见内容
	
    /**
     * 修改时间
     */
    @Column(columnDefinition="TIMESTAMP", insertable = true, updatable = true)  
    @Temporal(TemporalType.TIMESTAMP)
    private Date update_time; //
    
    /**
     * 提交时间
     */
    private Date create_time; 
	
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

	public String getMobileSn() {
		return mobileSn;
	}

	public void setMobileSn(String mobileSn) {
		this.mobileSn = mobileSn;
	}

	public String getFeedbackContent() {
		return feedbackContent;
	}

	public void setFeedbackContent(String feedbackContent) {
		this.feedbackContent = feedbackContent;
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
