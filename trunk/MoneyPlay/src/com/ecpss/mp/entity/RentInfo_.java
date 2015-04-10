package com.ecpss.mp.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-23T21:38:05.000+0800")
@StaticMetamodel(RentInfo.class)
public class RentInfo_ {
	public static volatile SingularAttribute<RentInfo, Long> riid;
	public static volatile SingularAttribute<RentInfo, BigDecimal> price;
	public static volatile SingularAttribute<RentInfo, Date> trustDate;
	public static volatile SingularAttribute<RentInfo, Date> freeDate;
}
