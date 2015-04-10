package com.manyi.hims.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.leo.jaxrs.fault.LeoFault;
import com.manyi.hims.Global;
import com.manyi.hims.uc.UcConst;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    private Map<String, Boolean> actions;

    public Map<String, Boolean> getActions() {
        return actions;
    }

    public void setActions(Map<String, Boolean> actions) {
        this.actions = actions;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    	//获得相对于项目的绝对路径,这里需要获得的是/rest开头的路径
    	String servletPath = request.getServletPath();
    	
    	//判断得到的路径是否是想要的路径
    	if(servletPath.startsWith(Global.RESTFULL_PATH_PRE)) {
    		//查看当前路径是否需要登录,与配置文件中的配置的进行对比,没有配置的为true
    		if(!getActions().containsKey(servletPath) || getActions().get(servletPath)) {
    			//获得Session
    			HttpSession session = request.getSession(true);
    			//判断Session里是否有用户已经登录的记录,如果没有抛出异常
    			if(session==null || session.getAttribute(UcConst.SESSION_UID_KEY)==null)
                    throw new LeoFault(LeoFault.INVALID_TOKEN);
    		}
    	}
    	
    	return super.preHandle(request, response, handler);
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        super.postHandle(request, response, handler, modelAndView);
    }

}
