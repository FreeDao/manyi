package com.manyi.fyb.callcenter.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class ReflectUtils {
	
	private static final Logger logger =LoggerFactory.getLogger(ReflectUtils.class);
	
	/**
	 * 将泛型形参给出的类中设置的属性值转换为Map形式的键值对
	 * t一般是pojo类
	 * 
	 * @param params
	 * @param t
	 */
	public static <T extends Object> void flushParams(Map<String, Object> params,T t) {
		if(params == null || t == null)
			return;
		
		Class<?> clazz = t.getClass() ;
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields() ;
				
				for (int j = 0; j < fields.length; j++) { // 遍历所有属性
					String name = fields[j].getName(); // 获取属性的名字
					Object value = null;
					
					if(logger.isDebugEnabled())
						logger.debug(ReflectUtils.class + "method flushParams attribute name:" + name + "  ");
						
					if(! "serialVersionUID".equals(name)){
						Method method = t.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
						value = method.invoke(t);

						if(logger.isDebugEnabled())
							logger.debug(ReflectUtils.class + "attribute value:" + value);
						
						if(value != null)
							params.put(name, value);
					}else {
						logger.info("serialVersionUID" );
					}
				}
			} catch (Exception e) {} 
		}
	}
	
	/**
	 * 将Map形式的键值对中的值转换为泛型形参给出的类中的属性值
	 * t一般代表pojo类
	 * 
	 * @param t
	 * @param params
	 */
	public static <T extends Object> void flushObject(T t, Map<String, Object> params) {
		if(params == null || t == null)
			return;
		
		Class<?> clazz = t.getClass() ;
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {
				Field[] fields = clazz.getDeclaredFields() ;
				
				for(int i = 0 ; i< fields.length;i++){
					String name = fields[i].getName(); // 获取属性的名字
					
					if(logger.isDebugEnabled())
						logger.debug(ReflectUtils.class + "method flushObject attribute name:" + name + "  ");
					
					Object value = params.get(name);
					if(value != null && !"".equals(value)){
						//注意下面这句，不设置true的话，不能修改private类型变量的值
						fields[i].setAccessible(true);
						fields[i].set(t, value);
					}
				}
			}catch(Exception e){}
		}
	}	
}
