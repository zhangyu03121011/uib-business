package com.uib.union.enums;

/**
 * 交易子类型
 * @author kevin
 *
 */
public enum TxnSubTypeEnum {
	
	/**
	 * 00-默认为00
	 */
	DEFULT_TYPE("00"),
	
	/**
	 * 01:自助消费
	 */
	TRADE_CHILD("01"),
	
	/**
	 * 02:订购
	 */
	ORDER_BUY("02"),
	
	/**
	 * 03-分期付款
	 */
	INSTALLMENT_PAY("03");
	
	private String desc;
	
	private TxnSubTypeEnum(String desc){
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}
	
	
	
}
