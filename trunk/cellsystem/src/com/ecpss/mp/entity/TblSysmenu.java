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
@Table(name = "tbl_sysmenu")
public class TblSysmenu implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "TblSysmenu";
	public static final String ALIAS_DID = "did";
	public static final String ALIAS_DMENUNAME = "dmenuname";
	public static final String ALIAS_DMENUTIPS = "dmenutips";
	public static final String ALIAS_DNAVIGATEURL = "dnavigateurl";
	public static final String ALIAS_DNAVIGATETARGET = "dnavigatetarget";
	public static final String ALIAS_DLOOKID = "dlookid";
	public static final String ALIAS_DSELECTEDLOOKID = "dselectedlookid";
	public static final String ALIAS_DEXPANDED = "dexpanded";
	public static final String ALIAS_DIMAGE = "dimage";
	public static final String ALIAS_DHOVERIMG = "dhoverimg";
	public static final String ALIAS_DEXPANDEDIMG = "dexpandedimg";
	public static final String ALIAS_DACTIVEIMG = "dactiveimg";
	public static final String ALIAS_DSELIMG = "dselimg";
	public static final String ALIAS_DSELHOVERIMG = "dselhoverimg";
	public static final String ALIAS_DSELEXPANDEDIMG = "dselexpandedimg";
	public static final String ALIAS_DSELACTIVEIMG = "dselactiveimg";
	public static final String ALIAS_DPARENTID = "dparentid";
	public static final String ALIAS_DSEQUENCE = "dsequence";
	public static final String ALIAS_DSTATUS = "dstatus";
	public static final String ALIAS_DCATALOGTYPE = "dcatalogtype";
	public static final String ALIAS_DSUBMENUURL = "dsubmenuurl";
	public static final String ALIAS_DOPERATE_PERMISSIONS = "doperatePermissions";
	public static final String ALIAS_DPERMISSIONOBJECT = "dpermissionobject";
	public static final String ALIAS_DCREATETIME = "dcreatetime";
	public static final String ALIAS_DUPDATETIME = "dupdatetime";
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * did       db_column: D_ID 
     */ 	
	
	private java.lang.Long did;
    /**
     * dmenuname       db_column: D_MENUNAME 
     */ 	
	@Length(max=64)
	private java.lang.String dmenuname;
    /**
     * dmenutips       db_column: D_MENUTIPS 
     */ 	
	@Length(max=64)
	private java.lang.String dmenutips;
    /**
     * dnavigateurl       db_column: D_NAVIGATEURL 
     */ 	
	@Length(max=64)
	private java.lang.String dnavigateurl;
    /**
     * dnavigatetarget       db_column: D_NAVIGATETARGET 
     */ 	
	@Length(max=64)
	private java.lang.String dnavigatetarget;
    /**
     * dlookid       db_column: D_LOOKID 
     */ 	
	@Length(max=64)
	private java.lang.String dlookid;
    /**
     * dselectedlookid       db_column: D_SELECTEDLOOKID 
     */ 	
	@Length(max=64)
	private java.lang.String dselectedlookid;
    /**
     * dexpanded       db_column: D_EXPANDED 
     */ 	
	
	private java.lang.Long dexpanded;
    /**
     * dimage       db_column: D_IMAGE 
     */ 	
	@Length(max=64)
	private java.lang.String dimage;
    /**
     * dhoverimg       db_column: D_HOVERIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dhoverimg;
    /**
     * dexpandedimg       db_column: D_EXPANDEDIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dexpandedimg;
    /**
     * dactiveimg       db_column: D_ACTIVEIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dactiveimg;
    /**
     * dselimg       db_column: D_SELIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dselimg;
    /**
     * dselhoverimg       db_column: D_SELHOVERIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dselhoverimg;
    /**
     * dselexpandedimg       db_column: D_SELEXPANDEDIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dselexpandedimg;
    /**
     * dselactiveimg       db_column: D_SELACTIVEIMG 
     */ 	
	@Length(max=32)
	private java.lang.String dselactiveimg;
    /**
     * dparentid       db_column: D_PARENTID 
     */ 	
	
	private java.lang.Long dparentid;
    /**
     * dsequence       db_column: D_SEQUENCE 
     */ 	
	
	private java.lang.Integer dsequence;
    /**
     * dstatus       db_column: D_STATUS 
     */ 	
	
	private java.lang.Integer dstatus;
    /**
     * dcatalogtype       db_column: D_CATALOGTYPE 
     */ 	
	@Length(max=32)
	private java.lang.String dcatalogtype;
    /**
     * dsubmenuurl       db_column: D_SUBMENUURL 
     */ 	
	@Length(max=32)
	private java.lang.String dsubmenuurl;
    /**
     * doperatePermissions       db_column: D_OPERATE_PERMISSIONS 
     */ 	
	
	private java.lang.Integer doperatePermissions;
    /**
     * dpermissionobject       db_column: D_PERMISSIONOBJECT 
     */ 	
	@Length(max=16)
	private java.lang.String dpermissionobject;
    /**
     * dcreatetime       db_column: D_CREATETIME 
     */ 	
	
	private java.util.Date dcreatetime;
    /**
     * dupdatetime       db_column: D_UPDATETIME 
     */ 	
	
	private java.util.Date dupdatetime;
	//columns END


	public TblSysmenu(){
	}

	public TblSysmenu(
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
	
	@Column(name = "D_MENUNAME", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDmenuname() {
		return this.dmenuname;
	}
	
	public void setDmenuname(java.lang.String value) {
		this.dmenuname = value;
	}
	
	@Column(name = "D_MENUTIPS", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDmenutips() {
		return this.dmenutips;
	}
	
	public void setDmenutips(java.lang.String value) {
		this.dmenutips = value;
	}
	
	@Column(name = "D_NAVIGATEURL", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDnavigateurl() {
		return this.dnavigateurl;
	}
	
	public void setDnavigateurl(java.lang.String value) {
		this.dnavigateurl = value;
	}
	
	@Column(name = "D_NAVIGATETARGET", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDnavigatetarget() {
		return this.dnavigatetarget;
	}
	
	public void setDnavigatetarget(java.lang.String value) {
		this.dnavigatetarget = value;
	}
	
	@Column(name = "D_LOOKID", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDlookid() {
		return this.dlookid;
	}
	
	public void setDlookid(java.lang.String value) {
		this.dlookid = value;
	}
	
	@Column(name = "D_SELECTEDLOOKID", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDselectedlookid() {
		return this.dselectedlookid;
	}
	
	public void setDselectedlookid(java.lang.String value) {
		this.dselectedlookid = value;
	}
	
	@Column(name = "D_EXPANDED", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDexpanded() {
		return this.dexpanded;
	}
	
	public void setDexpanded(java.lang.Long value) {
		this.dexpanded = value;
	}
	
	@Column(name = "D_IMAGE", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getDimage() {
		return this.dimage;
	}
	
	public void setDimage(java.lang.String value) {
		this.dimage = value;
	}
	
	@Column(name = "D_HOVERIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDhoverimg() {
		return this.dhoverimg;
	}
	
	public void setDhoverimg(java.lang.String value) {
		this.dhoverimg = value;
	}
	
	@Column(name = "D_EXPANDEDIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDexpandedimg() {
		return this.dexpandedimg;
	}
	
	public void setDexpandedimg(java.lang.String value) {
		this.dexpandedimg = value;
	}
	
	@Column(name = "D_ACTIVEIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDactiveimg() {
		return this.dactiveimg;
	}
	
	public void setDactiveimg(java.lang.String value) {
		this.dactiveimg = value;
	}
	
	@Column(name = "D_SELIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDselimg() {
		return this.dselimg;
	}
	
	public void setDselimg(java.lang.String value) {
		this.dselimg = value;
	}
	
	@Column(name = "D_SELHOVERIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDselhoverimg() {
		return this.dselhoverimg;
	}
	
	public void setDselhoverimg(java.lang.String value) {
		this.dselhoverimg = value;
	}
	
	@Column(name = "D_SELEXPANDEDIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDselexpandedimg() {
		return this.dselexpandedimg;
	}
	
	public void setDselexpandedimg(java.lang.String value) {
		this.dselexpandedimg = value;
	}
	
	@Column(name = "D_SELACTIVEIMG", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDselactiveimg() {
		return this.dselactiveimg;
	}
	
	public void setDselactiveimg(java.lang.String value) {
		this.dselactiveimg = value;
	}
	
	@Column(name = "D_PARENTID", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.lang.Long getDparentid() {
		return this.dparentid;
	}
	
	public void setDparentid(java.lang.Long value) {
		this.dparentid = value;
	}
	
	@Column(name = "D_SEQUENCE", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDsequence() {
		return this.dsequence;
	}
	
	public void setDsequence(java.lang.Integer value) {
		this.dsequence = value;
	}
	
	@Column(name = "D_STATUS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDstatus() {
		return this.dstatus;
	}
	
	public void setDstatus(java.lang.Integer value) {
		this.dstatus = value;
	}
	
	@Column(name = "D_CATALOGTYPE", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDcatalogtype() {
		return this.dcatalogtype;
	}
	
	public void setDcatalogtype(java.lang.String value) {
		this.dcatalogtype = value;
	}
	
	@Column(name = "D_SUBMENUURL", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDsubmenuurl() {
		return this.dsubmenuurl;
	}
	
	public void setDsubmenuurl(java.lang.String value) {
		this.dsubmenuurl = value;
	}
	
	@Column(name = "D_OPERATE_PERMISSIONS", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getDoperatePermissions() {
		return this.doperatePermissions;
	}
	
	public void setDoperatePermissions(java.lang.Integer value) {
		this.doperatePermissions = value;
	}
	
	@Column(name = "D_PERMISSIONOBJECT", unique = false, nullable = true, insertable = true, updatable = true, length = 16)
	public java.lang.String getDpermissionobject() {
		return this.dpermissionobject;
	}
	
	public void setDpermissionobject(java.lang.String value) {
		this.dpermissionobject = value;
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
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Did",getDid())
			.append("Dmenuname",getDmenuname())
			.append("Dmenutips",getDmenutips())
			.append("Dnavigateurl",getDnavigateurl())
			.append("Dnavigatetarget",getDnavigatetarget())
			.append("Dlookid",getDlookid())
			.append("Dselectedlookid",getDselectedlookid())
			.append("Dexpanded",getDexpanded())
			.append("Dimage",getDimage())
			.append("Dhoverimg",getDhoverimg())
			.append("Dexpandedimg",getDexpandedimg())
			.append("Dactiveimg",getDactiveimg())
			.append("Dselimg",getDselimg())
			.append("Dselhoverimg",getDselhoverimg())
			.append("Dselexpandedimg",getDselexpandedimg())
			.append("Dselactiveimg",getDselactiveimg())
			.append("Dparentid",getDparentid())
			.append("Dsequence",getDsequence())
			.append("Dstatus",getDstatus())
			.append("Dcatalogtype",getDcatalogtype())
			.append("Dsubmenuurl",getDsubmenuurl())
			.append("DoperatePermissions",getDoperatePermissions())
			.append("Dpermissionobject",getDpermissionobject())
			.append("Dcreatetime",getDcreatetime())
			.append("Dupdatetime",getDupdatetime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getDid())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof TblSysmenu == false) return false;
		if(this == obj) return true;
		TblSysmenu other = (TblSysmenu)obj;
		return new EqualsBuilder()
			.append(getDid(),other.getDid())
			.isEquals();
	}
}

