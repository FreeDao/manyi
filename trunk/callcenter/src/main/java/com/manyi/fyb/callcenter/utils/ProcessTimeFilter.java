package com.manyi.fyb.callcenter.utils;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 执行时间过滤器
 * 
 * @author liufang
 * 
 */
public class ProcessTimeFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(ProcessTimeFilter.class);

	/**
	 * 请求执行开始时间
	 */
	public static final String START_TIME = "_start_time";

	public void destroy() {
	}

	public void doFilter(ServletRequest req, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		long time = System.currentTimeMillis();
		request.setAttribute(START_TIME, time);
		ParamUtils.printParameters(request);
		chain.doFilter(request, response);
		time = System.currentTimeMillis() - time;
		//log.debug("process in {} ms: {}", time, request.getRequestURI());//slf api
		logger.info("process in {" + time + "} ms: {" + request.getRequestURI() + "}");
	}

	public void init(FilterConfig arg0) throws ServletException {
	}
}
