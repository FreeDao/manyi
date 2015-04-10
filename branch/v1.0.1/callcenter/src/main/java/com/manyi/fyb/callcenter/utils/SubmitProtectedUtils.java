package com.manyi.fyb.callcenter.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.springframework.stereotype.Component;

import com.manyi.fyb.callcenter.index.model.TokenModel;

@Component
public class SubmitProtectedUtils<T> {
	
	@Getter
	private Map<String ,TokenModel<T>> submitCheckMap = new HashMap<String ,TokenModel<T>>();
	
	
	private long INTERVAL_TIME = 1000 * 60;
	
	/**
	 * 同步失败则返回false 不允许提交
	 * @param token
	 * @param model
	 * @param employeeId
	 * @param inStance
	 * @return
	 */
	public boolean sync(int token,T model, int employeeId,Date date) {
		
		TokenModel<T> tm = new TokenModel<T>(token,employeeId,model,date);
		synchronized (submitCheckMap) {
			TokenModel<T> tmLast = submitCheckMap.get(String.valueOf(employeeId));
			if (tmLast != null ) {
				if (tm.getTokenObject().equals(tmLast.getTokenObject()) && tm.getAddTime().getTime() - tmLast.getAddTime().getTime() < INTERVAL_TIME) {
					return false;
				}
			}
			submitCheckMap.put(String.valueOf(employeeId), tm);
			
		}
		return true;
		
	}
	
	public boolean sync(int token,T model, int employeeId) {
		
		return sync(token, model, employeeId,new Date());
		
	}
	
	public TokenModel<T> clear(String s) {
		
		return submitCheckMap.remove(s);
		
	}
	

}
