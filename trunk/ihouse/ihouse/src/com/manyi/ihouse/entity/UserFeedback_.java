package com.manyi.ihouse.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T14:19:33.980+0800")
@StaticMetamodel(UserFeedback.class)
public class UserFeedback_ {
	public static volatile SingularAttribute<UserFeedback, Long> id;
	public static volatile SingularAttribute<UserFeedback, User> user;
	public static volatile SingularAttribute<UserFeedback, String> mobileSn;
	public static volatile SingularAttribute<UserFeedback, String> feedbackContent;
	public static volatile SingularAttribute<UserFeedback, Date> update_time;
	public static volatile SingularAttribute<UserFeedback, Date> create_time;
}
