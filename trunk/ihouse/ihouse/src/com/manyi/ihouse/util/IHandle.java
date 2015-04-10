/**
 * 
 */
package com.manyi.ihouse.util;

/**
 * @author zxc
 */
public interface IHandle {

	//自定义初始化规则
	public <T> T init(T obj); 
	//自定义字段为null规则
	public <T> boolean isNull(T obj);
}
