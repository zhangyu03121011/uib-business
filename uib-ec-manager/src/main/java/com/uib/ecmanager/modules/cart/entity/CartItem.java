/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/uib_ec">uib_ec</a> All rights reserved.
 */
package com.uib.ecmanager.modules.cart.entity;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.uib.ecmanager.common.persistence.DataEntity;

/**
 * 购物车项Entity
 * @author gaven
 * @version 2015-06-30
 */
public class CartItem extends DataEntity<CartItem> {
	
	private static final long serialVersionUID = 1L;
	private String cartId;		// 购物车ID
	private String productId;		// 商品ID
	private String specificationId;		// 规格ID
	private Integer quantity;		// 数量
	private Date modifyDate;		// 修改日期
	
	public CartItem() {
		super();
	}

	public CartItem(String id){
		super(id);
	}

	@Length(min=1, max=64, message="购物车ID长度必须介于 1 和 64 之间")
	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	
	@Length(min=1, max=64, message="商品ID长度必须介于 1 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	@Length(min=1, max=64, message="规格ID长度必须介于 1 和 64 之间")
	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}
	
	@NotNull(message="数量不能为空")
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="修改日期不能为空")
	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	
}