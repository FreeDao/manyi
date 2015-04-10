package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T15:15:27.106+0800")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Long> id;
	public static volatile SingularAttribute<User, String> mobile;
	public static volatile SingularAttribute<User, String> mobileSn;
	public static volatile SingularAttribute<User, String> name;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, Integer> gender;
	public static volatile SingularAttribute<User, String> memo;
	public static volatile SingularAttribute<User, Agent> agent;
	public static volatile SingularAttribute<User, Date> last_login_time;
	public static volatile SingularAttribute<User, Integer> user_type;
	public static volatile SingularAttribute<User, Integer> sys_status;
	public static volatile SingularAttribute<User, Integer> biz_status;
	public static volatile SingularAttribute<User, Date> update_time;
	public static volatile SingularAttribute<User, Date> create_time;
	public static volatile SetAttribute<User, UserCollection> userCollection;
	public static volatile SetAttribute<User, SeekHouse> seeHouse;
	public static volatile SetAttribute<User, Appointment> appointment;
	public static volatile SetAttribute<User, UserFeedback> userFeedback;
}
