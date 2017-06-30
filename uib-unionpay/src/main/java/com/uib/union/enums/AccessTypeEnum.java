package com.uib.union.enums;

/**
 * 银联商户接入类型 
 * @author kevin
 *
 */
public enum AccessTypeEnum {
	
	/**
	 * 0-商户
	 */
	MERCHANT("0"),
	
	/**
	 * 1-收单
	 */
	CLOSED_ORDER("1"),
	
	/**
	 * 2-平台商户
	 */
	PLATFORM_MERCHANT("2");
	
	
	
	private String desc;
	
	
	
	public String getDesc() {
		return desc;
	}



	private AccessTypeEnum(String desc){
		this.desc = desc;
	}
}
