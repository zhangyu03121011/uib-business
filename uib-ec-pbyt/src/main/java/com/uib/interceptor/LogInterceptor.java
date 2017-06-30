/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.interceptor;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: LogInterceptor
 * @Description: 日志记录
 * @author sl
 * @date 2015年10月15日 下午3:39:29
 */
public class LogInterceptor implements HandlerInterceptor {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();
		logger.info("开始计时: {} \t 请求参数 :{} ", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime), JSONObject.toJSON(request.getParameterMap()));
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long endTime = System.currentTimeMillis(); // 结束时间
		logger.info("计时结束  " + new SimpleDateFormat("hh:mm:ss.SSS").format(endTime) + "\t URl: " + request.getRequestURI());

	}

}
