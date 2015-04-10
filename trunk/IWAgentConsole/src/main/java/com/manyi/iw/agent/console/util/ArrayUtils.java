package com.manyi.iw.agent.console.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ArrayUtils {
	/**
	 * 将数组转换为集合
	 * @param <T>
	 * @param a
	 * @return
	 */
	public static <T> List<T> asList(T... a) {
		List<T> list=new ArrayList<T>();
		if(a!=null && a.length>0){
			for(T t:a){
				list.add(t);
			}
		}
		return list;
	}
	
	public static boolean hasValue(@SuppressWarnings("rawtypes") Collection collection){
		return collection!=null && collection.size()>0;
	}
	
	public static boolean hasValue(@SuppressWarnings("rawtypes") Map map){
		return map!=null && map.size()>0;
	}
}
