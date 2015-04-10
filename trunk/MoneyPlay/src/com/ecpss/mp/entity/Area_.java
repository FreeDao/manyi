package com.ecpss.mp.entity;

import java.util.Calendar;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-23T17:44:14.755+0800")
@StaticMetamodel(Area.class)
public class Area_ {
	public static volatile SingularAttribute<Area, Integer> areaId;
	public static volatile SingularAttribute<Area, String> name;
	public static volatile SingularAttribute<Area, Calendar> createTime;
	public static volatile SingularAttribute<Area, Area> parent;
	public static volatile SingularAttribute<Area, String> path;
	public static volatile SingularAttribute<Area, String> remark;
}
