package com.uib.weixin.util;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.DateUtil;
import com.uib.common.utils.PhoneMessageUtil;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.constant.WxConstant;

/**
 * @todo   
 * @author chengw
 * @date   2015年12月23日
 * @time   下午3:09:15
 */
@Component
public class WxPhoneMessageUtil {
	
	@Value("${systemPath}")
	private String systemPath;

	@Value("${send.phone.loginName}")
	private String SEND_PHONE_LOGINNAME;

	@Value("${send.phone.pwd}")
	private String SEND_PHONE_PWD;

	@Value("${send.phone.url}")
	private String SEND_PHONE_URL;

	@Value("${send.phone.sign}")
	private String SEND_PHONE_SIGN;
	
	private static Logger logger = LoggerFactory.getLogger(WxPhoneMessageUtil.class);
	
	/**
	 * 
	 * @param phone		手机号码
	 * @param messCode	验证码
	 */
	public void sendMessCode(String phone, String messCode,String type) throws Exception{
		logger.info("给phone=" + phone + ",发送验证码messCode=" + messCode + ",type=" + type);
		String content = SEND_PHONE_SIGN + "您好,验证码为:" + messCode;
		PhoneMessageUtil.sendPhoneMessage(SEND_PHONE_LOGINNAME,SEND_PHONE_PWD, SEND_PHONE_URL, content, phone);
		//保存手机验证码到session
		ValidateCodeInfo messCodeInfo = new ValidateCodeInfo();
		messCodeInfo.setValidateCode(messCode);
		messCodeInfo.setCreateDate(DateUtil.dateToDateString(new Date()));
		UserSession.setSession(type, messCodeInfo);
	}
	
	/**
	 * 校验验证码
	 * @param messType
	 * @param type mess_code_1：正常输入手机号码验证码校验；mess_code_2：给用户登录的手机号码验证码校验	
	 * @return
	 * @throws Exception
	 */
	public static ReturnMsg<Object> checkMessCode(String messCode,String type){
		logger.info("校验验证码入参：messCode=" + messCode);
		ReturnMsg<Object> returnMsg = new ReturnMsg<Object>();
		returnMsg.setStatus(true);
		try {
			if(Utils.isObjectsBlank(messCode,type)){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
				return returnMsg;
			}
			
			if(!WxConstant.wx_mess_code_1.equals(type) && !WxConstant.wx_mess_code_2.equals(type)){
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.mess_code_type_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.mess_code_type_error.getValue());
				return returnMsg;
			}
			
			ValidateCodeInfo codeInfo = (ValidateCodeInfo) UserSession.getSession(type);
			
			if (null == codeInfo || null == codeInfo.getValidateCode() || null == messCode) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
				return returnMsg;
			}
			long time = DateUtil.compareDateStr(codeInfo.getCreateDate(), DateUtil.dateToDateString(new Date()));
			
			// 验证码超时请重新获取
			if (time > 120000) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.validate_code_timeout.getIndex());
				returnMsg.setMsg(ExceptionEnum.validate_code_timeout.getValue());
				return returnMsg;
			}
			
			// 验证码验证不通过
			if (!codeInfo.getValidateCode().equals(messCode)) {
				returnMsg.setStatus(false);
				returnMsg.setCode(ExceptionEnum.validate_code_error.getIndex());
				returnMsg.setMsg(ExceptionEnum.validate_code_error.getValue());
				return returnMsg;
			}
		} catch (Exception e) {
			returnMsg.setStatus(false);
			returnMsg.setCode(ExceptionEnum.system_error.getIndex());
			returnMsg.setMsg(ExceptionEnum.system_error.getValue());
			logger.error("校验验证码出错：" + e);
		}
		return returnMsg;
	}
}


