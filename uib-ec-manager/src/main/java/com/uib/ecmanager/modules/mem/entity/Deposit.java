/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.mem.entity;

import java.beans.Transient;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.enums.Deposit_Type;
import com.uib.ecmanager.common.enums.ShippingStatus;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.order.entity.OrderTable;
import com.uib.ecmanager.modules.order.entity.OrderTablePayment;

/**
 * 预存款Entity
 * @author limy
 * @version 2015-06-15
 */
public class Deposit extends DataEntity<Deposit> {
	
	private static final long serialVersionUID = 1L;
	private BigDecimal balance;		// 当前余额
	private BigDecimal credit;		// 收入金额
	private BigDecimal debit;		// 支出金额
	private String operator;		// 操作员
	private Integer type;		// 类型
	private MemMember memMember;		// 会员
	private OrderTable orderTable;		// 订单
	private OrderTablePayment orderTablePayment;		// 收款单
	
	
	
	public Deposit() {
		super();
	}

	public Deposit(String id){
		super(id);
	}
	@Transient
	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	@Transient
	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	@Transient
	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	
	@Length(min=0, max=32, message="操作员长度必须介于 0 和 32 之间")
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Transient
	@Length(min=0, max=32, message="类型长度必须介于 0 和 32 之间")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getTypeName(){
		return Deposit_Type.getDescription(type);
	}


	public MemMember getMemMember() {
		return memMember;
	}

	public void setMemMember(MemMember memMember) {
		this.memMember = memMember;
	}

	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}

	public OrderTablePayment getOrderTablePayment() {
		return orderTablePayment;
	}

	public void setOrderTablePayment(OrderTablePayment orderTablePayment) {
		this.orderTablePayment = orderTablePayment;
	}
	
}