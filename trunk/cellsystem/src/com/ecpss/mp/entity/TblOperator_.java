package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T15:06:26.070+0800")
@StaticMetamodel(TblOperator.class)
public class TblOperator_ {
	public static volatile SingularAttribute<TblOperator, Long> did;
	public static volatile SingularAttribute<TblOperator, String> dloginname;
	public static volatile SingularAttribute<TblOperator, String> dpassword;
	public static volatile SingularAttribute<TblOperator, Integer> dgender;
	public static volatile SingularAttribute<TblOperator, String> drealname;
	public static volatile SingularAttribute<TblOperator, Integer> displatoperator;
	public static volatile SingularAttribute<TblOperator, Integer> doperatortype;
	public static volatile SingularAttribute<TblOperator, Long> dcompanyid;
	public static volatile SingularAttribute<TblOperator, String> dloginip;
	public static volatile SingularAttribute<TblOperator, Date> dlogintime;
	public static volatile SingularAttribute<TblOperator, Date> dcreatetime;
	public static volatile SingularAttribute<TblOperator, Date> dupdatetime;
	public static volatile SingularAttribute<TblOperator, String> demail;
	public static volatile SingularAttribute<TblOperator, String> dtel;
	public static volatile SingularAttribute<TblOperator, Integer> dstatus;
	public static volatile SingularAttribute<TblOperator, Integer> derrortimes;
}
