package com.manyi.ihouse.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-20T16:01:52.252+0800")
@StaticMetamodel(SubwayStation.class)
public class SubwayStation_ {
	public static volatile SingularAttribute<SubwayStation, Integer> subwayStationId;
	public static volatile SingularAttribute<SubwayStation, String> stationDesc;
	public static volatile SingularAttribute<SubwayStation, String> stationLat;
	public static volatile SingularAttribute<SubwayStation, String> stationLon;
	public static volatile SingularAttribute<SubwayStation, String> stationName;
	public static volatile SingularAttribute<SubwayStation, String> stationOrder;
	public static volatile SingularAttribute<SubwayStation, Integer> stationStatus;
	public static volatile SingularAttribute<SubwayStation, SubwayLine> subwayLine;
	public static volatile SingularAttribute<SubwayStation, String> stationGeoHash;
}
