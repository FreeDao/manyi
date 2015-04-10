package com.manyi.ihouse.entity.hims;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T11:31:55.272+0800")
@StaticMetamodel(BuildingImage.class)
public class BuildingImage_ {
	public static volatile SingularAttribute<BuildingImage, Integer> id;
	public static volatile SingularAttribute<BuildingImage, Integer> type;
	public static volatile SingularAttribute<BuildingImage, Integer> buildingId;
	public static volatile SingularAttribute<BuildingImage, String> description;
	public static volatile SingularAttribute<BuildingImage, String> imgExt;
	public static volatile SingularAttribute<BuildingImage, String> imgKey;
	public static volatile SingularAttribute<BuildingImage, String> thumbnailKey;
	public static volatile SingularAttribute<BuildingImage, Integer> status;
	public static volatile SingularAttribute<BuildingImage, Integer> orderNum;
	public static volatile SingularAttribute<BuildingImage, Date> addTime;
}
