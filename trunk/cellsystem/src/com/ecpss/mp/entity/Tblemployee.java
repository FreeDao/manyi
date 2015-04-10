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
import javax.validation.constraints.Max;

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
@Table(name = "tblemployee")
public class Tblemployee  implements java.io.Serializable{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "Tblemployee";
	public static final String ALIAS_EMP_ID = "empId";
	public static final String ALIAS_EMP_NO = "empNo";
	public static final String ALIAS_EMP_NAME = "empName";
	public static final String ALIAS_BRIEF = "brief";
	public static final String ALIAS_DEPT_ID = "deptId";
	public static final String ALIAS_PASSWORD = "password";
	public static final String ALIAS_TEL = "tel";
	public static final String ALIAS_MOD_DATE = "modDate";
	public static final String ALIAS_FLAG_TRASHED = "flagTrashed";
	public static final String ALIAS_FLAG_DELETED = "flagDeleted";
	public static final String ALIAS_PROPERTY_COLS = "propertyCols";
	public static final String ALIAS_INQUIRY_COLS = "inquiryCols";
	public static final String ALIAS_STATUS = "status";
	public static final String ALIAS_ROLL_IN = "rollIn";
	public static final String ALIAS_ROLL_OUT = "rollOut";
	public static final String ALIAS_POSITION_ID = "positionId";
	public static final String ALIAS_EX_DATE = "exDate";
	public static final String ALIAS_SEX = "sex";
	public static final String ALIAS_BIRTHDAY = "birthday";
	public static final String ALIAS_IDCARD = "idcard";
	public static final String ALIAS_ADDRESS = "address";
	public static final String ALIAS_EDUCATION = "education";
	public static final String ALIAS_SPECIALITY = "speciality";
	public static final String ALIAS_EMAIL = "email";
	public static final String ALIAS_REMARK = "remark";
	public static final String ALIAS_CONTRACT_COLS = "contractCols";
	public static final String ALIAS_PWD_DATE = "pwdDate";
	public static final String ALIAS_SIGNER = "signer";
	public static final String ALIAS_ACL3 = "acl3";
	public static final String ALIAS_ACL1 = "acl1";
	public static final String ALIAS_ACL2 = "acl2";
	public static final String ALIAS_ACL6 = "acl6";
	public static final String ALIAS_JOIN_DATE = "joinDate";
	public static final String ALIAS_FOLK = "folk";
	public static final String ALIAS_NATIVE = "native";
	public static final String ALIAS_POLITY = "polity";
	public static final String ALIAS_GRADUATE = "graduate";
	public static final String ALIAS_TECH_TITLE = "techTitle";
	public static final String ALIAS_ARCHIVES = "archives";
	public static final String ALIAS_ACL4 = "acl4";
	public static final String ALIAS_IDIO = "idio";
	public static final String ALIAS_AWAY_DATE = "awayDate";
	public static final String ALIAS_WEB_UID = "webUid";
	public static final String ALIAS_WEB_PWD = "webPwd";
	public static final String ALIAS_FLAG_SALARY = "flagSalary";
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
    /**
     * empId       db_column: EmpID 
     */ 	
	@Length(max=32)
	private java.lang.String empId;
    /**
     * empNo       db_column: EmpNo 
     */ 	
	@Length(max=10)
	private java.lang.String empNo;
    /**
     * empName       db_column: EmpName 
     */ 	
	@Length(max=10)
	private java.lang.String empName;
    /**
     * brief       db_column: Brief 
     */ 	
	@Length(max=2147483647)
	private java.lang.String brief;
    /**
     * deptId       db_column: DeptID 
     */ 	
	@Length(max=32)
	private java.lang.String deptId;
    /**
     * password       db_column: Password 
     */ 	
	@Length(max=100)
	private java.lang.String password;
    /**
     * tel       db_column: Tel 
     */ 	
	@Length(max=100)
	private java.lang.String tel;
    /**
     * modDate       db_column: ModDate 
     */ 	
	
	private java.util.Date modDate;
    /**
     * flagTrashed       db_column: FlagTrashed 
     */ 	
	@Max(127)
	private Integer flagTrashed;
    /**
     * flagDeleted       db_column: FlagDeleted 
     */ 	
	@Max(127)
	private Integer flagDeleted;
    /**
     * propertyCols       db_column: PropertyCols 
     */ 	
	@Length(max=200)
	private java.lang.String propertyCols;
    /**
     * inquiryCols       db_column: InquiryCols 
     */ 	
	@Length(max=200)
	private java.lang.String inquiryCols;
    /**
     * status       db_column: Status 
     */ 	
	@Length(max=10)
	private java.lang.String status;
    /**
     * rollIn       db_column: RollIn 
     */ 	
	
