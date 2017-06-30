/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderShippingRef extends DataEntity<OrderShippingRef> {
	
	private static final long serialVersionUID = 1L;
	private OrderTableShipping orderTableShipping;		// 发货主键ID 父类
	private String orderTabelId;		// 订单主键ID
	
	public OrderShippingRef() {
		super();
	}

	public OrderShippingRef(String id){
		super(id);
	}

	public OrderShippingRef(OrderTableShipping orderTableShipping){
		this.orderTableShipping = orderTableShipping;
	}

	@Length(min=1, max=64, message="发货主键ID长度必须介于 1 和 64 之间")

	public OrderTableShipping getOrderTableShipping() {
		return orderTableShipping;
	}

	public void setOrderTableShipping(OrderTableShipping orderTableShipping) {
		this.orderTableShipping = orderTableShipping;
	}
	
	@Length(min=1, max=64, message="订单主键ID长度必须介于 1 和 64 之间")
	public String getOrderTabelId() {
		return orderTabelId;
	}

	public void setOrderTabelId(String orderTabelId) {
		this.orderTabelId = orderTabelId;
	}

}