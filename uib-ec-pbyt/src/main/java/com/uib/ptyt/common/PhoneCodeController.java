package com.uib.ptyt.common;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.DateUtil;
import com.uib.common.utils.RandomUtil;
import com.uib.common.utils.StringUtils;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.weixin.util.UserSession;

@Controller
@RequestMapping("/phone")
public class PhoneCodeController {
	
	private Logger logger = LoggerFactory.getLogger(PhoneCodeController.class);

	/**
	 * 发送手机验证码
	 * 
	 * @param phone 手机号
	 * @return
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public ReturnMsg<Object> sendCode(String phone,String type, HttpServletRequest request) {
		logger.info("发生验证码入参phone=" + phone);
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		try {
			if(StringUtils.isEmpty(phone)){
				returnMsg.setStatus(false);
				return returnMsg;
			}
			
			ValidateCodeInfo codeInfo = (ValidateCodeInfo) UserSession.getSession(type);
			if (null != codeInfo) {
				long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
				// 验证码未超过120秒不能重新获取
				if (time < 120000) {
					logger.info("验证码未超过120秒不能重新获取【phone=" + phone + "】");
					returnMsg.setStatus(false);
					returnMsg.setCode(ExceptionEnum.validate_code_noReady_send.getIndex());
					returnMsg.setMsg(ExceptionEnum.validate_code_noReady_send.getValue());
					return returnMsg;
				}
			}
			String messCode = RandomUtil.getRandom(6);
			PbytPhoneMessageUtil.sendMessCode(phone, messCode,type);
			returnMsg.setStatus(true);
		} catch (Exception ex) {
			returnMsg.setStatus(false);
			logger.error("发送手机验证码失败", ex);
		}
		return returnMsg;
	}
	
	
	
	
	/**
	 * 校验验证码
	 * @param type
	 * @param type mess_code_1：正常输入手机号码验证码校验；mess_code_2：给用户登录的手机号码验证码校验	
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/checkMessCode")
	@ResponseBody
	public ReturnMsg<Object> checkMessCode(String messCode,String type, HttpServletRequest request){
		return PbytPhoneMessageUtil.checkMessCode(messCode, type);
	}
	
	/**
	 * 清除发送验证码时所形成的缓存
	 * @return
	 */
	@RequestMapping("/cleanSession")
	@ResponseBody
	public ReturnMsg<Object> cleanSession(String type){
		ReturnMsg<Object> result=new ReturnMsg<Object>();
		logger.info("清除发送验证码时所形成的缓存入参：type=" + type);
		try{
			UserSession.setSession(type,null);
			result.setStatus(true);
		}catch(Exception e){
			result.setStatus(false);
			result.setCode(ExceptionEnum.system_error.getIndex());
			result.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("清除缓存出错：" + e);	
		}
		return result;
	}
}
