package com.manyi.ihouse.entity.hims;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-13T13:43:49.584+0800")
@StaticMetamodel(HouseResource.class)
public class HouseResource_ {
	public static volatile SingularAttribute<HouseResource, Integer> houseId;
	public static volatile SingularAttribute<HouseResource, Integer> userId;
	public static volatile SingularAttribute<HouseResource, BigDecimal> rentPrice;
	public static volatile SingularAttribute<HouseResource, BigDecimal> sellPrice;
	public static volatile SingularAttribute<HouseResource, Boolean> isGotPrice;
	public static volatile SingularAttribute<HouseResource, Date> rentFreeDate;
	public static volatile SingularAttribute<HouseResource, Date> rentTermDate;
	public static volatile SingularAttribute<HouseResource, Date> entrustDate;
	public static volatile SingularAttribute<HouseResource, Integer> houseState;
	public static volatile SingularAttribute<HouseResource, Integer> stateReason;
	public static volatile SingularAttribute<HouseResource, Integer> actionType;
	public static volatile SingularAttribute<HouseResource, Date> publishDate;
	public static volatile SingularAttribute<HouseResource, Integer> operatorId;
	public static volatile SingularAttribute<HouseResource, Integer> status;
	public static volatile SingularAttribute<HouseResource, Integer> checkNum;
	public static volatile SingularAttribute<HouseResource, Date> resultDate;
	public static volatile SingularAttribute<HouseResource, String> remark;
}
