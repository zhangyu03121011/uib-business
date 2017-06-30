package com.uib.union.payment.controller;

import java.util.HashMap;
import java.util.Map;

import com.uib.common.web.HttpCall;
import com.uib.union.common.SystemConstant;
import com.unionpay.acp.sdk.HttpClient;

public class TestMain {
	
	public static void main(String []args){
		try{
			Map<String,String> params = new HashMap<String,String>();
			HttpCall.post("http://220.112.198.36:81/ec-frontweb/f/pay/callBackNotify/async/1437114079576", params);
		}catch (Exception ex){
			ex.printStackTrace();
		}
	
		
	}
		
}
