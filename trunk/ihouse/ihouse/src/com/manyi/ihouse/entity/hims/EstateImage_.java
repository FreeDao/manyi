package com.manyi.ihouse.entity.hims;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-06-18T11:31:55.285+0800")
@StaticMetamodel(EstateImage.class)
public class EstateImage_ {
	public static volatile SingularAttribute<EstateImage, Integer> id;
	public static volatile SingularAttribute<EstateImage, Integer> type;
	public static volatile SingularAttribute<EstateImage, Integer> estateId;
	public static volatile SingularAttribute<EstateImage, String> description;
	public static volatile SingularAttribute<EstateImage, String> imgExt;
	public static volatile SingularAttribute<EstateImage, String> imgKey;
	public static volatile SingularAttribute<EstateImage, String> thumbnailKey;
	public static volatile SingularAttribute<EstateImage, Integer> status;
	public static volatile SingularAttribute<EstateImage, Integer> orderNum;
	public static volatile SingularAttribute<EstateImage, Date> addTime;
}
