package com.ecpss.mp.entity;

/*
* Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "tbl_log_operate")
public class TblLogOperate  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblLogOperate";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DLOGINNAME = "dloginname";
	public static final String ALIAS_DOPERATORNAME = "doperatorname";
	public static final String ALIAS_DOPERATORID = "doperatorid";
	public static final String ALIAS_DOPERATETYPE = "doperatetype";
	public static final String ALIAS_DOPERATERESULT = "doperateresult";
	public static final String ALIAS_DOPERATEOBJECT = "doperateobject";
	public static final String ALIAS_DOPERATEVALUE = "doperatevalue";
	public static final String ALIAS_DOPERATETIME = "doperatetime";
	public static final String ALIAS_DCOMPANYCODE = "dcompanycode";
	
	//date formats

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
     * doperatetype       db_column: D_OPERATETYPE 
     */ 	
	@NotNull 
	private java.lang.Integer doperatetype;
    /**
     * doperateresult       db_column: D_OPERATERESULT 
     */ 	
	@NotNull 
	private java.lang.Integer doperateresult;
    /**
     * doperateobject       db_column: D_OPERATEOBJECT 
     */ 	
	@NotBlank @Length(max=16)
	private java.lang.String doperateobject;
    /**
     * doperatevalue       db_column: D_OPERATEVALUE 
     */ 	
	@NotBlank @Length(max=1024)
	private java.lang.String doperatevalue;
    /**
     * doperatetime       db_column: D_OPERATETIME 
     */ 	
	@NotNull 
	private java.util.Date doperatetime;
    /**
     * dcompanycode       db_column: D_COMPANYCODE 
     */ 	
	@NotNull 
	private java.lang.Long dcompanycode;
	//columns END


	public TblLogOperate(){
	}

	public TblLogOperate(
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
	
	@Column(name = "D_OPERATETYPE", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDoperatetype() {
		return this.doperatetype;
	}
	
	public void setDoperatetype(java.lang.Integer value) {
		this.doperatetype = value;
	}
	
	@Column(name = "D_OPERATERESULT", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDoperateresult() {
		return this.doperateresult;
	}
	
	public void setDoperateresult(java.lang.Integer value) {
		this.doperateresult = value;
	}
	
	@Column(name = "D_OPERATEOBJECT", unique = false, nullable = false, insertable = true, updatable = true, length = 16)
	public java.lang.String getDoperateobject() {
		return this.doperateobject;
	}
	
	public void setDoperateobject(java.lang.String value) {
		this.doperateobject = value;
	}
	
	@Column(name = "D_OPERATEVALUE", unique = false, nullable = false, insertable = true, updatable = true, length = 1024)
	public java.lang.String getDoperatevalue() {
		return this.doperatevalue;
	}
	
	public void setDoperatevalue(java.lang.String value) {
		this.doperatevalue = value;
	}
	
	@Column(name = "D_OPERATETIME", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public java.util.Date getDoperatetime() {
		return this.doperatetime;
	}
	
	public void setDoperatetime(java.util.Date value) {
		this.doperatetime = value;
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
			.append("Doperatetype",getDoperatetype())
			.append("Doperateresult",getDoperateresult())
			.append("Doperateobject",getDoperateobject())
			.append("Doperatevalue",getDoperatevalue())
			.append("Doperatetime",getDoperatetime())
			.append("Dcompanycode",getDcompanycode())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblLogOperate == false) return false;
		if(this == obj) return true;
		TblLogOperate other = (TblLogOperate)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

