
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.RequestContext;

import com.uib.base.BaseController;
import com.uib.common.Principal;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.service.CaptchaService;
import com.uib.common.utils.MailSenderUtil;
import com.uib.common.utils.RandomUtil;
import com.uib.common.utils.WebUtil;
import com.uib.member.dto.MemberDto;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;

/**
 * 会员注册
 * 
 * @Title RegisterController
 * @Company: e-life
 * @Copyright: Copyright(C) 2014
 * @Version 1.0
 * @author elife
 * @date 2014-6-10
 * @time 下午2:55:49
 * @Description
 */
@Controller
@RequestMapping("/reg")
public class RegisterController extends BaseController {

	@Autowired
	private MemMemberService memMemberService;

	@Autowired
	private MailSenderUtil mailSenderUtil;

	@Autowired
	private CaptchaService captchaService;

	@Value("/register")
	private String registerView;

	@Value("${systemPath}")
	private String systemPath;

	private static final String CAPTCHA_SEQ = "captcha_seq";

	@RequestMapping("/validateUserName")
	@ResponseBody
	public String validateUserName(String username) {
		String memberFlag = "false";
		MemMember member = memMemberService.getMemMemberByUsername(username);
		if (null != member) {
			memberFlag = "true";
		}
		return memberFlag;
	}
	
	/**
	 * 验证手机号码是否已使用
	 * @param phone
	 * @return
	 */
	@RequestMapping("/validatePhone")
	@ResponseBody
	public String validatePhone(String phone) {
		String memberFlag = "false";
		MemMember member = null;
		try {
			member = memMemberService.getMemberByPhone(phone);
		} catch (Exception e) {
			logger.info("验证手机号码是否已使用异常"+e);
		}
		if (null != member) {
			memberFlag = "true";
		}
		return memberFlag;
	}

	@RequestMapping("/registerView")
	public String registerView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		request.setAttribute("captchaId", request.getSession().getId());
		return registerView;
	}

	/**
	 * 验证验证码
	 * 
	 * @param email
	 * @param phone
	 * @param request
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/validateCode")
	@ResponseBody
	public String validateCode(String email, String phone, String captcha, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		String flag = "false";
		try {
			if (!captcha.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				return flag;
			}
			String validateCode = RandomUtil.getRandom(4);
			boolean tempflag = memMemberService.sendValidateCode(phone, email, validateCode);
			if (tempflag) {
				request.getSession().setAttribute("validateCode", validateCode);
				flag = "true";
			} else {
				flag = "false";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			flag = "false";
		}
		return flag;
	}

	/**
	 * 验证码验证
	 */
	@RequestMapping(value = "/captcha/check", method = RequestMethod.POST)
	@ResponseBody
	public ReturnMsg<Object> check(String captchaId, String captcha, HttpServletRequest request) {
		ReturnMsg<Object> result = new ReturnMsg<Object>();
		try {
			if (StringUtils.isEmpty(captchaId)) {
				captchaId = request.getSession().getId();
			}
			logger.info("captchaId:"+captchaId);
			if (Utils.isObjectsBlank(captchaId, captcha)) {
				result.setMsg("验证参数为空");
				result.setStatus(false);
				result.setCode("506");// 参数错误码
				String captchaId_ = UUID.randomUUID().toString();
				request.setAttribute("captchaId", captchaId_);
			}
			if (captcha.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				result.setStatus(true);
				result.setCode("200");
				result.setMsg("验证成功");
			} else if (!captchaService.isValid(captchaId, captcha)) {
				result.setStatus(false);
				result.setCode("507");
				result.setMsg("验证错误");
				// String captchaId_ = UUID.randomUUID().toString();
				// request.setAttribute("captchaId", captchaId_);
				// result.setData(captchaId_);
			} else {
				result.setStatus(true);
				result.setCode("200");
				result.setMsg("验证成功");
				request.getSession().setAttribute(CAPTCHA_SEQ, captcha);
			}
		} catch (Exception e) {
			logger.error("验证码验证失败", e);
			result.setCode("500");// 内部错误
			result.setMsg("验证失败");
			result.setStatus(false);
			request.setAttribute("captchaId", UUID.randomUUID().toString());
		}
		return result;
	}

	@RequestMapping("/saveMember")
	public String saveMember(@ModelAttribute MemberDto memberDto, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) {
		String validateCode = (String) request.getSession().getAttribute("validateCode");
		String fromPageValidateCode = request.getParameter("validateCode");
		String captcha = request.getParameter("captcha");
		request.setAttribute("captchaId", request.getSession().getId());
		RequestContext requestContext = new RequestContext(request);
		if (Utils.isObjectsBlank(validateCode, fromPageValidateCode, captcha)) {
			modelMap.addAttribute("showmessage", requestContext.getMessage("user.regist.validcodeerror"));
			return "/register";
		} else {
			if (!validateCode.equals(fromPageValidateCode) || !captcha.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				modelMap.addAttribute("showmessage", requestContext.getMessage("user.regist.validcodeerror"));
				return "/register";
			}
			request.getSession().removeAttribute("validateCode");
		}
		try {
			MemMember m = memMemberService.memberLogin(memberDto.getUserName(), memberDto.getPhone());
			if (m != null) {
				modelMap.addAttribute("showmessage", ExceptionEnum.phone_is_register.getValue());
				return "/register";
			}
			memMemberService.saveMember(memberDto);
			// returnStr = "注册成功";
		} catch (Exception ex) {
			ex.printStackTrace();
			modelMap.addAttribute("showmessage", requestContext.getMessage("user.regist.failregist"));
			return "/register";
		}

		// 保存注册的用户信息到sessions
		String redirectUrl = "redirect:/f/index";
		MemMember member = memMemberService.getMemMemberByUsername(memberDto.getUserName());
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
		if (StringUtils.isNotEmpty(request.getParameter("redirectUrl"))) {
			redirectUrl = (String) request.getParameter("redirectUrl");
			redirectUrl = redirectUrl.substring(redirectUrl.indexOf("/f"), redirectUrl.length());
			redirectUrl = "redirect:" + redirectUrl;
		}

		session.setAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
		session.setAttribute("member", member);
		WebUtil.addCookie(request, response, "username", member.getUsername());
		request.getSession().removeAttribute(CAPTCHA_SEQ);
		return redirectUrl;
		// return "forward:/f/index";
	}

	// 查询手机号是否存在
	@ResponseBody
	@RequestMapping("/phoneIsExist")
	public boolean phoneIsExist(String phone) {
		MemMember m = null;
		try {
			m = memMemberService.memberLogin(null, phone);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m == null;
	}

}