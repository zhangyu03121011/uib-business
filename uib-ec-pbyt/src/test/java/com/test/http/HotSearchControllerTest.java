package com.test.http;

import java.util.HashMap;
import java.util.Map;

import com.easypay.common.web.HttpCall;

public class HotSearchControllerTest {

	private static String url = "http://localhost:8080/f/mobile/hotSearch";
	
	public static void getHotSearchList(){
		Map<String, String> params = new HashMap<>();
		params.put("count", "6");
		System.out.println(HttpCall.post(url + "/getHotSearchList", params).getContent());
	} 
	
	public static void main(String[] args) {
		getHotSearchList();
	}
	
}
