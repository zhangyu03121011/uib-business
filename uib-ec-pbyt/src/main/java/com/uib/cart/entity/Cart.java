/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.cart.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uib.common.utils.DateUtils;
import com.uib.member.entity.MemMember;
import com.uib.product.entity.Product;

/**
 * 购物车Entity
 * 
 * @author gaven
 * @version 2015-06-30
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String cartKey; // 密钥
	private String memberId; // 会员id
	private Date modifyDate; // 修改时间
	private Date createDate;
	private List<CartItem> cartItems = new ArrayList<CartItem>();
	private MemMember member;

	/** 超时时间 */
	public static final int TIMEOUT = 604800;
	/** 最大商品数 */
	public static final Integer MAX_PRODUCT_COUNT = 99;

	/** "ID"Cookie名称 */
	public static final String ID_COOKIE_NAME = "cartId";

	/** "密钥"Cookie名称 */
	public static final String KEY_COOKIE_NAME = "cartKey";

	public Cart() {
		super();
	}

	public Cart(String id, String cartKey, String memberId) {
		super();
		this.id = id;
		this.cartKey = cartKey;
		this.memberId = memberId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCartKey() {
		return cartKey;
	}

	public void setCartKey(String cartKey) {
		this.cartKey = cartKey;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 判断是否已过期
	 * 
	 * @return 是否已过期
	 */
	@Transient
	public boolean hasExpired() {
		return new Date().after(DateUtils.addSeconds(getModifyDate(), TIMEOUT));
	}
	
	public MemMember getMember() {
		return member;
	}

	public void setMember(MemMember member) {
		this.member = member;
	}

	/**
	 * 判断是否包含商品
	 * 
	 * @param product
	 *            商品
	 * @return 是否包含商品
	 */
	@Transient
	public boolean contains(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProductId() != null
						&& cartItem.getProductId().equals(product.getId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 获取购物车项
	 * 
	 * @param product
	 *            商品
	 * @return 购物车项
	 */
	@Transient
	public CartItem getCartItem(Product product) {
		if (product != null && getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getProductId() != null
						&& cartItem.getProductId().equals(product.getId())) {
					return cartItem;
				}
			}
		}
		return null;
	}

	/**
	 * 获取商品数量
	 * 
	 * @return 商品数量
	 */
	@Transient
	public int getQuantity() {
		int quantity = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getQuantity() != null) {
					quantity += cartItem.getQuantity();
				}
			}
		}
		return quantity;
	}

	/**
	 * 获取有效商品价格
	 * 
	 * @return 有效商品价格
	 */
	@Transient
	public double getEffectivePrice() {
		BigDecimal price = new BigDecimal(0);
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null && cartItem.getSubtotal() != null) {
					price = price.add(cartItem.getSubtotal());
				}
			}
		}
		return (price.compareTo(new BigDecimal(0)) > 0 ? price
				: new BigDecimal(0)).doubleValue();
	}
	
	/**
	 * 获取有效赠送积分
	 * 
	 * @return 有效赠送积分
	 */
	@Transient
	public long getEffectivePoint() {
		long effectivePoint = getPoint() + getAddedPoint();
		return effectivePoint > 0L ? effectivePoint : 0L;
	}
	
	/**
	 * 获取赠送积分增加值
	 * 
	 * @return 赠送积分增加值
	 */
	@Transient
	public long getAddedPoint() {
		long originalPoint = 0L;
		long currentPoint = 0L;
//		if (getCartItems() != null) {
//			for (CartItem cartItem : getCartItems()) {
//				if (cartItem != null) {
//					cartItem.setTempPoint(null);
//				}
//			}
//		}
		long addedPoint = currentPoint - originalPoint;
		return addedPoint > 0 ? addedPoint : 0L;
	}
	
	/**
	 * 获取赠送积分
	 * 
	 * @return 赠送积分
	 */
	@Transient
	public long getPoint() {
		long point = 0L;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					point += cartItem.getPoint();
				}
			}
		}
		return point;
	}
	
	/**
	 * 获取商品重量
	 * 
	 * @return 商品重量
	 */
	@Transient
	public int getWeight() {
		int weight = 0;
		if (getCartItems() != null) {
			for (CartItem cartItem : getCartItems()) {
				if (cartItem != null) {
					weight += cartItem.getWeight();
				}
			}
		}
		return weight;
	}

}