/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 退货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderReturnsRef extends DataEntity<OrderReturnsRef> {
	
	private static final long serialVersionUID = 1L;
	private OrderTableReturns orderTableReturns;		// 退货主键ID 父类
	private String orderTabelId;		// 订单主键ID
	
	public OrderReturnsRef() {
		super();
	}

	public OrderReturnsRef(String id){
		super(id);
	}

	public OrderReturnsRef(OrderTableReturns orderTableReturns){
		this.orderTableReturns = orderTableReturns;
	}

	@Length(min=1, max=64, message="退货主键ID长度必须介于 1 和 64 之间")


	public OrderTableReturns getOrderTableReturns() {
		return orderTableReturns;
	}

	public void setOrderTableReturns(OrderTableReturns orderTableReturns) {
		this.orderTableReturns = orderTableReturns;
	}
	@Length(min=1, max=64, message="订单主键ID长度必须介于 1 和 64 之间")
	public String getOrderTabelId() {
		return orderTabelId;
	}

	public void setOrderTabelId(String orderTabelId) {
		this.orderTabelId = orderTabelId;
	}
	
}