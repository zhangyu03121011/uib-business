package com.uib.common.enums;

/**
 * 支付方式编码枚举类
 * 
 * @author gaven
 *
 */
public enum PaymentMethodCodeEnum {
	PAYMENT_METHOD_ON_LINE("在线支付", "1"), PAYMENT_METHOD_BANK_REMITTANCE("银行汇款",
			"2"), PAYMENT_METHOD_CASH_ON_DELIVERY("货到付款", "3");

	private String description;
	private String index;

	private PaymentMethodCodeEnum() {
	}

	private PaymentMethodCodeEnum(String description, String index) {
		this.description = description;
		this.index = index;
	}

	public String getDescription(String index) {
		for (PaymentMethodCodeEnum t : PaymentMethodCodeEnum.values()) {
			if (t.getIndex().equals(index)) {
				return t.description;
			}
		}
		return null;
	}
	
	public static PaymentMethodCodeEnum getEnum(String index) {
		for (PaymentMethodCodeEnum t : PaymentMethodCodeEnum.values()) {
			if (t.getIndex().equals(index)) {
				return t;
			}
		}
		return null;
	}

	public String getDescription() {
		return description;
	}

	public String getIndex() {
		return index;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIndex(String index) {
		this.index = index;
	}
}
