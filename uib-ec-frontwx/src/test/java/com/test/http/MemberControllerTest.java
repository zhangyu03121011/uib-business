/**
 * Copyright &copy; 2014-2016  All rights reserved.
 *
 * Licensed under the 深圳嘉宝易汇通科技发展有限公司 License, Version 1.0 (the "License");
 * 
 */
package com.test.http;

import java.util.HashMap;
import java.util.Map;

import com.uib.common.web.HttpCall;

/**
 * @ClassName: memberControllerTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author sl
 * @date 2015年10月26日 上午10:11:08
 */
public class MemberControllerTest {

	//static String url = "http://127.0.0.1/ec-frontweb/f/mobile/user/identityAuthentication";
	static String url = "http://localhost:8080/f/wechat/member/order/update";

//	public static void identityAuthentication() {
//		Map<String, String> params = new HashMap<>();
//		params.put("sessionId", "86F03E3DA84EEF3C4FCFC875FDB362C6");
//		params.put("idCardPositive", "aaaa");
//		params.put("idCardOpposite", "dasdasd");
//		params.put("idCardHand", "dasdasdas");
//		params.put("realName", "asdasdasd");
//		params.put("idCard", "asdasdas");
//		params.put("id", "fe5bf4be30af481fb320f4f41a9b37ca");
//		System.out.println(HttpCall.post(url, params).getContent());
//
//	}
	
	public static void identityAuthentication() {
		Map<String, String> params = new HashMap<>();
		params.put("orderNo", "2015111315330989478497");
		
		System.out.println(HttpCall.post(url, params).getContent());

	}

	public static void main(String[] args) {
		identityAuthentication();

	}
}
