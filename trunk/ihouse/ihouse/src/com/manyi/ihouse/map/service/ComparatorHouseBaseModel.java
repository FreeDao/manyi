package com.manyi.ihouse.map.service;

import java.util.Comparator;

import com.manyi.ihouse.user.model.HouseBaseModel;


public class ComparatorHouseBaseModel implements Comparator{

	@Override
	public int compare(Object o1, Object o2) {
		int i = 0;
		// TODO Auto-generated method stub
		HouseBaseModel model1 = (HouseBaseModel)o1;
		HouseBaseModel model2 = (HouseBaseModel)o2;
		if(model1.getDistance() > model2.getDistance())
		i = 1;
			else 
		i = -1;
		return i;
	}

}