	private java.util.Date rollIn;
    /**
     * rollOut       db_column: RollOut 
     */ 	
	
	private java.util.Date rollOut;
    /**
     * positionId       db_column: PositionID 
     */ 	
	@Length(max=32)
	private java.lang.String positionId;
    /**
     * exDate       db_column: ExDate 
     */ 	
	
	private java.util.Date exDate;
    /**
     * sex       db_column: Sex 
     */ 	
	@Length(max=2)
	private java.lang.String sex;
    /**
     * birthday       db_column: Birthday 
     */ 	
	
	private java.util.Date birthday;
    /**
     * idcard       db_column: IDCard 
     */ 	
	@Length(max=20)
	private java.lang.String idcard;
    /**
     * address       db_column: Address 
     */ 	
	@Length(max=50)
	private java.lang.String address;
    /**
     * education       db_column: Education 
     */ 	
	@Length(max=10)
	private java.lang.String education;
    /**
     * speciality       db_column: Speciality 
     */ 	
	@Length(max=20)
	private java.lang.String speciality;
    /**
     * email       db_column: EMail 
     */ 	
	@Length(max=100)
	private java.lang.String email;
    /**
     * remark       db_column: Remark 
     */ 	
	@Length(max=2147483647)
	private java.lang.String remark;
    /**
     * contractCols       db_column: ContractCols 
     */ 	
	@Length(max=200)
	private java.lang.String contractCols;
    /**
     * pwdDate       db_column: PwdDate 
     */ 	
	
	private java.util.Date pwdDate;
    /**
     * signer       db_column: Signer 
     */ 	
	@Length(max=32)
	private java.lang.String signer;
    /**
     * acl3       db_column: ACL3 
     */ 	
	@Length(max=10)
	private java.lang.String acl3;
    /**
     * acl1       db_column: ACL1 
     */ 	
	@Length(max=65535)
	private java.lang.String acl1;
    /**
     * acl2       db_column: ACL2 
     */ 	
	@Length(max=100)
	private java.lang.String acl2;
    /**
     * acl6       db_column: ACL6 
     */ 	
	@Length(max=100)
	private java.lang.String acl6;
    /**
     * joinDate       db_column: JoinDate 
     */ 	
	
	private java.util.Date joinDate;
    /**
     * folk       db_column: Folk 
     */ 	
	@Length(max=10)
	private java.lang.String folk;
    /**
     * native       db_column: Native 
     */ 	
	private java.lang.String natives;
    /**
     * polity       db_column: Polity 
     */ 	
	private java.lang.String polity;
    /**
     * graduate       db_column: Graduate 
     */ 	
	@Length(max=40)
	private java.lang.String graduate;
    /**
     * techTitle       db_column: TechTitle 
     */ 	
	@Length(max=30)
	private java.lang.String techTitle;
    /**
     * archives       db_column: Archives 
     */ 	
	@Length(max=30)
	private java.lang.String archives;
    /**
     * acl4       db_column: ACL4 
     */ 	
	@Length(max=10)
	private java.lang.String acl4;
    /**
     * idio       db_column: Idio 
     */ 	
	@Length(max=50)
	private java.lang.String idio;
    /**
     * awayDate       db_column: AwayDate 
     */ 	
	
	private java.util.Date awayDate;
    /**
     * webUid       db_column: WebUID 
     */ 	
	@Length(max=30)
	private java.lang.String webUid;
    /**
     * webPwd       db_column: WebPWD 
     */ 	
	@Length(max=60)
	private java.lang.String webPwd;
    /**
     * flagSalary       db_column: FlagSalary 
     */ 	
	@Max(127)
	private Integer flagSalary;
	//columns END


	public Tblemployee(){
	}

	public Tblemployee(
		java.lang.String empId
	){
		this.empId = empId;
	}

	

	public void setEmpId(java.lang.String value) {
		this.empId = value;
	}
	
