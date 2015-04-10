package com.manyi.hims.interceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.Global;
import com.manyi.hims.system.service.SystemService;

public class AppAuthInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger logger = LoggerFactory.getLogger(AppAuthInterceptor.class);

	private Map<String, Boolean> actions;

	private SystemService systemService;
	
	private List<String> allowHosts;

	public Map<String, Boolean> getActions() {
		return actions;
	}

	public void setActions(Map<String, Boolean> actions) {
		this.actions = actions;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}
	
	public List<String> getAllowHosts() {
		return allowHosts;
	}

	public void setAllowHosts(List<String> allowHosts) {
		this.allowHosts = allowHosts;
	}

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String servletPath = request.getServletPath();
		String host = request.getRemoteHost();
		logger.info("host:{},servletPath:{}" , host,servletPath);
		if (allowHosts != null && allowHosts.size() > 0) {
			for (String one : allowHosts) {
				if (host != null && host.startsWith(one)) {
					return super.preHandle(request, response, handler);
				}
			}
		}
		logger.info("unlocalhost if case...");

		if (servletPath.startsWith(Global.RESTFULL_PATH_PRE)) {
			// 查看当前路径是否需要登录,与配置文件中的配置的进行对比,没有配置的为true
			if (!getActions().containsKey(servletPath) || getActions().get(servletPath)) {

				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> parameterMap = null;
				try{
					parameterMap = objectMapper.readValue(request.getInputStream(), Map.class);
				}catch(Exception e){
					parameterMap = new HashMap<String,Object>(0);
				}
				//Map<String, Object> parameterMap = objectMapper.readValue(request.getInputStream(), Map.class);
//				if(request.getInputStream().available()>0){
//					parameterMap = objectMapper.readValue(request.getInputStream(), Map.class);
//				}else
//					parameterMap = new HashMap<String,Object>(0);

				String appKey = request.getHeader("App-Key");
				String appSecret = request.getHeader("App-Secret");
				String appTime = request.getHeader("App_Time");
//				
//				//验证手机端时间，不能与服务器时间相差10分钟
				if (appTime==null || Math.abs(Long.parseLong(appTime) - System.currentTimeMillis()) > Global.TEN_MINUTES) {
					throw new LeoFault(Global.TIME_ERROR);
				}
				
				logger.info("App-Key:{},App-Secret:{}",appKey,appSecret);

				if (StringUtils.isBlank(appKey) || StringUtils.isBlank(appSecret)) {
					logger.info("{}, App-Key:{}, App-Secret:{}", Global.APP_AUTH_FAILED, appKey, appSecret);
					throw new LeoFault(Global.APP_AUTH_FAILED);
				}

				getSystemService().appAuth(parameterMap, appKey, appSecret);
			}
		}

		return super.preHandle(request, response, handler);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {

		super.postHandle(request, response, handler, modelAndView);
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	
	

}
