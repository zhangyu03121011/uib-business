package com.uib.ptyt.constants;

/**
 * 系统常量类
 */
public interface SysConstant {

	// phone send message info

	String SEND_PHONE_USERID = "lbkj001hy";
	String SEND_PHONE_MD5PASSWORD = "E10ADC3949BA59ABBE56E057F20F883E";
	String SEND_PHONE_URL = "http://61.152.104.249:8035/API/HttpAPISupportCode.aspx?action=sendsms";
	String SEND_PHONE_SIGN = "【联保信息科技集团有限公司】";

	// 申请代理
	String SEND_PHONE_TYPE_PROXY = "1";
	// 提现--身份认证
	String SEND_PHONE_TYPE_AUTHENTICATION = "2";
	//个人中心--身份认证
	String SEND_PHONE_TYPE_PERSONAL="3";
}