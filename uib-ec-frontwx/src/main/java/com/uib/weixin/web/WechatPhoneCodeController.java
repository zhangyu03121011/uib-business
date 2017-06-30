package com.uib.weixin.web;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.utils.DateUtil;
import com.uib.common.utils.RandomUtil;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;
import com.uib.weixin.util.UserSession;
import com.uib.weixin.util.WxPhoneMessageUtil;

@Controller
@RequestMapping("/wechat/phone")
public class WechatPhoneCodeController {
	
	private Logger logger = LoggerFactory.getLogger(WechatPhoneCodeController.class);

	@Autowired
	private WxPhoneMessageUtil wxPhoneMessageUtil;

	/**
	 * 发送手机验证码
	 * 
	 * @param phone 手机号
	 * @return
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public ReturnMsg<Object> sendCode(String phone, HttpServletRequest request) {
		logger.info("发生验证码入参phone=" + phone);
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if(Utils.isObjectsBlank(phone)){
				returnMsg.setStatus(false);
				return returnMsg;
			}
			ValidateCodeInfo codeInfo = (ValidateCodeInfo) UserSession.getSession(WxConstant.wx_mess_code_1);
			if (null != codeInfo) {
				long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
				// 验证码未超过90秒不能重新获取
				if (time < 90000) {
					logger.info("验证码未超过90秒不能重新获取【phone=" + phone + "】");
					returnMsg.setStatus(false);
					return returnMsg;
				}
			}
			String messCode = RandomUtil.getRandom(4);
			wxPhoneMessageUtil.sendMessCode(phone, messCode,WxConstant.wx_mess_code_1);
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			logger.error("发送手机验证码失败", ex);
		}
		return returnMsg;
	}
	
	
	/**
	 * 重置手机号码获取手机验证码
	 * @param request
	 * @return
	 */
	@RequestMapping("/sendCode4ResetPhone")
	@ResponseBody
	public ReturnMsg<Object> sendCode4ResetPhone(HttpServletRequest request) {
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			//获取当前用户userName
			String phone = (String)UserSession.getSession(WxConstant.wx_user_phone);
			logger.info("通过session获取phone=" + phone);
			// 用户未登录
			if (null == phone){
				returnMsg.setStatus(false);
				return returnMsg;
			}

			ValidateCodeInfo codeInfo = (ValidateCodeInfo) UserSession.getSession(WxConstant.wx_mess_code_2);
			if (null != codeInfo) {
				long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
				// 验证码未超过90秒不能重新获取
				if (time < 90000) {
					logger.info("验证码未超过90秒不能重新获取【phone=" + phone + "】");
					returnMsg.setStatus(false);
					return returnMsg;
				}
			}
			String messCode = RandomUtil.getRandom(4);
			wxPhoneMessageUtil.sendMessCode(phone, messCode,WxConstant.wx_mess_code_2);
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			logger.error("重置手机号码获取手机验证码异常", ex);
		}
		return returnMsg;
	}
	
	/**
	 * 校验验证码
	 * @param messType
	 * @param type mess_code_1：正常输入手机号码验证码校验；mess_code_2：给用户登录的手机号码验证码校验	
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkMessCode")
	@ResponseBody
	public ReturnMsg<Object> checkMessCode(String messCode,String type, HttpServletRequest request){
		return WxPhoneMessageUtil.checkMessCode(messCode, type);
	}
	
}
