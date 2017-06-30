package com.test.http;

import java.util.HashMap;

import com.easypay.common.web.HttpCall;
import com.easypay.common.web.HttpCallResult;

public class TestUser {
	
	/**
	 * 测试验证码方法
	 * @param phone
	 * @param sendType
	 */
	private void testValidateCode(){
		HashMap<String,String> map = new HashMap<String,String> ();
		map.put("phone", "15986628929");
		map.put("sendType", "1");
		map.put("isRegist", "1");
		HttpCallResult callResult=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/user/validateCode", map);
		System.out.println(callResult.getContent());
	}
	
	
	/**
	 * 测试忘记密码
	 */
	private void testForgetPassword(){
		HashMap<String,String> map = new HashMap<String,String> ();
		map.put("password", "12345678");
		map.put("password1", "12345678");
		map.put("phone", "15986628929");
		map.put("validateCode", "7178");
		
		
		HttpCallResult callResult=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/user/forgetPassword", map);
		System.out.println(callResult.getContent());
	}
	
	/**
	 * 测试修改密码
	 */
	private void testUpdatePassword(){
		HashMap<String,String> map = new HashMap<String,String> ();
		map.put("userName", "kevin_001");
		map.put("password1", "12345678");
		map.put("password", "12345678");
		map.put("oldPassword", "12345678");
		
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult callResult=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/user/updatePassword", map);
		System.out.println(callResult.getContent());
	}
	
	
	public void testSaveMember(){
		HashMap<String,String> map = new HashMap<String,String> ();
		map.put("validateCode", "6080");
		map.put("password", "12345678");
		map.put("userName", "admin888");
		map.put("phone", "15986628929");
		
		HttpCallResult callResult=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/user/save", map);
		System.out.println(callResult.getContent());
	}
	
	/**
	 * 测试查看身份认证
	 */
	public void getApproveByUserName(){
		HashMap<String,String> map = new HashMap<String,String> ();
		map.put("userName", "test1001");
		map.put("sessionId", "B9DF4C15E08289DCD73258CD367C37FD");
		HttpCallResult callResult=	HttpCall.post("http://localhost:9988/ec-frontweb/f/mobile/member/user/getApproveByUserName", map);
		System.out.println(callResult.getContent());
	}
	
	
	public static void main(String []args) throws Exception{
		TestUser t = new TestUser();
		t.getApproveByUserName();
		//t.testValidateCode();
		//t.testSaveMember();
		//t.testForgetPassword();
		//t.testUpdatePassword();
	}

}
