package com.uib.common.utils;

import java.io.UnsupportedEncodingException;

import com.uib.common.utils.DigestUtil;

/**
 * 签名
 * @author kevin
 *
 */
public class HmacSHASign {
	
	/**
	 * 签名
	 * @param tranData
	 * @param hmd5Password
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String hmacSHASign(String tranData,String hmd5Password) throws UnsupportedEncodingException{
		String tradeData = new String( (hmd5Password+tranData).getBytes("ISO-8859-1"),"UTF-8");
		String signData =	DigestUtil.MD5(tradeData);
		return signData;
	}
	
	
	
	
}
