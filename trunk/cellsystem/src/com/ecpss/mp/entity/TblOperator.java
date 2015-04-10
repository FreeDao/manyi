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

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Entity
@Table(name = "tbl_operator")
public class TblOperator implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblOperator";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DLOGINNAME = "dloginname";
	public static final String ALIAS_DPASSWORD = "dpassword";
	public static final String ALIAS_DGENDER = "dgender";
	public static final String ALIAS_DREALNAME = "drealname";
	public static final String ALIAS_DISPLATOPERATOR = "displatoperator";
	public static final String ALIAS_DOPERATORTYPE = "doperatortype";
	public static final String ALIAS_DCOMPANYID = "dcompanyid";
	public static final String ALIAS_DLOGINIP = "dloginip";
	public static final String ALIAS_DLOGINTIME = "dlogintime";
	public static final String ALIAS_DCREATETIME = "dcreatetime";
	public static final String ALIAS_DUPDATETIME = "dupdatetime";
	public static final String ALIAS_DEMAIL = "demail";
	public static final String ALIAS_DTEL = "dtel";
	public static final String ALIAS_DSTATUS = "dstatus";
	public static final String ALIAS_DERRORTIMES = "derrortimes";
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * did       db_column: D_ID 
     */ 	
	
	private java.lang.Long did;
    /**
     * dloginname       db_column: D_LOGINNAME 
     */ 	
	@Length(max=16)
	private java.lang.String dloginname;
    /**
     * dpassword       db_column: D_PASSWORD 
     */ 	
	@Length(max=32)
	private java.lang.String dpassword;
    /**
     * dgender       db_column: D_GENDER 
     */ 	
	
	private java.lang.Integer dgender;
    /**
     * drealname       db_column: D_REALNAME 
     */ 	
	@Length(max=16)
	private java.lang.String drealname;
    /**
     * displatoperator       db_column: D_ISPLATOPERATOR 
     */ 	
	
	private java.lang.Integer displatoperator;
    /**
     * doperatortype       db_column: D_OPERATORTYPE 
     */ 	
	
	private java.lang.Integer doperatortype;
    /**
     * dcompanyid       db_column: D_COMPANYID 
     */ 	
	
	private java.lang.Long dcompanyid;
    /**
     * dloginip       db_column: D_LOGINIP 
     */ 	
	@Length(max=20)
	private java.lang.String dloginip;
    /**
     * dlogintime       db_column: D_LOGINTIME 
     */ 	
	
	private java.util.Date dlogintime;
    /**
     * dcreatetime       db_column: D_CREATETIME 
     */ 	
	
	private java.util.Date dcreatetime;
    /**
     * dupdatetime       db_column: D_UPDATETIME 
     */ 	
	
	private java.util.Date dupdatetime;
    /**
     * demail       db_column: D_EMAIL 
     */ 	
	@Length(max=32)
	private java.lang.String demail;
    /**
     * dtel       db_column: D_TEL 
     */ 	
	@Length(max=16)
	private java.lang.String dtel;
    /**
     * dstatus       db_column: D_STATUS 
     */ 	
	
	private java.lang.Integer dstatus;
    /**
     * derrortimes       db_column: D_ERRORTIMES 
     */ 	
	
	private java.lang.Integer derrortimes;
	//columns END


	public TblOperator(){
	}

	public TblOperator(
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
	
	@Column(name = "D_LOGINNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getDloginname() {
		return this.dloginname;
	}
	
	public void setDloginname(java.lang.String value) {
		this.dloginname = value;
	}
	
	@Column(name = "D_PASSWORD", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDpassword() {
		return this.dpassword;
	}
	
	public void setDpassword(java.lang.String value) {
		this.dpassword = value;
	}
	
	@Column(name = "D_GENDER", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDgender() {
		return this.dgender;
	}
	
	public void setDgender(java.lang.Integer value) {
		this.dgender = value;
	}
	
	@Column(name = "D_REALNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getDrealname() {
		return this.drealname;
	}
	
	public void setDrealname(java.lang.String value) {
		this.drealname = value;
	}
	
	@Column(name = "D_ISPLATOPERATOR", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDisplatoperator() {
		return this.displatoperator;
	}
	
	public void setDisplatoperator(java.lang.Integer value) {
		this.displatoperator = value;
	}
	
	@Column(name = "D_OPERATORTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDoperatortype() {
		return this.doperatortype;
	}
	
	public void setDoperatortype(java.lang.Integer value) {
		this.doperatortype = value;
	}
	
	@Column(name = "D_COMPANYID", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDcompanyid() {
		return this.dcompanyid;
	}
	
	public void setDcompanyid(java.lang.Long value) {
		this.dcompanyid = value;
	}
	
	@Column(name = "D_LOGINIP", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getDloginip() {
		return this.dloginip;
	}
	
	public void setDloginip(java.lang.String value) {
		this.dloginip = value;
	}
	
	@Column(name = "D_LOGINTIME", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getDlogintime() {
		return this.dlogintime;
	}
	
	public void setDlogintime(java.util.Date value) {
		this.dlogintime = value;
	}
	
	@Column(name = "D_CREATETIME", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getDcreatetime() {
		return this.dcreatetime;
	}
	
	public void setDcreatetime(java.util.Date value) {
		this.dcreatetime = value;
	}
	
	@Column(name = "D_UPDATETIME", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getDupdatetime() {
		return this.dupdatetime;
	}
	
	public void setDupdatetime(java.util.Date value) {
		this.dupdatetime = value;
	}
	
	@Column(name = "D_EMAIL", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDemail() {
		return this.demail;
	}
	
	public void setDemail(java.lang.String value) {
		this.demail = value;
	}
	
	@Column(name = "D_TEL", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getDtel() {
		return this.dtel;
	}
	
	public void setDtel(java.lang.String value) {
		this.dtel = value;
	}
	
	@Column(name = "D_STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDstatus() {
		return this.dstatus;
	}
	
	public void setDstatus(java.lang.Integer value) {
		this.dstatus = value;
	}
	
	@Column(name = "D_ERRORTIMES", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDerrortimes() {
		return this.derrortimes;
	}
	
	public void setDerrortimes(java.lang.Integer value) {
		this.derrortimes = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Did",getDid())
			.append("Dloginname",getDloginname())
			.append("Dpassword",getDpassword())
			.append("Dgender",getDgender())
			.append("Drealname",getDrealname())
			.append("Displatoperator",getDisplatoperator())
			.append("Doperatortype",getDoperatortype())
			.append("Dcompanyid",getDcompanyid())
			.append("Dloginip",getDloginip())
			.append("Dlogintime",getDlogintime())
			.append("Dcreatetime",getDcreatetime())
			.append("Dupdatetime",getDupdatetime())
			.append("Demail",getDemail())
			.append("Dtel",getDtel())
			.append("Dstatus",getDstatus())
			.append("Derrortimes",getDerrortimes())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblOperator == false) return false;
		if(this == obj) return true;
		TblOperator other = (TblOperator)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

