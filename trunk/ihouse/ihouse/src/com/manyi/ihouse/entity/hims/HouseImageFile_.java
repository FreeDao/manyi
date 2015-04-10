package com.manyi.ihouse.entity.hims;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-23T17:47:50.886+0800")
@StaticMetamodel(HouseImageFile.class)
public class HouseImageFile_ {
	public static volatile SingularAttribute<HouseImageFile, Integer> id;
	public static volatile SingularAttribute<HouseImageFile, Long> houseId;
	public static volatile SingularAttribute<HouseImageFile, String> type;
	public static volatile SingularAttribute<HouseImageFile, String> description;
	public static volatile SingularAttribute<HouseImageFile, String> imgExtensionName;
	public static volatile SingularAttribute<HouseImageFile, String> imgKey;
	public static volatile SingularAttribute<HouseImageFile, String> thumbnailKey;
	public static volatile SingularAttribute<HouseImageFile, Integer> orderId;
	public static volatile SingularAttribute<HouseImageFile, Integer> bdTaskId;
	public static volatile SingularAttribute<HouseImageFile, Integer> userTaskId;
	public static volatile SingularAttribute<HouseImageFile, Integer> enable;
}
