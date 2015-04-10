package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-24T15:47:13.491+0800")
@StaticMetamodel(SeekHouse.class)
public class SeekHouse_ {
	public static volatile SingularAttribute<SeekHouse, Long> id;
	public static volatile SingularAttribute<SeekHouse, User> user;
	public static volatile SingularAttribute<SeekHouse, Long> houseId;
	public static volatile SingularAttribute<SeekHouse, Integer> state;
	public static volatile SingularAttribute<SeekHouse, Integer> recommend_source;
	public static volatile SingularAttribute<SeekHouse, Long> agent_id;
	public static volatile SingularAttribute<SeekHouse, String> memo;
	public static volatile SingularAttribute<SeekHouse, String> wishTime;
	public static volatile SingularAttribute<SeekHouse, Date> update_time;
	public static volatile SingularAttribute<SeekHouse, Date> create_time;
	public static volatile SingularAttribute<SeekHouse, Appointment> appointment;
}
