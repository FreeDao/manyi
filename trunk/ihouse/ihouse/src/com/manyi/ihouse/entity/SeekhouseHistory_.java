package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-25T14:46:55.951+0800")
@StaticMetamodel(SeekhouseHistory.class)
public class SeekhouseHistory_ {
	public static volatile SingularAttribute<SeekhouseHistory, Long> id;
	public static volatile SingularAttribute<SeekhouseHistory, Long> houseId;
	public static volatile SingularAttribute<SeekhouseHistory, Integer> state;
	public static volatile SingularAttribute<SeekhouseHistory, String> memo;
	public static volatile SingularAttribute<SeekhouseHistory, Date> update_time;
	public static volatile SingularAttribute<SeekhouseHistory, Date> create_time;
	public static volatile SingularAttribute<SeekhouseHistory, String> nosaw_reason;
	public static volatile SingularAttribute<SeekhouseHistory, Appointment> appointment;
}
