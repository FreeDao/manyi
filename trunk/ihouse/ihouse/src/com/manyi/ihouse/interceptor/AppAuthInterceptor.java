package com.manyi.ihouse.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class AppAuthInterceptor extends HandlerInterceptorAdapter {

	private Map<String, Boolean> actions;

//	private SystemService systemService;

	public Map<String, Boolean> getActions() {
		return actions;
	}

	public void setActions(Map<String, Boolean> actions) {
		this.actions = actions;
	}

//	public SystemService getSystemService() {
//		return systemService;
//	}
//
//	public void setSystemService(SystemService systemService) {
//		this.systemService = systemService;
//	}

	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String servletPath = request.getServletPath();
//
//		if (servletPath.startsWith(Global.RESTFULL_PATH_PRE)) {
//			// 查看当前路径是否需要登录,与配置文件中的配置的进行对比,没有配置的为true
//			if (!getActions().containsKey(servletPath) || getActions().get(servletPath)) {
//
//				ObjectMapper objectMapper = new ObjectMapper();
//
//				Map<String, Object> parameterMap = null;
//				try{
//					parameterMap = objectMapper.readValue(request.getInputStream(), Map.class);
//				}catch(Exception e){
//					parameterMap = new HashMap<String,Object>(0);
//				}
//
//				String appKey = request.getHeader("App-Key");
//				String appSecret = request.getHeader("App-Secret");
//
//				if (StringUtils.isBlank(appKey) || StringUtils.isBlank(appSecret))
//					throw new LeoFault(Global.APP_AUTH_FAILED);
//
//				getSystemService().appAuth(parameterMap, appKey, appSecret);
//			}
//		}

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
