package com.uib.union.enums;

/**
 * 银联全渠道支付 交易类型 
 * @author kevin
 *
 */
public enum TxnTypeEnum {
	
	/**
	 * 00-查询交易 
	 */
	QUERY_TRADE("00"),
	
	/**
	 * 01-消费
	 */
	CONSUMPTION("01"),
	
	
	/**
	 * 04-退货 
	 */
	RETURN_GOODS("04"),
	
	
	/**
	 * 31-消费撤销
	 */
	CANCEL_ORDER_TRADE("31");
	


	
	
	/*
	 00：查询交易 
	 01：消费 
	 02：预授权
	 03：预授权完成 
	 04：退货 
	 05：圈存 
	 11：代收 
	 12：代付 
	 13：账单支付 
	 */
	
	private TxnTypeEnum(String desc){
		this.desc = desc;
	}
	
	private String desc;

	public String getDesc() {
		return desc;
	}
	
	
}
