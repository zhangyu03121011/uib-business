package com.uib.common.enums;

/**
 * easypay 支付状态枚举类型
 * @author kevin
 *
 */
public enum EasyPayTranStatEnum {
	//最新状态 订单状态(0:未支付; 1:已支付; 2:失败; 4:已关闭; 5:取消;)
	
	/**
	 * 未支付
	 */
	NOT_PAY("0"),
	
	/**
	 * 已支付
	 */
	ALREADY_PAY("1"),
	
	/**
	 * 支付失败
	 */
	FAILURE_PAY("2"),
	
	/**
	 * 已关闭
	 */
	CLOSE_PAY("4"),
	
	
	/**
	 * 已取消
	 */
	CANCEN_PAY("5"),
	
	/**
	 * 退款成功
	 */
	RETURN_MONEY_SUCCES("6");
	
	
	private String desc;
	
	private EasyPayTranStatEnum(String desc){
		this.desc = desc;
	}
	
	public String getDesc(){
		 return desc;
	}
}
