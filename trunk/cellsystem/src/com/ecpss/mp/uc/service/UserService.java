/**
 * 
 */
package com.ecpss.mp.uc.service;

import com.ecpss.mp.entity.User;

/**
 * @author lei
 *
 */
public interface UserService {
	public User login(String loginName,String password);
}
