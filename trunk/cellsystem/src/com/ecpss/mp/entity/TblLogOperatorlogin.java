package com.ecpss.mp.entity;

/*

 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Entity
@Table(name = "tbl_log_operatorlogin")
public class TblLogOperatorlogin  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblLogOperatorlogin";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DLOGINNAME = "dloginname";
	public static final String ALIAS_DOPERATORNAME = "doperatorname";
	public static final String ALIAS_DOPERATORID = "doperatorid";
	public static final String ALIAS_DTYPE = "dtype";
	public static final String ALIAS_DRESULT = "dresult";
	public static final String ALIAS_DDESC = "ddesc";
	public static final String ALIAS_DLOGINIP = "dloginip";
	public static final String ALIAS_DTIME = "dtime";
	public static final String ALIAS_DCOMPANYCODE = "dcompanycode";
	
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * did       db_column: D_ID 
     */ 	
	
	private java.lang.Long did;
    /**
     * dloginname       db_column: D_LOGINNAME 
     */ 	
	@NotBlank @Length(max=16)
	private java.lang.String dloginname;
    /**
     * doperatorname       db_column: D_OPERATORNAME 
     */ 	
	@NotBlank @Length(max=16)
	private java.lang.String doperatorname;
    /**
     * doperatorid       db_column: D_OPERATORID 
     */ 	
	@NotNull 
	private java.lang.Long doperatorid;
    /**
     * dtype       db_column: D_TYPE 
     */ 	
	@NotNull 
	private java.lang.Integer dtype;
    /**
     * dresult       db_column: D_RESULT 
     */ 	
	@NotNull 
	private java.lang.Integer dresult;
    /**
     * ddesc       db_column: D_DESC 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String ddesc;
    /**
     * dloginip       db_column: D_LOGINIP 
     */ 	
	@NotBlank @Length(max=32)
	private java.lang.String dloginip;
    /**
     * dtime       db_column: D_TIME 
     */ 	
	@NotNull 
	private java.util.Date dtime;
    /**
     * dcompanycode       db_column: D_COMPANYCODE 
     */ 	
	@NotNull 
	private java.lang.Long dcompanycode;
	//columns END


	public TblLogOperatorlogin(){
	}

	public TblLogOperatorlogin(
		java.lang.Long did
	){
		this.did = did;
	}

	

	public void setDid(java.lang.Long value) {
		this.did = value;
	}
	
	@Id 
	@GenericGenerator(name="custom-id", strategy = "assigend")
	@Column(name = "D_ID", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDid() {
		return this.did;
	}
	
	@Column(name = "D_LOGINNAME", unique = false, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getDloginname() {
		return this.dloginname;
	}
	
	public void setDloginname(java.lang.String value) {
		this.dloginname = value;
	}
	
	@Column(name = "D_OPERATORNAME", unique = false, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getDoperatorname() {
		return this.doperatorname;
	}
	
	public void setDoperatorname(java.lang.String value) {
		this.doperatorname = value;
	}
	
	@Column(name = "D_OPERATORID", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDoperatorid() {
		return this.doperatorid;
	}
	
	public void setDoperatorid(java.lang.Long value) {
		this.doperatorid = value;
	}
	
	@Column(name = "D_TYPE", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDtype() {
		return this.dtype;
	}
	
	public void setDtype(java.lang.Integer value) {
		this.dtype = value;
	}
	
	@Column(name = "D_RESULT", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDresult() {
		return this.dresult;
	}
	
	public void setDresult(java.lang.Integer value) {
		this.dresult = value;
	}
	
	@Column(name = "D_DESC", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getDdesc() {
		return this.ddesc;
	}
	
	public void setDdesc(java.lang.String value) {
		this.ddesc = value;
	}
	
	@Column(name = "D_LOGINIP", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getDloginip() {
		return this.dloginip;
	}
	
	public void setDloginip(java.lang.String value) {
		this.dloginip = value;
	}
	
	@Column(name = "D_TIME", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public java.util.Date getDtime() {
		return this.dtime;
	}
	
	public void setDtime(java.util.Date value) {
		this.dtime = value;
	}
	
	@Column(name = "D_COMPANYCODE", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDcompanycode() {
		return this.dcompanycode;
	}
	
	public void setDcompanycode(java.lang.Long value) {
		this.dcompanycode = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Did",getDid())
			.append("Dloginname",getDloginname())
			.append("Doperatorname",getDoperatorname())
			.append("Doperatorid",getDoperatorid())
			.append("Dtype",getDtype())
			.append("Dresult",getDresult())
			.append("Ddesc",getDdesc())
			.append("Dloginip",getDloginip())
			.append("Dtime",getDtime())
			.append("Dcompanycode",getDcompanycode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblLogOperatorlogin == false) return false;
		if(this == obj) return true;
		TblLogOperatorlogin other = (TblLogOperatorlogin)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

