/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.cart.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import com.uib.product.entity.Product;

/**
 * 购物车项Entity
 * 
 * @author gaven
 * @version 2015-06-30
 */
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String cartId; // 购物车ID
	private String productId; // 商品ID
	private String specificationId; // 规格ID 废弃字段
	private Integer quantity; // 数量
	private Date modifyDate; // 修改日期
	private Date createDate;//
	private Cart cart;
	private Product product;
	private Double productPrice;  //商品价格 
	private BigDecimal marketPrice; // 市场价
	
	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	/** 最大数量 */
	public static final Integer MAX_QUANTITY = 99;

	public CartItem() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getSpecificationId() {
		return specificationId;
	}

	public void setSpecificationId(String specificationId) {
		this.specificationId = specificationId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		if (cart != null) {
			this.cartId = cart.getId();
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	/**
	 * 增加商品数量
	 * 
	 * @param quantity
	 *            数量
	 */
	@Transient
	public void add(int quantity) {
		if (quantity > 0) {
			if (getQuantity() != null) {
				setQuantity(getQuantity() + quantity);
			} else {
				setQuantity(quantity);
			}
		}
	}
	
	@Transient
	public double getPrice() {
		//getProduct().getPrice()*quantity;
		return getProduct().getPrice();
	}
	
	/**
	 * 获取小计
	 * 
	 * @return 小计
	 */
	@Transient
	public BigDecimal getSubtotal() {
		if (getQuantity() != null) {
			BigDecimal price = new BigDecimal(getPrice()).setScale(2, RoundingMode.HALF_UP);
			BigDecimal quantPrice = price.multiply(new BigDecimal(getQuantity()));
			return quantPrice;
		} else {
			return new BigDecimal(0);
		}
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		if (getProduct() != null && getProduct().getWeight() != null && getQuantity() != null) {
			return (int)(getProduct().getWeight() * getQuantity());
		} else {
			return 0;
		}
	}
	
	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		if (getProduct() != null && getProduct().getPoint() != null && getQuantity() != null) {
			return getProduct().getPoint() * getQuantity();
		} else {
			return 0L;
		}
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	
}