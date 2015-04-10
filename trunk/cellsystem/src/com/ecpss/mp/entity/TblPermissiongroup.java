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
@Table(name = "tbl_permissiongroup")
public class TblPermissiongroup implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblPermissiongroup";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DNAME = "dname";
	public static final String ALIAS_DDESC = "ddesc";
	public static final String ALIAS_DPERMISSIONS = "dpermissions";
	public static final String ALIAS_DSTATUS = "dstatus";
	public static final String ALIAS_DCREATETIME = "dcreatetime";
	public static final String ALIAS_DUPDATETIME = "dupdatetime";
	public static final String ALIAS_DINITVALUE = "dinitvalue";
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * did       db_column: D_ID 
     */ 	
	
	private java.lang.Long did;
    /**
     * dname       db_column: D_NAME 
     */ 	
	@Length(max=32)
	private java.lang.String dname;
    /**
     * ddesc       db_column: D_DESC 
     */ 	
	@Length(max=1024)
	private java.lang.String ddesc;
    /**
     * dpermissions       db_column: D_PERMISSIONS 
     */ 	
	@Length(max=256)
	private java.lang.String dpermissions;
    /**
     * dstatus       db_column: D_STATUS 
     */ 	
	
	private java.lang.Integer dstatus;
    /**
     * dcreatetime       db_column: D_CREATETIME 
     */ 	
	
	private java.util.Date dcreatetime;
    /**
     * dupdatetime       db_column: D_UPDATETIME 
     */ 	
	
	private java.util.Date dupdatetime;
    /**
     * dinitvalue       db_column: D_INITVALUE 
     */ 	
	
	private java.lang.Integer dinitvalue;
	//columns END


	public TblPermissiongroup(){
	}

	public TblPermissiongroup(
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
	
	@Column(name = "D_NAME", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDname() {
		return this.dname;
	}
	
	public void setDname(java.lang.String value) {
		this.dname = value;
	}
	
	@Column(name = "D_DESC", unique = false, nullable = true, insertable = true, updatable = true, length = 1024)
	public java.lang.String getDdesc() {
		return this.ddesc;
	}
	
	public void setDdesc(java.lang.String value) {
		this.ddesc = value;
	}
	
	@Column(name = "D_PERMISSIONS", unique = false, nullable = true, insertable = true, updatable = true, length = 256)
	public java.lang.String getDpermissions() {
		return this.dpermissions;
	}
	
	public void setDpermissions(java.lang.String value) {
		this.dpermissions = value;
	}
	
	@Column(name = "D_STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDstatus() {
		return this.dstatus;
	}
	
	public void setDstatus(java.lang.Integer value) {
		this.dstatus = value;
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
	
	@Column(name = "D_INITVALUE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDinitvalue() {
		return this.dinitvalue;
	}
	
	public void setDinitvalue(java.lang.Integer value) {
		this.dinitvalue = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Did",getDid())
			.append("Dname",getDname())
			.append("Ddesc",getDdesc())
			.append("Dpermissions",getDpermissions())
			.append("Dstatus",getDstatus())
			.append("Dcreatetime",getDcreatetime())
			.append("Dupdatetime",getDupdatetime())
			.append("Dinitvalue",getDinitvalue())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblPermissiongroup == false) return false;
		if(this == obj) return true;
		TblPermissiongroup other = (TblPermissiongroup)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

