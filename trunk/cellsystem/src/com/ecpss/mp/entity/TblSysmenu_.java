package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T14:34:44.444+0800")
@StaticMetamodel(TblSysmenu.class)
public class TblSysmenu_ {
	public static volatile SingularAttribute<TblSysmenu, Long> did;
	public static volatile SingularAttribute<TblSysmenu, String> dmenuname;
	public static volatile SingularAttribute<TblSysmenu, String> dmenutips;
	public static volatile SingularAttribute<TblSysmenu, String> dnavigateurl;
	public static volatile SingularAttribute<TblSysmenu, String> dnavigatetarget;
	public static volatile SingularAttribute<TblSysmenu, String> dlookid;
	public static volatile SingularAttribute<TblSysmenu, String> dselectedlookid;
	public static volatile SingularAttribute<TblSysmenu, Long> dexpanded;
	public static volatile SingularAttribute<TblSysmenu, String> dimage;
	public static volatile SingularAttribute<TblSysmenu, String> dhoverimg;
	public static volatile SingularAttribute<TblSysmenu, String> dexpandedimg;
	public static volatile SingularAttribute<TblSysmenu, String> dactiveimg;
	public static volatile SingularAttribute<TblSysmenu, String> dselimg;
	public static volatile SingularAttribute<TblSysmenu, String> dselhoverimg;
	public static volatile SingularAttribute<TblSysmenu, String> dselexpandedimg;
	public static volatile SingularAttribute<TblSysmenu, String> dselactiveimg;
	public static volatile SingularAttribute<TblSysmenu, Long> dparentid;
	public static volatile SingularAttribute<TblSysmenu, Integer> dsequence;
	public static volatile SingularAttribute<TblSysmenu, Integer> dstatus;
	public static volatile SingularAttribute<TblSysmenu, String> dcatalogtype;
	public static volatile SingularAttribute<TblSysmenu, String> dsubmenuurl;
	public static volatile SingularAttribute<TblSysmenu, Integer> doperatePermissions;
	public static volatile SingularAttribute<TblSysmenu, String> dpermissionobject;
	public static volatile SingularAttribute<TblSysmenu, Date> dcreatetime;
	public static volatile SingularAttribute<TblSysmenu, Date> dupdatetime;
}
