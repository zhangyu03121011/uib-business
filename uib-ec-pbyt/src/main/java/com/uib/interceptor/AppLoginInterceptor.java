/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.easypay.common.utils.JacksonUtil;
import com.easypay.common.utils.StringUtil;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.SpringContextHolder;
import com.uib.member.dao.MemberLoginStatusDao;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;

/**
 * @ClassName: MemberInterceptorAPI
 * @Description:检查App是否登录
 * @author sl
 * @date 2015年9月24日 下午1:36:08
 */
public class AppLoginInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = LoggerFactory.getLogger(AppLoginInterceptor.class);
	
	@Autowired
	private MemMemberService memMemberService;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		ReturnMsg<Object> msg = new ReturnMsg<Object>();
		String sessionId = request.getParameter("sessionId");
		logger.info("当前登录session======:"+sessionId);
		try {
			if (StringUtil.isNullOrEmpty(sessionId)) {
				msg.setStatus(false);
				msg.setCode(ExceptionEnum.not_login.getIndex());
				response.getWriter().print(JacksonUtil.writeValueAsString(msg));
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().close();
				return false;
			}

			MemMember memMemberBySessionId = memMemberService.getMemMemberBySessionId(sessionId);
			if(null == memMemberBySessionId) {
				msg.setStatus(false);
				msg.setMsg(ExceptionEnum.member_repetition_login.getValue());
				msg.setCode(ExceptionEnum.member_repetition_login.getIndex());
				response.setContentType("application/json; charset=UTF-8");
				response.getWriter().print(JacksonUtil.writeValueAsString(msg));
				response.getWriter().close();
				return false;
			}
		} catch (Exception e) {
			logger.info("检查App是否登录"+e);
		}
		return true;
	}
}
