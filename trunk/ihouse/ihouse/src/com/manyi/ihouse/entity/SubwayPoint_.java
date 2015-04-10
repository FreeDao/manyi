package com.manyi.ihouse.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T17:47:50.926+0800")
@StaticMetamodel(SubwayPoint.class)
public class SubwayPoint_ {
	public static volatile SingularAttribute<SubwayPoint, Integer> pointId;
	public static volatile SingularAttribute<SubwayPoint, String> pointDesc;
	public static volatile SingularAttribute<SubwayPoint, String> pointLat;
	public static volatile SingularAttribute<SubwayPoint, String> pointLon;
	public static volatile SingularAttribute<SubwayPoint, Integer> pointOrder;
	public static volatile SingularAttribute<SubwayPoint, Integer> pointStatus;
	public static volatile SingularAttribute<SubwayPoint, SubwayLine> subwayLine;
	public static volatile SingularAttribute<SubwayPoint, String> pointGeoHash;
}
