/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.order.entity;

import java.beans.Transient;
import java.math.BigDecimal;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.uib.ecmanager.common.persistence.DataEntity;
import com.uib.ecmanager.modules.product.entity.Product;

/**
 * 订单项Entity
 * @author limy
 * @version 2015-06-08
 */
public class OrderTableItem extends DataEntity<OrderTableItem> {
	
	private static final long serialVersionUID = 1L;
	private OrderTable orderTable;		// 订单表ID 父类
	private String fullName;		// 商品全称
	private String isGift;		// 是否为赠品
	private String name;		// 商品名称
	private BigDecimal price;		// 商品价格
	private Integer quantity;		// 商品数量
	private Integer returnQuantity;		// 退货量
	private Integer shippedQuantity;		// 发货量
	private Product product;		// 商品编号
	private String thumbnail;		// 缩略图
	private Integer weight;		// 商品重量
	private String orderNo;		// 订单编号
	private String productType;   // 订单类型：0-平台；1-推荐
	public OrderTableItem() {
		super();
	}

	public OrderTableItem(String id){
		super(id);
	}

	public OrderTableItem(OrderTable orderTable){
		this.orderTable = orderTable;
	}

	@Length(min=1, max=64, message="订单表ID长度必须介于 1 和 64 之间")
	public OrderTable getOrderTable() {
		return orderTable;
	}

	public void setOrderTable(OrderTable orderTable) {
		this.orderTable = orderTable;
	}
	
	@Length(min=0, max=32, message="商品全称长度必须介于 0 和 32 之间")
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	@Length(min=0, max=32, message="是否为赠品长度必须介于 0 和 32 之间")
	public String getIsGift() {
		return isGift;
	}

	public void setIsGift(String isGift) {
		this.isGift = isGift;
	}
	
	@Length(min=0, max=32, message="商品名称长度必须介于 0 和 32 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Transient
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	
	@Transient
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Transient
	public Integer getReturnQuantity() {
		return returnQuantity;
	}
	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	
	@Transient
	public Integer getShippedQuantity() {
		return shippedQuantity;
	}
	public void setShippedQuantity(Integer shippedQuantity) {
		this.shippedQuantity = shippedQuantity;
	}

	
	

	@Length(min=0, max=32, message="缩略图长度必须介于 0 和 32 之间")
	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@Transient
	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	
	@Length(min=0, max=32, message="订单编号长度必须介于 0 和 32 之间")
	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**
	 * 获取商品总重量
	 * 
	 * @return 商品总重量
	 */
	@JsonProperty
	@Transient
	public int getTotalWeight() {
		if (getWeight() != null && getQuantity() != null) {
			return getWeight() * getQuantity();
		} else {
			return 0;
		}
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}



	
	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@JsonProperty
	@Transient
	public BigDecimal getSubtotal() {
		if (getPrice() != null && getQuantity() != null) {
			return getPrice().multiply(new BigDecimal(getQuantity()));
		} else {
			return new BigDecimal(0);
		}
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}
}