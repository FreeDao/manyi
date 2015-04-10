package com.manyi.hims.entity;

import static javax.persistence.GenerationType.TABLE;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.Data;

/**
 * Entity implementation class for Entity: VerifyCode
 *
 */
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL)
@Data
public class VerifyCode implements Serializable {

	   
	@Id
	@Column(columnDefinition = "BIGINT UNSIGNED")
	@GeneratedValue(strategy = TABLE, generator = "VerifyCode")
	@TableGenerator(name = "VerifyCode", allocationSize = 1)
	private int id;
	/**
	 * 手机号码
	 */
	private String mobile;
	/**
	 * 短信返回状态: -2 网络异常，请求失败,  -1 用户名或者密码不正确，1 发送短信成功，2 余额不够，3 扣费失败（请联系客服），6 有效号码为空，7 短信内容为空  8 无签名,
	 * 9 没有Url提交权限 ,10 发送号码过多,最多支持200个号码,11 产品ID异常,12 参数异常,14  产品余额为0，禁止提交，联系客服
	 * 15 Ip验证失败,19 短信内容过长，最多支持500个
	 */
	private int status;
	/**
	 * 验证码
	 */
	private String verifyCode;
	/**
	 * 短信发送时间
	 */
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP",nullable=false,insertable=false,updatable=false)
	private Date createTime;
	private static final long serialVersionUID = 1L;

	public VerifyCode() {
		super();
	}   
}
