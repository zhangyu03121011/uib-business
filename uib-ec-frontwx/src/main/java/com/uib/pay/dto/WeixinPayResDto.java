package com.uib.pay.dto;

import java.io.Serializable;

public class WeixinPayResDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	/**
	 * 订单编号
	 * @return
	 */
	private String orderNo;
	
	/**
	 * 收款账号
	 */
	private String account;
	
	/**
	 * 付款人
	 * @return
	 */
	private String payer;
	
	/**
	 * 付款金额
	 */
	private String amount;
	
	/**
	 * 付款日期
	 */
	private String paymentDate;
	
	/**
	 * 付款方式
	 */
	private String paymentMethod;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 付款银行
	 * @return
	 */
	private String paymentBank;
	
	/**
	 * 支付状态
	 */
	private Integer status;
	private String orderTableId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentBank() {
		return paymentBank;
	}

	public void setPaymentBank(String paymentBank) {
		this.paymentBank = paymentBank;
	}

	public String getOrderTableId() {
		return orderTableId;
	}

	public void setOrderTableId(String orderTableId) {
		this.orderTableId = orderTableId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}