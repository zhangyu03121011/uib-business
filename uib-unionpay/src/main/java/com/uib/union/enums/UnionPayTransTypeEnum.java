package com.uib.union.enums;

/**
 * 银联在线交易类型
 * @author kevin
 *
 */
public enum UnionPayTransTypeEnum {
	
	/**
	 * 消费
	 */
	CONSUMPTION("01"),
	
	/**
	 * 消费撤销
	 */
	CONSUMPTION_CANCEL("31"),
	
	/**
	 * 预授权
	 */
	PRE_AUTHORIZATION("02"),
	
	/**
	 * 预授权撤销
	 */
	PRE_AUTHORIZATION_CANCEL("32"),
	
	/**
	 * 预授权完成
	 */
	PRE_AUTHORIZATION_COMPLETE("03"),
	
	
	/**
	 * 预授权完成撤销
	 */
	PRE_AUTHORIZATION_COMPLETE_CANCEL("33"),
	
	/**
	 * 退货
	 */
	RETURN_GOODS("04");
	
	
	
	private String desc;
	
	
	private UnionPayTransTypeEnum(String desc){
		this.desc = desc;
	}


	public String getDesc() {
		return desc;
	}
	
	
}
