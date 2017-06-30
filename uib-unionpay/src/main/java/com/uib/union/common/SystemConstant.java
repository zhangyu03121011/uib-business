package com.uib.union.common;

/**
 * 
 * @author kevin
 *
 */
public class SystemConstant {
	
	
	public final static String  version = "5.0.0";
	
	/**
	 * 字符编码
	 */
	public final static String encoding = "UTF-8";
	
	/**
	 * 交易币种人民币
	 */
	public final static String CURRENCYCODE_RMB = "156";
	
	
	/**
	 * 渠道类型 07-互联网渠道
	 */
	public final static String CHANNEL_TYPE_NET = "07";
	
	/**
	 * 业务类型 000201 B2C网关支付
	 */
	public final static String BIZTYPE_B2C = "000201";
	
	/**
	 * 签名方法 01 RSA
	 */
	public final static String SIGN_METHOD_RSA = "01";
	
	/**  
	 * 交易渠道 0:
	 */
	public final static String PAY_TYPE_ANYZPAY = "0";
	
	/**  
	 * 交易渠道    1:银联在线
	 */
	public final static String PAY_TYPE_UPOP = "1";
	
	/**  
	 * 交易渠道   2:银联全渠道支付
	 */
	public final static String PAY_TYPE_UNIONPAY = "1";
}
