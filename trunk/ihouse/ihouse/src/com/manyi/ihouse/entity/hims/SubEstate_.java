package com.manyi.ihouse.entity.hims;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T11:31:55.296+0800")
@StaticMetamodel(SubEstate.class)
public class SubEstate_ {
	public static volatile SingularAttribute<SubEstate, Integer> id;
	public static volatile SingularAttribute<SubEstate, String> name;
	public static volatile SingularAttribute<SubEstate, Integer> estateId;
	public static volatile SingularAttribute<SubEstate, Integer> status;
	public static volatile SingularAttribute<SubEstate, Date> addTime;
}
