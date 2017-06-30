/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import java.beans.Transient;

import org.hibernate.validator.constraints.Length;

import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 发货单Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableShippingItem extends DataEntity<OrderTableShippingItem> {
	
	private static final long serialVersionUID = 1L;
	private OrderTableShipping orderTableShipping;		// 发货单ID 父类
	private String productNo;	//商品编号
	private String name;		// 商品名称
	private Integer quantity;		// 商品数量
	private String shippingNo;		// 发货单编号
	
	public OrderTableShippingItem() {
		super();
	}

	public OrderTableShippingItem(String id){
		super(id);
	}

	public OrderTableShippingItem(OrderTableShipping orderTableShipping){
		this.orderTableShipping = orderTableShipping;
	}

	@Length(min=1, max=64, message="发货单ID长度必须介于 1 和 64 之间")
	public OrderTableShipping getOrderTableShipping() {
		return orderTableShipping;
	}

	public void setOrderTableShipping(OrderTableShipping orderTableShipping) {
		this.orderTableShipping = orderTableShipping;
	}
	
	@Length(min=0, max=32, message="商品名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	@Length(min=0, max=11, message="商品数量长度必须介于 0 和 11 之间")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	@Length(min=0, max=32, message="发货单编号长度必须介于 0 和 32 之间")
	public String getShippingNo() {
		return shippingNo;
	}

	public void setShippingNo(String shippingNo) {
		this.shippingNo = shippingNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	
}