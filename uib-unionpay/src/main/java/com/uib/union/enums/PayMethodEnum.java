package com.uib.union.enums;

/**
 * 支付方式
 * @author kevin
 *
 */
public enum PayMethodEnum {
	
	/**
	 * 爱浓支付渠道 
	 */
	YZ_PAY("0"),
	
	/**
	 * 银联在线支付
	 */
	UNION_ONLINE_PAY("1"),
	
	/**
	 * 全渠道支付
	 */
	UNION_ALL_PAY("2");
	
	private String desc;
	 
	 private PayMethodEnum(String desc) {
	        this.desc = desc;
	    } 
	
	public String getDesc(){
		 return desc;
	}
}
