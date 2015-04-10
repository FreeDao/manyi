package com.manyi.ihouse.entity.hims;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-12T10:25:59.694+0800")
@StaticMetamodel(Area.class)
public class Area_ {
	public static volatile SingularAttribute<Area, Integer> areaId;
	public static volatile SingularAttribute<Area, String> name;
	public static volatile SingularAttribute<Area, Date> createTime;
	public static volatile SingularAttribute<Area, String> remark;
	public static volatile SingularAttribute<Area, Integer> userId;
	public static volatile SingularAttribute<Area, String> path;
	public static volatile SingularAttribute<Area, Integer> parentId;
	public static volatile SingularAttribute<Area, Integer> status;
	public static volatile SingularAttribute<Area, String> serialCode;
}
