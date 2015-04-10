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

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */


@Entity
@Table(name = "tbl_operator_role")
public class TblOperatorRole implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblOperatorRole";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DOPERATORID = "doperatorid";
	public static final String ALIAS_DROLEID = "droleid";
	
	//date formats
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * did       db_column: D_ID 
     */ 	
	
	private java.lang.Long did;
    /**
     * doperatorid       db_column: D_OPERATORID 
     */ 	
	
	private java.lang.Long doperatorid;
    /**
     * droleid       db_column: D_ROLEID 
     */ 	
	
	private java.lang.Long droleid;
	//columns END


	public TblOperatorRole(){
	}

	public TblOperatorRole(
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
	
	@Column(name = "D_OPERATORID", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDoperatorid() {
		return this.doperatorid;
	}
	
	public void setDoperatorid(java.lang.Long value) {
		this.doperatorid = value;
	}
	
	@Column(name = "D_ROLEID", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDroleid() {
		return this.droleid;
	}
	
	public void setDroleid(java.lang.Long value) {
		this.droleid = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Did",getDid())
			.append("Doperatorid",getDoperatorid())
			.append("Droleid",getDroleid())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblOperatorRole == false) return false;
		if(this == obj) return true;
		TblOperatorRole other = (TblOperatorRole)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