	@Id @GeneratedValue()
	@Column(name = "EmpID", unique = true, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getEmpId() {
		return this.empId;
	}
	
	@Column(name = "EmpNo", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getEmpNo() {
		return this.empNo;
	}
	
	public void setEmpNo(java.lang.String value) {
		this.empNo = value;
	}
	
	@Column(name = "EmpName", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getEmpName() {
		return this.empName;
	}
	
	public void setEmpName(java.lang.String value) {
		this.empName = value;
	}
	
	@Column(name = "Brief", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getBrief() {
		return this.brief;
	}
	
	public void setBrief(java.lang.String value) {
		this.brief = value;
	}
	
	@Column(name = "DeptID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(java.lang.String value) {
		this.deptId = value;
	}
	
	@Column(name = "Password", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getPassword() {
		return this.password;
	}
	
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	@Column(name = "Tel", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getTel() {
		return this.tel;
	}
	
	public void setTel(java.lang.String value) {
		this.tel = value;
	}
	
	@Column(name = "ModDate", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getModDate() {
		return this.modDate;
	}
	
	public void setModDate(java.util.Date value) {
		this.modDate = value;
	}
	
	@Column(name = "FlagTrashed", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getFlagTrashed() {
		return this.flagTrashed;
	}
	
	public void setFlagTrashed(Integer value) {
		this.flagTrashed = value;
	}
	
	@Column(name = "FlagDeleted", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getFlagDeleted() {
		return this.flagDeleted;
	}
	
	public void setFlagDeleted(Integer value) {
		this.flagDeleted = value;
	}
	
	@Column(name = "PropertyCols", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getPropertyCols() {
		return this.propertyCols;
	}
	
	public void setPropertyCols(java.lang.String value) {
		this.propertyCols = value;
	}
	
	@Column(name = "InquiryCols", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getInquiryCols() {
		return this.inquiryCols;
	}
	
	public void setInquiryCols(java.lang.String value) {
		this.inquiryCols = value;
	}
	
	@Column(name = "Status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String value) {
		this.status = value;
	}
	
	@Column(name = "RollIn", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getRollIn() {
		return this.rollIn;
	}
	
	public void setRollIn(java.util.Date value) {
		this.rollIn = value;
	}
	
	
	@Column(name = "RollOut", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getRollOut() {
		return this.rollOut;
	}
	
	public void setRollOut(java.util.Date value) {
		this.rollOut = value;
	}
	
	@Column(name = "PositionID", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getPositionId() {
		return this.positionId;
	}
	
	public void setPositionId(java.lang.String value) {
		this.positionId = value;
	}
	
	@Column(name = "ExDate", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getExDate() {
		return this.exDate;
	}
	
	public void setExDate(java.util.Date value) {
		this.exDate = value;
	}
	
	@Column(name = "Sex", unique = false, nullable = true, insertable = true, updatable = true, length = 2)
	public java.lang.String getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.String value) {
		this.sex = value;
	}
	
	@Column(name = "Birthday", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(java.util.Date value) {
		this.birthday = value;
	}
	
	@Column(name = "IDCard", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getIdcard() {
		return this.idcard;
	}
	
	public void setIdcard(java.lang.String value) {
		this.idcard = value;
	}
	
	@Column(name = "Address", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getAddress() {
		return this.address;
	}
	
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	
	@Column(name = "Education", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getEducation() {
		return this.education;
	}
	
	public void setEducation(java.lang.String value) {
		this.education = value;
	}
	
	@Column(name = "Speciality", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
	public java.lang.String getSpeciality() {
		return this.speciality;
	}
	
	public void setSpeciality(java.lang.String value) {
		this.speciality = value;
	}
	
	@Column(name = "EMail", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getEmail() {
		return this.email;
	}
	
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	
	@Column(name = "Remark", unique = false, nullable = true, insertable = true, updatable = true, length = 2147483647)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	@Column(name = "ContractCols", unique = false, nullable = true, insertable = true, updatable = true, length = 200)
	public java.lang.String getContractCols() {
		return this.contractCols;
	}
	
	public void setContractCols(java.lang.String value) {
		this.contractCols = value;
	}
	
	@Column(name = "PwdDate", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getPwdDate() {
		return this.pwdDate;
	}
	
	public void setPwdDate(java.util.Date value) {
		this.pwdDate = value;
	}
	
	@Column(name = "Signer", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getSigner() {
		return this.signer;
	}
	
	public void setSigner(java.lang.String value) {
		this.signer = value;
	}
	
	@Column(name = "ACL3", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAcl3() {
		return this.acl3;
	}
	
	public void setAcl3(java.lang.String value) {
		this.acl3 = value;
	}
	
	@Column(name = "ACL1", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public java.lang.String getAcl1() {
		return this.acl1;
	}
	
	public void setAcl1(java.lang.String value) {
		this.acl1 = value;
	}
	
	@Column(name = "ACL2", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getAcl2() {
		return this.acl2;
	}
	
	public void setAcl2(java.lang.String value) {
		this.acl2 = value;
	}
	
	@Column(name = "ACL6", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getAcl6() {
		return this.acl6;
	}
	
	public void setAcl6(java.lang.String value) {
		this.acl6 = value;
	}
	
	@Column(name = "JoinDate", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getJoinDate() {
		return this.joinDate;
	}
	
	public void setJoinDate(java.util.Date value) {
		this.joinDate = value;
	}
	
	@Column(name = "Folk", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getFolk() {
		return this.folk;
	}
	
	public void setFolk(java.lang.String value) {
		this.folk = value;
	}
	
	@Column(name = "Natives", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getNatives() {
		return this.natives;
	}
	
	public void setNatives(java.lang.String value) {
		this.natives = value;
	}
	
	@Column(name = "Polity", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getPolity() {
		return this.polity;
	}
	
	public void setPolity(java.lang.String value) {
		this.polity = value;
	}
	
	@Column(name = "Graduate", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
	public java.lang.String getGraduate() {
		return this.graduate;
	}
	
	public void setGraduate(java.lang.String value) {
		this.graduate = value;
	}
	
	@Column(name = "TechTitle", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getTechTitle() {
		return this.techTitle;
	}
	
	public void setTechTitle(java.lang.String value) {
		this.techTitle = value;
	}
	
	@Column(name = "Archives", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getArchives() {
		return this.archives;
	}
	
	public void setArchives(java.lang.String value) {
		this.archives = value;
	}
	
	@Column(name = "ACL4", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getAcl4() {
		return this.acl4;
	}
	
	public void setAcl4(java.lang.String value) {
		this.acl4 = value;
	}
	
	@Column(name = "Idio", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
	public java.lang.String getIdio() {
		return this.idio;
	}
	
	public void setIdio(java.lang.String value) {
		this.idio = value;
	}
	
	
	@Column(name = "AwayDate", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.util.Date getAwayDate() {
		return this.awayDate;
	}
	
	public void setAwayDate(java.util.Date value) {
		this.awayDate = value;
	}
	
	@Column(name = "WebUID", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
	public java.lang.String getWebUid() {
		return this.webUid;
	}
	
	public void setWebUid(java.lang.String value) {
		this.webUid = value;
	}
	
	@Column(name = "WebPWD", unique = false, nullable = true, insertable = true, updatable = true, length = 60)
	public java.lang.String getWebPwd() {
		return this.webPwd;
	}
	
	public void setWebPwd(java.lang.String value) {
		this.webPwd = value;
	}
	
	@Column(name = "FlagSalary", unique = false, nullable = true, insertable = true, updatable = true, length = 3)
	public Integer getFlagSalary() {
		return this.flagSalary;
	}
	
	public void setFlagSalary(Integer value) {
		this.flagSalary = value;
	}
	

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("EmpId",getEmpId())
			.append("EmpNo",getEmpNo())
			.append("EmpName",getEmpName())
			.append("Brief",getBrief())
			.append("DeptId",getDeptId())
			.append("Password",getPassword())
			.append("Tel",getTel())
			.append("ModDate",getModDate())
			.append("FlagTrashed",getFlagTrashed())
			.append("FlagDeleted",getFlagDeleted())
			.append("PropertyCols",getPropertyCols())
			.append("InquiryCols",getInquiryCols())
			.append("Status",getStatus())
			.append("RollIn",getRollIn())
			.append("RollOut",getRollOut())
			.append("PositionId",getPositionId())
			.append("ExDate",getExDate())
			.append("Sex",getSex())
			.append("Birthday",getBirthday())
			.append("Idcard",getIdcard())
			.append("Address",getAddress())
			.append("Education",getEducation())
			.append("Speciality",getSpeciality())
			.append("Email",getEmail())
			.append("Remark",getRemark())
			.append("ContractCols",getContractCols())
			.append("PwdDate",getPwdDate())
			.append("Signer",getSigner())
			.append("Acl3",getAcl3())
			.append("Acl1",getAcl1())
			.append("Acl2",getAcl2())
			.append("Acl6",getAcl6())
			.append("JoinDate",getJoinDate())
			.append("Folk",getFolk())
			.append("Native",getNatives())
			.append("Polity",getPolity())
			.append("Graduate",getGraduate())
			.append("TechTitle",getTechTitle())
			.append("Archives",getArchives())
			.append("Acl4",getAcl4())
			.append("Idio",getIdio())
			.append("AwayDate",getAwayDate())
			.append("WebUid",getWebUid())
			.append("WebPwd",getWebPwd())
			.append("FlagSalary",getFlagSalary())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getEmpId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Tblemployee == false) return false;
		if(this == obj) return true;
		Tblemployee other = (Tblemployee)obj;
		return new EqualsBuilder()
			.append(getEmpId(),other.getEmpId())
			.isEquals();
	}
}

