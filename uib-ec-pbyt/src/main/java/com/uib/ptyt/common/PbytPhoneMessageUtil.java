package com.uib.ptyt.common;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easypay.common.utils.DateUtil;
import com.easypay.common.web.HttpCall;
import com.easypay.common.web.HttpCallResult;
import com.uib.common.enums.ExceptionEnum;
import com.uib.common.utils.PhoneMessageUtil;
import com.uib.member.dto.ValidateCodeInfo;
import com.uib.mobile.dto.ReturnMsg;
import com.uib.ptyt.constants.SysConstant;
import com.uib.serviceUtils.Utils;
import com.uib.weixin.util.UserSession;

@Component
public class PbytPhoneMessageUtil {

	private static Logger logger = LoggerFactory.getLogger(PbytPhoneMessageUtil.class);
	
	/**
	 * 
	 * @param phone		手机号码
	 * @param messCode	验证码
	 */
	public static void sendMessCode(String phone, String messCode,String type) throws Exception{
		logger.info("给phone=" + phone + ",发送验证码messCode=" + messCode + ",type=" + type);
		String content = SysConstant.SEND_PHONE_SIGN + "您好,验证码为:" + messCode;
		PhoneMessageUtil.pbytSendPhoneMessage(SysConstant.SEND_PHONE_URL, SysConstant.SEND_PHONE_USERID, SysConstant.SEND_PHONE_MD5PASSWORD, content, phone);
		//保存手机验证码到session
		ValidateCodeInfo messCodeInfo = new ValidateCodeInfo();
		messCodeInfo.setValidateCode(messCode);
		messCodeInfo.setCreateDate(DateUtil.dateToDateString(new Date()));
		UserSession.setSession(type, messCodeInfo);
	}
	
	/**
	 * 校验验证码
	 * @param messType
	 * @param type 
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
			
			if(!SysConstant.SEND_PHONE_TYPE_PROXY.equals(type)&&!SysConstant.SEND_PHONE_TYPE_AUTHENTICATION.equals(type)&&!SysConstant.SEND_PHONE_TYPE_PERSONAL.equals(type)){
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


