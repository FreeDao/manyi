/**
 * 
 */
package com.manyi.hims.system.service;

import java.util.Map;

/**
 * @author leo.li
 *
 */
public interface SystemService {
	public void appAuth(Map<String,Object> parameterMap,String appKey,String appSecret);
}
