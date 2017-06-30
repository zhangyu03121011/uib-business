package com.uib.common.utils;

public class HmacSign {
	
	
	private static String rootKey = "uibRoot";
	
	public static String sign(String context,String signKey){
		String signStr =DigestUtil.MD5(rootKey + context + signKey) + DigestUtil.MD5(  context + signKey);
		return signStr;
	}
	
	

}
