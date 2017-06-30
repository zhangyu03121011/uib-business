package com.uib.common.utils;

import com.uib.common.web.HttpCall;
import com.uib.common.web.HttpCallResult;

/**
 * 发送短信API
 * @author kevin
 *
 */
public class PhoneMessageUtil {
	
	/**
	 * 发送手机短信
	 * @param name
	 * @param pass
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static boolean sendPhoneMessage(String loginname,String pass,String URL,String content, String phoneList) throws Exception{
		String requestUrl = URL + "&loginname=" + loginname + "&password=" + pass + "&p="+phoneList + "&c="+content;
		HttpCallResult  callResult =	HttpCall.get(requestUrl);
		if (callResult.getStatusCode() == 200) {
			System.out.println("callResult.getContent() ==" + callResult.getContent()); 
		}
		return false;
	}
}
