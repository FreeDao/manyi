package com.manyi.ihouse.entity.hims;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-12T10:24:49.480+0800")
@StaticMetamodel(House.class)
public class House_ {
	public static volatile SingularAttribute<House, Integer> houseId;
	public static volatile SingularAttribute<House, String> building;
	public static volatile SingularAttribute<House, Integer> floor;
	public static volatile SingularAttribute<House, String> room;
	public static volatile SingularAttribute<House, Integer> layers;
	public static volatile SingularAttribute<House, BigDecimal> coveredArea;
	public static volatile SingularAttribute<House, BigDecimal> spaceArea;
	public static volatile SingularAttribute<House, Integer> houseRightId;
	public static volatile SingularAttribute<House, Integer> status;
	public static volatile SingularAttribute<House, Date> updateTime;
	public static volatile SingularAttribute<House, Integer> certificates;
	public static volatile SingularAttribute<House, Integer> houseTypeId;
	public static volatile SingularAttribute<House, Integer> houseDirectionId;
	public static volatile SingularAttribute<House, Integer> estateId;
	public static volatile SingularAttribute<House, Integer> mainOwnerId;
	public static volatile SingularAttribute<House, String> otherOwnersId;
	public static volatile SingularAttribute<House, String> serialCode;
	public static volatile SingularAttribute<House, Integer> decorateType;
	public static volatile SingularAttribute<House, Integer> picNum;
	public static volatile SingularAttribute<House, Integer> currBDTaskId;
	public static volatile SingularAttribute<House, Integer> currUserTaskId;
}
