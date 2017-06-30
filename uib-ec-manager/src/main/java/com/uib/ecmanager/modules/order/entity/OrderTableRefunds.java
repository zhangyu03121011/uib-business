/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import java.beans.Transient;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.enums.Refunds_Method;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.method.entity.PaymentMethod;

/**
 * 订单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableRefunds extends DataEntity<OrderTableRefunds> {
	
	private static final long serialVersionUID = 1L;
	private String refundNo;		// 退款单编号
	private OrderTable orderTable;		// 订单表ID 父类
	private String account;		// 退款账号
	private BigDecimal amount;		// 退款金额
	private String bank;		// 退款银行
	private Integer method;		// 方式
	private String operator;		// 操作员
	private String payee;		// 收款人
	private String paymentMethod;		// 支付方式
	
	public OrderTableRefunds() {
		super();
	}

	public OrderTableRefunds(String id){
		super(id);
	}

	public OrderTableRefunds(OrderTable orderTable){
		this.orderTable = orderTable;
	}

	@Length(min=0, max=32, message="退款单编号长度必须介于 0 和 32 之间")
	public String getRefundNo() {
		return refundNo;
	}

	public void setRefundNo(String refundNo) {
		this.refundNo = refundNo;
	}
	
	@Length(min=1, max=64, message="订单表ID长度必须介于 1 和 64 之间")
	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Length(min=0, max=32, message="退款账号长度必须介于 0 和 32 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	@Transient
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	
	@Length(min=0, max=32, message="退款银行长度必须介于 0 和 32 之间")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	@Transient
	@Length(min=0, max=32, message="方式长度必须介于 0 和 32 之间")
	public Integer getMethod() {
		return method;
	}

	public void setMethod(Integer method) {
		this.method = method;
	}
	
	public String getMethodName(){
		return Refunds_Method.getDescription(method);
	}
	@Length(min=0, max=32, message="操作员长度必须介于 0 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=32, message="收款人长度必须介于 0 和 32 之间")
	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	@Length(min=0, max=32, message="支付方式长度必须介于 0 和 32 之间")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}