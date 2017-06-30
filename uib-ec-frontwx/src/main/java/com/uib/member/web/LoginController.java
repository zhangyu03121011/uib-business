package com.uib.member.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.uib.base.BaseController;
import com.uib.common.Principal;
import com.uib.common.service.CaptchaService;
import com.uib.common.utils.DigestUtil;
import com.uib.common.utils.WebUtil;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	@Autowired
	private CaptchaService captchaService;
	
	@Autowired
	private MemMemberService memMemberService;
	
	/**
	 * 登录检测
	 */
	@RequestMapping(value = "/check", method = RequestMethod.GET)
	public @ResponseBody
	Boolean check() {
		return memMemberService.isAuthenticated();
	}
	
	
	/**
	 * 登陆页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index( HttpServletRequest request, HttpServletResponse response,ModelMap modelMap){
		String captchaId = UUID.randomUUID().toString();
		String errorCode = request.getParameter("error");
		String redirectUrl =	request.getParameter("redirectUrl");
		if (StringUtils.isNotEmpty(errorCode)) {
			modelMap.addAttribute("errorCode", errorCode);
		}
		modelMap.addAttribute("redirectUrl", redirectUrl);
		modelMap.addAttribute("captchaId", captchaId);
		return "login";
	}
	
	
	
	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public String submit(String captchaId, String captcha, String userName,String password, HttpServletRequest request, HttpServletResponse response, HttpSession session,ModelMap modelMap) {
		String redirectUrl  = "redirect:/f/index";
		try {
			//从后台代码获取国际化信息
            RequestContext requestContext = new RequestContext(request);

			if (!captchaService.isValid(captchaId, captcha)){
				modelMap.addAttribute("redirectUrl", request.getParameter("redirectUrl"));
				modelMap.addAttribute("captchaId", UUID.randomUUID());
				modelMap.addAttribute("errorCode", requestContext.getMessage("user.codeValidate.error"));
				return "login"; 
			}
			
			MemMember member =	memMemberService.memberLogin(userName, userName);
			if (null == member){
				modelMap.addAttribute("redirectUrl", request.getParameter("redirectUrl"));
				modelMap.addAttribute("captchaId", UUID.randomUUID());
				modelMap.addAttribute("errorCode", requestContext.getMessage("user.username.inexistence"));
				return "login"; 
			}
			if (!DigestUtil.MD5(password).equals(member.getPassword())){
				modelMap.addAttribute("redirectUrl", request.getParameter("redirectUrl"));
				modelMap.addAttribute("captchaId", UUID.randomUUID());
				modelMap.addAttribute("errorCode", requestContext.getMessage("user.password.error"));
				return "login"; 
			}
			Map<String, Object> attributes = new HashMap<String, Object>();
			Enumeration<?> keys = session.getAttributeNames();
			while (keys.hasMoreElements()) {
				String key = (String) keys.nextElement();
				attributes.put(key, session.getAttribute(key));
			}
			session.invalidate();
			session = request.getSession();
			for (Entry<String, Object> entry : attributes.entrySet()) {
				session.setAttribute(entry.getKey(), entry.getValue());
			}
			if (StringUtils.isNotEmpty(request.getParameter("redirectUrl"))){
				 redirectUrl  =(String)	request.getParameter("redirectUrl");
				 redirectUrl = redirectUrl.substring(redirectUrl.indexOf("/f"), redirectUrl.length()  ) ;
				 redirectUrl = "redirect:" + redirectUrl;
			}
		   
			session.setAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), userName));
			session.setAttribute("member", member);
			WebUtil.addCookie(request, response, "username", member.getUsername());
		} catch (Exception ex ){
			ex.printStackTrace();
		}
	   return redirectUrl; 

	}
	
}
