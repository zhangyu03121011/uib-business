package com.uib.union.enums;

/**
 * 保存银联全
 * @author kevin
 *
 */
public enum AcpsdkEnum {
	
	/**
	 * 查询交易
	 */
	QUERY_TRADE("00"),
	
	/**
	 * 消费
	 */
	CONSUMPTION("01"),

	/**
	 * 预授权
	 */
	PRE_AUTHORIZATION("02"),
	
	
	
	/**
	 * 预授权完成
	 */
	PRE_AUTHORIZATION_COMPLETE("03"),
	
	
	/**
	 * 退货
	 */
	RETURN_GOODS("04"),
	
	
	/**
	 * 圈存
	 */
	CIRCLE_SAVE("05"),
	
	
	
	/**
	 * 代收
	 */
	GENERATION_CLOSED("11"),

	
	
	/**
	 * 12：代付
	 */
	GENERATION_PAY("12");
	

	
/*
	13：账单支付

	14： 转账（保留）

	21：批量交易

	22：批量查询

	31：消费撤销

	32：预授权撤销

	33：预授权完成撤销

	71：余额查询

	72：实名认证-建立绑定关系

	73： 账单查询

	74：解除绑定关系

	75：查询绑定关系

	77：发送短信验证码交易

	78：开通查询交易

	79：开通交易

	94：IC卡脚本通知

*/
	

	private String desc;
	
	
	private  AcpsdkEnum(String desc){
		this.desc = desc;
	}


	public String getDesc() {
		return desc;
	}
	
	
	
}
