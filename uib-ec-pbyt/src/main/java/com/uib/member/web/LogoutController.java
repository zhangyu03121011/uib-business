package com.uib.member.web;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uib.base.BaseController;
import com.uib.common.utils.WebUtil;
import com.uib.member.entity.MemMember;

@Controller
@RequestMapping("/logout")
public class LogoutController extends BaseController {
	
	/**
	 * 注销
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String execute(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		session.removeAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME);
		session.removeAttribute("member");
		WebUtil.removeCookie(request, response, MemMember.USERNAME_COOKIE_NAME);
		return "redirect:/";
	}

}
