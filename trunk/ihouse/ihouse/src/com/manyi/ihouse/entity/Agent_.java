package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T01:33:17.352+0800")
@StaticMetamodel(Agent.class)
public class Agent_ {
	public static volatile SingularAttribute<Agent, Long> id;
	public static volatile SingularAttribute<Agent, String> name;
	public static volatile SingularAttribute<Agent, String> serialNumber;
	public static volatile SingularAttribute<Agent, String> password;
	public static volatile SingularAttribute<Agent, String> mobile;
	public static volatile SingularAttribute<Agent, String> memo;
	public static volatile SingularAttribute<Agent, String> photoUrl;
	public static volatile SingularAttribute<Agent, Date> update_time;
	public static volatile SingularAttribute<Agent, Date> create_time;
	public static volatile SingularAttribute<Agent, Integer> status;
	public static volatile SetAttribute<Agent, User> users;
}
