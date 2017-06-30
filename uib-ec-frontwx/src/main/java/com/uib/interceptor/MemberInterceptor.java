package com.uib.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.uib.common.Principal;
import com.uib.common.utils.StringUtils;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;

/**
 * Interceptor - 会员权限
 * 
 * @Title MemberInterceptor
 * @Company: e-life
 * @Copyright: Copyright(C) 2014
 * @Version 1.0
 * @author kevin
 * @date 2014-6-10
 * @time 下午4:47:17
 * @Description
 */
public class MemberInterceptor extends HandlerInterceptorAdapter {

	/** 重定向视图名称前缀 */
	private static final String REDIRECT_VIEW_NAME_PREFIX = "redirect:";

	/** "重定向URL"参数名称 */
	private static final String REDIRECT_URL_PARAMETER_NAME = "redirectUrl";

	/** "会员"属性名称 */
	private static final String MEMBER_ATTRIBUTE_NAME = "member";

	/** 默认登录URL */
	private static final String DEFAULT_LOGIN_URL = "/f/login";

	/** 登录URL */
	private String loginUrl = DEFAULT_LOGIN_URL;

	@Value("${url_escaping_charset}")
	private String urlEscapingCharset;

	@Autowired
	private MemMemberService memMemberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Principal principal = (Principal) session.getAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME);
		if (principal != null) {
			return true;
		} else {
			String requestType = request.getHeader("X-Requested-With");
			if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
				response.addHeader("loginStatus", "accessDenied");
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return false;
			} else {
				if (request.getMethod().equalsIgnoreCase("GET")) {
					String redirectUrl = request.getQueryString() != null ? request.getRequestURI() + "?" + request.getQueryString() : request.getRequestURI();
					response.getWriter().print("<script>top.location='" + request.getContextPath() + loginUrl + "?" + REDIRECT_URL_PARAMETER_NAME + "="
							+ URLEncoder.encode(redirectUrl, urlEscapingCharset) + "'</script>");
					response.getWriter().close();
				} else {
					response.getWriter().print("<script>top.location='" + request.getContextPath() + loginUrl + "'</script>");
					response.getWriter().close();
				}
				return false;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();

			if (!StringUtils.startsWith(viewName, REDIRECT_VIEW_NAME_PREFIX)) {
				modelAndView.addObject(MEMBER_ATTRIBUTE_NAME, memMemberService.getCurrent());
			}
		}
	}

	/**
	 * 获取登录URL
	 * 
	 * @return 登录URL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * 设置登录URL
	 * 
	 * @param loginUrl
	 *            登录URL
	 */
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

}