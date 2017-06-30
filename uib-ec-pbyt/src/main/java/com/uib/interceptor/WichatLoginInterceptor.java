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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.easypay.common.utils.StringUtil;
import com.uib.common.utils.StringUtils;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WeixinUtil;

/**
 * 拦截微信公众号服务
 * @author zhangxiaoyu
 *
 */
public class WichatLoginInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(WichatLoginInterceptor.class);
	
	@Autowired
	private MemMemberService memMemberService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String sessionId = request.getSession().getId();
		String contextPath = request.getContextPath();
		String session = (String)UserSession.getSession("sessionId");
		String userName = (String)UserSession.getSession("userName");
		logger.info("当前登录session======:"+session);
		if (StringUtil.isNullOrEmpty(session) || StringUtil.isNullOrEmpty(userName)) {
			response.getWriter().print("window.location.href =login.html");
//			request.getRequestDispatcher(contextPath+"/page/weixin/login.html").forward(request,response);
//			new ModelAndView("redirect:"+contextPath+"/page/weixin/login.html");
			
			return false;
		}
		try {
			//如果与用户绑定的sessionId变化了，则为账号在另一终端在线，需要重新登录验证
			MemMember memMemberBySessionId = memMemberService.getMemMemberBySessionId(sessionId);
			if(null == memMemberBySessionId) {
				response.getWriter().print("window.location.href =login.html");
//				response.sendRedirect(contextPath+"/page/weixin/login.html?status=false&code=1016");
//				request.getRequestDispatcher(contextPath+"/page/weixin/login.html").forward(request,response);
				return false;
			}
		} catch (Exception e) {
		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.info("/page/weixin/login.html");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long endTime = System.currentTimeMillis(); // 结束时间
		logger.info("计时结束  " + new SimpleDateFormat("hh:mm:ss.SSS").format(endTime) + "\t URl: " + request.getRequestURI());

	}
	
}
