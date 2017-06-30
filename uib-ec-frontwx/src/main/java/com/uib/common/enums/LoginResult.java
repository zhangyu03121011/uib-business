package com.uib.common.enums;

public enum LoginResult {

	/**
	 * 登录成功
	 */
	SUCCESS,
	
	/**
	 * 用户名不存在
	 */
	USER_NAME_NOT_FOUND,
	
	/**
	 * 密码输入错误
	 */
	PWD_ERROR,
	
	/**
	 * 验证码输入错误
	 */
	CAPTCHA_ERROR;
	
}
