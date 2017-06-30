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
 * @ClassName: ProductControllerTest
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author sl
 * @date 2015年10月26日 上午10:27:08
 */
public class ProductControllerTest {
	static String url = "http://localhost:8080/f/mobile/";

	public static void findByCategory() {
		Map<String, String> params = new HashMap<>();
		params.put("categoryId", "2e263f6881ce4979a4574638d4ca994b");
		params.put("sortord", "6");
		params.put("productName", "");
		params.put("begin", "10");
		params.put("end", "20");
		System.out.println(HttpCall.post(url+"product/findByCategory", params).getContent());

	}
	
	public static void queryCurUserCouponList(){
		Map<String, String> params = new HashMap<>();
		params.put("sessionId", "bd1fe2273bcb444cbc9abd1d843d2098");
		params.put("states", "2");
		System.out.println(HttpCall.post(url+"/member/coupon/myCoupon", params).getContent());
	}
	
	public static void listDefaultAddress(){
		Map<String, String> params = new HashMap<>();
		params.put("sessionId", "bd1fe2273bcb444cbc9abd1d843d2098");
		System.out.println(HttpCall.post(url+"/member/address/listDefaultAddress", params).getContent());
	}
	
	public static void addOrder(){
		Map<String, String> params = new HashMap<>();
		params.put("sessionId", "bd1fe2273bcb444cbc9abd1d843d2098");
		params.put("receiverId", "");
		params.put("consignee", "张三");
		params.put("areaName", "甘肃省-定西市-安定区");
		params.put("address", "安定街126号");
		params.put("phone", "18520125458");
		params.put("area", "7,106,10601");
		params.put("pid", "19a400c4076e42c3b1825005047b353f");
		params.put("cartId", "8367e793e4774e918ef5cc723aa81fcc");
		params.put("paymentMethod", "1");
		
		System.out.println(HttpCall.post(url+"/member/order/add", params).getContent());
	}

	public static void main(String[] args) {
		//findByCategory();
		//queryCurUserCouponList();
		listDefaultAddress();
		addOrder();
	}
}
