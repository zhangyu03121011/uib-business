/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import java.beans.Transient;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.enums.OrderStatus;
import com.uib.ecmanager.common.enums.PaymentStatus;
import com.uib.ecmanager.common.enums.Payment_Method;
import com.uib.ecmanager.common.enums.Payment_Type;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.common.utils.OrderProperties;

/**
 * 收款表Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTablePayment extends DataEntity<OrderTablePayment> {
	
	private static final long serialVersionUID = 1L;
	private OrderTable orderTable;		// 订单表ID 父类
	private String orderNo;		// 订单编号
	private String method;		// 方式
	private String account;		// 账户
	private BigDecimal amount;		// 付款金额
	private String bank;		// 收款银行
	private Date expire;		// 到期时间
	private BigDecimal fee;		// 支付手续费
	private String operator;		// 操作员
	private String payer;		// 付款人
	private Date paymentDate;		// 付款日期
	private String paymentMethod;		// 付款方式
	private String paymentPluginId;		// 支付插件编号
	private String paymentNo;		// 收款账号
	private String status;		// 状态
	private String type;		// 类型
	private String userName;		// 用户名
	private String member;		// 会员
	private String isRemarks;		// 是否备注
	private String exceptionRemarks;		// 异常备注

	public OrderTablePayment() {
		super();
	}

	public OrderTablePayment(String id){
		super(id);
	}

	public OrderTablePayment(OrderTable orderTable){
		this.orderTable = orderTable;
	}

	@Length(min=1, max=64, message="订单表ID长度必须介于 1 和 64 之间")
	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Transient
	@Length(min=0, max=32, message="方式长度必须介于 0 和 32 之间")
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getMethodName(){
		return Payment_Method.getDescription(method);
	}
	@Length(min=0, max=32, message="账户长度必须介于 0 和 32 之间")
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

	@Length(min=0, max=32, message="收款银行长度必须介于 0 和 32 之间")
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}
	@Transient
	public BigDecimal getFee() {
		return fee;
	}
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	
	
	@Length(min=0, max=32, message="操作员长度必须介于 0 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Length(min=0, max=32, message="付款人长度必须介于 0 和 32 之间")
	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	@Length(min=0, max=32, message="付款方式长度必须介于 0 和 32 之间")
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	@Length(min=0, max=32, message="支付插件编号长度必须介于 0 和 32 之间")
	public String getPaymentPluginId() {
		return paymentPluginId;
	}

	public void setPaymentPluginId(String paymentPluginId) {
		this.paymentPluginId = paymentPluginId;
	}
	
	@Length(min=0, max=32, message="收款账号长度必须介于 0 和 32 之间")
	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	@Transient
	@Length(min=0, max=11, message="状态长度必须介于 0 和 11 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatusName(){
		return PaymentStatus.getDescription(status);
	}
	
	
	@Transient
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeName(){
		return Payment_Type.getDescription(type);
	}
	@Length(min=0, max=32, message="用户名长度必须介于 0 和 32 之间")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=32, message="会员长度必须介于 0 和 32 之间")
	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public String getIsRemarks() {
		return isRemarks;
	}

	public void setIsRemarks(String isRemarks) {
		this.isRemarks = isRemarks;
	}

	public String getExceptionRemarks() {
		return exceptionRemarks;
	}

	public void setExceptionRemarks(String exceptionRemarks) {
		this.exceptionRemarks = exceptionRemarks;
	}
	
}