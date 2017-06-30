package com.uib.mobile.dto;

import java.util.List;

public class CartPojo4Mobile {
	private String id;
	private String cartKey; // 密钥
	private String memberId; // 会员id
	private List<CartItemPojo4Mobile> cartItems;//购物车项
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
	public List<CartItemPojo4Mobile> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItemPojo4Mobile> cartItems) {
		this.cartItems = cartItems;
	}
}
