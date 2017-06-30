package com.uib.union.enums;

/**
 * 手机控件支付  返回码牧举
 * @author kevin
 *
 */
public enum MobileReturnTypeEnum {
	
	/**
	 * 0-成功
	 */
	SUCCESS("0"),
	
	/**
	 * 1-失败
	 */
	FAILURE("1"),
	
	/**
	 * 2-订单重复
	 */
	ORDER_REPEAT("2"),
	
	/**
	 * 3-系统内部错误
	 */
	ERROR("3");
	
	private String desc;
	
	
	private MobileReturnTypeEnum(String desc){
		this.desc = desc;
	}


	public String getDesc() {
		return desc;
	}
	
	
	
}
