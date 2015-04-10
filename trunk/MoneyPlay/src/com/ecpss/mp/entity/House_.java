package com.ecpss.mp.entity;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-23T20:56:18.065+0800")
@StaticMetamodel(House.class)
public class House_ {
	public static volatile SingularAttribute<House, Integer> hid;
	public static volatile SingularAttribute<House, String> building;
	public static volatile SingularAttribute<House, Integer> floor;
	public static volatile SingularAttribute<House, Integer> layers;
	public static volatile SingularAttribute<House, Integer> bedroomSum;
	public static volatile SingularAttribute<House, Integer> livingRoomSum;
	public static volatile SingularAttribute<House, Integer> wcSum;
	public static volatile SingularAttribute<House, Integer> balconySum;
	public static volatile SingularAttribute<House, String> room;
	public static volatile SingularAttribute<House, HouseUsage> usage;
	public static volatile SingularAttribute<House, HouseType> type;
	public static volatile SingularAttribute<House, HouseDirection> direction;
	public static volatile SingularAttribute<House, Estate> estate;
	public static volatile SingularAttribute<House, Owner> mainOwner;
	public static volatile SingularAttribute<House, BigDecimal> coveredArea;
	public static volatile SingularAttribute<House, BigDecimal> spaceArea;
}
