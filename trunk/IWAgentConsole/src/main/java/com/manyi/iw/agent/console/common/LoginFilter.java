package com.manyi.iw.agent.console.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.manyi.iw.agent.console.util.StringUtils;

public class LoginFilter implements Filter {
    /**
     * 被排除的路径集合
     */
    private String[] excludes;
    /**
     * 已经被排除过的地址集合
     */
    private List<String> free = new ArrayList<String>();
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String s = filterConfig.getInitParameter("exclude");
        if(StringUtils.hasValue(s)){
            excludes = s.split(";");
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String url = request.getServletPath();
        
        if (isFree(url)) {
            chain.doFilter(request, response);
        }else{
           HttpSession session = request.getSession();
           //未登录
           if(session.getAttribute("agent")==null){
               response.sendRedirect(request.getContextPath()+"/login.jsp");
           }else{
               chain.doFilter(request, response);
           }
        }
    }

    
    /**
     * 检查请求地址是否需要被放过
     * @author  程康
     * <p>创建时间 2014-4-23下午3:32:38</p>
     * @param url
     * @return
     */
    private boolean isFree(String url){
        if(free.contains(url)){
            return true;
        }else{
            Pattern pattern = null;
            Matcher matcher = null;
            for(String s:excludes){
                pattern = Pattern.compile(s);
                matcher = pattern.matcher(url);
                if(matcher.find()){
                    free.add(url);
                    return true;
                }
            }
        }
        return false;
    }
    
    
    
    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}
