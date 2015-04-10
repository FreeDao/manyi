package com.ecpss.mp.entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-02-23T18:09:00.524+0800")
@StaticMetamodel(ProvinceType.class)
public class ProvinceType_ {
	public static volatile SingularAttribute<ProvinceType, Integer> ptid;
	public static volatile SingularAttribute<ProvinceType, String> title;
	public static volatile ListAttribute<ProvinceType, Province> provinces;
}
