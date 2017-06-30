package com.uib.union.enums;

/**
 * 退货状态枚举
 * @author kevin
 *
 */
public enum UibRefundStateEnum {
	
	/**
	 * 退货中
	 */
	RETURN_EXCE("0"),
	
	/**
	 * 退货失败
	 */
	RETURN_FAILURE("1"),
	
	/**
	 * 退货成功
	 */
	RETURN_SUCCESS("2"),
	
	/**
	 * 退款成功
	 */
	RETURN_MONEY_SUCCESS("3");
	
	private String desc;
	
	private UibRefundStateEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
