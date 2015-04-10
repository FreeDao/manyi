/**
 * 
 */
package com.manyi.hims.system.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.leo.common.util.MD5Digest;
import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.BaseService;
import com.manyi.hims.Global;
import com.manyi.hims.entity.Application;
import com.manyi.hims.entity.Application_;

/**
 * @author leo.li
 *
 */
@Service(value = "systemService")
@Scope(value = "singleton")
public class SystemServiceImpl extends BaseService implements SystemService {
	private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

	@Override
	public void appAuth(Map<String, Object> parameterMap, String appKey, String appSecret) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Application> cq = cb.createQuery(Application.class);
		Root<Application> oe = cq.from(Application.class);
		
		cq.where(cb.equal(oe.get(Application_.appKey), appKey)).select(oe);
		
        try{
        	Application app = getEntityManager().createQuery(cq).setHint("org.hibernate.cacheable", true).getSingleResult();
        	if(!app.isEnable())
        		throw new LeoFault(Global.APP_STATUS_DISABLE);

        	List<Map.Entry<String, Object>> params = new ArrayList<Map.Entry<String, Object>>(parameterMap.entrySet());// 
        	Collections.sort(params, new Comparator<Map.Entry<String, Object>>() {
    			public int compare(Entry<String, Object> o1,Entry<String, Object> o2) {
    				if(o1.getKey()==null || o2.getKey()==null)
    					return 0;
    				return o1.getKey().compareTo(o2.getKey());
    			}
    		});
        	
        	//a=1&b=b&MT0VT5EN1FAP7SGA840OBW2DUFJUAB&1100000000
        	String secret = "";
        	for(Map.Entry<String, Object> param : params){
        		String key = param.getKey();
        		Object value = param.getValue();
        		secret = secret + key + "=" + value + "&";
        	}
        	if(secret.endsWith("&"))
        		secret = secret.substring(0, secret.length()-1);
        	
        	long currentTime = System.currentTimeMillis()/1000000;
        	
        	String secret_ = secret + "&" + app.getAppSecret() + "&" + currentTime;
        	//logger.info(secretFirst);
        	String md5 = MD5Digest.getMD5Digest(secret_);
        	//logger.info(md5);
        	
        	if(!md5.equalsIgnoreCase(appSecret)){
        		secret_ = secret + "&" + app.getAppSecret() + "&" + (currentTime+1);
        		md5 = MD5Digest.getMD5Digest(secret_);
        		if(!md5.equalsIgnoreCase(appSecret)){
            		secret_ = secret + "&" + app.getAppSecret() + "&" + (currentTime-1);
            		md5 = MD5Digest.getMD5Digest(secret_);
            		if(!md5.equalsIgnoreCase(appSecret)){
            			logger.info("{}, App-Key:{}, App-Secret:{}, appSecretNotMatch:{}", Global.APP_AUTH_FAILED, appKey, appSecret);
            			throw new LeoFault(Global.APP_AUTH_FAILED);
            		}
        		}
        	}
        		
        		
        	
        }catch(NoResultException e){
			logger.info("{}, App-Key:{}, App-Secret:{}, Message:{}", Global.APP_AUTH_FAILED, appKey, appSecret, e.getMessage());
        	throw new LeoFault(Global.APP_AUTH_FAILED);
        }
		
	}
}
