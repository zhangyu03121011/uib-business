package com.uib.weixin.web;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.uib.cart.service.CartService;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.Des;
import com.uib.common.utils.DigestUtil;
import com.uib.common.utils.FileUploadUtil;
import com.uib.common.utils.StringUtils;
import com.uib.member.dto.MemberDto;
import com.uib.member.entity.MemMember;
import com.uib.member.service.MemMemberService;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.mobile.service.RecommendProductLogService;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WxPhoneMessageUtil;
/**
 * 手机
 * 
 * @author kevin
 * 
 */
@Controller
@RequestMapping("/wechat/user")
public class WechatUserController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemMemberService memMemberService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private RecommendProductLogService recommendProductLogService;
	
	@Autowired
	private WxPhoneMessageUtil wxPhoneMessageUtil;
	
	private static final String CAPTCHA_SEQ = "captcha_seq";

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
	public ReturnMsg<Object> saveMember(@ModelAttribute MemberDto memberDto, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			String fromPageValidateCode = request.getParameter("validateCode");
			String rMemberId = request.getParameter("rmemberId");
			String productId = request.getParameter("productId");
			if (StringUtils.isEmpty( memberDto.getPhone()) || StringUtils.isEmpty(memberDto.getUserName()) || StringUtils.isEmpty(memberDto.getPassword()) ||StringUtils.isEmpty(fromPageValidateCode)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member =memMemberService.memberLogin(memberDto.getUserName(), memberDto.getPhone());
			if (null != member) {
				// 该用户名已存在
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.member_exist.getIndex());
				returnMsg.setMsg(ExceptionEnum.member_exist.getValue());
				return returnMsg;
			}
			ReturnMsg<Object> checkResult = WxPhoneMessageUtil.checkMessCode(fromPageValidateCode,WxConstant.wx_mess_code_1);
			if (!checkResult.isStatus()) {
				return checkResult;
			}
			//des解密
			Des desObj = new Des();
			memberDto.setPassword(desObj.strDec(memberDto.getPassword(), "1", "2", "3"));
			memMemberService.saveMember(memberDto);
			if (null != productId && !productId.equals("") && !productId.equals("undefined")) {
				cartService.addCart(productId, 1, memberDto.getUserName(),"WX");
				if(null != rMemberId && !rMemberId.equals("") && !rMemberId.equals("undefined")){
					int rs =recommendProductLogService.checkOnly(memberDto.getId(), rMemberId, productId);
					if(rs<=0){
						recommendProductLogService.addRecommendProductLog(memberDto.getId(), rMemberId, productId);
					}
				}
				
			}
			returnMsg.setStatus(true);
			returnMsg.setData(member);
			UserSession.setSession(WxConstant.wx_user_name, memberDto.getUserName());
		} catch (Exception ex) {	
			ex.printStackTrace();
			logger.error("用户注册失败", ex);
			returnMsg.setStatus(false);
			returnMsg.setMsg(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			return returnMsg;
		}
		return returnMsg;
	}

	/**
	 * 登录提交
	 */
	@RequestMapping(value = "/login")
	@ResponseBody
	public ReturnMsg<Object> login(String userName, String password, HttpServletRequest request) {
		String rMemberId = request.getParameter("rmemberId");
		String productId = request.getParameter("productId");
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		logger.info("用户登录入参userName=" + userName);
		try {
			if (Utils.isObjectsBlank(userName, password)) {
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.memberLogin(userName, userName);
			if(null==member){
				returnMsg.setCode(ExceptionEnum.member_not_exist.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.member_not_exist.getValue());
				return returnMsg;
			}
			//des解密
			Des desObj = new Des();
			password = desObj.strDec(password, "1", "2", "3");
			if (!DigestUtil.MD5(password).equalsIgnoreCase(member.getPassword())) {
				returnMsg.setCode(ExceptionEnum.pass_error.getIndex());
				returnMsg.setStatus(false);
				returnMsg.setMsg(ExceptionEnum.pass_error.getValue());
				return returnMsg;
			}
			
			if (null != productId && !productId.equals("") && !productId.equals("undefined")) {
				cartService.addCart(productId, 1, member.getUsername(),"WX");
				if(null != rMemberId && !rMemberId.equals("") && !rMemberId.equals("undefined")){
					int rs =recommendProductLogService.checkOnly(member.getId(), rMemberId, productId);
					if(rs<=0){
						recommendProductLogService.addRecommendProductLog(member.getId(), rMemberId, productId);
					}
				}
			}
			
			UserSession.setSession(WxConstant.wx_user_name, member.getUsername());
			UserSession.setSession(WxConstant.wx_user_id, member.getId());
			UserSession.setSession(WxConstant.wx_user_phone, member.getPhone());
			UserSession.setSession(WxConstant.wx_member_info, member);
		} catch (Exception e) {
			logger.error("登录出错", e);
			return returnMsg;
		}
		logger.info("用户登录入成功");
		returnMsg.setStatus(true);
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
	
	
	@RequestMapping(value = "/queryMemberByIdCard")
	@ResponseBody
	public Object queryMemberByIdCard(String idCard){
		logger.info("============查询用户身份是否唯一接口queryMemberByIdCard===========" + idCard);
		try {
			Object obj = memMemberService.queryMemberByIdCard(idCard);
			logger.info("用户身份是否唯一" + obj);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "0";
		
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
		try {
			if (Utils.isObjectsBlank(password, password1,phone,validateCode)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setCode(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			MemMember member = memMemberService.getMemberByPhone(phone);
			if (null == member) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.phone_no_register.getIndex());
				returnMsg.setMsg(ExceptionEnum.phone_no_register.getValue());
				return returnMsg;
			}
			
			ReturnMsg<Object> checkResult = WxPhoneMessageUtil.checkMessCode(validateCode,WxConstant.wx_mess_code_1);
			if (!checkResult.isStatus()) {
				return checkResult;
			}
			
			if (!password.equals(password1)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}
			memMemberService.updatePassword(DigestUtil.MD5(password), member.getUsername());
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("重置密码出错", ex);
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
	
	@RequestMapping(value = "/toLogin")
	@ResponseBody
	public ModelAndView toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception {
		logger.info("登录跳转");
		return new ModelAndView("redirect:/page/weixin/login.html");
	}
	
	/**
	 * 校验微信用户是否登录
	 * 没有登录返回0，否则返回用户名
	 * @return
	 */
	@RequestMapping(value="/ticket/state",method=RequestMethod.GET)
	@ResponseBody
	public String getTicketState(){
		return (null == UserSession.getSession(WxConstant.wx_user_name)) ? "0" : (String)UserSession.getSession(WxConstant.wx_user_name);
	}
	
	
	
	/**
	 * 重置手机号码
	 * @param sessionId
	 * @param phone
	 * @param validateCode
	 * @param phoneFlag
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/resetPhone")
	@ResponseBody
	public ReturnMsg<Object> resetPhone(String firstMessCode, String secondMessCode, String phone, HttpServletRequest request) {
		logger.info("重置手机号码入参firstMessCode=" + firstMessCode + ",secondMessCode=" + secondMessCode + ",phone=" + phone);
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (Utils.isObjectsBlank(firstMessCode,secondMessCode,phone)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setMsg(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			
			String userId = UserSession.getSession(WxConstant.wx_user_id).toString();
			if (Utils.isObjectsBlank(userId)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.not_login.getIndex());
				returnMsg.setMsg(ExceptionEnum.not_login.getValue());
				return returnMsg;
			}
			
			returnMsg = WxPhoneMessageUtil.checkMessCode(firstMessCode, WxConstant.wx_mess_code_2);
			if (!returnMsg.isStatus()) {
				return returnMsg;
			}
			
			returnMsg = WxPhoneMessageUtil.checkMessCode(secondMessCode, WxConstant.wx_mess_code_1);
			if (!returnMsg.isStatus()) {
				return returnMsg;
			}
			
			HashMap<String, String> memMemberMap = new HashMap<String, String>();
			memMemberMap.put("phone", phone);
			memMemberMap.put("id", userId);
			memMemberService.updateMemberInfoById(memMemberMap);
			//更新手机号码到session
			UserSession.setSession(WxConstant.wx_user_phone, phone);
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.member_updatePhone_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.member_updatePhone_error.getValue());
			logger.error("重置手机号码异常", ex);  
		}
		return returnMsg;
	}
	/**
	 * 忘记支付密码
	 * @param validateimg
	 * @param password
	 * @param password1
	 * @param validateCode
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/forgetPayPassword")
	@ResponseBody
	public ReturnMsg<Object> forgetPayPassword(String validateimg,String password, String password1,String validateCode, HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if (Utils.isObjectsBlank(validateimg,password, password1,validateCode)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.param_not_null.getIndex());
				returnMsg.setCode(ExceptionEnum.param_not_null.getValue());
				return returnMsg;
			}
			if (!validateimg.equals(request.getSession().getAttribute(CAPTCHA_SEQ))) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
				return returnMsg;
			}
			ReturnMsg<Object> checkResult = WxPhoneMessageUtil.checkMessCode(validateCode,WxConstant.wx_mess_code_2);
			if (!checkResult.isStatus()) {
				return checkResult;
			}
			
			if (!password.equals(password1)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.password_different.getIndex());
				returnMsg.setMsg(ExceptionEnum.password_different.getValue());
				return returnMsg;
			}
			String memberId=(String) UserSession.getSession(WxConstant.wx_user_id);
			memMemberService.updatePayPassword(memberId, DigestUtil.MD5(password));
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("重置支付密码出错", ex);
			return returnMsg;
		}
		return returnMsg;
	}           
}
