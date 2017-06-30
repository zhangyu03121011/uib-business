/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.uib.mobile.constants;

import java.util.HashMap;
import java.util.Map;

import com.uib.member.dto.ValidateCodeInfo;

/**
 * @ClassName: Constants
 * @author sl
 * @date 2015年9月15日 下午2:27:32
 */
public class Constants {
	/**
	 * 短信验证码常量类
	 */
	public static Map<String, ValidateCodeInfo> codeMap = new HashMap<String, ValidateCodeInfo>();

	/** app提示语接口 **/

	public static String SUCCEED_SUBMIT = "提交成功";
	
	/**
	 * 修改支付手机号码标识
	 */
	public static String UPDATE_LOGINPHONE_FLAG = "1";
	
	/**
	 * 修改支付手机号码标识
	 */
	public static String UPDATE_PAYPHONE_FLAG = "2";
}
