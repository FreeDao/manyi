package com.manyi.ihouse.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-26T23:57:27.155+0800")
@StaticMetamodel(SubwayLine.class)
public class SubwayLine_ {
	public static volatile SingularAttribute<SubwayLine, Integer> subwayLineId;
	public static volatile SingularAttribute<SubwayLine, Integer> lineCityCode;
	public static volatile SingularAttribute<SubwayLine, String> lineDesc;
	public static volatile SingularAttribute<SubwayLine, String> lineName;
	public static volatile SingularAttribute<SubwayLine, Integer> lineStatus;
	public static volatile SingularAttribute<SubwayLine, String> lineLat;
	public static volatile SingularAttribute<SubwayLine, String> lineLon;
	public static volatile SingularAttribute<SubwayLine, String> lineGeoHash;
	public static volatile SingularAttribute<SubwayLine, Integer> level;
	public static volatile SetAttribute<SubwayLine, SubwayStation> subwayStations;
	public static volatile SetAttribute<SubwayLine, SubwayPoint> subwayPoints;
}
