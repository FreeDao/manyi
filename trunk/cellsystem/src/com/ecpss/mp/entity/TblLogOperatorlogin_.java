package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T14:34:44.437+0800")
@StaticMetamodel(TblLogOperatorlogin.class)
public class TblLogOperatorlogin_ {
	public static volatile SingularAttribute<TblLogOperatorlogin, Long> did;
	public static volatile SingularAttribute<TblLogOperatorlogin, String> dloginname;
	public static volatile SingularAttribute<TblLogOperatorlogin, String> doperatorname;
	public static volatile SingularAttribute<TblLogOperatorlogin, Long> doperatorid;
	public static volatile SingularAttribute<TblLogOperatorlogin, Integer> dtype;
	public static volatile SingularAttribute<TblLogOperatorlogin, Integer> dresult;
	public static volatile SingularAttribute<TblLogOperatorlogin, String> ddesc;
	public static volatile SingularAttribute<TblLogOperatorlogin, String> dloginip;
	public static volatile SingularAttribute<TblLogOperatorlogin, Date> dtime;
	public static volatile SingularAttribute<TblLogOperatorlogin, Long> dcompanycode;
}
