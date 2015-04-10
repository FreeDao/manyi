/**
 * 
 */
package com.manyi.hims.util;

/**
 * @author zxc
 */
public interface IHandle {

	//自定义初始化规则
	public <T> T init(Class<T> clazz); 
	//自定义字段为null规则
	public <T> boolean isNull(T obj);
}
