package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T14:10:40.245+0800")
@StaticMetamodel(UserCollection.class)
public class UserCollection_ {
	public static volatile SingularAttribute<UserCollection, Long> id;
	public static volatile SingularAttribute<UserCollection, User> user;
	public static volatile SingularAttribute<UserCollection, Long> houseId;
	public static volatile SingularAttribute<UserCollection, Date> update_time;
	public static volatile SingularAttribute<UserCollection, Date> create_time;
	public static volatile SingularAttribute<UserCollection, String> memo;
}
