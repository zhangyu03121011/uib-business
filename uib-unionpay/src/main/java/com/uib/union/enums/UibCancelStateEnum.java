package com.uib.union.enums;

/**
 * 退货状态枚举
 * @author kevin
 *
 */
public enum UibCancelStateEnum {
	
	/**
	 * 0:取消订单中
	 */
	CANCEL_EXCE("0"),
	
	/**
	 * 1:取消订单失败
	 */
	CANCEL_FAILURE("1"),
	
	/**
	 * 2:取消订单成功
	 */
	CANCEL_SUCCESS("2");
	
	private String desc;
	
	private UibCancelStateEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	
}
