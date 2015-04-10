package com.manyi.ihouse.entity.hims;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T11:31:55.264+0800")
@StaticMetamodel(Building.class)
public class Building_ {
	public static volatile SingularAttribute<Building, Integer> id;
	public static volatile SingularAttribute<Building, String> name;
	public static volatile SingularAttribute<Building, String> aliasName;
	public static volatile SingularAttribute<Building, Integer> totalLayers;
	public static volatile SingularAttribute<Building, Integer> finishDate;
	public static volatile SingularAttribute<Building, Integer> subEstateId;
	public static volatile SingularAttribute<Building, Integer> estateId;
	public static volatile SingularAttribute<Building, Integer> status;
	public static volatile SingularAttribute<Building, String> longitude;
	public static volatile SingularAttribute<Building, String> latitude;
	public static volatile SingularAttribute<Building, String> geoHash;
	public static volatile SingularAttribute<Building, Date> addTime;
}
