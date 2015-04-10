package com.ecpss.mp.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-21T14:34:44.441+0800")
@StaticMetamodel(TblPermissiongroup.class)
public class TblPermissiongroup_ {
	public static volatile SingularAttribute<TblPermissiongroup, Long> did;
	public static volatile SingularAttribute<TblPermissiongroup, String> dname;
	public static volatile SingularAttribute<TblPermissiongroup, String> ddesc;
	public static volatile SingularAttribute<TblPermissiongroup, String> dpermissions;
	public static volatile SingularAttribute<TblPermissiongroup, Integer> dstatus;
	public static volatile SingularAttribute<TblPermissiongroup, Date> dcreatetime;
	public static volatile SingularAttribute<TblPermissiongroup, Date> dupdatetime;
	public static volatile SingularAttribute<TblPermissiongroup, Integer> dinitvalue;
}
