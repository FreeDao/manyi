package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T14:34:44.436+0800")
@StaticMetamodel(TblLogOperate.class)
public class TblLogOperate_ {
	public static volatile SingularAttribute<TblLogOperate, Long> did;
	public static volatile SingularAttribute<TblLogOperate, String> dloginname;
	public static volatile SingularAttribute<TblLogOperate, String> doperatorname;
	public static volatile SingularAttribute<TblLogOperate, Long> doperatorid;
	public static volatile SingularAttribute<TblLogOperate, Integer> doperatetype;
	public static volatile SingularAttribute<TblLogOperate, Integer> doperateresult;
	public static volatile SingularAttribute<TblLogOperate, String> doperateobject;
	public static volatile SingularAttribute<TblLogOperate, String> doperatevalue;
	public static volatile SingularAttribute<TblLogOperate, Date> doperatetime;
	public static volatile SingularAttribute<TblLogOperate, Long> dcompanycode;
}
