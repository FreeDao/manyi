package com.manyi.fyb.callcenter.base.controller;

import java.util.ArrayList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.manyi.fyb.callcenter.utils.Constants;
import com.manyi.fyb.callcenter.utils.SubmitProtectedUtils;

public class BaseController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String rootUrl;
	
	private RestTemplate restTemplate;
	
	//private RestErrorHandler restErrorHandler;
	
	@Autowired
	protected SubmitProtectedUtils<Object> submitProtectedUtils;

	public BaseController() {
		rootUrl = Constants.LINK_FRONT_PART;
		restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>());
		//restTemplate.getInterceptors().add();//new AppAuthInterceptor()
	}
	
	protected <T> T getForObject(String shortLink,Class<T> clazz,Map<String, Object> map) {
		return restTemplate.getForObject(rootUrl + shortLink, clazz, map);
	}
	
	

}
