package com.uib.mobile.web;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.RequestContext;

import com.easypay.common.utils.DateUtil;
import com.easypay.common.utils.DigestUtil;
import com.easypay.common.utils.RandomUtil;
import com.easypay.common.utils.UUIDGenerator;
import com.uib.cart.service.CartService;
import com.uib.common.Principal;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.FileUploadUtil;
import com.uib.common.utils.StringUtils;
import com.uib.member.dto.MemberDto;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.member.entity.MemMember;
import com.uib.member.entity.MemberLoginStatus;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.constants.Constants;
import com.uib.mobile.dto.MemberLoginStatusDto;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.Cart4MobileService;
import com.uib.mobile.service.CommentService;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.serviceUtils.Utils;

/**
 * 手机
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/mobile/user")
public class UserController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("/mobile/registered-success")
	private String registerView;
	
	@Value("/mobile/login-success")
	private String loginView;

	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private CommentService commentService;

	@Autowired
	private Cart4MobileService cart4MobileService;

	private static final String CAPTCHA_SEQ = "captcha_seq";

	/**
	 * 清除大于120秒的验证码
	 */
	private void removeValidateCode() {
		for (Map.Entry<String, ValidateCodeInfo> entry : Constants.codeMap.entrySet()) {
			ValidateCodeInfo codeInfo = entry.getValue();
			long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
			if (time > 120000) {
				Constants.codeMap.remove(entry.getKey());
			}
		}
	}

	/**
	 * wap获取验证码
	 * 
	 * @param email
	 *            邮箱号
	 * @param phone
	 *            手机号
	 * @param sendType
	 *            发邮type 1:手机,0:邮箱
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/wap/validateCode")
	@ResponseBody
	public ReturnMsg<Object> validateCode4Wap(String email, String phone, String sendType, String captcha, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (Utils.isObjectsBlank(phone, sendType) && Utils.isObjectsBlank(email, sendType)) {
				returnMsg.setStatus(false);
				returnMsg.setCode("501");
				return returnMsg;
			}
			if (!captcha.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				returnMsg.setStatus(false);
				returnMsg.setCode("502");
				return returnMsg;
			}
			String validateCode = RandomUtil.getRandom(4);
			logger.info("发送验证码为：" + validateCode);
			boolean tempflag = memMemberService.sendValidateCode(phone, email, sendType, validateCode);
			if (tempflag) {
				ValidateCodeInfo codeInfo = new ValidateCodeInfo();
				codeInfo.setValidateCode(validateCode);
				codeInfo.setCreateDate(DateUtil.dateToDateString(new Date()));
				request.getSession().setAttribute("validateCodeInfo", codeInfo);
				returnMsg.setStatus(true);
			} else {
				returnMsg.setStatus(false);
			}

		} catch (Exception ex) {
			returnMsg.setStatus(false);
			ex.printStackTrace();
		}
		return returnMsg;
	}

	/**
	 * 获取验证码
	 * 
	 * @param email
	 *            邮箱号
	 * @param phone
	 *            手机号
	 * @param isRegist 1: 注册, 2: 忘记密码
	 * @param sendType
	 *            发邮type 1:手机,0:邮箱
	 * @param response
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/validateCode")
	@ResponseBody
	public ReturnMsg<Object> validateCode(String email, String phone,String isRegist,  String sendType, HttpServletRequest request,
			HttpServletResponse response, ModelMap modelMap) {
		removeValidateCode();
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			MemMember member=	memMemberService.getMemberByPhone(phone);
			//注册时调用
			if ("1".equals(isRegist)){
				
				if (null != member) {
					// 该手机号已注册
					returnMsg.setCode(ExceptionEnum.phone_is_register.getIndex());
					returnMsg.setStatus(false);
					returnMsg.setMsg(ExceptionEnum.phone_is_register.getValue());
					return returnMsg;
				}
			//忘记密码是调用	
			} else if("2".equals(isRegist)) {
				// 该手机号未注册
				if (null == member){
					returnMsg.setCode(ExceptionEnum.phone_no_register.getIndex());
					returnMsg.setStatus(false);
					returnMsg.setMsg(ExceptionEnum.phone_no_register.getValue());
					return returnMsg;
				}
			}
				

			ValidateCodeInfo codeInfo = Constants.codeMap.get(phone);
			if (null != codeInfo) {
				long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
				if (time < 120000) {
					// 验证码未超过120秒不能重新获取
					returnMsg.setCode(ExceptionEnum.validate_code_noReady_send.getIndex());
					returnMsg.setStatus(false);
					returnMsg.setMsg(ExceptionEnum.validate_code_noReady_send.getValue());
					return returnMsg;
				}
			}
			String validateCode = RandomUtil.getRandom(4);
			boolean tempflag = memMemberService.sendValidateCode(phone, email, sendType, validateCode);
			if (!tempflag) {
				returnMsg.setStatus(false);
				returnMsg.setMsg(requestContext.getMessage("user.sendCode.failre"));
				return returnMsg;
			}
			ValidateCodeInfo validateCodeInfo = new ValidateCodeInfo();
			validateCodeInfo.setCreateDate(DateUtil.dateToDateString(new Date()));
			validateCodeInfo.setValidateCode(validateCode);
			Constants.codeMap.put(phone, validateCodeInfo);
			returnMsg.setMsg(requestContext.getMessage("user.sendCode.success"));
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.sendCode.failre"));
			ex.printStackTrace();
		}
		return returnMsg;
	}

	/**
	 * 手机注册信息
	 * 
	 * @param memberDto
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ReturnMsg<Object> saveMember(@ModelAttribute MemberDto memberDto, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		RequestContext requestContext = new RequestContext(request);
		try {
			String fromPageValidateCode = request.getParameter("validateCode");
			if (StringUtils.isEmpty( memberDto.getPhone()) || StringUtils.isEmpty(memberDto.getUserName()) || StringUtils.isEmpty(memberDto.getPassword()) ||StringUtils.isEmpty(fromPageValidateCode)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member =memMemberService.memberLogin(memberDto.getUserName(), memberDto.getPhone());
			//MemMember member = memMemberService.getMemMemberByUsername(memberDto.getUserName());
			if (null != member) {
				// 该用户名已存在
				returnMsg.setCode(ExceptionEnum.member_exist.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.member_exist.getValue());
				return returnMsg;
			}
			Map<String, Object> map = validateCode(memberDto.getPhone(), fromPageValidateCode);
			Boolean errorFlag = (Boolean) map.get("errorFlag");
			if (errorFlag == true) {
				return (ReturnMsg<Object>) map.get("returnMsg");
			}
			//memMemberService.saveMember(memberDto);
			memMemberService.saveAppMember(memberDto);
			returnMsg.setMsg(requestContext.getMessage("user.regist.okregist"));
			returnMsg.setStatus(true);
			returnMsg.setData(member);
		} catch (Exception ex) {	
			ex.printStackTrace();
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.regist.failregist"));
			return returnMsg;
		}
		return returnMsg;
	}

	/**
	 * 验证验证码是否填写正确
	 * 
	 * @param phone
	 * @param formCode
	 * @return
	 */
	private Map<String, Object> validateCode(String phone, String formCode) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ValidateCodeInfo codeInfo = Constants.codeMap.get(phone);
		String fromPageValidateCode = formCode;
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		if (null == codeInfo || null == codeInfo.getValidateCode() || null == fromPageValidateCode) {
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}
		long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
		if (time > 120000) {
			// 验证码超时请重新获取
			returnMsg.setCode(ExceptionEnum.validate_code_timeout.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_timeout.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}

		if (!codeInfo.getValidateCode().equals(fromPageValidateCode)) {
			// 验证码验证不通过
			returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
			map.put("errorFlag", true);
			map.put("returnMsg", returnMsg);
			return map;
		}
		map.put("errorFlag", false);
		return map;
	}

	/**
	 * wap 注册
	 * 
	 * @param memberDto
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/register")
	public String wapSaveMember(@ModelAttribute MemberDto memberDto, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session) {
		RequestContext requestContext = new RequestContext(request);
		try {
			ValidateCodeInfo validateCodeInfo = (ValidateCodeInfo) request.getSession().getAttribute("validateCodeInfo");
			String validateCode = validateCodeInfo == null ? null : validateCodeInfo.getValidateCode();
			String fromPageValidateCode = request.getParameter("validateCode");
			String captcha = request.getParameter("captcha");
			String password_ = request.getParameter("password1");
			String rMemberId = request.getParameter("rMemberId");
			String productId = request.getParameter("productId");
			if (Utils.isObjectsBlank(validateCode, fromPageValidateCode, captcha, password_, memberDto, memberDto.getUserName(), memberDto.getPhone())) {
				request.setAttribute("error", requestContext.getMessage("user.regist.validcodeerror"));
				return "/mobile/registered";
			}
			if (!validateCode.equals(fromPageValidateCode) || !captcha.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				request.setAttribute("error", requestContext.getMessage("user.regist.validcodeerror"));
				return "/mobile/registered";
			}
			request.getSession().removeAttribute("validateCode");
			memMemberService.saveMember(memberDto);
			
			// 保存注册的用户信息到sessions
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
			session.setAttribute(MemMember.PRINCIPAL_ATTRIBUTE_NAME, new Principal(member.getId(), member.getUsername()));
			session.setAttribute("member", member);
			if (Utils.isNotBlank(productId)||Utils.isNotBlank(rMemberId)) {
//				cartService.addCart(productId, 1, memberDto.getUserName(),"APP");
				int rs =recommendProductLogService.checkOnly(member.getId(), rMemberId, productId);
				if(rs<=0){
//					recommendProductLogService.addRecommendProductLog(member.getId(), rMemberId, productId);
				}
			}
			request.getSession().removeAttribute("validateCodeInfo");
			request.getSession().removeAttribute(CAPTCHA_SEQ);
		} catch (Exception e) {
			logger.error("注册出错", e);
			request.setAttribute("error", requestContext.getMessage("user.regist.failregist"));
			return "/mobile/registered";
		}
		return registerView;
	}
	/**
	 * 登录
	 * @param userName
	 * @param password
	 * @param request
	 * @param response
	 * @param modelMap
	 * @param session
	 * @return
	 */
	@RequestMapping("/login")
	public String wapSubmitMember(String userName, String password, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap, HttpSession session){
		RequestContext requestContext = new RequestContext(request);
		String rMemberId = request.getParameter("rMemberId");
		String productId = request.getParameter("productId");
		if (Utils.isObjectsBlank(userName, password)) {
			request.setAttribute("error", requestContext.getMessage("user.regist.validcodeerror"));
			return "/mobile/login";
		}
		try {
			MemMember member = memMemberService.memberLogin(userName, userName);
			if(null==member){
				request.setAttribute("error", requestContext.getMessage("user.username.inexistence"));
				return "/mobile/login";
			}
			if (password.equalsIgnoreCase(member.getPassword())) {
				request.setAttribute("error", requestContext.getMessage("user.password.error"));
				return "/mobile/login";
			}
			
			MemberLoginStatus memberLoginStatus = memMemberService.findByMemberId(member.getId());
			if (null == memberLoginStatus) {
				memberLoginStatus = new MemberLoginStatus();
				memberLoginStatus.setId(UUIDGenerator.getUUID());
				memberLoginStatus.setCreateTime(new Date());
				memberLoginStatus.setDelFlag("0");
				memberLoginStatus.setIpAddress(request.getRemoteAddr());
				memberLoginStatus.setSessionId(request.getSession().getId());
				memberLoginStatus.setMemberId(member.getId());
				memMemberService.saveMemberLoginStatus(memberLoginStatus);
			}

			MemberLoginStatusDto loginDto = new MemberLoginStatusDto();
			String ignore[] = new String[] { "id", "ipAddress", "updateTime", "delFlag", "createTime" };
			BeanUtils.copyProperties(memberLoginStatus, loginDto, ignore);
			loginDto.setCreateTime(DateUtil.dateToDateString(new Date()));
			
			if (Utils.isNotBlank(productId)||Utils.isNotBlank(rMemberId)) {
//				cartService.addCart(productId, 1, member.getUsername(),"APP");
				int rs =recommendProductLogService.checkOnly(member.getId(), rMemberId, productId);
				if(rs<=0){
//					recommendProductLogService.addRecommendProductLog(member.getId(), rMemberId, productId);
				}
			}
			
		} catch (Exception e) {
			logger.error("登录出错", e);
			request.setAttribute("error", requestContext.getMessage("user.regist.failregist"));
			return loginView;
		}
		
		return loginView;
			
	}
	
	
	

	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/submit")
	@ResponseBody
	public ReturnMsg<MemberLoginStatusDto> submit(String userName, String password, HttpServletRequest request) {
		ReturnMsg<MemberLoginStatusDto> returnMsg = new ReturnMsg<MemberLoginStatusDto>();
		if (Utils.isObjectsBlank(userName, password)) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		// 从后台代码获取国际化信息
		RequestContext requestContext = new RequestContext(request);
		try {
			//MemMember member = memMemberService.getMemMemberByUsername(userName);
			MemMember member = memMemberService.memberLogin(userName, userName);
			if (null == member) {
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(requestContext.getMessage("user.username.inexistence"));
				return returnMsg;
			}
			logger.info("password:"+password);
			logger.info("member.getPassword():"+member.getPassword());
			/*if (!DigestUtil.MD5(password).equalsIgnoreCase(member.getPassword())) {
				returnMsg.setCode(ExceptionEnum.pass_error.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(requestContext.getMessage("user.password.error"));
				return returnMsg;
			}*/
			
			if (!password.equalsIgnoreCase(member.getPassword())) {
					returnMsg.setCode(ExceptionEnum.pass_error.getIndex());
					returnMsg.setStatus(false);
					returnMsg.setMsg(requestContext.getMessage("user.password.error"));
					return returnMsg;
			}
			
			//更新SESSIONID
			String sessionId =UUIDGenerator.getUUID(); 
			memMemberService.updateSessionId(userName,sessionId);
			
			MemberLoginStatusDto loginDto = new MemberLoginStatusDto();
			loginDto.setCreateTime(DateUtil.dateToDateString(new Date()));
			loginDto.setSessionId(sessionId);
			loginDto.setMemberId(member.getId());
			loginDto.setApproveFlag(member.getApproveFlag());
			loginDto.setUserName(member.getUsername());
			loginDto.setPhone(member.getPhone());
			
			returnMsg.setStatus(true);
			returnMsg.setMsg(requestContext.getMessage("user.login.success"));
			returnMsg.setData(loginDto);
		} catch (Exception ex) {
			returnMsg.setCode("1");
			returnMsg.setStatus(false);
			returnMsg.setMsg(requestContext.getMessage("user.password.error"));
			logger.error("手机APP登陆出错", ex);
			return returnMsg;
		}
		return returnMsg;

	}
	
	
	/**
	 * 退出登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/loginOff")
	@ResponseBody
	public ReturnMsg<Object> loginOff(HttpServletRequest request){
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		logger.info("============用户退出登录接口loginOff===========" );
		try {
			HttpSession session =request.getSession();
			session.invalidate();
		}catch (Exception e) {
			logger.error("退出登录出错", e);
			return returnMsg;
		}
			logger.info("退出登录成功");
			returnMsg.setStatus(true);
			return returnMsg;
		}
	
	

	/**
	 * 忘记密码
	 * @param userName
	 * @param password
	 * @param password1
	 * @param phone
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword")
	@ResponseBody
	public ReturnMsg<Object> forgetPassword(String password, String password1, String phone, String validateCode, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		// 从后台代码获取国际化信息
		RequestContext requestContext = new RequestContext(request);
		try {
			MemMember member = memMemberService.getMemberByPhone(phone);
			if (null == member) {
				returnMsg.setCode(ExceptionEnum.phone_no_register.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.phone_no_register.getValue());
				return returnMsg;
			}
			Map<String, Object> map = validateCode(phone, validateCode);
			Boolean errorFlag = (Boolean) map.get("errorFlag");
			if (errorFlag) {
				return (ReturnMsg<Object>) map.get("returnMsg");
			}
			
		/*	if (password.equalsIgnoreCase(password1)) {
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}*/
			//memMemberService.updatePassword(DigestUtil.MD5(password), member.getUsername());
			memMemberService.updatePassword(password.toLowerCase(), member.getUsername());
			returnMsg.setStatus(true);
			returnMsg.setMsg(requestContext.getMessage("user.updatepassword.ok"));
		} catch (Exception ex) {
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("手机忘记密码方法forgetPassword出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}

	/**
	 * 
	 * @Title: fileUpload
	 * @author sl 文件上传
	 */
	@ResponseBody
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public ReturnMsg<String> fileUpload(MultipartFile myFile) {
		logger.debug("文件上传开始mobile" + myFile);
		logger.info("文件上传开始mobile" + myFile);
		ReturnMsg<String> returnMsg = new ReturnMsg<String>();
		String filePath = FileUploadUtil.upload(myFile);
		logger.info("filePath" + filePath);
		if (filePath == null) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
			return returnMsg;
		}
		returnMsg.setData(filePath);
		return returnMsg;
	}
	
	}

		

