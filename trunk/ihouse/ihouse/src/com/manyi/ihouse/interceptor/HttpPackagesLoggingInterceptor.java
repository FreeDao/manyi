package com.manyi.ihouse.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class HttpPackagesLoggingInterceptor extends HandlerInterceptorAdapter {
    
    @SuppressWarnings("rawtypes")
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception {
        //throw new RuntimeException("11111111111111");
        Enumeration names = request.getHeaderNames();
        StringBuilder sb = new StringBuilder("headerInfo---\n");  
        while(names.hasMoreElements()) {
            String name = names.nextElement().toString();  
            Enumeration headers = request.getHeaders(name);  
            sb.append(name).append(":");  
            while(headers.hasMoreElements()) {  
                sb.append(headers.nextElement()).append(" ");  
            }  
            sb.append("\n");
        }  
        System.out.println(sb.toString());  
        System.out.println(request.getQueryString());
        
        return super.preHandle(request, response, handler);
    }
    
    public void postHandle(HttpServletRequest request,HttpServletResponse response,Object handler,ModelAndView modelAndView) throws Exception{
        super.postHandle(request, response, handler, modelAndView);
    }

}
