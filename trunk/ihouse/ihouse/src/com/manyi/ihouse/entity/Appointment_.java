package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-26T14:59:30.429+0800")
@StaticMetamodel(Appointment.class)
public class Appointment_ {
	public static volatile SingularAttribute<Appointment, Long> id;
	public static volatile SingularAttribute<Appointment, User> user;
	public static volatile SingularAttribute<Appointment, Long> agent_id;
	public static volatile SingularAttribute<Appointment, Date> appointment_time;
	public static volatile SingularAttribute<Appointment, Date> agent_checkin_time;
	public static volatile SingularAttribute<Appointment, String> agent_checkin_addr;
	public static volatile SingularAttribute<Appointment, Date> user_checkin_time;
	public static volatile SingularAttribute<Appointment, String> user_checkin_addr;
	public static volatile SingularAttribute<Appointment, String> meetAddress;
	public static volatile SingularAttribute<Appointment, String> memo;
	public static volatile SingularAttribute<Appointment, Integer> appointmentState;
	public static volatile SingularAttribute<Appointment, Integer> ability;
	public static volatile SingularAttribute<Appointment, Integer> appearance;
	public static volatile SingularAttribute<Appointment, Integer> attitude;
	public static volatile SingularAttribute<Appointment, String> appraise;
	public static volatile SingularAttribute<Appointment, String> changeDateMemo;
	public static volatile SingularAttribute<Appointment, String> cancelMemo;
	public static volatile SingularAttribute<Appointment, Integer> aware_state;
	public static volatile SingularAttribute<Appointment, Integer> cancelReason;
	public static volatile SingularAttribute<Appointment, String> seehouseNumber;
	public static volatile SetAttribute<Appointment, SeekHouse> seekHouse;
	public static volatile SetAttribute<Appointment, SeekhouseHistory> seekhouseHistory;
}
