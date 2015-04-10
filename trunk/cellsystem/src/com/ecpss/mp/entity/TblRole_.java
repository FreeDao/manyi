package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T14:34:44.442+0800")
@StaticMetamodel(TblRole.class)
public class TblRole_ {
	public static volatile SingularAttribute<TblRole, Long> did;
	public static volatile SingularAttribute<TblRole, String> dname;
	public static volatile SingularAttribute<TblRole, String> ddesc;
	public static volatile SingularAttribute<TblRole, Integer> dstatus;
	public static volatile SingularAttribute<TblRole, Date> dcreatetime;
	public static volatile SingularAttribute<TblRole, Date> dupdatetime;
	public static volatile SingularAttribute<TblRole, Integer> dtype;
	public static volatile SingularAttribute<TblRole, Integer> dinitvalue;
}
